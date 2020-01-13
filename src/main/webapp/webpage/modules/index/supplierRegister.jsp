<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>

<html>
<head>
    <title>供应商--注册</title>
    <meta name="decorator" content="default"/>
    <link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css"/>
    <script src="${ctxStatic}/online/lgt-web.js"></script>
</head>
<body>
<div class="header">
    <div class="header_con" style="width: 1150px;">
        <div class="logo1">
            <span style="border-left: 0px; font-weight: 600; letter-spacing: 2px; font-size: 25px;">创信供应链金融平台</span>
            <span style="margin-left: 15px;">供应商注册</span>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        //刷新验证图片
        $("#btn_kan").bind("click", function () {
            refreshCode();
        });

        //获取验证码
        $("#btn_code").bind("click", function () {
            var mobile = $("#mobile").val();
            var img_code = $("#img_code").val();
            if (mobile == "") {
                layer.msg("请输入手机号");
                return false;
            }
            if (img_code == "") {
                layer.msg("请输入图形验证码");
                return false;
            }

            var thic = this;
            $.ajax({
                async: true,
                cache: true,
                url: "${ctx}/sys/register/get-mobileCode?validateCode=" + img_code + "&mobile=" + mobile,
                data: {},
                type: "get",
                dataType: "text",
                success: function (data, status, xhr) {
                    if (data == "000") {
                        layer.msg("验证码已发送，请注意查收短信");
                        time(thic, 60);
                    } else if (data == "003") {
                        layer.msg("图形验证码错误");
                        refreshCode();
                    } else if (data == "004") {
                        layer.msg("用户名不存在");
                        time(thic, 60);
                    } else if (data == "005") {
                        layer.msg("手机号码不一致");
                        time(thic, 60);
                    } else if (data == "006") {
                        layer.msg("手机号码已存在");
                        time(thic, 60);
                    } else if (data == "007") {
                        layer.msg("手机号码或验证均不能为空");
                        time(thic, 60);
                    } else {
                        layer.msg(data);
                        time(thic, 60);
                    }
                },
                error: function (xhr, status, error) {
                    layer.msg(status);
                }
            });
        });
    });

    //刷新图形验证码
    function refreshCode() {
        var imgSrc = $("#codeImg");
        var src = imgSrc.attr("src");
        imgSrc.attr("src", chgUrl(src));
    }

    //验证码加入时间戳去缓存
    function chgUrl(url) {
        var timestamp = (new Date()).valueOf();
        if (url.indexOf("&") >= 0) {
            url = url + "&timestamp=" + timestamp;
        } else {
            url = url + "?timestamp=" + timestamp;
        }
        return url;
    }

    var error_img = "<img src=\"${ctxStatic}/images/ti1.png\">";

    //校验参数
    function checkParams(area) {
        var login = $("#login").val();
        var password = $("#password").val();
        var re_password = $("#re_password").val();
        var mobile = $("#mobile").val();



        switch (area) {
            case "login":
                if (login == "") {
                    $("#error_login").html(error_img + " 请输入用户名");
                    return false;
                } else
				if (login.length < 6 ||login.length > 12) {
                    $("#error_login").html(error_img + " 用户名长度6-12位");
                    return false;
                }  else if (isChineseChar(login) || isFullwidthChar(login)) {
                    $("#error_login").html(error_img + " 账户不能有中文");
                    return false;
                } else {
                    $("#error_login").html("");
                }
                break;
            case "password":
                if (password == "") {
                    $("#error_password").html(error_img + " 输入密码");
                    return false;
                } else if(login.length < 6 || login.length > 16 || reg_password.test(login) !== true) {
                    $("#error_password").html(error_img + " 密码长度6-16位，由英文、数字或下划线组成");
                    return false;
                } else {
                    $("#error_password").html("");
                }
                break;
            case "re_password":
                if (re_password == "") {
                    $("#error_repassword").html(error_img + " 再次输入密码");
                    return false;
                } else if (password !== re_password) {
                    $("#error_repassword").html(error_img + " 两次密码不一致");
                    return false;
                } else {
                    $("#error_repassword").html("");
                }
                break;
            case "mobile":
                if (mobile == "") {
                    $("#error_mobile").html(error_img + " 输入手机号");
                    return false;
                } else {
                    if (!reg_mobile.test(mobile)) {
                        $("#error_mobile").html(error_img + " 请输入正确的手机号");
                    } else {
                        $("#error_mobile").html("");
                        $("#login").val(mobile);
                    }
                }
                break;
            default:
                break;
        }
    }

    //是否含有中文（也包含日文和韩文）
    function isChineseChar(str) {
        var reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
        return reg.test(str);
    }

    //同理，是否含有全角符号的函数
    function isFullwidthChar(str) {
        var reg = /[\uFF00-\uFFEF]/;
        return reg.test(str);
    }

    //提交
    function checkForm() {
        var invite_code = $("#invite_code").val();
        var login = $("#login").val();
        var password = $("#password").val();
        var re_password = $("#re_password").val();
        var verify_code = $("#verify_code").val();
		var mobile = $("#mobile").val();

        if (invite_code == "") {
            $("#error_invite_code").html(error_img+" 输入邀请码");
            $("#invite_code").focus();
            return false;
        } else {
            $("#error_invite_code").html("");
        }

		if (mobile == "") {
			$("#error_mobile").html(error_img + " 输入手机号");
			return false;
		} else {
			if (!reg_mobile.test(mobile)) {
				$("#error_mobile").html(error_img + " 请输入正确的手机号");
			} else {
				$("#error_mobile").html("");
				$("#login").val(mobile);
			}
		}
        if (login == "") {
            $("#error_login").html(error_img +" 输入用户名");
            $("#login").focus();
            return false;
        } else if (login.length < 6||login.length > 12) {
            $("#error_login").html(error_img+" 用户名长度6-12位");
            $("#login").focus();
            return false;
        } else {
            $("#error_login").html("");
        }

        if (password == "") {
            $("#error_password").html(error_img +" 输入密码");
            $("#password").focus();
            return false;
        } else if(login.length < 6 || login.length > 16 || reg_password.test(login) !== true) {
			$("#error_password").html(error_img + " 密码长度6-16位，由英文、数字或下划线组成");
            $("#password").focus();
            return false;
        } else {
            $("#error_password").html("");
        }

        if (re_password == "") {
            $("#error_repassword").html(error_img +" 再次输入密码");
            $("#repassword").focus();
            return false;
        } else if (password !== re_password) {
            $("#error_repassword").html(error_img +" 两次密码不一致");
            $("#repassword").focus();
            return false;
        } else {
            $("#error_repassword").html("");
        }

        if (verify_code == "") {
            $("#error_verify_code").html(error_img +" 输入校验码");
            $("#verify_code").focus();
            return false;
        } else {
            $("#error_verify_code").html("");
        }

        if (!disableLinkButton($("#btnSubmit"))) return false;

        layer.open({
            type: 2,
            content: '<div style="text-align: center">正在提交数据...</div>',
            style: 'border:none;',
            shadeClose: false
        });
        return false;
    }

