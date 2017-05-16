<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.map{height:620px;padding:0px 0px}
label{font-weight:normal;margin-right:20px}
input[type=checkbox]{right:7px;position:relative;top:2px}

.someIndex ul{padding:0}
.someIndex ul li{ float:left; width:16%; margin:0 2% 20px 0; box-sizing:border-box; display:inline-block;}
.someIndex ul li a{position:relative;display:block; height:88px;}
.someIndexLi_l{ position:relative;width:100%; float:left; color:#fff;}
.someIndexLi_l h2{ margin:20px 0 5px 9px; font-size:20px;}
.someIndexLi_l p{ margin-left:46px; font-size:24px; font-weight:bold;}
.someIndexLi_r{ width:30%; background:rgba(0,0,0,0.1); text-align:center; vertical-align:middle; height:100px; display:table-cell;}
.someIndex ul li:nth-child(1){ background: rgb(69,137,148);}
.someIndex ul li:nth-child(2){ background: rgb(117,121,71);}
.someIndex ul li:nth-child(3){ background: rgb(114,83,52);}
.someIndex ul li:nth-child(4){ background: rgb(148,41,35);}
.someIndex ul li:nth-child(5){ background:rgb(85,170,173);}
.personimg{position:absolute;height:88px;width:70px;top:0;right:0}
#pageContainer .btn-default{text-shadow: 0 0 0 #fff;color:#fff}
.bgimg{position: absolute; 
  	width: 26%;
    height: 100%;
    top: 0;
    right: 0;
    text-align: center;
    line-height: 88px;
    font-size: 45px;
    background: rgba(0,0,0,0.1);}
</style>
<%@ include file="/jsp/frameSource.jsp"%>
<link rel="stylesheet" href="${ctx }/js/layer/skin/layer.css" />
<script type="text/javascript" src="${ctx }/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx }/js/echarts/echarts.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>小区人群分析</title>
</head>
<body>
<div class="row page-component-element">
		<div id="tracelocation_control">
		<form id="searchForm" class="form-inline" >
		<div class="col-md-12 col-sm-12" style="padding:15px 0; width: 100%">   
		
        <div class="form-group" style="float: left;">
			<div class="input-group" style="font-weight:bold;font-size:20px;margin-left:15px">
            	数据日期：${queryTime}
           </div>
           </div>       
        <div class="form-group" style="float: right;">
            <div class="input-group" >
				<button type="button" class="btn btn-default btn-md" id="submit">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<a style="color:#fff" href="${ctx}/population/crowd/communityHistory?serviceCode=${serviceCode}&serviceName=${serviceName}&beginTime=${queryTime}&overTime=${queryTime}" onclick="return openPage(this.href,'查看${serviceName}历史数据')">查看历史数据</a>
				</button>
			</div>
        </div> 
        </div> 
        <input hidden="hidden" id="serviceCode" value="${serviceCode}"/>  
        <input hidden="hidden" id="queryTime" value="${queryTime}"/> 
      </form>                                   
	</div>
	<div class="row  page-component-element">

			<div class="col-md-12  col-md-offset-0">
				<div class="someIndex">
					<ul>
						<li><a href="javascript:void(0)" onclick='detailQuery("${serviceCode}","${serviceName}","tempPeople")'>
								<div class="someIndexLi_l">
									<h2>暂住人群</h2>
									<p id="tempPeopleNum">0</p>
									<span class="glyphicon glyphicon-user bgimg"></span>
								</div>
						</a></li>
						<li><a href="javascript:void(0)" onclick='detailQuery("${serviceCode}","${serviceName}","highRisk")'>
								<div class="someIndexLi_l">
									<h2>关注人群</h2>
									<p id="dangerPersonCount">0</p>
									<span class="glyphicon glyphicon-eye-open bgimg"></span>
								</div>
						</a></li>
						<li><a href="javascript:void(0)" onclick='detailQuery("${serviceCode}","${serviceName}","prevent")'>
								<div class="someIndexLi_l">
									<h2>高危地区人群</h2>
									<p id="dangerAreaCount">0</p>
									<span class="glyphicon glyphicon-info-sign bgimg"></span>
								</div>
						</a></li>
						<li><a href="javascript:void(0)" onclick='detailQuery("${serviceCode}","${serviceName}","unmeant")'>
								<div class="someIndexLi_l">
									<h2>闪现人群</h2>
									<p id="unmeantNum">0</p>
									<span class="glyphicon glyphicon-question-sign bgimg"></span>
								</div>
						</a></li>
						<li><a href="javascript:void(0)" onclick='detailAbnormalQuery("${serviceCode}","${serviceName}")'>
								<div class="someIndexLi_l">
									<h2>异常常住人群</h2>
									<p id="abnormalCount">0</p>
									<span class="glyphicon glyphicon-eye-close bgimg"></span>
								</div>
						</a></li>
					</ul>
				</div>
			</div>

			<div class="col-md-12  col-md-offset-0">
          <div id="typeAnalyze" style="height:380px; width: 50%;float: left;"></div>
	   	  <div id="dangerAnalyze" style="height:380px; width: 50%;float: left;"></div>
    </div>
</div>                               	   
</div>     
        
<script type="text/javascript">
var typeAnalyzeChart; 
var dangerAnalyzeChart; 
$(document).ready(function() {
	$.date97();
 	typeAnalyzeChart = echarts.init(document.getElementById('typeAnalyze')); 
 	dangerAnalyzeChart = echarts.init(document.getElementById('dangerAnalyze')); 
	var data={serviceCode: $("#serviceCode").val(),queryTime:$("#queryTime").val()};
	loaderChert(data);
	
	$('#submit').click(function(){
		loaderChert({serviceCode: $("#serviceCode").val(),queryTime:$("#queryTime").val()});
	});
});

	function loaderChert(area) {
		 $.ajax(
                 {
                     url: $.rainsoft.baseUrl() + 'population/crowd/queryCommunityAnalyze',
                     type: "post",
                     data: JSON.stringify(area),
                     dataType: "json",
                     contentType: "application/json",
                     success: function (result) {
                    	 $("#dangerAreaCount").text(result.dangerAreaCount);
                    	 $("#tempPeopleNum").text(result.tempPeopleNum);
                    	 $("#dangerPersonCount").text(result.dangerPersonCount);
                    	 $("#unmeantNum").text(result.unmeantNum);
                    	 $("#abnormalCount").text(result.abnormalCount);
                    	// 为echarts对象加载数据 
         				typeAnalyzeChart.setOption(getTypeAnalyzeOption(result));
         				dangerAnalyzeChart.setOption(getDangerAnalyzeOption(result));
                     }
                 }
             );
	}
	
	
	
	function getTypeAnalyzeOption(result) {
		return {
			title : {
				text : '人群类型分析',
				subtext : '总人数:'+optionCrowd(result.personTypeMap).totalCount,
				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : optionCrowd(result.personTypeMap).legendDatas
			},

			calculable : true,
			series : [ {
				name : '云嗅检测来源',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : optionCrowd(result.personTypeMap).seriesDatas
			} ]
		};
	}
	//组装Option所需数据
	function optionCrowd(map){
		var ODangerCrowd={};
		var ALegendData =[];
		var ASeriesData =[];
		var ItotalCount = 0;
		$(map).each(function(i,val) { 
			
			ALegendData.push(val.key+':'+val.value+'人');
			ASeriesData.push({value : Number(val.value) ,name : val.key+':'+val.value+'人'});
			ItotalCount += Number(val.value);
		});
		ODangerCrowd.legendDatas=ALegendData;
		ODangerCrowd.seriesDatas=ASeriesData;
		ODangerCrowd.totalCount=ItotalCount;
		return ODangerCrowd;
	}
	
	function getDangerAnalyzeOption(result) {
		return {
			title : {
				text : '高危地区人群分析',
				subtext : '总人数:'+optionCrowd(result.dangerMap).totalCount,
				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'right',
				data : optionCrowd(result.dangerMap).legendDatas
			},

			calculable : true,
			series : [ {
				name : '云嗅检测来源',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data :optionCrowd(result.dangerMap).seriesDatas
			} ]
		};
	}
	
	function detailQuery(serviceCode,serviceName,crowdType) {
		var url = $.rainsoft.baseUrl() + 'population/crowd/communityDetail?serviceCode=' + serviceCode +'&serviceName=' + serviceName;
		url += '&queryTime=' + $("#queryTime").val();
		if(crowdType == 'highRisk'){
			url += '&highRisk=1';
		}
		if(crowdType == 'prevent'){
			url += '&prevent=1';
		}
		if(crowdType == 'tempPeople'){
			url += '&peopleType=2';
		}
		if(crowdType == 'unmeant'){
			url += '&peopleType=3';
		}
		openPage(url, serviceName);
	}
	function detailAbnormalQuery(serviceCode,serviceName,crowdType) {
		var url = $.rainsoft.baseUrl() + 'population/crowd/communityAbnormalDetail?serviceCode=' + serviceCode +'&serviceName=' + serviceName;
		if(crowdType == 'highRisk'){
			url += '&highRisk=1';
		}
		if(crowdType == 'prevent'){
			url += '&prevent=1';
		}
		openPage(url, serviceName);
	}
</script>
</body>
</html>