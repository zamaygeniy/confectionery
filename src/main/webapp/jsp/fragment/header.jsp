<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mycss.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/myjs.js"></script>


<header>
    <div class="wrapper">
        <a href="${pageContext.request.contextPath}/controller?command=main_page"></a>
            <img src="${pageContext.request.contextPath}/img/logo1.jpg" alt="">
        </a>

        <ul class="menu">
            <li class="menu_link">
                <a href="${pageContext.request.contextPath}/controller?command=catalog_page"><fmt:message key="menu.menu"/></a>
            </li>
            <li class="menu_link">
                <a href="#"><fmt:message key="menu.company"/></a>
            </li>
            <li class="menu_link">
                <a href="#"><fmt:message key="menu.vacancy"/></a>
            </li>
        </ul>
        <div class="control_panel">
            <c:if test="${empty sessionScope.user}">
                <div class="user_actions">
                    <div class="register">
                        <a href="${pageContext.request.contextPath}/controller?command=registration_page" method="post"><fmt:message key="header.registration"/></a>
                    </div>
                    <div class="login">
                        <a href="${pageContext.request.contextPath}/controller?command=login_page">
                            <fmt:message key="header.login"/>
                        </a>
                    </div>
                </div>
            </c:if>
            <c:if test="${not empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/controller?command=profile_page">
                    <fmt:message key="profile.menu"/>
                </a>
            </c:if>
            <ul class="localization">
                <li class="loc_item">
                    <a href="${pageContext.request.contextPath}/controller?command=change_locale&locale=ru_RU"><img
                            src="${pageContext.request.contextPath}/img/ru.png" alt="ru"></a>
                </li>
                <li class="loc_item">
                    <a href="${pageContext.request.contextPath}/controller?command=change_locale&locale=en_EN"><img
                            src="${pageContext.request.contextPath}/img/en.png" alt="en"></a>
                </li>
            </ul>
        </div>
    </div>
</header>