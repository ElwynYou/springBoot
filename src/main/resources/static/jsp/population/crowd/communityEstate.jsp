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

        label {
            font-weight: normal;
            margin-right: 20px
        }

        input[type=checkbox] {
            right: 7px;
            position: relative;
            top: 2px
        }

        .col-xs-12 {
            padding-left: 0;
            padding-right: 0;
        }

        .table > tbody > tr > th, .table > thead > tr > th {
            padding: 8px;
        }

        #pageContainer .table-bordered > thead > tr > th {
            color: #aa896a;
        }

        #pageContainer .table-bordered > tbody > tr > th {
            color: #666;
        }

        .table-bordered {
            border: 1px solid #aa896a;
            border-left: none;
            border-right: none
        }

        thead {
            border-top: 1px solid #e4d5ba
        }

        tbody {
            border-bottom: 1px solid #e4d5ba
        }
    </style>
    <%@ include file="/jsp/frameSource.jsp" %>
    <link rel="stylesheet" href="${ctx }/css/form.style.css"/>
    <script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery.cookie.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>小区人群</title>
</head>
<body>
<div class="row page-component-element">
    <div id="tracelocation_control">
        <form id="searchForm" class="form-inline" action="${ctx}/population/crowd/communityEstate"
              target="<%=request.getHeader("X-Requested-With") != null?"ajax":"_self" %>">
            <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
            <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
            <c:if test="${page.totalCount ne 0}">
                <div class="col-md-12 col-sm-12" style="padding:0 10px 10px 10px; width: 100%;line-height:26px">
                    <div class="form-group">
                        <div class="input-group" style="font-size:16px">
                            数据日期：${crowdManage.queryTime }
                        </div>
                    </div>
                </div>
            </c:if>
            <input name="areaCode" type="hidden" value="${crowdManage.areaCode}">
            <input name="queryTime" id="queryTime" type="hidden" value="${crowdManage.queryTime}">
        </form>
    </div>
    <div class="row  page-component-element" id="content">
        <c:if test="${page.totalCount ne 0}">
            <div class="col-xs-12">
                <table class="table table-bordered table-striped" id="dataList">
                    <caption class="export">
                        <div class="row datatablehead_head">
                            <div class="col-xs-5 datatablehead_lefthead ">
                                <div class="input-group input-group-sm">
                                    <span class="input-group-addon">每页显示结果数</span><select id="pageSizeSelect" onchange="page(${page.pageNo})">
                                    <option value="10" ${page.pageSize == 10 ? "selected":"" }>10条</option>
                                    <option value="20" ${page.pageSize == 20 ? "selected":"" }>20条</option>
                                    <option value="50" ${page.pageSize == 50 ? "selected":"" }>50条</option>
                                    <option value="100" ${page.pageSize == 100 ? "selected":"" }>100条</option>
                                </select>
                                </div>
                            </div>
                            <div class="col-xs-7 datatablehead_righthead">
                                列表|
                                <a href="javascript:;"
                                   onclick='toLoadPage("${ctx}/population/crowd/communityEstate?changeType=colorLump&areaCode=${crowdManage.areaCode}")'>
                                    色块</a>
                            </div>
                        </div>

                    </caption>
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>小区名</th>
                        <th>实有人口</th>
                        <th>重点关注人员</th>
                        <th>高危地区人群</th>
                        <th>闪现人群</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.list}" var="crowdManage" varStatus="status">
                        <tr>
                            <th>${( page.pageNo - 1 ) * page.pageSize + status.index + 1 }</th>
                            <th>${crowdManage.serviceName}</th>
                            <th><a href="javascript:;"
                                   onclick='detailQuery("${crowdManage.serviceCode}","${crowdManage.serviceName}","null")'>${crowdManage.totalCount == null ? 0 : crowdManage.totalCount}</a>
                            </th>
                            <th><a href="javascript:;"
                                   onclick='detailQuery("${crowdManage.serviceCode}","${crowdManage.serviceName}","highRisk")'>${crowdManage.dangerPersonCount }</a>
                            </th>
                            <th><a href="javascript:;"
                                   onclick='detailQuery("${crowdManage.serviceCode}","${crowdManage.serviceName}","prevent")'>${crowdManage.dangerAreaCount }</a>
                            </th>
                            <th><a href="javascript:;"
                                   onclick='detailQuery("${crowdManage.serviceCode}","${crowdManage.serviceName}","peopleType")'>${crowdManage.unmeantNum == null ? 0 :crowdManage.unmeantNum}</a>
                            </th>
                            <th><a href="javascript:;" onclick='analyzeQuery("${crowdManage.serviceCode}","${crowdManage.serviceName}")'>查看</a></th>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="row" style="padding:0 10px">
                    <div class="col-xs-5">
                        <div class="dataTables_info" role="status" aria-live="polite">${page.message}</div>
                    </div>
                    <div class="col-xs-7">
                        <div class="dataTables_paginate paging_simple_numbers">
                                ${page}</div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${page.totalCount eq 0}">
            <p class='bg-danger'>您所属辖区内还没有安装有设备的小区</p>
        </c:if>
    </div>
</div>
<script type="text/javascript">
    function shortcutSelect(day) {
        updateDate(day);
        page(1);
    }

    function updateDate(day) {
        var start = new Date();
        start.setDate(start.getDate() - Number(day));
        var end = new Date();
        $("#startTime").val(dateFormatYYYYMMDD(start));
        $("#endTime").val(dateFormatYYYYMMDD(end));
    }


    $(document).ready(function () {
        $.cookie('communityType', 'list', {expires: 7, path: '/'});
        $("#pageSizeSelect").change(function () {
            $("#pageSize").val(this.value);
            doQuery("#searchForm");
        });

        $("#query").click(function () {
            page(1);
        });

    });

    function toLoadPage(href) {
        $.get(href, function (page) {
            $("#pageContainer").html(page);
            $('#pageContainer').trigger('layoutChange', $.rainsoft.getPageContainerSize());
        });
    }


    function analyzeQuery(serviceCode, serviceName) {
        openPage($.rainsoft.baseUrl() + 'population/crowd/communityAnalyze?serviceCode=' + serviceCode + '&serviceName=' + serviceName + '&queryTime=' + $("#queryTime").val(), serviceName);
    }

    function formerlyTime(day) {
        var time = new Date();
        time.setDate(time.getDate() - Number(day));
        return dateFormatYYYYMMDD(time);
    }

    function detailQuery(serviceCode, serviceName, crowdType) {
        var url = $.rainsoft.baseUrl() + 'population/crowd/communityDetail?serviceCode=' + serviceCode + '&serviceName=' + serviceName;
        url += '&queryTime=' + $("#queryTime").val();
        if (crowdType == 'highRisk') {
            url += '&highRisk=1';
        }
        if (crowdType == 'prevent') {
            url += '&prevent=1';
        }
        if (crowdType == 'peopleType') {
            url += '&peopleType=3';
        }
        openPage(url, serviceName);
    }
</script>
</body>
</html>