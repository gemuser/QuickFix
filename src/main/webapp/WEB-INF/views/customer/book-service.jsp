<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="activeNav" value="services"/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Book Service - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"></head>
<body class="market-page"><%@ include file="../includes/customer-navbar.jspf" %><main class="market-main"><div class="container">
    <section class="market-two-column">
        <article class="card"><div class="card__header"><h1 class="card__title">Complete booking details</h1></div><form class="card__body form" method="post" action="${pageContext.request.contextPath}/booking"><c:if test="${not empty bookingError}"><div class="alert alert--error">${bookingError}</div></c:if><input type="hidden" name="serviceId" value="${service.serviceId}"><div class="form__grid"><label class="field"><span class="field__label">Date</span><input class="field__control" type="date" name="bookingDate" value="${param.bookingDate}" required></label><label class="field"><span class="field__label">Time</span><input class="field__control" type="time" name="bookingTime" value="${param.bookingTime}" required></label></div><label class="field"><span class="field__label">Address</span><textarea class="field__control" name="address" placeholder="Enter your service address" required>${param.address}</textarea><span class="field__error">${addressError}</span></label><label class="field"><span class="field__label">Problem description</span><textarea class="field__control" name="notes" placeholder="Describe what needs to be fixed">${param.notes}</textarea></label><button class="btn btn--primary" type="submit">Confirm Booking</button></form></article>
        <aside class="card"><div class="card__header"><h2 class="card__title">Selected service</h2></div><div class="card__body"><dl class="summary-list"><div class="summary-list__row"><dt>Service</dt><dd>${service.serviceTitle}</dd></div><div class="summary-list__row"><dt>Provider</dt><dd>${profile.providerName}</dd></div><div class="summary-list__row"><dt>Price</dt><dd class="price">Rs. ${service.price}</dd></div></dl></div></aside>
    </section>
</div></main></body>
</html>
