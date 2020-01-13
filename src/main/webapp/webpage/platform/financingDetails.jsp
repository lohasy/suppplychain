<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--融资管理--融资详情</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/checkbix.min.css" rel="stylesheet">
	<link href="${ctxStatic}/online/prettyPhoto.css" rel="stylesheet">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet" />
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>
					<c:if test="${not empty fns:getUser().supplier.id }">
						<a href="${ctx}/rzglall/gyslist">融资列表</a>
					</c:if>
					<c:if test="${not empty fns:getUser().core.id }">
						<a href="${ctx}/rzglall/hxqylist">融资列表</a>
					</c:if>
					<c:if test="${empty fns:getUser().supplier.id and empty fns:getUser().core.id }">
						<a href="${ctx}/rzglall/list">融资管理</a>
					</c:if>
				</h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;融资详情</span>
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
				<div class="main_body clear">
            		<script type="text/javascript" src="${ctxStatic}/online/jquery.min.js"></script>
					<script src="${ctxStatic}/online/script.js" type="text/javascript"></script>
					<script src="${ctxStatic}/online/jquery.prettyPhoto.js" type="text/javascript"></script>
					
					<!--融资详情NAV 平台管理-->
					<div class="rong_nav" style="width: 100%;">
						<ul>
					    	<li><a href="${ctx}/rzglall/redirect?type=0" class="nav_a">融资详情</a></li>
					    	<li><a href="${ctx}/rzglall/redirect?type=1" class="nav_b">供应商详情</a></li>
					    	<%-- <c:if test="${not empty fns:getUser().supplier.id }">
								<li><a href="${ctx}/rzglall/redirect?type=2" class="nav_b">应收账款转让合同</a></li>
							</c:if>
							<c:if test="${not empty fns:getUser().core.id }">
								<li><a href="${ctx}/rzglall/redirect?type=2" class="nav_b">应付账款转让合同</a></li>
							</c:if>
							<c:if test="${empty fns:getUser().supplier.id and empty fns:getUser().core.id }">
								<li><a href="${ctx}/rzglall/redirect?type=2" class="nav_b">单据账款转让合同</a></li>
							</c:if> --%>
					    	<li><a href="${ctx}/rzglall/redirect?type=3" class="nav_b">融资合同</a></li>
					  	</ul>
					</div>
					<script type="text/javascript" src="${ctxStatic}/online/checkbix.min.js"></script>
					<script type="text/javascript">
					    $(function () {
					        // 全选
					        $("#checkall").bind("click", function () {
					            $('input:checkbox').not(this).prop('checked', this.checked);
					        });
					    });
					</script>

					<form name="mgmtform" id="mgmtform" enctype="multipart/form-data" action="" accept-charset="UTF-8" method="post">
    					<div class="financing clear" style="width: 100%;">
      						<div class="financing_xi">
					        	<div class="financing_xi_title">
					          		<div class="financing_xi_jin">
						            	<span>融资总金额（元）：<font color="#ff0000"><fmt:formatNumber type="currency" currencySymbol="￥" value="${financing_info.totalAmount }" /></font></span><span>月息费率（%）：<font color="#ff0000">${financing_info.monthRate }</font></span><span>月融资成本（元）： <font color="#ff0000"><fmt:formatNumber type="currency" currencySymbol="￥" value="${financing_info.monthFinancing }" /></font></span>
						          	</div>
						          	<span class="financing_xi_dian1"></span>
						          	<span class="financing_xi_name1">单据详情</span>
						        </div>
        						<div class="financing_table">
          							<table width="100%" border="0" cellpadding="0" cellspacing="0">
            							<thead>
            								<tr class="financing_header">
						                  		<td width="10">
					                    			<input type="checkbox" id="checkall" name="checkall" value="0" title="全选" class="checkbix" data-text=""><label aria-label="" role="checkbox" for="checkall" class="checkbix"><span class=""></span></label>
							                  	</td>
								              	<td>单据号</td>
								              	<td>合同号</td>
								              	<td>单据金额（元）</td>
								              	<td>单据起始日期</td>
								              	<td>单据到期日</td>
								              	<td>融资到期日</td>
								              	<td>融资比例（%）</td>
								              	<td>融资金额（元）</td>
								              	<td>预计融资成本（元）</td>
								              	<td>单据状态</td>
								              	<td style="width: 80px;">操作</td>
							            	</tr>
           								</thead>
            							<tbody>
           									<tr>
                      							<td><input name="select_state[1566]" type="hidden" value="no"><input class="checkbix" data-text="" type="checkbox" value="yes" name="select_state[1566]" id="select_state_1566"><label aria-label="" role="checkbox" for="select_state_1566" class="checkbix"><span class=""></span></label></td>
						                  		<td>${bill_info.num}</td>
						                  		<td>${bill_info.contractNum}</td>
							                  	<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${bill_info.amount}" /></td>
							                  	<td><fmt:formatDate value="${bill_info.startDate}" pattern="yyyy-MM-dd"/></td>
							                  	<td><fmt:formatDate value="${bill_info.endDate}" pattern="yyyy-MM-dd"/></td>
							                  	<td><fmt:formatDate value="${financing_info.expiryDate}" pattern="yyyy-MM-dd"/></td>
							                  	<td>${bill_info.supplierEnterpriseId.paramsId.financingRatio}</td>
							                  	<td><fmt:formatNumber type="currency" currencySymbol="￥" value="${bill_info.financingAmount}" /></td>
							                  	<td>${bill_info.planFinancingCost}</td>
							                  	<td>
													<c:if test="${bill_info.state == 0}">
														待提交
													</c:if>
													<c:if test="${bill_info.state == 1}">
														待供应商融资
													</c:if>
													<c:if test="${bill_info.state == 2}">
														待供应商审核
													</c:if>
													<c:if test="${bill_info.state == 3}">
														待签约
													</c:if>
													<c:if test="${bill_info.state == 4}">
														供应商审核不通过
													</c:if>
													<c:if test="${bill_info.state == 5}">
														待银行配置
													</c:if>
													<c:if test="${bill_info.state == 6}">
														待银行审核
													</c:if>
													<c:if test="${bill_info.state == 7}">
														银行审核不通过
													</c:if>
													<c:if test="${bill_info.state == 8}">
														待银行复核
													</c:if>
													<c:if test="${bill_info.state == 9}">
														银行复核不通过
													</c:if>
													<c:if test="${bill_info.state == 10}">
														待银行放款
													</c:if>
													<c:if test="${bill_info.state == 11}">
														已放款
													</c:if>
													<c:if test="${bill_info.state == 12}">
														待银行清分
													</c:if>
													<c:if test="${bill_info.state == 13}">
														已完成
													</c:if>
												</td>
							                  	<td>
							                    	<a href="javascript:void(0);" onclick="openDialogView('单据详情', '${ctx}/yfzk/redirect-info?id=${bill_info.id}', '1200px', '700px')" class="yao1 bill_details" data-kid="32" data-bid="847" data-cid="1">单据详情</a>
							                  	</td>
							                </tr>
            							</tbody>
									</table>
        						</div>
   							</div>
    					</div>
					</form>
          		</div>
			</div>
		</div>
	</div>
</body>
</html>