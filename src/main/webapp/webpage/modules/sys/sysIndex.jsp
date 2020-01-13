<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>创信供应链</title>
	<%@ include file="/webpage/include/head.jsp"%>
	<script src="${ctxStatic}/common/inspinia.js?v=3.2.0"></script>
	<script src="${ctxStatic}/common/contabs.js"></script>
    <meta name="keywords" content="供应链金融平台">
    <meta name="description" content="供应链金融平台">
    <style type="text/css">
		* {
	   		margin: 0px;
    		padding: 0px;
	   	}
	   	html, body {
	    	height: 100%;
	   	}
	   	body {
	    	font-size: 14px;
	    	line-height: 24px;
	   	}
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
</head>

<body class="fixed-sidebar full-height-layout gray-bg">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span><img alt="image" class="img-circle" style="height:64px;width:64px;" src="${fns:getUser().photo }" onerror="this.src='${ctxStatic}/images/userinfo.jpg';this.onerror=null" /></span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold">${fns:getUser().name}</strong></span>
                               <span class="text-muted text-xs block">${fns:getUser().roleNames}<b class="caret"></b></span>
                                </span>
                            </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li><a class="J_menuItem" href="${ctx}/sys/user/imageEdit">修改头像</a>
                                </li>
                                <li><a class="J_menuItem" href="${ctx }/sys/user/info">个人资料</a>
                                </li>
                                <%-- <li style="display: none;"><a class="J_menuItem" href="${ctx }/iim/contact/index">我的通讯录</a>
                                </li>
                                <li style=""><a class="J_menuItem" href="${ctx }/iim/mailBox/list">信箱</a>
                                </li>
                                <li class="divider"></li>
                                <li><a onclick="changeStyle()" href="#">切换到ACE模式</a>
                                </li> --%>
                                 
                                <li class="divider"></li>
                                <li><a href="${ctx}/logout">安全退出</a></li>
                            </ul>
                        </div>
                        <div class="logo-element">SCF</div>
                    </li>
                  <t:menu  menu="${fns:getTopMenu()}"></t:menu>
                  
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                        <form style="display: none;" role="search" class="navbar-form-custom" method="post" action="search_results.html">
                            <div class="form-group">
                                <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
                            </div>
                        </form>
                    </div>
                    <ul class="nav navbar-top-links navbar-right" style="margin-right: 0;">
                        <li class="dropdown">
                             <a style="padding: 13px 30px;" class="dropdown-toggle count-info" data-toggle="dropdown" href="javascript: void(0);">
                                <i class="fa fa-bell"></i> <span style="padding: 2px 8px; top: 17px;" id="noreadcount" class="label label-warning">${noReadCount}</span>
                            </a>
                            <ul class="dropdown-menu dropdown-messages">
                                <li>
                                    <div class="text-center link-block">
                                        <a class="J_menuItem" id="showAllMsg-a" href="${ctx}/iim/mailBox/list?orderBy=sendtime desc">
                                            <i class="fa fa-bell"></i> <strong> 查看所有消息</strong>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown" style="display:none">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-bell"></i> <span class="label label-primary">${count }</span>
                            </a>
                            <ul class="dropdown-menu dropdown-alerts">
                         		<li>
	                                <c:forEach items="${page.list}" var="oaNotify">
	                               		<div>
	                              	   		<a class="J_menuItem" href="${ctx}/oa/oaNotify/view?id=${oaNotify.id}&">
	                                     		<i class="fa fa-envelope fa-fw"></i> ${fns:abbr(oaNotify.title,50)}
	                                     	</a>
	                                    	<span class="pull-right text-muted small">${fns:getTime(oaNotify.updateDate)}前</span>
	                                   	</div>
									</c:forEach>
                                </li>
                                <li class="divider"></li>
                                <li>
                              		<div class="text-center link-block">您有${count }条未读消息 <a class="J_menuItem" href="${ctx }/oa/oaNotify/self ">
                                   		<strong>查看所有 </strong>
                                   		<i class="fa fa-angle-right"></i>
                                	</div>
                                </li>
                            </ul>
                        </li>
                      
                      <!-- 国际化功能预留接口 -->
                        <li class="dropdown" style="display: none;">
							<a id="lang-switch" class="lang-selector dropdown-toggle" href="#" data-toggle="dropdown" aria-expanded="true">
								<span class="lang-selected">
										<img  class="lang-flag" src="${ctxStatic}/common/img/china.png" alt="中国">
										<span class="lang-id">中国</span>
										<span class="lang-name">中文</span>
									</span>
							</a>

							<!--Language selector menu-->
							<ul class="head-list dropdown-menu with-arrow">
								<li>
									<!--English-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/china.png" alt="中国">
										<span class="lang-id">中国</span>
										<span class="lang-name">中文</span>
									</a>
								</li>
								<li>
									<!--English-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/united-kingdom.png" alt="English">
										<span class="lang-id">EN</span>
										<span class="lang-name">English</span>
									</a>
								</li>
								<li>
									<!--France-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/france.png" alt="France">
										<span class="lang-id">FR</span>
										<span class="lang-name">Français</span>
									</a>
								</li>
								<li>
									<!--Germany-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/germany.png" alt="Germany">
										<span class="lang-id">DE</span>
										<span class="lang-name">Deutsch</span>
									</a>
								</li>
								<li>
									<!--Italy-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/italy.png" alt="Italy">
										<span class="lang-id">IT</span>
										<span class="lang-name">Italiano</span>
									</a>
								</li>
								<li>
									<!--Spain-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/spain.png" alt="Spain">
										<span class="lang-id">ES</span>
										<span class="lang-name">Español</span>
									</a>
								</li>
							</ul>
						</li>
                    </ul>
                </nav>
            </div>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="${ctx}/home">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose"  data-toggle="dropdown">关闭操作<span class="caret"></span>

                    </button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>
                <a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/home" frameborder="0" data-id="${ctx}/home" seamless></iframe>
            </div>
            <div class="footer" style="height: 41px;">
                <div class="pull-left" style="margin-left: 20px;">版权所有 ©2018 上海创信供应链管理有限公司 沪ICP备 <a href="http://www.miitbeian.gov.cn" target="_blank">18038558</a>号</div>     
            </div>
        </div>
        <!--右侧部分结束-->
       
       
    </div>
