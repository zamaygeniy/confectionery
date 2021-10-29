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
            <input type="hidden" name="command" value="create_product">
            <fmt:message key="create_product.name"/><input class="input_middle" type="text" name="name"
                                                           value="${requestScope.productMap["name"]}"><br>
            <fmt:message key="create_product.price"/><input class="input_middle" type="text" required pattern="^[\d.]+$" name="price"
                                                            value="${requestScope.productMap["price"]}"><br>
            <fmt:message key="create_product.description"/><input class="input_middle" type="text" name="description"
                                                                  value="${requestScope.productMap["description"]}"><br>
            <fmt:message key="create_product.weight"/><input class="input_middle" type="number" name="weight"
                                                             value="${requestScope.productMap["weight"]}"><br>
            <fmt:message key="create_product.product_type"/><input class="input_middle" type="number"
                                                                   name="product_type_id"
                                                                   value="${requestScope.productMap["product_type_id"]}"><br>
            <fmt:message key="create_product.image"/><input class="input_middle" type="file" name="file" autocomplete="off"
                                                            value="${requestScope.productMap["image"]}"><br>
            <span class="fail-message">${errorWrongDataMessage}</span>
            <button type="submit" class="simple-btn"><fmt:message key="button.confirm"/></button>
        </form>
    </div>
</main>
<c:import url="fragment/footer.jsp"/>
</body>
</html>