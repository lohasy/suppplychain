<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>创信供应链</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
   		#tip {
	    	position: absolute;
	    	right: 2px;
	    	bottom: 0px;
	    	height: 100px;
	    	width: 200px;
	    	border: 1px solid #CCCCCC;
	    	background-color: #eeeeee;
	    	padding: 1px;
	    	overflow: hidden;
	    	display: none;
	    	font-size: 1em;
	    	z-index: 10;
	   	}
	   	
	   	#tip p {
	    	padding: 6px;
	   	}
	   	
	   	#tip h1 {
	    	font-size: 14px;
	    	height: 25px;
	    	line-height: 25px;
	    	background-color: #3e4954;
	    	color: #FFFFFF;
	    	padding: 0px 5px 0px 5px;
	    	filter: Alpha(Opacity = 100);
	    	margin-top: 0px;
	   	}
	   	
	   	#tip h1 a, #detail h1 a {
	    	float: right;
	    	text-decoration: none;
	    	color: #FFFFFF;
	   	}
  	</style>
	<script src="${ctxStatic}/online/lgt-web.js"></script>
	
	<script>
		//初始化
		$(function(){
			$(".menu li:eq(0)").click();
			
			var divTip = document.createElement("div");
		    divTip.id = "tip";
		    divTip.innerHTML = "<h1><a href='javascript:void(0)' onclick='closeMsgBox()'>关闭</a>消息提示</h1><p><a id='upwin' href='javascript:void(0)' onclick='showwin()'></a></p>";
		    divTip.style.position = 'fixed';
		    document.body.appendChild(divTip);
			
			var t1 = window.setInterval(refreshCount, 5000);
			function refreshCount() {
				$.ajax({
			 		type: "POST",
			  		url: "${ctx}/noReadCount",
			   		success: function(data){
			        	$("#noreadcount").html(data.msg);
			         	if(data.msg > 0) {
			      	   		$("#upwin").html("您好，您有"+ data.msg +"条未读信息！");
			      	   		start();
			         	}
			   		}
				});
			}
		});
		
		//点击Tab菜单
		function clickTabMenu(index, obj){
			$(".menu li").removeClass("menu_hover");
			$(".menu li:eq("+ index +")").addClass("menu_hover");
			$("#content-frame").attr("src", $(obj).attr("src"));
		}
		
		//设置Iframe高度自适应
		function setIframeHeight(childHeight){
			var obj = document.getElementById("content-frame");
			obj.height = 100; 
			obj.height=(childHeight > 0 ? childHeight : 100);
		}
		
		//打开修改密码窗口
		function openUpdatePwd(){
			$("#layui-layer-shade100002").show();
			$("#layui-layer100002").show();
		}
		
		//确认
		function sure(){
			var oldPwd = $("#oldPassword").val();
			var newPwd = $("#newPassword").val();
			var twoNewPwd = $("#twoNewPassword").val();
			$("#error_oldPassword").text("");
			$("#error_newPassword").text("");
			$("#error_twoNewPassword").text("");
			if($.isEmpty(oldPwd)){
				$("#error_oldPassword").text("请输入旧密码！");
				return;
			}
			if($.isEmpty(newPwd)){
				$("#error_newPassword").text("请输入新密码！");
				return;
			}
			if(newPwd.length < 6){
				$("#error_newPassword").text("密码长度不小于6位！");
				return;
			}
			if($.isEmpty(twoNewPwd)){
				$("#error_twoNewPassword").text("请输入确认密码！");
				return;
			}
			if(newPwd != twoNewPwd){
				$("#error_twoNewPassword").text("两次密码输入不一致！");
				return;
			}
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/sys/user/updatePwdByAnscy",
				data : { "oldPassword" : oldPwd, "newPassword" : newPwd },
				type: "post",
				dataType: "text",
				success: function(data, status, xhr){
					if(data == "修改密码成功！"){
						alert("密码修改成功，请重新登录！");
						location.href = "${ctx}/logout";
					}else{
						layer.msg(data);
					}
				},
				error: function(xhr, status, error){
					layer.msg(status);
				}
			});
		}
		
		//取消
		function cacel(){
			$("#layui-layer-shade100002").hide();
			$("#layui-layer100002").hide();
		}
		
		//弹出消息框
		function start(){
			$("#tip").slideDown(200);
		}

		//点击消息框
		function showwin(){
			$("#tip").slideUp(200);
			toMsgPage();
		}
		
		//关闭消息框
		function closeMsgBox(){
			$("#tip").slideUp(200);
		}
		
		//跳转至消息页面
		function toMsgPage(){
			$("#content-frame").attr("src", "${ctx}/iim/mailBox/list?orderBy=sendtime desc");
		}
	</script>
