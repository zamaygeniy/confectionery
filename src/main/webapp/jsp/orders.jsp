<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>

<html>
<c:import url="fragment/header1.jsp"/>
<head>
    <meta charset="UTF-8">
    <title>one plus</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/musesans/stylesheet.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/scroll.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ui_kit.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">

</head>


<body>


<main class="main _cart">
    <div class="wrapper">
        <div class="cart_container">
            <h1 class="page_titie">Заказы</h1>
            <div class="cart_main_part">
                <div class="left_part">
                    <c:forEach var="order" items="${ordersMap}">
                        <div class="product_item">
                            <div class="desc_part">
                                <a href="#" class="name">${order.key.phone}</a>
                                <div class="description">${order.key.userId}</div>
                                <div class="weight">${order.key.status} г.</div>
                                <div class="cost">${order.key.date} р.</div>
                            </div>
                            <c:forEach var="product" items="${order.value}">
                                Key = ${product.key}, value = ${product.value}<br>
                            </c:forEach>


                        </div>
                    </c:forEach>
                </div>

            </div>
        </div>
    </div>
</main>


<div class="black_href"></div>


<c:import url="fragment/footer.jsp"/>
<script src="${pageContext.request.contextPath}/scripts/cart.js"></script>
</body>

</html>
