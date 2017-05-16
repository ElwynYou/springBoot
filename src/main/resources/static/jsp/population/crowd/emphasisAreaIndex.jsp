<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style type="text/css">
        .map {
            height: 620px;
            padding: 0px 0px
        }
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
            font-size: 14px;
        }

        .updatetime {
            font-size: 16px;
        }

        /********顶部自动刷新与历史记录部分结束*******/
        label {
            font-weight: normal;
            margin-right: 20px
        }

        input[type=checkbox] {
            right: 7px;
            position: relative;
            top: 2px
        }

        * {
            margin: 0;
            padding: 0
        }

        body {
            font-size: 12px;
            line-height: 1.5;
            color: #000;
            font-family: 微软雅黑, Arial, sans-serif;
        }

        ol, ul {
            list-style: none;
            padding: 0 10px
        }

        a {
            text-decoration: none;
            color: #000
        }

        a:hover {
            text-decoration: none
        }

        .someIndex2 ul li {
            float: left;
    		width: 280px;
   			margin: 0 2% 20px 0;
   			box-sizing: border-box;
   			height: 200px;
    		overflow: hidden;
        }

        .someIndex2Li_l {
            color: #fff;
            background: rgba(0, 0, 0, 0.1);
            width: 100%;
        }
		.someIndex2Li_l:hover{
			cursor: pointer;
			background: rgba(0, 0, 0, 0.2);
		}
        .someIndex2Li_l h2 {
          font-weight: bold;
    	font-size: 20px;
   		 display: inline-block;
   		 line-height: 34px;
   		 letter-spacing: 2px;
   		 margin: 5px 0 5px 10px;
        }

        .someIndex2Li_l p {
            margin-left: 30px;
            font-size: 24px;
            font-weight: bold;
        }

        .someIndex2Li_r {
            overflow: hidden;
            margin-top: 6px;
        }
		.someIndex2Li_r tr:hover{
			 background: rgba(0, 0, 0, 0.1);
			 cursor: pointer;
		}
        .someIndex2Li_r td {
            padding: 5px 10px;
            font-size: 16px;
            font-weight: bold;
            color: #fff
        }

        .someIndex2Li_r a {
            border-radius: 2px;
            line-height: 32px;
            padding: 0 15px;
            display: inline-block;
            font-size: 14px;
        }

        .someIndex2 ul li:nth-child(1) {
           background: rgb(69,137,148);
        }

        .someIndex2 ul li:nth-child(1) .someIndex2Li_r a {
            background: #feecb5;
            color: #b18500
        }

        .someIndex2 ul li:nth-child(2) {
            background: rgb(117,121,71);
        }

        .someIndex2 ul li:nth-child(2) .someIndex2Li_r a {
            background: #e2bac8;
            color: #d01a58
        }

        .someIndex2 ul li:nth-child(3) {
            background: rgb(114,83,52);
        }

        .someIndex2 ul li:nth-child(3) .someIndex2Li_r a {
            background: #cbf39d;
            color: #7cae42
        }

        .someIndex2 ul li:nth-child(4) {
            background: rgb(148,41,35);
        }

        .someIndex2 ul li:nth-child(4) .someIndex2Li_r a {
            background: #c8e7fc;
            color: #0274c3
        }
        .tab-content {
            border: 1px solid #aa896a
        }

        .col-md-12 {
            padding-left: 0;
            padding-right: 0;
        }

    </style>
    <%@ include file="/jsp/frameSource.jsp" %>
    <script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
    <script src="${ctx}/js/artTemplate.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>重点区域色块</title>
</head>
<body>
<div id="layout" style="display: none"></div>
<div class="content">
    <!-----------------顶部自动刷新与历史记录部分开始----------------->
    <div class="topdiv">
        <div class="refresh">
            自动刷新频率:
            <select name="" id="freq">
                <option  value="none">不自动刷新</option>
                <option  value="1">1分钟</option>
                <option  value="2">2分钟</option>
                <option  value="5">5分钟</option>
                <option  value="10">10分钟</option>
            </select>
            <span id="updatetime" class="updatetime"></span>
        </div>
    </div>
    <!-----------------顶部自动刷新历史记录部分结束----------------->
    <!-----------------热力图开始----------------->
    <div class="someIndex2">
        <ul>

        </ul>
    </div>
