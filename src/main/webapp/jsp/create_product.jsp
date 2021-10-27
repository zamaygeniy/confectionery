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
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="create_product">
            <fmt:message key="create_product.name"/><input class="input_middle" type="text" name="name"
                                                               value="${requestScope.productMap["name"]}"><br>
            <fmt:message key="create_product.price"/><input class="input_middle" type="text" name="price"
                                                              value="${requestScope.productMap["price"]}"><br>
            <fmt:message key="create_product.description"/><input class="input_middle" type="text" name="description"
                                                          value="${requestScope.productMap["description"]}"><br>
            <fmt:message key="create_product.weight"/><input class="input_middle" type="text" name="weight"
                                                                  value="${requestScope.productMap["weight"]}"><br>
            <fmt:message key="create_product.product_type"/><input class="input_middle" type="text" name="product_type_id"
                                                             value="${requestScope.productMap["product_type_id"]}"><br>
            <span class="fail-message">${errorWrongDataMessage}</span>
            <button type="submit" class="simple-btn"><fmt:message key="registration.button.signup"/></button>
        </form>
    </div>
</main>
<c:import url="fragment/footer.jsp"/>
</body>
</html>