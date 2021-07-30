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
<title>Matching User</title>
</head>
<body>
	<jsp:include page="/Navbar.jsp" />
	<br/><br/>

	<figure class="text-center">
	<p class="display-5">${user.getUserName()}</p>
	
	<table class="table">
		<thead>
	    	<tr>
	      		<th scope="col">First Name</th>
		      	<th scope="col">Last Name</th>
		      	<th scope="col">Email</th>
		      	<th scope="col">Current ZIP Code</th>
		      	<th scope="col">Delete</th>
	    	</tr>
	  	</thead>
	  	<tbody>
	    	<tr>
	      		<td>${user.getFirstName()}</td>
	      		<td>${user.getLastName()}</td>
	      		<td>${user.getEmail()}</td>
	      		<td>${user.getCurrentZip()} <a href="userupdate?username=<c:out value="${user.getUserName()}"/>">Update</a></td>
	      		<td><a href="userdelete?username=<c:out value="${user.getUserName()}"/>">Delete</a></td>
	    	</tr>
	  	</tbody>
	</table>
	<br/>
	<p class="display-6">Favorites</p>
	<table class="table table-striped">
		<thead>
	    	<tr>
		      	<th scope="col">FIPS County Code</th>
		      	<th scope="col">County Name</th>
		      	<th scope="col">State</th>
		      	<th scope="col">Delete</th>
	    	</tr>
	  	</thead>
	  	<tbody>
	  		<c:forEach items="${favorites}" var="favorite" >
		    	<tr>
		      		<td>${favorite.getCounty().getFipsCountyCode()}</td>
		      		<td>${favorite.getCounty().getCountyName()}</td>
		      		<td>${favorite.getCounty().getState()}</td>
		      		<td><a href="favoritedelete?favoriteid=<c:out value="${favorite.getFavoriteId()}"/>">Delete</a></td>
		    	</tr>
	    	 </c:forEach>
	  	</tbody>
	</table>
	</figure>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>
