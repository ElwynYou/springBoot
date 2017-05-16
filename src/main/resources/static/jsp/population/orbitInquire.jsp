<%@ page import="com.rainsoft.cache.DictionaryCache" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style type="text/css">
        .map {
            height: 680px;
            padding: 0px 0px
        }

        label {
            font-weight: normal;
            margin-right: 20px
        }

        input[type=checkbox] {
            right: 7px;
            position: relative;
            top: 2px
        }

        .showTable div {
            border: 2px solid #68c8a0;
            height: 30px;
            width: 0;
            display: inline-block
        }

        .showTable span {
            position: relative;
            top: -10px;
            left: 10px;
            color: #68c8a0
        }

        .exportList {
            font-size: 16px;
            background: #f4ecdc;
            color: #aa896a;
            line-height: 30px;
            margin-bottom: 15px;
            padding-left: 10px
        }
    </style>
    <%@ include file="/jsp/frameSource.jsp" %>
    <link rel="stylesheet" href="${ctx }/js/layer/skin/layer.css"/>
    <script type="text/javascript" src="${ctx }/js/layer/layer.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>轨迹查询</title>
</head>
<body>
<div class="row page-component-element">
    <form id="formData">
        <div id="tracelocation_control">
            <div class="col-md-12 col-sm-12" style="padding:15px 0; width: 100%">

                <input type="hidden" name="pageNo" id="pageNo" value="1">
                <div class="col-md-1 col-sm-2" style="padding-left:0px">
                    <span></span>
                    <div class="input-group">
                        <input type="text" class="form-control" id="certificateCode" name="certificateCode">
                    </div>
                </div>
                <div class="col-md-3 col-sm-3">
                    <div class="input-group input-medium  input-daterange"
                         data-date-format="yyyy-mm-dd">
                        <span class="input-group-addon">时段</span> <input name="startTime"
                                                                         class="form-control datetime-picker"
                                                                         id="startTime" type="text"> <span
                            class="input-group-addon">到</span> <input name="endTime"
                                                                      class="form-control datetime-picker"
                                                                      id="endTime" type="text">
                    </div>
                </div>
                <div class="col-md-2 col-sm-1">
                    <div class="input-group">
                        <button type="button" id="sumBtn" class="btn btn-default btn-md">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                            查询
                        </button>
                        <button type="reset" class="btn reset-btn btn-md">
                            <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                            重置
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="col-md-12 col-sm-12" style="padding:15px 0; width: 100%">
    <div class="col-md-8 col-sm-8">
        <div class="map" id="areacontrast_map"></div>
    </div>
    <div class="col-md-4  col-sm-4">
        <div class="exportList">轨迹列表</div>
        <table class="table table-bordered table-striped" id="dataList">
            <thead>
            <tr>
                <th>序号</th>
                <th>捕获时间</th>
                <th>场所设备</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
</div>

