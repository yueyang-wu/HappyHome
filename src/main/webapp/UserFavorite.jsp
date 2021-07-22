<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Favorites</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>FavoriteId</th>
                <th>UserName</th>
                <th>FipsCountyCode</th>
                <th>State</th>
                <th>County</th>
            </tr>
            <c:forEach items="${Favorites}" var="Favorite" >
                <tr>
                    <td><c:out value="${favorite.getFavoriteId()}" /></td>
                    <td><c:out value="${favorite.getUser().getUserName()}" /></td>
                    <td><c:out value="${favorite.getCounty().getFipsCountyCode()}" /></td>
                    <td><c:out value="${favorite.getCounty().getState()}" /></td>
                    <td><c:out value="${favorite.getCounty().getCountyName()}" /></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
