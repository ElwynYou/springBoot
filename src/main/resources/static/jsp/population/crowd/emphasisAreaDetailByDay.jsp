<%@ page language="java"  pageEncoding="utf-8"%>
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
    </style>
    <%@ include file="/jsp/frameSource.jsp" %>
    <link rel="stylesheet" href="${ctx }/css/form.style.css" />
    <script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
	<script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>人群明细</title>
</head>
<body>
<div class="row page-component-element">
	<div id="tracelocation_control">
        <form id="searchForm" class="form-inline" action="${ctx }/population/crowd/emphasisAreaDetailByDay" target="<%=request.getHeader("X-Requested-With") != null?"ajax":"_self" %>">
       		<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
            <div class="col-xs-12" style="padding: 15px 0;">
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">IMSI:</span>
                        <input name="imsiCode" type="text" class="form-control" value="${emphasisAreaDetail.imsiCode }">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group input-medium  input-daterange"
                         data-date-format="yyyy-mm-dd">
                        <span class="input-group-addon">查询时间:</span> 
                        <input name="queryTime" value="${emphasisAreaDetail.queryTime }" 
                         onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',maxDate:'%y-%M-%d %H',readOnly:true,opposite:true,disabledDates:['${heatMapDetail.limitingTime} ..']})"
                         class="form-control datetime-picker" id="startTime" type="text"> 
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">归属地</span>
                        <sys:city value="${emphasisAreaDetail.city }" name="city"/>
                    </div>
                </div>
               
                <div class="form-group">
                    <div class="input-group">
                        <input id="highRisk" name="highRisk"  value="1" ${emphasisAreaDetail.highRisk == 1 ? "checked":"" } type="checkbox"/>
                        <label for="highRisk">关注人群</label>
                        <input id="prevent" name="prevent" value="1"  ${emphasisAreaDetail.prevent == 1 ? "checked":"" } type="checkbox"/>
                        <label for="prevent">重点防范</label>
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="input-group">
                        <button type="button" id="query" class="btn btn-default btn-md">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                       		     查询
                        </button>
                       
                    </div>
                </div>
            </div>
            <input hidden="hidden" id="limitingTime" name="limitingTime"  value=" ${emphasisAreaDetail.limitingTime}">
            <input hidden="hidden" id="configId" name="configId"  value="${emphasisAreaDetail.configId}">
        </form>

     <div class="row  page-component-element">
			<div class="col-xs-12">
				<table class="table table-striped table-bordered dataTable" id="dataList">
					<caption>
						<div class="row datatablehead_head">
							<div class="col-xs-5 datatablehead_lefthead ">
								<div class="input-group input-group-sm">
									<span class="input-group-addon">每页显示结果数</span><select id="pageSizeSelect" onchange="page(${page.pageNo})">
										<option value="10" ${page.pageSize == 10 ? 'selected':'' }>10条</option>
										<option value="20" ${page.pageSize == 20 ? 'selected':'' }>20条</option>
										<option value="50" ${page.pageSize == 50 ? 'selected':'' }>50条</option>
										<option value="100" ${page.pageSize == 100 ? 'selected':'' }>100条</option>
									</select>
								</div>
							</div>
							<div class="col-xs-7 datatablehead_righthead ">
							</div>
						</div>
					</caption>
					<thead>
						<tr>
							<th>序号</th>
							<th>IMSI</th>
							<th>运营商</th>
							<th>归属地</th>
							<th>出现次数</th>
							<th>关注人群</th>
							<th>首次出现时间</th>
							<th>最后出现时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody style="text-align:center;">
						<c:forEach items="${page.list}" var="item" varStatus="status">
							<tr>
								<td>${( page.pageNo - 1 ) * page.pageSize + status.index + 1 }</td>
								<td>${item.imsiCode }</td>
								<td>${fns:dictLabel('1001',item.operatorType) }</td>
								<td ${item.prevent == 1 ? "style='color:red'":"" }>${item.areaName }</td>
								<td>${item.captureNum }</td>
								<td ${!empty item.attentionName ? "style='color:red'":"" }>${!empty item.attentionName ? item.attentionName : "否" }</td>
								<td>${item.firstCaptureTime}</td>
								<td>${item.lastCaptureTime}</td>
								<td>
				    				<a href="${ctx}/population/query/trace?imsiCode=${item.imsiCode }" onclick="return openPage(this.href,'查看${item.imsiCode }运动轨迹')">轨迹</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="row" style="padding: 0 10px">
					<div class="col-xs-5">
						<div class="dataTables_info" role="status" aria-live="polite">${page.message }</div>
					</div>
					<div class="col-xs-7">
						<div class="dataTables_paginate paging_simple_numbers">
							${page}
						</div>
					</div>
				</div>
			</div>
		</div>
</div>
</div>
 
<script type="text/javascript">
$(function(){
	$.date97();
	$("#query").click(function(){
		page(1);
	});
	$("#pageSizeSelect").change(function(){
		$("#pageSize").val(this.value);
		doQuery("#searchForm");
	});
});
</script>
</body>
</html>