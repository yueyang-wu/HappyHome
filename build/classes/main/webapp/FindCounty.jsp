
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a County</title>
</head>
<body>
	<form action="findcounty" method="post">
		<h1>Search for a County by CountyName and State</h1>
		<p>
			<label for="countyName">CountyName</label>
			<input id="countyName" name="countyName" value="${fn:escapeXml(param.countyName)}">
			<label for="state">State</label>
			<input id="state" name="state" value="${fn:escapeXml(param.state)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="findUser"><a href="finduser">Find A User</a></div>
	<br/>
	<h1>Matching County</h1>
	<table border="1">
		<tr>
			<th>FipsCountyCode</th>
			<th>CountyName</th>
			<th>State</th>
			<th>PopularityIndex</th>
			<th>CurrentPrice</th>
			<th>PriceForecast</th>
			<th>RecommendationIndex</th>
			<th>Recommendation</th>
			<th>Disasters</th>
		</tr>
		<tr>
			<td><c:out value="${county.getFipsCountyCode()}" /></td>
			<td><c:out value="${county.getCountyName()}" /></td>
			<td><c:out value="${county.getState()}" /></td>
			<td><c:out value="${popularity}" /></td>
			<td><c:out value="${housePrice.getCurrentPrice()}" /></td>
			<td><c:out value="${housePriceForecast.getHomePriceForecast()}" /></td>
			<td><c:out value="${report.getIndex()}" /></td>
			<td><c:out value="${report.getRecommendation()}" /></td>
			<td><a href="countydisasters?countyName=<c:out value="${county.getCountyName()}"/>&&state=<c:out value="${county.getState()}"/>">Disasters</a></td>
		</tr>
	</table>
</body>
</html>