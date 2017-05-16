<%@ page language="java"  pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>人群查询</title>
    <%@ include file="/jsp/frameSource.jsp" %>
    <script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
	<script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#queryBtn").click(function(){
			page(1);
		});
		$("#pageSizeSelect").change(function(){
			$("#pageSize").val(this.value);
			doQuery("#searchForm");
		});
	});
	</script>
</head>
<body>
	<div class="row">
        <form id="searchForm" class="form-inline" action="${ctx }/population/crowd/emphasisAreaCrowdQuery" method="post" target="<%=request.getHeader("X-Requested-With") != null?"ajax":"_self" %>">
        	<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
            <div class="col-xs-12" style="padding: 15px 0;">
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">IMSI</span>
                        <input name="imsi" type="text" class="form-control" value="${query.imsi }">
                    </div>
                </div>
				<div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">时间段</span> 
                        <input name="beginTime" class="input-medium Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d %H:%m'})" value="${query.beginTime}"  id="beginTime" type="text" style="width: 130px">
                        <span class="input-group-addon">-</span> 
                        <input name="endTime" class="input-medium Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', maxDate:'%y-%M-%d %H:%m'})"  value="${query.endTime}" id="endTime" type="text" style="width: 130px">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">归属地</span>
                         <sys:city value="${query.phoneArea }" name="phoneArea"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <input id="dangerPerson" name="dangerPerson" value="1" type="checkbox" ${query.dangerPerson == 1 ? "checked":"" }/>
                        <label for="dangerPerson">关注人群</label>
                        <input id="dangerArea" name="dangerArea" value="1" type="checkbox" ${query.dangerArea == 1 ? "checked":"" }/>
                        <label for="dangerArea">高危地区人群</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <button id="queryBtn" type="button" class="btn btn-default btn-md">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                          	  查询
                        </button>
                    </div>
                </div>
            </div>
            <input type="hidden" name="serviceCode" value="${query.serviceCode}"/>
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
							<th>关注人群</th>
							<th>出现次数</th>
							<th>首次出现时间</th>
							<th>最后出现时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item" varStatus="status">
							<tr>
								<td>${( page.pageNo - 1 ) * page.pageSize + status.index + 1 }</td>
								<td>${item.imsiCode }</td>
								<td>${fns:dictLabel('1001', item.operators) }</td>
								<td ${item.prevent == 1 ? "style='color:red'":"" }>${fns:areaName(item.phoneArea) }</td>
								<td ${item.highrisk == 1 ? "style='color:red'":"" }>${item.highrisk == 1 ? "是" : "否" }</td>
								<td>${item.count }</td>
								<td><fmt:formatDate value="${item.firstTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><fmt:formatDate value="${item.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>
				    				<a href="${ctx}/population/query/trace?imsiCode=${item.imsiCode }&startTime=${query.beginTime}:00&endTime=${query.endTime}:59" onclick="return openPage(this.href,'查看${item.imsiCode }运动轨迹')">轨迹</a>
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