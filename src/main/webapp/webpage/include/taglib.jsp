<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="echarts" uri="/WEB-INF/tlds/echarts.tld" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<%@ taglib prefix="table" tagdir="/WEB-INF/tags/table" %>
<%@ taglib prefix="t" uri="/menu-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}${fns:getAdminPath()}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<script>
    // 手机号
    var reg_mobile = /^(1[0-9][0-9])[0-9]{8}$/;
    // 用户名
    var reg_password = /^[A-Za-z0-9_]{6,12}$/;
    // 邮箱
    var reg_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;

    var reg_contact = /^[\u4e00-\u9fa5]{2,4}$/;

    var error_html= "<img src=\"${ctxStatic}/images/ti1.png\">";

    var success_html= "<img src=\"${ctxStatic}/images/ti2.png\">";


</script>