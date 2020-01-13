<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>核心企业--融资管理--还款</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script>
		var validateForm;
		
		//供回调提交表单
		function doSubmit(){
			var repayment_amount = $("#repayment_amount").val();
	        if (repayment_amount.replace(/\s/g, "") == "") {
	            $("#error_repayment_amount").html("<img src=\"${ctxStatic}/images/ti1.png\">输入还款金额");
	            $("#repayment_amount").focus();
	            return false;
	        }
	        var picture1 = $("#picture1").val();
	        if (picture1.replace(/\s/g, "") == "") {
	            $("#error_picture1").html("<img src=\"${ctxStatic}/images/ti1.png\">选择还款凭证照片");
	            return false;
	        }
	        if(validateForm.form()){
	        	$("#inputForm").submit();
				return true;
	        }
			return false;
		}
		
		
		$(document).ready(function() {
			$("#pic").click(function(){
				$("#picture1").click();
			});
			
			$("#picture1").change(function(){
				$("#error_picture1").html($("#picture1").val());
			});
			
	        validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body style="background-color: #fff;">
	<form name="inputForm" id="inputForm" class="form-horizontal" action="${ctx}/yfzk/hxqyBillHkSave" accept-charset="UTF-8" method="post"  enctype="multipart/form-data">
		<input type="hidden" name="str" value="2"/>
		<input type="hidden" id="id" name="id" value="${bill.id}" />
    	<input type="hidden" id="state" name="state" value="${bill.state}" />
    	<input type="hidden" id="rzid" name="rzid" value="${financing.id}" />
    	
    	<input type="hidden" id="voucher_info.id" name="voucher_info.id" value="${bill.amount}" />
    	
    	<div class="popup_tian clear" style="width: 100%;">
      		<div class="upload_ti" style="width: 100%;">根据应付账款金额为<font color="#ff0000"><fmt:formatNumber type="currency" currencySymbol="￥" value="${financing.loanAmount}" /></font>元，您应该还银行<font color="#ff0000">${financing.loanAmount}</font>元</div>
      		<div class="popup_hang" style="width: 100%;">
        		<span class="supplier_name">还款金额(元)：</span>
        		<input type="text" class="text1" id="repayment_amount" name="loanamount" readonly="readonly" value="${financing.loanAmount}" placeholder="请输入还款金额（元）" />
        		<span class="disclose_r" id="error_repayment_amount"></span>
      		</div>
      		<div class="popup_hang1" style="width: 100%;">
        		<span class="supplier_name">上传还款凭证：</span>
        		<input id="picture1" name="file" accept="image/*" type="file" style="opacity: 0;" />
        		<img class="pic" id="pic" />
        		<span class="disclose_r" id="error_picture1"></span>
      		</div>
    	</div>
    	<div class="clear"></div>
	</form>
</body>
</html>