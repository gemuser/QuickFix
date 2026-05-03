<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="navBase" value="provider"/><c:set var="navRole" value="Provider"/><c:set var="activeNav" value="profile"/><c:set var="pageTitle" value="Manage Profile"/><c:set var="pageSubtitle" value="Maintain the public provider profile customers see."/>
<!doctype html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Manage Profile - QuickFix</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/forms.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css"></head>
<body><div class="app-shell"><%@ include file="../includes/sidebar.jspf" %><main><%@ include file="../includes/topbar.jspf" %><section class="page">
    <div class="split">
        <section class="card"><div class="card__header"><h1 class="card__title">Profile information</h1></div><form class="card__body form" method="post"><input type="hidden" name="action" value="profile"><label class="field"><span class="field__label">Bio</span><textarea class="field__control" name="bio">${profile.bio}</textarea></label><label class="field"><span class="field__label">Experience years</span><input class="field__control" type="number" name="experienceYears" value="${profile.experienceYears}"></label><button class="btn btn--primary" type="submit">Save profile</button></form></section>
        <aside class="card card__body"><h2>Profile status</h2><dl class="summary-list"><div class="summary-list__row"><dt>Verification</dt><dd><span class="badge badge--${fn:toLowerCase(profile.verificationStatus)}">${profile.verificationStatus}</span></dd></div><div class="summary-list__row"><dt>Average rating</dt><dd>${profile.averageRating}</dd></div></dl></aside>
    </div>
</section></main></div></body>
</html>
