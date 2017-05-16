<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jsp/include/taglib.jsp"%>
<html lang="zh-CN">
<head>
	<title>报警策略管理</title>
	<%@ include file="/jsp/include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					if ($("input[name='arg_key']:checked").size() == 0) {
						layer.msg("请设置预警人数");
						return false;
					}
					var flag = false;
					var args  = "";
					$("input[name='arg_key']:checked").each(function(i) {
						var no = $(this).attr("no");
						var num = $("#arg_num_"+no).val();
						if (!/^[1-9]\d*/.test(num)) {
							flag = true;
							return;
						}
						if (i > 0) {
							args += ", ";
						}
						args += "'"+this.value+"':"+num;
					});
					if (flag) {
						layer.msg("预警人数无效，请输入大于0的数字");
						return false;
					}
					$("#args").val("{"+args+"}");
					doSubmit(form);
				},
				rules:{
					name:{
                        required:true
                    }
                },
				errorPlacement: function(error, element) {
					layer.msg("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body style="padding-top: 15px">
	<div class="row">
	<form:form id="inputForm" modelAttribute="alarmStrategy" action="${ctx}/population/config/alarmStrategy/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div class="control-group">
			<label class="control-label">策略名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生效日期：</label>
			<div class="controls">
				<input name="startDate" value="${alarmStrategy.startDate }" maxlength="10" class="input-medium Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" style="width: 130px" placeholder="不选择立即生效"/>
				 - 
				<input name="endDate" value="${alarmStrategy.endDate }" maxlength="10" class="input-medium Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" style="width: 130px" placeholder="不选择永久有效"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">监控时段：</label>
			<div class="controls">
				<select id="startTime" name="startTime" class="input-xlarge required" style="width: 100px">
					<c:forEach var="item" items="${hours }">
						<c:set var="val" value="${item }:00"></c:set>
						<option value="${val }" ${alarmStrategy.startTime==val ? 'selected':''}>${val }</option>
					</c:forEach>
				</select>
				 - 
				 <select id="endTime" name="endTime" class="input-xlarge required" style="width: 100px">
					<c:forEach var="item" items="${hours }" varStatus="status">
						<c:set var="val" value="${hours[23-status.index] }:59"></c:set>
						<option value="${val }" ${alarmStrategy.endTime==val ? 'selected':''}>${val }</option>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<input type="hidden" id="args" name="args" value="${alarmStrategy.args }" />
			<label class="control-label">预警人数：</label>
			<div class="controls" style="line-height: 35px">
				<c:forEach var="item" items="${alarmStrategy.argList }" varStatus="status">
				<input type="checkbox" value="${item.key }" id="arg_key_${status.index }" name="arg_key" no="${status.index }" ${item.valid?'checked':'' }>
				${item.label }&nbsp;&nbsp;<input type="text" id="arg_num_${status.index }" value="${item.num }" placeholder="请输入人数" style="width: 100px">&nbsp;&nbsp;人及以上
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