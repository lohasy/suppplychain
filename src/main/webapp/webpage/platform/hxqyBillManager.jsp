<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>核心企业--融资管理--应付账款管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script>
		$(function(){
			$("#submitBill").click(function(){
				var str="";
		 	 	var ids="";
		 	 	var count = 0;
			  	$("#contentTable tbody tr td input.i-checks:checkbox").each(function(){
			    	if(true == $(this).is(':checked')){
			    		count++;
	     	 			str += $(this).attr("id") + ",";
			    	}
			  	});
			  	if(str.substr(str.length - 1) == ','){
			    	ids = str.substr(0 , str.length-1);
			  	}
			  	if(ids == ""){
					top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
					return;
			  	}
			  	top.layer.confirm('您已选择了'+ count +'条应付账款记录，是否全部提交？一旦提交，默认同意转让应收账款。', {icon: 3, title:'提交应付账款单据'}, function(index){
			  		location.href = "${ctx}/yfzk/batch-submit?ids=" + ids;
				    top.layer.close(index);
				});
			});
			
			if($("#hxqyIsUploadVoucher").attr("val") != undefined && $("#hxqyIsUploadVoucher").attr("val") != null && $("#hxqyIsUploadVoucher").attr("val") != ""){
      			$("#hxqyIsUploadVoucher option[value="+ $("#hxqyIsUploadVoucher").attr("val") +"]")[0].selected = true;
      		}
			
			if($("#bill-state").val() != ""){
				$("#financing-tab-area li").removeClass("financing_cur");
				$("#financing-tab-"+ $("#bill-state").val()).addClass("financing_cur");
			}
		});
	
		function submitBill(id){
			top.layer.confirm('确定提交应付账款记录？一旦提交，供应商即可进行融资。', {icon: 3, title: '提交应付账款单据'}, function(index){
				location.href = "${ctx}/yfzk/batch-submit?ids=" + id;
			    top.layer.close(index);
			});
		}
		
		//导出excel数据
		function exportExcel(){
			$("#excel-form").submit();
		}
	</script>
