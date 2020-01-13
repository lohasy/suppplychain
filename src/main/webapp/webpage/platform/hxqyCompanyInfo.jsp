<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>核心企业--我的公司--基本资料</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script src="${ctxStatic}/online/lgt-web.js"></script>
	<link href="${ctxStatic}/online/prettyPhoto-3.1.6.css" rel="stylesheet" type="text/css">
	<script src="${ctxStatic}/online/jquery.prettyPhoto-3.1.6.js" type="text/javascript"></script>
	<style type="text/css" rel="stylesheet">
		.row dd {
			margin: 10px 180px;
		}
	</style>
	<script>
		$(function(){
			$("a[rel^='prettyPhoto']").prettyPhoto();
			
			if($.isEmpty($("#user-roles").val()) || $("#user-roles").val().indexOf("核心企业负责人") == -1){
				$("#edit").hide();
			}
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content" style="padding: 0;">
		<div class="ibox" style="margin-bottom: 0;">
			<div class="ibox-title">
				<c:if test="${fns:getUser().roleNames == '平台操作员'}">
					<h5><a href="${ctx}/CoreEnterpriseCtrl/platform-index">核心企业管理</a></h5>
				</c:if>
				<c:if test="${fns:getUser().roleNames != '平台操作员'}">
					<h5><a href="#">基本资料</a></h5>
				</c:if>
				<span class="page-title">&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;核心企业详情</span>
				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
					<a class="close-link">
						<i class="fa fa-times"></i>
					</a>
				</div>
			</div>
    		<div class="ibox-content">
				<sys:message content="${message}"/>
				<input type="text" style="display: none;" value="${core_enterprise.id}" />
				<input type="hidden" id="user-roles" value="${fns:getUser().roleNames}" />
				<div class="row">
	                <div class="ibox-title" style="background-color: #f5f5f5;">
	                	<h5>一、企业信息</h5>
	                </div>
                	<div class="ibox-content">
                  		<dl class="dl-horizontal">
                    		<dt>企业名称：</dt>
                    		<dd>${core_enterprise.name}</dd>
                    		<dt>组织机构代码：</dt>
                    		<dd>${core_enterprise.orgCode}</dd>
                    		<dt>营业期限至：</dt>
                    		<dd>${core_enterprise.businessPeriodTo}</dd>
                    		<dt>注册资本：</dt>
                    		<dd>${core_enterprise.registeredCapital}</dd>
                    		<dt>企业类型：</dt>
                    		<c:choose>
                    			<c:when test="${core_enterprise.type == 1}">
                    				<dd>股份有限公司</dd>
                    			</c:when>
                    			<c:when test="${core_enterprise.type == 2}">
                    				<dd>有限责任公司</dd>
                    			</c:when>
                    			<c:when test="${core_enterprise.type == 3}">
                    				<dd>合伙企业</dd>
                    			</c:when>
                    			<c:when test="${core_enterprise.type == 4}">
                    				<dd>集体企业</dd>
                    			</c:when>
                    			<c:when test="${core_enterprise.type == 5}">
                    				<dd>国有企业</dd>
                    			</c:when>
                    			<c:otherwise>
                    				<dd>无</dd>
                    			</c:otherwise>
                    		</c:choose>
                    		<dt>营业地址：</dt>
                    		<dd>${core_enterprise.businessAddress}</dd>
                    		<dt>营业执照：</dt>
                    		<dd>
                      			<a class="image-zoom" href="${core_enterprise.businessLicense}" rel="prettyPhoto"><img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${core_enterprise.businessLicense}" /></a>
                    		</dd>
                  		</dl>
               		</div>

	                <div class="ibox-title" style="background-color: #f5f5f5;">
                  		<h5>二、法人信息</h5>
	                </div>
                	<div class="ibox-content">
                  		<dl class="dl-horizontal">
                    		<dt>身份证正面：</dt>
                    		<dd>
                      			<a class="image-zoom" href="${core_enterprise.legalCardPositive}" rel="prettyPhoto"><img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${core_enterprise.legalCardPositive}" /></a>
                    		</dd>
                    		<dt>身份证反面：</dt>
                    		<dd>
                      			<a class="image-zoom" href="${core_enterprise.legalCardBack}" rel="prettyPhoto"><img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${core_enterprise.legalCardBack}" /></a>
                    		</dd>
                    		<dt>有效期：</dt>
                    		<dd>${core_enterprise.legalCardValidity}</dd>
                    		<dt>签证机关：</dt>
                    		<dd>${core_enterprise.legalCardOffice}</dd>
                    		<dt>姓名：</dt>
                    		<dd>${core_enterprise.legalName}</dd>
                    		<dt>身份证号码：</dt>
                    		<dd>${core_enterprise.legalIdCard}</dd>
                    		<dt>性别：</dt>
                    		<c:if test="${core_enterprise.legalSex == '0'}">
                    			<dd>男</dd>
                    		</c:if>
                    		<c:if test="${core_enterprise.legalSex != '0'}">
                    			<dd>女</dd>
                    		</c:if>
                    		<dt>民族：</dt>
                    		<dd>${core_enterprise.legalNation}</dd>
                    		<dt>地址：</dt>
                    		<dd>${core_enterprise.legalAddress}</dd>
                  		</dl>
               		</div>

	                <div class="ibox-title" style="background-color: #f5f5f5;">
	                	<h5>三、负责人信息</h5>
	                </div>
                	<div class="ibox-content">
                  		<dl class="dl-horizontal">
				      		<dt>姓名：</dt>
				      		<dd>${core_enterprise.chargeName}</dd>
				      		<dt>手机号：</dt>
				      		<dd>${core_enterprise.chargePhone}</dd>
				      		<dt>邮箱：</dt>
				      		<dd>${core_enterprise.chargeEmail}</dd>
				      		<dt>身份证正面：</dt>
                    		<dd>
                      			<a class="image-zoom" href="${core_enterprise.chargeCardPositive}" rel="prettyPhoto"><img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${core_enterprise.chargeCardPositive}" /></a>
                    		</dd>
                    		<dt>身份证反面：</dt>
                    		<dd>
                      			<a class="image-zoom" href="${core_enterprise.chargeCardBack}" rel="prettyPhoto"><img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${core_enterprise.chargeCardBack}" /></a>
                    		</dd>
                    		<dt>有效期：</dt>
                    		<dd>${core_enterprise.chargeCardValidity}</dd>
                    		<dt>签证机关：</dt>
                    		<dd>${core_enterprise.chargeCardOffice}</dd>
                    		<dt>身份证号码：</dt>
                    		<dd>${core_enterprise.chargeIdCard}</dd>
                    		<dt>性别：</dt>
                    		<c:if test="${core_enterprise.chargeSex == '0'}">
                    			<dd>男</dd>
                    		</c:if>
                    		<c:if test="${core_enterprise.chargeSex != '0'}">
                    			<dd>女</dd>
                    		</c:if>
                    		<dt>民族：</dt>
                    		<dd>${core_enterprise.chargeNation}</dd>
                    		<dt>地址：</dt>
                    		<dd>${core_enterprise.chargeAddress}</dd>
				    	</dl>
                	</div>
              	</div>
			</div>
		</div>
	</div>
	
	<script>
		document.onreadystatechange = subSomething;//当页面加载状态改变的时候执行这个方法. 
		function subSomething() 
		{ 
			if(document.readyState == "complete") //当页面加载状态
				parent.setIframeHeight($(".wrapper").height());
		} 
	</script>
</body>
</html>