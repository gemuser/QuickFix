<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="activeNav" value="feedback"/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Feedback - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"></head>
<body class="market-page"><%@ include file="../includes/customer-navbar.jspf" %><main class="market-main"><div class="container">
    <section class="market-two-column">
        <article class="card">
            <div class="card__header"><h1 class="card__title">Rate your service</h1></div>
            <c:if test="${not empty error}"><div class="card__body"><div class="alert alert--error">${error}</div></div></c:if>
            <c:choose>
                <c:when test="${not empty bookings}">
                    <form class="card__body form" method="post" action="${pageContext.request.contextPath}/feedback">
                        <label class="field"><span class="field__label">Booking</span><select class="field__control" name="bookingId"><c:forEach items="${bookings}" var="b"><option value="${b.bookingId}">${b.serviceTitle} - ${b.providerName}</option></c:forEach></select></label>
                        <div class="field"><span class="field__label">Star rating</span><div class="star-input"><input id="star5" type="radio" name="rating" value="5"><label for="star5">&#9733;</label><input id="star4" type="radio" name="rating" value="4"><label for="star4">&#9733;</label><input id="star3" type="radio" name="rating" value="3"><label for="star3">&#9733;</label><input id="star2" type="radio" name="rating" value="2"><label for="star2">&#9733;</label><input id="star1" type="radio" name="rating" value="1"><label for="star1">&#9733;</label></div><span class="field__error">Please select a star rating.</span></div>
                        <label class="field"><span class="field__label">Feedback</span><textarea class="field__control" name="comments" placeholder="Tell us about the service quality"></textarea></label>
                        <button class="btn btn--primary" type="submit">Submit Feedback</button>
                    </form>
                </c:when>
                <c:otherwise><div class="card__body"><div class="empty-state">No completed bookings are waiting for review.</div></div></c:otherwise>
            </c:choose>
        </article>
        <aside class="card">
            <div class="card__header"><h2 class="card__title">Your reviews</h2></div>
            <div class="card__body review-list">
                <c:choose>
                    <c:when test="${not empty feedbacks}">
                        <c:forEach items="${feedbacks}" var="f">
                            <div class="list-item">
                                <p class="list-item__title">${f.rating} star review for ${f.providerName}</p>
                                <p class="list-meta">${empty f.comments ? 'No written feedback provided.' : f.comments}</p>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise><div class="empty-state">Your submitted reviews will appear here.</div></c:otherwise>
                </c:choose>
            </div>
        </aside>
    </section>
</div></main></body>
</html>
