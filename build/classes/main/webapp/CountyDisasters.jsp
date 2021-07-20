<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CountyDisasters</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>DisasterId</th>
                <th>FipsCountyCode</th>
                <th>Year</th>
                <th>DisasterType</th>
                <th>Description</th>
            </tr>
            <c:forEach items="${disasters}" var="disaster" >
                <tr>
                    <td><c:out value="${disaster.getDisasterId()}" /></td>
                    <td><c:out value="${disaster.getCounty().getFipsCountyCode()}" /></td>
                    <td><c:out value="${disaster.getYear()}" /></td>
                    <td><c:out value="${disaster.getDisasterType()}" /></td>
                    <td><c:out value="${disaster.getDescription()}" /></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
