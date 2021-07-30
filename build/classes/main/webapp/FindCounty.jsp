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
<title>Find a County</title>
</head>
<body>
	<jsp:include page="/Navbar.jsp" />
	<br/><br/><br/><br/><br/><br/><br/>
    
	<style>
		body {
		  background-image: url("background.jpg");
		  height: 100%;
		  background-position: relative;
		  background-repeat: no-repeat;
		  background-size: cover;
		}
	</style>
		
	<div class="col d-flex justify-content-center text-center">
		<div class="card-transparent border-primary mb-3">
		<div class="card-header special-card border-success" style="background-color: rgba(245, 245, 245, 0.9);">
			<p class="display-4">Search for a county</p>
		</div>
		<div class="card-body special-card" style="background-color: rgba(245, 245, 245, 0.9);">
			<form action="findcounty" method="post">
				<p>
					<div class="row g-2 justify-content-md-center">
						<div class="col-md-6">
					    	<div class="form-floating">
					      		<input type="text" class="form-control" id="countyname" name="countyname" value="${fn:escapeXml(param.countyname)}" required>
					      		<label for="countyname">County Name</label>
					    	</div>
					  	</div>
					  	<div class="col-md-4">
					    	<div class="form-floating">
					      		<select class="form-select" id="state" name="state" value="${fn:escapeXml(param.state)}" required>
					        		<option selected></option>
					        		<option value="AL">AL</option>
					        		<option value="AK">AK</option>
					        		<option value="AS">AS</option>
					        		<option value="AZ">AZ</option>
					        		<option value="AR">AR</option>
					        		<option value="CA">CA</option>
					        		<option value="CO">CO</option>
					        		<option value="CT">CT</option>
					        		<option value="DE">DE</option>
					        		<option value="DC">DC</option>
					        		<option value="FL">FL</option>
					        		<option value="GA">GA</option>
					        		<option value="HI">HI</option>
					        		<option value="ID">ID</option>
					        		<option value="IL">IL</option>
					        		<option value="IN">IN</option>
					        		<option value="IA">IA</option>
					        		<option value="KS">KS</option>
					        		<option value="KY">KY</option>
					        		<option value="LA">LA</option>
					        		<option value="ME">ME</option>
					        		<option value="MD">MD</option>
					        		<option value="MA">MA</option>
					        		<option value="MI">MI</option>
					        		<option value="MN">MN</option>
					        		<option value="MS">MS</option>
					        		<option value="MO">MO</option>
					        		<option value="MT">MT</option>
					        		<option value="NE">NE</option>
					        		<option value="NV">NV</option>
					        		<option value="NH">NH</option>
					        		<option value="NJ">NJ</option>
					        		<option value="NM">NM</option>
					        		<option value="NY">NY</option>
					        		<option value="NC">NC</option>
					        		<option value="ND">ND</option>
					        		<option value="OH">OH</option>
					        		<option value="OK">OK</option>
					        		<option value="OR">OR</option>
					        		<option value="PA">PA</option>
					        		<option value="PR">PR</option>
					        		<option value="RI">RI</option>
					        		<option value="SC">SC</option>
					        		<option value="SD">SD</option>
					        		<option value="TN">TN</option>
					        		<option value="TX">TX</option>
					        		<option value="UT">UT</option>
					        		<option value="VT">VT</option>
					        		<option value="VA">VA</option>
					        		<option value="VI">VI</option>
					        		<option value="WA">WA</option>
					        		<option value="WV">WV</option>
					        		<option value="WI">WI</option>
					        		<option value="WY">WY</option>
					      		</select>
					      		<label for="state">State/Territory</label>
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
		</div>
		</div>
	</div>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>