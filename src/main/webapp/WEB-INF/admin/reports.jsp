<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="navBase" value="admin"/><c:set var="navRole" value="Admin"/><c:set var="activeNav" value="reports"/><c:set var="pageTitle" value="Reports"/><c:set var="pageSubtitle" value="Summaries for users, bookings, providers, and services."/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Reports - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css"></head>
<body><div class="app-shell"><%@ include file="../includes/sidebar.jspf" %><main><%@ include file="../includes/topbar.jspf" %><section class="page">
    <div class="metric-grid"><article class="card metric-card"><p class="metric-card__label">Total users</p><p class="metric-card__value">${report.totalUsers}</p></article><article class="card metric-card"><p class="metric-card__label">Total bookings</p><p class="metric-card__value">${report.totalBookings}</p></article><article class="card metric-card"><p class="metric-card__label">Active providers</p><p class="metric-card__value">${report.activeProviders}</p></article></div>
    <section class="card"><div class="card__header"><h2 class="card__title">Popular services</h2></div><div class="card__body activity-list"><c:forEach items="${report.popularServices}" var="row"><article class="list-item"><p class="list-item__title">${row}</p><p class="list-meta">Service demand summary</p></article></c:forEach></div></section>
</section></main></div></body>
</html>
