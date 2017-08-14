<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Instaliter | Sign In</title>
<link href="style.css" rel="stylesheet" />
<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
<script src="jquery-3.2.1.js"></script>
</head>
<body>

	<div id="header">
		<div id="name"> Instaliter </div>
	</div>
	
	<div id="login-error" class="error">${loginerror}</div>
	<div class="loginregdiv">
		<form id="loginform" action="login" method="POST">
			<br>
			<div class="loginreglbl">LOGIN</div>
			<br><br>
			Username: <input type="text" name="uname">
			<br><br>
			Password: <input type="password" name="pword">
			<br><br>
			<div class="loginregbtn">
				<input id="loginbtn" type="submit" value="LOGIN">
			</div>
			<br><br>
			Do not have an account yet? <br> <span id="gotoreg" class="clickable">Register here!</span>
		</form>
	</div>
	<div class="divider"></div>
	
	<div id="reg-error" class="error">${regerror}</div>
	<div class="loginregdiv">
		<form id="registerform" action="register" method="POST">
			<div class="loginreglbl">SIGN UP</div>
			<br><br>
			Username: <input type="text" name="uname">
			<br><br>
			Password: <input type="password" name="pword">
			<br><br>
			Description: <input type = "text" name="desc">
			<br><br>
			<div class="loginregbtn">
				<input id="regbtn" type="submit" value="REGISTER">
			</div>
			<br><br><br>
		</form>
	</div>
	
	<footer> © Instaliter 2017</footer>
	
	<script>
		$(document).ready(function() {
			if ($("#login-error").html() != "") {
				$("#login-error").show();
			} else if ($("#reg-error").html() != "") {
				$("#reg-error").show();
				$('html, body').animate({
	                   scrollTop: $("#registerform").offset().top}, 0);
			}
			
			$("#gotoreg").click(function(){
				$('html, body').animate({
                   scrollTop: $("#registerform").offset().top}, 500);
			});
		});
	</script>
</body>
</html>