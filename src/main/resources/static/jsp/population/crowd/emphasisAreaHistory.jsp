<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.map{height:620px;padding:0px 0px}
label{font-weight:normal;margin-right:20px}
input[type=checkbox]{right:7px;position:relative;top:2px}
.someIndex ul li{ float:left; width:19%; margin:0 4% 20px 0; box-sizing:border-box; display:inline-block;}
.someIndex ul li a{position:relative;display:block; height:88px;}
.someIndexLi_l{ width:100%; float:left; color:#fff;}
.someIndexLi_l h2{ margin:20px 0 5px 30px; font-size:18px;}
.someIndexLi_l p{ margin-left:30px; font-size:24px; font-weight:bold;}
.someIndexLi_r{ width:30%; background:rgba(0,0,0,0.1); text-align:center; vertical-align:middle; height:100px; display:table-cell;}
.someIndex ul li:nth-child(1){ background:#fec006;}
.someIndex ul li:nth-child(2){ background:#e81d62;}
.someIndex ul li:nth-child(3){ background:#8ac249;}
.someIndex ul li:nth-child(4){ background:#0698fe;}
.personimg{position:absolute;height:88px;width:79px;top:0;right:0}
.tablepad{margin-top: 20px;padding: 0 70px;}
</style>
<%@ include file="/jsp/frameSource.jsp"%>
<script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx }/js/echarts/echarts.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>热力分布历史</title>
</head>
<body>
<div class="row page-component-element">
		<div id="tracelocation_control">
		<form id="searchForm" class="form-inline" action="${ctx}/population/crowd/emphasisAreaHistory" target="<%=request.getHeader("X-Requested-With") != null?"ajax":"_self" %>">
		<div class="col-md-12 col-sm-12" style="padding:15px 0; width: 100%">   
		 <div class="form-group">
            <div class="input-group" >
				<button type="button" id="dayStatistics" class="btn btn-md" onclick="shortcutSelect('day')">日统计</button>
				<button type="button" id="monthStatistics" class="btn btn-md" onclick="shortcutSelect('month')">月统计</button>	
				<button type="button" id="yearStatistics" class="btn btn-md" onclick="shortcutSelect('year')">年统计</button>
			</div>
        </div>
        <div class="form-group">
			<div class="input-group">
				<label class="input-group-addon" id="queryTimeText">选择日期:</label>
				<input name="queryTime" value="${emphasisAreaHistory.queryTime}" class="form-control datetime-picker" id="startTime" type="text" > 
			</div>
		</div>
        </div>
        <input hidden="hidden" id="configId" name="configId" value="${emphasisAreaHistory.configId}"/>  
        <input hidden="hidden" id="configName" name="configName" value="${emphasisAreaHistory.configName}"/>  
        <input hidden="hidden" id="queryTimeType" name="queryTimeType" value="${emphasisAreaHistory.queryTimeType}"/> 
      </form>                                   
	</div>
	<div class="row  page-component-element">
	 <div class="col-md-12">
          <div id="chartShow" style="height:400px; width: 100%;"></div>
    </div>
    <div class="col-md-12  tablepad">
			<table class="table table-bordered table-striped" id="dataList">
            <thead>
						<tr>
							<th id="queryDateText">日期</th>
							<th>人流量</th>
							<th>关注人群</th>
							<th>高危地区人群</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${emphasisAreaHistoryList}" var="item" varStatus="status" >
							<tr>
							<th>${item.simpStatDate}</th>
							<th><a href="javascript:;" onclick='detailQuery("${emphasisAreaHistory.configId}","${emphasisAreaHistory.configName}","${item.statDate}")'>${item.totalPeople }</a></th>
							<th><a href="javascript:;" onclick='detailQuery("${emphasisAreaHistory.configId}","${emphasisAreaHistory.configName}","${item.statDate}","highRisk")'>${item.attentionPeople }</a></th>
							<th><a href="javascript:;" onclick='detailQuery("${emphasisAreaHistory.configId}","${emphasisAreaHistory.configName}","${item.statDate}","prevent")'>${item.dangerPeople }</a></th>
							<th><a href="javascript:;" onclick='detailQuery("${emphasisAreaHistory.configId}","${emphasisAreaHistory.configName}","${item.statDate}")'>明细</a></th>
							</tr>
						</c:forEach>
					</tbody>         
        </table>
	</div>
</div>                               	   
</div>     
        
<script type="text/javascript">
//快捷查询
function shortcutSelect(type){
	updateDateInputEvent(type);
	if(type == "day"){
		$("#startTime").val(new Date().format("YYYY-MM-DD"));
	}
	if(type == "month"){
		$("#startTime").val(new Date().format("YYYY-MM"));
	}
	if(type == "year"){
		$("#startTime").val(new Date().format("YYYY"));
	}
	$("#queryTimeType").val(type);
	page(1);
}

function updateDateInputEvent(type){
	 $("#startTime").unbind("focus"); //移除
		if(type == "day"){
		  $("#startTime").bind("focus",function(){
			  WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',onpicked:function(){page(1)}})
		  });
		  $("#dayStatistics").addClass("btn-default");
		  $("#queryTimeText").text("选择日期");
		  $("#queryDateText").text("小时");
		}
		if(type == "month"){
			 $("#startTime").bind("focus",function(){
				  WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M',isShowToday: false, isShowClear: false,onpicked:function(){page(1)}})
		  });
		 $("#monthStatistics").addClass("btn-default");
		 $("#queryTimeText").text("选择月份");
		 $("#queryDateText").text("日期");
		}
		if(type == "year"){
			 $("#startTime").bind("focus",function(){
				  WdatePicker({dateFmt:'yyyy',maxDate:'%y',isShowToday: false, isShowClear: false,onpicked:function(){page(1)}})
		  });
		 $("#yearStatistics").addClass("btn-default");
		 $("#queryTimeText").text("选择年限");
		 $("#queryDateText").text("月份");
		}
		$("#queryTimeType").val(type);
}

var chartShow; 
$(document).ready(function() {
	$.date97();
	$("#pageSizeSelect").change(function(){
		$("#pageSize").val(this.value);
		doQuery("#searchForm");
	});
	updateDateInputEvent($("#queryTimeType").val());
	
	
	chartShow = echarts.init(document.getElementById('chartShow')); 
	initChart();
	function initChart(){
		var AHistory=[];
		<c:forEach items="${emphasisAreaHistoryList}" var="history" >
			
		AHistory.push({simpStatDate:"${history.simpStatDate}",totalPeople:"${history.totalPeople}"});
		
		</c:forEach> 
		chartShow.setOption(getLineChartOption(AHistory));
	}
});
	//组装折线图Option所需数据
	function assembleLineChartOption(map){
		var OPieOption={};
		var ALegendData =["人流量"];
		var AAxisData = [];
		var ASeriesData =[];
		var OTotalCounts = getParamObject("人流量");
		$.each(map, function (index, obj) {
            AAxisData.push(obj.simpStatDate);
            OTotalCounts.data.push(Number(obj.totalPeople));
        });
		ASeriesData.push(OTotalCounts);
		OPieOption.legendData = ALegendData;
		OPieOption.axisData = AAxisData;
		OPieOption.seriesData = ASeriesData;
		return OPieOption;
	}
	
	function getParamObject(namey){
		return {name:namey,type:'bar',label: {normal:{show:true,position:'top'}},areaStyle:{normal:{}},data:[]}
	}
	function getDateText(){
		var type = $("#queryTimeType").val();
		var dateText = "";
		if(type == "day"){
			dateText = "天";
		}
		if(type == "month"){
			dateText = "月";
		}
		if(type == "year"){
			dateText = "年";
		}
		return $("#configName").val() + $("#startTime").val() + dateText;
	}
	function getLineChartOption(result) {
		var optionParam = assembleLineChartOption(result);
		return {
			    title: {
			        text: getDateText() + ' 人流量统计'
			    },
			    tooltip: {
			        trigger: 'axis'
			    },
			    legend: {
			        data: optionParam.legendData
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    toolbox: {
			        feature: {
			          show:true,
		        	  magicType:{
		                  type:['bar','line']
		              }
			        }
			    },
			    xAxis: {
			        type: 'category',
			        boundaryGap: true,
			        data: optionParam.axisData
			    },
			    yAxis: {
			        type: 'value',
		        	 axisLabel: {
		                 formatter: '{value}人'
		             },
		              minInterval: 1
			    },
			    series: optionParam.seriesData
			};
	}
	var ODetailUrl = {"day":"emphasisAreaDetailByDay","month":"emphasisAreaDetailByMonth","year":"emphasisAreaDetailByYear"};
	function detailQuery(configId,configName,queryTime,crowdType) {
		var url = $.rainsoft.baseUrl() + 'population/crowd/'+ ODetailUrl[$("#queryTimeType").val()] +'?configId=' + configId +'&configName=' + configName;
		url += '&queryTime=' + queryTime;
		url += '&limitingTime=' + $("#startTime").val();
		if(crowdType == 'highRisk'){
			url += '&highRisk=1';
		}
		if(crowdType == 'prevent'){
			url += '&prevent=1';
		}
		openPage(url, configName);
	}
</script>
</body>
</html>