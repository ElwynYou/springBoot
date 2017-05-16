<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.map{height:620px;padding:0px 0px}
label{font-weight:normal;margin-right:20px}
input[type=checkbox]{right:7px;position:relative;top:2px}
</style>
<%@ include file="/jsp/frameSource.jsp"%>
<link rel="stylesheet" href="${ctx }/js/layer/skin/layer.css" />
<script type="text/javascript" src="${ctx }/js/layer/layer.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>人员列表</title>
</head>
<body>
<div class="row page-component-element">
		<div id="tracelocation_control">
		<form id="searchForm">
		<div class="col-md-12 col-sm-12" style="padding:15px 0; width: 100%">   
		<div class="col-md-2 col-sm-2">
                <div class="input-group">
                <span class="input-group-addon">IMSI</span>
                <input name="imsiCode" type="text" class="form-control"  >       
            </div>
           </div>   
           <!--  <div class="col-md-2 col-sm-2">
                <div class="input-group">
                <span class="input-group-addon">设备</span>
                <input type="text" class="form-control"  >       
            </div>
           </div>   -->        
         <div class="col-md-3 col-sm-3">
                        <div class="input-group input-medium  input-daterange"
                             data-date-format="yyyy-mm-dd">
                            <span class="input-group-addon">活动时间段</span> <input name="beginTime"
                                                                             class="form-control datetime-picker"
                                                                             id="startTime" type="text"> <span
                                class="input-group-addon">到</span> <input name="endTime"
                                                                          class="form-control datetime-picker"
                                                                          id="endTime" type="text">
                        </div>
           </div>                  
         <div class="col-md-2 col-sm-1" style="margin-left:40px">
            <div class="input-group" >
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
      <%--   <div class="col-md-12 col-sm-12" style=" width: 100%">  
         <div class="newExport">
            <a href="javascript:">
               <img src="${ctx }/img/export.png">
            <span>导出</span>
            </a>
         </div> 
      </div>  --%>     
      </form>                                   
	</div>
	<div class="row  page-component-element">
    <div class="col-md-12  col-md-offset-0">
        <table class="table table-bordered table-striped" id="dataList">
            <caption class="export"></caption>         
        </table>
    </div>
</div>                               	   
</div>     
<input hidden="hidden" id="submenu" value="${submenu}">           
<script type="text/javascript">
var atable;
$(document).ready(function() {
$.date97();
var configId = $("#submenu").val();
var data={configId: configId};
atable = $('#dataList').DataTable({
	serverSide : true,
	processing : true,
	ajax : new DataTablesAjaxSetting({
		url : '<c:url value="/crowdFlowrate/crowdList"/>',
		type : 'POST',
		dataType : 'json',
		contentType : 'application/json',
		autoWidth: true
	},$('#searchForm'),data),
    order:[[6, 'desc']],
	columnDefs : [
		rownumColumn
		, {
			orderable:false,
			rderable:false,
     	    searchable:false,
     	    className:"padding-3px content-center",
    	    "targets" : 1,
    	    "title" : "IMSI",
			"data" : "imsiCode"
			
 		},{
 			orderable:false,
     	    className:"padding-3px content-center",
    	    "targets" : 2,
    	    "title" : "运营商",
			"data" : "operatorName"
			
		},
		 {
 			orderable:false,
     	    className:"padding-3px content-center",
    	    "targets" : 3,
    	    "title" : "归属地",
			"data" : "localtion"
			
		},
		{
 			orderable:false,
     	    className:"padding-3px content-center",
    	    "targets" : 4,
    	    "title" : "手机号",
			"data" : "phone"
			
		},
		{
 			orderable:false,
 			rderable:false,
     	    searchable:false,
     	    className:"padding-3px content-center",
    	    "targets" : 5,
    	    "title" : "进入时间",
			"data" : "beginTime",
			render: function(data){
   		    	return dateFormatYYYYMMDDHHMMSS(data);
			}
		},{
 			orderable:false,
 			rderable:false,
     	    searchable:false,
     	    className:"padding-3px content-center",
    	    "targets" : 6,
    	    "title" : "离开时间",
			"data" : "endTime",
			render: function(data){
   		    	return dateFormatYYYYMMDDHHMMSS(data);
			}
		} ]

	});
$("#searchForm").submit(function(e){
	e.preventDefault();
	atable.draw();
});

});
</script>
</body>
</html>