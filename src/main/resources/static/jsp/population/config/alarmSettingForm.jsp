<%@page import="com.rainsoft.common.Consts"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
pageContext.setAttribute("housing", Consts.SERVICE_TYPE_HOUSING);
pageContext.setAttribute("emphasis", Consts.SERVICE_TYPE_EMPHASIS_AREA);
%>
<%@ include file="/jsp/include/taglib.jsp"%>
<html lang="zh-CN">
<head>
	<title>报警设置管理</title>
	<%@ include file="/jsp/include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					if ($("input[name='arg_key']:checked").size() == 0) {
						layer.msg("请设置报警方式");
						return false;
					}
					var flag = false;
					var args  = "";
					$("input[name='arg_key']:checked").each(function(i) {
						var no = $(this).attr("no");
						if (i > 0) {
							args += ", ";
						}
						args += "'"+this.value+"':{";
						$("input[name=arg_value_"+no+"]").each(function(j){
							if (flag) {
								return;
							}
							var _this = $(this);
							if (this.value == "") {
								layer.msg(_this.attr("placeholder"));
								this.focus();
								flag = true;
								return;
							}
							var regex = _this.attr("regex");
							if (regex != "" && !eval("/"+regex+"/").test(this.value)) {
								layer.msg(_this.attr("label")+"无效");
								this.focus();
								flag = true;
								return;
							}
							if (j > 0) {
								args += ", ";
							}
							args += "'"+_this.attr("key") +"':'"+ this.value+"'";
						});
						args +="}";
					});
					if (flag) {
						//layer.msg("报警方式信息输入不全");
						return false;
					}
					$("#remindType").val("{"+args+"}");
					doSubmit(form);
				},
				rules:{
					serviceCode:{
                        required:true
                    },
                    alarmId:{
                        required:true
                    },
                    receiver:{
                        required:true,
                    }
                },
				//errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					layer.msg("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			$("#areaCode").change(loadServiceInfo);
			$("input[name='serviceType']").click(loadServiceInfo);
		});
		function loadServiceInfo() {
			var type = $("input[name='serviceType']:checked").val();
			var area = $("#areaCode").val();
			showLoading();
			$.get("${ctx}/population/config/alarmSetting/serviceInfoList?v="+Math.random(), {serviceType: type, areaCode: area}, function(data){
				closeLoading();
				var html = '<option value="">-请选择-</option>';
				if (data.length > 0) {
					$.each(data, function (i, item) {
						html += '<option value="'+item.serviceCode+'">'+item.serviceName+'</option>';
					});
				}
				$("#serviceCode").html(html);
			});
		}
	</script>
</head>
<body style="padding-top: 15px">
	<div class="row">
	<form:form id="inputForm" modelAttribute="alarmSetting" action="${ctx}/population/config/alarmSetting/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div class="control-group">
			<label class="control-label">所属城区：</label>
			<div class="controls">
				<sys:userarea name="areaCode" value="${alarmSetting.areaCode }" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区域类型：</label>
			<div class="controls">
				<input type="radio" name="serviceType" value="${housing }" ${empty alarmSetting.serviceType or alarmSetting.serviceType==housing ? "checked":"" }> 小区
				&nbsp;&nbsp;
				<input type="radio" name="serviceType" value="${emphasis }" ${alarmSetting.serviceType==emphasis ? "checked":"" }> 重点区域
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">小区/重点区域：</label>
			<div class="controls">
				<select id="serviceCode" name="serviceCode" class="required">
					<option value="">-请选择-</option>
					<c:forEach items="${serviceList }" var="item">
						<option value="${item.serviceCode }" ${item.serviceCode == alarmSetting.serviceCode?"selected":"" } >${item.serviceName }</option>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报警策略：</label>
			<div class="controls">
				<select id="alarmId" name="alarmId" class="required">
					<option value="">-请选择-</option>
					<c:forEach items="${strategyList }" var="item">
						<option value="${item.id }" ${item.id == alarmSetting.alarmId?"selected":"" } >${item.name }</option>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接警人：</label>
			<div class="controls">
				<form:input path="receiver" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">报警方式：</label>
			<div class="controls" style="line-height: 35px">
				<form:hidden path="remindType" htmlEscape="false" maxlength="1000" class="input-xlarge required"/>
				<c:forEach var="item" items="${alarmSetting.argList }" varStatus="status">
				<input type="checkbox" value="${item.key }" name="arg_key" no="${status.index }" ${item.valid ? 'checked':'' }>
				${item.label }
					<c:forEach var="arg" items="${item.param }">
					&nbsp;&nbsp;<input type="text" name="arg_value_${status.index }" key="${arg.key }" value="${arg.value }" placeholder="请输入${arg.label }" regex="${arg.regex }" label="${arg.label }" style="width: 150px">
					</c:forEach>
					<c:if test="${!status.last}">
					   <br />
					</c:if>
				</c:forEach>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form>
	</div>
</body>
</html>