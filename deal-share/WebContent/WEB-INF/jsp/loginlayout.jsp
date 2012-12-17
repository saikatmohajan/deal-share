<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dealshare.social.products.network.cj.*" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet" type="text/css" />
	<link href="/resources/css/container.css" rel="stylesheet" type="text/css" /> 
    <style type="text/css">
	
    </style>
	
	<script src="/resources/js/Queue.js" type="text/javascript"></script>
	<script src="/resources/js/ProductDetails.js" type="text/javascript"></script>
	<script src="/resources/js/SearchResult.js" type="text/javascript"></script>
	<script src="/resources/js/ServerRequest.js" type="text/javascript"></script>
	<script>
		var pageQueue = new Queue();
		//var totalMatch = '${totalMatch}';
		//var productList = '${productList}';
	</script>
    <!-- Le fav and touch icons -->
    <!--<link rel="shortcut icon" href="images/favicon.ico">
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png">-->
  </head>

  <body>	
	<div class="container-narrow" style=" background-color: #F8F8F8; border: 0px;border-radius: 0px;">
		<tiles:insertAttribute name="logo" />
	</div>
	<br>	
	<div class="container-narrow container-shadow" id="mainBody">		
		<div class="container-fluid" >	
			<div class="row-fluid">			
				<tiles:insertAttribute name="menu" />		
			</div>
			<div >
				<hr>
			</div>
		</div>
     	<div class="container-fluid" id="welcome">
      		<tiles:insertAttribute name="body" />
		</div>
	</div>
	<hr>
	<div >
		<tiles:insertAttribute name="footer" />
	</div>

	<script src="http://code.jquery.com/jquery-1.7.2.min.js"></script>	
	<script src="http://twitter.github.com/bootstrap/assets/js/bootstrap.js"></script>    

  </body>
</html>
