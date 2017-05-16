<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style type="text/css">
        label {
            font-weight: normal;
            margin-right: 20px
        }

        .urlAddress span {
            color: #e4d5ba;
            font-size: 14px;
            margin-right: 5px
        }

        input[type=checkbox] {
            right: 7px;
            position: relative;
            top: 2px
        }
    </style>
    <%@ include file="/jsp/frameSource.jsp" %>
    <link rel="stylesheet" href="${ctx }/js/layer/skin/layer.css"/>
    <script type="text/javascript" src="${ctx }/js/layer/layer.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>重点防范管理</title>
</head>
<body>
<form id="searchForm">
    <div class="row page-component-element">
        <div id="tracelocation_control">
            <div class="col-md-12 col-sm-12" style="padding:15px 0; width: 100%">
                <div class="col-md-3 col-sm-4">
                    <div class="input-group">
                        <span class="input-group-addon">重点防范名称</span>
                        <input type="text" id="epId" name="name" class="form-control" placeholder="重点防范名称">
                    </div>
                </div>
                <div class="col-md-2 col-sm-3">
                    <div class="input-group">
                        <span class="input-group-addon">状态</span>
                        <select name="status" class="form-control">
                            <option></option>
                            <option value="1">启用</option>
                            <option value="0">禁用</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-2 col-sm-1">
                    <div class="input-group">
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
            <div class="col-md-12 col-sm-12" style="padding:15px 15px; width: 100%;">
                <a href='javascript:void(0)' style="float:right;text-decoration:none" onclick="goToaddEmphasisprecaution();"
                   class="urlAddress"><span>+</span><span>重点防范</span></a>
            </div>
        </div>
    </div>
</form>
<div class="row  page-component-element">
    <div class="col-md-12  col-md-offset-0">
        <table class="table table-bordered table-striped" id="dataList">
            <caption class="export"></caption>
        </table>
    </div>
</div>

<script type="text/javascript">
    var atable;
    $(document).ready(function () {
        $('#serviceName').rainsoft().service();
        $('#nation').rainsoft().nation();
        var hasHandlesHtml = $.hasHandles();
        $.date97();
        atable = $('#dataList').DataTable({
            serverSide: true,
            processing: true,
            ajax: new DataTablesAjaxSetting({
                url: '<c:url value="/emphasisprecaution/list"/>',
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                autoWidth: true
            }, $('#searchForm')),
            order: [[3, 'desc']],
            columnDefs: [
                rownumColumn
                , {
                    orderable: false,
                    rderable: false,
                    searchable: false,
                    className: "padding-3px content-center",
                    "targets": 1,
                    "title": "特定人群名称",
                    "data": "name"

                }, {
                    className: "padding-3px content-center",
                    "targets": 2,
                    "title": "创建时间",
                    "data": "creatTime",
                    render: function (data) {
                        return dateFormatYYYYMMDDHHMM(data);
                    }
                }, {
                    orderable: false,
                    rderable: false,
                    searchable: false,
                    className: "padding-3px content-center",
                    "targets": 3,
                    "title": "区域设备数",
                    "data": "deviceNum"
                }, {
                    orderable: false,
                    rderable: false,
                    searchable: false,
                    className: "padding-3px content-center",
                    "targets": 4,
                    "title": "状态",
                    "data": "status",
                    render: function (data) {
                        if (data == 1) {
                            return "<span style='color:green;'>启用</span> "
                        } else {
                            return "<span style='color:red;'>禁用</span> "
                        }
                    }
                }
                , {
                    orderable: false,
                    searchable: false,
                    className: "padding-3px content-center",
                    "title": "操作",
                    "targets": 5,
                    render: function (data, type, row) {
                        var obj = {};
                        obj.configId = row.id;
                        obj.status = row.status;
                        if (row.status == 1) {
                            return "<a href='javascript:void(0);' onclick='updateStatus(" + JSON.stringify(row) + ")' >禁用</a>|" + hasHandlesHtml
                        } else {
                            return "<a href='javascript:void(0);' onclick='updateStatus(" + JSON.stringify(row) + ")' >启用</a>|" + hasHandlesHtml
                        }

                        /* return '<a href="javascript:del('+row.id+');" class="view">删除</a><span id = "id"></span>';	 */
                    }
                }
            ]


        });
        $("#searchForm").submit(function (e) {
            e.preventDefault();
            atable.draw();
        });


    });


    atable.on('click', 'td>a.del', function (e) {
        var rowdata = atable.row($(this).closest("tr")).data();//获取当前行数据

        layer.confirm('您确定删除该配置？', {
            btn: ['确定', '取消']
        }, function () {
            $.ajax({
                url: '<c:url value="/emphasisprecaution/delEmphasisConfig"/>',
                data: {"configId": rowdata.id},
                success: function (data) {
                    if (data.state == 1) {
                        layer.msg("删除成功");
                        atable.draw();
                    } else {
                        layer.msg("删除失败");
                        atable.draw();
                    }

                },
                error: function () {
                    layer.msg("删除失败");
                    atable.draw();
                }
            })
        }, function () {
            return;
        });
    })

    function goToaddEmphasisprecaution(data) {
        if(data!=null && data!=undefined){
            layer.open({
                type: 2,
                title: '修改重点防范',
                shadeClose: true,
                shade: 0.8,
                area: ['80%', '90%'],
                content: $.rainsoft.baseUrl() +'emphasisprecaution/addPage?configId='+data.id, //iframe的url
                end: function () {
                    atable.draw();
                }
            });
        }else {
            layer.open({
                type: 2,
                title: '添加重点防范',
                shadeClose: true,
                shade: 0.8,
                area: ['80%', '90%'],
                content: '<c:url value="/emphasisprecaution/addPage"/>', //iframe的url
                end: function () {
                    atable.draw();
                }
            });
        }
    }
    function updateStatus(row) {
        layer.confirm('您确定修改该状态？', {
            btn: ['确定', '取消']
        }, function () {
            $.post('<c:url value="/emphasisprecaution/updateStatus"/>', row, function (data) {
                if (data.state == 1) {
                    layer.msg("更新成功");
                    atable.draw();
                } else {
                    layer.msg("更新失败");
                    atable.draw();
                }
            })
        })
    }


    atable.on('click', 'td>a.edit', function (e) {
        var rowdata = atable.row($(this).closest("tr")).data();//获取当前行数据
        goToaddEmphasisprecaution(rowdata);

    })
</script>
</body>
</html>