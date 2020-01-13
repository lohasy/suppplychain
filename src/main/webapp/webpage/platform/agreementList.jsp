<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--协议管理--协议列表</title>
	<meta name="decorator" content="default"/>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5><a href="${ctx}/xieyi/list">协议管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;协议列表</span>
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
					<form:form style="padding-left: 15px; margin-bottom: 5px;" id="searchForm" modelAttribute="protocol_info" action="${ctx}/xieyi/list" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
						<div class="form-group">
							<input name="name" value="${protocol_info.name}" placeholder="协议名称" class="input-sm form-control" />
						</div>
					</form:form>
					<div class="col-sm-12" style="margin-top: 10px; margin-bottom: 10px;">
						<div class="pull-left">
					       <button style="margin-right: 10px;" class="btn btn-white btn-sm" data-toggle="tooltip" onclick="location.href='${ctx}/xieyi/editXieyi?type=3';" data-placement="left" title="添加"><i class="fa fa-plus"></i> 添加</button>
					       <table:delRow url="${ctx}/xieyi/delete" id="contentTable"></table:delRow>
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
									<th>协议编号</th>
									<th>协议名称</th>
									<th>添加时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								 <c:forEach items="${page.list}" var="page">
									<tr>
										<td><input type="checkbox" id="${page.id}" class="i-checks"></td>
										<td>${page.id}</td>
										<td>${page.name}</td>
										<td><fmt:formatDate value="${page.createDate}" type="both"/></td>
										<td>
		                            		<a href="${ctx}/xieyi/editXieyi?id=${page.id}&type=1" class="btn btn-primary btn-xs">查看协议</a>
		                            		<a href="${ctx}/xieyi/editXieyi?id=${page.id}&&type=2" class="btn btn-primary btn-xs">编辑协议</a>
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