<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuickFix Home Services System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css">
</head>
<body>
<header class="site-header">
    <div class="container site-header__inner">
        <a class="brand" href="${pageContext.request.contextPath}/">
            <img class="brand__logo" src="${pageContext.request.contextPath}/assets/images/quickfix-wordmark.png" alt="QuickFix">
        </a>
        <nav class="site-nav" aria-label="Primary">
            <a href="#services">Services</a>
            <a href="#how">How it works</a>
            <a href="${pageContext.request.contextPath}/coverage">Coverage</a>
        </nav>
        <div class="site-actions">
            <a class="btn btn--secondary btn--sm" href="${pageContext.request.contextPath}/login">Login</a>
            <a class="btn btn--primary btn--sm" href="${pageContext.request.contextPath}/register">Register</a>
        </div>
    </div>
</header>

<main>
    <section class="hero">
        <div class="container hero__grid">
            <div>
                <div class="hero__label">Trusted home services near you</div>
                <h1 class="hero__title">Book Trusted Home Services Near You</h1>
                <p class="hero__copy">Find verified plumbers, electricians, carpenters, and cleaners. Book, track, and review every service request in one simple system.</p>
                <form class="search-row" action="${pageContext.request.contextPath}/services#recommended" method="get">
                    <input class="field__control" name="keyword" value="${param.keyword}" placeholder="Search plumbing, cleaning, electrical">
                    <button class="btn btn--primary" type="submit">Search</button>
                </form>
                <div class="hero__actions">
                    <a class="btn btn--primary" href="${pageContext.request.contextPath}/register">Book a Service</a>
                    <a class="btn btn--secondary" href="${pageContext.request.contextPath}/login">Provider Login</a>
                </div>
            </div>
            <aside class="hero-panel" aria-label="Recent service requests">
                <div class="hero-panel__header">
                    <h2 class="card__title">Today on QuickFix</h2>
                    <p class="list-meta">Recent booking activity</p>
                </div>
                <div class="hero-panel__body">
                    <div class="hero-job"><span><strong class="hero-job__title">Pipe leak repair</strong><span class="hero-job__meta">Waiting for a provider</span></span><span class="badge badge--pending">Pending</span></div>
                    <div class="hero-job"><span><strong class="hero-job__title">Switchboard inspection</strong><span class="hero-job__meta">Scheduled for 2:30 PM</span></span><span class="badge badge--accepted">Accepted</span></div>
                    <div class="hero-job"><span><strong class="hero-job__title">Apartment cleaning</strong><span class="hero-job__meta">Customer review requested</span></span><span class="badge badge--completed">Completed</span></div>
                </div>
            </aside>
        </div>
    </section>

    <section class="section" id="services">
        <div class="container">
            <div class="section__header">
                <h2>Service categories</h2>
                <p>Simple categories help customers compare providers and help administrators track platform coverage.</p>
            </div>
            <div class="category-grid">
                <c:choose>
                    <c:when test="${not empty categories}">
                        <c:forEach items="${categories}" var="c">
                            <a class="card category-card category-card--photo" href="${pageContext.request.contextPath}/services?categoryId=${c.categoryId}#recommended"><span class="category-card__icon" aria-hidden="true"></span><h3>${c.categoryName}</h3><p>${c.description}</p></a>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <article class="card category-card category-card--photo category-card--plumbing"><span class="category-card__icon" aria-hidden="true"></span><h3>Plumbing</h3><p>Leaks, fittings, drainage, and fixture repairs.</p></article>
                        <article class="card category-card category-card--photo category-card--electrical"><span class="category-card__icon" aria-hidden="true"></span><h3>Electrical</h3><p>Wiring, switches, lighting, and safety checks.</p></article>
                        <article class="card category-card category-card--photo category-card--cleaning"><span class="category-card__icon" aria-hidden="true"></span><h3>Cleaning</h3><p>Home cleaning, deep cleaning, and move-in support.</p></article>
                        <article class="card category-card category-card--photo category-card--carpentry"><span class="category-card__icon" aria-hidden="true"></span><h3>Carpentry</h3><p>Furniture fixes, doors, shelves, and custom work.</p></article>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </section>

    <section class="section" id="recommended">
        <div class="container">
            <div class="section__header">
                <h2>Recommended providers</h2>
                <p>Live provider services from the QuickFix backend.</p>
            </div>
            <c:if test="${not empty homeDataError}">
                <div class="empty-state">${homeDataError}</div>
            </c:if>
            <div class="market-provider-grid">
                <c:forEach items="${recommendedServices}" var="s">
                    <article class="card service-card">
                        <div class="service-card__photo" aria-hidden="true"></div>
                        <div class="service-card__top"><span class="service-card__icon" aria-hidden="true"></span><span class="rating">${s.averageRating} rating</span></div>
                        <p class="service-card__meta">${s.categoryName}</p>
                        <h3>${s.providerName}</h3>
                        <p>${s.serviceTitle}</p>
                        <div class="service-card__footer"><span class="price">Rs. ${s.price}</span><a class="btn btn--primary btn--sm" href="${pageContext.request.contextPath}/login">Login to Book</a></div>
                    </article>
                </c:forEach>
                <c:if test="${empty recommendedServices}">
                    <div class="empty-state">No matching providers are available yet. Try another service or check back after providers are approved.</div>
                </c:if>
            </div>
        </div>
    </section>

    <section class="section section--surface" id="how">
        <div class="container">
            <div class="section__header">
                <h2>How it works</h2>
                <p>A predictable booking flow from search to completion.</p>
            </div>
            <div class="steps">
                <article class="card step-card"><span class="step-card__number">1</span><h3>Search and compare</h3><p>Customers filter by category, price, rating, and provider details.</p></article>
                <article class="card step-card"><span class="step-card__number">2</span><h3>Book a time</h3><p>Providers review requests and update booking status from their dashboard.</p></article>
                <article class="card step-card"><span class="step-card__number">3</span><h3>Review and report</h3><p>Feedback, complaints, and admin reports close the operational loop.</p></article>
            </div>
        </div>
    </section>

    <section class="section">
        <div class="container">
            <div class="section__header">
                <h2>Why customers trust QuickFix</h2>
                <p>Every booking includes simple safeguards so customers and providers know what to expect.</p>
            </div>
            <div class="assurance-grid">
                <article class="card assurance-card"><h3>Verified providers</h3><p>Provider accounts can be reviewed by admins before they appear in customer search results.</p></article>
                <article class="card assurance-card"><h3>Transparent pricing</h3><p>Customers see service prices before booking, with provider details available before confirmation.</p></article>
                <article class="card assurance-card"><h3>Status tracking</h3><p>Bookings move through clear states: pending, accepted, in progress, completed, cancelled, or rejected.</p></article>
                <article class="card assurance-card"><h3>Feedback loop</h3><p>Ratings, complaints, and admin reports help keep service quality visible and accountable.</p></article>
            </div>
        </div>
    </section>

    <section class="section section--surface">
        <div class="container">
            <div class="trust-band">
                <div>
                    <p class="hero__label">Built for daily home service work</p>
                    <h2>One place for customers, providers, and administrators</h2>
                    <p>QuickFix keeps the booking flow organized from the first search to the final review. Customers can compare services, providers can manage requests, and admins can monitor platform activity.</p>
                </div>
                <div class="trust-stats" aria-label="Platform highlights">
                    <div><strong>13</strong><span>core data modules</span></div>
                    <div><strong>6</strong><span>booking statuses</span></div>
                    <div><strong>3</strong><span>workspaces</span></div>
                </div>
            </div>
        </div>
    </section>

    <section class="section">
        <div class="container">
            <div class="section__header">
                <h2>What people say</h2>
                <p>Sample customer experiences based on common QuickFix service journeys.</p>
            </div>
            <div class="review-grid">
                <article class="card review-card"><p>“I could compare the plumber, price, and timing before booking. The request status made it easy to know what was happening.”</p><strong>Rina S.</strong><span>Kitchen plumbing repair</span></article>
                <article class="card review-card"><p>“The provider dashboard makes requests clear. I can update customers without needing separate calls for every small change.”</p><strong>Prakash M.</strong><span>Electrical provider</span></article>
                <article class="card review-card"><p>“The admin view helps me see users, providers, complaints, and bookings in one place. It feels controlled.”</p><strong>Admin team</strong><span>Operations review</span></article>
            </div>
        </div>
    </section>

    <section class="section section--surface">
        <div class="container">
            <div class="section__header">
                <h2>Ready for common home repairs</h2>
                <p>Use QuickFix for planned maintenance, urgent repairs, and routine household work.</p>
            </div>
            <div class="service-strip">
                <span>Leak repair</span>
                <span>Switchboard checks</span>
                <span>Door and shelf work</span>
                <span>Deep cleaning</span>
                <span>Appliance support</span>
                <span>Bathroom fixtures</span>
            </div>
        </div>
    </section>
</main>

<footer class="site-footer" id="coverage">
    <div class="container site-footer__grid">
        <div>
            <a class="brand" href="${pageContext.request.contextPath}/"><img class="brand__logo" src="${pageContext.request.contextPath}/assets/images/quickfix-wordmark.png" alt="QuickFix"></a>
            <p>Home services management for customers, providers, and administrators.</p>
        </div>
        <div class="site-footer__links"><strong>Product</strong><a href="#services">Services</a><a href="#how">Workflow</a></div>
        <div class="site-footer__links"><strong>Access</strong><a href="${pageContext.request.contextPath}/login">Login</a><a href="${pageContext.request.contextPath}/register">Register</a></div>
        <div class="site-footer__links"><strong>Support</strong><a href="${pageContext.request.contextPath}/coverage">Coverage</a><a href="${pageContext.request.contextPath}/help-desk">Help desk</a></div>
    </div>
</footer>
<script src="${pageContext.request.contextPath}/assets/js/main.js" defer></script>
</body>
</html>
