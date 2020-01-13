<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="default"/>
	<title>操作说明</title>   
	<link rel="stylesheet" href="${ctxStatic}/help/css/base.css">
	<link rel="stylesheet" href="${ctxStatic}/help/css/common.css">
	<link rel="stylesheet" href="${ctxStatic}/help/css/page.css">
	<style type="text/css">
		.guide-part img{
			margin-left: 40px;
			box-shadow:0px 0px 10px rgba(62,112,202,0.4);
			border-radius: 5px;
		}
	</style>
</head>
<body>
	<div class="wrap">
	<div class="header">
  		<div class="header_top">
    		<div class="header_top_con">
      			<div class="header_top_l">
				欢迎光临！ 
				<c:if test="${not empty fns:getUser().supplier.id}">${fns:getUser().supplier.name}</c:if>
				<c:if test="${not empty fns:getUser().core.id}">${fns:getUser().core.name}</c:if>
<%--				--- ${fns:getUser().roleNames}--%>
				</div>
				<div class="header_top_l">
      				<span>${fns:getUser().loginName} <a style="margin-left: 15px;" href="${ctx}/logout">退出系统</a>
				</div>
    		</div>
  		</div>
	</div>
    <div class="centerWrap">
        <div class="guideContainer">
            <div class="center">
                <!--<p class="tip">创信</p>-->
                <div class="guideInner" style="margin-top: 30px;">
                    <div class="guide-part">
                        <h3 class="title">1.账号登录</h3>
                        <h4 class="subtitle ico1">登录创信供应链平台</h4>
                        <img src="${ctxStatic}/help/image/login.png" style="width: 800px;">
                    </div>
                    <div class="guide-part">
                        <h3 class="title">2.查看账款单据</h3>
                        <h4 class="subtitle ico9">点击进入融资管理</h4>
                        <p class="paragraph">进入融资管理页面，查看应收账款单据</p>
                        <p></p>
                        <img src="${ctxStatic}/help/image/1.png"style="width: 800px;">
                        <h4 class="subtitle ico9">查看应收账单据</h4>
                        <p class="paragraph">查看下方应收账款单据列表</p>
                        <img src="${ctxStatic}/help/image/3.png" style="width: 800px;">
                    </div>
                    <div class="guide-part">
                        <h3 class="title">3.申请融资</h3>
                        <h4 class="subtitle ico9">选择可申请融资的单据</h4>
                        <p class="paragraph">选择带有融资申请的单据列表，点击融资申请,进入融资申请页面</p>
                        <img src="${ctxStatic}/help/image/6.png" style="width: 800px;">
                        <h4 class="subtitle ico9">提交凭证</h4>
                        <p class="paragraph">依次点击上传提交凭证，征信报告授权书，商务合同等凭证</p>
                        <img src="${ctxStatic}/help/image/5.png" style="width: 800px;">
                        <h4 class="subtitle ico9">发出申请</h4>
                        <p class="paragraph">勾选我已阅读并同意，填写本次申请金额后，选择立即申请</p>
                        <img src="${ctxStatic}/help/image/4.png" style="width: 800px;">
                        
                    </div>
                    <div class="guide-part">
                        <h3 class="title">4.确认提交</h3>
                        <h4 class="subtitle ico9">供应商负责人登录，确认提交</h4>
                        <p class="paragraph">供应商负责人登录，进入融资管理页面，点击融资管理查看待审核的单据</p>
                        <p></p>
                        <img src="${ctxStatic}/help/image/7.png"style="width: 800px;">
                        
                    </div>
                    <div class="guide-part">
                        <h3 class="title">5.银行审批</h3>
                         <h4 class="subtitle ico9">等待银行审批</h4>
                         <h4 class="subtitle ico9 end">&nbsp;</h4>
                    </div>
                     <div class="guide-part" style="margin-left: 274px;margin-top: -206px;">
                        <h3 class="title" >6.银行放款</h3>
                         <h4 class="subtitle ico9">等待银行放款</h4>
                     </div>
                     <div class="guide-part" style="margin-left: 553px;margin-top: -178px;">  
                        <h3 class="title">7.还款</h3>
                         <h4 class="subtitle ico9">按规定还款</h4>                       
                     </div>
                    <!--<div class="guide-part">
                        <h3 class="title" style="margin: auto;">马上融资</h3>                      
                    </div>-->
                </div>
            </div>
        </div>
    </div>

		<div class="footer clear" style="margin-top: 0px; padding: 0;">
		  <div class="footer_con">
		    <div class="footer_wei"><!--<img src="/images/weixin.jpg">--></div>	   
		    <div class="footer_nei" style="line-height: 22px;">
		    	<br>版权所有 ©2018 上海创信供应链管理有限公司 &nbsp;&nbsp;&nbsp; <a href="http://www.miitbeian.gov.cn"  target="_blank">沪ICP备 18038558号</a> 
		    </div>
		    <div class="clear"></div>
		  </div>
		  <div class="clear"></div>
		</div>
</div>
</body>
</html>