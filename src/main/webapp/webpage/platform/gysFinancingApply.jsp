<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商--应付账款管理--融资申请</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script>
		$(function(){
			//追加申请日期
			var nowDate = new Date();
			$("#apply-date").text("申请日期：" + nowDate.getFullYear() + "-" + ((nowDate.getMonth() + 1) > 9 ? (nowDate.getMonth() + 1) : "0" + (nowDate.getMonth() + 1)) + "-" + (nowDate.getDate() > 9 ? nowDate.getDate() : "0" + nowDate.getDate()));
		
			//计算本次可融资金额
			var billAmount = $("#bill-amount").val();
			var finacingRatio = $("#finacing-ratio").val();
			var availableQuota = $("#available-quota").val();
			
			if(billAmount != "" && billAmount != undefined){
				if(finacingRatio != "" && finacingRatio != undefined){
					billAmount = parseFloat(billAmount) * (parseFloat(finacingRatio) / 100);
				}
				if(availableQuota != "" && availableQuota != undefined){
					if(parseFloat(billAmount) > parseFloat(availableQuota)){
						billAmount = parseFloat(availableQuota);
					}
				}
				$("#can-finacing-amount").text(billAmount.toFixed(2));
			}
		});
		
		//申请融资
		function financingApply(){
			if($("#report1").val() == undefined || $("#report1").val() == null && $("#report1").val() == ""){
				layer.msg('请上传征信报告授权书！', {shift: 6});
				return;
			}
			if($("#business1").val() == undefined || $("#business1").val() == null && $("#business1").val() == ""){
				layer.msg('请上传商务合同！', {shift: 6});
				return;
			}
			if(!$("#isagree").is(':checked')){
				layer.msg('请先勾选同意《银行开户协议》！', {shift: 6});
				return;
			}
			if($("#can-finacing-amount").text() == "0"){
				layer.msg('本次可融资金额为0时，无法融资！', {shift: 6});
				return;
			}
			if($("#total-amount").val() == undefined || $("#total-amount").val() == null || $("#total-amount").val() == ""){
				layer.msg('请填写申请融资金额！', {shift: 6});
				return;
			}
			if(!/^(([1-9]{1}\d*)|(0{1}))(\.\d{0,2})?$/.test($("#total-amount").val())){

				layer.msg('申请融资金额填写不正确！', {shift: 6});
				return;
			}
			if(parseInt($("#total-amount").val()) > parseInt($("#can-finacing-amount").text())){
				layer.msg('申请融资金额不能大于可申请融资金额！', {shift: 6});
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
				<h5><a href="${ctx}/yfzk/gyslist?supplierEnterpriseId.id=${fns:getUser().supplier.id}">应付账款管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;融资申请</span>
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
				<form name="mgmtform" id="mgmtform" enctype="multipart/form-data" action="${ctx}/rzglall/financing-apply" accept-charset="UTF-8" method="post">
					<input type="hidden" name="billId.id" value="${bill_info.id}" />
					<input type="hidden" id="bill-amount" value="${bill_info.amount}" />
					<input type="hidden" id="finacing-ratio" value="${bill_info.supplierEnterpriseId.paramsId.financingRatio}" />
					<input type="hidden" id="available-quota" value="${bill_info.supplierEnterpriseId.paramsId.availableQuota}" />
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
    										<td>${bill_info.num}</td>
    										<td><fmt:formatDate value="${bill_info.startDate}" pattern="yyyy-MM-dd"/></td>
    										<td><fmt:formatDate value="${bill_info.endDate}" pattern="yyyy-MM-dd"/></td>
    										<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${bill_info.amount}" /></td>
    										<td>${bill_info.dayDiffValue}</td>
    										<td><font color="#3e70ca">${bill_info.hxqyVoucherCount}</font></td>
    										<td><a href="javascript:void(0);" onclick="openDialogView('查看供应商凭证', '${ctx}/yfzk/to-showSupplierVoucher?id=${bill_info.id}', '800px', '600px');"><font color="#3e70ca"><span id="cnt923">${bill_info.gysVoucherCount}</span></font></a></td>
    										<td><a href="javascript:void(0);" onclick="openDialog('上传凭证', '${ctx}/yfzk/to-gysUploadVoucher?id=${bill_info.id}', '800px', '600px', 'content-frame');" class="yao1 upload_bills" style="padding: 2px; width: 75px;">上传凭证</a><a href="javascript:void(0);" onclick="openDialogView('查看核心凭证', '${ctx}/yfzk/to-showCoreVoucher?id=${bill_info.id}', '800px', '600px');" class="yao7 show_bills" style="margin-left: 10px;">查看核心凭证</a></td>
  										</tr>
									</tbody>
								</table>
      							<div class="clear"></div>
    						</div>
    
    						<div class="account_title"><span></span>征信报告查询授权书</div>
    						<div class="account_chuan">
    							<input type="file" id="report1" name="file" style="color: #002000;" value="" />
    						</div>
    
    						<div class="account_title"><span></span>商务合同</div>
    						<div class="account_chuan">
           						<input type="file" id="business1" name="file" style="color: #002000;" value="" />
       						</div>
    						<div class="account_yue">
    							<input id="isagree" name="isagree" type="checkbox" class="checkbix" style="margin-bottom: 5px;" />
    							<label role="checkbox" for="checkbox" class="checkbix">
    								<span class=""></span>
    							</label>我已阅读并同意
    							<a href="javascript:void(0);">《银行开户协议》</a>
    						</div>
    						<div class="account_bot clear" style="height: 57px; line-height: 40px;">
    							<a href="javascript:void(0);" id="apply" onclick="financingApply()" style="margin-right: 18px;">立即申请</a>
    							<span class="account_day" id="apply-date" style="width: auto;"></span>
    							<span class="account_day" style="width: auto; margin-left: 15px;">单据总金额：<b>${bill_info.amount}</b> 元</span>
    							<span class="account_day" style="width: auto; margin-left: 15px;">本次可融资金额：<b id="can-finacing-amount">0</b> 元</span>
    							<span class="account_day" style="width: auto; margin-left: 15px;">本次申请金额：<input type="text" id="total-amount" style="width: 100px; height: 25px; margin-top: -2px; text-indent: 10px;" name="totalAmount" /> 元</span>
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