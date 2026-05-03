<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="navBase" value="provider"/><c:set var="navRole" value="Provider"/><c:set var="activeNav" value="past"/><c:set var="pageTitle" value="Past Bookings"/><c:set var="pageSubtitle" value="Review completed and historical booking records."/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Past Bookings - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css"></head>
<body><div class="app-shell"><%@ include file="../includes/sidebar.jspf" %><main><%@ include file="../includes/topbar.jspf" %><section class="page">
    <section class="card table-card"><table class="table"><thead><tr><th>Customer</th><th>Service</th><th>Date</th><th>Status</th></tr></thead><tbody><c:forEach items="${bookings}" var="b"><tr><td>${b.customerName}</td><td>${b.serviceTitle}</td><td>${b.bookingDate}</td><td><span class="badge badge--${fn:toLowerCase(b.statusName)}">${b.statusName}</span></td></tr></c:forEach></tbody></table></section>
</section></main></div></body>
</html>
