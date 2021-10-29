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
<c:import url="fragment/header1.jsp"/>
<main class="main">
    <div class="form_container">
        <form action="${pageContext.request.contextPath}/controller" method="post" enctype="multipart/form-data">
            <input type="hidden" name="command" value="registration">
            <fmt:message key="registration.first_name"/><input class="input_middle" type="text" required pattern="(?=^.{1,32}$)^([A-zА-яЁё`'.-])+$" name="first_name"
                                                               value="${requestScope.userMap["first_name"]}"><br>
            <fmt:message key="registration.last_name"/><input class="input_middle" type="text" required pattern="(?=^.{1,32}$)^([A-zА-яЁё`'.-])+$" name="last_name"
                                                              value="${requestScope.userMap["last_name"]}"><br>
            <fmt:message key="registration.email"/><input class="input_middle" type="text" name="email"
                                                          value="${requestScope.userMap["email"]}"><br>
            <fmt:message key="registration.password"/><input class="input_middle" type="text" required pattern="^\w{6,20}$" name="password"><br>
            <fmt:message key="registration.password_repeat"/><input class="input_middle" type="text" required pattern="^\w{6,20}$"
                                                                    name="password_repeat"><br>
            <fmt:message key="create_product.image"/><input class="input_middle" type="file" name="file"
                                                            value="${requestScope.userMap["image"]}"><br>
            <span class="fail-message">${errorEmailExistsMessage}</span>
            <span class="fail-message">${errorWrongDataMessage}</span>
            <button type="submit" class="simple-btn"><fmt:message key="registration.button.signup"/></button>
        </form>
    </div>
</main>
<c:import url="fragment/footer.jsp"/>
</body>
</html>