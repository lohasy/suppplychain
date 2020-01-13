<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--企业管理--核心企业配置参数</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
			if(validateForm.form()){
				$("#remindRepayment").val($("#repayment_remind_day1").val()+","+$("#repayment_remind_day2").val()+","+$("#repayment_remind_day3").val()+","+$("#repayment_remind_hour").val());
				$("#inputForm").submit();
            	return true;
            }
			return false;
		}
		
		$(document).ready(function() {
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
<body style="background-color: #fff; ">
	<div>	
		<!--主体区-->
		<div class="wrapper wrapper-content fadeInRight">
  			<form name="inputForm" id="inputForm" class="form-horizontal" action="${ctx}/CoreEnterpriseCtrl/saveInfo" accept-charset="UTF-8" method="post">
  				<h4>额度</h4>
  				<input type="hidden" id="id" name="id"  value="${enterprise_params.id}"/>
      			<div class="form-group">
        			<label class="col-sm-3 control-label">额度（元）：<span class="text-warning">*</span></label>
			        <div class="col-sm-4">
		          		<input type="text" id="allQuota" name="allQuota" class="form-control" placeholder="" value="${enterprise_params.allQuota} ">
			        </div>
     	 		</div>
      			<div class="hr-line-dashed"></div>

      			<h4>放款账户</h4>
      			<div class="form-group">
        			<label class="col-sm-3 control-label">账户名：<span class="text-warning">*</span></label>
			        <div class="col-sm-4">
		          		<input type="text" id="loanName" name="loanName" class="form-control" placeholder="" value="${enterprise_params.loanName}">
			        </div>
      			</div>
      			<div class="hr-line-dashed"></div>

      			<div class="form-group">
        			<label class="col-sm-3 control-label">银行账号：<span class="text-warning">*</span></label>
        			<div class="col-sm-4">
          				<input type="text" id="loanAccount" name="loanAccount" class="form-control" placeholder="" value="${enterprise_params.loanAccount}">
        			</div>
      			</div>
      			<div class="hr-line-dashed"></div>

      			<div class="form-group">
        			<label class="col-sm-3 control-label">开户行：<span class="text-warning">*</span></label>
        			<div class="col-sm-4">
          				<input type="text" id="loanOpenBank" name="loanOpenBank" class="form-control" placeholder="" value="${enterprise_params.loanOpenBank }">
        			</div>
      			</div>
      			<div class="hr-line-dashed"></div>

      			<h4>回款至金融机构账户（银行、保理等）</h4>
      			<div class="form-group">
        			<label class="col-sm-3 control-label">账户名：<span class="text-warning">*</span></label>
			        <div class="col-sm-4">
		          		<input type="text" id="returnName" name="returnName" class="form-control" placeholder="" value="${enterprise_params.returnName }">
			        </div>
      			</div>
      			<div class="hr-line-dashed"></div>

      			<div class="form-group">
        			<label class="col-sm-3 control-label">银行账号：<span class="text-warning">*</span></label>

			        <div class="col-sm-4">
		        		<input type="text" id="returnAccount" name="returnAccount" class="form-control" placeholder="" value="${enterprise_params.returnAccount }">
			        </div>
      			</div>
      			<div class="hr-line-dashed"></div>

      			<div class="form-group">
        			<label class="col-sm-3 control-label">开户行：<span class="text-warning">*</span></label>
			        <div class="col-sm-4">
		          		<input type="text" id="returnOpenBank" name="returnOpenBank" class="form-control" placeholder="" value="${enterprise_params.returnOpenBank }">
			        </div>
      			</div>
      			<div class="hr-line-dashed"></div>


      			<div class="form-group">
        			<label class="col-sm-3 control-label">提醒核心企业还款参数</label>
	          		<div class="col-sm-2" style="padding-right: 2px;">
		            	<input type="text" id="repayment_remind_day1" name="repayment_remind_day1" class="form-control" placeholder="第1次提醒(天)" value="${repayment_remind_day1}">
	          		</div>
		          	<div class="col-sm-2" style="padding-right: 2px;">
		            	<input type="text" id="repayment_remind_day2" name="repayment_remind_day2" class="form-control" placeholder="第2次提醒(天)" value="${repayment_remind_day2}">
		          	</div>
		          	<div class="col-sm-2" style="padding-right: 2px;">
			            <input type="text" id="repayment_remind_day3" name="repayment_remind_day3" class="form-control" placeholder="第3次提醒(天)" value="${repayment_remind_day3}">
		          	</div>
		          	<div class="col-sm-2" style="padding-right: 2px;">
			            <input type="text" id="repayment_remind_hour" name="repayment_remind_hour" class="form-control" placeholder="提醒时间点" value="${repayment_remind_hour}">
			            <input type="hidden" id="remindRepayment" name="remindRepayment"/>
		          	</div>
      			</div>
      			<span class="help-block m-b-none" style="margin-left: 160px;">提示：比如单据到期日为2018年2月8号，这边配置为5天，则2月3号早上10点发送站内信和短信</span>
  			</form>
		</div>
	</div>
</body>
</html>