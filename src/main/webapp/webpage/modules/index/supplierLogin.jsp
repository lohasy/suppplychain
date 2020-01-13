<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>创信供应链</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css" />
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
		
	</script>
</head>
<body>
	<div class="login">
		<input type="hidden" name="message" id="message" value="${message}" />
		<div class="btn-area" style="position: absolute; width: 100%; top: 10px;">
			<a href="${ctx}/login?loginType=register" style="margin-right: 10px;">供应商注册</a>
			<a href="${ctx}/login">机构登录</a>
		</div>
  		<form id="loginForm" action="${ctx}/login?loginType=supplier" method="post">
  			<input type="hidden" name="loginType" value="supplier" />
  			<div class="login_con clear">
  				<div class="login_kuang">
	      			<div class="login_hang">
		      			<img src="${ctxStatic}/images/login_01.png" class="login_biao" />
		      			<input type="text" name="username" id="username" class="login_text" placeholder="请输入用户名" onkeydown="enterLogin()" />
		      		</div>
		      		<div class="login_hang">
		      			<img src="${ctxStatic}/images/login_02.png" class="login_biao" />
		      			<input type="password" name="password" id="password" class="login_text" placeholder="请输入密码" onkeydown="enterLogin()" />
		      		</div>
		      		<div class="login_bot">
		      			<a href="#" style="float:right;">忘记密码？</a>
		      			<input style="position: relative; top: -2px;" type="checkbox" id="rememberMe" name="rememberMe" />
		      			<span style="color: #ffffff; font-size:1em; margin-left: 5px;">记住我</span>
		      		</div>
		      		<div class="login_deng">
		      			<input type="button" onclick="login()" style="border:none; text-align: center; font-size: 20px; cursor:pointer; width: 570px; height: 70px; background: #e54e15; color: #ffffff !important;" value="立即登录" />
		      		</div>
	    		</div>
  			</div>
		</form>
	</div>
	
	<div class="login_footer clear" style="height: auto;">
		咨询热线：000-0000000 9:00 -- 18:00（周一至周五）<br/> 版权所有 ©2018 上海创信供应链管理有限公司 沪ICP备 <a href="http://www.miitbeian.gov.cn" target="_blank">18038558</a>号
	</div>
</body>
</html>