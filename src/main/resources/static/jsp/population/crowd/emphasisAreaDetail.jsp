<%--suppress JSUnresolvedVariable --%>
<%--suppress JSUnresolvedVariable --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <title></title>
    <%@ include file="/jsp/frameSource.jsp" %>
    <script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
    <script type="text/javascript" src="${ctx }/js/echarts/echarts.min.js"></script>
</head>
<style>
    /********顶部自动刷新与历史记录部分开始*******/

    .content {
        padding: 10px;
        overflow: hidden;
    }

    .topdiv {
        width: 100%;
        height: 30px;
        line-height: 30px;
        text-align: center;
    }

    .refresh {
        float: left;
        font-size: 16px;
    }

    .history {
        float: right;
        width: 108px;
        height: 30px;
        background: rgba(153, 153, 153, 1);
        color: #fff;
        border-radius: 6px;
    }

    .history:hover {
        cursor: pointer;
    }

    #freq {
        width: 120px;
        height: 22px;
        font-size: 13px;
    }

    .updatetime {
        font-size: 16px;
    }

    /********顶部自动刷新与历史记录部分结束*******/
    /********热力图开始*******/

    .hotarea {
        width: 100%;
        overflow: hidden;
    }

    .chart {
        width: 100%;
        overflow: hidden;
    }

    .chart_top,
    .chart_bottom {
        position: relative;
        position: relative;
        padding: 10px 0;
        height: 260px;
    }

    .chart_top {
        width: 44%;
        float: left;
    }

    .chart_bottom {
        width: 55%;
        float: right;
        overflow-y: auto;
        border: 1px solid #666;
    }

    .chart_bottom li {
        line-height: 30px;
        padding-left: 10px;
        font-weight: bold;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;

    }

    .chart_bottom li:hover {
        background: #ccc;
        cursor: pointer;
    }

    .hotarea {
        margin-top: 10px;
    }

    .dubious {
        color: #ED8E1A;
    }

    .crowd_type {
        position: absolute;
        width: 48%;
        height: 120px;
        color: #fff;
        font-size: 18px;
        line-height: 110px;
        text-align: center;
        cursor: pointer;
    }

    .crowd_type span {
        font-size: 22px;
    }

    .crow_total {
        left: 0px;
        top: 0px;
        background: rgb(69, 137, 148);
    }

    .crow_focus {
        background: rgb(117, 121, 71);
        right: 0px;
        top: 0px;
    }

    .crow_highrisk {
        background: rgb(114, 83, 52);
        bottom: 0px;
        left: 0px;
    }

    .crow_suspected {
        background: rgb(148, 41, 35);
        bottom: 0px;
        right: 0px;
    }

    .crowd_list {
        position: absolute;
        width: 173px;
        height: 40px;
        padding: 12px 0;
        text-align: center;
        color: #fff;
        font-size: 18px;
        font-weight: bold;
        right: -10px;
        box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.2);
        -webkit-box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.2);
    }

    .crowd_list:first-child {
        top: 20px;
        background: #EF5AA1;
    }

    .crowd_list:nth-child(2) {
        top: 104px;
        background: #FF9900;
    }

    .crowd_list:last-child {
        top: 192px;
        background: #996633;
    }

    .trend {
        width: 100%;
        overflow: hidden;
    }

    /********热力图结束*******/
    /********趋势图开始*******/

    .trend_top,
    .trend_bottom {
        width: 100%;
        overflow: hidden;
        margin-bottom: 20px;
    }

    p,
    .con {
        width: 100%;
        height: 30px;
        line-height: 30px;
        font-size: 18px;
        font-weight:bold;
    }

    .trend_bottom p {
        color: #4BA6FA;
    }

    .peonum {
        float: left;
        width: 120px;
        height: 26px;
        color: #4BA6FA;
        margin-top: 2px;
        line-height: 26px;
        border-radius: 2px;
    }

    .time {
        position: relative;
        float: right;
        width: 70%;
        text-align: right;
    }

    input[type="checkbox"] {
        position: relative;
        width: 14px;
        height: 14px;
        top: 3px;
    }

    #trend_img,
    #highestrisk,
    #focus {
        width: 100%;
        height: 400px;
    }

    .highestrisk,
    .focus {
        width: 49%;
        height: 500px;

    }

    .highestrisk {
        float: left;
    }

    .focus {
        float: right;
    }

    /********趋势图结束*******/
    #pageContainer .btn-default {
        text-shadow: 0 1px 0 #fff;
        background-image: -webkit-linear-gradient(top, #fff 0, #e0e0e0 100%);
        background-image: -o-linear-gradient(top, #fff 0, #e0e0e0 100%);
        background-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#e0e0e0));
        background-image: linear-gradient(to bottom, #fff 0, #e0e0e0 100%);
        filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff', endColorstr='#ffe0e0e0', GradientType=0);
        filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
        background-repeat: repeat-x;
        border-color: #dbdbdb;
        border-color: #ccc;
        color: #aa896a;
        background-color: #fff;
        border-color: #ccc;
        border: 1px solid #ccc;
        border-radius: 2px;
    }

    #pageContainer .btn-default:hover {
        background: #ddd;
    }

    .time label {
        font-weight: normal
    }
</style>

<body>
<div class="content">
    <!-----------------顶部自动刷新与历史记录部分开始----------------->
    <div class="topdiv">
        <div class="refresh">
            自动刷新频率:
            <select name="" id="freq">
                <option name=0 value="none">不自动刷新</option>
                <option name=1 value="1">1分钟</option>
                <option name=2 value="2">2分钟</option>
                <option name=3 value="5">5分钟</option>
                <option name=4 value="10">10分钟</option>
            </select>
            <span id="updatetime">正在加载数据...</span>
        </div>
        <div class="history btn btn-default" id="history" onclick=" openPage('${ctx}/population/crowd/emphasisAreaHistory?&configId=${serviceCode}', '历史查询');">
            <i class="glyphicon glyphicon-signal"></i>
            历史统计
        </div>
    </div>
    <!-----------------顶部自动刷新历史记录部分结束----------------->
    <!-----------------热力图开始----------------->
    <div class="hotarea">

        <div class="chart">
            <div class="chart_top">
                <div class="crowd_type crow_total">总人数 <span id="totalPerson">0</span> 人</div>
                <div class="crowd_type crow_focus">关注人群 <span id="dangerPerson">0</span> 人</div>
                <div class="crowd_type crow_highrisk">高危地区 <span id="dangerArea">0</span> 人</div>
                <div class="crowd_type crow_suspected">可疑人群 <span id="suspiciousPeople">0</span> 人</div>
            </div>
            <ul class="chart_bottom" id="chart_bottom">
                <li id="suspli" style="display: none">暂无可疑人群</li>
            </ul>
        </div>
    </div>
    <!-----------------热力图结束----------------->
    <!-----------------趋势图开始----------------->
    <div class="trend">
        <div class="trend_top">
            <div class="con">
                <div class="peonum">总人数趋势图</div>
                <div class="time">
                    对比：&nbsp;<input type="checkbox" id="prevDay"/> <label for="prevDay">前一日</label> &nbsp;&nbsp;
                    <input type="checkbox" id="prevWeek"/> <label for="prevWeek">上周同期</label> &nbsp;&nbsp;
                    <input type="checkbox" id="prevYear"/> <label for="prevYear">去年同期</label>
                </div>
            </div>
            <div id="trend_img"></div>
        </div>
        <div class="trend_bottom">
            <div class="highestrisk">
                <p>高危地区人群趋势图</p>
                <div id="highestrisk"></div>
            </div>
            <div class="focus">
                <p>关注人群趋势图</p>
                <div id="focus"></div>
            </div>
        </div>
    </div>
    <!-----------------趋势图结束----------------->
</div>
</body>
<script>
    var echartTotal=echarts.init(document.getElementById("trend_img"));
    var echartDangerArea=echarts.init(document.getElementById("highestrisk"));
    var echartDangerPerson= echarts.init(document.getElementById("focus"));

    //////总趋势图
    function totalTrend(totalPerson, preDay, preWeek, preYear, refreshTime) {
        if (totalPerson instanceof Array) {
            totalPerson = getArray(totalPerson);
            preDay = getArray(preDay);
            preWeek = getArray(preWeek);
            preYear = getArray(preYear);
        } else {
            var time = refreshTime.substring(11, 13);//小时数对应下标
            time = parseInt(time);
            var series = echartTotal.getOption().series;
            $.each(series, function (i, n) {
                var totalArry = n.data;
                if (i == 0) {
                    totalArry[time] = parseInt(totalPerson);
                    totalPerson = totalArry
                } else if (i == 1) {
                    totalArry[time] = parseInt(preDay);
                    preDay = totalArry
                } else if (i == 2) {
                    totalArry[time] = parseInt(preWeek);
                    preWeek = totalArry
                } else if (i == 3) {
                    preWeek = totalArry;
                    totalArry[time] = parseInt(preYear);
                }
            });
        }
        var a = [{
            name: getTime(0) + "总人数",
            data: totalPerson /////当前日总人数

        }];
        trend("trend_img", a);
        $("input[type='checkbox']").click(function () {
            var $that = $(this);
            var id = $that.attr("id");
            if ($that.prop("checked")) { ///表示已选中
                if (a.length > 1) {
                    a.pop();
                }
                $("input[type='checkbox']").prop("checked", false);
                $that.prop("checked", true);
                if (id == "prevDay") { ////前一天
                    a.push({
                        name: getTime(1) + "总人数",
                        data: preDay /////前一日总人数
                    })
                } else if (id == "prevWeek") { ////前一周
                    a.push({
                        name: getTime(2) + "总人数",
                        data: preWeek /////前一周总人数
                    })
                } else if (id == "prevYear") { ////前一年
                    a.push({
                        name: getTime(3) + "总人数",
                        data: preYear /////前一年总人数
                    })
                }
                trend("trend_img", a);
            } else { ////表示取消选中
                a.pop();
                trend("trend_img", a);
            }
        });
    }
    //////高危地区趋势图
    function dangerArea(data, init, refreshTime) {
        if (init != null) {
            var map = {},
                dest = [];
            for (var i = 0; i < data.length; i++) {
                var ai = data[i];
                if (!map[ai.areaCode]) {
                    dest.push({
                        id: ai.areaCode,
                        name: ai.dangerAreaName,
                        data: [{count: ai.peopleNum, hour: ai.statTime.substring(11, 13)}]

                    });
                    map[ai.areaCode] = ai;
                } else {
                    for (var j = 0; j < dest.length; j++) {
                        var dj = dest[j];
                        if (dj.id == ai.areaCode) {
                            dj.data.push({count: ai.peopleNum, hour: ai.statTime.substring(11, 13)});
                            break;
                        }
                    }
                }
            }

            $.each(dest, function (index, ele) {
                ele.data = getArray(ele.data);
            });
            trend("highestrisk", dest, "高危地区人群趋势图");
        } else {
            var a = [];
            var series = echartDangerArea.getOption().series;
            var time = refreshTime.substring(11, 13);//小时数对应下标
            time = parseInt(time);
            $.each(series, function (i, n) {
                var obj = {};
                var arry = n.data;
                arry[time] = parseInt(data[i].peopleNum);
                obj.name = n.name;
                obj.data = arry;
                a.push(obj)
            });
            trend("highestrisk", a, "高危地区人群趋势图");
        }
    }
    /////关注人群趋势图
    function focusPeo(data, init, refreshTime) {
        if (init != null) {
            var map = {},
                dest = [];
            for (var i = 0; i < data.length; i++) {
                var ai = data[i];
                if (!map[ai.attentionType]) {
                    dest.push({
                        id: ai.attentionType,
                        name: ai.dangerPersonGroupName,
                        data: [{count: ai.peopleNum, hour: ai.statTime.substring(11, 13)}]
                    });
                    map[ai.attentionType] = ai;
                } else {
                    for (var j = 0; j < dest.length; j++) {
                        var dj = dest[j];
                        if (dj.id == ai.attentionType) {
                            dj.data.push({count: ai.peopleNum, hour: ai.statTime.substring(11, 13)});
                            break;
                        }
                    }
                }
            }
            $.each(dest, function (index, ele) {
                ele.data = getArray(ele.data);
            });
            trend("focus", dest, "关注人群趋势图");
        } else {
            var a = [];
            var series = echartDangerPerson.getOption().series;
            var time = refreshTime.substring(11, 13);//小时数对应下标
            time = parseInt(time);
            $.each(series, function (i, n) {
                var obj = {};
                var arry = n.data;
                arry[time] = parseInt(data[i].peopleNum);
                obj.name = n.name;
                obj.data = arry;
                a.push(obj)
            });

            trend("focus", a, "关注人群趋势图");
        }
    }
    ////趋势图
    function trend(id, Cdata) { ///id容器ID，Cdate如果为
        var xdata = [],
            txtData = [],
            crowd = [],
            isShow;
        var myChart;
        if (id == "trend_img") {
            isShow=false;
            myChart=echartTotal;
            for (var i = 0; i < 24; i++) {
                xdata.push(i + "点");
            }
            for (var n = 0, m = Cdata.length; n < m; n++) {
                var color = "#50A8F7";
                txtData.push(Cdata[n].name);
                if (n > 0) {
                    color = "#B8DAFB"
                }
                crowd.push({
                    type: "line",
                    name: Cdata[n].name,
                    data: Cdata[n].data,
                    itemStyle: {
                        normal: {
                            color: color,
                            lineStyle: {
                                width: 3
                            },
                            areaStyle: {type: 'default'}
                        }
                    }

                })
            }
        } else {
            isShow=true;
            if ("highestrisk" == id) {
                myChart = echartDangerArea;
            } else if ("focus" == id) {
                myChart =echartDangerPerson;
            }
            for (var i = 0; i < 24; i++) {
                xdata.push(i + "点");
            }
            for (var n = 0, m = Cdata.length; n < m; n++) {
                txtData.push(Cdata[n].name);
                crowd.push({
                    type: "line",
                    name: Cdata[n].name,
                    data: Cdata[n].data,
                    stack:"总量",
                    itemStyle: {normal: {areaStyle: {type: 'default'}}}
                })
            }
        }
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: txtData
            },
            calculable: true,
            xAxis: [{
                type: 'category',
                boundaryGap: false,
                data: xdata
            }],
            yAxis: [{
                type: 'value',
                axisLabel: {
                    formatter: '{value}人'
                },
                minInterval : 1
            }],
            series: crowd
        };
        myChart.setOption(option);
    }

    /*
     :
     Object
     COUNT:6
     HOUR:"08"*/
    //小时对应
    function getArray(data) {
        var array = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
        if (data == null || data.length == 0) {
            return array;
        }
        try {
            for (var i = 0; i < data.length; i++) {
                var index = parseInt(data[i].hour);
                array[index] = data[i].count
            }
        } catch (e) {
            return array;
        }
        return array;
    }


    var lifecycle_websocket;

    $(function () {
        var loading=layer.load(0, {shade: 0.4});
        // 指定websocket路径
        <% String url=request.getServerName()+":"+request.getServerPort();%>
        lifecycle_websocket = new WebSocket("ws://<%=url%>/case/emphasisDetail/${serviceCode}");
        $("#pageContainer").one("beforeChange", function () {
            lifecycle_websocket.close();
        });
        window.onbeforeunload = function () {
            lifecycle_websocket.close();
        };
        lifecycle_websocket.onmessage = function (evt) {
            var obj = JSON.parse(evt.data);
            if (obj == null || obj.length == 0) {
                layer.close(loading);
                return;
            }
            console.log(obj);
            $('#totalPerson').html(obj.empArea[0].peopleNum);
            $('#totalPerson').parent().click(function () {
                openPage('${ctx}/population/crowd/emphasisAreaCrowdQuery?serviceCode=${serviceCode}&beginTime=' + obj.beginTime + '&endTime=' + obj.endTime, '人群查询');
            });
            $('#dangerArea').html(obj.empArea[0].dangerAreaCount);
            $('#dangerArea').parent().click(function () {
                openPage('${ctx}/population/crowd/emphasisAreaCrowdQuery?serviceCode=${serviceCode}&beginTime=' + obj.beginTime + '&endTime=' + obj.endTime + '&dangerArea=1', '高危地区人群查询');
            });
            $('#dangerPerson').html(obj.empArea[0].dangerPersonCount);
            $('#dangerPerson').parent().click(function () {
                openPage('${ctx}/population/crowd/emphasisAreaCrowdQuery?serviceCode=${serviceCode}&beginTime=' + obj.beginTime + '&endTime=' + obj.endTime + '&dangerPerson=1', '关注人群查询');
            });
            $('#suspiciousPeople').html(obj.empArea[0].suspiciousNum);
            $('#suspiciousPeople').parent().click(function () {
                openPage('${ctx}/population/crowd/emphasisAreaSuspicious?beginTime=' + obj.beginTime +'&endTime=' + obj.endTime + '&configId=${serviceCode}', '可疑人群查询');
            });


            $('#updatetime').html("数据更新时间：" + obj.beginTime + "到 " + obj.endTime);
            //总人数
            /* if (obj.initdata == "init") {*/
            totalTrend(obj.totalPerson, obj.totalPersonPreDay, obj.totalPersonPreWeek, obj.totalPersonPreYear, obj.endTime);
            //////关注人群趋势图
            dangerArea(obj.dangerAreaCount, obj.initdata, obj.endTime);
            /////关注人群趋势图
            focusPeo(obj.dangerPersonCount, obj.initdata, obj.endTime);
            if (obj.suspiciousPeople != null && obj.suspiciousPeople.length > 0) {
                $('#chart_bottom').find('#suspli').hide();
                $.each(obj.suspiciousPeople, function (i, n) {
                    var $li = $('#chart_bottom').children("li[id='" + n.suspiciousImsiCode + "']");
                    if ($li.length == 0) {
                        var dangerArea = "";
                        var dangerPerson = "";
                        var status = "";
                        var captureTimes="";
                        var showDays="";
                        if (n.isDangerArea == 1) {
                            dangerArea = "高危地区人群,"
                        }
                        if (n.isDangerPerson == 1) {
                            dangerPerson = "关注人群,"
                        }
                        if (n.suspiciousStatus == 0) {
                            status = 'class=dubious'
                        }
                        if(n.capTimes!=undefined && n.capTimes!=null){
                            captureTimes=n.capTimes+"次 "
                        }
                        if(n.showDays!=undefined && n.showDays!=null){
                            showDays=n.showDays+"天 "
                        }

                        var html = "<li " + status + " id=" + n.suspiciousImsiCode + ">[" + n.statTime + "] 可疑人员: " + n.suspiciousImsiCode +
                            ", 出现 " +showDays+captureTimes+ dangerArea + dangerPerson + "<input type='button' class='btn btn-default' onclick='cancelSusp(this,2,0)' value='取消可疑'></li>";
                        $('#chart_bottom').append(html);
                    }
                });
                $('#chart_bottom').children().each(function () {
                    $(this).click(function () {
                        cancelSusp(this, 1);
                    })
                });
            }else {
                $('#chart_bottom').find('#suspli').show();
            }
            layer.close(loading);
        };
        $('#freq').change(function () {
            lifecycle_websocket.send($('#freq').val());
        });
    });

    function cancelSusp(data, status, isparent) {
        var imsi;
        if (isparent == 0) {
            imsi = $(data).parent().attr("id");
        } else {
            imsi = $(data).attr("id");
        }
        var configId = "${serviceCode}";
        var query = {
            imsiCode: imsi,
            configId: configId,
            status: status
        };
        $.post("<c:url value='/population/crowd/updateSuspicious'/>", query, function (result) {
            if (result == "0") {
                if (isparent == 0) {
                    $(data).parent().remove();
                    layer.msg("取消成功", {icon: 1, time: 1000});
                } else {
                    $(data).removeClass("dubious");
                }
            } else {
                layer.msg("取消失败", {icon: 0, time: 1000});
            }
        })
    }


    /////获取当前时间，前一周时间，前一年时间
    function getTime(data) {
        var nowdate = new Date();
        var y = nowdate.getFullYear();
        var m = nowdate.getMonth() + 1 < 10 ? "0" + (nowdate.getMonth() + 1) : nowdate.getMonth() + 1;
        var d = nowdate.getDate() < 10 ? "0" + nowdate.getDate() : nowdate.getDate();
        var ndate = y + '/' + m + '/' + d; ////当前时间
        //获取系统前一日的时间
        var onedaydate = new Date(nowdate - 24 * 3600 * 1000);
        var dy = onedaydate.getFullYear();
        var dm = onedaydate.getMonth() + 1 < 10 ? "0" + (onedaydate.getMonth() + 1) : onedaydate.getMonth() + 1;
        var dd = onedaydate.getDate() < 10 ? "0" + onedaydate.getDate() : onedaydate.getDate();
        var prevdaydate = dy + '/' + dm + '/' + dd; /////前一天时间
        //获取系统前一周的时间
        var oneweekdate = new Date(nowdate - 7 * 24 * 3600 * 1000);
        var wy = oneweekdate.getFullYear();
        var wm = oneweekdate.getMonth() + 1 < 10 ? "0" + (oneweekdate.getMonth() + 1) : oneweekdate.getMonth() + 1;
        var wd = oneweekdate.getDate() < 10 ? "0" + oneweekdate.getDate() : oneweekdate.getDate();
        var prevweekdate = wy + '/' + wm + '/' + wd; /////前一周时间
        //获取前一年时间
        var prevyeardate = (y - 1) + '/' + m + '/' + d;
        switch (data) {
            case 0:
                return ndate;
                break;
            case 1:
                return prevdaydate;
                break;
            case 2:
                return prevweekdate;
            case 3:
                return prevyeardate;
                break;
        }
    }
</script>

</html>