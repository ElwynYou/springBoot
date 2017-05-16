<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <title></title>
    <%@ include file="/jsp/frameSource.jsp" %>
    <script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
    <script type="text/javascript" src="${ctx }/js/echarts/echarts.min.js"></script>
    <script src="${ctx}/js/artTemplate.js"></script>
</head>
<style>
    /********顶部自动刷新与历史记录部分开始*******/

    .content {
        padding: 20px 40px 20px 10px;
        overflow: hidden;
        min-width: 1500px;
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

    /********热力图开始*******/

    .content {
        width: 100%;
        min-width: 1400px;
        overflow: hidden;
        padding-right: 10px;
    }

    .addrdiv {
        position: relative;
        float: left;
        width: 23%;
        height: 290px;
        margin-right: 1%;
        margin-top: 10px;
        border: 1px solid #cbbeae;
        min-width: 340px;
    }

    .chart_img {
        position: absolute;
        width: 220px;
        height: 220px;
        left: 10px;
        top: 10px;
        background: #fff;
    }

    .addr {
        position: absolute;
        width: 100%;
        height: 50px;
        background: #CBBEAE;
        line-height: 50px;
        text-align: center;
        color: #fff;
        left: 0;
        bottom: 0;
        font-size: 20px;
    }

    .addr:hover {
        cursor: pointer;
    }

    .crowd_num {
        position: absolute;
        height: 220px;
        top: 10px;
        left: 240px;
        right: 10px;
    }

    .crowd_num div {
        height: 33.3%;
        width: 100%;
        line-height: 55px;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
    }

    .spanl span {
        color: red;
    }

    .spanl:hover {
        cursor: pointer;
    }

    /********热力图结束*******/


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
            <span id="updatetime" class="updatetime"></span>
        </div>
    </div>
    <!-----------------顶部自动刷新历史记录部分结束----------------->
    <!-----------------热力图开始----------------->
    <div class="heatMap" id="heatMap"></div>
</div>
<div id="layout" style="display: none"></div>
</body>
<script id="addrs" type="text/html">
    {{each DATA as value i}}
    <div class="addrdiv">
        <div id={{"chart_img_"+i}} class="chart_img"></div>
        <div class="crowd_num">
            <div>
                <span class="spanl"
                      onclick="openPage('${ctx}/population/heatmap/heatmapCrowdQuery?configId={{value.configId}}&beginTime={{value.beginTime}}&endTime={{value.endTime}}&dangerPerson=1','关注人群查询')">关注人群 <span>{{value.dangerPersonCount}}</span> 人</span>
            </div>
            <div>
                <span class="spanl"
                      onclick="openPage('${ctx}/population/heatmap/heatmapCrowdQuery?configId={{value.configId}}&beginTime={{value.beginTime}}&endTime={{value.endTime}}&dangerArea=1','高危地区查询')">高危地区 <span>{{value.dangerAreaCount}}</span> 人</span>
            </div>
            <div>
                <span class="spanl"
                      onclick="openPage('${ctx}/population/heatmap/heatmapSuspicious?beginTime={{value.beginTime}}&endTime={{value.endTime}}&configId={{value.configId}}','可疑人群查询')">可疑人群 <span>{{value.suspiciousNum}}</span> 人</span>
            </div>
        </div>
        <div class="addr" id={{value.configId}} onclick="openPage('${ctx}/population/heatmap/heatmapDetail?configId='+this.id,this.innerHTML)">
            {{value.configName}}
        </div>
    </div>
    {{/each}}
</script>
<script>
    var lifecycle_websocket;
    $(function () {
        showLoading();
        <% String url=request.getServerName()+":"+request.getServerPort();%>
        try {
            lifecycle_websocket = new WebSocket("ws://<%=url%>/case/heatMapIndex");
        } catch (e) {
            closeLoading();
            layer.msg("创建数据通信服务失败，该功能将无法正常使用，请尝试重新进入", {icon: 0, time: 2000});
            return;
        }

        lifecycle_websocket.onmessage = function (evt) {
            try {
                var obj = JSON.parse(evt.data);
                if (obj == null || obj.length == 0) {
                    closeLoading();
                    $('.topdiv').hide();
                    $('#heatMap').append("<p class='bg-danger'>您还没有配置热力分布，请先到配置管理中添加</p>");
                    return;
                }
                addrs(obj);
                $('#updatetime').html("数据更新开始时间：" + obj[0].beginTime + " 到 " + obj[0].endTime);
                var $layout = $('#layout');
                $.each(obj, function (i, e) {
                    if (e.suspiciousNum > 0) {
                        var html = "<p>[" +e.beginTime.substr(11,14)+"] "+ e.configName + ":出现可疑人群" + e.suspiciousNum + "人;</p>";
                        $layout.append(html)
                    }
                });
                if ($layout.children().length > 0) {
                    layerPopup("提醒", 300, 215, $layout);
                }
                closeLoading();
            } catch (e) {
                console.log(e);
                closeLoading();
            }
        };
    });
    //////后台获取的数据
    function addrs(data1) {
        var data = data1;
        var addrs = {
            DATA: data
        };
        var html = template("addrs", addrs);
        $(".heatMap").html(html);
        for (var i = 0; i < data.length; i++) {
            hotTrend("chart_img_" + i, data[i].totalPerson, data[i].warnNum);
        }
    }
    //////热力图
    function hotTrend(id, totalPerson, warnNum) {
        var titleformatter = "{a} <br/>{b} : {c}人";
        warnNum = (parseFloat(warnNum) / 0.8).toFixed(0);
        var myChart = echarts.init(document.getElementById(id));
        var option = {
            backgroundColor: "#fff",
            tooltip: {
                formatter: titleformatter
            },
            series: [{
                name: '',
                type: 'gauge',
                z: 3,
                min: 0,
                max: warnNum,
                splitNumber: 10,
                radius: "100px",
                axisLine: { // 坐标轴线
                    lineStyle: { // 属性lineStyle控制线条样式
                        color: [
                            [0.25, "#2DC6C8"],
                            [0.5, "#5AB1ED"],
                            [0.75, "#ED8E1A"],
                            [1, "#D7797F"]
                        ],
                        width: 10
                    }
                },
                axisTick: { // 坐标轴小标记
                    length: 15, // 属性length控制线长
                    lineStyle: { // 属性lineStyle控制线条样式
                        color: 'auto'
                    },
                    splitNumber: 10
                },
                splitLine: { // 分隔线
                    length: 18, // 属性length控制线长
                    lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
                        color: 'auto'
                    }
                },
                title: {
                    textStyle: {
                        fontSize: 17
                    }
                },
                detail: {
                    textStyle: {
                        fontSize: 22
                    },
                    formatter: formater
                },
                data: [{
                    value: totalPerson,
                    name: '总人数'
                }],
                center: ["110px", "120px"],

                axisLabel: {formatter: formater}
            }]
        };
        myChart.setOption(option);
    }

    $("#pageContainer").one("beforeChange", function () {
        lifecycle_websocket.close();
    });
    window.onbeforeunload = function () {
        lifecycle_websocket.close();
    };

    function formater(params, ticket, callback) {
        if (params < 1000) {
            return params.toFixed(0);
        } else if (params < 10000) {
            params = (parseFloat(params) / 1000).toFixed(1);
            return params + 'K'
        } else {
            params = (parseFloat(params) / 10000).toFixed(2);
            return params + 'W'
        }
    }

</script>
<script>
    $('#freq').change(function () {
        lifecycle_websocket.send($('#freq').val());
    })
</script>
</html>