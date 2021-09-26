<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Products</title>
</head>
<body>
<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <th>Product ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Description</th>
        <th>Weight</th>
        <th>Image</th>
        <th>Number in stock</th>
    </tr>

    <c:forEach var="product" items="${productList}">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.price}</td>
            <td>${product.description}</td>
            <td>${product.weight}</td>
            <td>${product.image}</td>
            <td>${product.numberInStock}</td>
        </tr>
    </c:forEach>
</table>

<%--For displaying Previous link except for the 1st page --%>
<c:if test="${currentPage != 1}">
    <td><a href="${pageContext.request.contextPath}/controller?command=product_page&page=${currentPage - 1}">Previous</a></td>
</c:if>

<%--For displaying Page numbers.
The when condition does not display a link for the current page--%>
<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="${pageContext.request.contextPath}/controller?command=product_page&page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<%--For displaying Next link --%>
${requestScope.currentPage}88${requestScope.noOfPages}
<c:if test="${currentPage lt noOfPages}">
    <td><a href="${pageContext.request.contextPath}/controller?command=product_page&page=${currentPage + 1}">Next</a></td>
</c:if>

</body>
</html>
