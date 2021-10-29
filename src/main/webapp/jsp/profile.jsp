<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title>
        <fmt:message key="profile.title"/>
    </title>
</head>
<body>

<c:import url="fragment/header1.jsp"/>
<main>
    <div class="user_profile">
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="edit_user">
            <div class="input_item_large">
                <div class="input_title">Имя</div>
                <div class="input">
                    <input type="text" name="first_name" required pattern="(?=^.{1,32}$)^([A-zА-яЁё`'.-])+$" placeholder="${sessionScope.user.firstName}">
                </div>
            </div>
            <div class="input_item_large">
                <div class="input_title">Фамилия</div>
                <div class="input">
                    <input type="text" name="last_name" required pattern="(?=^.{1,32}$)^([A-zА-яЁё`'.-])+$" placeholder="${sessionScope.user.lastName}">
                </div>
            </div>
            <span class="success-message">${successfulEditMessage}</span>
            <span class="fail-message">${errorWrongDataMessage}</span>
            <button type="submit" class="button_middle _white">Изменить</button>
        </form>
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="change_password">
            <div class="input_item_large">
                <div class="input_title">Пароль</div>
                <div class="input">
                    <input type="password" name="password" required pattern="^\w{6,20}$" maxlength="20" placeholder="">
                </div>
            </div>
            <div class="input_item_large">
                <div class="input_title">Новый пароль</div>
                <div class="input">
                    <input type="password" name="new_password" required pattern="^\w{6,20}$" maxlength="20"
                           placeholder="">
                </div>
            </div>
            <span class="success-message">${successfulPasswordEditMessage}</span>
            <span class="fail-message">${errorWrongPasswordMessage}</span>
            <button type="submit" class="button_middle _white">Изменить пароль</button>
        </form>

        <div class="img"><img src='data:image/jpg;base64,${sessionScope.user.image}'></div>
        <form action="${pageContext.request.contextPath}/controller" method="post" enctype="multipart/form-data">
            <input type="hidden" name="command" value="change_user_image">
            <input id="file" type="file" name="file" autocomplete="off">
            <button type="submit" class="button_middle _white">Изменить фото</button>
        </form>

    </div>
</main>
<c:import url="fragment/footer.jsp"/>
</body>
</html>