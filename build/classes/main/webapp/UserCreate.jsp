<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a User</title>
</head>
<body>
	<h1>Create a User</h1>
	<form action="usercreate" method="post">
		<p>
			<label for="UserName">UserName</label>
			<input id="UserName" name="UserName" value="">
		</p>
		<p>
			<label for="Password">Password</label>
			<input id="Password" name="Password" value="">
		</p>
		<p>
			<label for="FirstName">FirstName</label>
			<input id="FirstName" name="FirstName" value="">
		</p>
		<p>
			<label for="LastName">LastName</label>
			<input id="LastName" name="LastName" value="">
		</p>
		<p>
		    <label for="EMAIL">Email</label>
		    <input id="EMAIL" name="EMAIL" value="">
		<p>
			<label for="CurrentZip">CurrentZip</label>
			<input id="CurrentZip" name="CurrentZip" value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>