</body>

<!-- 语言切换插件，为国际化功能预留插件 -->
<script type="text/javascript">

$(document).ready(function(){
	$("a.lang-select").click(function(){
		$(".lang-selected").find(".lang-flag").attr("src",$(this).find(".lang-flag").attr("src"));
		$(".lang-selected").find(".lang-flag").attr("alt",$(this).find(".lang-flag").attr("alt"));
		$(".lang-selected").find(".lang-id").text($(this).find(".lang-id").text());
		$(".lang-selected").find(".lang-name").text($(this).find(".lang-name").text());
	});

	var t1 = window.setInterval(refreshCount, 5000);
	function refreshCount() {
		$.ajax({
	 		type: "POST",
	  		url: "${ctx}/noReadCount",
	   		success: function(data){
	        	$("#noreadcount").html(data.msg);
	         	if(data.msg > 0) {
	      	   		$("#upwin").html("您好，您有"+ data.msg +"条未读信息哦！");
	      	   		start();
	         	}
	   		}
		});
	}
});

function changeStyle(){
   $.get('${pageContext.request.contextPath}/theme/ace?url='+ window.top.location.href, function(result){ window.location.reload(); });
}


window.onload = function(){
	var divTip = document.createElement("div");
    divTip.id = "tip";
    divTip.innerHTML = "<h1><a href='javascript:void(0)' onclick='closeMsgBox()'>关闭</a>消息提示</h1><p><a id='upwin' href='javascript:void(0)' onclick='showwin()'></a></p>";
    divTip.style.position = 'fixed';
    document.body.appendChild(divTip);
}

//弹出消息框
function start(){
	$("#tip").slideDown(200);
}

//点击消息框
function showwin(){
	$("#tip").slideUp(200);
	$("#showAllMsg-a").click();
}

//关闭消息框
function closeMsgBox(){
	$("#tip").slideUp(200);
}

</script>



<!-- 即时聊天插件 -->
<%-- <link href="${ctxStatic}/layer-v2.3/layim/layim.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
	var currentId = '${fns:getUser().loginName}';
	var currentName = '${fns:getUser().name}';
	var currentFace ='${fns:getUser().photo}';
	var url="${ctx}";
	var wsServer = 'ws://'+window.document.domain+':8668';
</script>
<script src="${ctxStatic}/layer-v2.3/layim/layer.min.js"></script>
<script src="${ctxStatic}/layer-v2.3/layim/layim.js"></script> --%>

</html>