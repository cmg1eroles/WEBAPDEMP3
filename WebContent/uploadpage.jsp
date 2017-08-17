<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Instaliter | Upload</title>
<link rel="stylesheet" type="text/css" href="style.css">
<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
<script src="jquery-3.2.1.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div id="header">
		<a href="homepage.jsp"><button class="clickable" id="btn-name">Instaliter</button></a>
		<a href="logout" id="signout" class="signing">SIGN OUT</a>
		<span id="username" class="clickable">${sessionScope.un}</span>
	</div>
	
	<div id="upload-error" class="error">${error}</div>
	<div id="upload-success" class="success">${success}</div>
	<div id="upload-container">
		<div id="upload-icon">
			<img id="upload-img" src="resources/icons/upload.png">
		</div>
		<hr>
		<div class="uploaddiv">
		<form action="uploadphoto" id="details" method="POST" enctype="multipart/form-data">
			<br>
			<div class="uploadlbl">UPLOAD</div>
			<br><br>
			<input id="imginp" type="file" name="image" accept=".jpg, .png, .tiff">
			<br><br>
			Title: <input type="text" name="title">
			<br><br>
			Description: <input type="text" name="descr" id="edit-desc">
			<br><br>
			Tags: <input type="text" name="tag">
			<br><br>
			<div class="uploadbtn">
				<input class="choose" name="btn" type="radio"><label class="pubpriv">Post as Public Photo</label><br>
				<input class="choose" name="btn" type="radio"><label class="pubpriv">Post as Private Photo</label><br>
			</div>
			<br><br>
			<input type="submit" value="UPLOAD">
			<br><br>
		</form>
		</div>
		<!-- <form action="uploadphoto" id="details" method="POST" enctype="multipart/form-data">
		<div class="form-div-upload">
			<input id="imginp" type="file" name="image" accept=".jpg, .png, .tiff">
			<br><br>
			Title: <input type="text" name="title">
			<br><br>
			Description: <input type="text" name="desc" id="descr">
			<br><br>
			Tags: <input type="text" name="tag">
		</div>
			<br><br>
			<input class="choose" name="btn" type="submit" value="Post as public photo">
			<input class="choose" name="btn" type="submit" value="Post as private photo">
		</form> -->
	</div>
	
	<script>
		var uname;
		
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					$("#upload-img").attr("src", e.target.result);
				}
				reader.readAsDataURL(input.files[0]);
			}
		}
		
		$(document).ready(function() {
			uname = $("#username").html();
			$("#signout").show();
			
			if ($("#upload-success").html() != "")
				$("#upload-success").show();
			
			if ($("#upload-error").html() != "")
				$("#upload-error").show();
			
			$("#imginp").change(function() {
				readURL(this);
				$("#upload-img").css("width", "256px");
				$("#upload-img").css("height", "256px");
			});
			
			$("#username").click(function() {
				window.location = "profile?u=" + uname;
			});
		});
	</script>
</body>
</html>