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
    <title>可疑人群</title>
</head>
<body>
<div class="row page-component-element">
	<div id="tracelocation_control">
        <form id="searchForm" class="form-inline" action="${ctx }/population/heatmap/heatmapSuspicious" target="<%=request.getHeader("X-Requested-With") != null?"ajax":"_self" %>">
       		<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
            <div class="col-xs-12" style="padding: 15px 0;">
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">IMSI</span>
                        <input name="imsiCode" type="text" class="form-control" value="${heatMapSuspicious.imsiCode }">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">捕获时间</span> 
                        <input name="beginTime" value="${heatMapSuspicious.beginTime}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d %H:%m'})"
                                                                            class="form-control datetime-picker"
                                                                            id="startTime" type="text"><span class="input-group-addon">-</span>
                        <input name="endTime" value="${heatMapSuspicious.endTime}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d %H:%m'})"
                      class="form-control datetime-picker"
                      id="endTime" type="text">  
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
            <input hidden="hidden" id="configId" name="configId"  value="${heatMapSuspicious.configId}">
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
								<td>${item.captureTimes }</td>
								<td ${item.highRisk == 1 ? "style='color:red'":"" }>${item.highRisk == 1 ? "是" : "否" }</td>
								<td>${item.firstCaptureTime}</td>
								<td>${item.lastCaptureTime}</td>
								<td id="${item.imsiCode}update">
									<a href="javascript:;" onclick='updateSuspicious("${item.imsiCode}","2")'>取消</a>   
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
		if($("#searchForm").valid()){
			page(1);
		}
	});
	$("#pageSizeSelect").change(function(){
		if($("#searchForm").valid()){
			$("#pageSize").val(this.value);
			doQuery("#searchForm");
		}
	});
	
	  $('#searchForm').validate({ 
			rules:{
				beginTime:{
					beginTimeValidator : true,
                  required :true
				},
				endTime:{
                  required :true
				}
			},
            errorPlacement: function (error, element) {
                if (element.is(':radio') || element.is(':checkbox')) { //如果是radio或checkbox
                    error.appendTo(element.parent()); //将错误信息添加当前元素的父结点后面
                } else {
                    layer.tips(error[0].textContent, element, {
                        tips: [3, '#f10f00'],  tipsMore: true
                    });
                }
            }
		});
	 
		jQuery.validator.addMethod("beginTimeValidator", function(value, element) {  
		    return this.optional(element) || !validateDate($("#endTime").val(), value).flag;    
		}, "结束时间不能小于开始时间");
});
    
  function updateSuspicious(imsiCode,status){
	  showLoading();
	  var url = "${ctx}/population/heatmap/updateSuspicious?imsiCode="+imsiCode+"&status=" + status +"&configId="+$("#configId").val();
		$.get(url, function(result){
			closeLoading();
			if (result == "0") {
				$("#"+imsiCode+"update").parent().remove();
			} else {
				layer.msg(result);
			} 
	 }); 
  }
</script>
</body>
</html>