<%@page import="com.rainsoft.core.utils.Utils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/frameSource.jsp"%>
<%pageContext.setAttribute("now", Utils.getServerTime("yyyy-MM-dd")); %>
<html>
<head>
<title>频次分析</title>
<script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx }/js/echarts/echarts.min.js"></script>
</head>
<body>
	<div id="distribution_mac">
		<div class="row  page-component-element">
			<form action="${ctx }/population/analyze/frequency/analyze" method="post" id="searchForm" class="form-inline">
				<input type="hidden" id="type" name="type" />
				<div class="col-xs-12" style="padding: 15px 0;">
					<div class="form-group">
						<div class="input-group">
							<label class="input-group-addon">IMSI</label>
							<input type="text" class="form-control" placeholder="请输入要分析的IMSI" id="imsi" name="imsi">
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<label class="input-group-addon">时间段</label>
							<input id="beginDate" name="beginDate" type="text" class="input-medium Wdate" value="${now }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" style="width: 130px">
					    	<span class="input-group-addon">到</span>
					    	<input id="endDate" name="endDate" type="text" class="input-medium Wdate" value="${now }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'%y-%M-%d'})" style="width: 130px">
						</div>
					</div>
					<div class="form-group">
						<button type="button" class="btn btn-primary btn-md" id="timeBtn">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							时间规律分析
						</button>
						<button type="button" class="btn btn-primary btn-md" id="placeBtn">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							地点规律分析
						</button>
					</div>
				</div>
			</form>
		</div>
		
		<div class="row  page-component-element">
			<div class="col-xs-12 tab-content" id="resultWarp" style="min-height: 600px; width: 100%">
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function () {
	$("#timeBtn").click(function(){
		$("#type").val("time");
		query();
	});
	$("#placeBtn").click(function(){
		$("#type").val("place");
		query();
	});
});
function query() {
	if($("#imsi").val() == ""){
		layer.msg("请输入imsi");
		$("#imsi").focus();
		return false;
	}
	if($("#beginDate").val() == ""){
		layer.msg("请选择分析开始日期");
		$("#beginDate").focus();
		return false;
	}
	if($("#endDate").val() == ""){
		layer.msg("请选择分析结束日期");
		$("#endDate").focus();
		return false;
	}
	showLoading();
	var type = $("#type").val();
	$("#searchForm").ajaxSubmit(function(data){
		closeLoading();
		$("#resultWarp").html("");
		console.log("len:"+data.length);
		if (data == null || data.length <= 0) {
			layer.alert("没有数据");
		} else {
			if (type == "time") {
				showTimeAnalyze(data);
			} else {
				showPlaceAnalyz(data);
			}
		}
	});
}
/**
 * 时间频次分析结果
 */
function showTimeAnalyze(data) {
	var values = new Array(), labels = new Array();
	$.each(data, function (i, item) {
		values.push(item.count);
		labels.push(item.hour+"点");
	});
	show("时间规律分析结果", values, labels, 'line', 'horizontal', 0);
}
/**
 * 地点频次分析结果
 */
function showPlaceAnalyz(data) {
	var values = new Array(), labels = new Array();
	$.each(data, function (i, item) {
		values.push(item.count);
		labels.push(item.serviceName+"\n ("+item.machineName+")");
	});
	show("地点规律分析结果", values, labels, 'bar', 'horizontal',0);//vertical
}
/**
 * 显示结果
 */
function show(title, values, labels, type, direction, labelRotate) {
	var option = {
		    title : {
		        text: title
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['出现次数']
		    },
		    calculable : true,
		    xAxis : [],
		    yAxis : [],
		    series : [
		        {
		            name:'出现次数',
		            type: type,
		            data:values
		        }
		    ]
		};
	if (direction == 'horizontal') {
		option.xAxis.push({
            type : 'category',
            boundaryGap : type == 'line'? false : true,
            data : labels,
            axisLabel : {
            	interval: 0,
            	rotate: labelRotate
            }
        });
		option.yAxis.push({
            type : 'value',
            axisLabel : {
                formatter: '{value} 次'
            }
        });
	} else {
		option.yAxis.push({
            type : 'category',
            boundaryGap : type == 'line'? false : true,
            data :labels,
            axisLabel : {
            	interval: 0,
            	rotate: labelRotate
            }
        });
		option.xAxis.push({
            type : 'value',
            axisLabel : {
                formatter: '{value} 次'
            }
        });
	}
	console.log(option);
	var myChart = echarts.init(document.getElementById('resultWarp'));
	// 为echarts对象加载数据 
    myChart.setOption(option);
}
</script>
</html>