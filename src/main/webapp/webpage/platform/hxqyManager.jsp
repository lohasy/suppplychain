<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--企业管理--核心企业管理</title>
	<meta name="decorator" content="default"/>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5><a href="${ctx}/CoreEnterpriseCtrl/platform-index">核心企业管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;核心企业列表</span>
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
					<form:form style="padding-left: 15px; margin-bottom: 5px;" id="searchForm" modelAttribute="core_enterprise" action="${ctx}/CoreEnterpriseCtrl/platform-index" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
						<div class="form-group">
							<form:input path="name" name="name" value="${core_enterprise.name}" placeholder="核心企业名称" class="input-sm form-control" />
							<form:input style="margin-left: 10px;" path="chargeName" name="chargeName" value="${core_enterprise.chargeName}" placeholder="负责人姓名" class="input-sm form-control" />
							<form:input style="margin-left: 10px;" path="chargePhone" name="chargePhone" value="${core_enterprise.chargePhone}" placeholder="手机号" class="input-sm form-control" />
						</div>
					</form:form>
			        <div class="col-sm-12" style="margin-top: 10px; margin-bottom: 10px;">
						<div class="pull-left">
					       <button style="margin-right: 10px;" class="btn btn-white btn-sm" data-toggle="tooltip" onclick="location.href='${ctx}/CoreEnterpriseCtrl/platform-edit';" data-placement="left" title="添加">
					       <i class="fa fa-plus"></i> 添加</button>
					       <table:delRow url="${ctx}/CoreEnterpriseCtrl/delete" id="contentTable"></table:delRow>
						</div>
						<div class="pull-right">
							<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
							<button style="margin-left: 10px;" class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
						</div>
					</div>
					<div class="col-sm-12">
						<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
							<thead>
								<tr>
									<th><input type="checkbox" class="i-checks"></th>
									<th>核心企业名称</th>
									<th>负责人姓名</th>
									<th>手机号</th>
									<th>邮箱</th>
									<th>总额度（元）</th>
									<th>可用额度（元）</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="core_enterprise">
									<tr>
										<td><input type="checkbox" id="${core_enterprise.id}" class="i-checks"></td>
										<td>${core_enterprise.name}</td>
										<td>${core_enterprise.chargeName}</td>
										<td>${core_enterprise.chargePhone}</td>
										<td>${core_enterprise.chargeEmail}</td>
										<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${core_enterprise.paramsId.allQuota}" /></td>
										<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${core_enterprise.paramsId.availableQuota}" /></td>
										<td>
			                            	<a href="${ctx}/CoreEnterpriseCtrl/platform-edit?id=${core_enterprise.id}" class="btn btn-primary btn-xs">编辑</a>
					                    	<a href="${ctx}/CoreEnterpriseCtrl/platform-info?id=${core_enterprise.id}" class="btn btn-info btn-xs">详情</a>
					                        <a href="javascript:void(0);" onclick="openDialog('配置参数', '${ctx}/CoreEnterpriseCtrl/showInfo?id=${core_enterprise.id}', '800px', '500px')" class="btn btn-warning btn-xs config">配置参数</a>
					                        <a href="${ctx}/CoreEnterpriseCtrl/userlist?core.id=${core_enterprise.id}" class="btn btn-primary btn-xs">查看员工</a>
					                        <a href="${ctx}/gys/gys-index?coresupplier.coreEnterpriseId.id=${core_enterprise.id}" class="btn btn-primary btn-xs">查看供应商</a>
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