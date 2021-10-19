<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html><title>Error Page</title>
<body>
<c:forEach var="element" items="${pageContext.exception.stackTrace}">
    ${element}
    <br/>
</c:forEach>
Request from ${pageContext.errorData.requestURI} is failed
<br/>
Servlet name: ${pageContext.errorData.servletName}
<br/>
Status code: ${pageContext.errorData.statusCode}
<br/>
Exception: ${pageContext.exception}
<br/>
<hr/>
Message from exception: ${pageContext.exception.message}
</body>
</html>