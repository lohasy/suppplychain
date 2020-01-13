<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>银行--银行管理--授信复核</title>
<meta name="decorator" content="default" />
<script>
	$(document).ready(function() {
		if($("#state").attr("val") != undefined && $("#state").attr("val") != null && $("#state").attr("val") != ""){
  			$("#state option[value="+ $("#state").attr("val") +"]")[0].selected = true;
  		}
	});
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5><a href="${ctx}/BankCreditReview/gys-index">授信管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;供应商列表</span>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a> <a class="close-link"> <i class="fa fa-times"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content">
				<sys:message content="${message}" />

				<!-- 查询条件 -->
				<div class="row">
					<form:form style="padding-left: 15px; margin-bottom: 5px;" modelAttribute="supplier_enterprise" id="searchForm" action="${ctx}/BankCreditReview/gys-index" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();" />
						<!-- 支持排序 -->
						<div class="form-group">
							<input name="name" value="${supplier_enterprise.name}" placeholder="供应商名称" class="input-sm form-control" />
							<select style="margin-left: 10px; padding:0 12px; width: 200px;" id="state" name="state" val="${supplier_enterprise.state}" class="input-sm form-control input-s-sm inline">
                      			<option value="">授信状态</option>
                      			<option value="4">待授信</option>
                    			<option value="5">授信通过</option>
                      			<option value="6">授信不通过</option>
                    		</select>
						</div>
					</form:form>
					<div class="col-sm-12" style="margin-top: 10px; margin-bottom: 10px;">
						<div class="pull-right">
							<button  class="btn btn-primary btn-rounded btn-outline btn-sm" onclick="search()" ><i class="fa fa-search"></i> 查询</button>
							<button style="margin-left: 10px;" class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
						</div>
					</div>
					<div class="col-sm-12">
						<table id="contentTable"
							class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
							<thead>
								<tr>
									<th><input type="checkbox" class="i-checks"></th>
									<th>供应商名称</th>
									<th>组织机构代码</th>
									<th>认证状态</th>
									<th>总额度（元）</th>
									<th>年化利率（%）</th>
									<th>融资比例（%）</th>
									<th>更新时间</th>					
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="supplier_enterprise">
									<tr>
										<td><input type="checkbox" id="${supplier_enterprise.id}" class="i-checks"></td>
										<td>${supplier_enterprise.name}</td>
										<td>${supplier_enterprise.orgCode}</td>
										<td>					
											<c:if test="${supplier_enterprise.state == '0'}">
												<span class="badge">待提交资料</span>
											</c:if>
											<c:if test="${supplier_enterprise.state == '1'}">
												<span class="badge badge-primary">待平台审核</span>
											</c:if>
											<c:if test="${supplier_enterprise.state == '2'}">
												<span class="badge">平台审核不通过</span>
											</c:if>
											<c:if test="${supplier_enterprise.state == '3'}">
												<span class="badge badge-primary">待签约</span>
											</c:if>						
											<c:if test="${supplier_enterprise.state == '4'}">
												<span class="badge badge-success">待授信</span>
											</c:if>
												<c:if test="${supplier_enterprise.state == '5'}">
												<span class="badge badge-success">授信通过</span>
											</c:if>
											<c:if test="${supplier_enterprise.state == '6'}">
												<span class="badge badge-success">授信不通过</span>
											</c:if>											
										</td>
										<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${supplier_enterprise.paramsId.allQuota}" /></td>
										<td>${supplier_enterprise.paramsId.interestRate}</td>
										<td>${supplier_enterprise.paramsId.financingRatio}</td>
										<td><fmt:formatDate value="${supplier_enterprise.updateDate}" type="both" /></td>
										<td>									
									 		<a href="${ctx}/BankCreditAudit/gys-info?id=${supplier_enterprise.id}" class="btn btn-info btn-xs">供应商详情</a>
								     		<c:if test="${supplier_enterprise.state == 7 and fn:contains(fns:getUser().roleNames, '银行复核员')}">
					                  			<a href="javascript: void(0);" onclick="openDialogView('授信复核', '${ctx}/BankCreditReview/form?id=${supplier_enterprise.id}','800px', '500px')" class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i>复核</a>
					                    	</c:if>
					                    	<a href="${ctx}/BankCreditAudit/toCreditReferenceView?id=${supplier_enterprise.id}" class="btn btn-warning btn-xs">授信参考</a>
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