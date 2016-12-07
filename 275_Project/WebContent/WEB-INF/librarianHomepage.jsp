
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Librarian Homepage</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>

	<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<div id="top-navbar" class="navbar navbar-default navbar-top">
			<div class="container">
				<ul class="nav navbar-nav">
				
					<li class="active"><a href="#"> Home </a></li>
					<li><a  href="/275_lab2/book/createBook">Create Book</a></li>
					<li><a  href="/275_lab2/book/searchBook">Update Book</a></li>
					<li><a  href="/275_lab2/book/deleteBook">Delete Book</a></li>
					<li><a  href="/275_lab2/deleteUserInfo">Logout</a></li>
				</ul>
											
				
			</div>
		</div>
	<div class="container-fluid" id="register">
		<div class="row-fluid">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<div class="span8">					
					<form action="#" method="post">
						
						<input type="text" id="bookName" name="bookName">

						<button type="submit" value="Sign Up" class="btn btn-default" name="action">Search</button>
					</form>
					
					
					
				</div>
			</div>
		</div>
	</div>

</body>
</html>