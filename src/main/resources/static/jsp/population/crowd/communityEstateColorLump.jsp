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

        ol, ul {
            list-style: none;
            margin: 0;
            padding: 0
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
            width: 100%;
            color: #fff;
            background: rgba(0, 0, 0, 0.1)
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

        .someIndex2Li_l:hover {
            cursor: pointer;
            background: rgba(0, 0, 0, 0.2);
        }

        .someIndex2Li_r {
            overflow: hidden;
            margin-top: 6px;
        }

        .someIndex2Li_r tr:hover {
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
            background: rgb(69, 137, 148);
        }

        .someIndex2 ul li:nth-child(1) .someIndex2Li_r a {
            background: #feecb5;
            color: #b18500
        }

        .someIndex2 ul li:nth-child(2) {
            background: rgb(117, 121, 71);
        }

        .someIndex2 ul li:nth-child(2) .someIndex2Li_r a {
            background: #e2bac8;
            color: #d01a58
        }

        .someIndex2 ul li:nth-child(3) {
            background: rgb(114, 83, 52);
        }

        .someIndex2 ul li:nth-child(3) .someIndex2Li_r a {
            background: #cbf39d;
            color: #7cae42
        }

        .someIndex2 ul li:nth-child(4) {
            background: rgb(148, 41, 35);
        }

        .someIndex2 ul li:nth-child(4) .someIndex2Li_r a {
            background: #c8e7fc;
            color: #0274c3
        }

        .col-xs-12 {
            padding-left: 0;
            padding-right: 0;
        }

        .page-component-element {
            padding: 0 10px;
        }
    </style>
    <%@ include file="/jsp/frameSource.jsp" %>
    <script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery.cookie.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>小区人群色块</title>
</head>
<body>
<div class="row page-component-element">
    <div id="tracelocation_control">
        <form id="searchForm" class="form-inline" action="${ctx}/population/crowd/communityEstate">
            <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
            <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
            <c:if test="${page.totalCount ne 0}">
            <div class="col-md-12 col-sm-12" style="padding:0 10px 10px 10px; width: 100%">
                <div class="form-group" style="float: left;">
                    <div style="font-weight:bold;font-size:16px" class="input-group">
                        数据日期：${crowdManage.queryTime }
                    </div>
                </div>

                <div class="form-group datatablehead_righthead" style="float: right;">
                    <div class="input-group">
                        <a href="javascript:;" style="color:#337ab7"
                           onclick='toLoadPage("${ctx}/population/crowd/communityEstate?changeType=list&areaCode=${crowdManage.areaCode}")'>列表</a>|
                        色块
                    </div>
                </div>
            </div>
            </c:if>
            <input name="areaCode" type="hidden" value="${crowdManage.areaCode}">
            <input name="queryTime" id="queryTime" type="hidden" value="${crowdManage.queryTime}">
        </form>
    </div>

    <div class="row  page-component-element">
        <c:if test="${page.totalCount ne 0}">
            <!-- 循环色块 开始 -->
            <div class="someIndex2">
                <ul>
                    <c:forEach items="${page.list}" var="crowdManage">
                        <li>
                            <div class="someIndex2Li_l" onclick='analyzeQuery("${crowdManage.serviceCode}","${crowdManage.serviceName}")'>
                                <h2>${crowdManage.serviceName }</h2>
                            </div>
                            <div class="someIndex2Li_r">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr onclick='detailQuery("${crowdManage.serviceCode}","${crowdManage.serviceName}","null")'>
                                        <td>实有人口</td>
                                        <td>${crowdManage.totalCount == null ? 0 :crowdManage.totalCount }人</td>
                                    </tr>
                                    <tr onclick='detailQuery("${crowdManage.serviceCode}","${crowdManage.serviceName}","highRisk")'>
                                        <td>重点关注人群</td>
                                        <td>${crowdManage.dangerPersonCount }人</td>
                                    </tr>
                                    <tr onclick='detailQuery("${crowdManage.serviceCode}","${crowdManage.serviceName}","prevent")'>
                                        <td>高危地区人群</td>
                                        <td>${crowdManage.dangerAreaCount }人</td>
                                    </tr>
                                    <tr>
                                        <td onclick='detailQuery("${crowdManage.serviceCode}","${crowdManage.serviceName}","peopleType")'>闪现人群</td>
                                        <td>${crowdManage.unmeantNum  == null ? 0 :crowdManage.unmeantNum}人</td>
                                    </tr>
                                </table>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <!-- 循环色块 结束 -->
            <div class="col-xs-12" id="colorLump">
                <div class="col-xs-5" style="padding-left:0">
                    <div class="dataTables_info" role="status" aria-live="polite">${page.message }</div>
                </div>
                <div class="col-xs-7" style="padding-right:0">
                    <div class="dataTables_paginate paging_simple_numbers">
                            ${page}</div>
                </div>
            </div>
        </c:if>
        <c:if test="${page.totalCount eq 0}">
            <p class='bg-danger'>您所属辖区内还没有安装有设备的小区</p>
        </c:if>
    </div>

</div>
<script type="text/javascript">
    $(function () {
/////css样式
        $.each($(".someIndex2 ul li"), function (i, t) {
            if (i > 3) {
                var n = i + 1;
                var m;
                if (n % 4 == 1 || n % 4 == 3) {
                    m = i - 3;
                } else if (n % 4 == 0 || n % 4 == 2) {
                    m = i - 5;
                }
                var bgcolor = $(".someIndex2 ul li").eq(m).css("background-color");
                $(this).css("background", bgcolor);
            }
        });
    });
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
        $.cookie('communityType', 'colorLump', {expires: 7, path: '/'});

        $.date97();
        if (!$("#startTime").val() && !$("#endTime").val()) {
            updateDate(1);
        }


        $("#query").click(function () {
            page(1);
        });

        $('#highRisk').click(function () {
            if ($(this).val() == 1) {
                $(this).val(0);
            } else {
                $(this).val(1);
            }

        });
        $('#prevent').click(function () {
            if ($(this).val() == 1) {
                $(this).val(0);
            } else {
                $(this).val(1);
            }
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