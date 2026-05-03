<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="activeNav" value="bookings"/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>My Bookings - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"></head>
<body class="market-page"><%@ include file="../includes/customer-navbar.jspf" %><main class="market-main"><div class="container">
    <section class="market-section"><div class="market-section__header"><div><h1>My Bookings</h1><p>Track all service requests and follow-up actions.</p></div><a class="btn btn--primary" href="search-services">Book Service</a></div><div class="booking-list"><c:forEach items="${bookings}" var="b"><article class="card booking-card"><div><h3>${b.serviceTitle}</h3><p class="list-meta">${b.providerName} - ${b.bookingDate} ${b.bookingTime}</p><span class="badge badge--${fn:toLowerCase(b.statusName)}">${b.statusName}</span></div><div class="booking-card__actions"><c:if test="${b.statusName == 'PENDING' || b.statusName == 'ACCEPTED'}"><a class="btn btn--danger btn--sm" href="cancel?bookingId=${b.bookingId}">Cancel</a></c:if><a class="btn btn--secondary btn--sm" href="feedback">Feedback</a></div></article></c:forEach><c:if test="${empty bookings}"><div class="empty-state">You have no bookings yet. Search services to book your first visit.</div></c:if></div></section>
</div></main></body>
</html>
