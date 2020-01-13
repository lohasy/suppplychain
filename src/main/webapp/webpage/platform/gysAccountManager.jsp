<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商--我的公司--帐号管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
</head>
<body class="gray-bg">
	<div class="rong_nav">
		<ul>
			<li><a href="${ctx}/gys/gys-company-info" class="nav_b">基本资料</a></li>
			<li><a href="${ctx}/gys/gys-mycompany-userManager" class="nav_a">帐号管理</a></li>
			<li><a href="${ctx}/gys/gys-mycompany-downloadContract" class="nav_b">下载合同</a></li>
		</ul>
	</div>
	<div class="wrapper wrapper-content" style="padding: 0;">
		<div class="ibox" style="margin-bottom: 0;">
			<div class="ibox-title">
				<h5><a href="${ctx}/gys/gys-mycompany-userManager?supplierEnterpriseId.id=${fns:getUser().supplier.id}">帐号管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;帐号列表</span>
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
					<form:form style="padding-left: 15px; margin-bottom: 5px;" id="searchForm" action="${ctx}/gys/gys-mycompany-userManager?supplierEnterpriseId.id=${fns:getUser().supplier.id}" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
						<div class="form-group">
							<input name="userId.name" value="${supplier_user.userId.name}" placeholder="姓名" class="input-sm form-control" />
							<input style="margin-left: 10px;" name="userId.mobile" value="${supplier_user.userId.mobile}" placeholder="手机号" class="input-sm form-control" />
						</div>
					</form:form>
					<div class="col-sm-12" style="margin-top: 5px; margin-bottom: 5px;">
						<img style="float: left; width: 16px; height: 16px; margin-right: 8px; margin-top: 1px;" src="${ctxStatic}/images/icon_15.png">由平台操作员添加财务账号和领导账号，财务账号可发起融资，负责人账号可审批融资
						<div class="pull-right">
							<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
							<button style="margin-left: 10px;" class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
						</div>
					</div>
					<div class="col-sm-12" style="margin-bottom: 15px;">
						<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
							<thead>
								<tr>
									<th><input type="checkbox" class="i-checks"></th>
									<th>帐号</th>
									<th>姓名</th>
									<th>手机号</th>
									<th>邮箱</th>
									<th>角色</th>
									<th>创建日期</th>
									<th>修改日期</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="supplier_user">
									<tr>
										<td><input type="checkbox" id="${supplier_user.userId.id}" class="i-checks"></td>
										<td>${supplier_user.userId.loginName}</td>
										<td>${supplier_user.userId.name}</td>
										<td>${supplier_user.userId.mobile}</td>
										<td>${supplier_user.userId.email}</td>
										<td>
											<c:forEach items="${supplier_user.userId.roleList}" var="roles">
												<span>${roles.name} </span>
											</c:forEach>
										</td>
										<td><fmt:formatDate value="${supplier_user.userId.createDate}" type="both"/></td>
										<td><fmt:formatDate value="${supplier_user.userId.updateDate}" type="both"/></td>
										<td></td>
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