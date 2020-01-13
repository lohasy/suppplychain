<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商--注册成功</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css" />
	<script src="${ctxStatic}/online/lgt-web.js"></script>
	<script>
		$(function(){
			//处理状态
			if(!$.isEmpty($("#orgState").val())){
				if($("#orgState").val() == "2"){
					location.href = "${ctx}/logout";
				}
				if($("#orgState").val() == "3" && !$.isEmpty($("#userId").val())){
					location.href = "${ctx}/sys/register/to-supplierContract?id="+ $("#userId").val();;
				}
				if($("#orgState").val() != "0" && $("#orgState").val() != "1" && $("#orgState").val() != "2" && $("#orgState").val() != "3"){
					location.href = "${ctx}/logout";
				}
			}
			
			if($.isEmpty($("#orgId").val())){
				location.href = "${ctx}/logout";
			}
		});
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
	
	<div class="successful clear">
    	<input type="hidden" id="orgId" name="supplierEnterpriseId.id" value="${supplier_user.supplierEnterpriseId.id}" />
    	<input type="hidden" id="orgState" value="${supplier_user.supplierEnterpriseId.state}" />
    	<input type="hidden" id="userId" name="userId.id" value="${supplier_user.userId.id}" />
  		<div class="title_nav"><span><font color="#ffffff">1 注册账号</font></span><span>2  提交资料</span><span>3  在线签约</span></div>
  		<div class="successful_cheng">
    		<div class="successful_l"><img src="${ctxStatic}/images/successful_01.jpg"></div>
    		<div class="successful_r" style="margin-top: 30px;">
      			<div class="successful_title">恭喜您注册成功！</div>
      			<div class="successful_nei">您刚走了第1步，还要两步才能进行融资操作哦！<br>马上提交资料进行企业认证吧！</div>
      			<div class="successful_link"><a href="${ctx}/sys/register/to-supplierSubmitData?id=${supplier_user.userId.id}">立即提交资料</a></div>
    		</div>
    		<div class="clear"></div>
  		</div>
  		<div class="clear"></div>
	</div>
	
	<div class="reg_footer" style="height: 94px; line-height: 25px;">
		<span>咨询热线：000-0000000 9:00 -- 18:00（周一至周五）</span>
		<br> 
		<span>版权所有 ©2018 上海创信供应链管理有限公司 沪ICP备 <a href="http://www.miitbeian.gov.cn" target="_blank">18038558</a>号</span>
	</div>
</body>
</html>