<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--融资管理--融资列表</title>
	<meta name="decorator" content="default"/>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5><a href="${ctx}/rzglall/list?state=${state}">融资管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;融资列表</span>
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
					<form:form style="padding-left: 15px; margin-bottom: 5px;" modelAttribute="financing_info" id="searchForm" action="${ctx}/rzglall/list?state=${state}" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
						<div class="form-group">
							<input id="num" name="num" value="${financing_info.num}" placeholder="融资编号" class="input-sm form-control" />
							<input style="margin-left: 10px;" id="billId.supplierEnterpriseId.name" name="billId.supplierEnterpriseId.name" value="${financing_info.billId.supplierEnterpriseId.name}" placeholder="供应商名称" class="input-sm form-control" />
							<!-- <button style="margin-left: 20px;" class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
							<button style="margin-left: 10px;" class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button> -->
						</div>
					</form:form>
					<div class="col-sm-12" style="margin-top: 10px; margin-bottom: 10px;">
						<div class="pull-left">
							<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
							<button style="margin-left: 10px;" class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
						</div>
					</div>
					<div class="col-sm-12">
						<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
							<thead>
								<tr>
									<th><input type="checkbox" class="i-checks"></th>
									<th>融资编号</th>
									<th>融资总金额</th>
									<th>融资生成日</th>
									<th>融资到期日</th>
									<th>融资状态</th>
									<th>供应商名称</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="financing_Info">
									<tr>
										<td><input type="checkbox" id="${financing_Info.id}" class="i-checks"></td>
										<td>${financing_Info.num}</td>
										<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${financing_Info.totalAmount}" /></td>
										<td><fmt:formatDate value="${financing_Info.generationDate}" pattern="yyyy-MM-dd"/></td>
										<td><fmt:formatDate value="${financing_Info.expiryDate}" pattern="yyyy-MM-dd"/></td>
										<c:if test="${financing_Info.state == 0}">
											<td>待负责人审核</td>
										</c:if>
										<c:if test="${financing_Info.state == 1}">
											<td>待签约</td>
										</c:if>
										<c:if test="${financing_Info.state == 2}">
											<td>负责人审核不通过</td>
										</c:if>
										<c:if test="${financing_Info.state == 3}">
											<td>待银行配置</td>
										</c:if>
										<c:if test="${financing_Info.state == 4}">
											<td>待银行审核</td>
										</c:if>
										<c:if test="${financing_Info.state == 5}">
											<td>银行审核不通过</td>
										</c:if>
										<c:if test="${financing_Info.state == 6}">
											<td>待银行复核</td>
										</c:if>
										<c:if test="${financing_Info.state == 7}">
											<td>银行复核不通过</td>
										</c:if>
										<c:if test="${financing_Info.state == 8}">
											<td>待银行放款</td>
										</c:if>
										<c:if test="${financing_Info.state == 9}">
											<td>已放款</td>
										</c:if>
										<c:if test="${financing_Info.state == 10}">
											<td>待银行清分</td>
										</c:if>
										<c:if test="${financing_Info.state == 11}">
											<td>已完成</td>
										</c:if>
										<td>${financing_Info.billId.supplierEnterpriseId.name}</td>
										<td>
			                            	<a href="${ctx}/rzglall/redirect-info?id=${financing_Info.id}" class="btn btn-primary btn-xs">融资详情</a>
					                    </td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!-- 分页代码 -->
						<table:page page="${page}"></table:page>
						<!-- 导出EXCEL -->
						<div class="row ibox-content">
						<form action="${ctx}/rzglall/export" method="post">
							<input type="hidden" name="state" value="${state}"/>
                  			<input type="submit" value="导出EXCEL数据" id="do_export" name="do_export" class="btn btn-sm btn-primary">
						</form>
                		</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>