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
<title>Matching County</title>
</head>
<body>
	<jsp:include page="/Navbar.jsp" />
	<br/><br/>

	<figure class="text-center">
	<p class="display-5">${county.getCountyName()}, ${county.getState()}</p>
	<a href="favoritecreate?fipscountycode=<c:out value="${county.getFipsCountyCode()}"/>">Favorite</a>
	<br/><br/>
	
	<table class="table">
		<thead>
	    	<tr>
	      		<th scope="col">FIPS County Code</th>
		      	<th scope="col">Current Price</th>
		      	<th scope="col">Price Forecast</th>
		      	<th scope="col">Recommendation Index</th>
		      	<th scope="col">Recommendation</th>
		      	<th scope="col">Favorites</th>
	    	</tr>
	  	</thead>
	  	<tbody>
	    	<tr>
	      		<td>${county.getFipsCountyCode()}</td>
	      		<td>$${housePrice.getCurrentPrice()}</td>
	      		<td>${housePriceForecast.getHomePriceForecast()}</td>
	      		<td>${report.getIndex()}</td>
	      		<td>
		      		<c:choose>
		         		<c:when test = "${report.getIndex() > 2}">
		            		Highly Recommended
		         		</c:when>
		         
		         		<c:when test = "${report.getIndex() < 2}">
		            		Not Recommended
		         		</c:when>
		         		
		         		<c:when test = "${report.getIndex() == 2}">
		            		Neutral
		         		</c:when>
		         
		         		<c:otherwise>
		            		
		         		</c:otherwise>
	      			</c:choose>
	      		</td>
	      		<td>${favorites}</td>
	    	</tr>
	  	</tbody>
	</table>
	<br/>
	<p class="display-6">Disasters</p>
	<table class="table table-striped">
		<thead>
	    	<tr>
		      	<th scope="col">Year</th>
		      	<th scope="col">Type</th>
		      	<th scope="col">Description</th>
	    	</tr>
	  	</thead>
	  	<tbody>
	  		<c:forEach items="${disasters}" var="disaster" >
		    	<tr>
		      		<td>${disaster.getYear()}</td>
		      		<td>${disaster.getDisasterType()}</td>
		      		<td>${disaster.getDescription()}</td>
		    	</tr>
	    	 </c:forEach>
	  	</tbody>
	</table>
	</figure>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>