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
</style>
<%@ include file="/jsp/frameSource.jsp"%>
<script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx }/js/echarts/echarts.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>小区人群分析</title>
</head>
<body>
<div class="row page-component-element">
		<div id="tracelocation_control">
		<form id="searchForm" class="form-inline" action="${ctx}/population/crowd/communityHistory" target="<%=request.getHeader("X-Requested-With") != null?"ajax":"_self" %>">
		<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
		<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
		<div class="col-md-12 col-sm-12" style="padding:15px 0; width: 100%">   
		 <div class="form-group">
            <div class="input-group" >
				<button type="button" class="btn btn-default btn-md" onclick="shortcutSelect(6)">最近7天</button>
				<button type="button" class="btn btn-default btn-md" onclick="shortcutSelect(29)">最近30天</button>	
			</div>
        </div> 
        <div class="form-group" style="margin-left: 15px">
                        <div class="input-group input-medium  input-daterange"
                             data-date-format="yyyy-mm-dd">
                             <input name="beginTime" value="${crowdHistory.beginTime }" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',minDate:'%y-%M-{%d-30}'})"
                                                                             class="form-control datetime-picker"
                                                                             id="startTime" type="text"> <span
                                class="input-group-addon">-</span> 
                                <input name="overTime"  value="${crowdHistory.overTime}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',minDate:'%y-%M-{%d-30}'})"
                                                                          class="form-control datetime-picker"
                                                                          id="endTime" type="text">
                        </div>
           </div>                  
        <div class="form-group">
            <div class="input-group" >
				<button type="button" class="btn btn-default btn-md" id="query">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					查询
				</button>
			</div>
        </div> 
        </div> 
        <input hidden="hidden" id="serviceCode" name="serviceCode" value="${crowdHistory.serviceCode}"/>  
          <input hidden="hidden" id="serviceName" name="serviceName" value="${crowdHistory.serviceName}"/>  
      </form>                                   
	</div>
	<div class="row  page-component-element">
	 <div class="col-md-12  col-md-offset-0">
          <div id="chartShow" style="height:400px; width: 100%;"></div>
    </div>
    <div class="col-md-12  col-md-offset-0">
			<table class="table table-bordered table-striped" id="dataList">
            <thead>
						<tr>
							<th>日期</th>
							<th>实有人口</th>
							<th>重点关注人员</th>
							<th>高危地区人群</th>
							<th>闪现人群</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${communityHistoryList}" var="communityHistory" varStatus="status" >
							<tr>
							<th>${communityHistory.captureTime}</th>
							<th><a href="javascript:;" onclick='detailQuery("${crowdHistory.serviceCode}","${crowdHistory.serviceName}","${communityHistory.captureTime}")'>${communityHistory.totalCount }</a></th>
							<th><a href="javascript:;" onclick='detailQuery("${crowdHistory.serviceCode}","${crowdHistory.serviceName}","${communityHistory.captureTime}","highRisk")'>${communityHistory.dangerPersonCount }</a></th>
							<th><a href="javascript:;" onclick='detailQuery("${crowdHistory.serviceCode}","${crowdHistory.serviceName}","${communityHistory.captureTime}","prevent")'>${communityHistory.dangerAreaCount }</a></th>
							<th><a href="javascript:;" onclick='detailQuery("${crowdHistory.serviceCode}","${crowdHistory.serviceName}","${communityHistory.captureTime}","peopleType")'>${communityHistory.unmeantNum }</a> </th>
							<th><a href="javascript:;" onclick='detailQuery("${crowdHistory.serviceCode}","${crowdHistory.serviceName}","${communityHistory.captureTime}")'>明细</a></th>
							</tr>
						</c:forEach>
					</tbody>         
        </table>
					
	</div>
</div>                               	   
</div>     
        
<script type="text/javascript">
//快捷查询
function shortcutSelect(day){
	updateDate(day);
	page(1);
}

function updateDate(day){
	var start = new Date();
	start.setDate(start.getDate() - Number(day));
	var end = new Date();
	$("#startTime").val(dateFormatYYYYMMDD(start));
	$("#endTime").val(dateFormatYYYYMMDD(end));
}

