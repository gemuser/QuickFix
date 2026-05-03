<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="navBase" value="provider"/><c:set var="navRole" value="Provider"/><c:set var="activeNav" value="dashboard"/><c:set var="pageTitle" value="Provider Dashboard"/><c:set var="pageSubtitle" value="Manage bookings, services, and availability."/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Provider Dashboard - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css"></head>
<body><div class="app-shell"><%@ include file="../includes/sidebar.jspf" %><main><%@ include file="../includes/topbar.jspf" %><section class="page">
    <header class="page__header"><div><p class="page__eyebrow">Overview</p><h1 class="page__title">Provider dashboard</h1><p class="page__description">Keep your service catalog and booking queue up to date.</p></div><a class="btn btn--primary" href="manage-services">Add service</a></header>
    <div class="metric-grid">
        <article class="card metric-card"><p class="metric-card__label">Verification</p><p class="metric-card__value">${profile.verificationStatus}</p><p class="metric-card__note">Admin approval status</p></article>
        <article class="card metric-card"><p class="metric-card__label">Rating</p><p class="metric-card__value">${profile.averageRating}</p><p class="metric-card__note">Average customer score</p></article>
        <article class="card metric-card"><p class="metric-card__label">Services</p><p class="metric-card__value">${fn:length(services)}</p><p class="metric-card__note">Active service listings</p></article>
    </div>
    <section class="card"><div class="card__header"><h2 class="card__title">Booking requests</h2><a href="booking-requests">Review requests</a></div><div class="card__body activity-list"><article class="list-item"><p class="list-item__title">New requests arrive here</p><p class="list-meta">Accept, reject, or mark services completed from the requests page.</p></article></div></section>
</section></main></div></body>
</html>
