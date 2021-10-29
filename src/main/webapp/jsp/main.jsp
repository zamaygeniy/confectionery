<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>


<html>
<head>
    <title><fmt:message key="main.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/musesans/stylesheet.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ui_kit.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/scroll.css">

    <script src="${pageContext.request.contextPath}/scripts/slick.min.js"></script>
    <script src="${pageContext.request.contextPath}/scripts/scroll.js"></script>
    <script src="${pageContext.request.contextPath}/scripts/scripts.js"></script>
</head>
<body>

<c:import url="fragment/header.jsp"/>

<main class="main _index">
    <div class="first_full_screen">
        <div class="wrapper">
            <div class="contetn_box">
                <div class="screen_title">Сахарная планета</div>

            </div>
        </div>

        <div class="back_slider">
            <div class="slider_item">
                <img src="${pageContext.request.contextPath}/img/main-image1.jpg" alt="">
            </div>
            <div class="slider_item">
                <img src="${pageContext.request.contextPath}/img/main-image2.jpg" alt="">
            </div>
            <div class="slider_item">
                <img src="${pageContext.request.contextPath}/img/main-image3.jpg" alt="">
            </div>
        </div>
    </div>
    <div class="company_stat_part">
        <div class="wrapper">
            <div class="stat_item">
                <div class="number">
                    150
                </div>
                <div class="sup_text">Товаров доступно</div>
            </div>
            <div class="stat_item _heart">
                <div class="number">
                    40
                </div>
                <div class="sup_text">Различных видов сладостей</div>
            </div>
            <div class="stat_item">
                <div class="number">
                    1000
                </div>
                <div class="sup_text">Отзывов от покупателей</div>
            </div>
        </div>
    </div>
    <div class="big_img_block">
        <div class="wrapper">
            <div class="category_cont">
                <div class="left_img">
                    <div class="img_item">
                        <div class="img">
                            <img src="${pageContext.request.contextPath}/img/confectionery.jpg" alt="">
                        </div>
                        <a href="#" class="img_title">Кондитерские<br> изделия</a>
                    </div>
                </div>
                <div class="right_img">
                    <div class="img_item">
                        <div class="img">
                            <img src="${pageContext.request.contextPath}/img/bakery.jpg" alt="">
                        </div>
                        <a href="#" class="img_title">Выпечка</a>
                    </div>
                </div>
            </div>
            <div class="button_large">
                <fmt:message key="button.menu"/>
                <span><img src="${pageContext.request.contextPath}/img/ico-chevron-right-white.svg" alt=""></span>
            </div>
        </div>
    </div>




</main>

<c:import url="fragment/footer.jsp"/>
</body>
</html>
