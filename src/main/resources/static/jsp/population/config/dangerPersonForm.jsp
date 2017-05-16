<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jsp/include/taglib.jsp"%>
<html lang="zh-CN">
<head>
	<title>高危人员管理</title>
	<%@ include file="/jsp/include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					if ($("input[name='groupId']:checked").length == 0) {
						layer.alert("请选择分组");
						return;
					}
					doSubmit(form);
				},
				rules:{
					imsi:{
                        required:true,
                        isImsiValidate:true,
                        remote: {
                            type: "post",
                            url: "${ctx}/population/config/danger/person/validateImsi",
                            data: {
                                username: function() {
                                    return $("#imsi").val();
                                },
								id:function(){
									return $("#id").val();
								}
                            },
                            dataType: "html",
                            dataFilter: function(data, type) {
                                if (data == "false")
                                    return true;
                                else
                                    return false;
                            }
                        }
                    
                    },
                    type:{
                        required:true
                    },
                    groupId:{
                        required:true
                    },
                    phone:{
                    	isPhoneValidate:true
                    },
                    idNo:{
                    	isIDNoValidate:true
                    }
                },
                messages: {
                	imsi: {
                		remote:"此imsi号已存在"
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
	<form:form id="inputForm" modelAttribute="dangerPerson" action="${ctx}/population/config/danger/person/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">IMSI号：</label>
			<div class="controls">
				<input type="text" name="imsi" id="imsi" value="${dangerPerson.imsi == '0' ? '' : dangerPerson.imsi  }" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<input type="text" name="phone" value="${dangerPerson.phone == '0' ? '' : dangerPerson.phone }" maxlength="15" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<input type="text" name="idNo" value="${dangerPerson.idNo == '0' ? '' : dangerPerson.idNo  }" maxlength="20" class="input-xlarge"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">关注类型：</label>
			<div class="controls">
				<sys:dict name="type" parentId="X1000" formType="radio" value="${empty dangerPerson.type ? 1 : dangerPerson.type }"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">所属分组：</label>
			<div class="controls">
				<c:set var="groupId" value=",${dangerPerson.groupId},"/>
				<c:forEach var="item" items="${groupList }">
				<c:set var="tmp_val" value=",${item.id},"/>
				<span><input name="groupId" type="checkbox" class="checkbox" value="${item.id }" ${fn:contains(groupId, tmp_val) ? "checked" : "" } />${item.name }</span>
				</c:forEach>
				<c:remove var="tmp_val"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">危险等级</label>
			<div class="controls">
				<select id="rank" name="rank">
					<option value="0" ${dangerPerson.rank == 0 ?"selected":"" }>普通</option>
					<option value="1" ${dangerPerson.rank == 1 ?"selected":"" }>1级</option>
					<option value="2" ${dangerPerson.rank == 2 ?"selected":"" }>2级</option>
					<option value="3" ${dangerPerson.rank == 3 ?"selected":"" }>3级</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注说明：</label>
			<div class="controls">
				<form:textarea path="brief" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form>
	</div>
</body>
</html>