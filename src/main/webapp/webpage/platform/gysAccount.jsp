<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商--账户管理--账户详情</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script>
		$(document).ready(function() {
	        //外部js调用
	    	laydate({
	        	elem: '#beginDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	    });
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content" style="padding: 0;">
		<div class="ibox" style="margin-bottom: 0;">
			<div class="ibox-title">
				<h5><a href="${ctx}/zhgl/gyszhgl-index?supplierEnterpriseId.id=${fns:getUser().supplier.id}">账户管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;账户详情</span>
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
	
				<div class="account_top" style="width: 100%; height: 150px; padding: 0; border-bottom: none;">
      				<div class="account_shou" style="width: 49%; margin-right: 2%; height: 130px; font-size: 1.1em; line-height: 26px;">
      					<!-- <a href="javascript:void(0);" class="account_ti">提现</a> -->
      					<%-- <c:if test="${not empty supplier_enterprise.paramsId.loanBalance}">
      						<font color="#666666">融资账户余额：</font>${supplier_enterprise.paramsId.loanBalance}元<br>
      					</c:if>
      					<c:if test="${empty supplier_enterprise.paramsId.loanBalance}">
      						<font color="#666666">融资账户余额：</font>0元<br>
      					</c:if> --%>
        				<font color="#666666" style="margin-left: 40px;">账号名：</font>${supplier_enterprise.paramsId.loanName}<br>
        				<font color="#666666" style="margin-left: 27px;">银行账号：</font>${supplier_enterprise.paramsId.loanAccount}<br>
        				<font color="#666666" style="margin-left: 40px;">开户行：</font>${supplier_enterprise.paramsId.loanOpenBank}
      				</div>
      				<div class="account_hui" style="width: 49%; height: 130px; font-size: 1.1em; line-height: 26px;">
      					<!-- <a href="javascript:void(0);" class="account_ti">提现</a> -->
      					<%-- <c:if test="${not empty supplier_enterprise.paramsId.returnBalance}">
      						<font color="#666666">回款账户余额：</font>${supplier_enterprise.paramsId.returnBalance}元<br>
      					</c:if>
        				<c:if test="${empty supplier_enterprise.paramsId.returnBalance}">
      						<font color="#666666">回款账户余额：</font>0元<br>
      					</c:if> --%>
        				<font color="#666666" style="margin-left: 40px;">账号名：</font>${supplier_enterprise.paramsId.returnName}<br>
        				<font color="#666666" style="margin-left: 27px;">银行账号：</font>${supplier_enterprise.paramsId.returnAccount}<br>
        				<font color="#666666" style="margin-left: 40px;">开户行：</font>${supplier_enterprise.paramsId.returnOpenBank}
      				</div>
    			</div>
				
				<!-- 查询条件 -->
				<div class="row">
					<form:form style="padding-left: 15px; margin-bottom: 5px;" id="searchForm" modelAttribute="account_detailed" action="${ctx}/zhgl/gyszhgl-index?supplierEnterpriseId.id=${fns:getUser().supplier.id}" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
						<div class="form-group">
							<input name="num" value="${account_detailed.num}" placeholder="交易明细编号" class="input-sm form-control" />
							<input style="margin-left: 10px;" name="financingId.billId.num" value="${account_detailed.financingId.billId.num}" placeholder="单据号" class="input-sm form-control" />
							<input style="margin-left: 10px; width: 200px; height: 29px;" id="beginDate" name="beginDate" value="${account_detailed.beginDate}" type="text" placeholder="起始日期" readOnly class="laydate-icon form-control layer-date input-sm" />
							<input style="margin-left: 10px; width: 200px; height: 29px;" id="endDate" name="endDate" value="${account_detailed.endDate}" type="text" placeholder="结束日期" readOnly class="laydate-icon form-control layer-date input-sm" />
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
									<th>交易明细编号</th>
									<th>单据号</th>
									<th>交易金额（元）</th>
									<th>交易时间</th>
									<th>账户类型</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="account_detailed">
									<tr>
										<td><input type="checkbox" id="${account_detailed.id}" class="i-checks"></td>
										<td>${account_detailed.num}</td>
										<td>${account_detailed.financingId.billId.num}</td>
										<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${account_detailed.amount}" /></td>
										<td><fmt:formatDate value="${account_detailed.time}" type="both"/></td>
										<td>
											<c:if test="${account_detailed.type == 0}">
												融资账户
											</c:if>
											<c:if test="${account_detailed.type == 1}">
												放款账户
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
	
	<script>
		document.onreadystatechange = subSomething;//当页面加载状态改变的时候执行这个方法. 
		function subSomething() 
		{ 
			if(document.readyState == "complete") //当页面加载状态
				parent.setIframeHeight($(".wrapper").height());
		} 
	</script>
</body>
</html>