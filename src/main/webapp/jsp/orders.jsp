<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>

<html>
<c:import url="fragment/header.jsp"/>
<head>
    <meta charset="UTF-8">
    <title>Makima</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/musesans/stylesheet.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/scroll.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ui_kit.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">

</head>


<body>


<main class="main _cart">
    <div class="wrapper">
        <div class="catalog_header">
            <div class="wrapper">
                <div class="upper">
                    <div class="page_title"><fmt:message key="catalog.search"/></div>
                </div>
            </div>
        </div>
        <div class="filter_part">
            <div class="filter_item">
                <div class="filter_header">
                    <div class="name"><fmt:message key="order.status"/> </div>
                    <div class="arrow">
                        <svg xmlns="http://www.w3.org/2000/svg" width="12" height="8" viewBox="0 0 12 8">
                            <path fill="#CDCDCD" fill-rule="nonzero"
                                  d="M6 4.415L10.5 0 12 1.714 6 8 0 1.714 1.5 0z"/>
                        </svg>
                    </div>
                </div>

                <div class="filter_body">
                    <form action="${pageContext.request.contextPath}/controller">
                        <c:if test="${sessionScope.user.role == 'ADMIN'}">
                            <input type="hidden" name="command" value="search_orders">
                        </c:if>
                        <c:if test="${sessionScope.user.role == 'USER'}">
                            <input type="hidden" name="command" value="user_orders">
                        </c:if>
                        <div class="check_large">
                            <input type="checkbox" id="WAITING_FOR_CONFIRMATION" name="order_status_id"
                                   value="1">
                            <label for="WAITING_FOR_CONFIRMATION"><fmt:message key="order.waiting"/></label>
                        </div>
                        <div class="check_large">
                            <input type="checkbox" id="IN_PROCESS" name="order_status_id"
                                   value="2">
                            <label for="IN_PROCESS"><fmt:message key="order.in_process"/></label>
                        </div>
                        <div class="check_large">
                            <input type="checkbox" id="DONE" name="order_status_id"
                                   value="3">
                            <label for="DONE"><fmt:message key="order.done"/></label>
                        </div>
                        <div class="check_large">
                            <input type="checkbox" id="CANCELLED" name="order_status_id"
                                   value="4">
                            <label for="CANCELLED"><fmt:message key="order.cancelled"/></label>
                        </div>
                        <button type="submit"><fmt:message key="catalog.search"/></button>
                    </form>
                </div>


            </div>
        </div>

        <div class="cart_container orders_products">
            <h1 class="page_titie"><fmt:message key="orders.orders"/></h1>
            <span class="fail-message">${errorNoOrdersFound}</span>
            <div class="cart_main_part">
                <div class="left_part">
                    <c:forEach var="order" items="${ordersMap}">
                        <div class="order_item">
                            <div class="desc_part">
                                <div>??????????????: ${order.key.phone}</div>
                                <c:if test="${sessionScope.user.role == 'ADMIN'}">
                                    <a href="${pageContext.request.contextPath}/controller?command=search_users&id=${order.key.userId}">????????????????????????: ${order.key.userId}</a>
                                </c:if>
                                <div>????????????: ${order.key.status}</div>
                                <div>????????: ${order.key.date}</div>
                            </div>
                            <div class="order_products">
                                <c:forEach var="product" items="${order.value}">
                                    <div class="product">
                                        <div class="field name"><span>????????????????</span> <span>${product.key.name}</span>
                                        </div>
                                        <div class="field price"><span>????????</span> <span>${product.key.price}</span>
                                        </div>
                                        <div class="field weight"><span>??????????</span> <span>${product.key.weight}</span>
                                        </div>
                                        <div class="field amount"><span>????????????????????</span> <span>${product.value}</span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                                <div class="controls">
                                    <a href="${pageContext.request.contextPath}/controller?command=accept_order&order_id=${order.key.id}">
                                        <button class="accept"><fmt:message key="order.accept"/></button>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/controller?command=reject_order&order_id=${order.key.id}">
                                        <button class="reject"><fmt:message key="order.cancel"/></button>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/controller?command=done_order&order_id=${order.key.id}">
                                        <button class="done"><fmt:message key="order.done"/></button>
                                    </a>
                                </div>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>

            </div>
        </div>
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <div class="pagination">
                <div class="pag_body">
                    <c:if test="${currentPage != 1}">
                        <a href="${pageContext.request.contextPath}/controller?command=orders_page&page=${currentPage - 1}">
                            <div class="prev">
                                <svg xmlns="http://www.w3.org/2000/svg" width="7" height="12"
                                     viewBox="0 0 7 12">
                                    <path fill="#CDCDCD" fill-rule="nonzero"
                                          d="M3.137 6L7 10.5 5.5 12 0 6l5.5-6L7 1.5z"/>
                                </svg>
                            </div>
                        </a>
                    </c:if>

                    <c:forEach begin="1" end="${numberOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <div class="page_number _active">${i}</div>
                            </c:when>
                            <c:when test="${i > (currentPage - 3) && i < currentPage + 3}">
                                <div class="page_number">
                                    <a href="${pageContext.request.contextPath}/controller?command=orders_page&page=${i}">
                                            ${i}
                                    </a>
                                </div>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${currentPage lt numberOfPages}">
                        <a href="${pageContext.request.contextPath}/controller?command=orders_page&page=${currentPage + 1}">
                            <div class="next">
                                <svg xmlns="http://www.w3.org/2000/svg" width="7" height="12"
                                     viewBox="0 0 7 12">
                                    <path fill="#CDCDCD" fill-rule="nonzero"
                                          d="M3.863 6L0 1.5 1.5 0 7 6l-5.5 6L0 10.5z"/>
                                </svg>
                            </div>
                        </a>
                    </c:if>
                </div>
            </div>
        </c:if>
        <c:if test="${sessionScope.user.role == 'USER'}">
            <div class="pagination">
                <div class="pag_body">
                    <c:if test="${currentPage != 1}">
                        <a href="${pageContext.request.contextPath}/controller?command=user_orders&page=${currentPage - 1}">
                            <div class="prev">
                                <svg xmlns="http://www.w3.org/2000/svg" width="7" height="12"
                                     viewBox="0 0 7 12">
                                    <path fill="#CDCDCD" fill-rule="nonzero"
                                          d="M3.137 6L7 10.5 5.5 12 0 6l5.5-6L7 1.5z"/>
                                </svg>
                            </div>
                        </a>
                    </c:if>

                    <c:forEach begin="1" end="${numberOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <div class="page_number _active">${i}</div>
                            </c:when>
                            <c:when test="${i > (currentPage - 3) && i < currentPage + 3}">
                                <div class="page_number">
                                    <a href="${pageContext.request.contextPath}/controller?command=user_orders&page=${i}">
                                            ${i}
                                    </a>
                                </div>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${currentPage lt numberOfPages}">
                        <a href="${pageContext.request.contextPath}/controller?command=user_orders&page=${currentPage + 1}">
                            <div class="next">
                                <svg xmlns="http://www.w3.org/2000/svg" width="7" height="12"
                                     viewBox="0 0 7 12">
                                    <path fill="#CDCDCD" fill-rule="nonzero"
                                          d="M3.863 6L0 1.5 1.5 0 7 6l-5.5 6L0 10.5z"/>
                                </svg>
                            </div>
                        </a>
                    </c:if>

                </div>
            </div>
        </c:if>
    </div>
</main>


<div class="black_href"></div>


<c:import url="fragment/footer.jsp"/>

</body>

</html>
