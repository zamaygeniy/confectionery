<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>

</head>
<body>
<c:import url="fragment/header1.jsp"/>
<main>
    <h3><fmt:message key="confirmation.message"/></h3>
</main>
<c:import url="fragment/footer.jsp"/>
</body>
</html>
