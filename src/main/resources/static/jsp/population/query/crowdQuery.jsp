<%@ page language="java"  pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>人群查询</title>
    <%@ include file="/jsp/frameSource.jsp" %>
    <script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
	<script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
	<link href="${ctx }/js/jquery-select2/3.4/select2.min.css" rel="stylesheet" />
    <script type="text/javascript" src="${ctx }/js/jquery-select2/3.4/select2.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#machineId").select2({
  		  ajax: {
  			    url: "${ctx }/baseinfo/machineInfo/list",
  			    dataType: 'json',
  			    quietMillis: 250,
  			    data: function (term,page) {
  			      return {
  			    	keyword:term,
  			    	machineMathodType: 4
  			      };
  			    },
  			    results: function (data, params) {
  			    	if (data.length == 0) {
                  		return {results: []};
  			    	}
                      var itemList = [];
                      $.each(data, function(i, item){
                          itemList.push({id: item.machineId, text: item.machineName})
                      });
                      return {
                          results: itemList
                      };
  			    },
  			    cache: true
  			  },
  			  initSelection: function (element, callback) {
	                  var machineName = $("#machineName").val();
	                  var data = {text:machineName};
	                  callback(data);
  			  },
  			  placeholder: "请输入设备关键字",
  			  minimumInputLength: 2,
  			  multiple: false,
  			  allowClear: true,
  			  dropdownCssClass: "bigdrop",
  			  formatSelection: function(orgs){$("#machineName").val(orgs.text);return orgs.text}, 
                formatResult: function(orgs){return orgs.text}
  			}); 
		
		$.fn.serializeObject = function()    
		{    
		   var o = {};    
		   var a = this.serializeArray();    
		   $.each(a, function() {    
		       if (o[this.name]) {    
		           if (!o[this.name].push) {    
		               o[this.name] = [o[this.name]];    
		           }    
		           o[this.name].push(this.value || '');    
		       } else {    
		           o[this.name] = this.value || '';    
		       }    
		   });    
		   return o;    
		}; 
		
		$("#queryBtn").click(function(){
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
            },
		});
	 
		jQuery.validator.addMethod("beginTimeValidator", function(value, element) {  
		    return this.optional(element) || !validateDate($("#endTime").val(), value).flag;    
		}, "结束时间不能小于开始时间");
		
		$("[data-toggle='popover']").popover();
		$('#dataList').on('click', '.batchoptionbuttom button.cancel', function(){
			$(this).closest("div.popover").prev().popover('hide');
		});
		$('#dataList').on('click','#datatablehead_exportCurrentPage',function(){
			var title = $('.pagefunctionname').text()|| $('#mesageGrid>div[class*=content_]:visible .Public_t>h2>span').text();
			DownloadExcel(title.match(/\S+/)[0] + "导出.xls",$('#pageNo').val(),$('#pageSize').val());
			$(this).closest("div.popover").prev().popover('hide');
		});
		var totalCount = '${page.totalCount}';
		$('#dataList').on('click','#datatablehead_export1000',function(){
			var title = $('.pagefunctionname').text()|| $('#mesageGrid>div[class*=content_]:visible .Public_t>h2>span').text();
			if(Number(totalCount) > 6534){
				DownloadExcel(title+ "导出.csv");
			}else{
				DownloadExcel(title+ "导出.xls");
			}
			$(this).closest("div.popover").prev().popover('hide');
		});
		
		function DownloadExcel(fileName,draw,length){
			var requestdata = {'formData':{'exportExcelfileName':fileName,"crowdQuery":$('#searchForm').serializeObject()},'returnCount':totalCount,'draw':draw,'length':length,
					'columns':[{'data':'imsiCode','name':'IMSI'},
					           {'data':'operators','name':'运营商'},
					           {'data':'phoneArea','name':'归属地'},
					           {'data':'highrisk','name':'关注人群'},
					           {'data':'count','name':'出现次数'},
					           {'data':'firstTime','name':'首次出现时间'},
					           {'data':'lastTime','name':'最后出现时间'}
			                  ]};
			var dUrl = "${ctx }/population/query/crowdListDownload";
			$.rainsoft.submitJSON(dUrl, requestdata);
		}
	});
	</script>
