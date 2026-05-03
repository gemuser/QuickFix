<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="navBase" value="admin"/><c:set var="navRole" value="Admin"/><c:set var="activeNav" value="complaints"/><c:set var="pageTitle" value="Complaints"/><c:set var="pageSubtitle" value="Review issues and update resolution status."/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Complaints - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css"></head>
<body><div class="app-shell"><%@ include file="../includes/sidebar.jspf" %><main><%@ include file="../includes/topbar.jspf" %><section class="page">
    <section class="card table-card"><table class="table"><thead><tr><th>Subject</th><th>Raised By</th><th>Status</th><th>Response</th></tr></thead><tbody><c:forEach items="${complaints}" var="c"><tr><td><strong>${c.subject}</strong><p class="list-meta">${c.description}</p></td><td>${c.raisedByName}</td><td><span class="badge badge--${fn:toLowerCase(c.status)}">${c.status}</span></td><td><form class="form" method="post"><input type="hidden" name="action" value="complaint"><input type="hidden" name="complaintId" value="${c.complaintId}"><select class="field__control" name="status"><option>IN_REVIEW</option><option>RESOLVED</option><option>REJECTED</option></select><input class="field__control" name="adminResponse" placeholder="Admin response"><button class="btn btn--primary btn--sm" type="submit">Update</button></form></td></tr></c:forEach></tbody></table></section>
</section></main></div></body>
</html>
