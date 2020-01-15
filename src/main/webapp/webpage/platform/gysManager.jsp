<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--企业管理--供应商管理</title>
	<meta name="decorator" content="default"/>
	<script>
		$(function(){
			if('${str}'=='0'){
				$("h5:eq(0)").children().html('核心企业管理').attr("href","${ctx}/CoreEnterpriseCtrl/platform-index");
			}
		})
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
	        
	        $("h5:eq(1)").hide();
	        
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
				<h5><a href="${ctx}/gys/gys-index">供应商管理</a></h5>
				<h5><a href="${ctx}/CoreEnterpriseCtrl/platform-index">核心企业管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;供应商列表</span>
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
					<form:form style="padding-left: 15px; margin-bottom: 5px;" modelAttribute="supplier_enterprise" id="searchForm" action="${ctx}/gys/gys-index" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
						<div class="form-group">
							<input name="name" value="${supplier_enterprise.name}" placeholder="供应商名称" class="input-sm form-control" />
							<input value="${supplier_enterprise.agencyName}" style="margin-left: 10px;" name="agencyName" placeholder="操作员姓名" class="input-sm form-control" />
							<input value="${supplier_enterprise.agencyPhone}" style="margin-left: 10px;" name="agencyPhone" placeholder="手机号" class="input-sm form-control" />
							<input name="coresupplier.coreEnterpriseId.id"  type="hidden" value="${supplier_enterprise.coresupplier.coreEnterpriseId.id}"  />
						
						</div>
						<div class="form-group" style="display: block; margin-top: 10px;">
							<input name="agencyEmail" value="${supplier_enterprise.agencyEmail}" type="text" placeholder="邮箱 " class="input-sm form-control" />
							<input style="margin-left: 10px; width: 200px; height: 29px;" id="beginDate" value="${supplier_enterprise.beginDate}" name="beginDate" type="text" placeholder="起始日期" readOnly class="laydate-icon form-control layer-date input-sm" />
							<input style="margin-left: 10px; width: 200px; height: 29px;" id="endDate" value="${supplier_enterprise.endDate}" name="endDate" type="text" placeholder="结束日期" readOnly class="laydate-icon form-control layer-date input-sm" />
							<select style="margin-left: 10px; padding:0 12px; width: 200px;" id="state" name="state" val="${supplier_enterprise.state}" class="input-sm form-control input-s-sm inline">
                      			<option value="">所有状态</option>
                      			<option value="0">待提交资料</option>
                      			<option value="1">待平台审核</option>
                    			<option value="2">审核不通过</option>
                      			<option value="3">待签约</option>
                      			<option value="4">已签约待银行授信</option>
                    		</select>
						</div>
					</form:form>
			        <div class="col-sm-12" style="margin-top: 10px; margin-bottom: 10px;">
			        	<div class="pull-left">
							<h4>
								待提交资料的有<font color="red">${sta0}</font>个，
					           	待审核的有<font color="red">${sta1}</font>个，
					           	待签约的有<font color="red">${sta2}</font>个，
					           	已签约待银行授信的有<font color="green">${sta3}</font>个
			              	</h4>
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
									<th>供应商名称</th>
									<th>负责人姓名</th>
									<th>手机号</th>
									<th>邮箱</th>
									<th>是否允许邀请供应商</th>
									<th>认证状态</th>
									<th>总额度（元）</th>
									<th>可用额度（元）</th>
									<th>年化利率（%）</th>
									<th>融资比例（%）</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="supplier_enterprise">
									<tr>
										<td><input type="checkbox" id="${supplier_enterprise.id}" class="i-checks"></td>
										<td>${supplier_enterprise.name}</td>
										<td>${supplier_enterprise.agencyName}</td>
										<td>${supplier_enterprise.agencyPhone}</td>
										<td>${supplier_enterprise.agencyEmail}</td>
										<td>
											<c:if test="${'1' != supplier_enterprise.isYqgys}">
												<span class="badge">否</span>
											</c:if>
											<c:if test="${supplier_enterprise.isYqgys == '1'}">
												<span class="badge">是</span>
											</c:if>
										</td>
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

											<c:if test="${supplier_enterprise.state == '-1'}">
												<span class="badge badge-primary">实名认证</span>
											</c:if>
											<c:if test="${supplier_enterprise.state == '4'}">
												<span class="badge badge-success">已签约待银行授信</span>
											</c:if>
											<c:if test="${supplier_enterprise.state == '5'}">
												<span class="badge">授信通过</span>
											</c:if>
											<c:if test="${supplier_enterprise.state == '6'}">
												<span class="badge badge-primary">银行授信不通过</span>
											</c:if>
										</td>
										<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${supplier_enterprise.paramsId.allQuota}" /></td>
										<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${supplier_enterprise.paramsId.availableQuota}" /></td>
										<td>${supplier_enterprise.paramsId.interestRate}</td>
										<td>${supplier_enterprise.paramsId.financingRatio}</td>
										<td>
			                            	<a href="${ctx}/gys/form?id=${supplier_enterprise.id}" class="btn btn-primary btn-xs">编辑</a>
					                    	<a href="${ctx}/gys/gys-info?id=${supplier_enterprise.id}" class="btn btn-info btn-xs">详情</a>
					                    	<c:if test="${supplier_enterprise.state == 1}">
					                    		<a href="${ctx}/gys/gysShenHe?id=${supplier_enterprise.id}" class="btn btn-warning btn-xs">审核</a>
					                    	</c:if>
					                    	<c:if test="${supplier_enterprise.state == 5}">
					                    		<a href="javascript:void(0);" onclick="openDialogView('查看参数', '${ctx}/gys/showParam?id=${supplier_enterprise.id}', '800px', '500px')" class="btn btn-primary btn-xs config">查看参数</a>
					                    	</c:if>
					                    	<c:if test="${supplier_enterprise.isYqgys == '1'}">
					                    		<a href="${ctx}/gys/is-yxyqgys?isYqgys=0&id=${supplier_enterprise.id}" class="btn btn-warning btn-xs">不允许邀请供应商</a>
					                    	</c:if>
					                    	<c:if test="${supplier_enterprise.isYqgys != '1'}">
					                    		<a href="${ctx}/gys/is-yxyqgys?isYqgys=1&id=${supplier_enterprise.id}" class="btn btn-warning btn-xs">允许邀请供应商</a>
					                    	</c:if>
					                    	<%-- <a href="javascript:void(0);" onclick="openDialogView('合同管理', '${ctx}/gys/gysHeTong?id=${supplier_enterprise.id}', '800px', '500px')" class="btn btn-primary btn-xs contract">合同管理</a> --%>
					                        <a href="${ctx}/gys/userlist?supplier.id=${supplier_enterprise.id}" class="btn btn-primary btn-xs">查看员工</a>
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