<%@ page import="com.rainsoft.cache.DictionaryCache" %>
<%@ page import="com.rainsoft.core.utils.Utils" %>
<%@ taglib prefix="sys" uri="/rs" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    pageContext.setAttribute("now", Utils.getServerTime("yyyy-MM-dd HH:mm"));
    pageContext.setAttribute("today", Utils.getServerTime("yyyy-MM-dd 00:00"));
%>
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
           margin: 0 auto;
        }

        .input-group-addon {
            background: #fff !important
        }

        table.gridtable {
            font-family: verdana, arial, sans-serif;
            font-size: 11px;
            color: #333333;
            border-width: 1px;
            border-color: #666666;
            border-collapse: collapse;
            opacity: 10;
            width: 100%;
            padding: 20px;
        }

        table.gridtable th {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #dedede;
        }

        table.gridtable td {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #ffffff;
        }
    </style>
    <%@ include file="/jsp/frameSource.jsp" %>
    <link rel="stylesheet" href="${ctx }/js/layer/skin/layer.css"/>
    <script type="text/javascript" src="${ctx }/js/layer/layer.js"></script>
    <script type="text/javascript" src="${ctx}/js/form.utils.js"></script>
    <!--加载鼠标绘制工具-->

    <link rel="stylesheet" href="${mapLib}/DrawingManager/1.4/src/DrawingManager_min.css"/>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>区域碰撞</title>
</head>
<body>


<div class="col-md-12 col-sm-12" style="padding:15px 0; width: 100%;float: left;">
    <div class="col-md-8 col-sm-8" style="width: 70%">
        <div class="map" id="areacontrast_map"></div>
    </div>
    <form id="dataForm">
        <div class="col-md-4 col-sm-4" style="height:640px; overflow:auto;width: 30%">
            <div id="machineList"></div>
            <div class="input-group" style="float: right;margin-top: 5px;display: none" id="btnDiv">
                <button type="button" class="btn btn-default btn-md" id="subtn">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                    碰撞分析
                </button>
                <button type="button" class="btn btn-md reset-btn" id="cancel">
                    <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                    取消
                </button>
            </div>
        </div>

    </form>

    <div id="area" style="display: none">
        <div>
            <div>区域<span style="color: #ff9000">0</span>设备列表</div>
            <div class="input-group"><span class="input-group-addon">填报时间</span>
                <input name="beginTime" class="input-medium Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"
                       value="${empty startTime ? today : startTime }" id="startTime" type="text" style="width: 150px">
                <span class="input-group-addon">-</span>
                <input name="endTime" class="input-medium Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', maxDate:'%y-%M-%d %H:%m'})"
                       value="${empty endTime ? now : endTime }" id="endTime" type="text" style="width: 150px">
            </div>
        </div>
        <table class="gridtable">
            <tr>
                <th width="5%"><input class="checkbox" onclick="selection(this)" name="checkbox" type="checkbox" value="1"></th>
                <th width="20%">设备编码</th>
                <th width="30%">设备地址</th>
            </tr>

        </table>

    </div>
</div>


</body>


