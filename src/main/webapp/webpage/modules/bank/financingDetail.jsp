<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>银行--融资管理--融资详情</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script type="text/javascript" language="javascript">
		//校验
		$(function () {
		    $("#mgmtform").validate({
		        rules: {
		            'state': "required"
		        },
		        messages: {
		            'state': "请选择审核结果"
		        },
		        errorPlacement: function (error, element) { //指定错误信息位置
		            if (element.is(':radio') || element.is(':checkbox')) { //如果是radio或checkbox
		                var container = element.parent().parent().find('error'); //获取元素的name属性
		                error.appendTo(element.parent().parent().parent()); //将错误信息添加当前元素的父结点后面
		            } else {
		                error.insertAfter(element);
		            }
		        }
		    });
		})
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5><a href="${ctx}/bank/bankList">融资管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;融资详情</span>
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
	
				<div class="rong_nav">
					<ul>
				  		<li><a href="${ctx}/bank/toFinancingDetail?id=${financing_info.id}" class="nav_a">融资详情</a></li>
					  	<li><a href="${ctx}/bank/toSupplierDetail?id=${financing_info.id}" class="nav_b">供应商详情</a></li>
					  	<%-- <c:if test="${not empty fns:getUser().supplier.id }">
							<li><a href="${ctx}/rzglall/redirect?type=2" class="nav_b">应收账款转让合同</a></li>
						</c:if>
						<c:if test="${not empty fns:getUser().core.id }">
							<li><a href="${ctx}/rzglall/redirect?type=2" class="nav_b">应付账款转让合同</a></li>
						</c:if>
						<c:if test="${empty fns:getUser().supplier.id and empty fns:getUser().core.id }">
							<li><a href="${ctx}/rzglall/redirect?type=2" class="nav_b">单据账款转让合同</a></li>
						</c:if> --%>
			    		<li><a href="${ctx}/rzglall/redirect?type=5" class="nav_b">融资合同</a></li>
					</ul>
				</div>
				
				<div class="financing_xi_title" style="margin-top: 15px; margin-bottom:15px;">
					<div class="financing_xi_jin">
				  		<span>融资总金额（元）：<font color="#ff0000">${financing_info.totalAmount}</font></span>
				  		<span>利率（%）：<font color="#ff0000">${financing_info.interestRate}</font></span>
				  		<span>融资比例（%）： <font color="#ff0000">${financing_info.financingRatio}</font></span>
					</div>
					<span class="financing_xi_dian1"></span>
					<span class="financing_xi_name1">单据详情</span>
				</div>
				
				<!-- 查询条件 -->
				<div class="row">
					<div class="col-sm-12">
						<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
							<thead>
								<tr>
									<th>单据号</th>
									<th>单据金额（元）</th>
									<th>单据起始日期</th>
									<th>单据截止日期</th>
									<th>剩余融资天数</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${billInfo.num}</td>
									<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${billInfo.amount}" /></td>
									<td><fmt:formatDate value="${billInfo.startDate}" pattern="yyyy-MM-dd"/></td>
									<td><fmt:formatDate value="${billInfo.endDate}" pattern="yyyy-MM-dd"/></td>
									<td>${billInfo.dayDiffValue}</td>
									<td>
		                            	<a href="javascript:void(0);" onclick="openDialogView('单据详情', '${ctx}/yfzk/redirect-info?id=${billInfo.id}', '1000px', '600px')" class="yao1 bill_details" data-kid="32" data-bid="847" data-cid="1">单据详情</a>
				                    </td>
								</tr>
							</tbody>
						</table>
						<c:if test="${flag == 1}">
							<form name="mgmtform" id="mgmtform" enctype="multipart/form-data" action="${ctx}/bank/examine" accept-charset="UTF-8" method="post">
								<input name="id" type="hidden" value="${financing_info.id}" />
								<div class="financing_xi clear">
									<div class="financing_xi_title">
										<span class="financing_xi_dian4"></span><span class="financing_xi_name4">审核结果</span>
									</div>
									<div class="financing_xi_list">
										<div style="margin:8px 0px;">
											<label class="check-inline">
											  <input type="radio" value="6" id="state" name="state" class="i-checks"> 审核通过
											</label>
										</div>
										<div>
											<label class="check-inline">
											  <input type="radio" value="5" id="state" name="state" class="i-checks"> 审核不通过
											</label>
										</div>
										<textarea id="examine_remark" name="remark" class="form-control" placeholder="审核不通过补充说明" style="margin:8px 0px;"></textarea>
										<input type="submit" value="审核确定" id="do_audit" name="do_audit" class="btn btn-primary">
									</div>
								</div>
							</form>
						</c:if>
						<!-- 分页代码 -->
						<table:page page="${page}"></table:page>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>