<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>供应商--邀请供应商--添加供应商</title>
    <meta name="decorator" content="default"/>
    <link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
    <script type="text/javascript">
        var validateForm;
        var isAbleSubmit;

        function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            isAbleSubmit = true;
            $("#inputForm input").blur();

            if (isAbleSubmit && validateForm.form()) {
                $("#inputForm").submit();
                return true;
            }
            return false;
        }

        function validateInfo() {
            $("#supplier_name").blur(function () {
                $("#lblsupplier_name").html(success_html);
                if ($("#supplier_name").val().replace(/\s/g, "") == "") {
                    $("#lblsupplier_name").html(error_html+" 请输入供应商名称");
                    isAbleSubmit = false;
                }
            });
            $("#contact").blur(function () {

                $("#lblcontact").html(success_html);
                var contact = $("#contact");
                if (contact == "") {
                    $("#lblcontact").html(error_html+"请输入联系人姓名");
                    isAbleSubmit = false;
                }
                if (reg_contact.test(contact) !== true) {
                    $("#lblcontact").html(error_html+"请输入2-4位中文字符");
                    isAbleSubmit = false;
                }

            });
            $("#mobile").blur(function () {
                $("#lblmobile").html(success_html);
                var mobile = $("#mobile").val();
                if (mobile == "") {
                    $("#lblmobile").html(error_html+"请输入手机号");
                    isAbleSubmit = false;
                } else if (!reg_mobile.test(mobile)) {
                    $("#lblmobile").html(error_html+"手机号码输入不正确");
                    isAbleSubmit = false;
                }
            });
            $("#email").blur(function () {
                $("#lblemail").html(success_html);
                var email = $("#email").val();
                if (email == "") {
                    $("#lblemail").html(error_html+"请输入邮箱");
                    isAbleSubmit = false;
                } else if (!reg_email.test(email)) {
                    $("#lblemail").html(error_html+"邮箱输入不正确");
                    isAbleSubmit = false;
                }
            });
        }

        $(document).ready(function () {
            validateForm = $("#inputForm").validate({
                submitHandler: function (form) {
                    var supplier_name = $("#supplier_name").val().replace(/\s/g, "");
                    $.ajax({
                        async: true,
                        cache: true,
                        url: "${ctx}/coreSupplierCtrl/invitation-gys-count?name=" + supplier_name ,
                        data: {},
                        type: "get",
                        dataType: "text",
                        success: function (data, status, xhr) {
                            var result = $.parseJSON(data);
                            if (result.succ) {
                                // layer.msg("供应商已经存在");
                                // time(thic, 60);
                                if(confirm("确定重复添加供应商?")){
                                    isAbleSubmit = true;
                                    loading('正在提交，请稍等...');
                                    form.submit();
                                }else{
                                    isAbleSubmit = false;
                                }
                            }
                        },
                        error: function (xhr, status, error) {
                            layer.msg(status);
                        }
                    });
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            validateInfo();
        });
    </script>
</head>
<body>
<form:form name="inputForm" id="inputForm" class="form-horizontal"
           action="${ctx}/supplierChildCtrl/hxqy-saveGys?supplierEnterpriseId.id=${fns:getUser().supplier.id}"
           accept-charset="UTF-8" method="post">
    <div class="popup_tian clear">
        <div class="popup_hang" style="width: 100%;">
            <span class="supplier_name">供应商名称：</span>
            <input type="text" class="text1" id="supplier_name" name="supplierChildId.name" value=""
                   placeholder="请输入供应商营业执照名称"/>
            <span class="tishi" id="lblsupplier_name"></span>
        </div>
        <div class="popup_hang" style="width: 100%;">
            <span class="supplier_name">负责人姓名：</span>
            <input type="text" class="text1" id="contact" name="supplierChildId.agencyName" value=""
                   placeholder="2-4位中文字符"/>
            <span class="tishi" id="lblcontact"></span>
        </div>
        <div class="popup_hang" style="width: 100%;">
            <span class="supplier_name">手机号：</span>
            <input type="text" class="text1" id="mobile" name="supplierChildId.agencyPhone" value=""
                   placeholder="请输入11位手机号"/>
            <span class="tishi" id="lblmobile"></span>
        </div>
        <div class="popup_hang" style="width: 100%;">
            <span class="supplier_name">邮箱：</span>
            <input type="text" class="text1" id="email" name="supplierChildId.agencyEmail" value=""
                   placeholder="类似：xx@xxxxxxxx.com"/>
            <span class="tishi" id="lblemail"></span>
        </div>
    </div>
</form:form>
</body>
</html>