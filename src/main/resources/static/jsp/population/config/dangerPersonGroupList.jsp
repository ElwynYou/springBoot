<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>分组管理</title>
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
			$("#pageNo").val(1);
			doQuery("#searchForm");
		});
	});
	function edit(href) {
		return toForm(href, 600,250);
	}
	</script>
</head>
<body>
	<div class="row page-component-element">
	<form id="searchForm" class="form-inline" action="${ctx}/population/config/dangerPersonGroup/list" method="post">
			<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
			<div class="col-xs-12" style="padding: 15px 0;">
				<div class="form-group">
					<div class="input-group">
						<label class="input-group-addon">分组名</label>
						<input type="text" class="form-control" id="name" name="name" value="${dangerPersonGroup.name}"  maxlength="30">
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
									<a class="add" id="btnAdd" href="${ctx}/population/config/dangerPersonGroup/form" onclick="return edit(this.href)"><img src="${ctx }/img/index/datatablehead/add.png"> <strong>添加</strong></a>
								</div>
							</div>
						</div>
					</caption>
					<thead>
						<tr>
							<th>ID</th>
							<th>分组名</th>
							<th>人数</th>
							<th>备注说明</th>
							<th>更新时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="dangerPersonGroup" varStatus="status">
							<tr>
								<td>${( page.pageNo - 1 ) * page.pageSize + status.index + 1 }</td>
								<td>${dangerPersonGroup.name}</td>
								<td>${dangerPersonGroup.personCount}</td>
								<td>${dangerPersonGroup.note}</td>
								<td><fmt:formatDate value="${dangerPersonGroup.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>
				    				<a href="${ctx}/population/config/dangerPersonGroup/form?id=${dangerPersonGroup.id}" onclick="return edit(this.href)">修改</a>
									<a href="${ctx}/population/config/dangerPersonGroup/delete?id=${dangerPersonGroup.id}" onclick="return ajaxDelete('确认要删除该分组吗？', this)">删除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="row">
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