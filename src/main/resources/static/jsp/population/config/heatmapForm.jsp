<%@ page import="com.rainsoft.cache.DictionaryCache" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style type="text/css">
        label {
            font-weight: normal;
            margin-right: 20px
        }

        .map {
            height: 680px;
            padding: 20px
        }

        .modal-header {
            background: #fff;
            border-bottom: none
        }

        .modal-footer {
            border-top: none
        }

        .modal-footer button {
            width: 48%;
            height: 50px;
            padding: 0;
            margin: 0;
            font-size: 14px
        }

        .modal-footer {
            padding: 0
        }

        .modal-body p {
            font-size: 16px;
            padding: 30px 40px
        }

        .modal-body {
            padding: 0
        }

        input[type=checkbox] {
            right: 7px;
            position: relative;
            top: 2px
        }

        .input-group-addon {
            background: #fff !important
        }
    </style>
    <%@ include file="/jsp/frameSource.jsp" %>
    <link rel="stylesheet" href="${ctx }/js/layer/skin/layer.css"/>
    <script type="text/javascript" src="${ctx }/js/layer/layer.js"></script>
    <!--加载鼠标绘制工具-->

    <link rel="stylesheet" href="${mapLib}/DrawingManager/1.4/src/DrawingManager_min.css"/>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>添加热力分布管理</title>
</head>
<body>
<div class="row page-component-element">
    <form id="formData" class="form-inline">
         <input type="hidden" name="machineIds" id="machineIds">
        <div class="col-xs-12" style="padding:15px 0; width: 100%">
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">名称：</span>
                    <input type="text" class="form-control" placeholder="" name="name" id="name">
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">预警人数：</span>
                    <input type="text" class="form-control" placeholder="" name="warnNum" id="warnNum">
                </div>
            </div>
<!--             <div class="form-group"> -->
<!--                 <div class="input-group"> -->
<!--                     <span class="input-group-addon">区域内报警人数：</span> -->
<!--                     <input type="text" class="form-control" placeholder="" name="alermNum" id="alermNum"> -->
<!--                 </div> -->
<!--             </div> -->
<!--             <div class="form-group"> -->
<!--                 <div class="input-group input-medium  input-daterange" -->
<!--                      data-date-format="yyyy-mm-dd"> -->
<!--                     <span class="input-group-addon">有效期：</span> -->
<!--                     <input name="startTime" class="form-control datetime-picker" id="startTime" type="text"/> -->
<!--                     <span class="input-group-addon">到</span> -->
<!--                     <input name="endTime" class="form-control datetime-picker" -->
<!--                            id="endTime" type="text"/> -->
<!--                 </div> -->
<!--             </div> -->
			<div class="form-group">
				<div class="input-group">
                    <span class="input-group-addon">可疑人群计算规则：</span>
                    <input type="text" name="doubtfulPeriod" id="doubtfulPeriod" style="width: 40px">&nbsp;&nbsp;天内出现
                    <input type="text" name="doubtfulDays" id="doubtfulDays" style="width: 40px">&nbsp;&nbsp;天或
                    <input type="text" name="doubtfulTimes" id="doubtfulTimes" style="width: 40px">&nbsp;&nbsp;次
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <input type="checkbox" name="status" id="status" value="1"><label>是否启用</label>
                </div>
            </div>
            <div class="form-group">
                <button id="sumBtn" type="button" class="btn btn-default btn-md">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                    确定
                </button>
            </div>
        </div>
        <div class="col-xs-12" style="width: 100%">
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">已选择<span style="text-decoration:underline" id="machineNum">0</span>台云嗅设备</span>
                    <input type="button" class="btn btn-default" value="取消已选设备" id="cancelMachine">
                </div>
            </div>
        </div>
    </form>
</div>
</div>
<div class="map" id="areacontrast_map"></div>
<form id="form_query">
    <input type="hidden" name="id" value="1"/>
</form>

