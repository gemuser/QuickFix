<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - QuickFix</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">
</head>
<body class="auth-page">
<main class="auth-card">
    <div class="auth-card__brand">
        <a class="brand" href="${pageContext.request.contextPath}/"><img class="brand__logo" src="${pageContext.request.contextPath}/assets/images/quickfix-wordmark.png" alt="QuickFix"></a>
    </div>
    <section class="card auth-card__body">
        <div class="auth-card__header">
            <h1 class="auth-card__title">Sign in</h1>
            <p class="auth-card__subtitle">Access your QuickFix workspace.</p>
        </div>
        <c:if test="${not empty error}">
            <div class="alert alert--error">${error}</div>
        </c:if>
        <c:if test="${param.registered == '1'}">
            <div class="alert alert--success">Account created successfully.</div>
        </c:if>
        <form class="form" method="post" action="${pageContext.request.contextPath}/login">
            <label class="field">
                <span class="field__label">Email address</span>
                <input class="field__control" type="email" name="email" value="${email != null ? email : ''}" placeholder="name@example.com" required>
                <span class="field__error">${emailError}</span>
            </label>
            <label class="field">
                <span class="field__label">Password</span>
                <input class="field__control" type="password" name="password" placeholder="Enter your password" required>
                <span class="field__error">${passwordError}</span>
            </label>
            <button class="btn btn--primary" type="submit">Login</button>
        </form>
        <p class="auth-card__footer">New to QuickFix? <a href="${pageContext.request.contextPath}/register">Create an account</a></p>
    </section>
</main>
<script src="${pageContext.request.contextPath}/assets/js/main.js" defer></script>
</body>
</html>
