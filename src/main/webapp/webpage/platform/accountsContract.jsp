<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html style="height:100%;">
<head>
	<title>平台--融资管理--单据账款转让合同</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" />
	<link href="${ctxStatic}/online/prettyPhoto.css" rel="stylesheet" />
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet" />
	<link href="${ctxStatic}/online/checkbix.min.css" rel="stylesheet" />
	<style type="text/css">
		body {       
			font-family: SimSun;
		}
	</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>
					<c:if test="${not empty fns:getUser().supplier.id }">
						<a href="${ctx}/rzglall/gyslist">融资列表</a>
						<span>&nbsp;/&nbsp;应收账款转让合同</span>
					</c:if>
					<c:if test="${not empty fns:getUser().core.id }">
						<a href="${ctx}/rzglall/hxqylist">融资列表</a>
						<span>&nbsp;/&nbsp;应付账款转让合同</span>
					</c:if>
					<c:if test="${fn:contains(fns:getUser().roleNames, '平台')}">
						<a href="${ctx}/rzglall/list">融资管理</a>
						<span>&nbsp;/&nbsp;单据账款转让合同</span>
					</c:if>
					<c:if test="${fn:contains(fns:getUser().roleNames, '银行')}">
						<a href="${ctx}/bank/bankList">融资管理</a>
						<span>&nbsp;/&nbsp;单据账款转让合同</span>
					</c:if>
				</h5>
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
				<div class="main_body clear" style="width: 100%; margin: 0;">
            		<script type="text/javascript" src="${ctxStatic}/online/jquery.min.js"></script>
					<script src="${ctxStatic}/online/script.js" type="text/javascript"></script>
					<script src="${ctxStatic}/online/jquery.prettyPhoto.js" type="text/javascript"></script>
					
					<!--融资详情NAV 平台管理-->
					<div class="rong_nav" style="width: 100%;">
						<ul>
							<li><a href="${ctx}/rzglall/redirect?type=0" class="nav_b">融资详情</a></li>
					    	<li><a href="${ctx}/rzglall/redirect?type=1" class="nav_b">供应商详情</a></li>
					    	<%-- <c:if test="${not empty fns:getUser().supplier.id }">
								<li><a href="${ctx}/rzglall/redirect?type=2" class="nav_a">应收账款转让合同</a></li>
							</c:if>
							<c:if test="${not empty fns:getUser().core.id }">
								<li><a href="${ctx}/rzglall/redirect?type=2" class="nav_a">应付账款转让合同</a></li>
							</c:if>
							<c:if test="${empty fns:getUser().supplier.id and empty fns:getUser().core.id }">
								<li><a href="${ctx}/rzglall/redirect?type=2" class="nav_a">单据账款转让合同</a></li>
							</c:if> --%>
					    	<li><a href="${ctx}/rzglall/redirect?type=3" class="nav_a">融资合同</a></li>
					  	</ul>
					</div>
					<script type="text/javascript" src="${ctxStatic}/online/checkbix.min.js"></script>
					<div class="financing clear" style="width: 100%;">
      					<div class="hetong">
            				<a href="${ctx}/exportpdf?id=${bill_info.id}&type=bill" class="yao9" style="float: right; margin-top: 10px; width: auto; padding: 5px;">下载合同</a>
            				<a href="${ctx}/rzglall/redirect?type=3" class="yao9" style="float: right; margin-top: 10px; width: auto; padding: 5px;">融资合同</a>
							<iframe name="toppage" style="height: 2000px; width: 100%;" frameBorder="0" scrolling="yes" src="${htm}"></iframe> 
          					<div class="clear"></div>
						</div>
          			</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>