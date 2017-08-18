<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<script src="jquery-3.2.1.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Instaliter | Edit</title>
</head>
<body>
	<div id="header">
		<a href="photos"><button class="clickable" id="btn-name">Instaliter</button></a>
		<a href="logout" id="signout" class="signing">SIGN OUT</a>
		<span id="username" class="clickable">${sessionScope.un}</span>
	</div>
	
	<div id="edit-error" class="error">${error}</div>
	<div id="edit-success" class="success">${success}</div>
	<div id="photoid" style="display: none">${photoId}</div>
	
	<div id="edit-container">
		<div id="edit-icon">
			<img id="edit-img" src="resources/icons/edit1.png">
		</div>
		<hr>
		<form action="updatephoto" id="details" method="POST">
		<br>
			<div class="editlbl">EDIT</div>
			<input type="hidden" name="photoId" value="${photoId}" >
			<br><br>
			Title: <input type="text" name="title" id="title">
			<br><br>
			Description: <input type="text" name="desc" id="descr">
			<br><br>
			Tags: <input type="text" name="tag" id="tag">
			<br><br>
			<span id="sharelabel">Share with: </span><input type="text" name="share" id="share">
			<br><br>
			<div class="editbtn">
				<input class="choose" name="edit" type="submit" value="Apply Changes">
			</div>
			<br><br>
		</form>
	</div>
	
	<script>
		var uname;
		var photoId = $("#photoid").html();
		var photo;
		
		function loadPhoto() {
			return $.ajax({
				"url" : "ajaxphoto/"+photoId,
				"method" : "post",
				"dataType" : "json",
				"success" : function(data){
					photo = data;
				}
			});
		}
		
		$(document).ready(function() {
			uname = $("#username").html();
			$("#signout").show();
			$("#share").hide();
			$("#sharelabel").hide();
			
			if ($("#edit-success").html() != "")
				$("#edit-success").show();
			
			if ($("#edit-error").html() != "")
				$("#edit-error").show();
			
			$.when(loadPhoto()).done(function(){ 
				$("#edit-img").attr("src", "photo/"+photo.filename);
				$("#edit-img").attr("width", "256px");
				$("#edit-img").attr("height", "256px");
				
				$("#title").attr("value", photo.title);
				$("#descr").attr("value", photo.desc);
				$.post("ajaxtags/"+photo.id, function(data){
    				$("#tag").attr("value", data);
    			});
				if (photo.privacy == true) {
					$("#share").show();
					$("#sharelabel").show();
					$.post("/ajaxshared/"+photoId, function(data){
						$("#share").attr("value", data);
					});
				}
			});
			
			$("#username").click(function() {
				window.location = "profile?u=" + uname;
			});
		});
	</script>
</body>
</html>