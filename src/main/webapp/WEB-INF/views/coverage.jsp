<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Coverage - QuickFix</title>
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
    <section class="page-hero"><div class="container"><p class="hero__label">Service coverage</p><h1>Home service coverage that grows with your providers</h1><p>QuickFix organizes service requests by category, provider availability, and customer location details. Admins can approve providers and keep local coverage dependable.</p></div></section>
    <section class="section"><div class="container coverage-layout"><article class="card coverage-map"><h2>Current focus areas</h2><p>Use QuickFix for local home-service operations across nearby city neighborhoods, apartment blocks, and residential areas.</p><div class="coverage-zones"><span>Central neighborhoods</span><span>Residential colonies</span><span>Apartment buildings</span><span>Local market areas</span><span>Provider-managed zones</span></div></article><aside class="card coverage-note"><h2>How coverage works</h2><ul class="clean-list"><li>Customers enter address details during booking.</li><li>Providers manage services and availability from their dashboard.</li><li>Admins approve providers before they appear to customers.</li><li>Categories help customers find the right professional quickly.</li></ul></aside></div></section>
    <section class="section section--surface"><div class="container"><div class="section__header"><h2>Services available by category</h2><p>Coverage depends on approved providers in each category.</p></div><div class="assurance-grid"><article class="card assurance-card"><h3>Plumbing</h3><p>Leaks, pipe fittings, faucets, bathroom fixtures, and drainage issues.</p></article><article class="card assurance-card"><h3>Electrical</h3><p>Switchboards, light fixtures, appliance checks, and wiring support.</p></article><article class="card assurance-card"><h3>Carpentry</h3><p>Doors, shelves, cabinets, furniture repair, and small woodwork tasks.</p></article><article class="card assurance-card"><h3>Cleaning</h3><p>Routine cleaning, deep cleaning, kitchen, bathroom, and move-in support.</p></article></div></div></section>
</main>
<footer class="site-footer"><div class="container site-footer__grid"><div><a class="brand" href="${pageContext.request.contextPath}/"><img class="brand__logo" src="${pageContext.request.contextPath}/assets/images/quickfix-wordmark.png" alt="QuickFix"></a><p>Home services management for customers, providers, and administrators.</p></div><div class="site-footer__links"><strong>Product</strong><a href="${pageContext.request.contextPath}/#services">Services</a><a href="${pageContext.request.contextPath}/#how">Workflow</a></div><div class="site-footer__links"><strong>Access</strong><a href="${pageContext.request.contextPath}/login">Login</a><a href="${pageContext.request.contextPath}/register">Register</a></div><div class="site-footer__links"><strong>Support</strong><a href="${pageContext.request.contextPath}/coverage">Coverage</a><a href="${pageContext.request.contextPath}/help-desk">Help desk</a></div></div></footer>
<script src="${pageContext.request.contextPath}/assets/js/main.js" defer></script>
</body>
</html>
