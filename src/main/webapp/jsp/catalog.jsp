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

<main class="main">
    <div class="catalog_header">
        <div class="wrapper">
            <div class="upper">
                <div class="page_title">Products</div>
            </div>
        </div>
    </div>o
    <div class="catalog_main_part">
        <div class="wrapper">
            <div class="filter_part">
                <div class="filter_item">
                    <div class="filter_header">
                        <div class="name">Menu</div>
                        <div class="arrow">
                            <svg xmlns="http://www.w3.org/2000/svg" width="12" height="8" viewBox="0 0 12 8">
                                <path fill="#CDCDCD" fill-rule="nonzero"
                                      d="M6 4.415L10.5 0 12 1.714 6 8 0 1.714 1.5 0z"/>
                            </svg>
                        </div>
                    </div>
                    <div class="filter_body">
                        <form action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="search_products">
                            <c:forEach var="element" items="${applicationScope.product_types}">
                                <div class="check_large">
                                    <input type="checkbox" id="${element.id}" name="product_type_id"
                                           value="${element.id}">
                                    <label for="${element.id}">${element.type}</label>
                                </div>
                            </c:forEach>
                            <button type="submit">Поиск</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="good_part">
                <div class="goods_container">

                    <c:forEach var="product" items="${productList}">
                        <div class="product_item">
                            <div class="img"><img src='data:image/jpg;base64,${product.image}'></div>
                            <div class="desc_part">
                                <a href="#" class="name">${product.name}</a>
                                <div class="description">${product.description}</div>
                                <div class="weight">${product.weight} г.</div>
                                <div class="cost">${product.price} р.</div>
                                <div class="add_item _blue_btn" data-id=${product.id} data-image=${product.image}>В
                                    корзину
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                    <p>
                        <span class="fail-message">${errorNoProductsFound}</span>
                        <button id="checkout">Оформить заказ</button>
                        <button id="clear_cart">Очистить корзину</button>
                    </p>
                    <div id="cart_content"></div>
                </div>
                <div class="pagination">
                    <div class="pag_body">
                        <c:if test="${currentPage > 1}">
                            <a href="${pageContext.request.contextPath}/controller?command=catalog_page&page=${currentPage - 1}">
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
                                        <a href="${pageContext.request.contextPath}/controller?command=catalog_page&page=${i}">
                                                ${i}
                                        </a>
                                    </div>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${currentPage lt numberOfPages}">
                            <a href="${pageContext.request.contextPath}/controller?command=catalog_page&page=${currentPage + 1}">
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
            </div>
        </div>
    </div>
</main>


<div class="black_href"></div>

<c:import url="fragment/footer.jsp"/>
<script src="${pageContext.request.contextPath}/scripts/cart.js"></script>
</body>

</html>