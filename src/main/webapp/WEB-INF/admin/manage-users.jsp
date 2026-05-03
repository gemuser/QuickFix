<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="navBase" value="admin"/><c:set var="navRole" value="Admin"/><c:set var="activeNav" value="users"/><c:set var="pageTitle" value="Manage Users"/><c:set var="pageSubtitle" value="Review accounts and update access status."/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Manage Users - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css"></head>
<body><div class="app-shell"><%@ include file="../includes/sidebar.jspf" %><main><%@ include file="../includes/topbar.jspf" %><section class="page">
    <section class="card table-card"><table class="table"><thead><tr><th>Name</th><th>Email</th><th>Role</th><th>Status</th><th>Action</th></tr></thead><tbody><c:forEach items="${users}" var="u"><tr><td><strong>${u.fullName}</strong></td><td>${u.email}</td><td>${u.roleName}</td><td><span class="badge badge--${fn:toLowerCase(u.status)}">${u.status}</span></td><td><form class="table__actions" method="post"><input type="hidden" name="action" value="userStatus"><input type="hidden" name="userId" value="${u.userId}"><select class="field__control" name="status"><option>ACTIVE</option><option>BLOCKED</option><option>REJECTED</option></select><button class="btn btn--primary btn--sm" type="submit">Save</button></form></td></tr></c:forEach></tbody></table></section>
</section></main></div></body>
</html>
