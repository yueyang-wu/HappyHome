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
<title>Find a User</title>
</head>
<body>
	<jsp:include page="/Navbar.jsp" />
	<br/><br/>

	<div class="col d-flex justify-content-center text-center">
		<div class="card border-success mb-3">
		<div class="card-header bg-transparent border-success">
			<p class="display-5">Search for a user</p>
		</div>
		<div class="card-body">
			<form action="finduser" method="post">
				<p>
					<div class="row g-2 justify-content-md-center">
						<div class="col-md-6">
					    	<div class="form-floating">
					      		<input type="text" class="form-control" id="username" name="username" value="${fn:escapeXml(param.username)}" required>
					      		<label for="username">Username</label>
					    	</div>
					  	</div>
					</div>
				</p>
				<p>
					<input type="submit" class="btn btn-outline-success" value="Submit"/>
				</p>
			</form>
			<p><em>
				<div class="alert-success" role="alert">
					<span id="successMessage"><b>${messages.success}</b></span>
				</div>
			</em></p>
			<br/>
			<div class="card-footer bg-transparent border-success">
				<div id="userCreate"><a href="usercreate">Create a user</a></div>
			</div>
		</div>
		</div>
	</div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>