<script>
    var _area = {};
    //设备数组
    var _machineList = new Array();
    var configIdsss;
    $(document).ready(function () {
        $.getScript("${mapApi}", function () {
            $.getScript("${mapLib}/TextIconOverlay/1.2/src/TextIconOverlay_min.js", function () {
                $.getScript("${mapLib}/MarkerClusterer/1.2/src/MarkerClusterer_min.js", function () {
                    $.getScript("${mapLib}/DrawingManager/1.4/src/DrawingManager_min.js", function () {
                        $('#areacontrast_map').html('');
                        var $map = $('#areacontrast_map').rainsoft().map();	//创建地图实例
                        map = $map.instance();
                        var _pointdict = "<%=DictionaryCache.THRESHOLD_MAP.get("AT_LOCATION") %>".split(",");//地图位置
                        console.log(_pointdict)
                        if (_pointdict[0] != "null" && _pointdict != undefined) {
                            map.centerAndZoom(new BMap.Point(parseFloat(_pointdict[0]), parseFloat(_pointdict[1])), 15);
                        }
                        var arrDrawingModes = new Array(BMAP_DRAWING_CIRCLE, BMAP_DRAWING_RECTANGLE);//工具只显示圆和多边形
                        //覆盖物数组
                        var overlays = [];
                        //画图结束后触发
                        var overlaycomplete = function (e) {
                            if (overlays.length > 0) {
                                drawingToolRemove();
                            }
                            overlays.push(e.overlay);
                            var mapData = getMapData();
                            $.ajax(
                                {
                                    url: $.rainsoft.baseUrl() + 'population/config/heatmap/checkMachine',
                                    type: "post",
                                    data: JSON.stringify(mapData),
                                    dataType: "json",
                                    contentType: "application/json",
                                    success: function (json) {
                                        confirmMachine(json);
                                    }
                                }
                            );
                        };

                        //地图加载完成后获取当前区域

                        map.addEventListener("tilesloaded", function () {
                            getArea();
                            getEquipmentAll(_area);
                        });
                        //拖拽地图完毕后监听事件
                        map.addEventListener("dragend", function (evt) {
                            getArea();
                            getEquipmentAll(_area);
                        });
                        //获取当前可视区域
                        function getArea() {
                            var bs = map.getBounds();   //获取可视区域
                            var bssw = bs.getSouthWest();   //可视区域左下角
                            var bsne = bs.getNorthEast();   //可视区域右上角
                            _area = {"bsswlng": bssw.lng, "bsswlat": bssw.lat, "bsnelng": bsne.lng, "bsnelat": bsne.lat};
                            return _area;
                        }

//鼠标工具
                        var styleOptions = {
                            strokeColor: "red",    //边线颜色。
                            fillColor: "red",      //填充颜色。当参数为空时，圆形将没有填充效果。
                            strokeWeight: 3,       //边线的宽度，以像素为单位。
                            strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
                            fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
                            strokeStyle: 'solid' //边线的样式，solid或dashed。
                        };
                        var drawingManager = "";
                        //实例化鼠标绘制工具
                        function showEnableDrawingTool(isOpen, arrDrawingModes) {
                            $(".BMapLib_Drawing_panel").remove();
                            drawingManager = new BMapLib.DrawingManager(map, {
                                isOpen: false, //是否开启绘制模式
                                enableDrawingTool: isOpen, //是否显示工具栏
                                drawingToolOptions: {
                                    anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
                                    offset: new BMap.Size(5, 5), //偏离值
                                    drawingModes: arrDrawingModes
                                },
                                circleOptions: styleOptions, //圆的样式
                                rectangleOptions: styleOptions //矩形的样式
                            });
                            //添加鼠标绘制工具监听事件，用于获取绘制结果
                            drawingManager.addEventListener('overlaycomplete', overlaycomplete);
                        }

                        //初始化
                        showEnableDrawingTool(true, arrDrawingModes);

                        //map删除鼠标工具
                        function drawingToolRemove(arrDrawingModes) {
                            drawingManagerClose();
                            $(".BMapLib_Drawing_panel").remove();
                            showEnableDrawingTool(true, new Array());
                        };

                        //map鼠标变为不可编辑状态
                        function drawingManagerClose() {
                            drawingManager.close();
                        };


                        //删除所有点
                        function deletePoint() {
                            overlays = map.getOverlays();
                            for (var i = 0; i < overlays.length - 1; i++) {
                                map.removeOverlay(overlays[i]);
                            }

                        }

                        //获得当前可视范围所有设备经纬度信息标注
                        function getEquipmentAll(areaCode) {
                            $.ajax(
                                {
                                    url: $.rainsoft.baseUrl() + 'companion/getEquipmentAll',
                                    type: "post",
                                    data: JSON.stringify(areaCode),
                                    dataType: "json",
                                    contentType: "application/json",
                                    success: function (json) {
                                        addMarker(json);
                                    }
                                }
                            );
                        }

//单机加弹窗
                        function addClickHandler(content, marker) {
                            marker.addEventListener("click", function (e) {
                                    openInfo(content, e)
                                }
                            );
                        }

                        // 编写自定义函数,创建点聚合
                        function addMarker(arry) {
                            var MAX = arry.length;
                            var markers = [];
                            var pt = null;
                            var i = 0;
                            for (; i < MAX; i++) {
                                pt = new BMap.Point(arry[i].longitude, arry[i].latitude);
                                markers.push(new BMap.Marker(pt));
                            }
                            //最简单的用法，生成一个marker数组，然后调用markerClusterer类即可。
                            setTimeout(function () {
                                new BMapLib.MarkerClusterer(map, {markers: markers});
                                for (var i = 0; i < markers.length; i++) {
                                    addClickHandler('<div><span>地址:' + arry[i].serviceAddress + '</span></div><div><span>设备:' + arry[i].machineName + '</span></div>', markers[i]);
                                }
                            }, 1000);
                        }
                        ;
//信息窗口设置
                        var opts = {
                            width: 250,     // 信息窗口宽度
                            height: 80,     // 信息窗口高度
                            //title : "信息窗口" , // 信息窗口标题
                            enableMessage: true//设置允许信息窗发送短息3
                        };


                        function openInfo(content, e) {
                            var p = e.target;
                            var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
                            var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
                            map.openInfoWindow(infoWindow, point); //开启信息窗口
                        }

                        //获得map数据
                        function getMapData() {
                            var ary_mapData = {};
                            var ary_circle_label = new Array();
                            var ary_rectangle_label = new Array();
                            for (var i = 0; i < overlays.length; i++) {
                                var label_data = {};
                                var p = overlays[i];
                                if (p instanceof BMap.Circle) {
                                    label_data.radius = p.getRadius() / 1000;
                                    label_data.lng = p.getCenter().lng;
                                    label_data.lat = p.getCenter().lat;
                                    ary_circle_label[ary_circle_label.length] = label_data;
                                } else if (p instanceof BMap.Polygon) {
                                    var sw = p.getBounds().getSouthWest(); //西南脚点
                                    var ne = p.getBounds().getNorthEast(); //东北脚点
                                    label_data.sw_lng = sw.lng;
                                    label_data.sw_lat = sw.lat;
                                    label_data.ne_lng = ne.lng;
                                    label_data.ne_lat = ne.lat;
                                    ary_rectangle_label[ary_rectangle_label.length] = label_data;
                                }
                            }
                            ary_mapData.ary_circle_label = ary_circle_label;
                            ary_mapData.ary_rectangle_label = ary_rectangle_label;
                            return ary_mapData;
                        };

                        //询问框
                        function confirmMachine(machineList) {
                            layer.confirm('当前你选取了' + machineList.length + '台云嗅设备', {
                                btn: ['确定', '取消'] //按钮
                            }, function (index) {
                                if (machineList.length == 0) {
                                    layer.msg('请重新选择设备', {
                                        time: 1000 //1s后自动关闭
                                    });
                                    deleteGraph();
                                    showEnableDrawingTool(true, arrDrawingModes);
                                    layer.close(index);
                                } else {
                                    layer.close(index);
                                    _machineList = machineList;
                                    $('#machineNum').html(_machineList.length);
                                    drawingToolRemove();
                                }
                            }, function () {
                                deleteGraph();
                                layer.msg('请重新选择设备', {
                                    time: 1000 //1s后自动关闭
                                });
                            });
                        }

                        $('#cancelMachine').click(function () {
                            if (_machineList == null || _machineList == '' || _machineList.length == 0) {
                                layer.msg('没有已选取的设备', {
                                    time: 1000, //1s后自动关闭
                                });
                            } else {
                                if (confirm("确定取消已选设备?")) {
                                    //初始化
                                    showEnableDrawingTool(true, arrDrawingModes);
                                    _machineList = [];
                                    $('#machineNum').html(0);
                                    deleteGraph();
                                    layer.msg('请重新选择设备', {
                                        time: 1000 //1s后自动关闭
                                    });
                                }
                            }
                        });

                        //点取消删除相应图形
                        function deleteGraph() {
                            var alloverlays = map.getOverlays();
                            for (var i = 0; i < alloverlays.length; i++) {
                                if (alloverlays[i] instanceof BMap.Circle || alloverlays[i] instanceof BMap.Polygon) {
                                    map.removeOverlay(alloverlays[i]);
                                }

                            }
                            overlays = [];
                        }


                        //ajax提交form表单的方式

                        $('#sumBtn').click(function () {
                                $('#formData').validate(validateSetting());
                                if ($('#formData').valid()) {
                                    if (_machineList == null || _machineList == '') {
                                        alert('请选择设备');
                                        return false;
                                    }

                                    var machineIds = [];
                                    $.each(_machineList, function (i, n) {
                                        machineIds.push(n.machineId);
                                    });
                                    var data = {};
                                    data.name = $('#name').val();
                                    data.warnNum = $('#warnNum').val();
                                    data.alermNum = $('#alermNum').val();
                                    data.startTime = $('#startTime').val();
                                    data.endTime = $('#endTime').val();
                                    data.doubtfulPeriod= $('#doubtfulPeriod').val();
                                    data.doubtfulDays = $('#doubtfulDays').val();
                                    data.doubtfulTimes= $('#doubtfulTimes').val();
                                    data.machineIds = machineIds;
                                    data.mapData = getMapData();
                                    if (configIdsss) {
                                        data.id = configIdsss.id;
                                    }
                                    if ($('#status').is(':checked')) {
                                        data.status = 1;
                                    } else {
                                        data.status = 0;
                                    }
                                    $.ajax({
                                        url: $.rainsoft.baseUrl() + 'population/config/heatmap/add',
                                        type: "post",
                                        data: JSON.stringify(data),
                                        dataType: "json",
                                        contentType: "application/json",
                                        success: function (data) {
                                            if (data.state == 1) {
                                                layer.msg(data.message, {icon: 1, time: 2000}, function () {
                                                	parent.$("body").attr("editForm", true);//成功后增加标识
                                                    /* parent.location.reload(); // 父页面刷新*/
                                                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                                                    parent.layer.close(index);
                                                    //window.location = $.rainsoft.baseUrl() + 'population/config/heatmap/list';
                                                });
                                            } else {
                                                layer.msg(data.message, {icon: 0, time: 2000});
                                            }
                                        }
                                    });
                                }
                            }
                        );

                        //更新回显
                        configIdsss = eval(${heatDistribution});
                        if (configIdsss != null && configIdsss != undefined) {
                            $('#name').val(configIdsss.name);
                            $('#warnNum').val(configIdsss.warnNum);
                            $('#doubtfulDays').val(configIdsss.doubtfulDays);
                            $('#doubtfulPeriod').val(configIdsss.doubtfulPeriod);
                            $('#doubtfulTimes').val(configIdsss.doubtfulTimes);
                            var machineIds = configIdsss.machineIds;
                            $.each(machineIds, function (i, n) {
                                var machineInfo = new Object();
                                machineInfo.machineId = n;
                                _machineList.push(machineInfo);
                            });
                            if (configIdsss.mapType == 1) {
                                var mapInfo = eval(configIdsss.mapInfo);
                                var rectangle = new BMap.Polygon([
                                    new BMap.Point(mapInfo[0].sw_lng, mapInfo[0].sw_lat),
                                    new BMap.Point(mapInfo[0].ne_lng, mapInfo[0].sw_lat),
                                    new BMap.Point(mapInfo[0].ne_lng, mapInfo[0].ne_lat),
                                    new BMap.Point(mapInfo[0].sw_lng, mapInfo[0].ne_lat)
                                ], {strokeColor: "red", strokeWeight: 2, fillColor: "red"});  //创建矩形
                                map.addOverlay(rectangle);
                                overlays.push(rectangle)//增加矩形
                            }
                            if (configIdsss.mapType == 2) {
                                var mapInfo1 = eval(configIdsss.mapInfo);
                                var circle = new BMap.Circle(new BMap.Point(mapInfo1[0].lng, mapInfo1[0].lat), mapInfo1[0].radius * 1000, {
                                    strokeColor: "red",
                                    strokeWeight: 2,
                                    fillColor: "red"
                                }); //创建圆
                                map.addOverlay(circle);            //增加圆
                                overlays.push(circle)
                            }
                            //禁止画图
                            drawingToolRemove(new Array());
                            $("#machineNum").html(configIdsss.deviceNum);
                            if (configIdsss.status == 1) {
                                $("input[name='status']").attr("checked", true);
                            }
                        }
                    })
                })
            })
        });
        $.date97();
    });


    var validateSetting = function (data, messages) {
        return {
            rules: {

                name: {
                    required: true,
                    maxlength:13
                },
                warnNum: {
                    required: true,
                    digits: true,
                    maxlength: 4
                },
                alermNum: {
                    required: true,
                    digits: true,
                    maxlength: 4
                },
                startTime: {
                    required: true
                },
                endTime: {
                    required: true
                },
                doubtfulPeriod: {
                    required: true,
                    digits: true
                },
                doubtfulDays: {
                    required: true,
                    digits: true
                },
                doubtfulTimes: {
                    required: true,
                    digits: true
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
                name: {
                    required: "请输入",
                    maxlength:"名称过长"
                },
                warnNum: {
                    required: "请输入"
                },
                alermNum: {
                    required: "请输入"
                },
                startTime: {
                    required: "请输入"
                },
                endTime: {
                    required: "请输入"
                },
                doubtfulPeriod: {
                    required: "请输入"

                },
                doubtfulDays: {
                    required: "请输入"

                },
                doubtfulTimes: {
                    required: "请输入"

                }
            }, messages)
        };
    };

</script>
</body>
</html>