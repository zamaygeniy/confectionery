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
                    <div class="page_title"><fmt:message key="users.users"/></div>
                </div>
            </div>
        </div>
        <div class="user_admin">

            <div class="filter_part">
                <div class="filter_item">
                    <div class="filter_header">
                        <div class="name"><fmt:message key="users.search"/></div>
                        <div class="arrow">
                            <svg xmlns="http://www.w3.org/2000/svg" width="12" height="8" viewBox="0 0 12 8">
                                <path fill="#CDCDCD" fill-rule="nonzero"
                                      d="M6 4.415L10.5 0 12 1.714 6 8 0 1.714 1.5 0z"/>
                            </svg>
                        </div>
                    </div>
                    <div class="filter_body">
                        <form action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="search_users">
                            <div class="check_large">
                                <input type="checkbox" id="non_activated" name="status_id"
                                       value="1">
                                <label for="non_activated"><fmt:message key="users.non_activated"/></label>
                            </div>
                            <div class="check_large">
                                <input type="checkbox" id="activated" name="status_id"
                                       value="2">
                                <label for="activated"><fmt:message key="users.activated"/></label>
                            </div>
                            <div class="check_large">
                                <input type="checkbox" id="blocked" name="status_id"
                                       value="3">
                                <label for="blocked"><fmt:message key="users.blocked"/></label>
                            </div>
                            <div><fmt:message key="users.search_by_id"/></div>
                            <br>
                            <input class="input_middle" type="number" name="id">
                            <button type="submit"><fmt:message key="catalog.search"/></button>
                        </form>
                    </div>
                </div>
            </div>

            <div class="good_part">
                <div class="goods_container">
                    <c:forEach var="user" items="${userList}">
                        <div class="product_item">
                            <div class="img"><img src='data:image/jpg;base64,${user.image}'></div>
                            <div class="desc_part">
                                <a href="#" class="name">${user.email}</a><br>
                                <a href="${pageContext.request.contextPath}/controller?command=search_users&id=${user.id}"
                                   class="name">id: ${user.id}</a>
                                <div class="description">${user.firstName}</div>
                                <div class="weight">${user.lastName}</div>
                                <div class="cost">${user.role}</div>
                                <div class="cost">${user.status}</div>
                            </div>

                            <div class="controls">
                                <a href="${pageContext.request.contextPath}/controller?command=block_user&id=${user.id}">
                                    <button class="accept"><fmt:message key="button.block"/></button>
                                </a>
                                <a href="${pageContext.request.contextPath}/controller?command=unblock_user&id=${user.id}">
                                    <button class="reject"><fmt:message key="button.unblock"/></button>
                                </a>
                                <a href="${pageContext.request.contextPath}/controller?command=make_admin&id=${user.id}">
                                    <button class="done"><fmt:message key="button.make_admin"/></button>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                    <span class="fail-message">${errorNoUsersFound}</span>
                </div>
                <div class="pagination">
                    <div class="pag_body">
                        <c:if test="${currentPage > 1}">
                            <a href="${pageContext.request.contextPath}/controller?command=users_page&page=${currentPage - 1}">
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
                                        <a href="${pageContext.request.contextPath}/controller?command=users_page&page=${i}">
                                                ${i}
                                        </a>
                                    </div>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${currentPage lt numberOfPages}">
                            <a href="${pageContext.request.contextPath}/controller?command=users_page&page=${currentPage + 1}">
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
