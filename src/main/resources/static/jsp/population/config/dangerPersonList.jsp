<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>高危人员管理</title>
    <%@ include file="/jsp/frameSource.jsp" %>
    <link rel="stylesheet" href="${ctx }/css/form.style.css"/>
    <script type="text/javascript" src="${ctx }/js/form.utils.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery.form.min.js"></script>
    <script type="text/javascript">
        $('#checkAll')[0].checked = false;
        $('#checkAll').click(function(cb){
            $('.audit_check').each(function(){
                this.checked = cb.toElement.checked;
            });
        });
        var index = 0;
        $(function () {
            $("#pageSizeSelect").change(function () {
                $("#pageSize").val(this.value);
                doQuery("#searchForm");
            });
            $("#query").click(function () {
                $("#pageNo").val(1);
                doQuery("#searchForm");
            });
        });
        function edit(href) {
            return toForm(href, 600, 470);
        }

        function uploadFile() {
            showLoading();
            $.ajaxFileUpload({
                type: "POST",
                url: "${ctx}/population/config/danger/person/uploadFile",
//                data: {file: $("#file")},//要传到后台的参数，没有可以不写
                secureuri: false,//是否启用安全提交，默认为false
                fileElementId: 'file',//文件选择框的id属性
                dataType: 'json',//服务器返回的格式
                async: false,
                success: function (data) {
                    closeLoading();
                    layer.alert(data.msg);
                    parent.$("body").attr("editForm", true);//成功后增加标识
                    layer.close(index);
                    resetInfo();
                    $("#query").click();
                },
                error: function (data, status, e) {
                    closeLoading();
                    layer.close(index);
                    layer.msg("上传失败: " + e);
                }
            });
        }

        function downloadModel() {
            var url = "${ctx}/population/config/danger/person/download";
            $("#download").attr("href", url);
            layer.close(index);
        }

        $("#btnImport").click(function () {
            index = layer.open({
                type: 1 //Page层类型
                ,
                area: ['400px', '180px']
                ,
                title: '导入关注人群'
                ,
                shade: 0.4 //遮罩透明度
                ,
                anim: 1 //0-6的动画形式，-1不开启
                ,
                content: '<div style="padding:20px;" id="upload_layer">' +
                '<input type="file" name= "myfile" id="file" value="选择文件"><br /><br /> ' +
                '<button type="button" onclick="uploadFile()" class="btn btn-default">开始导入</button>' +
                '&nbsp;&nbsp;<a id="download" onclick="downloadModel()">下载模板</a>' +
                '</div>'
            });
        });

        function query(imsi) {
            openWindow("${ctx}/population/query/trace?imsiCode=" + imsi, "通过IMSI查询" + imsi + "运动轨迹");
        }

        function resetInfo() {
            $("#name").val("");
            $("#imsi").val("");
            $("#phone").val("");
            $("#idNo").val("");
            $("#groupId").val("");
            $("#groupId  option:first").prop("selected", 'selected');
        }

        function getDangerPersonIds(){
            var $dataList = $("#dataList");
            var ids = "";
            $dataList.find('input[type=checkbox]:checked').each(function(){
                var $this = $(this);
                ids += $this.val() + ",";
            });
            layer.confirm("确认删除?",function(){
                showLoading();
                $.ajax({
                    url: "${ctx}/population/config/danger/person/batchDelete?ids="+ids,
                    success: function (resp) {
                        closeLoading();
                        if (resp == "0") {
                            layer.msg('操作成功');
                            $("#query").click();
                        } else {
                            layer.msg(resp);
                        }
                    },
                    error: function (e) {
                        closeLoading();
                        layer.msg("删除失败：" + e);
                    }
                });
            });
        }

        $.validator.addMethod("isIDNoValidate", function(value, element) {
            return this.optional(element) || isIDNo(value);
        }, "请输入正确的身份证号");

        $.validator.addMethod("isPhoneValidate", function(value, element) {
            return this.optional(element) || isPhone(value);
        }, "请输入正确的手机号");

        //$imsi验证
        $.validator.addMethod("isImsiValidate", function(value, element) {
            return this.optional(element) || isImsi(value);
        }, "请输入正确的imsi号");

        $(document).ready(function(){
            $("#searchForm").validate({
                rules:{
                    imsi:{
                        isImsiValidate:true
                    },
                    phone:{
                        isPhoneValidate:true
                    },
                    idNo:{
                        isIDNoValidate:true
                    }
                }
            });
        });
    </script>
