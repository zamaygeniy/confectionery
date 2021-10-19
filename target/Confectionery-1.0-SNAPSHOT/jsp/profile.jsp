<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title>
        <fmt:message key="profile.title"/>
    </title>
</head>
<body>

<c:import url="fragment/header1.jsp"/>
<main>
<h3>${sessionScope.user.firstName} ${sessionScope.user.lastName}</h3><br>
<fmt:message key="profile.email"/> ${sessionScope.user.email}<br>
    <div class="img"><img src='data:image/jpg;base64,${sessionScope.user.image}'></div>
</main>
<c:import url="fragment/footer.jsp"/>
</body>
</html>