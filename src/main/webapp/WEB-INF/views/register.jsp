<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - QuickFix</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">
</head>
<body class="auth-page">
<main class="auth-card auth-card--wide">
    <div class="auth-card__brand">
        <a class="brand" href="${pageContext.request.contextPath}/"><img class="brand__logo" src="${pageContext.request.contextPath}/assets/images/quickfix-wordmark.png" alt="QuickFix"></a>
    </div>
    <section class="card auth-card__body">
        <div class="auth-card__header">
            <h1 class="auth-card__title">Create account</h1>
            <p class="auth-card__subtitle">Register as a customer or service provider.</p>
        </div>
        <c:if test="${not empty error}">
            <div class="alert alert--error">${error}</div>
        </c:if>
        <form class="form" method="post" action="${pageContext.request.contextPath}/register">
            <div class="field">
                <span class="field__label">Role</span>
                <div class="role-choice">
                    <input class="role-choice__input" id="roleCustomer" type="radio" name="role" value="CUSTOMER" checked>
                    <label class="role-choice__label" for="roleCustomer"><span class="role-choice__title">Customer</span><span class="role-choice__meta">Book and track home services</span></label>
                    <input class="role-choice__input" id="roleProvider" type="radio" name="role" value="PROVIDER">
                    <label class="role-choice__label" for="roleProvider"><span class="role-choice__title">Provider</span><span class="role-choice__meta">Manage requests and services</span></label>
                </div>
            </div>
            <div class="form__grid">
                <label class="field"><span class="field__label">Full name</span><input class="field__control" name="fullName" value="${fullName != null ? fullName : ''}" required><span class="field__error">${fullNameError}</span></label>
                <label class="field"><span class="field__label">Phone</span><input class="field__control" name="phone" value="${phone != null ? phone : ''}"><span class="field__error">${phoneError}</span></label>
                <label class="field"><span class="field__label">Email</span><input class="field__control" type="email" name="email" value="${email != null ? email : ''}" required><span class="field__error">${emailError}</span></label>
                <label class="field"><span class="field__label">Password</span><input class="field__control" type="password" name="password" required><span class="field__error">${passwordError}</span></label>
            </div>
            <div class="form__grid form__grid--single">
                <label class="field"><span class="field__label">Address</span><textarea class="field__control" name="address" placeholder="Street, city, state, postal code"></textarea><span class="field__hint">Used for customer booking locations and provider service coverage.</span></label>
            </div>
            <button class="btn btn--primary" type="submit">Create account</button>
        </form>
        <p class="auth-card__footer">Already registered? <a href="${pageContext.request.contextPath}/login">Sign in</a></p>
    </section>
</main>
<script src="${pageContext.request.contextPath}/assets/js/main.js" defer></script>
</body>
</html>