</script>

<script type="text/javascript">
    var wait = 60;

    function time(o) {
        if (wait == 0) {
            o.removeAttribute("disabled");
            o.value = "获取验证码";
            o.style.background = "#f08519";
            wait = 60;
        } else {
            o.setAttribute("disabled", true);
            o.value = "重新发送(" + wait + ")";
            o.style.background = "#bbbbbb";
            wait--;
            setTimeout(function () {
                time(o);
            }, 1000);
        }
    }
</script>

<div class="reg_body clear">
    <input type="hidden" id="userId" value="${user.id}"/>
    <input type="hidden" id="message" value="${message}"/>
    <form name="mgmtform" id="mgmtform" onsubmit="return checkForm(this);"
          action="${ctx}/sys/register/register-supplierUser" accept-charset="UTF-8" method="post">
        <div class="reg_left" style="padding: 0px 0 0 20px;">
            <div class="reg_header">新用户注册</div>
            <div class="reg_qi" style="margin-top: 6px;">企业名称： <span id="company_name"></span></div>
            <div class="reg_hang" style="margin-top: 12px;">
                <div class="reg_l">
                    <img src="${ctxStatic}/images/reg_01.png" class="reg_tu"><span class="reg_name">邀请码<font
                        color="#ff0000">*</font>：</span>
                    <input type="text" class="reg_text" id="invite_code" name="invitationCode" value="${invitationCode}"
                           placeholder="请输入邀请码">
                </div>
                <div class="reg_r" id="error_invite_code"></div>
            </div>
            <div class="reg_hang" style="margin-top: 12px;">
                <div class="reg_l">
                    <img src="${ctxStatic}/images/reg_04.png" class="reg_tu"><span class="reg_name">手机号<font
                        color="#ff0000">*</font>：</span>
                    <input type="text" class="reg_text" value="${user.mobile}" id="mobile" name="mobile"
                           placeholder="请输入手机号" onblur="checkParams('mobile');">
                </div>
                <div class="reg_r" id="error_mobile"></div>
            </div>
            <div class="reg_hang" style="margin-top: 12px;">
                <div class="reg_l">
                    <a href="javascript:void(0);" class="reg_kan" id="btn_kan">看不清？</a>
                    <span class="reg_tu1" id="updateimg1" name="updateimg1"><img id="codeImg"
                                                                                 src="${ctx}/sys/register/get-validateCode"
                                                                                 alt="验证码"></span>
                    <img src="${ctxStatic}/images/reg_05.png" class="reg_tu"><span class="reg_name">验证码<font
                        color="#ff0000">*</font>：</span>
                    <input type="text" class="reg_text" id="img_code" name="img_code" value="" placeholder="请输入验证码">
                </div>
                <div class="reg_r"></div>
            </div>
            <div class="reg_hang" style="margin-top: 12px;">
                <div class="reg_l">
                    <input type="button" class="reg_yan" id="btn_code" name="btn_code" value="获取校验码"><img
                        src="${ctxStatic}/images/reg_06.png" class="reg_tu"><span class="reg_name">校验码<font
                        color="#ff0000">*</font>：</span>
                    <input type="text" class="reg_text" id="verify_code" name="validateCode" value="${validateCode}"
                           placeholder="请输入校验码">
                </div>
                <div class="reg_r" id="error_verify_code"></div>
            </div>
            <div class="reg_hang">
                <div class="reg_l">
                    <img src="${ctxStatic}/images/reg_02.png" class="reg_tu"><span class="reg_name">用户名<font
                        color="#ff0000">*</font>：</span>
                    <input type="text" class="reg_text" id="login" name="loginName" value="${user.loginName}"
                           placeholder="请输入用户名" onblur="checkParams('login');">
                </div>
                <div class="reg_r" id="error_login"></div>
            </div>
            <div class="reg_hang" style="margin-top: 12px;">
                <div class="reg_l">
                    <img src="${ctxStatic}/images/reg_03.png" class="reg_tu"><span class="reg_name">密　码<font
                        color="#ff0000">*</font>：</span>
                    <input type="password" class="reg_text" id="password" name="password" value=""
                           placeholder="6-16位，由英文、数字或下划线组成" onblur="checkParams('password');">
                </div>
                <div class="reg_r" id="error_password"></div>
            </div>
            <div class="reg_hang" style="margin-top: 12px;">
                <div class="reg_l">
                    <img src="${ctxStatic}/images/reg_03.png" class="reg_tu"><span class="reg_name">确认密码<font
                        color="#ff0000">*</font>：</span>
                    <input type="password" class="reg_text" id="re_password" name="re_password" value=""
                           placeholder="请再次输入密码" onblur="checkParams('re_password');">
                </div>
                <div class="reg_r" id="error_repassword"></div>
            </div>
            <div class="reg_zhu clear" style="margin-top: 40px;"><input type="submit" class="reg_submit1" id="btnSubmit"
                                                                        value="立即注册"></div>
            <div class="clear"></div>
        </div>
    </form>

    <div class="reg_right">
        <div class="reg_header1">已有账号，去<a href="${ctx}/login">登录</a></div>
        <div class="reg_wen" style="width: 420px;"><!-- 拨打客户电话：<font color="#ff0000">000-00000000</font><br> -->
            发送邮件：<font color="#ff0000">service@cx-gyl.com</font> 与我们联系！
        </div>
    </div>
    <div class="clear"></div>
