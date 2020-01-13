<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商--融资管理--融资签约</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script>
		$(function(){
			//追加申请日期
			var nowDate = new Date();
			$("#apply-date").text("申请日期：" + nowDate.getFullYear() + "-" + ((nowDate.getMonth() + 1) > 9 ? (nowDate.getMonth() + 1) : "0" + (nowDate.getMonth() + 1)) + "-" + (nowDate.getDate() > 9 ? nowDate.getDate() : "0" + nowDate.getDate()));
		});
		
		//融资签约
		function financingContract(){
			if(!$("#isagree").is(':checked')){
				layer.msg('请先勾选"签订合同（打开合同签订的页面，生成电子印章）"！', {shift: 6});
				return;
			}
			$("#mgmtform").submit();
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5><a href="${ctx}/rzglall/gyslist?billId.supplierEnterpriseId.id=${fns:getUser().supplier.id}">融资管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;融资签约</span>
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
				<form name="mgmtform" id="mgmtform" enctype="multipart/form-data" action="${ctx}/rzglall/financing-contract" accept-charset="UTF-8" method="post">
					<input type="hidden" name="id" value="${financing_info.id}" />
					<div class="main_body clear" style="width: 100%; margin: 0 auto 0 auto;">
  						<div class="supplier clear" style="width: 100%; top: 0px; padding: 0 30px 0 30px;">  
    						<div class="account_title"><span></span>应收账款表</div>
    						<div class="financing_he clear" style="padding:20px 0 10px 0;">
      							<table width="100%" border="0" cellpadding="0" cellspacing="0">
      								<thead>
      									<tr class="financing_he_head" style="height: 30px; font-size: 1.3em;">
									    	<th style="text-align: center;">单据号</th>
										    <th style="text-align: center;">单据起始日</th>
										    <th style="text-align: center;">单据到期日</th>
										    <th style="text-align: center;">单据金额（元）</th>
										    <th style="text-align: center;">剩余融资天数（天）</th>
										    <th style="text-align: center;">核心凭证数（张）</th>
										    <th style="text-align: center;">已上传凭证（张）</th>
										    <th style="text-align: center;">操作</th>
							  			</tr>
      								</thead>
  									<tbody>
  										<tr>
    										<td>${financing_info.billId.num}</td>
    										<td><fmt:formatDate value="${financing_info.billId.startDate}" pattern="yyyy-MM-dd"/></td>
    										<td><fmt:formatDate value="${financing_info.billId.endDate}" pattern="yyyy-MM-dd"/></td>
    										<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${financing_info.billId.amount}" /></td>
    										<td>${financing_info.billId.dayDiffValue}</td>
    										<td><a href="javascript:void(0);" onclick="openDialogView('查看核心凭证', '${ctx}/yfzk/to-showCoreVoucher?id=${financing_info.billId.id}', '800px', '600px');"><font color="#3e70ca">${financing_info.billId.hxqyVoucherCount}</font></a></td>
    										<td><a href="javascript:void(0);" onclick="openDialogView('查看供应商凭证', '${ctx}/yfzk/to-showSupplierVoucher?id=${financing_info.billId.id}', '800px', '600px');"><font color="#3e70ca"><span id="cnt923">${financing_info.billId.gysVoucherCount}</span></font></a></td>
    										<td><a href="javascript:void(0);" onclick="openDialogView('单据详情', '${ctx}/yfzk/redirect-info?id=${financing_info.billId.id}', '1000px', '600px')" class="yao1 upload_bills" style="padding: 2px; width: 75px;">单据详情</a></td>
  										</tr>
									</tbody>
								</table>
      							<div class="clear"></div>
    						</div>
    
    						<div class="account_title"><span></span>征信报告查询授权书</div>
    						<c:forEach items="${zxContract}" var="zx">
    							<div class="account_chuan">
    								<c:if test="${empty zx.name}">《征信报告》</c:if>
    								<c:if test="${not empty zx.name}">《${zx.name}》</c:if>
   									<a href="${zx.url}" target="_blank">下载查看</a>
   								</div>
    						</c:forEach>
    
    						<div class="account_title"><span></span>商务合同</div>
    						<c:forEach items="${swContract}" var="sw">
    							<div class="account_chuan">
    								<c:if test="${empty sw.name}">《商务合同》</c:if>
    								<c:if test="${not empty zx.name}">《${sw.name}》</c:if>
   									<a href="${sw.url}" target="_blank">下载查看</a>
   								</div>
    						</c:forEach>
   							
    						<div class="account_yue">
    							<input id="isagree" name="isagree" type="checkbox" class="checkbix" style="margin-bottom: 5px;" />
    							<label role="checkbox" for="checkbox" class="checkbix">
    								<span class=""></span>
    							</label>
    							<a href="javascript:void(0);">签订合同（打开合同签订的页面，生成电子印章）</a>
    						</div>
    						<div class="account_bot clear" style="height: 57px; line-height: 40px;">
    							<a href="javascript:void(0);" id="apply" onclick="financingContract()" style="margin-right: 18px;">同意签约</a>
    							
    							<span class="account_day" style="width: auto;" id="apply-date"></span>
    							<span class="account_day" style="width: auto; margin-left: 15px;">单据总金额：<b><fmt:formatNumber type="currency" currencySymbol="￥" value="${financing_info.billId.amount}" /></b>元</span>
    							<span class="account_day" style="width: auto; margin-left: 15px;">申请融资金额：<b id="can-finacing-amount"><fmt:formatNumber type="currency" currencySymbol="￥" value="${financing_info.totalAmount}" /></b> 元</span>
    						</div>
    						<div class="clear"></div>
  						</div>
  						<div class="clear"></div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>