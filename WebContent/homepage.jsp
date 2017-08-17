<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Instaliter | Photos</title>
<link href="style.css" rel="stylesheet" />
<script src="jquery-3.2.1.js"></script>
<script src="header.js"></script>
</head>
<body>
	<div id="header">
        <div id="homepage-header">
            <div id="name"> Instaliter </div>
        </div>
		<a href="loginreg.jsp" id="signin" class="signing">SIGN IN</a>
		<a href="logout" id="signout" class="signing">SIGN OUT</a>
		<span id="username" class="clickable">${sessionScope.un}</span>
	
		<div style="float:right">
			<form action="search" style="float:right" method="GET">
			<input id="hanap" type="text" name="key" value="${searchkey}">
			<input type="submit" id="search-button" value="">
			</form>
		</div>

        <a href="uploadpage.jsp"><span id="to-upload-page" class="clickable">
            <img src="resources/icons/upload1.png">
        </span></a>
	</div>
	
	<div id="menu"> 
		<div id="innermenu">
			<span id="publicbtn" class="clickable menubtn">
			Public Photos
			</span>
			<span id="sharedbtn" class="clickable menubtn">
			Shared Photos
			</span>
		</div> 
	</div>
	
	<div id="public-container" class="container">
		
    </div>  
    
    <div id="private-container" class="container">
    	
    </div>
        
    <div id="more">
    	<div id="moreimg">
    		<img  src="resources/icons/angle-arrow-down.png" >
    	</div>
    </div>
    
    <div id="modal">
         <div id="modal-container">
         	<img id="modal-photo">
         	<div id="modal-info">
	         	<div class="photo-info" id="photo-title">Title</div> 
	         	<div class="photo-info clickable" id="photo-uploader">Uploader</div>
	         	<div class="photo-info" id="photo-desc">Description</div>
	         	<div class="photo-info" id="photo-tags">Tags</div>
	         </div>
         </div>
    </div>
    
    <script>
    	const BY = 15;
    	var sharedpics;
    	var pics;
    	var uname, mode;
    	var lastpic, lastshared;
    	
    	function loadPics() {
    		return $.ajax({
				"url" : "ajaxphotos/public",
				"method" : "post",
				"dataType" : "json",
				"success" : function(data){
					pics = data;
				}
			});
    	}
    	
    	function loadShared() {
    		return $.ajax({
				"url" : "ajaxphotos/private",
				"method" : "post",
				"dataType" : "json",
				"success" : function(data){
					sharedpics = data;
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
    		$("#public-container").append(d);
    	}
    	
    	function addPrivate(num) {
    		var d = document.createElement("div");
    		var img = document.createElement("img");
    		
    		$(d).addClass("thumbnail");
    		$(d).addClass("clickable");
    		
    		$(d).attr("data-photoId", num);
    		$(img).attr("src", "photo/"+sharedpics[num].filename);
    		
    		$(d).append(img);
    		$("#private-container").append(d);
    	}
    	
    	function loadNext(by) {
    		var i;
    		for (i = lastpic ; i < lastpic+by && i < pics.length ; i++)
                addPic(i);
            lastpic = i;
    	}
    	
    	function loadPrivate(by) {
    		var i;
    		for (i = lastshared ; i < lastshared + by && i < sharedpics.length ; i++)
                addPrivate(i);
            lastshared = i;
    	}
    	
    	$(document).ready(function() {
    		uname = $("#username").html();
    		searchkey = $("#hanap").attr("value");
    		
    		if (uname != "")
    			$("#to-upload-page").show();
    		
    		$("#private-container").hide();
    		$("#public-container").show();
    		mode = "public";
			
  			lastpic = 0;
   			lastshared = 0;
   			
   			$.when(loadPics(), loadShared()).done(function(){
   				
   				loadNext(BY);
	   			if (mode == "public" && lastpic >= pics.length)
	   					$("#more").hide();
	   			loadPrivate(BY);
	   			if (mode == "private" && lastshared >= sharedpics.length)
	   					$("#more").hide();
	            
	    		$("#more").click(function() {
	    			if (mode == "public") {
	    				loadNext(BY);
	    				if (lastpic >= pics.length)
	    					$("#more").hide();
	    			} else if (mode == "shared") {
	    				loadPrivate(BY);
	    				if (lastshared >= sharedpics.length)
	    					$("#more").hide();
	    			}
	    		});
   			}); 
    		
    		$("#publicbtn").click(function() {
    			$("#private-container").hide();
    			$("#public-container").show();
    			if (lastpic >= pics.length)
    				$("#more").hide();
    			else $("#more").show();
    			mode = "public";
    		});
    		
    		$("#sharedbtn").click(function() {
    			$("#public-container").hide();
    			$("#private-container").show();
    			if (lastshared >= sharedpics.length)
    				$("#more").hide();
    			else $("#more").show();
    			mode = "shared";
    		});
    		
    		$(document).on("click", ".thumbnail", function(event) {
    		  	var id = event.currentTarget.getAttribute("data-photoId");
    		  	var photo;
    		  	if (mode == "public")
    		  		photo = pics[id];
    		  	else if (mode == "private")
    		  		photo = sharedpics[id];
    		  		
   		  		$("#modal-photo").attr("src", "photo/"+photo.filename);
    			$("#photo-title").text(photo.title);
    			$.post("ajaxusername/"+photo.userid, function(data){
    				$("#photo-uploader").text(data);
    			});
    			$("#photo-desc").text(photo.desc);
    			//$("#photo-tags").text("Tags: " + photo.tags);
    			
    			$("#modal").css("display", "flex");
    		});
    		
    		window.onclick = function(event) {
                if (event.target == document.getElementById("modal")) {
                    $("#modal").css("display", "none");
                }
            }
            
            $("#photo-uploader").click(function() {
            	window.location = "profile?u=" + $("#photo-uploader").text();
            });
            
            $("#username").click(function() {
				window.location = "profile?u=" + uname;
			});
    	});
    </script>
</body>
</html>