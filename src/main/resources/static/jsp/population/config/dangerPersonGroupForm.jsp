<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jsp/include/taglib.jsp"%>
<html lang="zh-CN">
<head>
	<title>分组管理</title>
	<%@ include file="/jsp/include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					doSubmit(form);
				},
				rules:{
					name:{
                        required:true,
                        byteMaxLength:20,
                        remote: {
                            url: "${ctx}/population/config/dangerPersonGroup/checkName",
                            type: "post",
                            dataType: "json",
                            data: {
                                    name:function(){return $("#name").val();},
									id: function(){return $("#id").val();}
                            }
                        }
                    },
                    note:{
                    	byteMaxLength:70
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
                    name:{remote:jQuery.format("分组名已被使用")}
                }
			});
		});
	</script>
</head>
<body style="padding-top: 15px">
	<div class="row">
	<form:form id="inputForm" modelAttribute="dangerPersonGroup" action="${ctx}/population/config/dangerPersonGroup/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div class="control-group">
			<label class="control-label">分组名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注说明：</label>
			<div class="controls">
				<form:input path="note" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form>
	</div>
</body>
</html>