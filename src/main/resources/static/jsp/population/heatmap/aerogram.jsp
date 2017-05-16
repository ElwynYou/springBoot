<%@ page import="com.rainsoft.cache.DictionaryCache" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style type="text/css">
        li {
            list-style: none
        }

        .map {
            height: 620px;
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

        .time_segmentation {
            width: 80px;
            padding: 0;
            float: left;
            position: relative
        }

        .time_segmentation .time {
            list-style: none;
            width: 80px;
            left: -10px;
            position: relative
        }

        .btn-move {
            background: #E6E6E6;
            color: #aa896a;
            padding: 5px 9px
        }

        .spinner {
            width: 200px
        }

        .spinner input {
            width: 40px;
            text-align: center
        }

        .time_segmentationT {
            margin-left: 40px
        }

        .time_segmentation .line {
            width: 80px;
            height: 25px;
            border-left: 1px solid #DACBBB;
            border-bottom: 1px solid #DACBBB;
            list-style: none;
        }

        #timeline {
            width: 1025px;
            overflow: hidden;
            position: relative;
            left: 30px;
            background: url('js/timelinr/img/dot.gif') left 45px repeat-x;
        }

        #dates {
            width: 1000px;
            height: 60px;
            overflow: hidden;
        }

        #dates li {
            list-style: none;
            float: left;
            width: 100px;
            height: 50px;
            font-size: 24px;
            text-align: center;
            background: url('js/timelinr/img/biggerdot.png') center bottom no-repeat;
        }

        #dates a {
            line-height: 38px;
            padding-bottom: 10px;
        }

        #dates .selected {
            font-size: 38px;
        }

        #tooltip {
            position: absolute;
            left: 0;
            top: 0;
            background: rgba(0, 0, 0, .8);
            color: #fff;
            font-size: 16px;
            padding: 1px;
            line-height: 18px;
            display: none;
        }

    </style>
    <%@ include file="/jsp/frameSource.jsp" %>
    <link rel="stylesheet" href="${ctx }/js/layer/skin/layer.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx }/js/timelinr/css/main.css"/>

    <script type="text/javascript" src="${ctx }/js/layer/layer.js"></script>
    <script type="text/javascript" src="${ctx }/js/spinner.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
    <script type="text/javascript" src="${ctx }/js/timelinr/js/jquery.timelinr-0.9.53.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>人群热力图</title>
</head>
<body style="background: none">
<div class="row page-component-element">
    <div id="tracelocation_control">
        <div class="col-md-12 col-sm-12" style="padding:15px 0; width: 100%">
            <form class="form-horizontal" id="queryForm">
                <div class="col-md-4 col-sm-4">
                    <div class="input-group input-medium  input-daterange"
                         data-date-format="yyyy-mm-dd">
                        <span class="input-group-addon">活动时间段</span>
                        <input name="start"
                               class="form-control datetime-picker"
                               id="startTime" type="text" readonly> <span
                            class="input-group-addon">到</span>
                        <input name="end"
                               class="form-control datetime-picker"
                               id="endTime" type="text" readonly>
                    </div>
                    <input type="hidden" name="id" value="${id}"/>
                </div>
                <div class="col-md-1 col-sm-2" style="padding-left:0px">
                    <div class="input-group">
                        <span class="input-group-addon">播放速度</span>
                        <input type="text" class="spinner"/>
                    </div>
                </div>
                <div class="col-md-2 col-sm-1" style="margin-left:40px">
                    <div class="input-group">
                        <button type="button" class="btn btn-default btn-md" id="queryBut">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                            查询
                        </button>
                        <button type="button" class="btn reset-btn btn-md" onClick="reSetValue()">
                            <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                            重置
                        </button>
                    </div>
                </div>
                <div class="col-md-1 col-sm-1">
                    <div class="input-group">
                        <button type="button" class="btn btn-move btn-md" onClick="returnHot()">
                            模拟人群移动图
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div id="timeline" style="display:none">
    <ul id="dates">
    </ul>
</div>
<div id="map-wrapper">
    <div class="col-md-12 col-sm-12">
        <div class="map" id="areacontrast_map"></div>
    </div>
    <div id="tooltip"></div>
</div>


