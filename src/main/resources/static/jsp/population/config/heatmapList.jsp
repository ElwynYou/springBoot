<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/jsp/frameSource.jsp" %>
	<link rel="stylesheet" href="${ctx }/css/form.style.css" />
    <script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>热力分布管理</title>
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
		return toForm(href, '90%', '90%',"添加/修改");
	}
	function updateStatus(id, status) {
        layer.confirm("您确定要"+(status == 1?"启用":"禁用")+"该热力分布吗？", {
            btn: ['确定', '取消']
        }, function () {
            $.post("${ctx}/population/config/heatmap/updateStatus?id="+id+"&status="+status, function (data) {
                if (data.state == 1) {
                    layer.msg("更新成功");
        			doQuery("#searchForm");
                } else {
                    layer.msg("更新失败");
                }
            })
        });
    }
	</script>
</head>
<body>
	<div class="row page-component-element">
		<form id="searchForm" class="form-inline" action="${ctx }/population/config/heatmap/list" method="post">
			<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
			<div class="col-xs-12" style="padding: 15px 0;">
				<div class="form-group">
					<div class="input-group">
						<label class="input-group-addon">热力分布名称</label>
						<input type="text" id="name" name="name" class="form-control" value="${heatmapConfig.name }">
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<label class="input-group-addon">状态</label>
						<select name="status" class="form-control">
	                          <option value="-1" ${heatmapConfig.status == '-1' ? "selected" : "" }>全部</option>
	                           <option value="1" ${heatmapConfig.status == '1' ? "selected" : "" }>启用</option>
	                           <option value="0" ${heatmapConfig.status == '0' ? "selected" : "" }>禁用</option>
	                    </select>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<button type="button" id="query" class="btn btn-default btn-md">
	                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
	                     </button>
	                     <button type="reset" class="btn reset-btn btn-md">
	                            <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>重置
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
									<span class="input-group-addon">每页显示结果数</span><select id="pageSizeSelect" onchange="page(${page.pageNo})">
										<option value="10" ${page.pageSize == 10 ? 'selected':'' }>10条</option>
										<option value="20" ${page.pageSize == 20 ? 'selected':'' }>20条</option>
										<option value="50" ${page.pageSize == 50 ? 'selected':'' }>50条</option>
										<option value="100" ${page.pageSize == 100 ? 'selected':'' }>100条</option>
									</select>
								</div>
							</div>
							<div class="col-xs-7 datatablehead_righthead ">
								<div class="batchoptionbuttom">
									<a class="add" id="btnAdd" href="${ctx}/population/config/heatmap/form" onclick="return edit(this.href)"><img src="${ctx }/img/index/datatablehead/add.png"> <strong>添加</strong></a>
								</div>
							</div>
						</div>
					</caption>
					<thead>
						<tr>
							<th>ID</th>
							<th>热力分布名称</th>
							<th>设备数</th>
							<th>状态</th>
							<th>可疑人群规则</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.id}</td>
								<td>${item.name}</td>
								<td>${item.deviceNum}</td>
								<td>${item.status == 1 ? "<span style='color:green;'>启用</span>" : "<span style='color:red;'>禁用</span>"}</td>
								<td>${item.doubtfulPeriod}天内出现${item.doubtfulDays}天或出现${item.doubtfulTimes}次</td>
								<td>
									<c:if test="${item.status == 1 }">
									<a href='javascript:void(0);' onclick="updateStatus(${item.id }, 0)">禁用</a>
									</c:if>
									<c:if test="${item.status != 1 }">
									<a href='javascript:void(0);' onclick="updateStatus(${item.id }, 1)" >启用</a>
									</c:if>
				    				<a href="${ctx}/population/config/heatmap/form?id=${item.id }" onclick="return edit(this.href)">修改</a>
									<a href="${ctx}/population/config/heatmap/delete?id=${item.id }" onclick="return ajaxDelete('确认要删除吗？', this)">删除</a>
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