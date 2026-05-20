<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="activeNav" value="complaints"/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>My Complaints - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"></head>
<body class="market-page"><%@ include file="../includes/customer-navbar.jspf" %><main class="market-main"><div class="container">
    <section class="market-section"><div class="market-section__header"><div><h1>My Complaints</h1><p>Track support tickets and admin responses.</p></div></div><c:if test="${not empty error}"><div class="alert alert--error">${error}</div></c:if><div class="booking-list"><c:forEach items="${complaints}" var="c"><article class="card booking-card"><div><h3>${c.subject}</h3><p class="list-meta">${c.description}</p><c:if test="${not empty c.adminResponse}"><p class="list-meta">${c.adminResponse}</p></c:if></div><span class="badge badge--${fn:toLowerCase(c.status)}">${c.status}</span></article></c:forEach><c:if test="${empty complaints}"><div class="empty-state">No complaints submitted yet.</div></c:if></div></section>
</div></main></body>
</html>
