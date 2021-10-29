<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>

<html>
<c:import url="fragment/header.jsp"/>
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
            <h1 class="page_titie"><fmt:message key="cart.cart"/> </h1>
            <div class="cart_main_part">
                <div class="left_part">
                </div>
                <div class="right_part">
                    <div class="cart_short">
                        <div class="short_footer">
                            <div class="all" id="all">
                                <div class="name"><fmt:message key="cart.intotal"/> </div>
                                <div class="value total_cart_value"></div>
                            </div>
                            <form action="${pageContext.request.contextPath}/controller?command=checkout_command"
                                  id="make_order" method="post">
                                <input type="hidden" name="cart" id="make_order_input" type="text"/>
                                <input type="hidden" name="cost" id="total_cost_input" type="text"/>
                                <c:if test="${sessionScope.user.role == 'GUEST'}"><fmt:message key="cart.signin.message"/></c:if>
                                <c:if test="${sessionScope.user.role == 'USER' || sessionScope.user.role == 'ADMIN'}">
                                    <div><fmt:message key="cart.phone"/></div>
                                    <br>
                                    <input type="text" required
                                           pattern="/^(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?$/"
                                           maxlength="15"
                                           name="phone">
                                    <div class="make_order _blue_btn" id="short_btn">
                                        <fmt:message key="cart.checkout"/>
                                    </div>
                                </c:if>
                            </form>
                            <button id="clear_cart"><fmt:message key="cart.clear"/></button>
                        </div>
                    </div>
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
