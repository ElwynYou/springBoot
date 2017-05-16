<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>重点防范地区管理</title>
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
		return toForm(href, 700,270);
	}
	</script>
</head>
<body>
	<div class="row page-component-element">
	<form id="searchForm" class="form-inline" action="${ctx}/population/config/danger/area/list" method="post">
			<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
			<div class="col-xs-12" style="padding: 15px 0;">
				<div class="form-group">
					<div class="input-group">
						<label class="input-group-addon">区域</label>
							<sys:city name="area" value="${dangerArea.area}" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<button id="query" type="button" class="btn btn-default btn-md">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							查询
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
									<a class="add" id="btnAdd" href="${ctx}/population/config/danger/area/form" onclick="return edit(this.href)"><img src="${ctx }/img/index/datatablehead/add.png"> <strong>添加</strong></a>
								</div>
							</div>
						</div>
					</caption>
					<thead>
						<tr>
							<th>区域</th>
							<th>名称</th>
							<th>更新时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="dangerArea">
							<tr>
								<td>${dangerArea.area } [${fns:areaName(dangerArea.area)}]</td>
								<td>${dangerArea.brief}</td>
								<td><fmt:formatDate value="${dangerArea.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>
 				    				<a href="${ctx}/population/config/danger/area/form?id=${dangerArea.area}" onclick="return edit(this.href)">修改</a>
									<a href="${ctx}/population/config/danger/area/delete?id=${dangerArea.area}" onclick="return ajaxDelete('确认要删除该重点防范地区吗？', this)">删除</a>
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