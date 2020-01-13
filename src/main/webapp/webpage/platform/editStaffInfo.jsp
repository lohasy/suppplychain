<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--企业管理--编辑企业员工</title>
	<meta name="decorator" content="default"/>
	<script>
		$(document).ready(function() {
			$(".page-title:eq(1)").hide();
			
			if('${coreid}' != '' && '${coreid}' != null){
				$("h5:eq(1)").hide();
        	}else{
        		$("h5:eq(0)").hide();
        	}
			
			setTimeout(function(){
				if($("#userRole").attr("val") != undefined && $("#userRole").attr("val") != null && $("#userRole").attr("val") != ""){
	      			$("#userRole option[value="+ $("#userRole").attr("val") +"]")[0].selected = true;
	      		}
			}, 1000);
			
	        $("#submit-button").click(function(){
	        	if($("#loginName").val() == undefined || $("#loginName").val() == null || $("#loginName").val() == ""){
	        		layer.msg('登录名不允许为空！', {shift: 6});
	        		return;
	        	}
	        	if($("#name").val() == undefined || $("#name").val() == null || $("#name").val() == ""){
	        		layer.msg('姓名不允许为空！', {shift: 6});
	        		return;
	        	}
	        	if($("#userRole").val() == undefined || $("#userRole").val() == null || $("#userRole").val() == ""){
	        		layer.msg('角色不允许为空！', {shift: 6});
	        		return;
	        	}
	        	$("#mgmtform").submit();
	        });
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5  id="core"><a href="${ctx}/CoreEnterpriseCtrl/userlist?core.id=${coreid }">核心企业员工列表</a></h5>
				<h5><a href="${ctx}/gys/gys-index">供应商管理</a></h5>
				<span class="page-title">&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;新增企业员工</span>
				<span class="page-title">&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;编辑企业员工</span>
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
				<form name="mgmtform" id="mgmtform" class="form-horizontal" action="${ctx}/${url}" accept-charset="UTF-8" method="post">
				<input type="hidden" name="core.id" value="${coreid}"/>
				<input type="hidden" name="supplier.id" value="${suppl}"/>
				<input type="hidden" name="id" value="${user.id}"/>
					<div class="form-group">
  						<label class="col-sm-3 control-label">登录名：<span class="text-warning">*</span></label>
				  		<div class="col-sm-6">
					    	<input type="text" id="loginName" name="loginName" class="form-control valid" placeholder="请输入登录账号" value="${user.loginName}" aria-required="true" aria-invalid="false">
					  	</div>
					</div>
					<div class="hr-line-dashed"></div>

					<div class="form-group">
  						<label class="col-sm-3 control-label">姓名：<span class="text-warning">*</span></label>
						<div class="col-sm-6">
    						<input type="text" id="name" name="name" class="form-control" placeholder="请输入中文姓名" value="${user.name}">
  						</div>
					</div>
					<div class="hr-line-dashed"></div>

					<div class="form-group">
  						<label class="col-sm-3 control-label">手机号码：</label>
				  		<div class="col-sm-6">
					    	<input type="text" id="mobile" name="mobile" class="form-control" placeholder="请输入手机号" value="${user.mobile}">
					  	</div>
					</div>
					<div class="hr-line-dashed"></div>

					<div class="form-group">
  						<label class="col-sm-3 control-label">Email：</label>
					  	<div class="col-sm-6">
					    	<input type="text" id="email" name="email" class="form-control" placeholder="请输入Email" value="${user.email }">
				  		</div>
					</div>
					<div class="hr-line-dashed"></div>

					<div class="form-group">
						<label class="col-sm-3 control-label">地区：</label>
  						<div class="col-sm-6">
    						<div class="col-sm-6" style="padding-left: 0px;">
	    						<sys:treeselect id="area" name="cityId.id" value="${user.cityId.id}" labelName="cityId.name" labelValue="${user.cityId.name}"
									title="区域" url="/sys/area/treeData" cssClass="form-control required"/>
    						</div>
  						</div>
					</div>
					<div class="hr-line-dashed"></div>

					<div class="form-group">
  						<label class="col-sm-3 control-label">角色：<span class="text-warning">*</span></label>
						<div class="col-sm-6">
    						<select class="form-control valid" id="userRole" name="role.id" aria-required="true" aria-invalid="false" val="${user.role.id}">
   								<option value="">请选择</option>
   								<c:forEach items="${allRoles}" var="arole">
						    		<option value="${arole.id}">${arole.name}</option>
								</c:forEach>	 
    						</select>
						</div>
						<div class="hr-line-dashed"></div>
					</div>

					<div class="form-group">
				  		<label class="col-sm-3 control-label"></label>
				  		<div class="col-sm-9">
					   		<input type="button" value="重 置" onclick="location.reload();" class="btn_cancel btn btn-lg">
					    	<input id="submit-button" style="margin-left: 10px;" type="button" value="提 交" class="btn_submit btn btn-lg btn-primary">
					  	</div>
					</div>
          		</form>
			</div>
		</div>
	</div>
</body>
</html>