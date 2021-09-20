<%--
  Created by IntelliJ IDEA.
  User: 100nout.by
  Date: 19.09.2021
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title>
        <fmt:message key="registration.title"/>
    </title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="registration">
    <fmt:message key="registration.first_name"/><input type="text" name="first_name" value="${requestScope.userMap["first_name"]}"><br>
    <fmt:message key="registration.last_name"/><input type="text" name="last_name" value="${requestScope.userMap["last_name"]}"><br>
    <fmt:message key="registration.email"/><input type="text" name="email"value="${requestScope.userMap["email"]}"><br>
    <fmt:message key="registration.password"/><input type="text" name="password"><br>
    <fmt:message key="registration.password_repeat"/><input type="text" name="password_repeat"><br>
    ${errorEmailExists}
    ${errorWrongDataMessage}
    <input type="submit" value="<fmt:message key="registration.button.signup"/>">
</form>
</body>
</html>