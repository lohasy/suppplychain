<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--融资管理--单据详情</title>
	<meta name="decorator" content="default"/>
	<!--主内容 主体-->
 		<link href="${ctxStatic}/online/prettyPhoto.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctxStatic}/online/jquery.min.js"></script>
	<script src="${ctxStatic}/online/script.js" type="text/javascript"></script>
	<script src="${ctxStatic}/online/jquery.prettyPhoto.js" type="text/javascript"></script>
	<link href="${ctxStatic}/online/financing.css" rel="stylesheet" />
	<script>
		$(function(){
			$("a[rel^='prettyPhoto']").prettyPhoto();
		});
	</script>
</head>
<body class="gray-bg" style="background: #ffffff;">
	<div>
		<!--main-->
  		<div class="financing clear" style="width:100%;">
    		<div class="financing_xi">
      			<div class="financing_xi_title"><span class="financing_xi_dian1"></span><span class="financing_xi_name1">单据详情</span></div>
      			<div class="financing_table">
        			<table width="100%" border="0" cellpadding="0" cellspacing="0">
          				<thead>
          					<tr class="financing_header" style="height: 25px;">
            					<th style="text-align: center;">单据号</th>
            					<th style="text-align: center;">合同号</th>
            					<th style="text-align: center;">单据金额（元）</th>
            					<th style="text-align: center;">单据起始日期</th>
            					<th style="text-align: center;">单据到期日</th>
            					<th style="text-align: center;">剩余融资天数</th>
            					<th style="text-align: center;">单据状态</th>
            					<th style="text-align: center;">操作</th>
          					</tr>
          				</thead>
						<tbody>
							<tr>
            					<td>${bill_info.num}</td>
            					<td>${bill_info.contractNum}</td>
          						<td>
									<fmt:formatNumber type="currency" currencySymbol="￥" value="${bill_info.amount}" />
								</td>
            					<td><fmt:formatDate value="${bill_info.startDate}" pattern="yyyy-MM-dd"/></td>
            					<td><fmt:formatDate value="${bill_info.endDate}" pattern="yyyy-MM-dd"/></td>
            					<td>${bill_info.dayDiffValue}</td>
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
           							<a href="javascript:void(0);" onclick="openDialogView('业务数据', '${ctx}/fdCtrl/findPaymentListByPage?billId.id=${bill_info.id}', '1200px', '700px', 'content-frame');" style="text-align: center;" class="yao1 upload_bills">业务数据</a>
           						</td>
          					</tr>
						</tbody>
          			</table>
      			</div>
      			<div class="clear"></div>
    		</div>
    		<div class="financing_xi clear">
      			<div class="financing_xi_title"><span class="financing_xi_dian3"></span><span class="financing_xi_name3">单据内容</span></div>
      			<div class="financing_content">${bill_info.content }</div>
      			<div class="clear"></div>
    		</div>
    		<div class="financing_xi clear">
      			<div class="financing_xi_title"><span class="financing_xi_dian4"></span><span class="financing_xi_name4">应收账款凭证</span></div>
      			<div class="financing_xi_list">
        			<ul>
              			<c:forEach items="${findlist}" var="voucherinfo">
              				<c:if test="${voucherinfo.type == 1}">
              					<li>
              						<a class="image-zoom" href="${voucherinfo.url}" rel="prettyPhoto[gallery]"><img src="${voucherinfo.url}" width="200" height="150"></a>
              					</li>
                			</c:if>
              			</c:forEach>
        			</ul>
        			<div class="clear"></div>
      			</div>
      			<div class="clear"></div>
    		</div>
    		<div class="financing_xi clear">
      			<div class="financing_xi_title"><span class="financing_xi_dian4"></span><span class="financing_xi_name4">应付账款凭证（核心上传）</span></div>
      			<div class="financing_xi_list">
        			<ul>
              			<c:forEach items="${findlist}" var="voucherinfo">
              				<c:if test="${voucherinfo.type == 0}">
              					<li>
              						<a class="image-zoom" href="${voucherinfo.url}" rel="prettyPhoto[gallery]"><img src="${voucherinfo.url}" width="200" height="150"></a>
              					</li>
                			</c:if>
              			</c:forEach>
        			</ul>
        			<div class="clear"></div>
      			</div>
      			<div class="clear"></div>
    		</div>
    		<div class="clear"></div>
  		</div>  
	</div>
</body>
</html>