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
    <title>小区人群明细</title>
</head>
<body>
<div class="row " >
        <form id="searchForm" class="form-inline" action="${ctx }/population/crowd/communityDetail" target="<%=request.getHeader("X-Requested-With") != null?"ajax":"_self" %>">
       		<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
            <div class="col-xs-12" style="padding: 15px 0;">
                 <div class="form-group">
                    <div class="input-group">
                  		<span class="input-group-addon">人群类型:</span> 
                    	<select  id="peopleType" name="peopleType">
             			 <option value="" selected="selected">全部类型</option>
             			  <c:forEach items="${peopleTypes}" var="peopleType">
							<c:choose>
									<c:when test="${crowdDetail.peopleType == peopleType.key}">
										<option value="${peopleType.key}" selected="selected">${peopleType.value }</option> 
									</c:when>
									<c:otherwise>
										<option value="${peopleType.key}">${peopleType.value }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select>
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">IMSI:</span>
                        <input name="imsiCode" type="text" class="form-control" value="${crowdDetail.imsiCode }">
                    </div>
                </div>
                  <div class="form-group">
                    <div class="input-group input-medium  input-daterange"
                         data-date-format="yyyy-mm-dd">
                        <span class="input-group-addon">日期:</span>
                        <input name="queryTime" value="${crowdDetail.queryTime }" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"
                                                                            class="form-control datetime-picker"
                                                                            id="startTime" type="text">
                    </div>
                </div>
                  <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">归属地</span>
                        <sys:city value="${crowdDetail.city }" name="city"/>
                    </div>
                 </div>
               
                <div class="form-group">
                    <div class="input-group"> 
                    <c:set  var="highRiskChecked" />  
                    <c:choose>
					   <c:when test="${crowdDetail.highRisk == 1}">  
					   		<c:set  var="highRiskChecked" value="checked='checked'"/>  
					   </c:when>
					  </c:choose>
					  <c:set  var="preventChecked" />  
                      <c:choose>
					   <c:when test="${crowdDetail.prevent == 1}">  
					   		<c:set  var="preventChecked" value="checked='checked'"/>  
					   </c:when>
					  </c:choose>
                    
                        <input id="highRisk" name="highRisk"  value="${crowdDetail.highRisk}" ${highRiskChecked} type="checkbox"/>
                        <label for="highRisk">重点关注人群</label>
                        <input id="prevent" name="prevent" value="${crowdDetail.prevent}"  ${preventChecked } type="checkbox"/>
                        <label for="prevent">高危地区人群</label>
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
            <input hidden="hidden" id="serviceCode" name="serviceCode" value="${crowdDetail.serviceCode}">
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
							<th>人群类型</th>
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
								<td>${item.operators }</td>
								<td ${item.prevent == 1 ? "style='color:red'":"" }>${fns:areaName(item.phoneArea) }</td>
								<td>${item.count }</td>
								<td id="${item.imsiCode }peopleType">${fns:dictLabel('X1001',item.peopleType) }</td>
								<td ${item.highRisk == 1 ? "style='color:red'":"" }>${item.highRisk == 1 ? "是" : "否" }</td>
								
								<td><fmt:formatDate value="${item.firstTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><fmt:formatDate value="${item.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td >
				    				<a href="${ctx}/population/query/trace?imsiCode=${item.imsiCode }&startTime=${crowdDetail.queryTime} 00:00:00&endTime=${crowdDetail.queryTime} 23:59:59" onclick="return openPage(this.href,'查看${item.imsiCode }运动轨迹')">轨迹</a>
				    				<span id="${item.imsiCode }sign">
				    				<c:choose>
									   <c:when test="${item.peopleType eq 3 or item.peopleType eq 4}">  
									      |<a href='javascript:;' onclick='signCrowd("${item.imsiCode}","1")'>常住</a>
				    				      |<a href="javascript:;" onclick='signCrowd("${item.imsiCode}","2")'>暂住</a>   
									   </c:when>
									   <c:when test="${item.peopleType eq 2}">  
									      |<a href='javascript:;' onclick='signCrowd("${item.imsiCode}","1")'>常住</a>
									   </c:when>
									</c:choose>
									</span>
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
 
<script type="text/javascript">

function signCrowd(imsiCode,signType){
	showLoading();
	var url = "${ctx}/population/crowd/updateCommunitySignDetail?imsiCode="+imsiCode+"&signType=" + signType +"&serviceCode="+$("#serviceCode").val();
 	var peopleType ={1:"常住人群",2:"暂住人群",3:"闪现人群",4:"其他人群"};
	var astr = "|<a href='javascript:;' onclick='signCrowd("+imsiCode+","+1+")'>常住</a>";
	$.get(url, function(result){
			closeLoading();
			if (result == "0") {
				$("#"+imsiCode+"peopleType").text(Number(peopleType.signType));
				if(signType == 1){
					$("#"+imsiCode+"sign").html("");
				}else{
					$("#"+imsiCode+"sign").html(astr);
				}
			} else {
				layer.msg(result);
			} 
	 }); 
}

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
    $('#highRisk').click(function () {
        if($(this).val()==1){
            $(this).val(0);
        }else {
            $(this).val(1);
        }

    });
    $('#prevent').click(function () {
        if($(this).val()==1){
            $(this).val(0);
        }else {
            $(this).val(1);
        }
    });
</script>
</body>
</html>