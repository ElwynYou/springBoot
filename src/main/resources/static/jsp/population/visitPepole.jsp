<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
label {
	font-weight: normal;
	margin-right: 20px
}

input[type=checkbox] {
	right: 7px;
	position: relative;
	top: 2px
}

.ul_list li {
	list-style: none;
	float: left;
	padding: 10px 10px
}

.upOrDown {
	color: red
}

.magnifier {
	color: #68c8a0
}

.showTable div {
	border: 2px solid #68c8a0;
	height: 30px;
	width: 0;
	display: inline-block
}

.showTable span {
	position: relative;
	top: -10px;
	left: 10px;
	color: #68c8a0
}

.export {
	float: right;
	margin-right: 10px
}

.export span {
	color: #68c8a0
}
</style>
<%@ include file="/jsp/frameSource.jsp"%>
<link rel="stylesheet" href="${ctx }/js/layer/skin/layer.css" />
<script type="text/javascript" src="${ctx }/js/layer/layer.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>窜访人群</title>
</head>
<body>
	<div class="row page-component-element">
		<div id="tracelocation_control">
			<div class="col-md-12 col-sm-12" style="padding: 15px 0; width: 100%">
				<div class="col-md-3 col-sm-3">
					<div class="input-group input-medium  input-daterange"
						data-date-format="yyyyMM">
						<span class="input-group-addon">请选择查询月份</span> <input name="startTime"
							type="text" class="Wdate" class="form-control datetime-picker"
							id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" />
					</div>
				</div>
				<div class="col-md-2 col-sm-1">
					<div class="input-group">
						<button type="button" class="btn btn-default btn-md"
							onclick="selectChart()">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							查询
						</button>
						<button type="reset" class="btn reset-btn btn-md">
							<span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
							重置
						</button>
						<button id="but1" type="reset" class="btn reset-btn btn-md"
							onclick="showAndHide()">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							显示列表
						</button>
						<button style="display: none;" id="but2" type="reset"
							class="btn reset-btn btn-md" onclick="showAndHide()">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							关闭列表
						</button>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12" style="width: 100%">
				<div class="col-md-4 col-sm-5" style="padding: 15px 15px;">
					<ul class="ul_list">
						<li id="statisticsList">
							<!--  <ul>
                   <li>常住人口</li><li>500人</li><li><span class="glyphicon glyphicon-arrow-up upOrDown"></span></li><li><span class="glyphicon glyphicon-search magnifier"></span></li>
                   </ul>
                   <ul>
                   <li>工作人员</li><li>500人</li><li><span class="glyphicon glyphicon-arrow-up upOrDown"></span></li><li><span class="glyphicon glyphicon-search magnifier"></span></li>
                   </ul>
                   <ul>
                   <li>窜访人员</li><li>500人</li><li><span class="glyphicon glyphicon-arrow-down upOrDown"></span></li><li><span class="glyphicon glyphicon-search magnifier"></span></li>
                   </ul>
                   <ul>
                   <li>常访人员</li><li>500人</li><li><span class="glyphicon glyphicon-arrow-up upOrDown"></span></li><li><span class="glyphicon glyphicon-search magnifier"></span></li>
                   </ul>
                   <ul>
                   <li>其他人员</li><li>500人</li><li><span class="glyphicon glyphicon-arrow-up upOrDown"></span></li><li><span class="glyphicon glyphicon-search magnifier" ></span></li>
                   </ul> -->
						</li>
					</ul>
					<ul class="ul_list">
						<li id="tempList">
						</li>
					</ul>
				</div>
				<div class="col-md-4 col-sm-5" style="padding: 0px 15px;">
					<div id="main" style="width: 600px; height: 230px"></div>
				</div>
			</div>
		</div>
	</div>

	<div id="pageList" style="display: none;">
		<form id="searchForm">
			<div class="showTable">
				<div></div>
				<span>列表显示</span>
			</div>
			<div class="col-md-12 col-sm-12" style="padding: 15px 0; width: 100%">
				<div class="col-md-2 col-sm-3">
					<div class="input-group">
						<span class="input-group-addon">账号类型</span> <select
							class="form-control" name="accountType">
							<option value="">---选择---</option>
							<option value="1">IMSI</option>
							<option value="2">手机</option>
							<option value="3">身份证</option>
							<option value="4">MAC</option>
						</select>
					</div>
				</div>
				<div class="col-md-2 col-sm-2">
					<div class="input-group">
						<input type="text" class="form-control" name="accountNo">
					</div>
				</div>
				<div class="col-md-3 col-sm-3">
					<div class="input-group input-medium  input-daterange"
						data-date-format="yyyyMM">
						<span class="input-group-addon">请选择查询月份</span> <input name="startTime"
							type="text" class="Wdate" class="form-control datetime-picker"
							id="startTime1" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" />
					</div>
				</div>
				<div class="col-md-2 col-sm-3">
					<div class="input-group">
						<span class="input-group-addon">人口类型</span> <select
							class="form-control" name="type">
							<option value="">---选择---</option>
							<c:forEach items="${throngTypes}" var="throngType">
								<option value="${throngType.DICT_CODE}"><c:if
										test="${throngType.DICT_CODE == type}">selected</c:if>
									${throngType.DICT_NAME}
								</option>
							</c:forEach>
						</select>

					</div>
				</div>
				<div class="col-md-2 col-sm-1">
					<div class="input-group">
						<button type="submit" class="btn btn-default btn-md">
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
			<div class="col-md-12 col-sm-12" style="width: 100%">
				<div class="newExport">
					<a href="javascript:"> <img src="${ctx }/img/export.png">
						<span>导出</span>
					</a>
				</div>
			</div>
		</form>

		<div class="row  page-component-element">
			<div class="col-md-12  col-md-offset-0">
				<table class="table table-bordered table-striped" id="dataList">
					<!-- <caption class="export"></caption> -->
				</table>
			</div>
		</div>
	</div>
	<input hidden="hidden" id="submenu" value="${submenu}">
	<script src="${ctx }/js/echarts/echarts.min.js"></script>
	<script type="text/javascript">
		window.showAndHide = function() {
			var div = $("#pageList");
			if (div.css("display") === "none") {
				div.show();
				$("#but2").show();
				$("#but1").hide();
			} else {
				div.hide();
				$("#but1").show();
				$("#but2").hide();
			}
		}
	</script>
	<script type="text/javascript">
		$(function() {
			var myChart = echarts.init(document.getElementById('main'));
			var start = new Date();
			start.setDate(start.getDate());
			var end = new Date();
			$.date97({
				"dateFmt" : "yyyy-MM",
				"startVal" : start.format("YYYY-MM-DD"),
				"endVal" : end.format("YYYY-MM-DD")
			});
			
			
			//var configId = $("#submenu").val();
			//var statMonth = $("#startTime").val().replace("-", "");
			//var data = {
			//	configId : configId,
			//	statMonth : statMonth
			//};
			//visitPepoleChart(data);
			function visitPepoleChart(data) {
				var index = layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
				$.getJSON($.rainsoft.baseUrl() + "visitpepole/chart?v="+Math.random(), data, function(datas) {
						layer.close(index);
									if (datas.length == 0) {
										layer.msg("该查询月份内没有数据！");
										$("#statisticsList").html("");
										myChart.clear();
										return;
									}
									var html = "";
									var xAxis = [];
									var series = [];
									$.each(datas,function(i) {
										var n = 0, n1 = 0;
										if (this.name == '常住人群') {
											n = 1;
										} else if (this.name == '常访人群') {
											n1 = 1;
										}
										html+= "<li>"
														+ this.name
														+ "</li><li>"
														+ this.peopleNum
														+ "</li><li><span class='glyphicon glyphicon-arrow-up upOrDown'></span></li><li><span class='glyphicon glyphicon-search magnifier' onclick='getList("
														+ this.type
														+ ")'></span></li><li>新疆 喀什</li><li>"+n+"人</li><li>湖南 娄底</li><li>"+n1+"人</li></ul> <br>";
											xAxis.push(this.name);
											series.push(this.peopleNum);
									 });
									$("#statisticsList").html(html);
									setOption(xAxis, series);
									visitpepoleList(data);
								});
			}
			
			window.selectChart = function() {
				visitPepoleChart({
					configId : $("#submenu").val(),
					startTime : $("#startTime").val().replace("-", "")
				});
			}
			
		

	
			window.getList = function(type) {
				var dataVal = {};
				var div = $("#pageList").show();
				$("#but2").show();
				$("#but1").hide();
				dataVal.type = type;
				dataVal.configId = configId;
				dataVal.statMonth = statMonth;
				visitpepoleList(dataVal);
			}

			function setOption(xAxis, series) {
				 //xAxis.splice(0,xAxis.length);
		  	     //series.splice(0,series.length);
				option = {
					title : {
						text : '人员组成统计'
					},
					color : [ '#ec0707' ],
					tooltip : {
						trigger : 'axis',
						axisPointer : {
							type : 'shadow'
						}
					},
					grid : {
						left : '3%',
						right : '4%',
						bottom : '3%',
						containLabel : true
					},
					xAxis : [ {
						type : 'category',
						data : xAxis,
						axisTick : {
							alignWithLabel : true
						}
					} ],
					yAxis : [ {
						type : 'value'
					} ],
					series : [ {
						name : '直接访问',
						type : 'bar',
						barWidth : '60%',
						data : series
					} ]
				}
				myChart.setOption(option);
			}
			
			
			function visitpepoleList(data) {
				atable = $('#dataList').DataTable({
					serverSide : true,
					bDestory : true,
					destroy : true,
					processing : true,
					ajax : new DataTablesAjaxSetting({
						url : '<c:url value="/visitpepole/statistics"/>',
						type : 'POST',
						dataType : 'json',
						contentType : 'application/json',
						autoWidth : true
					}, $('#searchForm'), data),
					order : [ [ 3, 'desc' ] ],
					columnDefs : [ rownumColumn, {
						orderable : false,
						rderable : false,
						searchable : false,
						className : "padding-3px content-center",
						"targets" : 1,
						"title" : "账号",
						"data" : "accountNo",
					}, {
						className : "padding-3px content-center",
						"targets" : 2,
						"title" : "账号类型",
						"data" : "accountType",
						render : function(data) {
							if(data==1){
								return "IMSI"
							}
							if(data==2){
								return "手机"
							}
							if(data==3){
								return "身份证"
							}
							if(data==4){
								return "MAC"
							}
						}
					}, {
						orderable : false,
						rderable : false,
						searchable : false,
						className : "padding-3px content-center",
						"targets" : 3,
						"title" : "最后捕获时间",
						"data" : "lastTime",
						render : function(data) {
							return dateFormatYYYYMMDDHHMMSS(data);
						}
					}, {
						orderable : false,
						rderable : false,
						searchable : false,
						className : "padding-3px content-center",
						"targets" : 4,
						"title" : "捕获次数",
						"data" : "captureTimes"

					}, {
						orderable : false,
						rderable : false,
						searchable : false,
						className : "padding-3px content-center",
						"targets" : 5,
						"title" : "场所/设备名称",
						"data" : "serviceName"
					} ]

				});
			}

		    $("#searchForm").submit(function(e){
				e.preventDefault();
				atable.draw();
			});

			/*  function visitpepoleListBytype(data){
			   atable1 = $('#dataList').DataTable({
					serverSide : true,
					bDestory:true,
					destroy: true,
					processing : true,
					ajax : new DataTablesAjaxSetting({
						url : '<c:url value="/visitpepole/statistics/type"/>',
						type : 'POST',
						dataType : 'json',
						contentType : 'application/json',
						autoWidth: true
					},null,data),
				    order:[[3, 'desc']],
					columnDefs : [
						rownumColumn
						, {
							orderable:false,
							rderable:false,
				     	    searchable:false,
				     	    className:"padding-3px content-center",
				    	    "targets" : 1,
				    	    "title" : "账号",
							"data" : "accountNo",
				 		},{
				     	    className:"padding-3px content-center",
				    	    "targets" : 2,
				    	    "title" : "账号类型",
							"data" : "accountType"
				   		   
						},{
				 			orderable:false,
				 			rderable:false,
				     	    searchable:false,
				     	    className:"padding-3px content-center",
				    	    "targets" : 3,
				    	    "title" : "最后捕获时间",
							"data" : "lastTime",
							 render: function(data){
					   		    	return dateFormatYYYYMMDDHHMMSS(data);
								}
						},{
				 			orderable:false,
				 			rderable:false,
				     	    searchable:false,
				     	    className:"padding-3px content-center",
				    	    "targets" : 4,
				    	    "title" : "捕获次数",
							"data" : "captureTimes"
							
						},{
				 			orderable:false,
				 			rderable:false,
				     	    searchable:false,
				     	    className:"padding-3px content-center",
				    	    "targets" : 5,
				    	    "title" : "场所名称",
							"data" : "serviceName"
						}]

					});
			   } */

		});
	</script>
</body>
</html>