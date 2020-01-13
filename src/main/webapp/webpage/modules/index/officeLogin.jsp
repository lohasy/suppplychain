<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>创信供应链</title>
	<link href="${ctxStatic}/online/officeLogin.css" rel="stylesheet" type="text/css">
	
	<script src="${ctxStatic}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.14.0/jquery.validate.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.14.0/localization/messages_zh.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/awesome/4.4/css/font-awesome.min.css" rel="stylesheet" />
	
	<!-- jeeplus -->
	<link href="${ctxStatic}/common/jeeplus.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/common/jeeplus.js" type="text/javascript"></script>
	<link rel="shortcut icon" href="images/favicon.png" type="image/png">
	
	<!-- 引入layer插件 -->
	<script src="${ctxStatic}/layer-v2.3/layer/layer.js"></script>
	<script src="${ctxStatic}/layer-v2.3/layer/laydate/laydate.js"></script>
	
	<script src="${ctxStatic}/online/lgt-web.js"></script>
	
	<script>
	
		$(function(){
			if (window.top !== window.self) {
				layer.msg("未登录或登录超时，请重新登录！", {shift: 6});
				setTimeout(function(){
					window.top.location = window.location;
				}, 1000);
			}
			
			//获取登录回调消息
			if(!$.isEmpty($("#message").val())){
				layer.msg($("#message").val(), {shift: 6});
			}
			
			//判断是否记住我
			onlineCache.type = "local";
			var userName = onlineCache.get("scf-userName");
			if(!$.isEmpty(userName)){
				$("#username").val(userName);
				$("#rememberMe").attr("checked", "checked");
			}
		});
		
		//回车登录
		function enterLogin(){
			if (event.keyCode == 13)
	  		{
				login();
	  		}
		}
	
		//登录
		function login(){
			var userName = $("#username").val();
			var password = $("#password").val();
			if($.isEmpty(userName)){
				layer.msg('请输入用户名！', {shift: 6});
				return false;
			}
			if($.isEmpty(password)){
				layer.msg('请输入密码！', {shift: 6});
				return false;
			}
			if($("#rememberMe").is(':checked')){
				onlineCache.type = "local";
				onlineCache.set("scf-userName", userName);
			}else{
				onlineCache.type = "local";
				onlineCache.set("scf-userName", "");
			}
			$("#loginForm").submit();
		}
		
		
		//跳转注册页面
		function toRegister(){
			location.href = "${ctx}/login?loginType=register";
		}
		
	</script>
</head>
<body>
	<div class="bg1"></div>
	<div class="gyl">
		上海创信供应链管理有限公司
	</div>
	<%-- <div class="btn-area" style="margin-top: 10px;">
		<a href="${ctx}/login?loginType=register" style="margin-right: 10px;">供应商注册</a>
		<a href="${ctx}/login?loginType=supplier">供应商登录</a>
	</div> --%>
	<input type="hidden" name="message" id="message" value="${message}" />
	<form id="loginForm" action="${ctx}/login" method="post">
		<input type="hidden" name="loginType" value="office" />
		<div class="bg">
			<div class="wel">用户登录</div>			
	        <div class="user">
	       	    <div id="yonghu" style="">用户名</div>
	       	    <input type="text" id="username" name="username" placeholder="请输入用户名" onkeydown="enterLogin()" />
	        </div>
	        <div class="password" >
	        	<div id="mima" >密&nbsp;&nbsp;&nbsp;码</div>
	       	    <input type="password" id="password" name="password" placeholder="请输入密码" onkeydown="enterLogin()" />
	        </div>
	        <div class="rem" >
	       	  <input type="checkbox" id="rememberMe" name="rememberMe" />
	        	 <div id="reb">
	        	 	记住我
	        	 </div>
	        </div>
	        <div class="fg" >
	       	    <div style="font-size: 11px;margin-top: 11px;">
	       	    	<a style="font-size: 11px; color: #666;" href="#">忘记密码？</a>
	       	    </div>
	        </div>
	        <input class="btn" type="button" name="登录" style="left: 2.5rem;" value="登录" onclick="login()" />
	        <input class="btn" type="button" name="注册" style="background-color: red;" value="注册" onclick="toRegister()" />
		</div>
	</form>
</body>
</html>