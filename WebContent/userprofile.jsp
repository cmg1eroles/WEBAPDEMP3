<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Instaliter | User Profile</title>
<link rel="stylesheet" type="text/css" href="style.css">
<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
<script src="jquery-3.2.1.js"></script>
<script src="header.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<body>

	<div id="header">
		<a href="photos"><button class="clickable" id="btn-name">Instaliter</button></a>
		<a href="loginreg.jsp" id="signin" class="signing">SIGN IN</a>
		<a href="logout" id="signout" class="signing">SIGN OUT</a>
		<span id="username" class="clickable">${sessionScope.un}</span>
		<a href="uploadpage.jsp"><span id="to-upload-page" class="clickable">
            <img src="resources/icons/upload1.png">
        </span></a>
	</div>
	<div class="below-header">
		<img src="resources/icons/profile.png" id="dp">
		<hr id="fhr">
		<div id="name-container">
			<span id="profile-name">${user.username}</span>
			<!--  <span id="profile-username">(TestUserName)</span> -->
		</div>
	</div>
	
	<div id="divider">
		<hr>
			<div id="desc">
				${user.desc}
			</div>
		<hr>
	</div>
	
	<div class="photos-container">
	
	</div>
	
	<div id="morepics">
    	<div id="moreimg">
    		<img  src="resources/icons/angle-arrow-down.png" >
    	</div>
    </div>
    
    <div id="modal">
         <div id="modal-container">
         	<img id="modal-photo">
         	<div id="modal-info">
	         	<div class="photo-info" id="photo-title"></div> 
	         	<div class="photo-info" id="photo-uploader"></div>
	         	<div class="photo-info" id="photo-desc"></div>
	         	<div class="photo-info" id="photo-tags"></div>
	         	<img id="editbtn" class="clickable" src="resources/icons/edit.png">
	         </div>
         </div>
    </div>
    
	<script>
		const BY = 5;
		var pics;
		var uname, profile;
    	var lastpic;
    	var picid;
    	
    	function loadPics() {
    		return $.ajax({
				"url" : "ajaxuserphotos/"+profile,
				"method" : "post",
				"dataType" : "json",
				"success" : function(data){
					pics = data;
				}
			});
    	}
		
    	function addPic(num) {
    		var d = document.createElement("div");
    		var img = document.createElement("img");
    		
    		$(d).addClass("thumbnail");
    		$(d).addClass("clickable");
    		
    		$(d).attr("data-photoId", num);
    		$(img).attr("src", "photo/"+pics[num].filename);
    		
    		$(d).append(img);
    		$(".photos-container").append(d);
    	}
    	
    	function loadNext(by) {
    		var i;
    		for (i = lastpic ; i < lastpic+by && i < pics.length ; i++)
                addPic(i);
            lastpic = i;
    	}
    	
		$(document).ready(function() {
			
			uname = $("#username").html();
			profile = $("#profile-name").html();
			if (uname == profile) {
				$("#editbtn").show();
				$("#to-upload-page").show();
			}
				
			$.when(loadPics()).done(function() {
				if (pics.length == 0)
					$("#morepics").hide();
					
				lastpic = 0;
				
				loadNext(BY);
				
				$("#morepics").click(function() {
    				loadNext(BY);
    				if (lastpic >= pics.length)
    					$("#morepics").hide();
	    		});
			});
			
			$("#username").click(function() {
				window.location = "profile?u=" + uname;
			});
			
			$(document).on("click", ".thumbnail", function(event) {
    			var id;
    			id = event.currentTarget.getAttribute("data-photoId");
    			photo = pics[id];
    			
    			$("#modal-photo").attr("src", "photo/"+photo.filename);
    			$("#photo-title").text(photo.title);
    			$("#photo-uploader").text($("#profile-name").text());
    			$("#photo-desc").text(photo.desc);
    			$.post("ajaxtags/"+photo.id, function(data){
    				$("#photo-tags").text("Tags: " + data);
    			});
    			
    			picid = photo.id;
    			$("#modal").css("display", "flex");
    		});
  			
			$("#editbtn").click(function() {
				window.location = "editphoto?id=" + picid;
			});
			
    		window.onclick = function(event) {
                if (event.target == document.getElementById("modal")) {
                    $("#modal").css("display", "none");
                }
            }
		});
	</script>
</body>
</html>