</head>
<script>
    var machineList;
    var _markerList=[];
    var map;
    $(document).ready(function () {
        $.getScript("${mapApi}",function () {//由于百度地图的功能问题,需要延时执行地图加载代码.
            // 百度地图API功能
             map = new BMap.Map("areacontrast_map");    // 创建Map实例
            var point = new BMap.Point(121.378895, 31.231976); // 创建点坐标
            var zoom = 16;
            map.centerAndZoom(point, zoom); // 初始化地图，设置中心点坐标和地图级别
            var  _pointdict = "<%=DictionaryCache.THRESHOLD_MAP.get("AT_LOCATION") %>".split(",");//地图位置
            if (_pointdict[0]!="null"&&_pointdict!=undefined){
                map.centerAndZoom(new BMap.Point(parseFloat(_pointdict[0]), parseFloat(_pointdict[1])), 15);
            }
            map.enableScrollWheelZoom(); // 启用滚动缩放
            map.addControl(new BMap.ScaleControl()); // 比例尺
            var navigationControl = new BMap.NavigationControl({
                // 靠左上角位置
                anchor: BMAP_ANCHOR_TOP_LEFT,
                // LARGE类型
                type: BMAP_NAVIGATION_CONTROL_LARGE,
                // 启用显示定位
                enableGeolocation: false
            });
            map.addControl(navigationControl); // 添加导航控件。

            $('#sumBtn').click(function (e) {
                $('#formData').validate(validateSetting());
                if (!$('#formData').valid()) {
                    return;
                }
                var loading = layer.load(1, {
                    shade: [0.3,'#000'] //0.1透明度的白色背景
                });
                var startTime = $('#startTime').val();
                var endTime = $('#endTime').val();
                var protocolType = $('#protocolType :selected').val();
                var certificateCode = $('#certificateCode').val();
                var pageNo = $('#pageNo').val();
                var _data = {
                    "protocolType": protocolType,
                    "certificateCode": certificateCode,
                    "startTime": startTime,
                    "endTime": endTime,
                    "pageNo": pageNo
                };


                $.ajax({
                    url: $.rainsoft.baseUrl() + "/traceQuery/list",
                    data: JSON.stringify(_data),
                    type: 'post',
                    dataType: 'json',
                    contentType: 'application/json',
                    success: function (data) {
                        layer.close(loading);
                        if(data.machineInfo==null||data.machineInfo.length==0||data.machineInfo==undefined){
                            $('#dataList tbody').html('');
                            deletePoint(map);
                            layer.msg("没有查询到轨迹信息", {icon: 0,time:2000});
                            return;
                        }
                        machineList = data.machineInfo;
                        var res = data.page.list;
                        var pageno = data.page.pageNo;
                        var pageSize = data.page.pageSize;
                        $('#dataList tbody').html('');
                        $.each(res, function (i, n) {
                            var index=((pageno - 1) * pageSize + i + 1)
                            $('#dataList').append('<tr onclick="javascript:clickPoint(this,_markerList);"><td>' + index + '</td><td>' + n.captureTime + '</td><td>' + n.address + '</td><td  style="display:none">' + n.longitude + '</td><td  style="display:none">' + n.latitude + '</td></tr>')
                        })
                        $('#dataList').next().remove();
                        $('#dataList').after(data.page);
                        var bpoints = [];
                        deletePoint(map);
                        _markerList=[];
                        bpoints=[];
                        $.each(machineList, function (index, element) {
                            var point = new BMap.Point(element.longitude, element.latitude);
                            bpoints.push(point);	//保存所有的点, 供画线和切换视图.
                             var marker = addMarker(point, map);
                            _markerList.push(marker);
                            var label = createLabel(index + 1 + '', {
                                position: point,
                                offset: new BMap.Size(-5, -22),
                                style: {border: 'none', "background": 'transparent', color: '#fff'}
                            }, map).add();
                        })
                        map.setCenter(bpoints[0]);
                        var bline = new BMap.Polyline(bpoints);
                        bline.setStrokeColor('blue');
                        bline.setStrokeWeight(3);
                        map.addOverlay(bline);
                    },
                    error:function () {
                        layer.close(loading);
                        layer.msg("查询失败", {icon: 0,time:2000});
                    }
                })
            });


        });

        $.date97();
    });
    function goPage(pageNo) {
        $('#pageNo').val(pageNo);
        $.ajax({
            url: $.rainsoft.baseUrl() + "/traceQuery/changePage",
            data: {"pageNo": pageNo},
            type: 'post',
            dataType: 'json',
            success: function (data) {
                var res = data.page.result;
                var pageno = data.page.pageNo;
                var pageSize = data.page.pageSize;
                $('#dataList tbody').html('');
                $.each(res, function (i, n) {
                    var index=((pageno - 1) * pageSize + i + 1)
                    $('#dataList').append('<tr onclick="javascript:clickPoint(this,_markerList);"><td>' + index + '</td><td>' + n.captureTime + '</td><td>' + n.address + '</td><td  style="display:none">' + n.longitude + '</td><td  style="display:none">' + n.latitude + '</td></tr>')
                })
                $('#dataList').next().remove();
                $('#dataList').after(data.pageNav);
            }
        })
    }

    $('#protocolType').change(function () {
        if ($(this).children('option:selected').val() == 'Mac') {
            $('#certificateCode').attr('onkeyup', 'macAddLayout(this)').attr('maxlength', '17')
        }
        if ($(this).children('option:selected').val() != 'Mac') {
            $('#certificateCode').removeAttr('onkeyup').removeAttr('maxlength')
        }

    });

    function macAddLayout(obj) {
        var aryMacLayoutNum = new Array(2, 5, 8, 11, 14);
        for (var i = 0; i < aryMacLayoutNum.length; i++) {
            if (aryMacLayoutNum[i] == ($(obj).val().length)) {
                $(obj).val($(obj).val() + "-");
            }
        }
    }


    /**添加覆盖物**/
    function createLabel(element, option, map) {
        option = $.extend({anchor: BMap.Size(0, 0)}, option);
        if (!(option.position instanceof BMap.Point)) {
            option.position = new BMap.Point(option.position.lng, option.position.lat);
        }
        var htmlElement;
        if (typeof element == 'string' || element instanceof Element || element instanceof String) {
            htmlElement = element;
        } else if (element instanceof jQuery) {
            if (element.length === 1) {
                htmlElement = element[0];
            } else {
                htmlElement = $('<div>').append(element)[0];
            }
        }
        var label = new BMap.Label(htmlElement, option);
        if (option.style) {
            label.setStyle(option.style);
        }
        label.add = function (callback) {
            map.addOverlay(label);
            if (callback) {
                callback(label);
            }
            return this;
        }
        return label;
    }


    function addMarker(point, map) {
        if (!(point instanceof BMap.Point)) {
            point = new BMap.Point(point.lng, point.lat);
        }
        var marker = new BMap.Marker(point);  // 创建标注
        map.addOverlay(marker);               // 将标注添加到地图中
        return marker;
    }

    //删除所有点
    function deletePoint(map) {
        var allOverlay = map.getOverlays();
        for (var i = 0; i < allOverlay.length - 1; i++) {
            map.removeOverlay(allOverlay[i]);
        }

    }

    function clickPoint(data,markerList) {
        var $data = $(data);
        var index=$data.children(":first").text()-1;
        var latitude = $data.children(":last").text();
        var longitude = $data.children(":last").prev().text();
        var marker=markerList[index];
        $.each(markerList,function (i, n) {
            n.setAnimation(null);
        })
        var point = new BMap.Point(longitude,latitude);
        map.setCenter(point);
        marker.setAnimation(BMAP_ANIMATION_BOUNCE);
    }

    var validateSetting = function (data, messages) {
        return {
            rules: {

                certificateCode: {
                    required: true,
                },
                startTime: {
                    required: true
                },
                endTime: {
                    required: true
                }
            },
            errorPlacement: function (error, element) {
                if (element.is(':radio') || element.is(':checkbox')) { //如果是radio或checkbox
                    error.appendTo(element.parent()); //将错误信息添加当前元素的父结点后面
                } else {
                    error.insertAfter(element);
                }
                error.wrap('<font style="color:red;"/>');
            },
            messages: $.extend({

                certificateCode: {
                    required: "请输入"
                },
                startTime: {
                    required: "请输入"
                },
                endTime: {
                    required: "请输入"
                }
            }, messages)
        };
    };
</script>
</body>
</html>