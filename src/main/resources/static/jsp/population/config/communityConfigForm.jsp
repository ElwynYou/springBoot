<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jsp/include/taglib.jsp"%>
<html lang="zh-CN">
<head>
<style type="text/css">
.numberInput>input{ width: 30px;}
.controls_a span{display: block;}
</style>
	<title>设置管理</title>
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
                    longCalcDays:{
                        required:true,
                        digits:true 
                    },
                    stayCalcDays:{
                        required:true,
                        digits:true 
                    },
                    newCalcDays:{
                        required:true,
                        digits:true 
                    },
                    longDisappearWarnDays:{
                        required:true,
                        digits:true 
                    },
                    longDisappearClearDays:{
                        required:true,
                        digits:true 
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
		});
		
	</script>
</head>
<body style="padding-top: 15px">
	<div class="row">
	<form:form id="inputForm" modelAttribute="communityConfig" action="${ctx}/population/config/communityConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div class="control-group">
			<label class="control-label">选择小区：</label>
			<div class="controls">
				<select id="serviceCode" name="serviceCode" class="required">
					<option value="">-请选择-</option>
					<c:forEach items="${serviceList }" var="item">
						<option value="${item.serviceCode }" ${item.serviceCode == communityConfig.serviceCode?"selected":"" } >${item.serviceName }</option>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人口类型：</label>
			<div class="controls numberInput" style="line-height: 35px">
					常住人群：&nbsp;&nbsp;连续出现&nbsp;&nbsp;<input type="text" name="longCalcDays"  value="${communityConfig.longCalcDays}"  >&nbsp;&nbsp;天及以上
				<span class="help-inline"><font color="red">*</font> </span><br>
					暂住人群：&nbsp;&nbsp;连续出现&nbsp;&nbsp;<input type="text" name="stayCalcDays"  value="${communityConfig.stayCalcDays}"  >&nbsp;&nbsp;天及以上
				<span class="help-inline"><font color="red">*</font> </span><br>
					闪现人群：&nbsp;&nbsp;连续出现&nbsp;&nbsp;<input type="text" name="newCalcDays"  value="${communityConfig.newCalcDays}"  >&nbsp;&nbsp;天及以上
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">常住人群异常：</label>
			<div class="controls numberInput" style="line-height: 35px">
					<input type="text" name="longDisappearWarnDays"   value="${communityConfig.longDisappearWarnDays}" >&nbsp;&nbsp;天未出现时提醒
				<span class="help-inline"><font color="red">*</font> </span><br>
					<input type="text" name="longDisappearClearDays"  value="${communityConfig.longDisappearClearDays}" >&nbsp;&nbsp;天未出现时移除
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息类型：</label>
			<div class="controls controls_a" style="line-height: 35px">
			<sys:dict name="msgTypes" parentId="X1002" formType="checkbox" value="${communityConfig.msgTypes }" styleClass="checkbox"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提醒方式：</label>
			<div class="controls" style="line-height: 35px">
				<form:hidden path="remindType" htmlEscape="false" maxlength="1000" class="input-xlarge required"/>
				<c:forEach var="item" items="${communityConfig.argList }" varStatus="status">
				<input type="checkbox" value="${item.key }" name="arg_key" no="${status.index }" ${item.valid ? 'checked':'' }>
				${item.label }
					<c:forEach var="arg" items="${item.param }" >
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