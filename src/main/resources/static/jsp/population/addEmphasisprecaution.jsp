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
            height: 550px;
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
    <link rel="stylesheet" href="${ctx }/css/main.css"/>
    <script type="text/javascript" src="${ctx }/js/layer/layer.js"></script>
    <link rel="stylesheet" href="${mapLib}/DrawingManager/1.4/src/DrawingManager_min.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>添加重点防范管理</title>
</head>
<body>
<div class="row page-component-element">
    <div id="tracelocation_control">
        <div class="col-md-12 col-sm-12" style="padding:15px 0; width: 100%">
            <div class="col-md-3 col-sm-4">
                <div class="input-group">
                    <span class="input-group-addon">重点防范名称</span>
                    <input type="text" class="form-control" placeholder="" maxLength="25" name="name" id="name">
                </div>
            </div>
            <div class="col-md-8 col-sm-8">
                <div class="input-group" id="divThrong">
                    <span class="input-group-addon">选择分析人群类型:</span>
                    <c:forEach items="${throngTypes}" var="throngType">
                        <input type="checkbox" value="${throngType.DICT_CODE}" name="throng"><label>${throngType.DICT_NAME}</label>
                    </c:forEach>
                </div>
            </div>

        </div>

        <div class="col-md-12 col-sm-12" style="padding:3px 0; width: 100%">
            <div class="col-md-2 col-sm-3" >
                <div class="input-group" id="dataType">
                    <span class="input-group-addon">选择分析数据类型:</span>
                    <input type="checkbox" value="1" name="dataType"><label>IMSI</label>
                </div>
            </div>
            <div class="col-md-2 col-sm-3" >
                <div class="input-group">
                    <span class="input-group-addon">归属地</span>
                    <select onChange="showLower(this)" name="firstSelect" id="firstSelect">
                        <option value="">请选择</option>
                        <c:forEach items="${district}" var="district">
                            <c:if test="${district.grade == '0' }">
                                <option value="${district.code}">${district.areaName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>


        </div>

        <div class="col-md-12 col-sm-12" style="padding:3px 0; width: 100%">
            <div class="col-md-2"  style="float:right">
                <div class="input-group">
                    <input type="checkbox" name="status"><label>是否启用</label>
                </div>
            </div>
            <div class="col-md-2"  id="mapShowDiv" style="float:right">
                <span id="mapShowSpan">已选择0台云嗅</span>
                <button type="button" class="btn btn-default btn-md" id="mapShowBut">
                    取消设备选择
                </button>
            </div>
            <div class="col-md-2 col-sm-4" style="float:right">

                <button type="button" class="btn btn-default btn-md" onClick="save()">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                    确定
                </button>
            </div>
        </div>
        <%-- <div class="col-md-12 col-sm-12" style="padding:3px 0; width: 100%">
             <div class="col-md-1 col-sm-2" style="margin-left:15px">
                 <div class="input-group">
                     <input type="checkbox" value="2" name="dataType"><label>手机</label>
                 </div>
             </div>
             <div class="col-md-1 col-sm-2" style="margin-left:5px">
                 <div class="input-group">
                     <span class="input-group-addon">归属地</span>
                     <select onChange="showLower(this)" name="firstSelect">
                         <option value="">请选择</option>
                         <c:forEach items="${district}" var="district">
                             <c:if test="${district.grade == '0' }">
                                 <option value="${district.code}">${district.areaName}</option>
                             </c:if>
                         </c:forEach>
                     </select>
                 </div>
             </div>
         </div>
         <div class="col-md-12 col-sm-12" style="padding:3px 0px; width: 100%">
             <div class="col-md-1 col-sm-2" style="margin-left:15px">
                 <div class="input-group">
                     <input type="checkbox" value="3" name="dataType"><label>身份证</label>
                 </div>
             </div>
             <div class="col-md-2 col-sm-3" style="margin-left:15px">
                 <div class="input-group" style="right:10px">
                     <span class="input-group-addon">归属地</span>
                     <select onChange="showLower(this)" name="firstSelect">
                         <option value="">请选择</option>
                         <c:forEach items="${district}" var="district">
                             <c:if test="${district.grade == '0' }">
                                 <option value="${district.code}">${district.areaName}</option>
                             </c:if>
                         </c:forEach>
                     </select>
                   &lt;%&ndash;  <span class="input-group-addon">民族</span>
                     <select name="idCard">
                         <c:forEach items="${nation}" var="nation">
                             <option value="${nation.DICT_CODE}">${nation.DICT_NAME}</option>
                         </c:forEach>
                     </select>&ndash;%&gt;
                 </div>
             </div>
         </div>
         <div class="col-md-12 col-sm-12" style="padding:3px 0; width: 100%">
             <div class="col-md-7 col-sm-7" style="margin-left:15px">
                 <div class="input-group">
                     <input type="checkbox" value="4" name="dataType"><label>Mac</label>
                 </div>
             </div>

         </div>--%>
    </div>
</div>
<div class="map" id="areacontrast_map"></div>
<!--       <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#viewModal">
Launch demo modal
</button> -->
<!--  <div class="modal fade" id="viewModal" style="display: none; "  tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
           <div class="modal-dialog modal-lg" role="document">
<div class="modal-content"  id="viewModalNum" style="position:absolute;left:50%;margin-left:-50px;top:70%;margin-top:480px;z-index:999;">
    <div class="modal-header">
     <div id="close">
     <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
     </div>
     <h4 class="modal-title" id="myModalLabel">提示</h4>
  </div>
   <div class="modal-body" id = "dataListModal">
        <p>当前你选取了<span style="color:#68c8a0">12</span>台云嗅设备</p>
   <div class="modal-footer" id="cloceviewModalNumSubmit">
   <button type="button"  class="btn btn-default  cloce"   data-dismiss="modal">确定</button>
   <button type="button"  class="btn   cloce"  style="color:#ddd;background:#fff" data-dismiss="modal">取消</button>
 </div>
</div>
</div>
</div>
</div> -->
<form id="form_query">
</form>
<%--
<script type="text/javascript"
        src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
<script type="text/javascript"
        src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
<!--加载检索信息窗口-->
<script type="text/javascript"
        src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
--%>

<script>
    var emphasisConfig = {};
    var configIdsss;
    $(document).ready(function () {
        $.getScript("${mapApi}", function () {
            $.getScript("${mapLib}/TextIconOverlay/1.2/src/TextIconOverlay_min.js", function () {
                $.getScript("${mapLib}/MarkerClusterer/1.2/src/MarkerClusterer_min.js", function () {
                    $.getScript("${mapLib}/DrawingManager/1.4/src/DrawingManager_min.js", function () {


                        $('#areacontrast_map').html('');
                        var $map = $('#areacontrast_map').rainsoft().map();	//创建地图实例
                        map = $map.instance();
                        var  _pointdict = "<%=DictionaryCache.THRESHOLD_MAP.get("AT_LOCATION") %>".split(",");//地图位置
                        if (_pointdict[0]!="null"&&_pointdict!=undefined){
                            map.centerAndZoom(new BMap.Point(parseFloat(_pointdict[0]), parseFloat(_pointdict[1])), 15);
                        }
                        var arrDrawingModes = new Array(BMAP_DRAWING_CIRCLE, BMAP_DRAWING_RECTANGLE);//工具只显示圆和多边形
                        var maxNumber = 0;//其实是0+1个

                        //地图加载完成后获取当前区域

                        map.addEventListener("tilesloaded", function () {
                            getArea();
                            getEquipmentAll(_area);
                        });
                        //拖拽地图完毕后监听事件
                        map.addEventListener("dragend", function (evt) {
                            deletePoint();
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
                        var overlays = [];
                        var targets = [];
                        var overlaycomplete = function (e) {
                            if (overlays.length >= maxNumber) {
                                drawingToolRemove(new Array());
                            }
                            ;
                            overlays.push(e.overlay);
                            targets.push(e.target);
                            var mapData = getMapData();
                            setTimeout(function () {
                                $.ajax(
                                    {
                                        url: $.rainsoft.baseUrl() + 'heatingdistribution/checkMachine',
                                        type: "post",
                                        data: JSON.stringify(mapData),
                                        dataType: "json",
                                        contentType: "application/json",
                                        success: function (json) {
                                            confirm(json);

                                        }
                                    }
                                );
                            }, 1000)
                        };

                        var styleOptions = {
                            strokeColor: "red",    //边线颜色。
                            fillColor: "red",      //填充颜色。当参数为空时，圆形将没有填充效果。
                            strokeWeight: 3,       //边线的宽度，以像素为单位。
                            strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
                            fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
                            strokeStyle: 'solid' //边线的样式，solid或dashed。
                        }
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

                        //清除所有覆盖物
                        function clearAll() {
                            for (var i = 0; i < overlays.length; i++) {
                                map.removeOverlay(overlays[i]);
                            }
                            overlays.length = 0
                        }

                        //删除所有点
                        function deletePoint() {
                            var allOverlay = map.getOverlays();
                            for (var i = 0; i < allOverlay.length - 1; i++) {
                                if (allOverlay[i] instanceof BMap.Marker) {
                                    map.removeOverlay(allOverlay[i]);
                                }
                            }
                        }

                        //获得当前可视范围所有设备经纬度信息标注
                        function getEquipmentAll(area) {
                            $.ajax(
                                {
                                    url: $.rainsoft.baseUrl() + 'companion/getEquipmentAll',
                                    type: "post",
                                    data: JSON.stringify(area),
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
                        function confirm(machineList) {
                            layer.confirm('当前你选取了' + machineList.length + '台云嗅设备', {
                                btn: ['确定', '取消'] //按钮
                            }, function () {
                                if (machineList.length == 0) {
                                    showEnableDrawingTool(true, arrDrawingModes);
                                    clearAll();
                                    layer.msg('请重新选择', {
                                        time: 4000 //20s后自动关闭

                                    });
                                } else {
                                    layer.msg('已选择' + machineList.length + '台云嗅设备', {icon: 1});
                                    $("#mapShowSpan").html("(已选择" + machineList.length + "台云嗅设备)");
                                    emphasisConfig.mapData = getMapData();
                                    emphasisConfig.machineInfos = machineList;
                                }

                            }, function () {
                                showEnableDrawingTool(true, arrDrawingModes);
                                clearAll();
                                layer.msg('请重新选择', {
                                    time: 4000 //20s后自动关闭
                                });
                            });
                        }

                        $("#mapShowBut").on('click', function () {
                            $("#mapShowSpan").html("(已选择0台云嗅)");
                            showEnableDrawingTool(true, arrDrawingModes);
                            emphasisConfig.machineInfos=null;
                            clearAll();
                        });
                        //更新回显
                         configIdsss = eval(${config});
                        if (configIdsss != null && configIdsss!=undefined) {
                            $('#name').val(configIdsss.name);
                            $.each(configIdsss.configId, function (n, e) {
                                $('#dataType').find('input[value=' + e.idType + ']').attr("checked", true);
                            });
                            var type = configIdsss.type.split(",");
                            $.each(type, function (i, e) {
                                $("#divThrong").find("input[value=" + e + "]").attr("checked", true);
                            });
                            var configIds = configIdsss.configId;
                            var province = configIds[0].value + "";
                            var provinceStr = province.substring(0, 2) + "0000";
                            $("#firstSelect").find("option[value=" + provinceStr + "]").attr("selected", true);
                            showLower(document.getElementById("firstSelect"));
                            if(configIdsss.mapType==1){
                                var mapInfo=eval(configIdsss.mapInfo);
                                var rectangle = new BMap.Polygon([
                                    new BMap.Point(mapInfo[0].sw_lng,  mapInfo[0].sw_lat),
                                    new BMap.Point(mapInfo[0].ne_lng,  mapInfo[0].sw_lat),
                                    new BMap.Point(mapInfo[0].ne_lng, mapInfo[0].ne_lat),
                                    new BMap.Point(mapInfo[0].sw_lng, mapInfo[0].ne_lat)
                                ], {strokeColor: "red", strokeWeight: 2,fillColor:"red"});  //创建矩形
                                map.addOverlay(rectangle);
                                overlays.push(rectangle)//增加矩形
                            }
                            if(configIdsss.mapType==2){
                                var mapInfo1=eval(configIdsss.mapInfo);
                                var circle = new BMap.Circle(new BMap.Point(mapInfo1[0].lng,  mapInfo1[0].lat),mapInfo1[0].radius*1000,{strokeColor:"red", strokeWeight:2,fillColor:"red"}); //创建圆
                                map.addOverlay(circle);            //增加圆
                                overlays.push(circle)
                            }
                            //禁止画图
                            drawingToolRemove(new Array());
                            $("#mapShowSpan").html("(已选择" + configIdsss.deviceNum + "台云嗅)");
                            emphasisConfig.machineInfos=configIdsss.machineInfos;
                            emphasisConfig.mapData = getMapData();
                            if(configIdsss.status==1){
                                $("input[name='status']").attr("checked",true);
                            }
                        }
                    })
                })
            })
        });

    });


    //二级连城区
    function showLower(obj) {
        var lowerOption = "";
        var array = new Array();
        <c:forEach items="${district}" var="d">
        if ("${d.grade == '1'}" == "true" && ( "${d.code}".substring(0, 2) == $(obj).val().substring(0, 2) )) {
            lowerOption = lowerOption + "<option value='${d.code}'>${d.areaName}</option>";
        }
        </c:forEach>
        $(obj).siblings("span[name='newSpan']").remove();
        $(obj).siblings("select[name='newSelect']").remove();
        if (lowerOption != "") {
            $(obj).after("<span class='input-group-addon' name='newSpan'></span><select name='newSelect'>" + lowerOption + "</select>");
        }
    }

    function save() {
        if (checking()) {
            var loady = null;
            loady = layer.load(1, {shade: [0.3, '#000']});

            if (configIdsss != null && configIdsss!=undefined) {
                emphasisConfig.isEdit=1;
                emphasisConfig.id =configIdsss.id;
                $.ajax(
                    {
                        url: $.rainsoft.baseUrl() + 'emphasisprecaution/addEmphasisConfig',
                        type: "post",
                        data: JSON.stringify(emphasisConfig),
                        dataType: "json",
                        contentType: "application/json",
                        success: function (json) {
                            if (json.code == 1) {
                                layer.close(loady);//数据传返回加载图片关闭
                                alert("修改成功！");
                                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                                parent.layer.close(index);
                            } else {
                                alert("修改失败!");
                            }
                        }
                    }
                );
            }else {
                $.ajax(
                    {
                        url: $.rainsoft.baseUrl() + 'emphasisprecaution/addEmphasisConfig',
                        type: "post",
                        data: JSON.stringify(emphasisConfig),
                        dataType: "json",
                        contentType: "application/json",
                        success: function (json) {
                            if (json.code == 1) {
                                layer.close(loady);//数据传返回加载图片关闭
                                alert("添加成功！");
                                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                                parent.layer.close(index);
                                //window.location = $.rainsoft.baseUrl() + 'populationmovement/emphasisprecaution';
                            } else {
                                alert("添加失败!");
                            }
                        }
                    }
                );
            }




        }
    }
    ;

    function checking() {
        var inputName = $("#name").val().trim();
        var cheskedObj = $("#divThrong input[name='throng']:checked");
        if (inputName == '') {
            alert("请输入重点防范名称！");
            return false;
        } else if ($(cheskedObj).length == 0) {
            alert("请选择人群类型！");
            return false;
        } else if (emphasisConfig.machineInfos == undefined||emphasisConfig.machineInfos==null) {
            alert("请选择设备！");
            return false;
        }

        emphasisConfig.name = inputName;
        var types = "";
        $.each(cheskedObj, function (index, obj) {
            if (index == 0) {
                types += $(obj).val();
            } else {
                types += "," + $(obj).val();
            }

        })
        emphasisConfig.type = types;
        emphasisConfig.deviceNum = emphasisConfig.machineInfos.length;
        if ($("input[name='status']:checked").length > 0) {
            emphasisConfig.status = 1;
        } else {
            emphasisConfig.status = 0;
        }

        var dataTypes = $("input[name='dataType']:checked");
        var blo = false;
        var configIdAry = [];
        $.each(dataTypes, function (index, obj) {
            var configId = {};
            configId.idType = $(obj).val();
            var $select = $(obj).parent().parent().siblings().children().children("select[name='firstSelect']");
            var $newSelect = $(obj).parent().parent().siblings().children().children("select[name='newSelect']");
            var $idCard = $(obj).parent().parent().siblings().children().children("select[name='idCard']");
            if ($select.length > 0 && $select.val() == "") {
                alert("请选择" + $(obj).siblings("label").html() + "归属地！");
                blo = true;
            } else {
                if ($newSelect.length > 0 && $newSelect.val() != "") {
                    configId.value = $newSelect.val();
                } else {
                    configId.value = $select.val();
                }
                configId.valueType = 1;
                configIdAry.push(configId);
                if (configId.idType == "idcard") {
                    var nation = {};
                    nation.idType = configId.idType;
                    nation.value = $idCard.val();
                    nation.valueType = 2;
                    configIdAry.push(nation);
                }
            }
            if (blo) {
                return false;
            }
            emphasisConfig.configId = configIdAry;
        });
        if (blo) {
            return false;
        }
        return true;
    }
    ;


</script>
</body>
</html>