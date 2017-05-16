<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.map{height:620px;padding:0px 0px}
label{font-weight:normal;margin-right:20px}
input[type=checkbox]{right:7px;position:relative;top:2px}
.time_segmentation{width:80px;padding:0;float:left}
.time_segmentation .time{list-style:none;width:80px;padding-left:-25px}
.crowd_list{background:#F7F7F7;height:500px;}
</style>
<%@ include file="/jsp/frameSource.jsp"%>
<link rel="stylesheet" href="${ctx }/js/layer/skin/layer.css" />
<script type="text/javascript" src="${ctx }/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx }/js/spinner.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>人流量</title>
</head>
<body>
<div class="row page-component-element">
		<div id="tracelocation_control">
		<div class="col-md-12 col-sm-12" style="padding:15px 0; width: 100%">             
         <div class="col-md-3 col-sm-3">
                        <div class="input-group input-medium  input-daterange"
                             data-date-format="yyyy-mm-dd">
                            <span class="input-group-addon">活动时间段</span> <input id="startTime" name="start"
                                                                             class="form-control datetime-picker"
                                                                             id="startTime" type="text"> <span
                                class="input-group-addon">到</span> <input id="endTime" name="end"
                                                                          class="form-control datetime-picker"
                                                                          id="endTime" type="text">
                                                                          <input id ="submenu" name="submenu" type="hidden" value="${submenu}">
                        </div>
           </div>      
                       
         <div class="col-md-2 col-sm-1" style="margin-left:40px">
            <div class="input-group" >
				<button type="button" class="btn btn-default btn-md" onclick="select()">
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
	</div>
	<!-- 图表 -->
	   <div class="col-xs-12" >  
        	<div id="main" style="width:100%;height:100%"></div>  
      </div>
    </div>    
                                	   
 <script src="${ctx }/js/echarts/echarts.min.js"></script>             
<script>
$(document).ready(function(){
		$("#main").width($(window).width()-200);
		$("#main").height($(window).height()-250);
	        var start = new Date();
	    	  start.setDate(start.getDate() -(1/24));
	    	var end = new Date();
	    	 $.date97({"start": "startTime", "end":"endTime", "startVal": start, "endVal": end});
	    	    var configId = $("#submenu").val();
			    var startTime = $("#startTime").val();
			    var endTime = $("#endTime").val();
			    var data={configId: configId, startTime: startTime,endTime: endTime};
		        var xAxis = [];
   			    var series = [];
			    //getCrowdFlowrateChart(data); 
	    	 window.select = function(){ 
    		  	     xAxis.splice(0,xAxis.length);
    		  	     series.splice(0,series.length);
	    		     getOption();
	    		    var configId = $("#submenu").val();
				    var startTime = $("#startTime").val();
				    var endTime = $("#endTime").val();
				    var data={configId: configId, startTime: startTime,endTime: endTime};
	    	        getCrowdFlowrateChart(data);
	    	 }
	    	 
	    	 
	    function getCrowdFlowrateChart(data){
			    $.getJSON($.rainsoft.baseUrl()+"crowdFlowrate/chart",data,function(datas){
			    	if (datas.length == 0) {
			    		layer.msg("该查询时间段内没有数据!");
			    		return;
			    	}
				  	getChart(datas); 
			  })  
	    }	 
	 
	    
	    function getChart(datas) {	    
    		$.each(datas,function(index,obj){
    			console.log(obj)
    			 xAxis.push(this.startTime);
    		  	 series.push(this.peopleNum);
    		}); 
    		getOption();
 		}       
	   var myChart = echarts.init(document.getElementById('main'));   
	   function getOption(){
		   option = {
				    title: {
				        text: '流动人口数'
				    },
				    tooltip: {trigger: 'axis'},
				    xAxis:  {
				        type: 'category',
				        boundaryGap: false,
				        data: xAxis
				    },
				    yAxis: {
				        type: 'value',
				        axisLabel: {
				            formatter: '{value} 人'
				        }
				    },
				    series: [
				        {
				            name:'最多人口数',
				            type:'line',
				            data:series,
				            markPoint: {
				                data: [
				                    {type: 'max', name: '最大值'},
				                    {type: 'min', name: '最小值'}
				                ]
				            },
				            markLine: {
				                data: [
				                    {type: 'average', name: '平均值'}
				                ]
				            }
				        }
				    ]
				};
		   console.log(option);
		   myChart.setOption(option);
	   }
	});
	</script>
</body>
</html>