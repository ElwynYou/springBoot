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
    <title>设备查询</title>
</head>
<body>
<div class="row page-component-element">
    <form id="searchForm" target="<%=request.getHeader("X-Requested-With") != null?"ajax":"_self" %>"
    >
        <div class="col-md-12 col-sm-12" style="padding-top:15px; width: 100%">
            <div class="col-md-2 col-sm-2">
                <div class="form-group">
                    <div class="input-group">
                        <label class="input-group-addon">所属城区</label>
                        <sys:userarea value="${areaCode}" name="areaCode"/>
                    </div>
                </div>
            </div>
            <div class="col-md-2 col-sm-2">
                <div class="input-group">
                    <span class="input-group-addon">采集设备</span>
                    <input name="machineId" id="machineId" type="text" class="form-control">
                </div>
            </div>

            <div class="col-md-2 col-sm-2">
                <div class="input-group">
                    <input id="highRisk" name="dangerPerson" value="0" type="checkbox"/>
                    <label for="highRisk">关注人群</label>
                    <input id="prevent" name="dangerArea" value="0" type="checkbox"/>
                    <label for="prevent">重点防范</label>
                </div>
            </div>
            <div class="col-md-3 col-sm-3">
                <div class="input-group">
                    <span class="input-group-addon">捕获时间</span>
                    <input name="beginTime" class="input-medium Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id="beginTime" type="text"
                           style="width: 140px">
                    <span class="input-group-addon">-</span>
                    <input name="endTime" class="input-medium Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', maxDate:'%y-%M-%d %H:%m'})"
                           id="endTime" type="text" style="width: 140px">
                </div>
            </div>
            <div class="col-md-2 col-sm-1" style="margin-left:40px">
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

            <div class="col-xs-12">
                <div class="input-group" style=" float: right;">
                    <i class="glyphicon glyphicon-list" style=" color: #9d7854;cursor:pointer;"  onclick="getMachineList()">列表模式</i>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="col-md-12 col-sm-12" style="padding:15px 0; width: 100%">

    <div class="map" id="areacontrast_map"></div>

</div>

</head>
<script>
    var machineList;
    var _markerList = [];
    var map;
    $(document).ready(function () {
        $.getScript("${mapApi}", function () {//由于百度地图的功能问题,需要延时执行地图加载代码.
            // 百度地图API功能
            map = new BMap.Map("areacontrast_map");    // 创建Map实例
            var point = new BMap.Point(121.378895, 31.231976); // 创建点坐标
            var zoom = 16;
            map.centerAndZoom(point, zoom); // 初始化地图，设置中心点坐标和地图级别
            var _pointdict = "<%=DictionaryCache.THRESHOLD_MAP.get("AT_LOCATION") %>".split(",");//地图位置
            if (_pointdict[0] != "null" && _pointdict != undefined) {
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
            $('#searchForm').validate(validateSetting);
            $('#sumBtn').click(function (e) {
                if($("#searchForm").valid()){
                    $.ajax({
                        url: $.rainsoft.baseUrl() + "population/query/machineMapQuery",
                        data: $('#searchForm').serialize(),
                        type: 'post',
                        dataType: 'json',
                        success: function (data) {
                            console.log(data);
                            layer.close(loading);
                            if (data == null || data == undefined || data.length == 0) {
                                deletePoint(map);
                                layer.msg("没有查询到设备信息", {icon: 0, time: 2000});
                                return;
                            }

                            machineList = data;

                            var bpoints = [];
                            deletePoint(map);
                            _markerList = [];
                            bpoints = [];
                            $.each(machineList, function (index, element) {
                                var point = new BMap.Point(element.longitude, element.latitude);
                                bpoints.push(point);	//保存所有的点, 供画线和切换视图.
                                var marker = addMarker(point, map);

                                _markerList.push(marker);
                                var content = "<div><p>设备名称" + element.machineName + "</p>" +
                                    "<p>设备ID:" + element.machineId + "</p>" +
                                    "<p>总采集人数:" + element.timesCount + "</p>" +
                                    "<p>关注人群:" + element.highriskCount + "</p>" +
                                    "<p>高危地区人群:" + element.preventCount + "</p>" +
                                    "</div>";
                                addClickHandler(content, marker);

                            });
                            map.setCenter(bpoints[0]);
                        },
                        error: function () {
                            layer.close(loading);
                            layer.msg("查询失败", {icon: 0, time: 2000});
                        }
                    })
                }


            });


        });

        $.date97();
    });


    $('#protocolType').change(function () {
        if ($(this).children('option:selected').val() == 'Mac') {
            $('#certificateCode').attr('onkeyup', 'macAddLayout(this)').attr('maxlength', '17')
        }
        if ($(this).children('option:selected').val() != 'Mac') {
            $('#certificateCode').removeAttr('onkeyup').removeAttr('maxlength')
        }

    });


    function addClickHandler(content, marker) {
        marker.addEventListener("click", function (e) {
                openInfo(content, e)
            }
        );
    }
    function openInfo(content, e) {
        var p = e.target;
        var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
        var infoWindow = new BMap.InfoWindow(content, {
            width: 250,     // 信息窗口宽度
            height: 130     // 信息窗口高度
        });  // 创建信息窗口对象
        map.openInfoWindow(infoWindow, point); //开启信息窗口
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


    function getMachineList() {
        showLoading();
        var areaCode = $('#areaCode').val();
        var machineId = $('#machineId').val();
        var dangerPerson = $('#highRisk').val();
        var dangerArea = $('#prevent').val();
        var beginTime = $('#beginTime').val();
        var endTime = $('#endTime').val();
        var data = {
            areaCode: areaCode, machineId: machineId, dangerPerson: dangerPerson, dangerArea: dangerArea,
            beginTime: beginTime, endTime: endTime
        };
        var href = $.rainsoft.baseUrl() + "population/query/machineList";
        $.post(href, data, function (page) {
            $("#c").remove();
            $("#pageContainer").addClass("active in").html(page);
            $('#pageContainer').trigger('layoutChange', $.rainsoft.getPageContainerSize());
            closeLoading();
        });
    }


    $('#highRisk').click(function () {
        if ($(this).val() == 1) {
            $(this).val(0);
        } else {
            $(this).val(1);
        }

    });
    $('#prevent').click(function () {
        if ($(this).val() == 1) {
            $(this).val(0);
        } else {
            $(this).val(1);
        }
    });

    var validateSetting = {

        rules: {
            beginTime: {
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
                //  error.insertAfter(element);
                layer.tips(error[0].textContent, element, {
                    tips: [3, '#f10f00'],  tipsMore: true
                });
            }
            //   error.wrap('<font style="color:red;"/>');
        },
        messages: {
            beginTime: {
                required: "请输入"
            },
            endTime: {
                required: "请输入"
            }
        }

    };
</script>
</body>
</html>