</head>
<body>
	<div class="row">
        <form id="searchForm" class="form-inline" action="${ctx }/population/query/crowdList" method="post" target="<%=request.getHeader("X-Requested-With") != null?"ajax":"_self" %>">
        	<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
			<input type="hidden" name="orderBy" id="orderBy" value="${query.orderBy}"/>
			<div class="col-xs-12">
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon">捕获时间</span>
						<input name="beginTime" class="input-medium Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', maxDate:'%y-%M-%d %H:%m'})" value="${query.beginTime}"  id="beginTime" type="text" style="width: 140px">
						<span class="input-group-addon">-</span>
						<input name="endTime" class="input-medium Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', maxDate:'%y-%M-%d %H:%m'})"  value="${query.endTime}" id="endTime" type="text" style="width: 140px">
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon">IMSI</span>
						<input name="imsi" type="text" class="form-control" value="${query.imsi }">
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
						<label for="dangerPerson">关注人群</label>&nbsp;&nbsp;
						<input id="dangerArea" name="dangerArea" value="1" type="checkbox" ${query.dangerArea == 1 ? "checked":"" }/>
						<label for="dangerArea">高危地区人群</label>
					</div>
				</div>
			</div>
            <div class="col-xs-12" style="margin-bottom: 15px;">
                <div class="form-group">
                    <div class="form-group">
                        <div class="input-group">
                            <label class="input-group-addon">所属城区</label>
                            <sys:userarea value="${query.areaCode}" name="areaCode"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">采集设备</span>
                    	<input type="text" name="machineId" id="machineId" value="${query.machineId}" width="80px" >
                    	<input id="machineName" name="machineName" type="text" hidden="hidden" value="${query.machineName}" />
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <button id="queryBtn" type="button" class="btn btn-default btn-md">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                            查询
                        </button>
						<button type="button" class="btn reset-btn btn-md" onclick="clearForm()">
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
									<span class="input-group-addon">每页显示结果数</span><select id="pageSizeSelect" onchange="page(${page.pageNo})">
										<option value="10" ${page.pageSize == 10 ? 'selected':'' }>10条</option>
										<option value="20" ${page.pageSize == 20 ? 'selected':'' }>20条</option>
										<option value="50" ${page.pageSize == 50 ? 'selected':'' }>50条</option>
										<option value="100" ${page.pageSize == 100 ? 'selected':'' }>100条</option>
									</select>
								</div>
							</div>
							<div class="col-xs-7 datatablehead_righthead ">
								<div class="batchoptionbuttom" style="float:right">
							<c:if test="${page.totalCount > 0}">
							<a class="export" href="javascript:;"
							title="导出" 
							data-toggle="popover"
							data-html="true"
							data-placement="left" 
							data-content='<div ><p>
						<button class="btn btn-sm btn-danger confirm btn-block" id="datatablehead_exportCurrentPage" >导出当前页</button>
						<button class="btn btn-sm btn-danger btn-block" id="datatablehead_export1000">导出全部数据</button>
						<button class="btn btn-sm btn-primary cancel btn-block cancel">取消</button>
						</p></div>'
					><img src="${ctx}/img/index/datatablehead/export.png">
					<strong>导出</strong></a>
					</c:if>
					</div>
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
<!-- 							<th>高危地区人群</th> -->
							<th onclick="orderBy('${query.orderBy == 'count desc' ? 'count asc' : 'count desc'}')" style="cursor:pointer;">出现次数</th>
							<th onclick="orderBy('${query.orderBy == 'firstTime desc' ? 'firstTime asc' : 'firstTime desc'}')" style="cursor:pointer;">首次出现时间</th>
							<th onclick="orderBy('${query.orderBy == 'lastTime desc' ? 'lastTime asc' : 'lastTime desc'}')" style="cursor:pointer;">最后出现时间</th>
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
<%-- 								<td ${item.prevent == 1 ? "style='color:red'":"" }>${item.prevent == 1 ? "是" : "否" }</td> --%>
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
<script>
	function orderBy(data) {
		$('#orderBy').val(data);
        doQuery("#searchForm");
    }
</script>