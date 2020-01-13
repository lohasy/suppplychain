<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商--融资申请--查看核心凭证</title>
	<meta name="decorator" content="default"/>
	
	<link href="${ctxStatic}/online/prettyPhoto-3.1.6.css" rel="stylesheet" type="text/css">
	<script src="${ctxStatic}/online/jquery.prettyPhoto-3.1.6.js" type="text/javascript"></script>
	
	<script>
		$(function(){
			$("a[rel^='prettyPhoto']").prettyPhoto();
		});
	</script>
</head>
<body class="gray-bg">
	<ul>
		<c:forEach items="${coreVoucherInfos}" var="voucher_info">
			<li style="margin-top: 20px; margin-right: 20px; list-style-type: none; float: left;">
				<a class="image-zoom" href="${voucher_info.url}" rel="prettyPhoto"><label></label><img src="${voucher_info.url}" width="100" height="100"></a>
			</li>
		</c:forEach>
	</ul>
</body>
</html>