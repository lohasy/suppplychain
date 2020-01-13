<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--企业管理--企业员工列表</title>
	<meta name="decorator" content="default"/>
	<script>
		$(document).ready(function() {
        	if('${coreid}' != '' && '${coreid}' != null){
            	$("h5:eq(1)").hide();
            	$("a[id^=asup]").css("display","none");
        	}else{
            	$("h5:eq(0)").hide();
            	$("a[id^=acor]").css("display","none");
        	}
   	 	});
		
		function sortOrRefresh(){
			location.reload();
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5><a  href="${ctx}/CoreEnterpriseCtrl/platform-index">核心企业管理</a></h5>
				<h5><a  href="${ctx}/gys/gys-index">供应商管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;企业员工列表</span>
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
			        <div class="col-sm-12" style="margin-top: 10px; margin-bottom: 10px;">
			        	<c:if test="${not empty coreid}">
			        		<div class="pull-left" id="core">
						       <button style="margin-right: 10px;" class="btn btn-white btn-sm" data-toggle="tooltip" onclick="location.href='${ctx}/CoreEnterpriseCtrl/editUser?coreid=${coreid}'" data-placement="left" title="添加"><i class="fa fa-plus"></i> 添加</button>
						       <table:delRow url="${ctx}/CoreEnterpriseCtrl/deleteuser" id="contentTable"></table:delRow>
							</div>
			        	</c:if>
						<c:if test="${empty coreid and not empty suppl}">
			        		<div class="pull-left" id="suppl">
						       <button style="margin-right: 10px;" class="btn btn-white btn-sm" data-toggle="tooltip" onclick="location.href='${ctx}/gys/editUser?suppl=${suppl}'" data-placement="left" title="添加"><i class="fa fa-plus"></i> 添加</button>
						       <table:delRow url="${ctx}/gys/deleteuser" id="contentTable"></table:delRow>
							</div>
			        	</c:if>
						<button class="btn btn-white btn-sm " style="margin-left: 14px;" data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
					</div>
					<div class="col-sm-12">
						<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
							<thead>
								<tr>
									<th><input type="checkbox" class="i-checks"></th>
									<th>帐号</th>
									<th>姓名</th>
									<th>手机号</th>
									<th>邮箱</th>
									<th>角色</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								 <c:forEach items="${page.list}" var="user">
									<tr>
										<td><input type="checkbox" id="${user.id}" class="i-checks"></td>
										<td>${user.loginName}</td>
										<td>${user.name}</td>
										<td>${user.mobile}</td>
										<td>${user.email}</td>
										<td>
											<c:forEach items="${user.roleList}" var="roles">
												<span>${roles.name} </span>
											</c:forEach>
										</td>
										<td>
			                            	<a id="acore" href="${ctx}/CoreEnterpriseCtrl/editUser?id=${user.id}&&coreid=${user.core.id}" class="btn btn-primary btn-xs">编辑</a>
			                            	<a id="asuppl"  href="${ctx}/gys/editUser?id=${user.id}&&suppl=${user.supplier.id}" class="btn btn-primary btn-xs">编辑</a>
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
</body>
</html>