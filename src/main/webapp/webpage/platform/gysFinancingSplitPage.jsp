<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商--应收账款管理--信链付拆分</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script>
		var validateForm;
		
		//供回调提交表单
		function doSubmit(){
	        if(validateForm.form()){
	        	//获取数据
	        	var params = "";
	        	var tempAmount = 0;
	        	var billAmount = $("#billAmount").val();
	        	$(".popup_hang").not(":first").each(function(){
	        		var amount = $(this).find("input").val();
	        		var gysId = $(this).find("select").val();
	        		if(amount != "" && amount != null && amount != undefined
	        				&& gysId != null && gysId != undefined && gysId != ""
	        				&& /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/.test(amount)){
	        			tempAmount += parseFloat(amount);
	        			params += amount + ";" + gysId + ",";
	        		}
	        	});
	        	
	        	if(params == ""){
	        		layer.msg("请补充信链付拆分信息！", {shift: 6});
	        		return false;
	        	}
	        	
	        	if(billAmount != null && billAmount != undefined && billAmount != ""){
	        		if(tempAmount > parseFloat(billAmount)){
		        		layer.msg("信链付拆分金额总和不能超过单据总额！", {shift: 6});
		        		return false;
		        	}
	        	}
	        	
	        	if(params.length > 0){
	        		params = params.substring(0, params.length - 1);
	        	}
	        	$("#params").val(params);
	        	
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
	        
	        deleteItem();
		});
		
		//添加列表项
		function addItem(){
			var item = $("#item-temp").clone();
			$(item).show();
			$(".lis").append(item);
		}
		
		//删除列表项
		function deleteItem(){
			$(".delete-item").live("click", function(){
				$(this).parent().remove();
			});
		}
	</script>
</head>
<body style="background-color: #fff;">
	<form name="inputForm" id="inputForm" class="form-horizontal" action="${ctx}/financingSplitCtrl/gysFinancingSplit" accept-charset="UTF-8" method="post">
		<input type="hidden" id="billId" name="billId.id" value="${billInfo.id}" />
		<input type="hidden" id="billAmount" name="billId.amount" value="${billInfo.amount}" />
    	<input type="hidden" id="params" name="params" />
    	
    	<a href="javascript:void(0);" onclick="addItem()" style="text-align: center; margin-top: 10px;" class="yao1">添加</a>
    	
    	<div class="lis" style="margin-left: 10px; margin-right: 10px;">
    		<div id="item-temp" class="popup_hang" style="width: 100%; padding-left: 0; display: none;">
	       		<span style="float: left; margin-left: 20px;">拆分金额(元)：</span>
	       		<input type="text" class="text1" value="" placeholder="请输入拆分金额（元）" />
	       		
	       		<span style="margin-left: 30px;">下级供应商：</span>
	       		<select class="form-control" val="" style="width: 200px;">
			        <option value="">请选择</option>
			        <c:forEach items="${supplierChilds}" var="supplier_child">
			        	<c:if test="${supplier_child.supplierChildId.state != 0 && supplier_child.supplierChildId.state != 1 && supplier_child.supplierChildId.state != 2}">
			        		<option value="${supplier_child.supplierChildId.id}">${supplier_child.supplierChildId.name}</option>
			        	</c:if>
	       			</c:forEach>
	      		</select>
	      		
	      		<a href="javascript:void(0);" style="text-align: center; margin-right: 20px; float: right; height: 30px; margin-top: 6px; line-height: 27px;" class="yao8 delete-item">删 除</a>
	   		</div>
    		<c:forEach items="${financingSplits}" var="financing_split">
    			<div class="popup_hang" style="width: 100%; padding-left: 0;">
		       		<span style="float: left; margin-left: 20px;">拆分金额(元)：</span>
		       		<input type="text" class="text1" value="${financing_split.amount}" placeholder="请输入拆分金额（元）" />
		       		
		       		<span style="margin-left: 30px;">下级供应商：</span>
		       		<select class="form-control" val="" style="width: 200px;">
		       			<option value="">请选择</option>
		       			<c:forEach items="${supplierChilds}" var="supplier_child">
		       				<c:if test="${supplier_child.supplierChildId.state != 0 && supplier_child.supplierChildId.state != 1 && supplier_child.supplierChildId.state != 2}">
		       					<c:if test="${supplier_child.supplierChildId.id == financing_split.supplierChildId.id}">
			       					<option selected value="${supplier_child.supplierChildId.id}">${supplier_child.supplierChildId.name}</option>
			       				</c:if>
			       				<c:if test="${supplier_child.supplierChildId.id != financing_split.supplierChildId.id}">
			       					<option value="${supplier_child.supplierChildId.id}">${supplier_child.supplierChildId.name}</option>
			       				</c:if>
		       				</c:if>
		       			</c:forEach>
		      		</select>
		      		
		      		<a href="javascript:void(0);" style="text-align: center; margin-right: 20px; float: right; height: 30px; margin-top: 6px; line-height: 27px;" class="yao8 delete-item">删 除</a>
		   		</div>
    		</c:forEach>
    	</div>
	</form>
</body>
</html>