</head>
<body>
	<div class="header">
  		<div class="header_top">
    		<div class="header_top_con">
      			<div class="header_top_l">
      				欢迎光临！ 
      				<c:if test="${not empty fns:getUser().supplier.id}">${fns:getUser().supplier.name}</c:if>
      				<c:if test="${not empty fns:getUser().core.id}">${fns:getUser().core.name}</c:if>
<%--      				--- ${fns:getUser().roleNames}--%>
      			</div>
      			<div class="header_top_l">
      				<span>|　${fns:getUser().loginName} <a href="${ctx}/logout">退出系统</a><a style="margin-left:20px; margin-right:20px;" href="javascript: void(0);" onclick="openUpdatePwd()">修改密码</a><a style="margin-left:20px;" href="${ctx}/CoreEnterpriseCtrl/toHelpPage" target="_blank">操作说明</a></span>
					<a href="javascript: void(0);" class="header_xiao" onclick="toMsgPage()">
						<img src="${ctxStatic}/images/icon_01.png" /><font id="noreadcount">0</font>
					</a>
				</div>
    		</div>
  		</div>
  
  		<div class="header_con clear">
    		<div class="logo"><a href="#"></a></div>
    		<div class="menu" style="height: 100px; padding: 37px 0 0 0;">
      			<ul>
        			<li src="${ctx}/CoreEnterpriseCtrl/hxqy-home?id=${fns:getUser().core.id}" onclick="clickTabMenu(0, this)"><a href="javascript: void(0);">首页</a></li>
        			<li src="${ctx}/yfzk/list?coreEnterpriseId.id=${fns:getUser().core.id}" onclick="clickTabMenu(1, this)"><a href="javascript: void(0);">融资管理</a></li>
        			<li src="${ctx}/coreSupplierCtrl/invitation-gys?coreEnterpriseId.id=${fns:getUser().core.id}" onclick="clickTabMenu(2, this)"><a href="javascript: void(0);">供应商管理</a></li>
        			<c:if test="${fn:contains(fns:getUser().roleNames, '核心企业财务')}">
        				<li src="${ctx}/zhgl/hxqy-index?coreEnterpriseId.id=${fns:getUser().core.id}" onclick="clickTabMenu(3, this)"><a href="javascript: void(0);">账户管理</a></li>
        				<li src="${ctx}/CoreEnterpriseCtrl/hxqy-company-info" onclick="clickTabMenu(4, this)"><a href="javascript: void(0);">我的公司</a></li>
        			</c:if>
        			<c:if test="${not fn:contains(fns:getUser().roleNames, '核心企业财务')}">
        				<li src="${ctx}/CoreEnterpriseCtrl/hxqy-company-info" onclick="clickTabMenu(3, this)"><a href="javascript: void(0);">我的公司</a></li>
        			</c:if>
      			</ul>
   			</div>
  		</div>
	</div>
	
	<div style="width: 1200px; margin: 30px auto 0 auto;">
		<iframe id="content-frame" name="content-frame" frameBorder="0" scrolling="no" style="width: 100%; padding: 0; margin: 0;"></iframe>
	</div>
	
	<div class="footer clear" style="margin: 30px 0 0 0; padding: 20px 0 20px 0;">
  		<div class="footer_con">
    		<div class="footer_nei" style="padding-top: 9px; letter-spacing: 2px;">
  				<a href="javascript: void(0);" onclick="clickTabMenu(0, this)" src="${ctx}/CoreEnterpriseCtrl/hxqy-home?id=${fns:getUser().core.id}">首页</a> |
  				<a href="javascript: void(0);" onclick="clickTabMenu(1, this)" src="${ctx}/yfzk/list?coreEnterpriseId.id=${fns:getUser().core.id}">融资管理</a> |
  				<a href="javascript: void(0);" onclick="clickTabMenu(2, this)" src="${ctx}/coreSupplierCtrl/invitation-gys?coreEnterpriseId.id=${fns:getUser().core.id}">供应商管理</a> |
  				<c:if test="${fn:contains(fns:getUser().roleNames, '核心企业财务')}">
  					<a href="javascript: void(0);" onclick="clickTabMenu(3, this)" src="${ctx}/zhgl/hxqy-index?coreEnterpriseId.id=${fns:getUser().core.id}">账户管理</a> |
  					<a href="javascript: void(0);" onclick="clickTabMenu(4, this)" src="${ctx}/CoreEnterpriseCtrl/hxqy-company-info">我的公司</a>
  				</c:if>
  				<c:if test="${not fn:contains(fns:getUser().roleNames, '核心企业财务')}">
  					<a href="javascript: void(0);" onclick="clickTabMenu(3, this)" src="${ctx}/CoreEnterpriseCtrl/hxqy-company-info">我的公司</a>
  				</c:if>
  				<br>
  				版权所有 ©2018 上海创信供应链管理有限公司 沪ICP备 <a href="http://www.miitbeian.gov.cn" target="_blank">18038558</a>号
  			</div>
		</div>
	</div>
	
	<div class="layui-layer-shade" id="layui-layer-shade100002" times="100002" style="z-index:19991015; display: none; background-color:#000; opacity:0.8; filter:alpha(opacity=80);"></div>
	
	<div class="layui-layer layui-layer-iframe  layer-anim" id="layui-layer100002" type="iframe" times="100002" showtime="0" contype="string" style="display: none; z-index: 19991016; width: 30%; height: 275px; top: 230px; left: 531.5px;">
		<div class="layui-layer-title" style="cursor: move;">修改密码</div>
		<div class="layui-layer-content">
    		<div class="popup_nei2 clear" style="width:; height: 60px;">
      			<input type="text" style="width:200px; margin-top: 10px;" class="disclose_text1" id="oldPassword" name="oldPassword" value="" placeholder="请输入旧密码" />
      			<span class="disclose_r" id="error_oldPassword" style="margin-top: 9px;"></span>
    		</div>
    		<div class="popup_nei2 clear" style="height: 60px;">
      			<input type="text" style="width:200px; margin-top: 10px;" class="disclose_text1" id="newPassword" name="newPassword" value="" placeholder="请输入新密码" />
      			<span class="disclose_r" id="error_newPassword" style="margin-top: 9px;"></span>
    		</div>
    		<div class="popup_nei2 clear" style="height: 80px;">
      			<input type="text" style="width:200px; margin-top: 10px;" class="disclose_text1" id="twoNewPassword" value="" placeholder="请确认新密码" />
      			<span class="disclose_r" id="error_twoNewPassword" style="margin-top: 9px;"></span>
    		</div>
    		<div class="popup_bot clear">
      			<a href="javascript:void(0);" onclick="sure()" class="popup_a">确 定</a>
      			<a href="javascript:void(0);" style="margin-left: 15px;" onclick="cacel()" class="popup_b">取 消</a>
    		</div>
    		<div class="clear"></div>
		</div>
	</div>
</body>
</html>