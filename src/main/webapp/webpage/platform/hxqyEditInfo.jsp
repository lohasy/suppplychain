<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--企业管理--编辑核心企业信息</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css" />
	<script src="${ctxStatic}/online/lgt-web.js"></script>
	<link href="${ctxStatic}/online/prettyPhoto-3.1.6.css" rel="stylesheet" type="text/css">
	<script src="${ctxStatic}/online/jquery.prettyPhoto-3.1.6.js" type="text/javascript"></script>
	<script src="${ctxStatic}/online/ajaxfileupload.js"></script>
	<script>
		var scrollTop = 0;
	
		$(function(){
			/* $("body").scroll(function(){
				scrollTop =document.documentElement.scrollTop||document.body.scrollTop;
			});
			
			var _before_day = '';
	        var start = {
	            elem: '#businessPeriodTo', //对应的id
	            format: 'YYYY-MM-DD',
	            min: laydate.now(), //最小日期
	            max: '2117-12-31 00:00:00', //最大日期
	            start: laydate.now(),//开始日期
	            istime: false,
	            festival: false, //显示节日
	            istoday: false,
	            choose: function (datas) {
	                _before_day = datas;
	            }
	        };
	        laydate(start);
	        $("#businessPeriodTo").click(function(){
	        	var dateTop = $("#laydate_box").css("top").substring(0, $("#laydate_box").css("top").length - 2);
        		$("#laydate_box").css("top", (parseInt(dateTop) - scrollTop) + "px");
	        }); */
	        
	        
	        $("#businessLicense-a").click(function(){
				$("#picture_license").click();
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
	            	        				$("#name").val(name);
	            	        			}else{
	            	        				$("#name")[0].readOnly = false;
	            	        			}
	            	        			if(!$.isEmpty(reg_num) && reg_num != "FailInRecognition"){
	            	        				$("#org_code").val(reg_num);
	            	        			}else{
	            	        				$("#org_code")[0].readOnly = false;
	            	        			}
	            	        			if(!$.isEmpty(reg_num) && reg_num != "FailInRecognition" && reg_num.length >= 8){
	            	        				$("#businessPeriodTo").val(valid_period.substring(0, 4) + "-" + valid_period.substring(4, 6) + "-" + valid_period.substring(6, 8));
	            	        			}else{
	            	        				$("#businessPeriodTo")[0].readOnly = false;
	            	        			}
	            	        			if(!$.isEmpty(capital) && capital != "FailInRecognition"){
	            	        				$("#registeredCapital").val(capital);
	            	        			}else{
	            	        				$("#registeredCapital")[0].readOnly = false;
	            	        			}
	            	        			if(!$.isEmpty(type) && type != "FailInRecognition"){
	            	        				$("#type option").each(function(){
	            	        					if(type.indexOf($(this).text()) != -1){
	            	        						this.selected = true;
	            	        					}
	            	        				});
	            	        			}else{
	            	        				$("#type").removeAttr("disabled");
	            	        			}
	            	        			if(!$.isEmpty(address) && address != "FailInRecognition"){
	            	        				$("#businessAddress").val(address);
	            	        			}else{
	            	        				$("#businessAddress")[0].readOnly = false;
	            	        			}
	        	        			}
	        	        			$("#businessLicense").attr("src", url);
	        	        			$("#ori_picture_license").val(url);
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
	        
			$("#legalCardPositive-a").click(function(){
				$("#picture_legal_front").click();
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
		        	        				'<li style="text-indent: 10px;"> 姓名：'+ data["name"] +'<input type="hidden" name="legalName" value="'+ data["name"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 性别：'+ data["sex"] +'<input type="hidden" name="legalSex" value="'+ (data["sex"] == "男"? "0" : "1") +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 民族：'+ data["nationality"] +'<input type="hidden" name="legalNation" value="'+ data["nationality"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;">身份证：'+ data["num"] +'<input type="hidden" name="legalIdCard" value="'+ data["num"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 地址：'+ data["address"] +'<input type="hidden" name="legalAddress" value="'+ data["address"] +'" /></li>'
		        	        			);
	        	        			}
	        	        			$("#legalCardPositive").attr("src", url);
	        	        			$("#ori_picture_legal_front").val(url);
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
	        
			$("#legalCardBack-a").click(function(){
				$("#picture_legal_back").click();
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
		        	        				'<li style="text-indent: 10px;">  有效期：'+ (data["start_date"] + " 至 " + data["end_date"]) +'<input type="hidden" name="legalCardValidity" value="'+ (data["start_date"] + " 至 " + data["end_date"]) +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 签发机关：'+ data["issue"] +'<input type="hidden" name="legalCardOffice" value="'+ data["issue"] +'" /></li>'
		        	        			);
	        	        			}
	        	        			$("#legalCardBack").attr("src", url);
	        	        			$("#ori_picture_legal_back").val(url);
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
			
			$("#agencyCardPositive-a").click(function(){
				$("#picture_agency_front").click();
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
		        	        				'<li style="text-indent: 10px;"> 姓名：'+ data["name"] +'<input type="hidden" name="chargeName" value="'+ data["name"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 性别：'+ data["sex"] +'<input type="hidden" name="chargeSex" value="'+ (data["sex"] == "男"? "0" : "1") +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 民族：'+ data["nationality"] +'<input type="hidden" name="chargeNation" value="'+ data["nationality"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;">身份证：'+ data["num"] +'<input type="hidden" name="chargeIdCard" value="'+ data["num"] +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 地址：'+ data["address"] +'<input type="hidden" name="chargeAddress" value="'+ data["address"] +'" /></li>'
		        	        			);
	        	        			}
	        	        			$("#agencyCardPositive").attr("src", url);
	        	        			$("#ori_picture_agency_front").val(url);
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
	        
			$("#agencyCardBack-a").click(function(){
				$("#picture_agency_back").click();
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
		        	        				'<li style="text-indent: 10px;">  有效期：'+ (data["start_date"] + " 至 " + data["end_date"]) +'<input type="hidden" name="chargeCardValidity" value="'+ (data["start_date"] + " 至 " + data["end_date"]) +'" /></li>'+
		        	        				'<li style="text-indent: 10px;"> 签发机关：'+ data["issue"] +'<input type="hidden" name="chargeCardOffice" value="'+ data["issue"] +'" /></li>'
		        	        			);
	        	        			}
	        	        			$("#agencyCardBack").attr("src", url);
	        	        			$("#ori_picture_agency_back").val(url);
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
	        
	        
      		//附加校验
	        $("#commit").bind("click", function () {
        		if ($("#businessPeriodTo").val() == "") {
                	layer.msg('请输入营业期限', {shift: 6});
	                $("#businessPeriodTo").focus();
	                return false;
	            }
	            if ($("#picture_license").val() == "" && $("#ori_picture_license").val() == "") {
	                layer.msg('请选择营业执照照片', {shift: 6});
	                $("#picture_license").focus();
	                return false;
	            }
	            if ($("#picture_legal_front").val() == "" && $("#ori_picture_legal_front").val() == "") {
	                layer.msg('请选择法人身份证正面照片', {shift: 6});
	                $("#picture_legal_front").focus();
	                return false;
	            }
	            if ($("#picture_legal_back").val() == "" && $("#ori_picture_legal_back").val() == "") {
	                layer.msg('请选择法人身份证反面照片', {shift: 6});
	                $("#picture_legal_back").focus();
	                return false;
	            }
	            if ($("#picture_agency_front").val() == "" && $("#ori_picture_agency_front").val() == "") {
	                layer.msg('请选择负责人身份证正面照片', {shift: 6});
	                $("#picture_agency_front").focus();
	                return false;
	            }
	            if ($("#picture_agency_back").val() == "" && $("#ori_picture_agency_back").val() == "") {
	                layer.msg('请选择负责人身份证反面照片', {shift: 6});
	                $("#picture_agency_back").focus();
	                return false;
	            }
	            if(/^[\u4e00-\u9fa5]+$/.test($("#orgCode").val())){
	    			layer.msg('组织机构代码不可以输入汉字', {shift: 6});
	    			$("#orgCode").focus();
	    			return false;
	    		}
	            if(!/^[1][3,4,5,7,8][0-9]{9}$/.test($("#chargePhone").val())){
	            	layer.msg('负责人手机号输入有误！', {shift: 6});
	    			$("#chargePhone").focus();
	            	return false;
	            }
	            if(!/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test($("#chargeEmail").val())){
	            	layer.msg('负责人邮箱输入有误！', {shift: 6});
	    			$("#chargeEmail").focus();
	            	return false;
	            }
	            
	            $("#mgmtform").validate({
		            rules: {
		            	name: "required",
		            	orgCode: "required",
		            	registeredCapital: {
		                    required: true
		                },
		                type: {
		                    required: true,
		                    min: 0
		                },
		                city_id: {
		                    required: true,
		                    min: 1
		                },
		                businessAddress: "required",
		                chargeName: "required",
		                chargePhone: "required",
		                chargeEmail: {
		                    required: true,
		                    email: true
		                }
		            },
		            messages: {
		            	name: "请输入企业名称",
		            	orgCode: "请输入组织机构代码",
		            	registeredCapital: {
		                    required: "请输入注册资本"
		                },
		                type: {
		                    required: "请选择企业类型",
		                    min: "请选择企业类型"
		                },
		                city_id: {
		                    required: "请选择营业地址",
		                    min: "请选择营业地址"
		                },
		                businessAddress: "请输入详细的营业地址",
		                chargeName: "请输入负责人姓名",
		                chargePhone: "请输入负责人手机号",
		                chargeEmail: {
		                    required: "请输入负责人邮箱",
		                    email: "请输入负责人正确的邮箱"
		                }
		            },
		            errorPlacement: function (error, element) { //指定错误信息位置
		                if (element.is(':radio') || element.is(':checkbox')) { //如果是radio或checkbox
	                    	var container = element.parent().parent().find('error'); //获取元素的name属性
		                    error.appendTo(element.parent().parent()); //将错误信息添加当前元素的父结点后面
		                } else {
		                    error.insertAfter(element);
	                	}
	            	}
		        });
	            
            	return true;
	        });
      		
      		if($("#type").attr("val") != undefined && $("#type").attr("val") != null && $("#type").attr("val") != ""){
      			$("#type option[value="+ $("#type").attr("val") +"]")[0].selected = true;
      		}
      		
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<c:if test="${not empty fns:getUser().core.id}">
					<h5><a href="${ctx}/CoreEnterpriseCtrl/hxqy-company-info">基本资料</a></h5>
				</c:if>
				<c:if test="${empty fns:getUser().core.id}">
					<h5><a href="${ctx}/CoreEnterpriseCtrl/platform-index">核心企业管理</a></h5>
				</c:if>
				<c:if test="${empty core_enterprise.id}">
					<span class="page-title">&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;新增核心企业</span>
				</c:if>
				<c:if test="${not empty core_enterprise.id}">
					<span class="page-title">&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;编辑核心企业</span>
				</c:if>
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
				<form id="mgmtform" name="mgmtform" class="form-horizontal" enctype="multipart/form-data" action="${ctx}/CoreEnterpriseCtrl/enterpriseEidt?str=${str}" accept-charset="UTF-8" method="post">
					<input type="text" name="id" style="display: none;" class="form-control" value="${core_enterprise.id}">
					<div class="ibox-title" style="background-color: #f5f5f5;">
				  		<h5>一、企业信息</h5>
					</div>
					<div class="ibox-content">
						<div class="form-group">
					    	<label class="col-sm-3 control-label">营业执照：<span class="text-warning">*</span></label>
					    	<div class="col-sm-6" style="margin-top: 7px;">
					    		<a id="businessLicense-a">上传</a>
					      		<input style="display: none; border: none; padding: 6px 0px;" type="file" class="form-control" id="picture_license" name="picture_license" placeholder="请选择照片">
					      		<input type="hidden" class="form-control" id="ori_picture_license" name="businessLicense" value="${core_enterprise.businessLicense}">
					      		<span class="help-block m-b-none">系统支持jpg和png格式，大小不超过10M</span>
					    	</div>
					  	</div>
					  	<div class="form-group">
				    		<label class="col-sm-3 control-label"> </label>
				    		<div class="col-sm-9">
				      			<img id="businessLicense" name="businessLicense" style="max-width:160px;max-height:120px;width:160px;height:120px" src="${core_enterprise.businessLicense}">
				    		</div>
				  		</div>
					  	<div class="hr-line-dashed"></div>
					
						<div class="form-group">
					    	<label class="col-sm-3 control-label">企业名称：<span class="text-warning">*</span></label>
					    	<div class="col-sm-6">
					      		<input type="text" id="name" name="name" class="form-control" placeholder="企业名称" value="${core_enterprise.name }">
					    	</div>
					  	</div>
					  	<div class="hr-line-dashed"></div>
					  	
					  	<div class="form-group">
					    	<label class="col-sm-3 control-label">组织机构代码：<span class="text-warning">*</span></label>
					    	<div class="col-sm-6">
					      		<input readOnly type="text" id="org_code" name="orgCode" class="form-control" placeholder="营业执照注册号或注册号" value="${core_enterprise.orgCode }">
					      		<span class="help-block m-b-none">普通营业执照输入注册号，三证合一输入组织机构代码</span>
					    	</div>
					  	</div>
					  	<div class="hr-line-dashed"></div>
					
					  	<div class="form-group">
					    	<label class="col-sm-3 control-label">营业期限至：<span class="text-warning">*</span></label>
					    	<div class="col-sm-6">
					      		<div class="col-sm-9" style="padding-left: 0px;">
					        		<input readOnly type="text" class="laydate-icon form-control" id="businessPeriodTo" name="businessPeriodTo" placeholder="选择日期" readonly="" style="background-color: #FFFFFF; padding: 6px 12px; height:34px;" value="${core_enterprise.businessPeriodTo }">
					      		</div>
					    	</div>
					  	</div>
					  	<div class="hr-line-dashed"></div>
					
					  	<div class="form-group">
					    	<label class="col-sm-3 control-label">注册资本：<span class="text-warning">*</span></label>
					    	<div class="col-sm-5">
					      		<input readOnly type="text" id="registeredCapital" name="registeredCapital" class="form-control" placeholder="请输入注册资本（元）" value="${core_enterprise.registeredCapital }">
				    		</div>
					    	<label class="col-sm-1 control-label" style="text-align:left;"></label>
					  	</div>
					  	<div class="hr-line-dashed"></div>
					
					  	<div class="form-group">
					    	<label class="col-sm-3 control-label">企业类型：<span class="text-warning">*</span></label>
					    	<div class="col-sm-6">
					      		<select class="form-control" id="type" name="type" val="${core_enterprise.type }">
							        <option>请选择企业类型</option>
							        <option value="0">股份有限公司</option>
							        <option value="1">有限责任公司</option>
							        <option value="2">合伙企业</option>
							        <option value="3">集体企业</option>
							        <option value="4">国有企业</option>
					      		</select>
					    	</div>
					  	</div>
					  	<div class="hr-line-dashed"></div>
					
					  	<div class="form-group">
					    	<label class="col-sm-3 control-label">营业地址：<span class="text-warning">*</span></label>
					    	<div class="col-sm-6">
					      		<sys:treeselect id="area" name="cityArea.id" value="${core_enterprise.cityArea.id}" labelName="cityArea.name" labelValue="${core_enterprise.cityArea.name}"
									title="区域" url="/sys/area/treeData" cssClass="form-control required"/>
					    	</div>
					  	</div>
					  	<div class="hr-line-dashed"></div>
					
					  	<div class="form-group">
					    	<label class="col-sm-3 control-label">营业地址详细：<span class="text-warning">*</span></label>
					    	<div class="col-sm-6">
					      		<input readOnly type="text" class="form-control" id="businessAddress" name="businessAddress" placeholder="请输入详细地址" value="${core_enterprise.businessAddress }">
					    	</div>
					  	</div>
					  	<div class="hr-line-dashed"></div>
					</div>


					<div class="ibox-title" style="background-color: #f5f5f5;">
						<h5>二、法人信息</h5>
					</div>

					<div class="ibox-content">
						<div class="form-group">
					    	<label class="col-sm-3 control-label">身份证正面：<span class="text-warning">*</span></label>
					    	<div class="col-sm-6" style="margin-top: 7px;">
					    		<a id="legalCardPositive-a">上传</a>
					      		<input style="display: none; border: none; padding: 6px 0px;" type="file" class="form-control" id="picture_legal_front" name="picture_legal" placeholder="请选择照片">
					      		<input type="hidden" class="form-control" id="ori_picture_legal_front" name="legalCardPositive" value="${core_enterprise.legalCardPositive}">
					      		<span class="help-block m-b-none">系统支持jpg和png格式，大小不超过10M</span>
				    		</div>
					  	</div>
					  	<div class="form-group">
					    	<label class="col-sm-3 control-label"></label>
					    	<div class="col-sm-9">
					       		<img id="legalCardPositive" name="legalCardPositive" style="max-width:160px;max-height:120px;width:160px;height:120px" src="${core_enterprise.legalCardPositive}">
					    	</div>
					  	</div>
						<c:if test="${not empty core_enterprise.legalCardPositive}">
         					<ul id="legalCardPositive_ul" style="width: 300px; border: 1px solid #cccccc; margin: 0 0 40px 160px;">
         						<li style="text-indent: 10px;"> 姓名：${core_enterprise.legalName}<input type="hidden" name="legalName" value="${core_enterprise.legalName}" /></li>
         						<c:if test="${core_enterprise.legalSex == '0'}">
         							<li style="text-indent: 10px;"> 性别：男<input type="hidden" name="legalSex" value="男" /></li>
         						</c:if>
        	        			<c:if test="${core_enterprise.legalSex != '0'}">
         							<li style="text-indent: 10px;"> 性别：女<input type="hidden" name="legalSex" value="女" /></li>
         						</c:if>
        	        			<li style="text-indent: 10px;"> 民族：${core_enterprise.legalNation}<input type="hidden" name="legalNation" value="${core_enterprise.legalNation}" /></li>
        	        			<li style="text-indent: 10px;">身份证：${core_enterprise.legalIdCard}<input type="hidden" name="legalIdCard" value="${core_enterprise.legalIdCard}" /></li>
        	        			<li style="text-indent: 10px;"> 地址：${core_enterprise.legalAddress}<input type="hidden" name="legalAddress" value="${core_enterprise.legalAddress}" /></li>
         					</ul>
         				</c:if>
         				<c:if test="${empty core_enterprise.legalCardPositive}">
         					<ul id="legalCardPositive_ul" style="display: none; width: 300px; border: 1px solid #cccccc; margin: 0 0 40px 160px;"></ul>
         				</c:if>
         				<div class="hr-line-dashed"></div>
					
					  	<div class="form-group">
					    	<label class="col-sm-3 control-label">身份证反面：<span class="text-warning">*</span></label>
					    	<div class="col-sm-6" style="margin-top: 7px;">
					    		<a id="legalCardBack-a">上传</a>
					      		<input style="display: none; border: none; padding: 6px 0px;" type="file" class="form-control" id="picture_legal_back" name="picture_legal" placeholder="请选择照片">
					      		<input type="hidden" class="form-control" id="ori_picture_legal_back" name="legalCardBack" value="${core_enterprise.legalCardBack}">
					      		<span class="help-block m-b-none">系统支持jpg和png格式，大小不超过10M</span>
					    	</div>
					  	</div>
					  	<div class="form-group">
					    	<label class="col-sm-3 control-label"> </label>
					    	<div class="col-sm-9">
					     		<img id="legalCardBack" name="legalCardBack" style="max-width:160px;max-height:120px;width:160px;height:120px" src="${core_enterprise.legalCardBack}">
					    	</div>
					  	</div>
					  	<c:if test="${not empty core_enterprise.legalCardBack}">
         					<ul id="legalCardBack_ul" style="width: 300px; border: 1px solid #cccccc; margin: 0 0 40px 160px;">
        	        			<li style="text-indent: 10px;">  有效期：${core_enterprise.legalCardValidity}<input type="hidden" name="legalCardValidity" value="${core_enterprise.legalCardValidity}" /></li>
        	        			<li style="text-indent: 10px;"> 签发机关：${core_enterprise.legalCardOffice}<input type="hidden" name="legalCardOffice" value="${core_enterprise.legalCardOffice}" /></li>
         					</ul>
         				</c:if>
         				<c:if test="${empty core_enterprise.legalCardBack}">
         					<ul id="legalCardBack_ul" style="display: none; width: 300px; border: 1px solid #cccccc; margin: 0 0 40px 160px;"></ul>
         				</c:if>
					  	<div class="hr-line-dashed"></div>
					  	
					</div>


					<div class="ibox-title" style="background-color: #f5f5f5;">
						<h5>三、负责人信息</h5>
					</div>

					<div class="ibox-content">
				  		<div class="form-group">
					    	<label class="col-sm-3 control-label">姓名：<span class="text-warning">*</span></label>
					    	<div class="col-sm-6">
					      		<input type="text" id="chargeName" class="form-control" placeholder="负责人姓名" value="${core_enterprise.chargeName }">
					    	</div>
					  	</div>
					  	<div class="hr-line-dashed"></div>

  						<div class="form-group">
    						<label class="col-sm-3 control-label">手机号：<span class="text-warning">*</span></label>
						    <div class="col-sm-6">
					      		<input type="text" id="chargePhone" name="chargePhone" class="form-control" placeholder="负责人手机号" value="${core_enterprise.chargePhone }">
						    </div>
					  	</div>
					  	<div class="hr-line-dashed"></div>

					  	<div class="form-group">
					    	<label class="col-sm-3 control-label">邮箱：<span class="text-warning">*</span></label>
					    	<div class="col-sm-6">
					      		<input type="text" id="chargeEmail" name="chargeEmail" class="form-control" placeholder="负责人邮箱" value="${core_enterprise.chargeEmail }">
					    	</div>
					  	</div>
					  	<div class="hr-line-dashed"></div>
					  	
					  	<div class="form-group">
					    	<label class="col-sm-3 control-label">身份证正面：<span class="text-warning">*</span></label>
					    	<div class="col-sm-6" style="margin-top: 7px;">
					    		<a id="agencyCardPositive-a">上传</a>
					      		<input style="display: none; border: none; padding: 6px 0px;" type="file" class="form-control" id="picture_agency_front" name="picture_legal" placeholder="请选择照片">
					      		<input type="hidden" class="form-control" id="ori_picture_agency_front" name="chargeCardPositive" value="${core_enterprise.chargeCardPositive}">
					      		<span class="help-block m-b-none">系统支持jpg和png格式，大小不超过10M</span>
				    		</div>
					  	</div>
					  	<div class="form-group">
					    	<label class="col-sm-3 control-label"></label>
					    	<div class="col-sm-9">
					       		<img id="agencyCardPositive" name="agencyCardPositive" style="max-width:160px;max-height:120px;width:160px;height:120px" src="${core_enterprise.chargeCardPositive}">
					    	</div>
					  	</div>
					  	<c:if test="${not empty core_enterprise.chargeCardPositive}">
         					<ul id="agencyCardPositive_ul" style="width: 300px; border: 1px solid #cccccc; margin: 0 0 40px 160px;">
         						<li style="text-indent: 10px;"> 姓名：${core_enterprise.chargeName}<input type="hidden" name="chargeName" value="${core_enterprise.chargeName}" /></li>
         						<c:if test="${core_enterprise.chargeSex == '0'}">
         							<li style="text-indent: 10px;"> 性别：男<input type="hidden" name="chargeSex" value="男" /></li>
         						</c:if>
        	        			<c:if test="${core_enterprise.chargeSex != '0'}">
         							<li style="text-indent: 10px;"> 性别：女<input type="hidden" name="chargeSex" value="女" /></li>
         						</c:if>
        	        			<li style="text-indent: 10px;"> 民族：${core_enterprise.chargeNation}<input type="hidden" name="chargeNation" value="${core_enterprise.chargeNation}" /></li>
        	        			<li style="text-indent: 10px;">身份证：${core_enterprise.chargeIdCard}<input type="hidden" name="chargeIdCard" value="${core_enterprise.chargeIdCard}" /></li>
        	        			<li style="text-indent: 10px;"> 地址：${core_enterprise.chargeAddress}<input type="hidden" name="chargeAddress" value="${core_enterprise.chargeAddress}" /></li>
         					</ul>
         				</c:if>
         				<c:if test="${empty core_enterprise.chargeCardPositive}">
         					<ul id="agencyCardPositive_ul" style="display: none; width: 300px; border: 1px solid #cccccc; margin: 0 0 40px 160px;"></ul>
         				</c:if>
					  	<div class="hr-line-dashed"></div>
					
					  	<div class="form-group">
					    	<label class="col-sm-3 control-label">身份证反面：<span class="text-warning">*</span></label>
					    	<div class="col-sm-6" style="margin-top: 7px;">
					    		<a id="agencyCardBack-a">上传</a>
					      		<input style="display: none; border: none; padding: 6px 0px;" type="file" class="form-control" id="picture_agency_back" name="picture_legal" placeholder="请选择照片">
					      		<input type="hidden" class="form-control" id="ori_picture_agency_back" name="chargeCardBack" value="${core_enterprise.chargeCardBack}">
					      		<span class="help-block m-b-none">系统支持jpg和png格式，大小不超过10M</span>
					    	</div>
					  	</div>
					  	<div class="form-group">
					    	<label class="col-sm-3 control-label"> </label>
					    	<div class="col-sm-9">
					     		<img id="agencyCardBack" name="agencyCardBack" style="max-width:160px;max-height:120px;width:160px;height:120px" src="${core_enterprise.chargeCardBack}">
					    	</div>
					  	</div>
					  	<c:if test="${not empty core_enterprise.chargeCardBack}">
         					<ul id="agencyCardBack_ul" style="width: 300px; border: 1px solid #cccccc; margin: 0 0 40px 160px;">
        	        			<li style="text-indent: 10px;">  有效期：${core_enterprise.chargeCardValidity}<input type="hidden" name="chargeCardValidity" value="${core_enterprise.chargeCardValidity}" /></li>
        	        			<li style="text-indent: 10px;"> 签发机关：${core_enterprise.chargeCardOffice}<input type="hidden" name="chargeCardOffice" value="${core_enterprise.chargeCardOffice}" /></li>
         					</ul>
         				</c:if>
         				<c:if test="${empty core_enterprise.chargeCardBack}">
         					<ul id="agencyCardBack_ul" style="display: none; width: 300px; border: 1px solid #cccccc; margin: 0 0 40px 160px;"></ul>
         				</c:if>
					  	<div class="hr-line-dashed"></div>
					</div>

					<div class="form-group">
  						<label class="col-sm-3 control-label"></label>
					  	<div class="col-sm-9">
					    	<input type="button" value="重 置" class="btn btn-lg" onclick="location.reload();">
					    	<input style="margin-left: 10px;" type="submit" value="提 交" id="commit" name="commit" class="btn btn-lg btn-primary">
					  	</div>
					</div>
					
          		</form>
			</div>
		</div>
	</div>
</body>
</html>