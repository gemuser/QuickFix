<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="activeNav" value="home"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuickFix Customer Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css">
</head>
<body class="market-page">
<%@ include file="../includes/customer-navbar.jspf" %>
<main class="market-main">
    <div class="container">
        <section class="market-hero">
            <h1 class="market-hero__title">Book Trusted Home Services Near You</h1>
            <p class="market-hero__copy">Find reliable plumbers, electricians, carpenters, and cleaners. Compare providers and book in a clean, hassle-free flow.</p>
            <form class="market-search" action="search-services" method="get">
                <input class="field__control" name="keyword" placeholder="Search plumbing, electrical, cleaning">
                <button class="btn btn--primary" type="submit">Search</button>
            </form>
        </section>

        <section class="market-section">
            <div class="market-section__header"><div><h2>Service categories</h2><p>Choose the type of help you need.</p></div></div>
            <div class="market-grid">
                <a class="card category-card category-card--photo category-card--plumbing" href="search-services"><span class="category-card__icon" aria-hidden="true"></span><h3>Plumbing</h3><p>Leaks, fittings, and urgent repairs.</p></a>
                <a class="card category-card category-card--photo category-card--electrical" href="search-services"><span class="category-card__icon" aria-hidden="true"></span><h3>Electrical</h3><p>Wiring, switches, and inspections.</p></a>
                <a class="card category-card category-card--photo category-card--carpentry" href="search-services"><span class="category-card__icon" aria-hidden="true"></span><h3>Carpentry</h3><p>Doors, shelves, and furniture work.</p></a>
                <a class="card category-card category-card--photo category-card--cleaning" href="search-services"><span class="category-card__icon" aria-hidden="true"></span><h3>Cleaning</h3><p>Routine and deep home cleaning.</p></a>
            </div>
        </section>

        <section class="market-section">
            <div class="market-section__header"><div><h2>Recommended providers</h2><p>Popular services based on recent bookings.</p></div><a href="search-services">View all</a></div>
            <div class="market-provider-grid">
                <article class="card service-card"><div class="service-card__photo service-card__photo--plumbing" aria-hidden="true"></div><div class="service-card__top"><span class="service-card__icon" aria-hidden="true"></span><span class="rating">4.8 rating</span></div><h3>Home plumbing repair</h3><p class="list-meta">Verified provider - Available today</p><div class="service-card__footer"><span class="price">Rs. 1200</span><a class="btn btn--primary btn--sm" href="search-services">Book Now</a></div></article>
                <article class="card service-card"><div class="service-card__photo service-card__photo--electrical" aria-hidden="true"></div><div class="service-card__top"><span class="service-card__icon" aria-hidden="true"></span><span class="rating">4.7 rating</span></div><h3>Electrical maintenance</h3><p class="list-meta">Certified electrician - Available tomorrow</p><div class="service-card__footer"><span class="price">Rs. 1500</span><a class="btn btn--primary btn--sm" href="search-services">Book Now</a></div></article>
            </div>
        </section>

        <section class="market-section">
            <div class="market-section__header"><div><h2>Recent bookings</h2><p>Quick view of your latest service activity.</p></div><a href="booking-history">My Bookings</a></div>
            <div class="booking-list">
                <article class="card booking-card"><div><h3>Kitchen plumbing</h3><p class="list-meta">Pending provider response</p></div><span class="badge badge--pending">Pending</span></article>
                <article class="card booking-card"><div><h3>Apartment cleaning</h3><p class="list-meta">Completed last week</p></div><span class="badge badge--completed">Completed</span></article>
            </div>
        </section>
    </div>
</main>
<footer class="site-footer"><div class="container">QuickFix Home Services System</div></footer>
</body>
</html>
