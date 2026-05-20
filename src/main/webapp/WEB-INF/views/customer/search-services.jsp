<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="activeNav" value="services"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Services - QuickFix</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css">
</head>
<body class="market-page">
<%@ include file="../includes/customer-navbar.jspf" %>
<main class="market-main">
    <div class="container">
        <section class="market-hero">
            <h1>Find the right provider</h1>
            <form class="market-search" method="get">
                <input class="field__control" name="keyword" value="${param.keyword}" placeholder="Search by service or provider">
                <button class="btn btn--primary" type="submit">Search</button>
            </form>
        </section>

        <section class="market-section market-layout">
            <aside class="card market-filter">
                <div class="card__header"><h2 class="card__title">Filters</h2></div>
                <form class="card__body form" method="get">
                    <label class="field"><span class="field__label">Category</span><select class="field__control" name="categoryId"><option value="">All categories</option><c:forEach items="${categories}" var="c"><option value="${c.categoryId}" ${param.categoryId == c.categoryId ? 'selected' : ''}>${c.categoryName}</option></c:forEach></select></label>
                    <label class="field"><span class="field__label">Price range</span><input class="field__control" name="maxPrice" type="number" value="${param.maxPrice}" placeholder="Max price"></label>
                    <label class="field"><span class="field__label">Rating</span><select class="field__control" name="minRating"><option value="">Any rating</option><option value="3.0">3.0+</option><option value="4.0">4.0+</option><option value="4.5">4.5+</option></select></label>
                    <button class="btn btn--primary" type="submit">Apply</button>
                </form>
            </aside>
            <div class="market-provider-grid">
                <c:choose>
                    <c:when test="${not empty services}">
                        <c:forEach items="${services}" var="s">
                            <article class="card service-card">
                                <div class="service-card__photo" aria-hidden="true"></div>
                                <div class="service-card__top"><span class="service-card__icon" aria-hidden="true"></span><span class="rating">${s.averageRating} rating</span></div>
                                <p class="service-card__meta">${s.categoryName}</p>
                                <h2 class="service-card__title">${s.providerName}</h2>
                                <p>${s.serviceTitle}</p>
                                <p class="list-meta">Availability: Open slots</p>
                                <div class="service-card__footer"><span class="price">Rs. ${s.price}</span><span class="table__actions"><a class="btn btn--secondary btn--sm" href="provider-details?serviceId=${s.serviceId}">View Details</a><a class="btn btn--primary btn--sm" href="book-service?serviceId=${s.serviceId}">Book Now</a></span></div>
                            </article>
                        </c:forEach>
                    </c:when>
                    <c:otherwise><div class="empty-state">No providers found.</div></c:otherwise>
                </c:choose>
            </div>
        </section>
    </div>
</main>
</body>
</html>
