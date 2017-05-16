<%@page import="com.rainsoft.common.Consts"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
pageContext.setAttribute("housing", Consts.SERVICE_TYPE_HOUSING);
pageContext.setAttribute("emphasis", Consts.SERVICE_TYPE_EMPHASIS_AREA);
%>
<!DOCTYPE html>
<html>
<head>
<title>报警设置管理</title>
<style>

.col-xs-12{padding-left:0;padding-right: 0;}
.table>tbody>tr>th,.table>thead>tr>th{padding: 8px;}
 thead > tr > th{color:#aa896a;}
 tbody > tr > th{color:#666;}
.table-bordered{border: 1px solid #aa896a;border-left:none;border-right:none}
.batchoptionbuttom{padding-right:10px}
</style>
<%@ include file="/jsp/frameSource.jsp"%>
<link rel="stylesheet" href="${ctx }/css/form.style.css" />
<script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#pageSizeSelect").change(function(){
			$("#pageSize").val(this.value);
			doQuery("#searchForm");
		});
		$("#query").click(function(){
			page(1);
		});
	});
	function edit(href) {
		return toForm(href, 650,450);
	}
	</script>
</head>
<body>
	<div class="row page-component-element">
	<form id="searchForm" class="form-inline" action="${ctx}/population/config/alarmSetting/list" method="post">
			<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
			<div class="col-xs-12" style="padding: 15px 0;">
				<div class="form-group">
					<div class="input-group">
						<label class="input-group-addon">所属城区</label>
						<sys:userarea name="areaCode" value="${alarmSetting.areaCode }" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<label class="input-group-addon">小区/重点区域</label>
						<input type="text" class="form-control" id="serviceName" name="serviceName" value="${alarmSetting.serviceName}"  maxlength="50" placeholder="请输入查询名称或代码">
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<label class="input-group-addon">区域类型</label>
						<select name="serviceType" id="serviceType">
							<option value="">不限</option>
							<option value="${housing }" ${alarmSetting.serviceType==housing ? "selected":"" }>小区</option>
							<option value="${emphasis }" ${alarmSetting.serviceType==emphasis ? "selected":"" }>重点区域</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<label class="input-group-addon">接警人</label>
						<input type="text" class="form-control" id="receiver" name="receiver" value="${alarmSetting.receiver}"  maxlength="30" placeholder="请输入查询接警人名称">
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<button id="query" type="button" class="btn btn-default btn-md">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							查询
						</button>
						<button type="reset" class="btn reset-btn btn-md">
							<span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
							重置
						</button>
					</div>
				</div>
			</div>
		</form>
		
		<div class="row  page-component-element">
			<div class="col-xs-12">
				<table class="table table-striped table-bordered dataTable" id="dataList">
					<caption>
						<div class="row datatablehead_head">
							<div class="col-xs-5 datatablehead_lefthead ">
								<div class="input-group input-group-sm">
									<span class="input-group-addon">每页显示结果数</span><select id="pageSizeSelect">
										<option value="10" ${page.pageSize == 10 ? 'selected':'' }>10条</option>
										<option value="20" ${page.pageSize == 20 ? 'selected':'' }>20条</option>
										<option value="50" ${page.pageSize == 50 ? 'selected':'' }>50条</option>
										<option value="100" ${page.pageSize == 100 ? 'selected':'' }>100条</option>
									</select>
								</div>
							</div>
							<div class="col-xs-7 datatablehead_righthead ">
								<div class="batchoptionbuttom">
									<a class="add" id="btnAdd" href="${ctx}/population/config/alarmSetting/form" onclick="return edit(this.href)"><img src="${ctx }/img/index/datatablehead/add.png"> <strong>添加</strong></a>
								</div>
							</div>
						</div>
					</caption>
					<thead>
						<tr>
							<th>ID</th>
							<th>报警策略</th>
							<th>小区/重点区域</th>
							<th>区域类型</th>
							<th>接警人</th>
							<th>报警方式</th>
							<th>更新时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="alarmSetting">
							<tr>
								<td>${alarmSetting.id}</td>
								<td>${alarmSetting.alarmName}</td>
								<td>${alarmSetting.serviceName}</td>
								<td><c:choose>
								<c:when test="${alarmSetting.serviceType==housing}">小区</c:when>
								<c:when test="${alarmSetting.serviceType==emphasis}">重点区域</c:when>
								<c:otherwise>其他[${alarmSetting.serviceType}]</c:otherwise>
								</c:choose></td>
								<td>${alarmSetting.receiver}</td>
								<td>
								<c:set var="notFirst" value="false" />
								<c:forEach var="item" items="${alarmSetting.argList }">
								<c:if test="${item.valid }">
								<c:if test="${notFirst }">、</c:if>${item.label }
								[<c:forEach var="arg" items="${item.param }" varStatus="status"><c:if test="${status.index > 0 }">, </c:if>${arg.value }</c:forEach>]
								<c:set var="notFirst" value="true" />
								</c:if>
								</c:forEach>
								</td>
								<td><fmt:formatDate value="${alarmSetting.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>
				    				<a href="${ctx}/population/config/alarmSetting/form?id=${alarmSetting.id}" onclick="return edit(this.href)">修改</a>
									<a href="${ctx}/population/config/alarmSetting/delete?id=${alarmSetting.id}" onclick="return ajaxDelete('确认要删除该报警设置吗？', this)">删除</a>
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
</body>
</html>