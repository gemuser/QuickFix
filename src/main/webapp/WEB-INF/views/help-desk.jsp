<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Help Desk - QuickFix</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css">
</head>
<body>
<header class="site-header">
    <div class="container site-header__inner">
        <a class="brand" href="${pageContext.request.contextPath}/"><img class="brand__logo" src="${pageContext.request.contextPath}/assets/images/quickfix-wordmark.png" alt="QuickFix"></a>
        <nav class="site-nav" aria-label="Primary"><a href="${pageContext.request.contextPath}/#services">Services</a><a href="${pageContext.request.contextPath}/#how">How it works</a><a href="${pageContext.request.contextPath}/coverage">Coverage</a></nav>
        <div class="site-actions"><a class="btn btn--secondary btn--sm" href="${pageContext.request.contextPath}/login">Login</a><a class="btn btn--primary btn--sm" href="${pageContext.request.contextPath}/register">Register</a></div>
    </div>
</header>
<main>
    <section class="page-hero"><div class="container"><p class="hero__label">Help desk</p><h1>Get help with bookings, provider accounts, and service issues</h1><p>QuickFix keeps support simple: customers can track bookings and submit feedback, providers can manage requests, and admins can review complaints.</p></div></section>
    <section class="section"><div class="container help-grid"><article class="card help-card"><h2>For customers</h2><ul class="clean-list"><li>Search services by category, price, or rating.</li><li>Review provider details before booking.</li><li>Track booking status from your dashboard.</li><li>Submit ratings or feedback after service completion.</li></ul><a class="btn btn--primary" href="${pageContext.request.contextPath}/register">Create customer account</a></article><article class="card help-card"><h2>For providers</h2><ul class="clean-list"><li>Keep your provider profile updated.</li><li>Add services with clear titles, descriptions, and pricing.</li><li>Publish availability slots for customers.</li><li>Accept, reject, or complete booking requests.</li></ul><a class="btn btn--secondary" href="${pageContext.request.contextPath}/login">Provider login</a></article><article class="card help-card"><h2>For admins</h2><ul class="clean-list"><li>Approve or reject provider verification.</li><li>Monitor users, bookings, complaints, and reports.</li><li>Maintain service categories.</li><li>Respond to complaint records from the admin workspace.</li></ul><a class="btn btn--secondary" href="${pageContext.request.contextPath}/login">Admin login</a></article></div></section>
    <section class="section section--surface"><div class="container"><div class="section__header"><h2>Common questions</h2><p>Quick answers for the most common QuickFix workflows.</p></div><div class="faq-list"><article class="card faq-item"><h3>Why can't a provider log in?</h3><p>Provider accounts may be pending admin approval. Once approved, the account becomes active and can access the provider dashboard.</p></article><article class="card faq-item"><h3>How do customers cancel a booking?</h3><p>Customers can cancel pending or accepted bookings from the booking history screen.</p></article><article class="card faq-item"><h3>Where do complaints go?</h3><p>Complaint records are available in the admin workspace, where admins can update the status and response.</p></article><article class="card faq-item"><h3>How are ratings used?</h3><p>Ratings contribute to provider averages and help customers compare services in search results.</p></article></div></div></section>
</main>
<footer class="site-footer"><div class="container site-footer__grid"><div><a class="brand" href="${pageContext.request.contextPath}/"><img class="brand__logo" src="${pageContext.request.contextPath}/assets/images/quickfix-wordmark.png" alt="QuickFix"></a><p>Home services management for customers, providers, and administrators.</p></div><div class="site-footer__links"><strong>Product</strong><a href="${pageContext.request.contextPath}/#services">Services</a><a href="${pageContext.request.contextPath}/#how">Workflow</a></div><div class="site-footer__links"><strong>Access</strong><a href="${pageContext.request.contextPath}/login">Login</a><a href="${pageContext.request.contextPath}/register">Register</a></div><div class="site-footer__links"><strong>Support</strong><a href="${pageContext.request.contextPath}/coverage">Coverage</a><a href="${pageContext.request.contextPath}/help-desk">Help desk</a></div></div></footer>
<script src="${pageContext.request.contextPath}/assets/js/main.js" defer></script>
</body>
</html>
