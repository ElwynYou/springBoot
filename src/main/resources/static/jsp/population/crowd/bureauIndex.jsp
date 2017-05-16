<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <style type="text/css">
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

        * {
            margin: 0;
            padding: 0
        }

        ol, ul {
            list-style: none
        }
		ul{
			padding: 0 10px;
		}
        a {
            text-decoration: none;
            color: #000
        }

        a:hover {
            text-decoration: none
        }

        .someIndex ul li {
            float: left;
            width: 32%;
            margin: 0 2% 20px 0;
            box-sizing: border-box;
            display: inline-block;
        }

        .someIndex ul li a {
            display: block;
            overflow: hidden;
        }

        .someIndexLi_l {
            width: 70%;
            float: left;
            color: #fff;
        }

        .someIndexLi_l h2 {
            margin: 20px 0 5px 30px;
            font-size: 18px;
        }

        .someIndexLi_l p {
            margin-left: 30px;
            font-size: 24px;
            font-weight: bold;
        }

        .someIndexLi_r {
            width: 30%;
            background: rgba(0, 0, 0, 0.1);
            text-align: center;
            vertical-align: middle;
            height: 100px;
            display: table-cell;
        }

        .someIndex ul li:nth-child(1) {
            background: #fec006;
        }

        .someIndex ul li:nth-child(2) {
            background: #e81d62;
        }

        .someIndex ul li:nth-child(3) {
            background: #8ac249;
            margin: 0 0 20px 0;
        }

        .someIndex ul li:nth-child(4) {
            background: #0698fe;
        }

        .someIndex ul li:nth-child(5) {
            background: #1de8a3;
        }

        .someIndex ul li:nth-child(6) {
            background: #c65cfd;
            margin: 0 0 20px 0;
        }
		.th{
			color: #9C7854;
		}
		.col-md-12{
			padding-left:0;
			padding-right: 0;
		}
		.table{
			border: none;
		}
		th,td{
			padding: 8px;
		}
    </style>
    <%@ include file="/jsp/frameSource.jsp" %>
    <script type="text/javascript" src="${ctx}/js/form.utils.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>人群查询</title>
</head>
<body>
<div class="someIndex">
    <div style="margin: 10px;">
        <p id="updateTime" style="font-size: 16px"></p>
    </div>
    <ul>
        <li>
            <a href="javascript:void(0)" onclick="openPage('${ctx}/population/query/crowdList?beginTime=${beginTime}&endTime=${endTime}&index=isIndex','人群查询')">
                <div class="someIndexLi_l">
                    <h2>总人数</h2>
                    <p>${peopleCount.totalPerson}</p>
                </div>
                <div class="someIndexLi_r"><img src="${ctx}/img/population/someIndex1.png"/></div>
            </a>
        </li>
        <li>
            <a href="javascript:void(0)" onclick="openPage('${ctx}/population/query/crowdList?dangerPerson=1&beginTime=${beginTime}&endTime=${endTime}&index=isIndex','人群查询')">
                <div class="someIndexLi_l">
                    <h2>关注人群</h2>
                    <p>${peopleCount.highRiskNum}</p>
                </div>
                <div class="someIndexLi_r"><img src="${ctx }/img/population/someIndex2.png"/></div>
            </a>
        </li>
        <li>
            <a href="javascript:void(0)" onclick="openPage('${ctx}/population/query/crowdList?dangerArea=1&beginTime=${beginTime}&endTime=${endTime}&index=isIndex','人群查询')">
                <div class="someIndexLi_l">
                    <h2>高危地区人群</h2>
                    <p>${peopleCount.preventNum}</p>
                </div>
                <div class="someIndexLi_r"><img src="${ctx }/img/population/someIndex3.png"/></div>
            </a>
        </li>
        <li>
            <a href="javascript:void(0)" onclick="openPage('${ctx }/population/crowd/communityEstate','小区查询')">
                <div class="someIndexLi_l">
                    <h2>小区</h2>
                    <p>${peopleCount.cell}</p>
                </div>
                <div class="someIndexLi_r"><img src="${ctx }/img/population/someIndex4.png"/></div>
            </a>
        </li>
        <li>
            <a href="javascript:void(0)" onclick="openPage('${ctx }/population/crowd/emphasisAreaIndex','重点区域')">
                <div class="someIndexLi_l">
                    <h2>重点区域</h2>
                    <p>${peopleCount.improtantArea}</p>
                </div>
                <div class="someIndexLi_r"><img src="${ctx }/img/population/someIndex5.png"/></div>
            </a>
        </li>
        <li>
            <a href="javascript:void(0)" onclick="openPage('${ctx}/population/query/machineList?index=isIndex','设备查询')">
                <div class="someIndexLi_l">
                    <h2>设备数</h2>
                    <p>${peopleCount.machineNum}</p>
                </div>
                <div class="someIndexLi_r"><img src="${ctx }/img/population/someIndex6.png"/></div>
            </a>
        </li>
    </ul>
</div>
<!-- <div class="col-md-12" style="font-size: 10px;padding: 0 10px"> -->
<!--     说明： -->
<!--     1、小区数、重点区域数、设备数为截止到当前的数据； -->
<!--     2、总人数：指所有设备在2017-03-23日采集到的IMSI数（同一个IMSI号被多次采集只算1次）； -->
<!--     3、关注人群：指总人数中在关注人群库中存在的IMSI数； -->
<!--     4、高危地区人群：指总人数中IMSI归属地在重点防范库中存在的IMSI数； -->
<!-- </div> -->
<div class="row  page-component-element">
    <div class="col-md-12  col-md-offset-0">
        <table class="table table-bordered table-striped">
            <tr>
                <th style="color:#9C7854">区县</th>
                <th>小区</th>
                <th>重点区域</th>
                <th>设备数</th>
                <th>总人数</th>
                <th>关注人群</th>
                <th>高危地区人群</th>
            </tr>
            <c:forEach items="${detailCount}" var="d">
                <tr>
                    <td>${d.areaName}</td>
                    <td><a href="javascript:void(0)" onclick="openPage('${ctx }/population/crowd/communityEstate?areaCode=${d.areaCode}','小区查询')">${d.cell}</a></td>
                    <td><a href="javascript:void(0)" onclick="openPage('${ctx }/population/crowd/emphasisAreaIndex?areaCode=${d.areaCode}','重点区域')">${d.improtantArea}</a></td>
                    <td><a href="javascript:void(0)" onclick="openPage('${ctx }/population/query/machineList?areaCode=${d.areaCode}&index=isIndex','设备查询')">${d.machineNum}</a></td>
                    <td><a href="javascript:void(0)" onclick="openPage('${ctx }/population/query/crowdList?areaCode=${d.areaCode}&beginTime=${beginTime}&endTime=${endTime}&index=isIndex','人群查询')">${d.totalPerson}</a></td>
                    <td><a href="javascript:void(0)" onclick="openPage('${ctx }/population/query/crowdList?areaCode=${d.areaCode}&dangerPerson=1&beginTime=${beginTime}&endTime=${endTime}&index=isIndex','人群查询')">${d.highRiskNum}</a></td>
                    <td><a href="javascript:void(0)" onclick="openPage('${ctx }/population/query/crowdList?areaCode=${d.areaCode}&dangerArea=1&beginTime=${beginTime}&endTime=${endTime}&index=isIndex','人群查询')">${d.preventNum}</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
<script>
    $('#updateTime').html("数据时间:"+"${beginTime}".substr(0,10));
</script>