<script>
    var mapData = [];
    var heatmapOverlay;
    var tagNum = 0;
    var map;
    $(document).ready(function () {
        editTimeinput();
        if (!isSupportCanvas()) {
            alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~')
            return;
        }
        $.getScript("${mapApi}", function () {
            $.getScript("${mapLib}/Heatmap/2.0/src/Heatmap_min.js", function () {
                init();
            });
        });
        function init() {
            $('#areacontrast_map').html('');
            map = new BMap.Map("areacontrast_map");          // 创建地图实例
            var point = new BMap.Point(116.404, 39.915);
            map.centerAndZoom(point, 15);
            var _pointdict = "<%=DictionaryCache.THRESHOLD_MAP.get("AT_LOCATION") %>".split(",");//地图位置
            if (_pointdict[0] != "null" && _pointdict != undefined) {
                map.centerAndZoom(new BMap.Point(parseFloat(_pointdict[0]), parseFloat(_pointdict[1])), 15);
            }// 初始化地图，设置中心点坐标和地图级别
            map.enableScrollWheelZoom(); // 允许滚轮缩放
            heatmapOverlay = new BMapLib.HeatmapOverlay({"radius": 20});
            map.addOverlay(heatmapOverlay);
            $("#queryBut").on('click', function () {
                heatmapOverlay.toggle();
                var stringTime1 = $("#startTime").val();
                var stringTime2 = $("#endTime").val();
                if (stringTime1 == "" || stringTime2 == "") {
                    alert("请选择日期!");
                    return false;
                }
                var timestamp1 = Date.parse(new Date(stringTime1));
                var timestamp2 = Date.parse(new Date(stringTime2));
                timestamp2 = timestamp2 / 1000 - timestamp1 / 1000;
                tagNum = parseInt(timestamp2 / 60 / 5);//每五分钟总时间段数
// 			  if( (timestamp2/60) < 60){//所选日期必须大于一个小时
// 				  alert("选择日期必须大于一个小时!");
// 				  return false;
// 			  }
                var startTag = stringTime1.substring(11);//第一个间隔显示的时间
                var startTageAry = startTag.split(":");
                var tagAry = [];
                for (var i = 0; i < tagNum + 1; i++) {
                    var hours = parseInt(startTageAry[0]);
                    var minute = parseInt(startTageAry[1]) + (5 * i);
                    if (i == 0) {
                        tagAry.push(startTag);
                    } else {

                        if (minute >= 60) {
                            hours = parseInt(hours + minute / 60);
                            minute = minute % 60;
                        }
                        if (hours.toString().length != 2) {
                            hours = "0" + hours;
                        }
                        if (minute.toString().length != 2) {
                            minute = "0" + minute;
                        }
                        tagAry.push(hours + ":" + minute);
                    }
                }
                $("#dates").children().remove();
                $("#timeline").hide();
                var loady = null;
                loady = layer.load(1, {shade: [0.3, '#000']});
                $("#queryForm").ajaxSubmit({
                    type: "post",
                    url: "<c:url value='/population/heatmap/heatmap'/>",
                    dataType: "json",
                    success: function (result) {
                        layer.close(loady);//数据传返回加载图片关闭
                        if (result.code == 1) {
                            if (result.result.length == 0) {
                                layer.msg("该查询时间段内没有数据！", {icon: 0, time: 2000});
                            } else {
                                //创建时间间隔
                                if (tagAry.length > 0) {
                                    $("#timeline").show();
                                }
                                $.each(tagAry, function (index, obj) {
                                    $("#dates").append('<li onClick="ulAdclass(this)" id="tag' + index + '"><a href="#' + index + '">' + obj + '</a></li>');
                                    //$("#time_segmentation").append('<ul id="tag'+index+'" class="time_segmentation" onClick="ulAdclass(this)"><li class="time">'+obj+'</li><li class="line"></li><li></li></ul>');
                                });
                                // $("#time_segmentation").children().last().find("li:eq(1)").css("border-bottom","none");

                                //更新地图热点
                                mapData = [];
                                $.each(tagAry, function (index, obj) {
                                    var mapAry = [];
                                    $.each(result.result, function (index, time) {
                                        if (obj == time.statTime.substring(11)) {
                                            mapAry.push({"lng": time.longitude, "lat": time.latitude, "count": time.peopleNum});
                                        }
                                    });
                                    mapData.push(mapAry);
                                });
                                ulAdclass("#tag0");
                                heatmapOverlay.toggle();
                                $().timelinr();
                            }
                        } else {
                            layer.msg("数据加载错误！", {icon: 0, time: 2000});
                        }
                    }
                });

            });

            /* tooltip code start */
            var demoWrapper = document.querySelector('#map-wrapper');
            var tooltip = document.querySelector('#tooltip');

            function updateTooltip(x, y, value) {
                // + 15 for distance to cursor
                var transl = 'translate(' + (x + 10 ) + 'px,' + (y + 120) + 'px)';
                tooltip.style.webkitTransform = transl;
                if (value <= 0) {
                    tooltip.style.display = 'none';
                } else {
                    tooltip.innerHTML = "总人数：" + value;
                }

            }

            demoWrapper.onmousemove = function (ev) {
                var x = ev.layerX;
                var y = ev.layerY;
                // getValueAt gives us the value for a point p(x/y)
                var value = heatmapOverlay.heatmap.getValueAt({
                    x: x,
                    y: y
                });
                tooltip.style.display = 'inline';
                updateTooltip(x, y, value);
            };
// hide tooltip on mouseout
            demoWrapper.onmouseout = function () {
                tooltip.style.display = 'none';
            };
            /* tooltip code end */

        }


        /*});  */
        //判断浏览区是否支持canvas
        function isSupportCanvas() {
            var elem = document.createElement('canvas');
            return !!(elem.getContext && elem.getContext('2d'));
        }
    });

    function editTimeinput() {
        //初始化日期控件
        var myDate = new Date();
        var year = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
        var month = myDate.getMonth() + 1;       //获取当前月份(0-11,0代表1月)
        var data = myDate.getDate();        //获取当前日(1-31)
        var hours = myDate.getHours();       //获取当前小时数(0-23)
        var minutes = myDate.getMinutes();     //获取当前分钟数(0-59)

        while ((minutes % 5) != 0)//当前默认分钟只能是五的倍数
        {
            minutes = minutes - 1;
        }
        if (minutes.toString().length != 2) {
            minutes = "0" + minutes;
        }
        var end = year + "-" + month + "-" + data + " " + hours + ":" + minutes;
        if (hours - 1 >= 0) {
            hours = hours - 1;
            if (hours.toString().length != 2) {
                hours = "0" + hours;
            }

        } else {
            minutes = '00';
            hours = '00';
        }
        var start = year + "-" + month + "-" + data + " " + hours + ":" + minutes;
        var timeFormat = "yyyy-MM-dd HH:mm";// + minutes;
        $.date97_3({"dateFmt": timeFormat, "startVal": start, "endVal": end});
    }
    var spinner = $('.spinner').spinner();

    function openHeatmap(points, heatmapOverlay) {
        heatmapOverlay.setDataSet({data: points, max: 100});
        if (points.length > 0) {
            map.panTo(new BMap.Point(points[0].lng, points[0].lat));
        }
    }

    var timer;
    function ulAdclass(obj) {
        clearInterval(timer);
        var id = $(obj).attr("id");
        id = id.substring(3);
// 		if(id == 0){
// 			$(".addTimeLogo").animate({left:'-8px'},'fast');
// 		}
        openHeatmap(mapData[id], heatmapOverlay);
        //setTimeLogo(obj);

    }

    function setTimeLogo(obj) {
        $(obj).addClass("addTimeColor").siblings().removeClass("addTimeColor");
        $(obj).find("li:eq(2)").addClass("addTimeLogo");
        $(obj).siblings().children().removeClass("addTimeLogo");
    }

    function reSetValue() {
        spinner.val("100倍");
        editTimeinput();
    }


    function returnHot() {

        if ($("#dates").children().length == 0) {
            layer.msg("该查询时间段内没有数据！", {icon: 0, time: 2000});
            return false;
        }
        var multiple = spinner.val();
        multiple = multiple.substring(0, multiple.length - 1);
        var ms = 5 * 60000 / multiple;

        $().timelinr({
            autoPlay: 'true',
            autoPlayDirection: 'forward',
            startAt: 1,
            autoPlayPause: ms
        });

    }

</script>
</body>
</html>