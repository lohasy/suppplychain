<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商--我的公司--下载合同</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
</head>
<body class="gray-bg">
	<div class="rong_nav">
		<ul>
			<li><a href="${ctx}/gys/gys-company-info" class="nav_b">基本资料</a></li>
			<li><a href="${ctx}/gys/gys-mycompany-userManager" class="nav_b">帐号管理</a></li>
			<li><a href="${ctx}/gys/gys-mycompany-downloadContract" class="nav_a">下载合同</a></li>
		</ul>
	</div>
	<div class="wrapper wrapper-content" style="padding: 0;">
		<div class="ibox" style="margin-bottom: 0;">
			<div class="ibox-title">
				<h5><a href="${ctx}/gys/gys-mycompany-downloadContract?financingId.billId.supplierEnterpriseId.id=${fns:getUser().supplier.id}">下载合同</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;合同列表</span>
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
	
				<!-- 查询条件 -->
				<div class="row">
					<div class="col-sm-12" style="margin-bottom: 15px;">
						<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
							<thead>
								<tr>
									<th><input type="checkbox" class="i-checks"></th>
									<th>合同名称</th>
									<th>合同类型</th>
									<th>创建日期</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="contract_info">
									<tr>
										<td><input type="checkbox" id="${contract_info.id}" class="i-checks"></td>
										<td>${contract_info.name}</td>
										<td>
											<c:if test="${contract_info.type == 0}">
												应收账款转让协议
											</c:if>
											<c:if test="${contract_info.type == 1}">
												融资合同
											</c:if>
											<c:if test="${contract_info.type == 2}">
												征信报告查询授权书
											</c:if>
										</td>
										<td><fmt:formatDate value="${contract_info.createDate}" pattern="yyyy-MM-dd"/></td>
										<td>
											<a href="${contract_info.url}" style="color: #ffffff;" class="btn btn-primary btn-xs">下载查看</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!-- 分页代码 -->
						<table:page page="${page}"></table:page>
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
			{
				setTimeout(function(){
					$(".pace-progress").css("top", "52px");
				}, 200);
				parent.setIframeHeight($(".wrapper").height() + 55);
			}
		} 
	</script>
</body>
</html>