<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>

<link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/musesans/stylesheet.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ui_kit.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/scroll.css">

<footer class="footer">
    <div class="part_1">
        <div class="wrapper">
            <div class="col_1">
                <div class="socials">
                    <div class="social_item">
                        <a href="#">
                            <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 36 36">
                                <g fill="none" fill-rule="nonzero">
                                    <circle cx="18" cy="18" r="17.5" stroke="#4489FF"/>
                                    <path fill="#4489FF"
                                          d="M28.6 12.55c-.675.375-1.5.6-2.25.675.825-.525 1.425-1.35 1.725-2.325-.75.45-1.575.825-2.55 1.05-.75-.825-1.725-1.35-2.85-1.35-2.175 0-3.975 1.875-3.975 4.275 0 .3 0 .675.075.975-3.3-.15-6.225-1.875-8.175-4.425-.375.6-.525 1.35-.525 2.175 0 1.5.675 2.775 1.725 3.525-.675 0-1.275-.225-1.8-.525v.075c0 2.025 1.35 3.75 3.15 4.2-.3.075-.675.15-1.05.15-.225 0-.525 0-.75-.075.525 1.725 1.95 2.925 3.675 2.925-1.35 1.125-3.075 1.8-4.875 1.8-.3 0-.6 0-.975-.075C10.9 26.8 13 27.55 15.25 27.55c7.275 0 11.25-6.45 11.25-12.075v-.525c.9-.75 1.575-1.5 2.1-2.4z"/>
                                </g>
                            </svg>
                        </a>
                    </div>
                    <div class="social_item">
                        <a href="#">
                            <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 36 36">
                                <g fill="none" fill-rule="nonzero">
                                    <circle cx="18" cy="18" r="17.5" stroke="#4489FF"/>
                                    <path fill="#4489FF"
                                          d="M22.425 14.625h-3v-1.95c0-.75.525-.9.825-.9h2.1v-3.3h-2.925c-3.3 0-3.975 2.475-3.975 3.975v2.175h-1.875V18h1.875v9.525h3.975V18h2.7l.3-3.375z"/>
                                </g>
                            </svg>
                        </a>
                    </div>
                    <div class="social_item">
                        <a href="#">
                            <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 36 36">
                                <g fill="none" fill-rule="nonzero">
                                    <circle cx="18" cy="18" r="17.5" stroke="#4489FF"/>
                                    <path fill="#4489FF"
                                          d="M28.495 13.88c.153-.507 0-.88-.728-.88h-2.406c-.612 0-.895.322-1.048.677 0 0-1.223 2.969-2.957 4.897-.561.558-.816.736-1.122.736-.153 0-.375-.178-.375-.685V13.88c0-.609-.178-.88-.687-.88h-3.783c-.382 0-.612.283-.612.55 0 .578.867.71.956 2.334v3.527c0 .773-.14.913-.446.913-.816 0-2.8-2.981-3.978-6.393-.23-.663-.462-.931-1.077-.931H7.825c-.687 0-.825.322-.825.677 0 .634.816 3.78 3.8 7.941C12.787 24.46 15.59 26 18.14 26c1.53 0 1.72-.342 1.72-.931V22.92c0-.684.144-.82.629-.82.357 0 .969.177 2.396 1.547C24.517 25.27 24.787 26 25.705 26h2.406c.688 0 1.032-.342.833-1.017-.217-.673-.996-1.65-2.03-2.807-.56-.66-1.402-1.37-1.657-1.725-.357-.456-.255-.66 0-1.065 0 0 2.932-4.11 3.238-5.506"/>
                                </g>
                            </svg>
                        </a>
                    </div>
                    <div class="social_item">
                        <a href="#">
                            <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" viewBox="0 0 36 36">
                                <g fill="none" fill-rule="evenodd">
                                    <circle cx="18" cy="18" r="17.5" stroke="#4489FF"/>
                                    <path fill="#4489FF"
                                          d="M14.732 23.99c.03-.072.041-.09.043-.108.088-.933.172-1.868.268-2.8a.508.508 0 0 1 .149-.3c.906-.821 1.822-1.632 2.733-2.449 1.301-1.168 2.6-2.338 3.9-3.508.31-.28.623-.557.928-.842.045-.042.093-.147.073-.177-.035-.05-.133-.102-.188-.088-.175.044-.36.09-.51.184-1.708 1.062-3.41 2.134-5.114 3.201-.862.54-1.727 1.078-2.59 1.618-.392.246-.783.496-1.176.746l1.484 4.523m2.852-1.229c-.705.681-1.4 1.348-2.09 2.02-.24.233-.516.33-.85.302-.206-.018-.316-.113-.38-.306-.519-1.603-1.047-3.202-1.565-4.805-.053-.166-.136-.246-.306-.297-1.22-.37-2.437-.75-3.653-1.134a2.13 2.13 0 0 1-.485-.21c-.308-.191-.345-.483-.067-.716.207-.174.455-.315.706-.418.721-.295 1.453-.564 2.18-.842l12.935-4.959c.238-.09.479-.176.714-.274.33-.138.66-.203.962.049.311.26.358.62.285.988-.188.94-.393 1.876-.592 2.814l-1.857 8.705c-.11.517-.21 1.036-.341 1.548-.19.749-.702.963-1.389.604a1.55 1.55 0 0 1-.196-.126l-3.819-2.807-.192-.136"/>
                                </g>
                            </svg>
                        </a>
                    </div>

                </div>
            </div>

            <div class="col_3">
                <div class="links_group">
                    <c:if test="${sessionScope.user.role == 'GUEST'}">
                        <div class="link_item"><a
                                href="${pageContext.request.contextPath}/controller?command=login_page"><fmt:message
                                key="header.login"/></a></div>
                        <div class="link_item"><a
                                href="${pageContext.request.contextPath}/controller?command=registration_page"><fmt:message
                                key="header.registration"/></a></div>
                    </c:if>
                    <c:if test="${sessionScope.user.role == 'USER' || sessionScope.user.role == 'ADMIN'}">
                        <div class="link_item">
                            <a href="${pageContext.request.contextPath}/controller?command=profile_page">
                                <fmt:message key="header.profile"/>
                            </a>
                        </div>
                        <div class="link_item">
                            <a href="${pageContext.request.contextPath}/controller?command=logout">
                                <fmt:message key="header.logout"/>
                            </a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <div class="part_2">
        <div class="wrapper">
            <div class="left_part">
                <div class="copy_right">2021 © «MAKIMA»<br>
                    148714 г. Минск. Октябрьская д.2
                </div>
            </div>
            <div class="right_part">
                <div class="en"><a
                        href="${pageContext.request.contextPath}/controller?command=change_locale&locale=en_EN">En</a>
                </div>
                <div class="ru"><a
                        href="${pageContext.request.contextPath}/controller?command=change_locale&locale=ru_RU">Ru</a>
                </div>
            </div>
        </div>
    </div>
</footer>
</div>