<%@ taglib prefix="sys" uri="/rs" %>
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
        .input-sm {
        	width: 300px;
        	padding: 0 5px;
        	margin: 0px;
        }
    </style>
    <%@ include file="/jsp/frameSource.jsp" %>
    <script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
    <link rel="stylesheet" href="${ctx }/js/jquery-select2/3.4/select2.min.css"/>
    <script type="text/javascript" src="${ctx }/js/jquery-select2/3.4/select2.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#queryBtn").click(function () {
                if ($('#searchForm').valid()) {
                    page(1);
                }
            });
            $(".chosen").select2();
        });
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>碰撞分析</title>
</head>
<body>
<div class="row">
    <form id="searchForm" class="form-inline" action="${ctx }/population/query/collision" method="post"
          target="<%=request.getHeader("X-Requested-With") != null?"ajax":"_self" %>">
        <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
        <div class="col-xs-12" style="padding: 15px 0;">
            <input name="flag" id="flag" type="hidden" value="-1">
            <div class="row form-group">
                    <div class="input-group">
                        <span class="input-group-addon">分析选项</span>
                        <div style="padding-left: 17px;">
                        <input id="dangerPerson" name="dangerPerson" value="1"
                               type="checkbox" ${machineCollisionQuery.dangerPerson == 1 ? "checked":"" }/>
                        <label for="dangerPerson">关注人群</label>
                        <input id="dangerArea" name="dangerArea" value="1" type="checkbox" ${machineCollisionQuery.dangerArea == 1 ? "checked":"" }/>
                        <label for="dangerArea">重点防范</label>
                        </div>
                    </div>
             </div>
            <div id="machineGroup" class="row form-group">
                <div class="col-xs-6 input-group">
                    <span class="input-group-addon">采集设备1</span>
                    <select name="machineId1" class="input-sm chosen">
                        <c:forEach items="${machineList}" var="machine">
                            <option value="${machine.machineId}" ${machine.machineId == machineCollisionQuery.machineId1 ? "selected" : ''} >${machine.machineId}-${machine.machineName}</option>
                        </c:forEach>
                    </select>
                    <span class="input-group-addon">采集时间</span>
                    <input class="input-medium Wdate required" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', maxDate:'%y-%M-%d %H:%m'})"
                           value="${machineCollisionQuery.beginTime1}" type="text"
                           id="beginTime1" name="beginTime1">
                    <span class="input-group-addon">到</span>
                    <input class="input-medium Wdate required" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', maxDate:'%y-%M-%d %H:%m'})"
                           value="${machineCollisionQuery.endTime1}" type="text" 
                           id="endTime1" name="endTime1">
                </div>
                <div class="col-xs-6 input-group">
                    <span class="input-group-addon">采集设备2</span>
                    <select name="machineId2" class="input-sm chosen">
                        <c:forEach items="${machineList}" var="machine">
                            <option value="${machine.machineId}" ${machine.machineId == machineCollisionQuery.machineId2 ? "selected" : ''}>${machine.machineId}-${machine.machineName}</option>
                        </c:forEach>
                    </select>
                    <span class="input-group-addon">采集时间</span>
                    <input class="input-medium Wdate required" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', maxDate:'%y-%M-%d %H:%m'})"
                           value="${machineCollisionQuery.beginTime2}" type="text" 
                           id="beginTime2" name="beginTime2">
                    <span class="input-group-addon">到</span>
                    <input class="input-medium Wdate required" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', maxDate:'%y-%M-%d %H:%m'})"
                           value="${machineCollisionQuery.endTime2}" type="text"
                           id="endTime2" name="endTime2">
                </div>
            </div>
            <div class="row form-group" style="margin-top: 10px">
                <div class="input-group">
                    <button type="button" onclick="addMachine()" class="btn btn-default btn-md">
                        <span class="glyphicon glyphicon-plus"></span>
                        添加设备
                    </button>
                    <button type="button" onclick="deleteMachine()" class="btn reset-btn btn-md">
                        <span class="glyphicon glyphicon-minus"></span>
                        删除设备
                    </button>
                </div>
            
            	<div class="input-group">
                    <button id="queryBtn" type="button" class="btn btn-default btn-md">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        查询
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
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="item" varStatus="status">
                    <tr>
                        <td>${( page.pageNo - 1 ) * page.pageSize + status.index + 1 }</td>
                        <td>${item.imsiCode}</td>
                        <td>${item.operators}</td>
                        <td ${item.prevent == 1 ? "style='color:red'":"" }>${fns:areaName(item.phoneArea) }</td>
                        <td ${item.highrisk == 1 ? "style='color:red'":"" }>${item.highrisk == 1 ? "是" : "否" }</td>
                        <td>
                            <a href="${ctx}/population/query/trace?imsiCode=${item.imsiCode}" onclick="return openPage(this.href,'轨迹查询')">轨迹</a>
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
	<div id="machineTemp" class="hidden">
			<div class="col-xs-6 input-group">
					<span class="input-group-addon">采集设备</span>
                    <select name="machineId">
                        <c:forEach items="${machineList}" var="machine">
                            <option value="${machine.machineId}">${machine.machineId}-${machine.machineName}</option>
                        </c:forEach>
                    </select>
                    <span class="input-group-addon">时间范围</span>
                    <input class="input-medium Wdate required" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', maxDate:'%y-%M-%d %H:%m'})" type="text" name="beginTime">
                    <span class="input-group-addon">到</span>
                    <input class="input-medium Wdate required" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', maxDate:'%y-%M-%d %H:%m'})" type="text" name="endTime">
			</div>
		</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#searchForm').validate({ 
            errorPlacement: function (error, element) {
                if (element.is(':radio') || element.is(':checkbox')) { //如果是radio或checkbox
                    error.appendTo(element.parent()); //将错误信息添加当前元素的父结点后面
                } else {
                    layer.tips(error[0].textContent, element, {
                        tips: [3, '#f10f00'],  tipsMore: true
                    });
                }
            }
		});
        <c:if test="${not empty machineCollisionQuery.machineId3}">
        var machineId3 = "${machineCollisionQuery.machineId3}";
        var beginTime3 = "${machineCollisionQuery.beginTime3}";
        var endTime3 = "${machineCollisionQuery.endTime3}";
        initMachine(machineId3, beginTime3, endTime3, 3);
        </c:if>
        <c:if test="${not empty machineCollisionQuery.machineId4}">
        var machineId4 = "${machineCollisionQuery.machineId4}";
        var beginTime4 = "${machineCollisionQuery.beginTime4}";
        var endTime4 = "${machineCollisionQuery.endTime4}";
        initMachine(machineId4, beginTime4, endTime4, 4);
        </c:if>
        <c:if test="${not empty machineCollisionQuery.machineId5}">
        var machineId5 = "${machineCollisionQuery.machineId5}";
        var beginTime5 = "${machineCollisionQuery.beginTime5}";
        var endTime5 = "${machineCollisionQuery.endTime5}";
        initMachine(machineId5, beginTime5, endTime5, 5)
        </c:if>
    });


    function traceQuery(row) {
        var href = $.rainsoft.baseUrl() + "population/query/trace?imsiCode=" + row.imsiCode;
        $.get(href, function (page) {
            $("#c").remove();
            $("#pageContainer").addClass("active in").html(page);
            $('#pageContainer').trigger('layoutChange', $.rainsoft.getPageContainerSize());
        });
    }


    function addMachine() {
        var $machineGroup = $('#machineGroup');
        var index = $machineGroup.children("div").length + 1;
        if (index > 5) {
            layer.msg("只能加5个设备", {icon: 0, time: 2000});
            return;
        }
        var $data = $("#machineTemp").children(':first').clone();
        var $input = $data.children('input');
        for (var i = 0; i < $input.length; i++) {
            var name = $input.eq(i).attr("name");
            $input.eq(i).attr("name", name + index);
        }
        var $select = $data.children('select');
        var selectName = $select.attr("name");
        $select.attr("name", selectName + index);
        $data.children(':first').html("采集设备" + index);
        $machineGroup.append($("<br>"));
        $machineGroup.append($data);
        $data.children('select').select2();
    }

    function deleteMachine() {
        var $machineGroup = $('#machineGroup');
        var index = $machineGroup.children("div").length;
        if (index == 2) {
            return;
        }
        $machineGroup.children(':last').remove();
        $machineGroup.children(':last').remove();

    }

    function initMachine(machineId, beginTime, endTime, index) {
        var $machineGroup = $('#machineGroup');
        var $data = $machineGroup.find(':first').clone();
        var $beginTime = $data.find('#beginTime1');
        var beginName = $beginTime.attr("name");
        $beginTime.attr("name", beginName.substring(0, (beginName.length - 1)) + index);
        $beginTime.val(beginTime);
        var $endTime = $data.find('#endTime1');
        var endTimeName = $endTime.attr("name");
        $endTime.attr("name", endTimeName.substring(0, (endTimeName.length - 1)) + index);
        $endTime.val(endTime);
        var $select = $data.children('select');
        var selectName = $select.attr("name");
        $select.attr("name", selectName.substring(0, (selectName.length - 1)) + index);
        $select.val(machineId);
        $data.children(':first').html("采集设备" + index);
        $machineGroup.append("<br>");
        $machineGroup.append($data);
    }
</script>
</body>
</html>