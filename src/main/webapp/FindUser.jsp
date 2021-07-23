<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a User</title>
</head>
<body>
	<form action="finduser" method="post">
		<h1>Search for a User by UserName</h1>
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="${fn:escapeXml(param.username)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="userCreate"><a href="usercreate">Create A User</a></div>
	<br/>
	<h1>Matching User</h1>
        <table border="1">
            <tr>
                <th>UserName</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Email</th>
                <th>CurrentZip</th>
                <th>Favorite</th>
                <th>Update ZipCode</th>
                <th>Delete User</th>
            </tr>
            <tr>
			<td><c:out value="${user.getUserName()}" /></td>
			<td><c:out value="${user.getFirstName()}" /></td>
			<td><c:out value="${user.getLastName()}" /></td>
			<td><c:out value="${user.getEmail()}" /></td>
			<td><c:out value="${user.getCurrentZip()}" /></td>
			<td><a href="userfavorite?username=<c:out value="${user.getUserName()}"/>">Favorite</a></td>
			<td><a href="userupdate?username=<c:out value="${user.getUserName()}"/>">Update</a></td>
			<td><a href="userdelete?username=<c:out value="${user.getUserName()}"/>">Delete</a></td>
		</tr>
       </table>
</body>
</html>