</div>

<script type="text/javascript" language="javascript">
    $(function () {
        //获取回调消息
        if (!$.isEmpty($("#message").val())) {
            layer.msg($("#message").val(), {shift: 6});

            if ($("#message").val() == "恭喜您注册成功！" && !$.isEmpty($("#userId").val())) {
                setTimeout(function () {
                    location.href = "${ctx}/sys/register/to-supplierRegisterSuccess?id=" + $("#userId").val();
                }, 2000);
            }
        }

        $("#invite_code").bind("blur", function () {
            renderCompanyName();
        });
        //获取地址拼接参数
        var param = location.search;

        if (!$.isEmpty(param) && param.indexOf("invite_code") != -1) {
            $("#invite_code").val(param.substring(param.indexOf("invite_code") + 12));
        }

        renderCompanyName();
    });

    //根据邀请码获取企业名称
    function renderCompanyName() {
        var invite_code = $("#invite_code").val();
        $.ajax({
            async: true,
            cache: true,
            url: "${ctx}/sys/register/get-companyInfo-code?invitationCode=" + invite_code,
            data: {},
            type: "get",
            dataType: "text",
            success: function (data, status, xhr) {
                $("#company_name").html("");
                $("#error_invite_code").html("");
                if ($.isEmpty(data)) {
                    $("#error_invite_code").html('<img src=\"${ctxStatic}/images/ti1.png\">邀请码不能为空！');
                } else if (data.indexOf("ok+") == -1) {
                    $("#error_invite_code").html('<img src=\"${ctxStatic}/images/ti1.png\">' + data);
                } else {
                    $("#company_name").html(data.substring(3, data.length));
                }
            },
            error: function (xhr, status, error) {
                layer.msg(status);
            }
        });
    }
</script>

<div class="reg_footer" style="height: 94px; line-height: 25px;">
    <span>咨询热线：000-0000000 9:00 -- 18:00（周一至周五）</span>
    <br>
    <span>版权所有 ©2018 上海创信供应链管理有限公司 沪ICP备 <a href="http://www.miitbeian.gov.cn" target="_blank">18038558</a>号</span>
</div>

</body>
</html>