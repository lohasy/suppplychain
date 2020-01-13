<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商--融资管理--融资列表</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script>
		$(function(){
			if($("#financing-state").val() != ""){
				$("#financing-tab-area li").removeClass("financing_cur");
				$("#financing-tab-"+ $("#financing-state").val()).addClass("financing_cur");
			}
		});
		
		//导出excel数据
		function exportExcel(){
			$("#excel-form").submit();
		}
	</script>
</head>
<body class="gray-bg">
	<div class="rong_nav">
		<ul>
			<li><a href="${ctx}/yfzk/gyslist?supplierEnterpriseId.id=${fns:getUser().supplier.id}" class="nav_b">应收账款管理</a></li>
	       	<li><a href="${ctx}/rzglall/gyslist?billId.supplierEnterpriseId.id=${fns:getUser().supplier.id}" class="nav_b">融资管理</a></li>
	       	<li style="display: none;"><a href="${ctx}/yfzk/gysYfzkList?supplierParentId.id=${fns:getUser().supplier.id}" class="nav_b">应付账款管理（下级供应商）</a></li>
	       	<li style="display: none;"><a href="${ctx}/rzglall/gysHxqylist?billId.supplierParentId.id=${fns:getUser().supplier.id}" class="nav_a">融资管理（下级供应商）</a></li>
	   	</ul>
	</div>
	<div class="wrapper wrapper-content" style="padding: 0;">
		<div class="ibox" style="margin-bottom: 0;">
			<div class="ibox-title">
				<h5><a href="${ctx}/rzglall/gysHxqylist?billId.supplierParentId.id=${fns:getUser().supplier.id}">融资管理</a></h5>
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
					<form:form style="padding-left: 15px; margin-bottom: 5px;" id="searchForm" modelAttribute="financing_info" action="${ctx}/rzglall/gysHxqylist?billId.supplierParentId.id=${fns:getUser().supplier.id}" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
						<div class="form-group">
							<input type="hidden" name="state" id="financing-state" value="${financing_info.state}" />
							<input name="num" value="${financing_info.num}" placeholder="融资编号" class="input-sm form-control" />
							<input style="margin-left: 10px;" name="billId.supplierEnterpriseId.name" value="${financing_info.billId.supplierEnterpriseId.name}" placeholder="供应商名称" class="input-sm form-control" />
                    	</div>
					</form:form>
					<div class="col-sm-12" style="margin-top: 10px; margin-bottom: 10px;">
						<div class="pull-left">
							<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
							<button style="margin-left: 10px;" class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
						</div>
					</div>
					<div class="financing_nav clear" style="width: auto; margin-left: 15px; margin-right: 15px;">
		          		<ul id="financing-tab-area">
			            	<li id="financing-tab" class="financing_cur"><a href="${ctx}/rzglall/gysHxqylist?billId.supplierParentId.id=${fns:getUser().supplier.id}">全部</a></li>
			            	<li id="financing-tab-4"><a href="${ctx}/rzglall/gysHxqylist?state=4&billId.supplierParentId.id=${fns:getUser().supplier.id}">待银行审核</a></li>
			            	<li id="financing-tab-5"><a href="${ctx}/rzglall/gysHxqylist?state=5&billId.supplierParentId.id=${fns:getUser().supplier.id}">审核未通过</a></li>
			            	<li id="financing-tab-6"><a href="${ctx}/rzglall/gysHxqylist?state=6&billId.supplierParentId.id=${fns:getUser().supplier.id}">待银行复核</a></li>
			            	<li id="financing-tab-9"><a href="${ctx}/rzglall/gysHxqylist?state=9&billId.supplierParentId.id=${fns:getUser().supplier.id}">已放款</a></li>
			            	<li id="financing-tab-10"><a href="${ctx}/rzglall/gysHxqylist?state=10&billId.supplierParentId.id=${fns:getUser().supplier.id}">待银行清分</a></li>
			            	<li id="financing-tab-11"><a href="${ctx}/rzglall/gysHxqylist?state=11&billId.supplierParentId.id=${fns:getUser().supplier.id}">已完成</a></li>
			          	</ul>
		        	</div>
					<div class="col-sm-12" style="margin-bottom: 15px;">
						<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
							<thead>
								<tr>
									<th><input type="checkbox" class="i-checks"></th>
									<th>融资编号</th>
									<th>融资总金额（元）</th>
									<th>融资生成时间</th>
									<th>融资到期日</th>
									<th>供应商名称</th>
									<th>融资状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="financing_info">
									<tr>
										<td><input type="checkbox" id="${financing_info.id}" class="i-checks"></td>
										<td>${financing_info.num}</td>
										<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${financing_info.totalAmount}" /></td>
										<td><fmt:formatDate value="${financing_info.generationDate}" pattern="yyyy-MM-dd"/></td>
										<td><fmt:formatDate value="${financing_info.expiryDate}" pattern="yyyy-MM-dd"/></td>
										<td>${financing_info.billId.supplierEnterpriseId.name}</td>
										<td>
											<c:if test="${financing_info.state == 0}">
												待负责人审核
											</c:if>
											<c:if test="${financing_info.state == 1}">
												待签约
											</c:if>
											<c:if test="${financing_info.state == 2}">
												负责人审核不通过
											</c:if>
											<c:if test="${financing_info.state == 3}">
												待银行配置
											</c:if>
											<c:if test="${financing_info.state == 4}">
												待银行审核
											</c:if>
											<c:if test="${financing_info.state == 5}">
												银行审核不通过
											</c:if>
											<c:if test="${financing_info.state == 6}">
												待银行复核
											</c:if>
											<c:if test="${financing_info.state == 7}">
												银行复核不通过
											</c:if>
											<c:if test="${financing_info.state == 8}">
												待银行放款
											</c:if>
											<c:if test="${financing_info.state == 9}">
												已放款
											</c:if>
											<c:if test="${financing_info.state == 10}">
												待银行清分
											</c:if>
											<c:if test="${financing_info.state == 11}">
												已完成
											</c:if>
										</td>
										<td>
			                            	<a href="${ctx}/rzglall/redirect-info?id=${financing_info.id}" style="text-align: center;" class="yao9">融资详情</a>
			                            	<c:if test="${financing_info.state == 9}">
			                            		<a href="javascript:void(0);" onclick="openDialog('还款', '${ctx}/rzglall/rzglhk?id=${financing_info.id}', '700px', '500px', 'content-frame');" style="text-align: center;" class="yao8 repay">还款</a>
			                            	</c:if>
					                    </td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!-- 分页代码 -->
						<table:page page="${page}"></table:page>
					</div>
					<div class="financing_upload clear" style="width: auto; margin-left: 15px; margin-right: 15px;">
          				<form:form id="excel-form" action="${ctx}/rzglall/export?billId.supplierParentId.id=${fns:getUser().supplier.id}" method="post" modelAttribute="financing_info">
          					<input type="hidden" name="state" value="${financing_info.state}"/>
          					<a href="javascript:void(0);" onclick="exportExcel()" class="financing_up" style="width: 220px;">下载全部数据</a><span>（EXCEL格式）</span>
          				</form:form>
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