var chartShow; 
$(document).ready(function() {
	$.date97();
	$("#query").click(function(){
		page(1);
	});
	
	chartShow = echarts.init(document.getElementById('chartShow')); 
	initChart();
	function initChart(){
		var AcommunityHistory=[];
		<c:forEach items="${communityHistoryList}" var="communityHistory" >
			
		AcommunityHistory.push({captureTime:"${communityHistory.captureTime}",totalCount:"${communityHistory.totalCount}",dangerPersonCount:"${communityHistory.dangerPersonCount}"
			,dangerAreaCount:"${communityHistory.dangerAreaCount}",unmeantNum:"${communityHistory.unmeantNum}"});
		
		</c:forEach>
		if(AcommunityHistory.length != 0){
			if(AcommunityHistory.length == 1){
				chartShow.setOption(getPieOption(AcommunityHistory[0]));
			}else{
				chartShow.setOption(getLineChartOption(AcommunityHistory));
			}
		}else{
			$("#chartShow").html("<span>暂无数据</span>");
		}
	}
});
	
	function getPieOption(result) {
		return {
			title : {
				text : $("#serviceName").val() + '小区人口情况',
				subtext : '',
				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : assemblePieOption(result).legendDatas
			},

			calculable : true,
			series : [ {
				name : '云嗅检测来源',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : assemblePieOption(result).seriesDatas
			} ]
		};
	}
	
	//组装饼图Option所需数据
	function assemblePieOption(map){
		var OCommunityParam = {captureTime:"日期",totalCount:"实有人口",dangerPersonCount:"重点关注人员",dangerAreaCount:"高危地区人群",unmeantNum:"闪现人群"};
		var ODangerCrowd={};
		var ALegendData =[];
		var ASeriesData =[];
		var ItotalCount = 0;
		$.each(map,function(i,val) {
			if(i != "captureTime"){
			ALegendData.push(OCommunityParam[i]+':'+val+'人');
			ASeriesData.push({value : Number(val) ,name : OCommunityParam[i]+':'+val+'人'});
			ItotalCount += Number(val);
			}
		});
		ODangerCrowd.legendDatas=ALegendData;
		ODangerCrowd.seriesDatas=ASeriesData;
		ODangerCrowd.totalCount=ItotalCount;
		return ODangerCrowd;
	}
	
	//组装折线图Option所需数据
	function assembleLineChartOption(map){
		var OPieOption={};
		var ALegendData =["实有人口","重点关注人员","高危地区人群","闪现人群"];
		var AAxisData = [];
		var ASeriesData =[];
		var OTotalCounts = getParamObject("实有人口");
		var ODangerPersonCounts = getParamObject("重点关注人员");
		var ODangerAreaCounts = getParamObject("高危地区人群");
		var OUnmeantNums = getParamObject("闪现人群");
		$.each(map, function (index, obj) {
            AAxisData.push(obj.captureTime);
            OTotalCounts.data.push(Number(obj.totalCount));
            ODangerPersonCounts.data.push(Number(obj.dangerPersonCount));
            ODangerAreaCounts.data.push(Number(obj.dangerAreaCount));
            OUnmeantNums.data.push(Number(obj.unmeantNum));
        });
		ASeriesData.push(OTotalCounts);
		ASeriesData.push(ODangerPersonCounts);
		ASeriesData.push(ODangerAreaCounts);
		ASeriesData.push(OUnmeantNums);
		OPieOption.legendData = ALegendData;
		OPieOption.axisData = AAxisData;
		OPieOption.seriesData = ASeriesData;
		return OPieOption;
	}
	
	function getParamObject(namey){
		return {name:namey,type:'line',data:[]}
	}
	function getLineChartOption(result) {
		return {
			    title: {
			        text: $("#serviceName").val() + '小区人口情况'
			    },
			    tooltip: {
			        trigger: 'axis'
			    },
			    legend: {
			        data:assembleLineChartOption(result).legendData
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
		                  type:['bar','line','tiled','stack']
		              }
			        }
			    },
			    xAxis: {
			        type: 'category',
			        boundaryGap: false,
			        data: assembleLineChartOption(result).axisData
			    },
			    yAxis: {
			        type: 'value',
		        	 axisLabel: {
		                 formatter: '{value}人'
		             }
			    },
			    series: assembleLineChartOption(result).seriesData
			};
	}
	
	function detailQuery(serviceCode,serviceName,queryTime,crowdType) {
		var url = $.rainsoft.baseUrl() + 'population/crowd/communityDetail?serviceCode=' + serviceCode +'&serviceName=' + serviceName;
		url += '&queryTime=' + queryTime;
		if(crowdType == 'highRisk'){
			url += '&highRisk=1';
		}
		if(crowdType == 'prevent'){
			url += '&prevent=1';
		}
		if(crowdType == 'peopleType'){
			url += '&peopleType=3';
		}
		openPage(url, serviceName);
	}
</script>
</body>
</html>