<script>
    var machineList_ = [];
    var overlays = [];
    $(document).ready(function () {
        $.getScript("${mapApi}", function () {
            $.getScript("${mapLib}/TextIconOverlay/1.2/src/TextIconOverlay_min.js", function () {
                $.getScript("${mapLib}/MarkerClusterer/1.2/src/MarkerClusterer_min.js", function () {
                    $.getScript("${mapLib}/DrawingManager/1.4/src/DrawingManager_min.js", function () {
                            $('#areacontrast_map').html('');
                            var $map = $('#areacontrast_map').rainsoft().map();	//创建地图实例
                            map = $map.instance();
                            var _pointdict = "<%=DictionaryCache.THRESHOLD_MAP.get("AT_LOCATION") %>".split(",");//地图位置
                            if (_pointdict[0] != "null" && _pointdict != undefined) {
                                map.centerAndZoom(new BMap.Point(parseFloat(_pointdict[0]), parseFloat(_pointdict[1])), 15);
                            }
                            var arrDrawingModes = new Array(BMAP_DRAWING_CIRCLE, BMAP_DRAWING_RECTANGLE);//工具只显示圆和多边形
                            //覆盖物数组

                            //画图结束后触发
                            var overlaycomplete = function (e) {
                                var mapData = getMapData(e.overlay);
                                $.ajax(
                                    {
                                        url: $.rainsoft.baseUrl() + 'population/config/heatmap/checkMachine',
                                        type: "post",
                                        data: JSON.stringify(mapData),
                                        dataType: "json",
                                        contentType: "application/json",
                                        success: function (json) {
                                            if (json == null || json.length == 0) {
                                                layer.msg('当前区域没有设备请重新选择', {
                                                    time: 1000 //1s后自动关闭
                                                });
                                                map.removeOverlay(e.overlay)
                                                return;
                                            }
                                            overlays.push(e.overlay);
                                            machineList_.push(json);
                                            confirmMachine();
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
                            function getMapData(overlay) {
                                var ary_mapData = {};
                                var ary_circle_label = new Array();
                                var ary_rectangle_label = new Array();

                                var label_data = {};
                                var p = overlay;
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

                                ary_mapData.ary_circle_label = ary_circle_label;
                                ary_mapData.ary_rectangle_label = ary_rectangle_label;
                                return ary_mapData;
                            };

                            //询问框
                            function confirmMachine() {
                                $("#machineList").html('');
                                $.each(overlays, function (i, n) {
                                    var $areaDiv = $('#area').clone(true);
                                    $areaDiv.children('div').children('div :first').find('span').html(i + 1);
                                    $areaDiv.find('#startTime').attr("name", "startTime" + (i + 1));
                                    $areaDiv.find('#endTime').attr("name", "endTime" + (i + 1));
                                    var $table = $areaDiv.children('table');
                                    $table.html('');
                                    var obj = machineList_[i];
                                    $.each(obj, function (i, e) {
                                        var tr =
                                            '<tr>'
                                            + "<td style='text-align: center'><input id='checkbox' name='checkbox' type='checkbox' checked></td>"
                                            + '<td>' + e.machineId + '</td>'
                                            + '<td>' + e.serviceAddress + '</td>'
                                            + '</tr>'
                                            + '<tr>';
                                        $table.append(tr);
                                    });
                                    $areaDiv.css('display', 'block');
                                    $("#machineList").append($areaDiv);
                                    $("#machineList").next().show();

                                })

                            }


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


                        }
                    )
                })
            })
        });


        $('#subtn').click(function () {
            var $tables = $("#dataForm").find('table');
            var datas = [];
            $.each($tables, function (i, n) {
                var data = {};
                data.areaNum = "area" + (i + 1);
                data.startTime = $("input[name='startTime" + (i + 1) + "']").val();
                data.endTime = $("input[name='endTime" + (i + 1) + "']").val();
                var machineIds = [];
                var $checkeds = $(n).find(':checked');
                $.each($checkeds, function (j, checkbox) {
                    machineIds.push($(checkbox).parent().next().text());
                });
                data.machineIds = machineIds;
                datas.push(data);

            });

            openPage($.rainsoft.baseUrl() + 'population/analyze/areaContast?data=' + encodeURI(JSON.stringify(datas)), "碰撞分析结果");

        })
    });


    function removeMachine(td, i) {
        $(td).parent().parent().remove();
        var alloverlays = map.getOverlays();
        map.removeOverlay(alloverlays[i]);

    }

    function selection(e) {
        var checked = $(".checkbox" + $(e).val());
        for (i = 0; i < checked.length; i++) {
            if (checked[i].checked == true) {
                checked[i].checked = false
            } else {
                checked[i].checked = true
            }
        }
    }
    $('#cancel').click(function () {
       for(var i=0;i<overlays.length;i++){
           map.removeOverlay(overlays[i]);
       }
        machineList_=[];
        $("#machineList").html('');
        $("#btnDiv").hide();

    })
</script>
</body>
</html>