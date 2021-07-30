<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<title>Delete a User</title>
</head>
<body>
	<jsp:include page="/Navbar.jsp" />
	<br/><br/>

	<figure class="text-center">
	<form action="userdelete" method="post">
		<p class="lead">
			<div <c:if test="${messages.disableSubmit}">style="display:none"</c:if>>
				Delete ${param.username}?
			</div>
		</p>
		<p>
			<span id="submitButton" <c:if test="${messages.disableSubmit}">style="display:none"</c:if>>
				<input type="hidden" name="username" value="${param.username}"/>
				<input type="submit" class="btn btn-outline-success" value="Submit"/>
			</span>
		</p>
 		<p><em>
 			<div class="alert-success d-inline-block" role="alert">
				<span id="successMessage"><b>${messages.success}</b></span>
			</div>
		</em></p>
	</form>
	</figure>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>