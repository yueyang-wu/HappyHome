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
<title>Create a User</title>
</head>
<body>
	<jsp:include page="/Navbar.jsp" />
	<br/><br/>

	<div class="col d-flex justify-content-center text-center">
		<div class="card border-success mb-3">
		<div class="card-header bg-transparent border-success">
			<p class="display-5">Create a user</p>
		</div>
		<div class="card-body">
			<form action="usercreate" method="post">
				<p>
					<div class="row g-2 justify-content-md-center">
						<div class="col-md-6">
					    	<div class="form-floating">
					      		<input type="text" class="form-control" id="username" name="username" value="${fn:escapeXml(param.username)}" required>
					      		<label for="username">Username</label>
					    	</div>
					  	</div>
					  	<div class="col-md-6">
					    	<div class="form-floating">
					      		<input type="text" class="form-control" id="password" name="password" value="${fn:escapeXml(param.password)}" required>
					      		<label for="password">Password</label>
					    	</div>
					  	</div>
					</div>
				</p>
				<p>
					<div class="row g-2 justify-content-md-center">
						<div class="col-md-6">
					    	<div class="form-floating">
					      		<input type="text" class="form-control" id="firstname" name="firstname" value="${fn:escapeXml(param.firstname)}" required>
					      		<label for="firstname">First Name</label>
					    	</div>
					  	</div>
					  	<div class="col-md-6">
					    	<div class="form-floating">
					      		<input type="text" class="form-control" id="lastname" name="lastname" value="${fn:escapeXml(param.lastname)}" required>
					      		<label for="lastname">Last Name</label>
					    	</div>
					  	</div>
					</div>
				</p>
				<p>
					<div class="row g-2 justify-content-md-center">
						<div class="col-md-6">
					    	<div class="form-floating">
					      		<input type="text" class="form-control" id="email" name="email" value="${fn:escapeXml(param.email)}" required>
					      		<label for="email">Email</label>
					    	</div>
					  	</div>
					  	<div class="col-md-6">
					    	<div class="form-floating">
					      		<input type="text" class="form-control" id="currentzip" name="currentzip" value="${fn:escapeXml(param.currentzip)}">
					      		<label for="currentzip">Current ZIP Code</label>
					    	</div>
					  	</div>
					</div>
				</p>
				<p>
		 			<input type="submit" class="btn btn-outline-success" value="Submit"/>
				</p>
			</form>
			<p><em>
				<div class="alert-success d-inline-block" role="alert">
					<span id="successMessage"><b>${messages.success}</b></span>
				</div>
			</em></p>
		</div>
		</div>
	</div>
	<!-- </figure> -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>