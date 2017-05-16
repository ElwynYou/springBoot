<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/jsp/frameSource.jsp" %>
    <link rel="stylesheet" href="${ctx }/css/form.style.css"/>
    <script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#pageSizeSelect").change(function () {
                $("#pageSize").val(this.value);
                doQuery("#searchForm");
            });
            $("#query").click(function () {
                page(1);
            });
        });

    </script>
    <style>
        * {
            margin: 0;
            padding: 0;
            list-style: none;
            font-size: 14px;
        }

        .fbox {
            width: 100%;
            color: #9C7854;
            padding: 20px;
            overflow: hidden;
        }

        .list {
            float: left;
            width: 120px;
            overflow: hidden;
            margin-left: 60px;
        }

        .img,
        .txt {
            width: 100%;
        }

        .img {
            position: relative;
            height: 120px;
            border-radius: 50%;
            background: #f5e2cf;
            margin-bottom: 10px;
        }

        .img img {
            position: absolute;
            width: 80px;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            -webkit-transform: translate(-50%, -50%);
            -moz-transform: translate(-50%, -50%);
            -o-transform: translate(-50%, -50%);
        }

        .txt {
            text-align: center;
            line-height: 20px;
            font-weight: bold;
            height: 60px;
        }
    </style>
</head>
<body>
<form id="searchForm" class="form-inline" action="${ctx}/population/analyze/areaContast" method="post">
    <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/></form>
<div class="col-xs-12">
    <table class="table table-striped table-bordered dataTable" id="dataList">
        <thead>
        <tr>
            <th>编号</th>
            <th>IMSI</th>
            <th>归属地</th>
            <th>关注人群</th>
            <c:if test="${not empty page.list[0].AREA1}">
                <th>区域1</th>
            </c:if>
            <c:if test="${not empty page.list[0].AREA2}">
                <th>区域2</th>
            </c:if>
            <c:if test="${not empty page.list[0].AREA3}">
                <th>区域3</th>
            </c:if>
            <c:if test="${not empty page.list[0].AREA4}">
                <th>区域4</th>
            </c:if>
            <c:if test="${not empty page.list[0].AREA5}">
                <th>区域5</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list}" var="list" varStatus="status">
            <tr>
                <td>${( page.pageNo - 1 ) * page.pageSize + status.index + 1 }</td>
                <td>${list.imsiCode}</td>
                <td>${fns:areaName(list.phoneArea)}</td>
                <td>
                    <c:if test="${list.highRisk eq 0}">否</c:if>
                    <c:if test="${list.highRisk ne 0}">是</c:if>
                </td>
                <c:if test="${not empty list.AREA1}">
                    <td>${list.AREA1}</td>
                </c:if>
                <c:if test="${not empty list.AREA2}">
                    <td>${list.AREA2}</td>
                </c:if>
                <c:if test="${not empty list.AREA3}">
                    <td>${list.AREA3}</td>
                </c:if>
                <c:if test="${not empty list.AREA4}">
                    <td>${list.AREA4}</td>
                </c:if>
                <c:if test="${not empty list.AREA5}">
                    <td>${list.AREA5}</td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="row">
    <div class="col-xs-7" style="float: right;">
        <div class="dataTables_paginate paging_simple_numbers">
            ${page}
        </div>
    </div>
</div>
</body>
</html>
<script>
</script>