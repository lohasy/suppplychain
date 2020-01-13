<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商--提交资料</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css" />
	<script src="${ctxStatic}/online/lgt-web.js"></script>
	
	<link href="${ctxStatic}/online/prettyPhoto-3.1.6.css" rel="stylesheet" type="text/css">
	<script src="${ctxStatic}/online/jquery.prettyPhoto-3.1.6.js" type="text/javascript"></script>
	<script src="${ctxStatic}/online/ajaxfileupload.js"></script>
	
	<script>
		var wait = 60;
		var scrollTop = 0;
		var validateForm;
		
		function time(o) {
	    	if (wait == 0) {
	        	o.removeAttribute("disabled");
	        	o.value = "获取验证码";
	        	o.style.background = "#f08519";
	        	wait = 60;
	    	} else {
	        	o.setAttribute("disabled", true);
	        	o.value = "重新发送(" + wait + ")";
	        	o.style.background = "#bbbbbb";
	        	wait--;
	        	setTimeout(function () {
	      			time(o);
	           	}, 1000);
	    	}
		}
	
		$(function(){
			getProvinceAreaSelect();
			
			$("a[rel^='prettyPhoto']").prettyPhoto();
			
			//处理状态
			if(!$.isEmpty($("#orgState").val())){
				if($("#orgState").val() == "1"){
					$("#title_nav2").html('<span>企业资料提交</span><label></label><span class="steps_cur">审核资料</span><label></label><span>审核通过</span>');
				}
				if($("#orgState").val() == "2"){
					$("#title_nav2").html('<span class="steps_cur">重新提交资料</span><label></label><span>审核资料</span><label></label><span>审核通过</span>');
				}
				if($("#orgState").val() == "3" && !$.isEmpty($("#userId").val())){
					location.href = "${ctx}/sys/register/to-supplierContract?id="+ $("#userId").val();;
				}
				if($("#orgState").val() != "0" && $("#orgState").val() != "1" && $("#orgState").val() != "2" && $("#orgState").val() != "3"){
					location.href = "${ctx}/logout";
				}
			}
			
			if($.isEmpty($("#orgId").val())){
				location.href = "${ctx}/logoutr";
			}
			
			if(!$.isEmpty($("#message").val())){
				layer.msg($("#message").val(), {shift: 6});
				
				if($("#message").val() == "资料提交成功，等待平台审核！" && !$.isEmpty($("#userId").val())){
					setTimeout(function(){
						location.href = "${ctx}/sys/register/to-supplierSubmitData?id="+ $("#userId").val();
					},2000);
				}else if(!$.isEmpty($("#userId").val())){
					location.href = "${ctx}/sys/register/to-supplierSubmitData?id="+ $("#userId").val();
				}
			}
			
			validateForm = $("#inputform").validate({
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
			
	        $("#pic").click(function () {
	            $("#picture_license").click(); //隐藏了input:file样式后，点击头像就可以本地上传
	            $("#picture_license").on("change", function () {
	            	var loading = layer.load();
	            	$.ajaxFileUpload({
	        			url: "${ctx}/sys/register/businessLicense-distinguish",
	        			fileElementId: "picture_license",
	        	        type: 'post',
	        	        secureuri: false, 
	        	        dataType: 'json', 
	        	        success: function(json, status){
	        	        	layer.close(loading);
	        	        	if(json != null && json.hasOwnProperty("msg")){
	        	        		var msg = json["msg"];
	        	        		var data = json["data"];
	        	        		var url = json["url"];
	        	        		if(msg == "OK" && !$.isEmpty(url)){
	        	        			if(data != null && data != undefined && data.hasOwnProperty("success") && data["success"]){
	        	        				layer.msg("营业执照上传并识别成功！", {shift: 6});
		        	        			
		        	        			//营业执照信息展现
		        	        			var name = data["name"];
		        	        			var reg_num = data["reg_num"];
		        	        			var valid_period = data["valid_period"];
		        	        			var capital = data["capital"];
		        	        			var type = data["type"];
		        	        			var address = data["address"];
		        	        			if(!$.isEmpty(name) && name != "FailInRecognition"){
		        	        				$("#company_name").val(name);
		        	        			}else{
		        	        				$("#company_name")[0].readOnly = false;
		        	        			}
		        	        			if(!$.isEmpty(reg_num) && reg_num != "FailInRecognition"){
		        	        				$("#org_code").val(reg_num);
		        	        			}else{
		        	        				$("#org_code")[0].readOnly = false;
		        	        			}
		        	        			if(!$.isEmpty(reg_num) && reg_num != "FailInRecognition" && reg_num.length >= 8){
		        	        				$("#license_expired_date").val(valid_period.substring(0, 4) + "-" + valid_period.substring(4, 6) + "-" + valid_period.substring(6, 8));
		        	        			}else{
		        	        				$("#license_expired_date")[0].readOnly = false;
		        	        			}
		        	        			if(!$.isEmpty(capital) && capital != "FailInRecognition"){
		        	        				$("#registered_capital").val(capital);
											$("#registered_capital")[0].readOnly = false;
		        	        			}else{
		        	        				//todo zuo
		        	        				// $("#registered_capital")[0].readOnly = false;
		        	        			}
		        	        			if(!$.isEmpty(type) && type != "FailInRecognition"){
		        	        				$("#company_type option").each(function(){
		        	        					if(type.indexOf($(this).text()) != -1){
		        	        						this.selected = true;
		        	        					}
		        	        				});
		        	        			}else{
		        	        				$("#company_type").removeAttr("disabled");
		        	        			}
		        	        			if(!$.isEmpty(address) && address != "FailInRecognition"){
		        	        				$("#address").val(address);
		        	        			}else{
		        	        				$("#address")[0].readOnly = false;
		        	        			}
	        	        			}
	        	        			$("#pic").attr("src", url);
	        	        			$("#businessLicense").val(url);
	        	        		}else{
	        	        			layer.msg(msg, {shift: 6});
	        	        		}
	        	        	}else{
	        	        		layer.msg(json, {shift: 6});
	        	        	}
	        	        }
	        		});
	            });
	        });
	        
	        $("#pic1").click(function () {
	            $("#picture_legal_front").click(); //隐藏了input:file样式后，点击头像就可以本地上传
	            $("#picture_legal_front").on("change", function () {
	            	var loading = layer.load();
	            	$.ajaxFileUpload({
	        			url: "${ctx}/sys/register/idCard-distinguish?type=face",
	        			fileElementId: "picture_legal_front",
	        	        type: 'post',
	        	        secureuri: false, 
	        	        dataType: 'json', 
	        	        success: function(json, status){
	        	        	layer.close(loading);
	        	        	if(json != null && json.hasOwnProperty("msg")){
	        	        		var msg = json["msg"];
	        	        		var data = json["data"];
	        	        		var url = json["url"];
	        	        		if(msg == "OK" && !$.isEmpty(url)){
	        	        			if(data != null && data != undefined && data.hasOwnProperty("success") && data["success"]){
	        	        				layer.msg("身份证正面上传并识别成功！", {shift: 6});
		        	        			//身份证信息展现
		        	        			$("#legalCardPositive_ul").show();
		        	        			$("#legalCardPositive_ul").empty();
		        	        			$("#legalCardPositive_ul").append(
		        	        				'<li style="text-indent: 10px;"> 姓名：'+ data["name"] +'<input type="hidden" name="supplierEnterpriseId.legalName" value="'+ data["name"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 性别：'+ data["sex"] +'<input type="hidden" name="supplierEnterpriseId.legalSex" value="'+ (data["sex"] == "男"? "0" : "1") +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 民族：'+ data["nationality"] +'<input type="hidden" name="supplierEnterpriseId.legalNation" value="'+ data["nationality"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;">身份证：'+ data["num"] +'<input type="hidden" name="supplierEnterpriseId.legalIdCard" value="'+ data["num"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 地址：'+ data["address"] +'<input type="hidden" name="supplierEnterpriseId.legalAddress" value="'+ data["address"] +'" /></li>'
		        	        			);
	        	        			}
	        	        			$("#pic1").attr("src", url);
	        	        			$("#legalCardPositive").val(url);
	        	        		}else{
	        	        			layer.msg(msg, {shift: 6});
	        	        		}
	        	        	}else{
	        	        		layer.msg(json, {shift: 6});
	        	        	}
	        	        }
	        		});
	            });
	        });
	        
	        $("#pic2").click(function () {
	            $("#picture_legal_back").click(); //隐藏了input:file样式后，点击头像就可以本地上传
	            $("#picture_legal_back").on("change", function () {
	            	var loading = layer.load();
	            	$.ajaxFileUpload({
	        			url: "${ctx}/sys/register/idCard-distinguish?type=back",
	        			fileElementId: "picture_legal_back",
	        	        type: 'post',
	        	        secureuri: false, 
	        	        dataType: 'json', 
	        	        success: function(json, status){
	        	        	layer.close(loading);
	        	        	if(json != null && json.hasOwnProperty("msg")){
	        	        		var msg = json["msg"];
	        	        		var data = json["data"];
	        	        		var url = json["url"];
	        	        		if(msg == "OK" && !$.isEmpty(url)){
	        	        			if(data != null && data != undefined && data.hasOwnProperty("success") && data["success"]){
	        	        				layer.msg("身份证反面上传并识别成功！", {shift: 6});
		        	        			//身份证信息展现
		        	        			$("#legalCardBack_ul").show();
		        	        			$("#legalCardBack_ul").empty();
		        	        			$("#legalCardBack_ul").append(
		        	        				'<li style="text-indent: 10px;">  有效期：'+ (data["start_date"] + " 至 " + data["end_date"]) +'<input type="hidden" name="supplierEnterpriseId.legalCardValidity" value="'+ (data["start_date"] + " 至 " + data["end_date"]) +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 签发机关：'+ data["issue"] +'<input type="hidden" name="supplierEnterpriseId.legalCardOffice" value="'+ data["issue"] +'" /></li>'
		        	        			);
	        	        			}
	        	        			$("#pic2").attr("src", url);
	        	        			$("#legalCardBack").val(url);
	        	        		}else{
	        	        			layer.msg(msg, {shift: 6});
	        	        		}
	        	        	}else{
	        	        		layer.msg(json, {shift: 6});
	        	        	}
	        	        }
	        		});
	            });
	        });
	        
	        $("#pic4").click(function () {
	            $("#operator_picture_auth").click(); //隐藏了input:file样式后，点击头像就可以本地上传
	            $("#operator_picture_auth").change(function(){
	            	var loading = layer.load();
	            	$.ajaxFileUpload({
	        			url: "${ctx}/sys/register/async-uploadFile",
	        			fileElementId: "operator_picture_auth",
	        	        type: 'post',
	        	        secureuri: false, 
	        	        dataType: 'json', 
	        	        success: function(json, status){
	        	        	layer.close(loading);
	        	        	if(json != null && json.hasOwnProperty("msg")){
	        	        		var msg = json["msg"];
	        	        		var data = json["data"];
	        	        		var url = json["url"];
	        	        		if(msg == "OK" && !$.isEmpty(url)){
	        	        			layer.msg("平台操作授权书上传成功！", {shift: 6});
	        	        			$("#pic4").attr("src", url);
	        	        			$("#platformOperateAuthor").val(url);
	        	        		}else{
	        	        			layer.msg(msg, {shift: 6});
	        	        		}
	        	        	}else{
	        	        		layer.msg(json, {shift: 6});
	        	        	}
	        	        }
	        		});
	            });
	        });
	        
	        
	        $("#pic5").click(function () {
	            $("#picture_agency_front").click(); //隐藏了input:file样式后，点击头像就可以本地上传
	            $("#picture_agency_front").on("change", function () {
	            	var loading = layer.load();
	            	$.ajaxFileUpload({
	        			url: "${ctx}/sys/register/idCard-distinguish?type=face",
	        			fileElementId: "picture_agency_front",
	        	        type: 'post',
	        	        secureuri: false, 
	        	        dataType: 'json', 
	        	        success: function(json, status){
	        	        	layer.close(loading);
	        	        	if(json != null && json.hasOwnProperty("msg")){
	        	        		var msg = json["msg"];
	        	        		var data = json["data"];
	        	        		var url = json["url"];
	        	        		if(msg == "OK" && !$.isEmpty(url)){
	        	        			if(data != null && data != undefined && data.hasOwnProperty("success") && data["success"]){
	        	        				layer.msg("身份证正面上传并识别成功！", {shift: 6});
		        	        			//身份证信息展现
		        	        			$("#agencyCardPositive_ul").show();
		        	        			$("#agencyCardPositive_ul").empty();
		        	        			$("#agencyCardPositive_ul").append(
		        	        				'<li style="text-indent: 10px;"> 姓名：'+ data["name"] +'<input type="hidden" name="supplierEnterpriseId.agencyName" value="'+ data["name"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 性别：'+ data["sex"] +'<input type="hidden" name="supplierEnterpriseId.agencySex" value="'+ (data["sex"] == "男"? "0" : "1") +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 民族：'+ data["nationality"] +'<input type="hidden" name="supplierEnterpriseId.agencyNation" value="'+ data["nationality"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;">身份证：'+ data["num"] +'<input type="hidden" name="supplierEnterpriseId.agencyIdCard" value="'+ data["num"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 地址：'+ data["address"] +'<input type="hidden" name="supplierEnterpriseId.agencyAddress" value="'+ data["address"] +'" /></li>'
		        	        			);
	        	        			}
	        	        			$("#pic5").attr("src", url);
	        	        			$("#agencyCardPositive").val(url);
	        	        		}else{
	        	        			layer.msg(msg, {shift: 6});
	        	        		}
	        	        	}else{
	        	        		layer.msg(json, {shift: 6});
	        	        	}
	        	        }
	        		});
	            });
	        });
	        
	        $("#pic6").click(function () {
	            $("#picture_agency_back").click(); //隐藏了input:file样式后，点击头像就可以本地上传
	            $("#picture_agency_back").on("change", function () {
	            	var loading = layer.load();
	            	$.ajaxFileUpload({
	        			url: "${ctx}/sys/register/idCard-distinguish?type=back",
	        			fileElementId: "picture_agency_back",
	        	        type: 'post',
	        	        secureuri: false, 
	        	        dataType: 'json', 
	        	        success: function(json, status){
	        	        	layer.close(loading);
	        	        	if(json != null && json.hasOwnProperty("msg")){
	        	        		var msg = json["msg"];
	        	        		var data = json["data"];
	        	        		var url = json["url"];
	        	        		if(msg == "OK" && !$.isEmpty(url)){
	        	        			if(data != null && data != undefined && data.hasOwnProperty("success") && data["success"]){
	        	        				layer.msg("身份证反面上传并识别成功！", {shift: 6});
		        	        			//身份证信息展现
		        	        			$("#agencyCardBack_ul").show();
		        	        			$("#agencyCardBack_ul").empty();
		        	        			$("#agencyCardBack_ul").append(
		        	        				'<li style="text-indent: 10px;">  有效期：'+ (data["start_date"] + " 至 " + data["end_date"]) +'<input type="hidden" name="supplierEnterpriseId.agencyCardValidity" value="'+ (data["start_date"] + " 至 " + data["end_date"]) +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 签发机关：'+ data["issue"] +'<input type="hidden" name="supplierEnterpriseId.agencyCardOffice" value="'+ data["issue"] +'" /></li>'
		        	        			);
	        	        			}
	        	        			$("#pic6").attr("src", url);
	        	        			$("#agencyCardBack").val(url);
	        	        		}else{
	        	        			layer.msg(msg, {shift: 6});
	        	        		}
	        	        	}else{
	        	        		layer.msg(json, {shift: 6});
	        	        	}
	        	        }
	        		});
	            });
	        });
	        
	        $("#pic15").click(function () {
	            $("#picture_operator_front").click(); //隐藏了input:file样式后，点击头像就可以本地上传
	            $("#picture_operator_front").on("change", function () {
	            	var loading = layer.load();
	            	$.ajaxFileUpload({
	        			url: "${ctx}/sys/register/idCard-distinguish?type=face",
	        			fileElementId: "picture_operator_front",
	        	        type: 'post',
	        	        secureuri: false, 
	        	        dataType: 'json', 
	        	        success: function(json, status){
	        	        	layer.close(loading);
	        	        	if(json != null && json.hasOwnProperty("msg")){
	        	        		var msg = json["msg"];
	        	        		var data = json["data"];
	        	        		var url = json["url"];
	        	        		if(msg == "OK" && !$.isEmpty(url)){
	        	        			if(data != null && data != undefined && data.hasOwnProperty("success") && data["success"]){
	        	        				layer.msg("身份证正面上传并识别成功！", {shift: 6});
		        	        			//身份证信息展现
		        	        			$("#operatorCardPositive_ul").show();
		        	        			$("#operatorCardPositive_ul").empty();
		        	        			$("#operatorCardPositive_ul").append(
		        	        				'<li style="text-indent: 10px;"> 姓名：'+ data["name"] +'<input type="hidden" name="supplierEnterpriseId.operatorName" value="'+ data["name"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 性别：'+ data["sex"] +'<input type="hidden" name="supplierEnterpriseId.operatorSex" value="'+ (data["sex"] == "男"? "0" : "1") +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 民族：'+ data["nationality"] +'<input type="hidden" name="supplierEnterpriseId.operatorNation" value="'+ data["nationality"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;">身份证：'+ data["num"] +'<input type="hidden" name="supplierEnterpriseId.operatorIdCard" value="'+ data["num"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 地址：'+ data["address"] +'<input type="hidden" name="supplierEnterpriseId.operatorAddress" value="'+ data["address"] +'" /></li>'
		        	        			);
	        	        			}
	        	        			$("#pic15").attr("src", url);
	        	        			$("#operatorCardPositive").val(url);
	        	        		}else{
	        	        			layer.msg(msg, {shift: 6});
	        	        		}
	        	        	}else{
	        	        		layer.msg(json, {shift: 6});
	        	        	}
	        	        }
	        		});
	            });
	        });
	        
	        $("#pic16").click(function () {
	            $("#picture_operator_back").click(); //隐藏了input:file样式后，点击头像就可以本地上传
	            $("#picture_operator_back").on("change", function () {
	            	var loading = layer.load();
	            	$.ajaxFileUpload({
	        			url: "${ctx}/sys/register/idCard-distinguish?type=back",
	        			fileElementId: "picture_operator_back",
	        	        type: 'post',
	        	        secureuri: false, 
	        	        dataType: 'json', 
	        	        success: function(json, status){
	        	        	layer.close(loading);
	        	        	if(json != null && json.hasOwnProperty("msg")){
	        	        		var msg = json["msg"];
	        	        		var data = json["data"];
	        	        		var url = json["url"];
	        	        		if(msg == "OK" && !$.isEmpty(url)){
	        	        			if(data != null && data != undefined && data.hasOwnProperty("success") && data["success"]){
	        	        				layer.msg("身份证反面上传并识别成功！", {shift: 6});
		        	        			//身份证信息展现
		        	        			$("#operatorCardBack_ul").show();
		        	        			$("#operatorCardBack_ul").empty();
		        	        			$("#operatorCardBack_ul").append(
		        	        				'<li style="text-indent: 10px;">  有效期：'+ (data["start_date"] + " 至 " + data["end_date"]) +'<input type="hidden" name="supplierEnterpriseId.operatorCardValidity" value="'+ (data["start_date"] + " 至 " + data["end_date"]) +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 签发机关：'+ data["issue"] +'<input type="hidden" name="supplierEnterpriseId.operatorCardOffice" value="'+ data["issue"] +'" /></li>'
		        	        			);
	        	        			}
	        	        			$("#pic16").attr("src", url);
	        	        			$("#operatorCardBack").val(url);
	        	        		}else{
	        	        			layer.msg(msg, {shift: 6});
	        	        		}
	        	        	}else{
	        	        		layer.msg(json, {shift: 6});
	        	        	}
	        	        }
	        		});
	            });
	        });
	        
	        $("#pic8").click(function () {
	            $("#opening_permit_letter").click(); //隐藏了input:file样式后，点击头像就可以本地上传
	            $("#opening_permit_letter").change(function(){
	            	var loading = layer.load();
	            	$.ajaxFileUpload({
	        			url: "${ctx}/sys/register/async-uploadFile",
	        			fileElementId: "opening_permit_letter",
	        	        type: 'post',
	        	        secureuri: false, 
	        	        dataType: 'json', 
	        	        success: function(json, status){
	        	        	layer.close(loading);
	        	        	if(json != null && json.hasOwnProperty("msg")){
	        	        		var msg = json["msg"];
	        	        		var data = json["data"];
	        	        		var url = json["url"];
	        	        		if(msg == "OK" && !$.isEmpty(url)){
	        	        			layer.msg("开户许可证上传成功！", {shift: 6});
	        	        			$("#pic8").attr("src", url);
	        	        			$("#openingPermitLetter").val(url);
	        	        		}else{
	        	        			layer.msg(msg, {shift: 6});
	        	        		}
	        	        	}else{
	        	        		layer.msg(json, {shift: 6});
	        	        	}
	        	        }
	        		});
	            });
	        });
	        
	        $("#pic9").click(function () {
	            $("#office_credit_letter").click(); //隐藏了input:file样式后，点击头像就可以本地上传
	            $("#office_credit_letter").change(function(){
	            	var loading = layer.load();
	            	$.ajaxFileUpload({
	        			url: "${ctx}/sys/register/async-uploadFile",
	        			fileElementId: "office_credit_letter",
	        	        type: 'post',
	        	        secureuri: false, 
	        	        dataType: 'json', 
	        	        success: function(json, status){
	        	        	layer.close(loading);
	        	        	if(json != null && json.hasOwnProperty("msg")){
	        	        		var msg = json["msg"];
	        	        		var data = json["data"];
	        	        		var url = json["url"];
	        	        		if(msg == "OK" && !$.isEmpty(url)){
	        	        			layer.msg("机构信用证上传成功！", {shift: 6});
	        	        			$("#pic9").attr("src", url);
	        	        			$("#officeCreditLetter").val(url);
	        	        		}else{
	        	        			layer.msg(msg, {shift: 6});
	        	        		}
	        	        	}else{
	        	        		layer.msg(json, {shift: 6});
	        	        	}
	        	        }
	        		});
	            });
	        });
	        
	        $("#pic10").click(function () {
	            $("#company_constitution").click(); //隐藏了input:file样式后，点击头像就可以本地上传
	            $("#company_constitution").change(function(){
	            	var loading = layer.load();
	            	$.ajaxFileUpload({
	        			url: "${ctx}/sys/register/async-uploadFile",
	        			fileElementId: "company_constitution",
	        	        type: 'post',
	        	        secureuri: false, 
	        	        dataType: 'json', 
	        	        success: function(json, status){
	        	        	layer.close(loading);
	        	        	if(json != null && json.hasOwnProperty("msg")){
	        	        		var msg = json["msg"];
	        	        		var data = json["data"];
	        	        		var url = json["url"];
	        	        		if(msg == "OK" && !$.isEmpty(url)){
	        	        			layer.msg("公司章程上传成功！", {shift: 6});
	        	        			$("#picc10").attr("href", url);
	        	        			$("#companyConstitution").val(url);
	        	        		}else{
	        	        			layer.msg(msg, {shift: 6});
	        	        		}
	        	        	}else{
	        	        		layer.msg(json, {shift: 6});
	        	        	}
	        	        }
	        		});
	            });
	        });
	        
	        
	        if($("#company_type").attr("val") != undefined && $("#company_type").attr("val") != null && $("#company_type").attr("val") != ""){
      			$("#company_type option[value="+ $("#company_type").attr("val") +"]")[0].selected = true;
      		}
	        
	        
	      	//获取验证码
        	$("#btn_code").bind("click", function () {
            	var mobile = $("#operator_mobile").val();
	            if (mobile == "") {
                	layer.msg("请输入手机号");
	                return false;
	            }
	            var thic = this;
	            if($("#orgState").val() == "2" && $("#agencyPhone").val() == mobile){
	            	$.ajax({
						async : true,
						cache : true,
						url: "${ctx}/sys/register/get-mobile-code?mobile=" + mobile,
						data : {  },
						type: "get",
						dataType: "text",
						success: function(data, status, xhr){
							if (data == "000") {
			                	layer.msg("验证码已发送，请注意查收短信");
			                    time(thic, 60);
			                } else if (data == "003") {
			                    layer.msg("图形验证码错误");
			                    refreshCode();
			                }else if (data == "004") {
			                    layer.msg("用户名不存在");
			                    time(thic, 60);
			                }else if (data == "005") {
			                    layer.msg("手机号码不一致");
			                    time(thic, 60);
			                }else if (data == "006") {
			                    layer.msg("手机号码已存在");
			                    time(thic, 60);
			                }else if (data == "007") {
			                    layer.msg("手机号码或验证均不能为空");
			                    time(thic, 60);
			                }else {
			                    layer.msg(data);
			                    time(thic, 60);
			                }
						},
						error: function(xhr, status, error){
							layer.msg(status);
						}
					});
	            }else{
	            	$.ajax({
						async : true,
						cache : true,
						url: "${ctx}/sys/register/validate-loginPhone",
						data : { "mobile" : mobile },
						type: "get",
						dataType: "text",
						success: function(data, status, xhr){
							if(data != "用户手机号可以注册！"){
								layer.msg("负责人手机号已被注册！");
							}else{
								$.ajax({
									async : true,
									cache : true,
									url: "${ctx}/sys/register/validate-loginName",
									data : { "loginName" : mobile },
									type: "get",
									dataType: "text",
									success: function(data, status, xhr){
										if(data != "用户登录名可以注册！"){
											layer.msg("负责人手机号已被注册！");
										}else{
								            $.ajax({
												async : true,
												cache : true,
												url: "${ctx}/sys/register/get-mobile-code?mobile=" + mobile,
												data : {  },
												type: "get",
												dataType: "text",
												success: function(data, status, xhr){
													if (data == "000") {
									                	layer.msg("验证码已发送，请注意查收短信");
									                    time(thic, 60);
									                } else if (data == "003") {
									                    layer.msg("图形验证码错误");
									                    refreshCode();
									                }else if (data == "004") {
									                    layer.msg("用户名不存在");
									                    time(thic, 60);
									                }else if (data == "005") {
									                    layer.msg("手机号码不一致");
									                    time(thic, 60);
									                }else if (data == "006") {
									                    layer.msg("手机号码已存在");
									                    time(thic, 60);
									                }else if (data == "007") {
									                    layer.msg("手机号码或验证均不能为空");
									                    time(thic, 60);
									                }else {
									                    layer.msg(data);
									                    time(thic, 60);
									                }
												},
												error: function(xhr, status, error){
													layer.msg(status);
												}
											});
										}
									},
									error: function(xhr, status, error){
										layer.msg(status);
									}
								});
							}
						},
						error: function(xhr, status, error){
							layer.msg(status);
						}
					});
	            }
        	});
		});
		
		
		//初始化获取省级下拉列表
		function getProvinceAreaSelect(){
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/sys/register/aynsc-getCityArea",
				data : { "type" : "2" },
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					if(data != null && data.length > 0){
						$.each(data, function(key, value){
							$("#province_id").append('<option value="'+ value["id"] +'">'+ value["name"] +'</option>');
						});
					}
					
					if($("#province_id").attr("val") != undefined && $("#province_id").attr("val") != null && $("#province_id").attr("val") != ""){
		      			$("#province_id option[value="+ $("#province_id").attr("val") +"]")[0].selected = true;
		      			checkCityArea($("#province_id")[0]);
		      		}
				},
				error: function(xhr, status, error){
					layer.msg(status);
				}
			});
		}
		
		
		//根据省切换地市
		function checkCityArea(obj){
			var id = $(obj).val();
			if(!$.isEmpty(id)){
				var loading = layer.load();
				$.ajax({
					async : true,
					cache : true,
					url: "${ctx}/sys/register/aynsc-getCityArea",
					data : { "type" : "3", "pArea.id" : id },
					type: "get",
					dataType: "json",
					success: function(data, status, xhr){
						layer.close(loading);
						$("#city_id").find("option").not(":first").remove();
						if(data != null && data.length > 0){
							$.each(data, function(key, value){
								$("#city_id").append('<option '+ ($("#city_id").attr("val") == value["id"]? "selected" : "") +' value="'+ value["id"] +'">'+ value["name"] +'</option>');
							});
						}
					},
					error: function(xhr, status, error){
						layer.close(loading);
						layer.msg(status);
					}
				});
			}
		}
		
		
		//添加股东
		function addShareHolder(obj){
			var that = $(obj).parent().parent();
			$(that).before(
				'<div class="share-holder-area">'+
        			'<div class="disclose_hang">'+
	            		'<span class="disclose_name"><font color="#ff0000">*</font> 股东姓名：</span>'+
	            		'<input type="text" class="disclose_text" placeholder="请输入股东姓名" />'+
	          		'</div>'+
	          		'<div class="disclose_hang">'+
	            		'<span class="disclose_name"><font color="#ff0000">*</font> 身份证号：</span>'+
	            		'<input type="text" class="disclose_text" placeholder="请输入身份证号" maxlength="20" />'+
	          		'</div>'+
	          		'<div class="disclose_hang" style="margin-bottom: 10px;">'+
	            		'<span class="disclose_name"><font color="#ff0000">*</font> 占股比例：</span>'+
	            		'<input type="text" class="disclose_text" placeholder="请输入占股比例" />'+
	            		'<span class="disclose_name" style="width: auto; margin-left: 10px;">%</span>'+
	            		'<input type="button" class="financing_submit" style="width: 100px; height: 30px; line-height: 30px; margin-left: 15px;" value="删 除" onclick="deletShareHolder(this)" />'+
	          		'</div>'+
        		'</div>'
			);
		}
		
		
		//删除股东
		function deletShareHolder(obj){
			var that = $(obj).parent().parent();
			$(that).remove();
		}
		
		
		//提交
	    function checkForm() {
        	var company_name = $("#company_name").val();
	        var org_code = $("#org_code").val();
	        var license_expired_date = $("#license_expired_date").val();
	        var registered_capital = $("#registered_capital").val();
	        var company_type = $("#company_type").val();
	        var city_id = $("#city_id").val();
	        var address = $("#address").val();

	        var operator_name = $("#operator_name").val();
	        var operator_mobile = $("#operator_mobile").val();
	        var operator_email = $("#operator_email").val();
	        var verify_code = $("#verify_code").val();

	        var picture_license = $("#picture_license").val();
	        var picture_legal_front = $("#picture_legal_front").val();
	        var picture_legal_back = $("#picture_legal_back").val();
	        var picture_operator_front = $("#picture_operator_front").val();
	        var picture_operator_back = $("#picture_operator_back").val();
	        var picture_agency_front = $("#picture_agency_front").val();
	        var picture_agency_back = $("#picture_agency_back").val();
	        var operator_picture_auth = $("#operator_picture_auth").val();
	        var opening_permit_letter = $("#opening_permit_letter").val();
	        var office_credit_letter = $("#office_credit_letter").val();
	        var company_constitution = $("#company_constitution").val();

	        if (company_name == "") {
	            $("#error_company_name").html("<img src=\"${ctxStatic}/images/ti1.png\">输入企业名称");
	            $("#company_name").focus();
	            return;
	        } else {
	            $("#error_company_name").html("");
	        }

	        if (org_code == "") {
	            $("#error_org_code").html("<img src=\"${ctxStatic}/images/ti1.png\">输入组织机构代码");
	            $("#org_code").focus();
	            return;
	        } else if (org_code.length > 20) {
	            $("#error_org_code").html("<img src=\"${ctxStatic}/images/ti1.png\">组织机构代码为18位或20位");
	            $("#org_code").focus();
	            return;
	        } else {
	            $("#error_org_code").html("");
	        }

	        if (license_expired_date == "") {
	            $("#error_license_expired_date").html("<img src=\"${ctxStatic}/images/ti1.png\">输入营业期限");
	            $("#license_expired_date").focus();
	            return;
	        } else {
	            $("#error_license_expired_date").html("");
	        }

	        if (registered_capital == "") {
	            $("#error_registered_capital").html("<img src=\"${ctxStatic}/images/ti1.png\">输入注册资本");
	            $("#registered_capital").focus();
	            return;
	        } else {
	            $("#error_registered_capital").html("");
	        }

	        if (company_type == "") {
	            $("#error_company_type").html("<img src=\"${ctxStatic}/images/ti1.png\">选择企业类型");
	            $("#company_type").focus();
	            return;
	        } else {
	            $("#error_company_type").html("");
	        }

	        if (city_id == 0) {
	            $("#error_address").html("<img src=\"${ctxStatic}/images/ti1.png\">选择省市");
	            $("#address").focus();
	            return;
	        } else {
	            $("#error_address").html("");
	        }

	        if (address == "") {
	            $("#error_address").html("<img src=\"${ctxStatic}/images/ti1.png\">输入地址");
	            $("#address").focus();
	            return;
	        } else {
	            $("#error_address").html("");
	        }

	        if (picture_license == "" && $.isEmpty($("#pic").attr("src"))) {
	            $("#error_picture_license").html("<img src=\"${ctxStatic}/images/ti1.png\">选择营业执照");
	            $("#picture_license").focus();
	            return;
	        } else {
	            $("#error_picture_license").html("");
	        }

	        if (picture_legal_front == "" && $.isEmpty($("#pic1").attr("src"))) {
	            $("#error_picture_legal_front").html("<img src=\"${ctxStatic}/images/ti1.png\">选择法人身份证正面照片");
	            $("#picture_legal_front").focus();
	            return;
	        } else {
	            $("#error_picture_legal_front").html("");
	        }

	        if (picture_legal_back == "" && $.isEmpty($("#pic2").attr("src"))) {
	            $("#error_picture_legal_back").html("<img src=\"${ctxStatic}/images/ti1.png\">选择法人身份证反面照片");
	            $("#picture_legal_back").focus();
	            return;
	        } else {
	            $("#error_picture_legal_back").html("");
	        }
	        
	        if (picture_operator_front == "" && $.isEmpty($("#pic15").attr("src"))) {
	            $("#error_picture_operator_front").html("<img src=\"${ctxStatic}/images/ti1.png\">选择操作员身份证正面照片");
	            $("#picture_operator_front").focus();
	            return;
	        } else {
	            $("#error_picture_operator_front").html("");
	        }

	        if (picture_operator_back == "" && $.isEmpty($("#pic16").attr("src"))) {
	            $("#error_picture_operator_back").html("<img src=\"${ctxStatic}/images/ti1.png\">选择操作员身份证反面照片");
	            $("#picture_operator_back").focus();
	            return;
	        } else {
	            $("#error_picture_operator_back").html("");
	        }
	        
	        if (picture_agency_front == "" && $.isEmpty($("#pic5").attr("src"))) {
	            $("#error_picture_agency_front").html("<img src=\"${ctxStatic}/images/ti1.png\">选择负责人身份证正面照片");
	            $("#picture_agency_front").focus();
	            return;
	        } else {
	            $("#error_picture_agency_front").html("");
	        }

	        if (picture_agency_back == "" && $.isEmpty($("#pic6").attr("src"))) {
	            $("#error_picture_agency_back").html("<img src=\"${ctxStatic}/images/ti1.png\">选择负责人身份证反面照片");
	            $("#picture_agency_back").focus();
	            return;
	        } else {
	            $("#error_picture_agency_back").html("");
	        }

	        if (operator_name == "") {
	            $("#error_operator_name").html("<img src=\"${ctxStatic}/images/ti1.png\">输入负责人姓名");
	            $("#operator_name").focus();
	            return;
	        } else {
	            $("#error_operator_name").html("");
	        }
	        
	        if (operator_mobile == "") {
	            $("#error_operator_mobile").html("<img src=\"${ctxStatic}/images/ti1.png\">输入负责人手机");
	            $("#operator_mobile").focus();
	            return;
	        } else {
	        	$("#error_operator_mobile").html("");
	        }
	        if (operator_email == "") {
	            $("#error_operator_email").html("<img src=\"${ctxStatic}/images/ti1.png\">输入负责人邮箱");
	            $("#operator_email").focus();
	            return;
	        }else{
	        	$("#error_operator_email").html("");
	        }
	        
	        if (verify_code == "") {
            	$("#error_verify_code").html("<img src=\"${ctxStatic}/images/ti1.png\">输入手机校验码");
	            $("#verify_code").focus();
	            return;
	        } else {
	            $("#error_verify_code").html("");
	        }

	        if (operator_picture_auth == "" && $.isEmpty($("#pic4").attr("src"))) {
	            $("#error_operator_picture_auth").html("<img src=\"${ctxStatic}/images/ti1.png\">选择平台操作授权书照片");
	            $("#operator_picture_auth").focus();
	            return;
	        } else {
	            $("#error_operator_picture_auth").html("");
	        }
	        
	        if (opening_permit_letter == "" && $.isEmpty($("#pic8").attr("src"))) {
	            $("#error_opening_permit_letter").html("<img src=\"${ctxStatic}/images/ti1.png\">选择开户许可证照片");
	            $("#opening_permit_letter").focus();
	            return;
	        } else {
	            $("#error_opening_permit_letter").html("");
	        }
	        //todo zuo
	        <%--if (office_credit_letter == "" && $.isEmpty($("#pic9").attr("src"))) {--%>
	        <%--    $("#error_office_credit_letter").html("<img src=\"${ctxStatic}/images/ti1.png\">选择机构信用证照片");--%>
	        <%--    $("#office_credit_letter").focus();--%>
	        <%--    return;--%>
	        <%--} else {--%>
	        <%--    $("#error_office_credit_letter").html("");--%>
	        <%--}--%>
			//todo zuo
	        <%--if (company_constitution == "" && $.isEmpty($("#picc10").attr("href"))) {--%>
	        <%--    $("#error_company_constitution").html("<img src=\"${ctxStatic}/images/ti1.png\">选择公司章程附件");--%>
	        <%--    $("#company_constitution").focus();--%>
	        <%--    return;--%>
	        <%--} else {--%>
	        <%--    $("#error_company_constitution").html("");--%>
	        <%--}--%>
	        
	        //校验股东比例
	        var supplierShareHolders = "";
	        var holderAllRatio = 0;
	        var flag = true;
	        $(".share-holder-area").each(function(){
	        	var name = $(this).find(".disclose_text:eq(0)").val();
	        	var idNum = $(this).find(".disclose_text:eq(1)").val();
	        	var ratio = $(this).find(".disclose_text:eq(2)").val();
	        	
	        	if(!$.isEmpty(name) && !$.isEmpty(idNum) && !$.isEmpty(ratio)){
	        		var str = name + " " + idNum + " " + ratio;
	        		supplierShareHolders += (str + ",");
	        		holderAllRatio += parseFloat(ratio);
	        	}else{
	        		flag = false;
	        	}
	        });
	        
	        if(!flag){
	        	layer.msg("请完善股东占比信息！");
	        	return;
	        }
	        
	        if(holderAllRatio != 100){
	        	layer.msg("股东比例总和有误，请先完善！");
	        	return;
	        }
	        
	        $("#supplierShareHolders").val(supplierShareHolders);
	        
	        if(validateForm.form()){
	        	$("#inputform").submit();
	        }
		 }
	</script>
</head>
<body>
	<div class="header">
  		<div class="header_con" style="width: 1150px;">
    		<div class="logo1">
    			<span style="border-left: 0px; font-weight: 600; letter-spacing: 2px; font-size: 25px;">创信供应链金融</span>
    			<span style="margin-left: 15px;">供应商注册</span>
    		</div>
  		</div>
	</div>
	
	<form name="inputform" id="inputform" class="form-horizontal" enctype="multipart/form-data" action="${ctx}/sys/register/submitDataBySupplier" accept-charset="UTF-8" method="post">
    	<input type="hidden" id="message" value="${message}" />
    	<input type="hidden" id="orgId" name="supplierEnterpriseId.id" value="${supplier_user.supplierEnterpriseId.id}" />
    	<input type="hidden" id="orgState" value="${supplier_user.supplierEnterpriseId.state}" />
    	<input type="hidden" id="userId" name="userId.id" value="${supplier_user.userId.id}" />
    	<input type="hidden" id="businessLicense" name="supplierEnterpriseId.businessLicense" value="${supplier_user.supplierEnterpriseId.businessLicense}" />
    	<input type="hidden" id="legalCardPositive" name="supplierEnterpriseId.legalCardPositive" value="${supplier_user.supplierEnterpriseId.legalCardPositive}" />
    	<input type="hidden" id="legalCardBack" name="supplierEnterpriseId.legalCardBack" value="${supplier_user.supplierEnterpriseId.legalCardBack}" />
    	<input type="hidden" id="operatorCardPositive" name="supplierEnterpriseId.operatorCardPositive" value="${supplier_user.supplierEnterpriseId.operatorCardPositive}" />
    	<input type="hidden" id="operatorCardBack" name="supplierEnterpriseId.operatorCardBack" value="${supplier_user.supplierEnterpriseId.operatorCardBack}" />
    	<input type="hidden" id="agencyCardPositive" name="supplierEnterpriseId.agencyCardPositive" value="${supplier_user.supplierEnterpriseId.agencyCardPositive}" />
    	<input type="hidden" id="agencyCardBack" name="supplierEnterpriseId.agencyCardBack" value="${supplier_user.supplierEnterpriseId.agencyCardBack}" />
    	<input type="hidden" id="platformOperateAuthor" name="supplierEnterpriseId.platformOperateAuthor" value="${supplier_user.supplierEnterpriseId.platformOperateAuthor}" />
    	<input type="hidden" id="openingPermitLetter" name="supplierEnterpriseId.openingPermitLetter" value="${supplier_user.supplierEnterpriseId.openingPermitLetter}" />
    	<input type="hidden" id="officeCreditLetter" name="supplierEnterpriseId.officeCreditLetter" value="${supplier_user.supplierEnterpriseId.officeCreditLetter}" />
    	<input type="hidden" id="companyConstitution" name="supplierEnterpriseId.companyConstitution" value="${supplier_user.supplierEnterpriseId.companyConstitution}" />
    	<input type="hidden" id="agencyPhone" value="${supplier_user.supplierEnterpriseId.agencyPhone}" />
    	<input type="hidden" id="supplierShareHolders" name="guDongs" />
    	<div class="successful clear">
      		<div class="title_nav2">
            	<span>1  注册账号</span><span><font color="#ffffff">2 提交资料</font></span><span>3  在线签约</span>
          	</div>
          	<div class="steps_nav" id="title_nav2">
            	<span class="steps_cur">企业资料提交</span><label></label><span>审核资料</span><label></label><span>审核通过</span>
          	</div>
      
      		<div class="disclose clear">
        		<div class="disclose_title">一、企业信息
          			<label>（带<font color="#ff0000">*</font>号的必填）</label>
        		</div>
        		<div class="disclose_con">
          			<div class="disclose_hang"><span class="disclose_name"><font color="#ff0000">*</font> 营业执照：</span>
            			<div class="disclose_img">
              				<div class="disclose_img_con">
                				<input id="picture_license" name="picture_license" accept="image/*" type="file" style="display: none" />
                				<img class="pic" id="pic" src="${supplier_user.supplierEnterpriseId.businessLicense}" />
                				<span class="disclose_fang"><a class="image-zoom" href="${ctxStatic}/images/tu_02.png" rel="prettyPhoto"><label></label><img src="${ctxStatic}/images/tu_02.png" height="92"></a></span>
              				</div>
           					<div class="disclose_img_zi">上传营业执照，系统智能填写企业信息(图片不超过10M)</div>
              				<span class="disclose_r" id="error_picture_license"></span>
            			</div>
          			</div>

          			<div class="disclose_hang">
            			<span class="disclose_name"><font color="#ff0000">*</font> 企业名称：</span>
           				<input readOnly type="text" class="disclose_text" id="company_name" name="supplierEnterpriseId.name" value="${supplier_user.supplierEnterpriseId.name}" placeholder="请输入企业名称" />
           				<span class="disclose_r" id="error_company_name"></span>
          			</div>
          			
		          	<div class="disclose_hang">
		            	<span class="disclose_name"><font color="#ff0000">*</font> 组织机构代码：</span>
		            	<input readOnly type="text" class="disclose_text" id="org_code" name="supplierEnterpriseId.orgCode" value="${supplier_user.supplierEnterpriseId.orgCode}" placeholder="统一社会信用代码/营业执照注册号" maxlength="18">
		            	<span class="disclose_r" id="error_org_code"></span>
		          	</div>
		          	
		          	<div class="disclose_hang">
		            	<span class="disclose_name"><font color="#ff0000">*</font> 营业期限至：</span>
		            	<input readOnly type="text" class="ECalendar disclose_day" style="width: 205px;" id="license_expired_date" readonly placeholder="选择日期" name="supplierEnterpriseId.businessPeriodTo" value="${supplier_user.supplierEnterpriseId.businessPeriodTo}" />
		            	<span class="disclose_r" id="error_license_expired_date"></span>
		            	<input type="hidden" id="license_expired_date_str" name="license_expired_date_str" value="">
		          	</div>
		          	
	          		<div class="disclose_hang">
		            	<span class="disclose_name"><font color="#ff0000">*</font> 注册资本：</span>
			            <input readOnly type="text" class="disclose_text" id="registered_capital" name="supplierEnterpriseId.registeredCapital" value="${supplier_user.supplierEnterpriseId.registeredCapital}" placeholder="请输入注册资本" maxlength="50"><span></span>
			            <span class="disclose_r" id="error_registered_capital"></span>
	          		</div>
          
          			<div class="disclose_hang">
		            	<span class="disclose_name"><font color="#ff0000">*</font> 企业类型：</span>
			            <select id="company_type" style="width: 205px;" name="supplierEnterpriseId.type" val="${supplier_user.supplierEnterpriseId.type}" class="disclose_select">
		              		<option value="">请选择企业类型</option>
					        <option value="0">股份有限公司</option>
					        <option value="1">有限责任公司</option>
					        <option value="2">合伙企业</option>
					        <option value="3">集体企业</option>
					        <option value="4">国有企业</option>
			            </select>
			            <span class="disclose_r" id="error_company_type"></span>
	          		</div>

		          	<div class="disclose_hang">
		            	<span class="disclose_name"><font color="#ff0000">*</font> 营业地址：</span>
		            	<select id="province_id" name="supplierEnterpriseId.provinceArea.id" val="${supplier_user.supplierEnterpriseId.provinceArea.id}" class="disclose_select1" onchange="checkCityArea(this)">
	              			<option value="0" selected>请选择省份</option>
		            	</select>
		            	<select id="city_id" name="supplierEnterpriseId.cityArea.id" val="${supplier_user.supplierEnterpriseId.cityArea.id}" class="disclose_select1">
		              		<option value="0" selected>请选择城市</option>
                        </select>
	            		<br/>
		            	<textarea readOnly style="padding: 0; width: 217px; margin-left: 240px; margin-top: 5px;" class="textarea" id="address" name="supplierEnterpriseId.businessAddress" placeholder="请输入实际营业地址">${supplier_user.supplierEnterpriseId.businessAddress}</textarea>
		            	<span class="disclose_r" id="error_address"></span>
		          	</div>

      				<div class="clear"></div>
        		</div>
        		<div class="clear"></div>
      		</div>

      		<div class="disclose clear">
        		<div class="disclose_title">二、法人信息</div>
        			<div class="disclose_con">
		          		<div class="disclose_hang">
			            	<span class="disclose_name"><font color="#ff0000">*</font> 身份证正面：</span>
				            <div class="disclose_img">
				              	<div class="disclose_img_con">
				                	<input id="picture_legal_front" name="picture_legal" accept="image/*" type="file" style="display: none">
				                	<img class="pic" id="pic1" src="${supplier_user.supplierEnterpriseId.legalCardPositive}"><span class="disclose_fang"><a class="image-zoom" href="${ctxStatic}/images/tu_03.jpg" rel="prettyPhoto[gallery]"><label></label><img src="${ctxStatic}/images/tu_03.jpg" height="92"></a></span>
				              	</div>
				              	<div class="disclose_img_zi">系统支持jpg和png格式，大小不超过10M</div>
				              	<span class="disclose_r" id="error_picture_legal_front"></span>
				            </div>
	         			</div>
	         			
	         			<div class="disclose_hang">
	         				<c:if test="${not empty supplier_user.supplierEnterpriseId.legalCardPositive}">
	         					<ul id="legalCardPositive_ul" style="width: 300px; border: 1px solid #cccccc; margin: 0 auto 40px auto;">
	         						<li style="text-indent: 10px;"> 姓名：${supplier_user.supplierEnterpriseId.legalName}<input type="hidden" name="supplierEnterpriseId.legalName" value="${supplier_user.supplierEnterpriseId.legalName}" /></li>
	         						<c:if test="${supplier_user.supplierEnterpriseId.legalSex == '0'}">
	         							<li style="text-indent: 10px;"> 性别：男<input type="hidden" name="legalSex" value="男" /></li>
	         						</c:if>
	        	        			<c:if test="${supplier_user.supplierEnterpriseId.legalSex != '0'}">
	         							<li style="text-indent: 10px;"> 性别：女<input type="hidden" name="legalSex" value="女" /></li>
	         						</c:if>
	        	        			<li style="text-indent: 10px;"> 民族：${supplier_user.supplierEnterpriseId.legalNation}<input type="hidden" name="supplierEnterpriseId.legalNation" value="${supplier_user.supplierEnterpriseId.legalNation}" /></li>
	        	        			<li style="text-indent: 10px;">身份证：${supplier_user.supplierEnterpriseId.legalIdCard}<input type="hidden" name="supplierEnterpriseId.legalIdCard" value="${supplier_user.supplierEnterpriseId.legalIdCard}" /></li>
	        	        			<li style="text-indent: 10px;"> 地址：${supplier_user.supplierEnterpriseId.legalAddress}<input type="hidden" name="supplierEnterpriseId.legalAddress" value="${supplier_user.supplierEnterpriseId.legalAddress}" /></li>
	         					</ul>
	         				</c:if>
	         				<c:if test="${empty supplier_user.supplierEnterpriseId.legalCardPositive}">
	         					<ul id="legalCardPositive_ul" style="display: none; width: 300px; border: 1px solid #cccccc; margin: 0 auto 40px auto;"></ul>
	         				</c:if>
	         			</div>
	         			
      					<div class="disclose_hang">
          					<span class="disclose_name"><font color="#ff0000">*</font> 身份证反面：</span>
			            	<div class="disclose_img">
			              		<div class="disclose_img_con">
			                		<input id="picture_legal_back" name="picture_legal" accept="image/*" type="file" style="display: none">
			                		<img class="pic" id="pic2" src="${supplier_user.supplierEnterpriseId.legalCardBack}"><span class="disclose_fang"><a class="image-zoom" href="${ctxStatic}/images/tu_04.jpg" rel="prettyPhoto[gallery]"><label></label><img src="${ctxStatic}/images/tu_04.jpg" height="92"></a></span>
			              		</div>
			              		<div class="disclose_img_zi">系统支持jpg和png格式，大小不超过10M</div>
		              			<span class="disclose_r" id="error_picture_legal_back"></span>
		            		</div>
          				</div>
          				
          				<div class="disclose_hang">
          					<c:if test="${not empty supplier_user.supplierEnterpriseId.legalCardBack}">
	         					<ul id="legalCardBack_ul" style="width: 300px; border: 1px solid #cccccc; margin: 0 auto 40px auto;">
	        	        			<li style="text-indent: 10px;">  有效期：${supplier_user.supplierEnterpriseId.legalCardValidity}<input type="hidden" name="supplierEnterpriseId.legalCardValidity" value="${supplier_user.supplierEnterpriseId.legalCardValidity}" /></li>
	        	        			<li style="text-indent: 10px;"> 签发机关：${supplier_user.supplierEnterpriseId.legalCardOffice}<input type="hidden" name="supplierEnterpriseId.legalCardOffice" value="${supplier_user.supplierEnterpriseId.legalCardOffice}" /></li>
	         					</ul>
	         				</c:if>
	         				<c:if test="${empty supplier_user.supplierEnterpriseId.legalCardBack}">
	         					<ul id="legalCardBack_ul" style="display: none; width: 300px; border: 1px solid #cccccc; margin: 0 auto 40px auto;"></ul>
	         				</c:if>
	         			</div>
          				
          				<div class="clear"></div>
       				</div>
        			<div class="clear"></div>
      			</div>
      			
      			<div class="disclose clear">
        			<div class="disclose_title">三、其他相关证照</div>
        			<div class="disclose_con">
		          		<div class="disclose_hang">
		          			<span class="disclose_name"><font color="#ff0000">*</font> 开户许可证：</span>	
			            	<div class="disclose_img">
			              		<div class="disclose_img_con">
			                		<input id="opening_permit_letter" name="uploadfile" accept="image/*" type="file" style="display: none">
			                		<img class="pic" id="pic8" src="${supplier_user.supplierEnterpriseId.openingPermitLetter}" />
			              		</div>
			              		<div class="disclose_img_zi" id="opening_permit_letter_url" style="display: none;"></div>
			              		<div class="disclose_img_zi">系统支持jpg和png格式，大小不超过10M</div>
			              		<span class="disclose_r" id="error_opening_permit_letter"></span>
			            	</div>
		          		</div>
		          		
		          		<div class="disclose_hang">
		          			<span class="disclose_name"><font color="#ff0000"></font> 机构信用证：</span>
			            	<div class="disclose_img">
			              		<div class="disclose_img_con">
			                		<input id="office_credit_letter" name="uploadfile" accept="image/*" type="file" style="display: none">
			                		<img class="pic" id="pic9" src="${supplier_user.supplierEnterpriseId.officeCreditLetter}" />
			              		</div>
			              		<div class="disclose_img_zi" id="office_credit_letter_url" style="display: none;"></div>
			              		<div class="disclose_img_zi">系统支持jpg和png格式，大小不超过10M</div>
			              		<span class="disclose_r" id="error_office_credit_letter"></span>
			            	</div>
		          		</div>
		          		
		          		<div class="disclose_hang">
		          			<span class="disclose_name"><font color="#ff0000"></font> 公司章程：</span>
			            	<div class="disclose_img">
			              		<div class="disclose_img_con">
			                		<input id="company_constitution" name="uploadfile" accept="image/*" type="file" style="display: none" />
			                		<a id="pic10" href="javascript: void(0);">上传</a>
			                		<c:if test="${not empty supplier_user.supplierEnterpriseId.companyConstitution}">
			                			<a style="margin-left: 15px;" id="picc10" href="${supplier_user.supplierEnterpriseId.companyConstitution}">下载/查看</a>
			                		</c:if>
			                		<c:if test="${empty supplier_user.supplierEnterpriseId.companyConstitution}">
			                			<a style="margin-left: 15px;" id="picc10" target="_blank" href="javascript:layer.msg('请上传公司章程！');">下载/查看</a>
			                		</c:if>
			              		</div>
			              		<div class="disclose_img_zi" id="company_constitution_url" style="display: none;"></div>
			              		<div class="disclose_img_zi">系统支持任意文件格式，大小不超过10M</div>
			              		<span class="disclose_r" id="error_company_constitution"></span>
			            	</div>
		          		</div>
       				</div>
        			<div class="clear"></div>
      			</div>
      			
      			<div class="disclose clear">
	        		<div class="disclose_title">四、操作员信息</div>
	        			<div class="disclose_con">
			          		<div class="disclose_hang">
				            	<span class="disclose_name"><font color="#ff0000">*</font> 身份证正面：</span>
					            <div class="disclose_img">
					              	<div class="disclose_img_con">
					                	<input id="picture_operator_front" name="picture_legal" accept="image/*" type="file" style="display: none">
					                	<img class="pic" id="pic15" src="${supplier_user.supplierEnterpriseId.operatorCardPositive}"><span class="disclose_fang"><a class="image-zoom" href="${ctxStatic}/images/tu_03.jpg" rel="prettyPhoto[gallery]"><label></label><img src="${ctxStatic}/images/tu_03.jpg" height="92"></a></span>
					              	</div>
					              	<div class="disclose_img_zi">系统支持jpg和png格式，大小不超过10M</div>
					              	<span class="disclose_r" id="error_picture_operator_front"></span>
					            </div>
		         			</div>
		         			
		         			<div class="disclose_hang">
		         				<c:if test="${not empty supplier_user.supplierEnterpriseId.operatorCardPositive}">
		         					<ul id="operatorCardPositive_ul" style="width: 300px; border: 1px solid #cccccc; margin: 0 auto 40px auto;">
		         						<li style="text-indent: 10px;"> 姓名：${supplier_user.supplierEnterpriseId.operatorName}<input type="hidden" name="supplierEnterpriseId.operatorName" value="${supplier_user.supplierEnterpriseId.operatorName}" /></li>
		         						<c:if test="${supplier_user.supplierEnterpriseId.operatorSex == '0'}">
		         							<li style="text-indent: 10px;"> 性别：男<input type="hidden" name="operatorSex" value="男" /></li>
		         						</c:if>
		        	        			<c:if test="${supplier_user.supplierEnterpriseId.operatorSex != '0'}">
		         							<li style="text-indent: 10px;"> 性别：女<input type="hidden" name="operatorSex" value="女" /></li>
		         						</c:if>
		        	        			<li style="text-indent: 10px;"> 民族：${supplier_user.supplierEnterpriseId.operatorNation}<input type="hidden" name="supplierEnterpriseId.operatorNation" value="${supplier_user.supplierEnterpriseId.operatorNation}" /></li>
		        	        			<li style="text-indent: 10px;">身份证：${supplier_user.supplierEnterpriseId.operatorIdCard}<input type="hidden" name="supplierEnterpriseId.operatorIdCard" value="${supplier_user.supplierEnterpriseId.operatorIdCard}" /></li>
		        	        			<li style="text-indent: 10px;"> 地址：${supplier_user.supplierEnterpriseId.operatorAddress}<input type="hidden" name="supplierEnterpriseId.operatorAddress" value="${supplier_user.supplierEnterpriseId.operatorAddress}" /></li>
		         					</ul>
		         				</c:if>
		         				<c:if test="${empty supplier_user.supplierEnterpriseId.operatorCardPositive}">
		         					<ul id="operatorCardPositive_ul" style="display: none; width: 300px; border: 1px solid #cccccc; margin: 0 auto 40px auto;"></ul>
		         				</c:if>
		         			</div>
		         			
	      					<div class="disclose_hang">
	          					<span class="disclose_name"><font color="#ff0000">*</font> 身份证反面：</span>
				            	<div class="disclose_img">
				              		<div class="disclose_img_con">
				                		<input id="picture_operator_back" name="picture_legal" accept="image/*" type="file" style="display: none">
				                		<img class="pic" id="pic16" src="${supplier_user.supplierEnterpriseId.operatorCardBack}"><span class="disclose_fang"><a class="image-zoom" href="${ctxStatic}/images/tu_04.jpg" rel="prettyPhoto[gallery]"><label></label><img src="${ctxStatic}/images/tu_04.jpg" height="92"></a></span>
				              		</div>
				              		<div class="disclose_img_zi">系统支持jpg和png格式，大小不超过10M</div>
			              			<span class="disclose_r" id="error_picture_operator_back"></span>
			            		</div>
	          				</div>
	          				
	          				<div class="disclose_hang">
	          					<c:if test="${not empty supplier_user.supplierEnterpriseId.operatorCardBack}">
		         					<ul id="operatorCardBack_ul" style="width: 300px; border: 1px solid #cccccc; margin: 0 auto 40px auto;">
		        	        			<li style="text-indent: 10px;">  有效期：${supplier_user.supplierEnterpriseId.operatorCardValidity}<input type="hidden" name="supplierEnterpriseId.operatorCardValidity" value="${supplier_user.supplierEnterpriseId.operatorCardValidity}" /></li>
		        	        			<li style="text-indent: 10px;"> 签发机关：${supplier_user.supplierEnterpriseId.operatorCardOffice}<input type="hidden" name="supplierEnterpriseId.operatorCardOffice" value="${supplier_user.supplierEnterpriseId.operatorCardOffice}" /></li>
		         					</ul>
		         				</c:if>
		         				<c:if test="${empty supplier_user.supplierEnterpriseId.operatorCardBack}">
		         					<ul id="operatorCardBack_ul" style="display: none; width: 300px; border: 1px solid #cccccc; margin: 0 auto 40px auto;"></ul>
		         				</c:if>
		         			</div>
	          				
	          				<div class="clear"></div>
	       				</div>
        			<div class="clear"></div>
      			</div>

      			<div class="disclose clear">
        			<div class="disclose_title">五、负责人信息（资料提交成功后，该用户是负责人角色）</div>
        			<div class="disclose_con">
			          	<div class="disclose_hang">
			            	<span class="disclose_name"><font color="#ff0000">*</font> 姓名：</span>
			            	<input type="text" id="operator_name" class="disclose_text" value="${supplier_user.supplierEnterpriseId.agencyName}" placeholder="请输入姓名" />
			            	<span class="disclose_r" id="error_operator_name"></span>
			          	</div>

			          	<div class="disclose_hang">
			            	<span class="disclose_name"><font color="#ff0000">*</font> 手机号：</span>
			            	<div class="disclose_img">
			              		<input type="text" id="operator_mobile" name="supplierEnterpriseId.agencyPhone" class="disclose_text" value="${supplier_user.supplierEnterpriseId.agencyPhone}" placeholder="请输入11位手机号" />
			              		<span class="disclose_r" id="error_operator_mobile" style="height: 28px;"></span>
			            	</div>
			          	</div>

		          		<div class="disclose_hang">
			            	<span class="disclose_name"><font color="#ff0000">*</font> 校验码：</span>
				            <input type="text" class="disclose_text1" id="verify_code" name="validateCode" value="" placeholder="请输入校验码">
				            <c:if test="${empty supplier_user.supplierEnterpriseId.state || supplier_user.supplierEnterpriseId.state == '0' || supplier_user.supplierEnterpriseId.state == '2'}">
				      			<input type="button" class="disclose_yan" id="btn_code" name="btn_code" value="获取校验码">
				      		</c:if>
				        	<c:if test="${not empty supplier_user.supplierEnterpriseId.state && supplier_user.supplierEnterpriseId.state != '0' && supplier_user.supplierEnterpriseId.state != '2'}">
				      			<input type="button" disabled="disabled" style="background: #b5b5b5;" class="disclose_yan" id="btn_code" name="btn_code" value="获取校验码">
				      		</c:if>
				            <span class="disclose_r" id="error_verify_code"></span>
		      			</div>
          
			          	<div class="disclose_hang">
			            	<span class="disclose_name"><font color="#ff0000">*</font> 邮箱：</span>
			            	<input type="text" id="operator_email" name="supplierEnterpriseId.agencyEmail" class="disclose_text" value="${supplier_user.supplierEnterpriseId.agencyEmail}" placeholder="请输入邮箱">
			            	<span class="disclose_r" id="error_operator_email"></span>
			          	</div>
		          		
		          		<div class="disclose_hang">
			            	<span class="disclose_name"><font color="#ff0000">*</font> 身份证正面：</span>
				            <div class="disclose_img">
				              	<div class="disclose_img_con">
				                	<input id="picture_agency_front" name="picture_legal" accept="image/*" type="file" style="display: none">
				                	<img class="pic" id="pic5" src="${supplier_user.supplierEnterpriseId.agencyCardPositive}"><span class="disclose_fang"><a class="image-zoom" href="${ctxStatic}/images/tu_03.jpg" rel="prettyPhoto[gallery]"><label></label><img src="${ctxStatic}/images/tu_03.jpg" height="92"></a></span>
				              	</div>
				              	<div class="disclose_img_zi">系统支持jpg和png格式，大小不超过10M</div>
				              	<span class="disclose_r" id="error_picture_agency_front"></span>
				            </div>
	         			</div>
	         			
	         			<div class="disclose_hang">
	         				<c:if test="${not empty supplier_user.supplierEnterpriseId.agencyCardPositive}">
	         					<ul id="agencyCardPositive_ul" style="width: 300px; border: 1px solid #cccccc; margin: 0 auto 40px auto;">
	         						<li style="text-indent: 10px;"> 姓名：${supplier_user.supplierEnterpriseId.agencyName}<input type="hidden" name="supplierEnterpriseId.agencyName" value="${supplier_user.supplierEnterpriseId.agencyName}" /></li>
	         						<c:if test="${supplier_user.supplierEnterpriseId.agencySex == '0'}">
	         							<li style="text-indent: 10px;"> 性别：男<input type="hidden" name="supplierEnterpriseId.agencySex" value="男" /></li>
	         						</c:if>
	        	        			<c:if test="${supplier_user.supplierEnterpriseId.agencySex != '0'}">
	         							<li style="text-indent: 10px;"> 性别：女<input type="hidden" name="supplierEnterpriseId.agencySex" value="女" /></li>
	         						</c:if>
	        	        			<li style="text-indent: 10px;"> 民族：${supplier_user.supplierEnterpriseId.agencyNation}<input type="hidden" name="supplierEnterpriseId.agencyNation" value="${supplier_user.supplierEnterpriseId.agencyNation}" /></li>
	        	        			<li style="text-indent: 10px;">身份证：${supplier_user.supplierEnterpriseId.agencyIdCard}<input type="hidden" name="supplierEnterpriseId.agencyIdCard" value="${supplier_user.supplierEnterpriseId.agencyIdCard}" /></li>
	        	        			<li style="text-indent: 10px;"> 地址：${supplier_user.supplierEnterpriseId.agencyAddress}<input type="hidden" name="supplierEnterpriseId.agencyAddress" value="${supplier_user.supplierEnterpriseId.agencyAddress}" /></li>
	         					</ul>
	         				</c:if>
	         				<c:if test="${empty supplier_user.supplierEnterpriseId.agencyCardPositive}">
	         					<ul id="agencyCardPositive_ul" style="display: none; width: 300px; border: 1px solid #cccccc; margin: 0 auto 40px auto;"></ul>
	         				</c:if>
	         			</div>
	         			
      					<div class="disclose_hang">
          					<span class="disclose_name"><font color="#ff0000">*</font> 身份证反面：</span>
			            	<div class="disclose_img">
			              		<div class="disclose_img_con">
			                		<input id="picture_agency_back" name="picture_legal" accept="image/*" type="file" style="display: none">
			                		<img class="pic" id="pic6" src="${supplier_user.supplierEnterpriseId.agencyCardBack}"><span class="disclose_fang"><a class="image-zoom" href="${ctxStatic}/images/tu_04.jpg" rel="prettyPhoto[gallery]"><label></label><img src="${ctxStatic}/images/tu_04.jpg" height="92"></a></span>
			              		</div>
			              		<div class="disclose_img_zi">系统支持jpg和png格式，大小不超过10M</div>
		              			<span class="disclose_r" id="error_picture_agency_back"></span>
		            		</div>
          				</div>
          				
          				<div class="disclose_hang">
          					<c:if test="${not empty supplier_user.supplierEnterpriseId.agencyCardBack}">
	         					<ul id="agencyCardBack_ul" style="width: 300px; border: 1px solid #cccccc; margin: 0 auto 40px auto;">
	        	        			<li style="text-indent: 10px;">  有效期：${supplier_user.supplierEnterpriseId.agencyCardValidity}<input type="hidden" name="supplierEnterpriseId.agencyCardValidity" value="${supplier_user.supplierEnterpriseId.agencyCardValidity}" /></li>
	        	        			<li style="text-indent: 10px;"> 签发机关：${supplier_user.supplierEnterpriseId.agencyCardOffice}<input type="hidden" name="supplierEnterpriseId.agencyCardOffice" value="${supplier_user.supplierEnterpriseId.agencyCardOffice}" /></li>
	         					</ul>
	         				</c:if>
	         				<c:if test="${empty supplier_user.supplierEnterpriseId.agencyCardBack}">
	         					<ul id="agencyCardBack_ul" style="display: none; width: 300px; border: 1px solid #cccccc; margin: 0 auto 40px auto;"></ul>
	         				</c:if>
	         			</div>

          				<div class="clear"></div>
        			</div>
        			<div class="clear"></div>
      			</div>
      			
      			<div class="disclose clear">
        			<div class="disclose_title">六、平台操作授权书</div>
        			<div class="disclose_con">
		          		<div class="disclose_hang">
		          			<span class="disclose_name"><font color="#ff0000">*</font> 平台操作授权书：</span>	
			            	<div class="disclose_img">
			              		<div class="disclose_img_con">
			                		<input id="operator_picture_auth" name="uploadfile" accept="image/*" type="file" style="display: none">
			                		<img class="pic" id="pic4" src="${supplier_user.supplierEnterpriseId.platformOperateAuthor}" /><span class="disclose_fang"><a class="image-zoom" href="${ctxStatic}/images/tu_06.png" rel="prettyPhoto[gallery]"><label></label><img src="${ctxStatic}/images/tu_06.png" height="92"></a></span>
			              		</div>
			              		<div class="disclose_img_zi" id="operator_picture_auth_url" style="display: none;"></div>
			              		<div class="disclose_img_zi">系统支持jpg和png格式，大小不超过10M</div>
			              		<span class="disclose_r" id="error_operator_picture_auth"></span>
			            	</div>
		          		</div>
          				<div class="clear"></div>
        			</div>
        			<div class="clear"></div>
      			</div>

		      	<div class="disclose clear">
		        	<div class="disclose_title">七、股东占比信息</div>
		        	<div class="disclose_con">
		        		<c:if test="${empty supplier_user.supplierEnterpriseId.state || supplier_user.supplierEnterpriseId.state == '0' || supplier_user.supplierEnterpriseId.state == '2'}">
			      			<div class="share-holder-area">
			        			<div class="disclose_hang">
				            		<span class="disclose_name"><font color="#ff0000">*</font> 股东姓名：</span>
				            		<input type="text" class="disclose_text" placeholder="请输入股东姓名" />
				          		</div>
				          		<div class="disclose_hang">
				            		<span class="disclose_name"><font color="#ff0000">*</font> 身份证号：</span>
				            		<input type="text" class="disclose_text" placeholder="请输入身份证号" maxlength="20" />
				          		</div>
				          		<div class="disclose_hang" style="margin-bottom: 10px;">
				            		<span class="disclose_name"><font color="#ff0000">*</font> 占股比例：</span>
				            		<input type="text" class="disclose_text" placeholder="请输入占股比例" />
				            		<span class="disclose_name" style="width: auto; margin-left: 10px;">%</span>
				            		<input type="button" class="financing_submit" style="width: 100px; height: 30px; line-height: 30px; margin-left: 15px;" value="增 加" onclick="addShareHolder(this)" />
				          		</div>
			        		</div>
			      		</c:if>
			        	<c:if test="${not empty supplier_user.supplierEnterpriseId.state && supplier_user.supplierEnterpriseId.state != '0' && supplier_user.supplierEnterpriseId.state != '2'}">
			      			<c:if test="${not empty shareHolders}">
			      				<c:forEach items="${shareHolders}" var="supplier_shareholder">
			      					<div class="share-holder-area">
					        			<div class="disclose_hang">
						            		<span class="disclose_name"><font color="#ff0000">*</font> 股东姓名：</span>
						            		<input type="text" class="disclose_text" value="${supplier_shareholder.name}" placeholder="请输入股东姓名" />
						          		</div>
						          		<div class="disclose_hang">
						            		<span class="disclose_name"><font color="#ff0000">*</font> 身份证号：</span>
						            		<input type="text" class="disclose_text" value="${supplier_shareholder.idNum}" placeholder="请输入身份证号" maxlength="20" />
						          		</div>
						          		<div class="disclose_hang" style="margin-bottom: 10px;">
						            		<span class="disclose_name"><font color="#ff0000">*</font> 占股比例：</span>
						            		<input type="text" class="disclose_text" value="${supplier_shareholder.ratio}" placeholder="请输入占股比例" />
						            		<span class="disclose_name" style="width: auto; margin-left: 10px;">%</span>
						          		</div>
					        		</div>
			      				</c:forEach>
			      			</c:if>
			      		</c:if>
		        		
		          		<div class="clear"></div>
		        	</div>
		        	
		        	<div class="clear"></div>
		      	</div>

		      	<div class="disclose_tijiao clear" style="padding-left:450px; margin: 10px auto 25px auto;">
		      		<c:if test="${empty supplier_user.supplierEnterpriseId.state || supplier_user.supplierEnterpriseId.state == '0' || supplier_user.supplierEnterpriseId.state == '2'}">
		      			<input type="button" class="financing_submit" id="btnSubmit" value="提 交" onclick="checkForm();" />
		      		</c:if>
		        	<c:if test="${not empty supplier_user.supplierEnterpriseId.state && supplier_user.supplierEnterpriseId.state != '0' && supplier_user.supplierEnterpriseId.state != '2'}">
		      			<input type="button" disabled="disabled" style="background: #b5b5b5;" class="financing_submit" id="btnSubmit" value="资料审核中" />
		      		</c:if>
		      	</div>
      		<div class="clear"></div>
    	</div>
	</form>
	
	<div class="reg_footer" style="height: 94px; line-height: 25px;">
		<span>咨询热线：000-0000000 9:00 -- 18:00（周一至周五）</span>
		<br> 
		<span>版权所有 ©2018 上海创信供应链管理有限公司 沪ICP备 <a href="http://www.miitbeian.gov.cn" target="_blank">18038558</a>号</span>
	</div>
</body>
</html>