</head>
<body>
<div class="row page-component-element">
    <form id="searchForm" class="form-inline" action="${ctx}/population/config/danger/person/list" method="post">
        <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>

        <div class="col-xs-12" style="padding: 15px 0;">
        
            <div class="form-group">
                <div class="input-group">
                    <label class="input-group-addon">关注类型</label>
                    <sys:dict name="type" parentId="X1000" formType="select" value="${dangerPerson.type}" emptyValue="0"
                              emptyLabel="全部"/>
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <label class="input-group-addon">姓名</label>
                    <input type="text" class="form-control" id="name" name="name" value="${dangerPerson.name}"
                           maxlength="50">
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <label class="input-group-addon">IMSI</label>
                    <input type="text" class="form-control" id="imsi" name="imsi" value="${dangerPerson.imsi}"
                           maxlength="50">
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <label class="input-group-addon">手机号</label>
                    <input type="text" class="form-control" id="phone" name="phone" value="${dangerPerson.phone}"
                           maxlength="50">
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <label class="input-group-addon">身份证号</label>
                    <input type="text" class="form-control" id="idNo" name="idNo" value="${dangerPerson.idNo}"
                           maxlength="50">
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <label class="input-group-addon">分组</label>
                    <select name="groupId" id="groupId">
                        <option value="">全部</option>
                        <c:forEach var="item" items="${groupList }">
                            <option value="${item.id }" ${item.id==dangerPerson.groupId ? "selected" : "" }>${item.name }</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <button id="query" type="button" class="btn btn-default btn-md">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        查询
                    </button>
                    <button onclick="resetInfo()" type="button" class="btn reset-btn btn-md">
                        <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                        重置
                    </button>
                </div>
            </div>
        </div>
    </form>

    <div class="row  page-component-element">
        <div class="col-xs-12">
            <table class="table table-striped table-bordered dataTable" id="dataList" style="table-layout:fixed">
                <caption>
                    <div class="row datatablehead_head">
                        <div class="col-xs-5 datatablehead_lefthead ">
                            <div class="input-group input-group-sm">
                                <span class="input-group-addon">每页显示结果数</span><select id="pageSizeSelect">
                                <option value="10" ${page.pageSize == 10 ? 'selected':'' }>10条</option>
                                <option value="20" ${page.pageSize == 20 ? 'selected':'' }>20条</option>
                                <option value="50" ${page.pageSize == 50 ? 'selected':'' }>50条</option>
                                <option value="100" ${page.pageSize == 100 ? 'selected':'' }>100条</option>
                            </select>
                            </div>
                        </div>
                        <div class="col-xs-7 datatablehead_righthead ">
                            <div class="batchoptionbuttom">
                                <a class="add" id="btnImport"><img src="${ctx }/img/index/datatablehead/add.png">
                                    <strong>导入</strong></a>
                                <a class="add" id="btnAdd" href="${ctx}/population/config/danger/person/form"
                                   onclick="return edit(this.href)"><img src="${ctx }/img/index/datatablehead/add.png">
                                    <strong>添加</strong></a>
                                <a class="add" id="btnDelete" href="#"
                                   onclick="getDangerPersonIds()/*;return ajaxDelete('确认要删除吗？', this)*/"><img src="${ctx }/img/index/datatablehead/delete.png">
                                    <strong>删除</strong></a>
                            </div>
                        </div>
                    </div>
                </caption>
                <thead>
                <tr>
                    <th>
                        <div style="text-align: center;"><label><input id="checkAll" type="checkbox"/></label></div>
                    </th>
                    <th>ID</th>
                    <th>姓名</th>
                    <th>IMSI</th>
                    <th>手机号</th>
                    <th>身份证号</th>
                    <th>关注类型</th>
                    <th>所属分组</th>
                    <th>备注说明</th>
                    <th>更新时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="dangerPerson" varStatus="status">
                    <tr>
                        <td>
                            <div style="text-align: center;"><input type="checkbox" class="audit_check" value="${dangerPerson.id}"></div>
                        </td>
                        <td>${( page.pageNo - 1 ) * page.pageSize + status.index + 1 }</td>
                        <td>${dangerPerson.name}</td>
                        <td>${dangerPerson.imsi == "0" ? "" : dangerPerson.imsi }</td>
                        <td>${dangerPerson.phone == "0" ? "" : dangerPerson.phone }</td>
                        <td>${dangerPerson.idNo == "0" ? "" : dangerPerson.idNo }</td>
                        <td>${fns:dictLabel('X1000',dangerPerson.type)}</td>
                        <td>${dangerPerson.groupName}</td>
                        <td>${dangerPerson.brief}</td>
                        <td><fmt:formatDate value="${dangerPerson.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <a onclick="query(${dangerPerson.imsi == "0" ? "" : dangerPerson.imsi })" href="#">轨迹</a>
                            <a href="${ctx}/population/config/danger/person/form?id=${dangerPerson.id}"
                               onclick="return edit(this.href)">修改</a>
                            <a href="${ctx}/population/config/danger/person/delete?id=${dangerPerson.id}"
                               onclick="return ajaxDelete('确认要删除该关注人员吗？', this)">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="row">
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
</body>
</html>