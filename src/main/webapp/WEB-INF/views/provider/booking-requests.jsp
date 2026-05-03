<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="navBase" value="provider"/><c:set var="navRole" value="Provider"/><c:set var="activeNav" value="requests"/><c:set var="pageTitle" value="Booking Requests"/><c:set var="pageSubtitle" value="Accept, reject, or update customer bookings."/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Booking Requests - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css"></head>
<body><div class="app-shell"><%@ include file="../includes/sidebar.jspf" %><main><%@ include file="../includes/topbar.jspf" %><section class="page">
    <div class="content-grid"><c:forEach items="${bookings}" var="b"><article class="card request-card"><div><h2>${b.serviceTitle}</h2><p class="list-meta">${b.customerName} - ${b.bookingDate} ${b.bookingTime}</p><span class="badge badge--${fn:toLowerCase(b.statusName)}">${b.statusName}</span></div><form class="request-card__actions" method="post"><input type="hidden" name="action" value="bookingStatus"><input type="hidden" name="bookingId" value="${b.bookingId}"><select class="field__control" name="status"><option>ACCEPTED</option><option>REJECTED</option><option>IN_PROGRESS</option><option>COMPLETED</option></select><button class="btn btn--primary btn--sm" type="submit">Update</button></form></article></c:forEach><c:if test="${empty bookings}"><div class="empty-state">No booking requests yet. New customer requests will appear here.</div></c:if></div>
</section></main></div></body>
</html>
