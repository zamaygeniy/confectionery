<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>

<link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/musesans/stylesheet.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ui_kit.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/scroll.css">
<script src="${pageContext.request.contextPath}/scripts/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/scroll.js"></script>
<script src="${pageContext.request.contextPath}/scripts/slick.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/scripts.js"></script>

<header class="header">
    <div class="burger">
        <div class="burger_bef"></div>
        <div class="burger_mid"></div>
        <div class="burger_af"></div>
    </div>
    <div class="logo_n_menu">
        <div class="logo"><a href="${pageContext.request.contextPath}/controller?command=main_page"><img
                src="${pageContext.request.contextPath}/img/logo.svg" alt=""></a></div>
        <div class="menu">
            <div class="menu_item _dropped">
                <a href="${pageContext.request.contextPath}/controller?command=catalog_page"
                   class="menu_title"><fmt:message key="header.catalog"/></a>
                <div class="menu_body">
                    <div class="left_part scrollbar-outer" id="scroll">
                        <div class="slider_body swiper-container">
                            <div class="swiper-wrapper">
                                <c:forEach var="element" items="${applicationScope.product_types}">
                                    <div class="swiper-slide">
                                        <div class="menu_drop_item">
                                            <a>${element.type}</a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="swiper-scroll"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="menu_item"><a href="${pageContext.request.contextPath}/controller?command=orders_page"><fmt:message key="header.orders"/></a></div>
            <div class="menu_item"><fmt:message key="header.about"/></div>
        </div>
    </div>
    <div class="right_part">
        <div class="lang_change">
            <div class="icon">
                <svg width="24px" height="24px" viewBox="0 0 24 24" id="magicoon-Regular"
                     xmlns="http://www.w3.org/2000/svg">
                    <defs>
                        <style>.cls-1 {
                            fill: #4489ff;
                        }</style>
                    </defs>
                    <title>globe</title>
                    <g id="globe-Regular">
                        <path id="globe-Regular-2" data-name="globe-Regular" class="cls-1"
                              d="M21.188,8.752A9.686,9.686,0,0,0,13.4,2.347a10.081,10.081,0,0,0-2.792,0,9.679,9.679,0,0,0-7.789,6.4,9.663,9.663,0,0,0,0,6.5,9.686,9.686,0,0,0,7.792,6.4,10.072,10.072,0,0,0,1.4.1,9.919,9.919,0,0,0,1.4-.1,9.679,9.679,0,0,0,7.789-6.4,9.663,9.663,0,0,0,0-6.5Zm-1.844-.5H16.3a16.182,16.182,0,0,0-1.443-3.977A8.165,8.165,0,0,1,19.344,8.251ZM20.25,12a8.089,8.089,0,0,1-.321,2.251H16.576A16.454,16.454,0,0,0,16.75,12a16.387,16.387,0,0,0-.173-2.249H19.93A8.09,8.09,0,0,1,20.25,12ZM8.938,14.251A14.862,14.862,0,0,1,8.75,12a14.879,14.879,0,0,1,.188-2.249h6.125A14.976,14.976,0,0,1,15.25,12a14.894,14.894,0,0,1-.188,2.251Zm-4.867,0a8.046,8.046,0,0,1,0-4.5H7.424A16.28,16.28,0,0,0,7.25,12a16.429,16.429,0,0,0,.174,2.251ZM12.849,3.809a14.8,14.8,0,0,1,1.912,4.442H9.238a14.816,14.816,0,0,1,1.913-4.442A8.459,8.459,0,0,1,12,3.75,8.439,8.439,0,0,1,12.849,3.809Zm-3.708.465A16.257,16.257,0,0,0,7.7,8.251H4.656A8.16,8.16,0,0,1,9.141,4.274ZM4.657,15.751H7.7a16.3,16.3,0,0,0,1.442,3.975A8.16,8.16,0,0,1,4.657,15.751Zm6.494,4.44a14.824,14.824,0,0,1-1.912-4.44h5.522a14.806,14.806,0,0,1-1.912,4.44A8.6,8.6,0,0,1,11.151,20.191Zm3.708-.465A16.251,16.251,0,0,0,16.3,15.751h3.042A8.158,8.158,0,0,1,14.859,19.726Z"/>
                    </g>
                </svg>
            </div>
            <div class="body">
                <div class="lang"><a
                        href="${pageContext.request.contextPath}/controller?command=change_locale&locale=ru_RU&current_url=${pageContext.request.requestURL}">ru</a>
                </div>
                <div class="lang"><a
                        href="${pageContext.request.contextPath}/controller?command=change_locale&locale=en_EN&current_url=${pageContext.request.requestURL}">en</a>
                </div>
            </div>
        </div>

        <div class="cart">
            <a href="${pageContext.request.contextPath}/controller?command=cart_page">
                <svg id="cart_icon" xmlns="http://www.w3.org/2000/svg" width="28" height="20" viewBox="0 0 28 20">
                    <defs>
                        <linearGradient id="a" x1="100%" x2="0%" y1="32%" y2="68%">
                            <stop offset="0%" stop-color="#9C5EFF"/>
                            <stop offset="100%" stop-color="#4489FF"/>
                        </linearGradient>
                    </defs>
                    <g fill="none" fill-rule="evenodd">
                        <path fill="url(#a)" fill-rule="nonzero"
                              d="M23.063 6.42H3.938c-.933 0-1.688.756-1.688 1.688L4.5 18.233c0 .932.755 1.687 1.688 1.687h14.625c.932 0 1.687-.755 1.687-1.687l2.25-10.125c0-.932-.755-1.688-1.688-1.688zM9 17.108a1.125 1.125 0 1 1-2.25 0v-3.375a1.125 1.125 0 1 1 2.25 0v3.375zm5.625 0a1.125 1.125 0 1 1-2.25 0v-3.375a1.125 1.125 0 1 1 2.25 0v3.375zm5.625 0a1.125 1.125 0 1 1-2.25 0v-3.375a1.125 1.125 0 1 1 2.25 0v3.375z"
                              transform="translate(.5 .08)"/>
                        <path fill="#4489FF"
                              d="M27.5 7.5c0 .829-.503 1.5-1.125 1.5H1.625C1.003 9 .5 8.329.5 7.5S1.003 6 1.625 6h24.75c.622 0 1.125.671 1.125 1.5z"/>
                        <path fill="#FFBB01"
                              d="M20.09.41c.44.439.44 1.15 0 1.59l-6.42 8.67a1.125 1.125 0 0 1-1.59-1.59L18.5.41c.44-.44 1.151-.44 1.59 0z"/>
                    </g>
                </svg>
            </a>
        </div>
        <div class="log_in">
            <a href="${pageContext.request.contextPath}/controller?command=registration_page"
               method="post"><fmt:message key="header.registration"/>
            </a>
        </div>
        <div class="log_in">
            <a href="${pageContext.request.contextPath}/controller?command=login_page">
                <fmt:message key="header.login"/>
            </a>
        </div>
    </div>

</header>