<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--企业管理--审核供应商</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css" />
	<script src="${ctxStatic}/online/lgt-web.js"></script>
	<link href="${ctxStatic}/online/prettyPhoto-3.1.6.css" rel="stylesheet" type="text/css">
	<script src="${ctxStatic}/online/jquery.prettyPhoto-3.1.6.js" type="text/javascript"></script>
	<style type="text/css" rel="stylesheet">
		.row dd {
			margin: 10px 180px;
		}
	</style>
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
				<h5><a href="${ctx}/gys/gys-index">供应商管理</a></h5>
				<span class="page-title">&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;审核供应商</span>
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
				<div class="row">
	                <div class="ibox-title" style="background-color: #f5f5f5;">
	                	<h5>一、企业信息</h5>
	                </div>
                	<div class="ibox-content">
                  		<dl class="dl-horizontal">
                    		<dt>企业名称：</dt>
                    		<dd>${supplier_enterprise.name}</dd>
                    		<dt>组织机构代码：</dt>
                    		<dd>${supplier_enterprise.orgCode}</dd>
                    		<dt>营业期限至：</dt>
                    		<dd>${supplier_enterprise.businessPeriodTo}</dd>
                    		<dt>注册资本：</dt>
                    		<dd>${supplier_enterprise.registeredCapital}</dd>
                    		<dt>企业类型：</dt>
                    		<c:choose>
                    			<c:when test="${supplier_enterprise.type == '0'}">
                    				<dd>股份有限公司</dd>
                    			</c:when>
                    			<c:when test="${supplier_enterprise.type == '1'}">
                    				<dd>有限责任公司</dd>
                    			</c:when>
                    			<c:when test="${supplier_enterprise.type == '2'}">
                    				<dd>合伙企业</dd>
                    			</c:when>
                    			<c:when test="${supplier_enterprise.type == '3'}">
                    				<dd>集体企业</dd>
                    			</c:when>
                    			<c:when test="${supplier_enterprise.type == '4'}">
                    				<dd>国有企业</dd>
                    			</c:when>
                    			<c:otherwise>
                    				<dd>无</dd>
                    			</c:otherwise>
                    		</c:choose>
                    		<dt>营业地址：</dt>
                    		<dd>${supplier_enterprise.businessAddress}</dd>
                    		<dt>营业执照：</dt>
                    		<dd>
                      			<a class="image-zoom" href="${supplier_enterprise.businessLicense}" rel="prettyPhoto"><img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${supplier_enterprise.businessLicense}" /></a>
                    		</dd>
                  		</dl>
               		</div>

	                <div class="ibox-title" style="background-color: #f5f5f5;">
                  		<h5>二、法人信息</h5>
	                </div>
                	<div class="ibox-content">
                  		<dl class="dl-horizontal">
                  			<dt>姓名：</dt>
                    		<dd>${supplier_enterprise.legalName}</dd>
                    		<dt>性别：</dt>
                    		<dd>
                    			<c:if test="${supplier_enterprise.legalSex == '0'}">
	                    			男
	                    		</c:if>
	                    		<c:if test="${supplier_enterprise.legalSex == '1'}">
	                    			女
	                    		</c:if>
                    		</dd>
                    		<dt>民族：</dt>
                    		<dd>${supplier_enterprise.legalNation}</dd>
                    		<dt>住址：</dt>
                    		<dd>${supplier_enterprise.legalAddress}</dd>
                    		<dt>身份证号码：</dt>
                    		<dd>${supplier_enterprise.legalIdCard}</dd>
                    		<dt>身份证正面：</dt>
                    		<dd>
                      			<a class="image-zoom" href="${supplier_enterprise.legalCardPositive}" rel="prettyPhoto"><img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${supplier_enterprise.legalCardPositive}" /></a>
                    		</dd>
                    		<dt>身份证反面：</dt>
                    		<dd>
                      			<a class="image-zoom" href="${supplier_enterprise.legalCardBack}" rel="prettyPhoto"><img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${supplier_enterprise.legalCardBack}" /></a>
                    		</dd>
                    		<dt>有效期：</dt>
                    		<dd>${supplier_enterprise.legalCardValidity}</dd>
                    		<dt>签证机关：</dt>
                    		<dd>${supplier_enterprise.legalCardOffice}</dd>
                  		</dl>
               		</div>
               		
               		<div class="ibox-title" style="background-color: #f5f5f5;">
                  		<h5>三、其他相关证照</h5>
	                </div>
                	<div class="ibox-content">
                  		<dl class="dl-horizontal">
                    		<dt>开户许可证：</dt>
				      		<dd>
				        		<div id="gallery5">
				          			<a class="image-zoom" href="${supplier_enterprise.openingPermitLetter}" rel="prettyPhoto">
				            			<img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${supplier_enterprise.openingPermitLetter}" />
				          			</a>
				        		</div>
				      		</dd>
				      		<dt>机构信用证：</dt>
				      		<dd>
				        		<div id="gallery6">
				          			<a class="image-zoom" href="${supplier_enterprise.officeCreditLetter}" rel="prettyPhoto">
				            			<img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${supplier_enterprise.officeCreditLetter}" />
				          			</a>
				        		</div>
				      		</dd>
				      		<dt>公司章程：</dt>
				      		<dd>
				      			<c:if test="${empty supplier_enterprise.companyConstitution}">
				      				无
				      			</c:if>
				      			<c:if test="${not empty supplier_enterprise.companyConstitution}">
				      				<a target="_blank" href="${supplier_enterprise.companyConstitution}">下载/查看</a>
				      			</c:if>
				      		</dd>
                  		</dl>
               		</div>
               		
               		<div class="ibox-title" style="background-color: #f5f5f5;">
                  		<h5>四、操作员信息</h5>
	                </div>
                	<div class="ibox-content">
                  		<dl class="dl-horizontal">
                  			<dt>姓名：</dt>
                    		<dd>${supplier_enterprise.operatorName}</dd>
                    		<dt>性别：</dt>
                    		<dd>
                    			<c:if test="${supplier_enterprise.legalSex == '0'}">
	                    			男
	                    		</c:if>
	                    		<c:if test="${supplier_enterprise.legalSex == '1'}">
	                    			女
	                    		</c:if>
                    		</dd>
                    		<dt>民族：</dt>
                    		<dd>${supplier_enterprise.operatorNation}</dd>
                    		<dt>住址：</dt>
                    		<dd>${supplier_enterprise.operatorAddress}</dd>
                    		<dt>身份证号码：</dt>
                    		<dd>${supplier_enterprise.operatorIdCard}</dd>
                    		<dt>身份证正面：</dt>
                    		<dd>
                      			<a class="image-zoom" href="${supplier_enterprise.operatorCardPositive}" rel="prettyPhoto"><img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${supplier_enterprise.operatorCardPositive}" /></a>
                    		</dd>
                    		<dt>身份证反面：</dt>
                    		<dd>
                      			<a class="image-zoom" href="${supplier_enterprise.operatorCardBack}" rel="prettyPhoto"><img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${supplier_enterprise.operatorCardBack}" /></a>
                    		</dd>
                    		<dt>有效期：</dt>
                    		<dd>${supplier_enterprise.operatorCardValidity}</dd>
                    		<dt>签证机关：</dt>
                    		<dd>${supplier_enterprise.operatorCardOffice}</dd>
                  		</dl>
               		</div>

	                <div class="ibox-title" style="background-color: #f5f5f5;">
    					<h5>五、负责人信息</h5>
  					</div>
  					<div class="ibox-content">
				    	<dl class="dl-horizontal">
				      		<dt>姓名：</dt>
				      		<dd>${supplier_enterprise.agencyName}</dd>
				      		<dt>性别：</dt>
                    		<dd>
                    			<c:if test="${supplier_enterprise.agencySex== '0'}">
	                    			男
	                    		</c:if>
	                    		<c:if test="${supplier_enterprise.agencySex == '1'}">
	                    			女
	                    		</c:if>
                    		</dd>
                    		<dt>民族：</dt>
                    		<dd>${supplier_enterprise.agencyNation}</dd>
                    		<dt>住址：</dt>
                    		<dd>${supplier_enterprise.agencyAddress}</dd>
				      		<dt>身份证号码：</dt>
                    		<dd>${supplier_enterprise.agencyIdCard}</dd>
				      		<dt>手机号：</dt>
				      		<dd>${supplier_enterprise.agencyPhone}</dd>
				      		<dt>邮箱：</dt>
				      		<dd>${supplier_enterprise.agencyEmail}</dd>
				      		<dt>身份证正面：</dt>
                    		<dd>
                      			<a class="image-zoom" href="${supplier_enterprise.agencyCardPositive}" rel="prettyPhoto"><img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${supplier_enterprise.agencyCardPositive}" /></a>
                    		</dd>
                    		<dt>身份证反面：</dt>
                    		<dd>
                      			<a class="image-zoom" href="${supplier_enterprise.agencyCardBack}" rel="prettyPhoto"><img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${supplier_enterprise.agencyCardBack}" /></a>
                    		</dd>
                    		<dt>有效期：</dt>
                    		<dd>${supplier_enterprise.agencyCardValidity}</dd>
                    		<dt>签证机关：</dt>
                    		<dd>${supplier_enterprise.agencyCardOffice}</dd>
				    	</dl>
				  	</div>
				  	
				  	<div class="ibox-title" style="background-color: #f5f5f5;">
    					<h5>六、平台操作授权书</h5>
  					</div>
  					<div class="ibox-content">
				    	<dl class="dl-horizontal">
				      		<dt>平台操作授权书：</dt>
				      		<dd>
				        		<div id="gallery4">
				          			<a class="image-zoom" href="${supplier_enterprise.platformOperateAuthor}" rel="prettyPhoto">
				            			<img style="max-width:320px;max-height:240px;width:320px;height:240px" src="${supplier_enterprise.platformOperateAuthor}" />
				          			</a>
				        		</div>
				      		</dd>
				    	</dl>
				  	</div>
				  	
				  	<div class="ibox-title" style="background-color: #f5f5f5;">
                  		<h5>七、股东占比信息</h5>
                	</div>
                	<div class="ibox-content">
                  		<dl class="dl-horizontal">
                    		<c:if test="${not empty shareHolders}">
			      				<c:forEach items="${shareHolders}" var="supplier_shareholder">
			      					<dt>姓名：</dt>
		                    		<dd>${supplier_shareholder.name}</dd>
		                    		<dt>身份证号：</dt>
		                    		<dd>${supplier_shareholder.idNum}</dd>
		                    		<dt>占股比例：</dt>
		                    		<dd>${supplier_shareholder.ratio} %</dd>
			      				</c:forEach>
			      			</c:if>
                  		</dl>
                	</div>
				  	
				  	<form name="mgmtform" id="mgmtform" action="${ctx}/gys/subgysShenHe" accept-charset="UTF-8" method="post">
				  	<input type="hidden" name="id" value="${supplier_enterprise.id}"/>
				  		<div class="ibox-title" style="background-color: #f5f5f5;">
	                  		<h5>审核结果</h5>
	               		</div>
	               		<div class="ibox-content">
	                  		<div>
	                    		<label class="check-inline">
	                      			<div class="iradio_square-green" style="position: relative;"><input type="radio" value="-1" id="state" name="state" class="i-checks" style="position: absolute; opacity: 0;"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins></div> 审核通过
	                    		</label>
	                 	 	</div>
	                  		<div style="margin-top: 10px;">
	                    		<label class="check-inline">
	                      			<div class="iradio_square-green" style="position: relative;"><input type="radio" value="2" id="state" name="state" class="i-checks" style="position: absolute; opacity: 0;"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins></div> 审核不通过
	                    		</label>
	                    		<textarea style="margin-top: 5px;" id="remarks" name="remarks" class="form-control" placeholder="审核不通过补充说明"></textarea>
	               			</div>
	                	</div>
	  					<div class="col-sm-3" style="margin-left: 5px;">
	                    	<input type="submit" value="审核确定" id="do_audit" name="do_audit" class="btn btn-primary">
	                  	</div>
				  	</form>
              	</div>
			</div>
		</div>
	</div>
</body>
</html>