<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>银行--融资管理</title>
	<meta name="decorator" content="default"/>
	<script>
		$(function(){
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
				<h5><a href="${ctx}/bank/bankList">融资管理</a></h5>
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
					<form:form style="padding-left: 15px; margin-bottom: 5px;" id="searchForm" modelAttribute="financing_info" action="${ctx}/bank/bankList" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();" /><!-- 支持排序 -->
						<div class="form-group">
							<form:input path="num" name="num" value="${financing_info.num}" placeholder="融资编号" class="input-sm form-control" />
							<input style="margin-left: 10px;" name="billId.supplierEnterpriseId.name" value="${financing_info.billId.supplierEnterpriseId.name}" placeholder="供应商名称" class="input-sm form-control" />
							<select style="margin-left: 10px; padding:0 12px; width: 200px;" id="state" name="state" val="${financing_info.state}" class="input-sm form-control input-s-sm inline">
                      			<option value="">所有状态</option>
                      			<option value="4">待审核</option>
                      			<option value="6">待复核</option>
                    			<option value="8">待放款</option>
                      			<option value="10">待清分</option>
                    		</select>
						</div>
					</form:form>
			        <div class="col-sm-12" style="margin-top: 10px; margin-bottom: 10px;">
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
									<th>融资编号</th>
									<th>融资总金额（元）</th>
									<th>融资生成时间</th>
									<th>融资到期日</th>
									<th>融资状态</th>
									<th>供应商名称</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="financing_info">
									<tr>
										<td><input type="checkbox" id="${financing_info.id}" class="i-checks"></td>
										<td>${financing_info.num}</td>
										<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${financing_info.totalAmount}" /></td>
										<td><fmt:formatDate value="${financing_info.generationDate}" pattern="yyyy-MM-dd" /></td>
										<td><fmt:formatDate value="${financing_info.expiryDate}" pattern="yyyy-MM-dd" /></td>
										<td>
											<c:if test="${financing_info.state == '4'}">待审核</c:if>
											<c:if test="${financing_info.state == '6'}">待复核</c:if>
											<c:if test="${financing_info.state == '8'}">待放款</c:if>
											<c:if test="${financing_info.state == '10'}">待清分</c:if>
										</td>
										<td>${financing_info.billId.supplierEnterpriseId.name}</td>
										<td>
											<a href="${ctx}/bank/toFinancingDetail?id=${financing_info.id}" class="btn btn-primary btn-xs">融资详情</a>
											<c:if test="${financing_info.state == '4' and fn:contains(fns:getUser().roleNames, '银行操作员')}">
					                    		<a href="${ctx}/bank/toFinancingDetail?id=${financing_info.id}&flag=1" class="btn btn-info btn-xs">审核</a>
					                    	</c:if>
					                    	<c:if test="${financing_info.state == '6' and fn:contains(fns:getUser().roleNames, '银行复核员')}">
					                    		<a href="javascript:void(0);" onclick="openDialog('融资复核', '${ctx}/bank/toFuhe?id=${financing_info.id}', '400px', '200px')" class="btn btn-info btn-xs">复核</a>
					                    	</c:if>
					                    	<c:if test="${financing_info.state == '8' and fn:contains(fns:getUser().roleNames, '银行操作员')}">
					                    		<a href="javascript:void(0);" onclick="openDialog('融资放款', '${ctx}/bank/toFangkuan?id=${financing_info.id}', '500px', '350px')" class="btn btn-info btn-xs">放款</a>
					                    	</c:if>
					                    	<c:if test="${financing_info.state == '10' and fn:contains(fns:getUser().roleNames, '银行操作员')}">
					                    		<a href="javascript:void(0);" onclick="openDialog('融资清分', '${ctx}/bank/toQingfen?id=${financing_info.id}', '600px', '550px')" class="btn btn-info btn-xs">清分</a>
					                    	</c:if>
					                    	<c:if test="${financing_info.state == '4' and fn:contains(fns:getUser().roleNames, '银行操作员')}">
					                        	<a href="javascript:void(0);" onclick="openDialog('配置参数', '${ctx}/bank/form?id=${financing_info.id}', '500px', '300px')" class="btn btn-warning btn-xs config">配置</a>
					                        </c:if>
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