<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="navBase" value="admin"/><c:set var="navRole" value="Admin"/><c:set var="activeNav" value="providers"/><c:set var="pageTitle" value="Manage Providers"/><c:set var="pageSubtitle" value="Approve, reject, or hold provider verification."/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Manage Providers - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css"></head>
<body><div class="app-shell"><%@ include file="../includes/sidebar.jspf" %><main><%@ include file="../includes/topbar.jspf" %><section class="page">
    <section class="card table-card"><table class="table"><thead><tr><th>Name</th><th>Email</th><th>Experience</th><th>Verification</th><th>Action</th></tr></thead><tbody><c:forEach items="${providers}" var="p"><tr><td><strong>${p.providerName}</strong></td><td>${p.email}</td><td>${p.experienceYears} years</td><td><span class="badge badge--${fn:toLowerCase(p.verificationStatus)}">${p.verificationStatus}</span></td><td><form class="table__actions" method="post"><input type="hidden" name="action" value="providerVerification"><input type="hidden" name="providerId" value="${p.userId}"><select class="field__control" name="status"><option>APPROVED</option><option>REJECTED</option><option>PENDING</option></select><button class="btn btn--primary btn--sm" type="submit">Save</button></form></td></tr></c:forEach></tbody></table></section>
</section></main></div></body>
</html>
