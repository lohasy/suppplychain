<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商--实名认证</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css" />
	<script src="${ctxStatic}/online/lgt-web.js"></script>
	<script>
		$(function(){
			//处理状态
			if(!$.isEmpty($("#orgState").val())){

				// 实名认证

				if($("#orgState").val() == "3" && !$.isEmpty($("#userId").val())){
					location.href = "${ctx}/sys/register/to-supplierContract?id="+ $("#userId").val();
				}

				if($("#orgState").val() != "1" && $("#orgState").val() != "2" && $("#orgState").val() != "-1" && $("#orgState").val() != "3"){
					location.href = "${ctx}/logout";
				}
			}

			if($.isEmpty($("#orgId").val())){
				location.href = "${ctx}/logout";
			}

			$("#real_name").click(function(){
                console.log("111")
                <%--location.href = "${ctx}/facetart/faceUrl"--%>
				$.ajax({
					url: "${ctx}/facetart/faceUrl",
					type: "get",
					dataType: "json",
					success: function (data, status, xhr) {
						if(data.code == "0"){
							location.href = data.data.url
						}else{
							console.log(data)
						}

					},
					error: function (xhr, status, error) {
						layer.msg(status);
					}
				});
            })
            $("#close_btn").click(function(){
                console.log("222")
                location.href = "${ctx}/logout"
            })
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
  		<div class="title_nav"><span>1 注册账号</span><span>2 提交资料 </span><span style="color: #fff">3  实名认证</span><span>4 在线签约</span></div>
		<div class="successful_cheng">
			<div class="successful_l"><img src="${ctxStatic}/images/successful_01.jpg"></div>
			<div class="successful_r" style="margin-top: 30px;">
				<div class="successful_title">恭喜您资料审核成功！</div>
				<div class="successful_nei">您刚走了第2步，还要两步才能进行融资操作哦！<br>马上进行企业实行认证吧！</div>
				<div class="successful_link">

					<c:if test="${fn:contains(fns:getUser().roleNames, '供应商负责人')}">
						<a href="javascript:void(0);" id="real_name" style="margin-right: 20px">马上实名</a>
					</c:if>
					<c:if test="${not fn:contains(fns:getUser().roleNames, '供应商负责人')}">
						<a href="javascript:void(0);" style="margin-right: 20px;background-color: #5E5E5E;">无实名权限</a>
					</c:if>



					<a href="javascript:void(0);" id="close_btn">关闭</a>
				</div>
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