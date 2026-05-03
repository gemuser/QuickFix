<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="activeNav" value="services"/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Provider Details - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"></head>
<body class="market-page"><%@ include file="../includes/customer-navbar.jspf" %><main class="market-main"><div class="container">
    <section class="market-two-column">
        <div class="stack">
            <article class="card card__body"><div class="provider-summary"><span class="provider-summary__avatar">PR</span><div><h1>${profile.providerName}</h1><p>${profile.bio}</p><span class="badge badge--provider">${profile.experienceYears} years experience</span></div></div></article>
            <article class="card"><div class="card__header"><h2 class="card__title">${service.serviceTitle}</h2><span class="badge badge--accepted">${service.categoryName}</span></div><div class="card__body"><p>${service.description}</p><dl class="summary-list"><div class="summary-list__row"><dt>Rating</dt><dd class="rating">${profile.averageRating}</dd></div><div class="summary-list__row"><dt>Availability</dt><dd>Open schedule</dd></div></dl></div></article>
            <article class="card"><div class="card__header"><h2 class="card__title">Reviews</h2></div><div class="card__body review-list"><div class="list-item"><p class="list-item__title">Professional and punctual</p><p class="list-meta">Clear communication and quality work.</p></div><div class="list-item"><p class="list-item__title">Recommended</p><p class="list-meta">Service was completed on time.</p></div></div></article>
        </div>
        <aside class="card"><div class="card__header"><h2 class="card__title">Booking summary</h2></div><div class="card__body stack"><dl class="summary-list"><div class="summary-list__row"><dt>Price</dt><dd class="price">Rs. ${service.price}</dd></div><div class="summary-list__row"><dt>Provider</dt><dd>${profile.providerName}</dd></div><div class="summary-list__row"><dt>Availability</dt><dd>Check next step</dd></div></dl><a class="btn btn--primary" href="book-service?serviceId=${service.serviceId}">Book Service</a></div></aside>
    </section>
</div></main></body>
</html>
