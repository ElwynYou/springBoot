<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jsp/include/taglib.jsp"%>
<html lang="zh-CN">
<head>
	<title>重点防范地区管理</title>
	<%@ include file="/jsp/include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					doSubmit(form);
				},
				rules:{
					brief:{
                        required:true,
						byteMaxLength:20,
						remote: {
							url: "${ctx}/population/config/danger/area/checkName",
							type: "post",
							dataType: "json",
							data: {
								name:function(){return $("#brief").val();},
								id: function(){return $("#area").val();}
							}
						}
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
				} ,
				messages: {
					name:{remote:jQuery.format("名称已被使用")}
				}
			});
		});
	</script>
</head>
<body style="padding-top: 15px">
	<div class="row">
	<form:form id="inputForm" modelAttribute="dangerArea" action="${ctx}/population/config/danger/area/save" method="post" class="form-horizontal">
		<input type="hidden" id="id" name="id" value="${dangerArea.area}"/>
		<div class="control-group">
			<label class="control-label">区域：</label>
			<div class="controls">
				<sys:city name="area" value="${dangerArea.area}" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="brief" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
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