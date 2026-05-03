<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="navBase" value="admin"/><c:set var="navRole" value="Admin"/><c:set var="activeNav" value="bookings"/><c:set var="pageTitle" value="Monitor Bookings"/><c:set var="pageSubtitle" value="Search, filter, and inspect booking flow."/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Monitor Bookings - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css"></head>
<body><div class="app-shell"><%@ include file="../includes/sidebar.jspf" %><main><%@ include file="../includes/topbar.jspf" %><section class="page">
    <section class="card"><div class="card__header"><h1 class="card__title">Filters</h1></div><form class="card__body form"><div class="form__grid"><label class="field"><span class="field__label">Status</span><select class="field__control"><option>All statuses</option><option>Pending</option><option>Accepted</option><option>Completed</option><option>Cancelled</option></select></label><label class="field"><span class="field__label">Date</span><input class="field__control" type="date"></label></div></form></section>
    <section class="card table-card"><table class="table"><thead><tr><th>ID</th><th>Customer</th><th>Provider</th><th>Service</th><th>Date</th><th>Status</th></tr></thead><tbody><c:forEach items="${bookings}" var="b"><tr><td>${b.bookingId}</td><td>${b.customerName}</td><td>${b.providerName}</td><td>${b.serviceTitle}</td><td>${b.bookingDate} ${b.bookingTime}</td><td><span class="badge badge--${fn:toLowerCase(b.statusName)}">${b.statusName}</span></td></tr></c:forEach></tbody></table></section>
</section></main></div></body>
</html>
