<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
    <script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
    <link href="${ctx }/js/jquery-select2/3.4/select2.min.css" rel="stylesheet" />
    <script type="text/javascript" src="${ctx }/js/jquery-select2/3.4/select2.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>设备查询</title>
</head>
<body>
<div class="row">
    <form id="searchForm" class="form-inline" action="${ctx }/population/query/machineList" method="post"
          target="<%=request.getHeader("X-Requested-With") != null?"ajax":"_self" %>">
        <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
        <div class="col-xs-12" style="padding: 15px 0;">
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">捕获时间</span>
                    <input name="beginTime" class="input-medium Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', maxDate:'%y-%M-%d %H:%m'})"
                           value="${analysisMachine.beginTime}" id="beginTime" type="text" style="width: 140px">
                    <span class="input-group-addon">-</span>
                    <input name="endTime" class="input-medium Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', maxDate:'%y-%M-%d %H:%m'})"
                           value="${analysisMachine.endTime}" id="endTime" type="text" style="width: 140px">
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">采集设备</span>
                    <input type="text" name="machineId" id="machineId" value="${analysisMachine.machineId}" width="80px" >
                    <input id="machineName" name="machineName" type="text" hidden="hidden" value="${analysisMachine.machineName}" />
                </div>
            </div>
            <div class="form-group">
                <div class="form-group">
                    <div class="input-group">
                        <label class="input-group-addon">所属城区</label>
                        <sys:userarea value="${analysisMachine.areaCode}" name="areaCode"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <input id="dangerPerson" name="dangerPerson" value="1" type="checkbox" ${analysisMachine.dangerPerson == 1 ? "checked":"" }/>
                    <label for="dangerPerson">关注人群</label>
                    <input id="dangerArea" name="dangerArea" value="1" type="checkbox" ${analysisMachine.dangerArea == 1 ? "checked":"" }/>
					<label for="dangerArea">高危地区人群</label>
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
                            <a href="javascript:void(0)" name="machineMap" id="machineMap"><i class="glyphicon glyphicon-globe"
                                                                                              style=" color: #9d7854;">地图模式</i></a>
                        </div>
                    </div>
                </caption>
                <thead>
                <tr>
                    <th>序号</th>
                    <th>设备编号</th>
                    <th>设备名称</th>
                    <th>设备地址</th>
                    <th>总人数</th>
                    <th>关注人群</th>
                    <th>高危地区人群</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="item" varStatus="status">
                    <tr>
                        <td>${( page.pageNo - 1 ) * page.pageSize + status.index + 1 }</td>
                        <td>${item.machineId}</td>
                        <td>${item.machineName}</td>
                        <td>${item.address}</td>
                        <td>${item.timesCount}</td>
                        <td>${item.highriskCount}</td>
                        <td>${item.preventCount}</td>
                        <td>
                            <a href="${ctx}/population/query/crowdList?machineId=${item.machineId}&beginTime=${analysisMachine.beginTime}&endTime=${analysisMachine.endTime}"
                               onclick="return openPage(this.href,'人群查询')">明细</a>
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
    $(function () {
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
    	
        $('#searchForm').validate(validateSetting);
        $("#queryBtn").click(function () {
            if ($("#searchForm").valid()) {
                page(1);
            }
        });
        
        jQuery.validator.addMethod("beginTimeValidator", function(value, element) {  
		    return this.optional(element) || !validateDate($("#endTime").val(), value).flag;    
		}, "结束时间不能小于开始时间");
    });
    $('#machineMap').click(function () {
            var href = $.rainsoft.baseUrl() + "population/query/machineMapList";
            $.get(href, function (page) {
                $("#c").remove();
                $("#pageContainer").addClass("active in").html(page);
                $('#pageContainer').trigger('layoutChange', $.rainsoft.getPageContainerSize());
            });
        }
    )
	
    var validateSetting = {
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
              //  error.insertAfter(element);
                layer.tips(error[0].textContent, element, {
                    tips: [3, '#f10f00'],  tipsMore: true
                });
            }
         //   error.wrap('<font style="color:red;"/>');
        },
        messages: {
            beginTime: {
                required: "请输入"
            },
            endTime: {
                required: "请输入"
            }
        }

    };
</script>
</body>
</html>