</head>
<body class="gray-bg">
	<div class="rong_nav">
		<ul>
			<li><a href="${ctx}/yfzk/list?coreEnterpriseId.id=${fns:getUser().core.id}" class="nav_a">应付账款管理</a></li>
	       	<li><a href="${ctx}/rzglall/hxqylist?billId.coreEnterpriseId.id=${fns:getUser().core.id}" class="nav_b">融资管理</a></li>
	   	</ul>
	</div>
	<div class="wrapper wrapper-content" style="padding: 0;">
		<div class="ibox" style="margin-bottom: 0;">
			<div class="ibox-title">
				<h5><a href="${ctx}/yfzk/list?coreEnterpriseId.id=${fns:getUser().core.id}">应付账款管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;单据列表</span>
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
					<form:form style="padding-left: 15px; margin-bottom: 5px;" id="searchForm" modelAttribute="bill_info" action="${ctx}/yfzk/list?coreEnterpriseId.id=${fns:getUser().core.id}" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
						<div class="form-group">
							<input type="hidden" name="state" id="bill-state" value="${bill_info.state}" />
							<input name="num" value="${bill_info.num}" placeholder="单据号" class="input-sm form-control" />
							<input style="margin-left: 10px;" name="contractNum" value="${bill_info.contractNum}" placeholder="合同号" class="input-sm form-control" />
							<input style="margin-left: 10px;" name="supplierEnterpriseId.name" value="${bill_info.supplierEnterpriseId.name}" placeholder="供应商名称" class="input-sm form-control" />
							<select style="margin-left: 10px; padding:0 12px;" id="hxqyIsUploadVoucher" name="hxqyIsUploadVoucher" val="${bill_info.hxqyIsUploadVoucher}" class="input-sm form-control input-s-sm inline">
                      			<option value="">是否上传凭证</option>
                      			<option value="0">否</option>
                      			<option value="1">是</option>
                    		</select>
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
			            	<li id="financing-tab" class="financing_cur"><a href="${ctx}/yfzk/list?coreEnterpriseId.id=${fns:getUser().core.id}">全部</a></li>
			            	<li id="financing-tab-0"><a href="${ctx}/yfzk/list?state=0&coreEnterpriseId.id=${fns:getUser().core.id}">待核心提交</a></li>
			            	<li id="financing-tab-1"><a href="${ctx}/yfzk/list?state=1&coreEnterpriseId.id=${fns:getUser().core.id}">待供应商融资</a></li>
			            	<li id="financing-tab-11"><a href="${ctx}/yfzk/list?state=11&coreEnterpriseId.id=${fns:getUser().core.id}">银行已放款</a></li>
			          	</ul>
		        	</div>
			        <div class="col-sm-12" style="margin-top: 15px; margin-bottom: 10px;">
			        	<a href="javascript:void(0);" style="float: left;" onclick="openDialog('上传应付账款', '${ctx}/yfzk/batch-uploadBill', '600px', '400px', 'content-frame');" class="button" id="uploadBill">批量上传应付账款</a>
						<a href="javascript:void(0);" style="float: left; margin-left: 20px;" class="button1 pinvite" id="submitBill">批量提交应付账款</a>
					</div>
					<div class="col-sm-12" style="margin-bottom: 15px;">
						<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
							<thead>
								<tr>
									<th><input type="checkbox" class="i-checks"></th>
									<th>单据号</th>
									<th>合同号</th>
									<th>单据金额（元）</th>
									<th>单据起始日期</th>
									<th>单据截止日期</th>
									<th>供应商名称</th>
									<th>是否上传凭证</th>
									<th>单据状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="bill_info">
									<tr>
										<td><input type="checkbox" id="${bill_info.id}" class="i-checks"></td>
										<td>${bill_info.num}</td>
										<td>${bill_info.contractNum}</td>
										<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${bill_info.amount}" /></td>
										<td><fmt:formatDate value="${bill_info.startDate}" pattern="yyyy-MM-dd"/></td>
										<td><fmt:formatDate value="${bill_info.endDate}" pattern="yyyy-MM-dd"/></td>
										<td>${bill_info.supplierEnterpriseId.name}</td>
										<td>
											<c:if test="${bill_info.hxqyVoucherCount <= 0}">
												否
											</c:if>
											<c:if test="${bill_info.hxqyVoucherCount > 0}">
												<a href="javascript:void(0);" onclick="openDialogView('查看核心凭证', '${ctx}/yfzk/to-showCoreVoucher?id=${bill_info.id}', '800px', '600px');"><font color="#3e70ca">是</font></a>
											</c:if>
										</td>
										<td>
											<c:if test="${bill_info.state == 0}">
												待提交
											</c:if>
											<c:if test="${bill_info.state == 1}">
												待供应商融资
											</c:if>
											<c:if test="${bill_info.state == 2}">
												待供应商审核
											</c:if>
											<c:if test="${bill_info.state == 3}">
												待签约
											</c:if>
											<c:if test="${bill_info.state == 4}">
												供应商审核不通过
											</c:if>
											<c:if test="${bill_info.state == 5}">
												待银行配置
											</c:if>
											<c:if test="${bill_info.state == 6}">
												待银行审核
											</c:if>
											<c:if test="${bill_info.state == 7}">
												银行审核不通过
											</c:if>
											<c:if test="${bill_info.state == 8}">
												待银行复核
											</c:if>
											<c:if test="${bill_info.state == 9}">
												银行复核不通过
											</c:if>
											<c:if test="${bill_info.state == 10}">
												待银行放款
											</c:if>
											<c:if test="${bill_info.state == 11}">
												已放款
											</c:if>
											<c:if test="${bill_info.state == 12}">
												待银行清分
											</c:if>
											<c:if test="${bill_info.state == 13}">
												已完成
											</c:if>
										</td>
										<td>
											<c:if test="${bill_info.state == 0}">
												<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="yao2 confirm_single">提交</a>
											</c:if>
											<a href="javascript:void(0);" onclick="openDialog('上传凭证', '${ctx}/yfzk/to-hxqyUploadVoucher?id=${bill_info.id}', '800px', '600px', 'content-frame');" style="text-align: center;" class="yao1 upload_bills">上传凭证</a>
											<a href="javascript:void(0);" onclick="openDialogView('业务数据', '${ctx}/fdCtrl/findPaymentListByPage?coreEnterpriseId.id=${fns:getUser().core.id}&billId.id=${bill_info.id}', '1200px', '700px', 'content-frame');" style="text-align: center;" class="yao1 upload_bills">业务数据</a>
											<c:if test="${bill_info.state == 11}">
												<a href="javascript:void(0);" onclick="openDialog('还款', '${ctx}/yfzk/hxqyBillHk?id=${bill_info.id}', '700px', '500px', 'content-frame');" style="text-align: center;" style="text-align: center;" class="yao8 repay">还款</a>
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
						<form:form id="excel-form" action="${ctx}/yfzk/export?coreEnterpriseId.id=${fns:getUser().core.id}" method="post" modelAttribute="bill_info">
							<input type="hidden" name="state" value="${bill_info.state}" />
							<a href="javascript:;" onclick="exportExcel()" class="financing_up" style="width: 220px;">下载全部数据</a><span>（EXCEL格式）</span>
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