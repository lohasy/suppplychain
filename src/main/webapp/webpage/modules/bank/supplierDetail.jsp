<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>银行--融资管理--供应商详情</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<link href="${ctxStatic}/online/checkbix.min.css" rel="stylesheet">
	<link href="${ctxStatic}/online/prettyPhoto-3.1.6.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${ctxStatic}/online/jquery.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/online/checkbix.min.js"></script>
	<script src="${ctxStatic}/online/script.js" type="text/javascript"></script>
	<script src="${ctxStatic}/online/jquery.prettyPhoto.js" type="text/javascript"></script>
	<script>
		$(function(){
			$("a[rel^='prettyPhoto']").prettyPhoto();
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5><a href="${ctx}/bank/bankList">融资管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;供应商详情</span>
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
	
				<div class="rong_nav">
					<ul>
				  		<li><a href="${ctx}/bank/toFinancingDetail?id=${financing_info.id}" class="nav_b">融资详情</a></li>
					  	<li><a href="${ctx}/bank/toSupplierDetail?id=${financing_info.id}" class="nav_a">供应商详情</a></li>
					  	<%-- <c:if test="${not empty fns:getUser().supplier.id }">
							<li><a href="${ctx}/rzglall/redirect?type=2" class="nav_b">应收账款转让合同</a></li>
						</c:if>
						<c:if test="${not empty fns:getUser().core.id }">
							<li><a href="${ctx}/rzglall/redirect?type=2" class="nav_b">应付账款转让合同</a></li>
						</c:if>
						<c:if test="${empty fns:getUser().supplier.id and empty fns:getUser().core.id }">
							<li><a href="${ctx}/rzglall/redirect?type=2" class="nav_b">单据账款转让合同</a></li>
						</c:if> --%>
		    			<li><a href="${ctx}/rzglall/redirect?type=5" class="nav_b">融资合同</a></li>
					</ul>
				</div>
				
				<div class="financing clear">
					<div class="financing_gong" style="width: 100%;">
				    	<div class="financing_gong_l" style="width: 100%;">
				      		<a class="image-zoom" href="${supplier_enterprise.businessLicense}" rel="prettyPhoto[gallery]"><img src="${supplier_enterprise.businessLicense}" width="210" height="280"/></a>
				      		<div class="financing_nei">
				      			<span>企业名称：</span>${supplier_enterprise.name}<br>
				        		<span>统一社会信用代码：</span>${supplier_enterprise.orgCode}<br>
				        		<span>注册资本：</span>${supplier_enterprise.registeredCapital}<br>
				        		<span>类型：</span>
						        <c:if test="${supplier_enterprise.type == 0}">
						        	股份有限公司
						        </c:if>
						        <c:if test="${supplier_enterprise.type == 1}">
						        	有限责任公司
						        </c:if>
						        <c:if test="${supplier_enterprise.type == 2}">
						        	合伙企业
						        </c:if>
						        <c:if test="${supplier_enterprise.type == 3}">
						        	集体企业
						        </c:if>
						        <c:if test="${supplier_enterprise.type == 4}">
						        	国有企业
						        </c:if>
				        		<br>
				        		<span>营业地址：</span>${supplier_enterprise.businessAddress}<br>
				        		<span>营业期限至：</span>${supplier_enterprise.businessPeriodTo}<br>
				        		<span>法人姓名：</span><a class="image-zoom" style="width: auto; height: auto; border: none;" href="${supplier_enterprise.legalCardPositive}" rel="prettyPhoto">${supplier_enterprise.legalName}</a><br>
						        <span>身份证号：</span>${supplier_enterprise.legalIdCard}
				        	</div>
				    	</div>
				    	<div class="financing_gong_r" style="float: none; width: 100%;">
				      		<div class="financing_gong_li" style="width: 49%; margin-right: 2%;">
				        		<div class="lines_l"><img src="${ctxStatic}/images/lines_01.jpg"></div>
				        		<div class="lines_r">
				          			<span><fmt:formatNumber type="currency" currencySymbol="￥" value="${enterprise_params.allQuota}" /></span>总额度（元）
				        		</div>
				      		</div>
				      		<div class="financing_gong_li" style="width: 49%;">
				        		<div class="lines_l"><img src="${ctxStatic}/images/lines_01.jpg"></div>
				        		<div class="lines_r">
				          			<span><fmt:formatNumber type="currency" currencySymbol="￥" value="${enterprise_params.availableQuota}" /></span>可用额度（元）
				        		</div>
				      		</div>
				    	</div>
				    	<div class="clear"></div>
				  	</div>
				  	<div class="financing_hu clear" style="width: 100%;">
				    	<div class="financing_hu_con" style="width: 49%; margin-right: 2%;">
				      		<div class="financing_hu_header" style="width: 100%; background: url(${ctxStatic}/images/curve_bg.jpg)"><img src="${ctxStatic}/images/icon_13.jpg">融资账户</div>
				      		<div class="financing_hu_nei" style="width: 100%;">
				        		<ul>
				          			<li><span>账户名</span>${enterprise_params.loanName}</li>
				          			<li><span>银行账户</span>${enterprise_params.loanAccount}</li>
				          			<li><span>开户行</span>${enterprise_params.loanOpenBank}</li>
				        		</ul>
				        		<div class="clear"></div>
				      		</div>
				      		<div class="clear"></div>
				    	</div>
				    	<div class="financing_hu_con1" style="width: 49%;">
				    		<div class="financing_hu_header" style="width: 100%; background: url(${ctxStatic}/images/curve_bg.jpg)"><img src="${ctxStatic}/images/icon_14.jpg">回款账户</div>
				      		<div class="financing_hu_nei" style="width: 100%;">
				        		<ul>
				          			<li><span>账户名</span>${enterprise_params.returnName}</li>
				          			<li><span>银行账户</span>${enterprise_params.returnAccount}</li>
				          			<li><span>开户行</span>${enterprise_params.returnOpenBank}</li>
				        		</ul>
				        		<div class="clear"></div>
				      		</div>
				      		<div class="clear"></div>
				    	</div>
				    	<div class="clear"></div>
					</div>
				
					<!-- 征信报告 -->
				  	<c:if test="${not empty zxContract}">
				  		<div class="financing_xi">
				        	<div class="financing_xi_title">
					        	<span class="financing_xi_dian1"></span><span class="financing_xi_name1">征信报告</span>
					        </div>
					        <div class="financing_table">
					        	<table width="100%" border="0" cellpadding="0" cellspacing="0">
					            	<tr class="financing_header">
					              		<td>征信报告名称</td>
					              		<td>操作</td>
					            	</tr>
					            	<c:forEach items="${zxContract}" var="contract">
					            		<tr>
						                	<td>${contract.name}</td>
						                  	<td>
						                    	<a href="${contract.url}" target="_blank" class="btn btn-info btn-xs">查看 / 下载</a>
						                  	</td>
						                </tr>
					            	</c:forEach>
					          	</table>
					        </div>
					        <div class="clear"></div>
						</div>
				  	</c:if>
				  	
				  	<!-- 其他相关材料 -->
					  	<div class="financing_xi">
				        	<div class="financing_xi_title">
					        	<span class="financing_xi_dian1"></span><span class="financing_xi_name1">其他材料</span>
					        </div>
					        <div class="financing_table">
					        	<table width="100%" border="0" cellpadding="0" cellspacing="0">
					            	<tr class="financing_header">
					              		<td>材料名称</td>
					              		<td>操作</td>
					            	</tr>
					            	<tr>
					                	<td>平台操作授权书</td>
					                  	<td>
					                    	<a href="${supplier_enterprise.platformOperateAuthor}" target="_blank" class="btn btn-info btn-xs">查看 / 下载</a>
					                  	</td>
					                </tr>
					                <tr>
					                	<td>开户许可证</td>
					                  	<td>
					                    	<a href="${supplier_enterprise.openingPermitLetter}" target="_blank" class="btn btn-info btn-xs">查看 / 下载</a>
					                  	</td>
					                </tr>
					                <tr>
					                	<td>机构信用证</td>
					                  	<td>
					                    	<a href="${supplier_enterprise.officeCreditLetter}" target="_blank" class="btn btn-info btn-xs">查看 / 下载</a>
					                  	</td>
					                </tr>
					                <tr>
					                	<td>公司章程</td>
					                  	<td>
					                    	<a href="${supplier_enterprise.companyConstitution}" target="_blank" class="btn btn-info btn-xs">查看 / 下载</a>
					                  	</td>
					                </tr>
					          	</table>
					        </div>
					        <div class="clear"></div>
						</div>
					
				  	<div class="clear"></div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
</body>
</html>