</div>
<script id="addrs" type="text/html">
    {{each DATA as value i}}
    <li>
        <div class="someIndex2Li_l" id="{{value.serviceCode}}" onclick="openPage('${ctx}/population/crowd/emphasisAreaDetail?serviceCode='+this.id,this.text)">
            <h2>{{value.serviceName}}</h2>
        </div>
        <div class="someIndex2Li_r">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr onclick="openPage('${ctx}/population/crowd/emphasisAreaDetail?serviceCode={{value.serviceCode}}','{{value.serviceName}}')">
                    <td>总人数</td>
                    <td>{{value.peopleNum}}人</td>
                </tr>
                <tr onclick="openPage('${ctx}/population/crowd/emphasisAreaCrowdQuery?serviceCode={{value.serviceCode}}&beginTime={{value.beginTime}}&endTime={{value.endTime}}&dangerPerson=1','关注人群查询')">
                    <td>重点关注人群</td>
                    <td>{{value.dangerPersonCount}}人</td>
                </tr>
                <tr onclick="openPage('${ctx}/population/crowd/emphasisAreaCrowdQuery?serviceCode={{value.serviceCode}}&beginTime={{value.beginTime}}&endTime={{value.endTime}}&dangerArea=1','高危地区查询')">
                    <td>高危地区人群</td>
                    <td>{{value.dangerAreaCount}}人</td>
                </tr>
                <tr onclick="openPage('${ctx}/population/crowd/emphasisAreaSuspicious?beginTime={{value.beginTime}}&endTime={{value.endTime}}&configId={{value.serviceCode}}','可疑人群查询')">
                    <td>可疑人群</td>
                    <td>{{value.suspiciousNum}}人</td>
                </tr>
            </table>
        </div>
    </li>
    {{/each}}
</script>
<script type="text/javascript">
    var lifecycle_websocket;
    $(function () {
        showLoading();
        <% String url=request.getServerName()+":"+request.getServerPort();%>
        lifecycle_websocket = new WebSocket("ws://<%=url%>/case/emphasisIndex");
        lifecycle_websocket.onmessage = function (evt) {
            try {
                var obj=JSON.parse(evt.data);
                if(obj==null || obj.length ==0){
                    closeLoading();
                    $('.topdiv').hide();
                    $('.someIndex2').append("<p class='bg-danger'>您所属辖区内还没有安装有设备的重点区域</p>");
                    return;
                }
                addrs(obj);
                $('#updatetime').html("数据更新时间:"+obj[0].beginTime+" 到 "+obj[0].endTime);
                var $layout = $('#layout');
                $.each(obj, function (i, e) {
                    if (e.suspiciousNum > 0) {
                        var html = "<p>[" +e.beginTime.substr(11,14)+"] " + e.serviceName + ":出现可疑人群" + e.suspiciousNum + "人</p>";
                        $layout.append(html)
                    }
                });
                if ($layout.children().length > 0) {
                    layerPopup("提醒",300,215,$layout);
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
        var addrs = {
            DATA: data1
        };
        var html = template("addrs", addrs);
        $(".someIndex2 ul:first").html(html);
		/////css样式
		$.each($(".someIndex2 ul li"),function(i,t){
			if(i>3){
				var n=i+1;
				var m;
				if(n%4==1||n%4==3){
					m=i-3;
				}else if(n%4==0||n%4==2){
					m=i-5;
				}
				var bgcolor=$(".someIndex2 ul li").eq(m).css("background-color");
				$(this).css("background",bgcolor);
			}
		});
    }
    $('#freq').change(function () {
        lifecycle_websocket.send($('#freq').val());
    });
    $("#pageContainer").one("beforeChange", function(){
        lifecycle_websocket.close();
    });
    window.onbeforeunload = function() {
        lifecycle_websocket.close();
    };
</script>
</body>
</html>