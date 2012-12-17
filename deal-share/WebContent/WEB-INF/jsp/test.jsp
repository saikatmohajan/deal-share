<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dealshare.social.products.network.cj.*" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Bootstrap, from Twitter</title>
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le styles -->
    <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet" type="text/css" />
	<link href="/resources/css/container.css" rel="stylesheet" type="text/css" /> 
    <style type="text/css">
	
    </style>
	
	<script src="/resources/js/Queue.js" type="text/javascript"></script>
	<script src="/resources/js/ProductDetails.js" type="text/javascript"></script>
	<script src="/resources/js/SearchResult.js" type="text/javascript"></script>
	<script>
		var pageQueue = new Queue();
		var totalMatch = '${totalMatch}';
	</script>
    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="images/favicon.ico">
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png">
  </head>

  <body>
	
	<div class="container-narrow" style=" background-color: #F8F8F8; border: 0px;border-radius: 0px;">
		<div class = "row" >
			<div class="span*">
				<a class="btn btn-danger btn-large">ElectroDeal &raquo;</a>
			</div> 
			<div class="span* controls" style="left:380px;position: absolute;">
				<div class="input-append" >
					<input class="span5" type="text" data-provide="typeahead">
					<button class="btn" type="button"><i class="icon-search"></i></button>
				</div>
			</div>
			<div class="span* options pull-right">
				<input class="btn btn-danger" type="button" value="signin" name="commit" />
				<button class="btn btn-danger" type="button">signup</button>
			</div>
		</div>
	</div>
	<br>
	
<div class="container-narrow container-shadow">
		
	<div class="container-fluid" onload="getSearchResult(); return false">
	
		<div class="row-fluid" onload="createPagination();return false">
			<br>
		</div>
		<div class="row-fluid">			
				<ul class="nav nav-pills">
					<li><a href="#" class = "nav-box"><i class="icon-wrench icon-large"></i>Laptop</a></li>
					<li onclick = "getSearchResult(); return false"><a href="/product/Laptop/pn/1" class = "nav-box">Tablet</a></li>				
					<li><a href="#" class = "nav-box">Cellphone</a></li>
					<li><a href="#" class = "nav-box">Music</a></li>
					<li><a href="#" class = "nav-box">Accessories</a></li>
				</ul>			
		</div>
		<div >
			<hr>
		</div>
	</div>

     <div class="container-fluid">
      <div class="row-fluid">
        <div class="span3">
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
              <li class="nav-header">Sidebar</li>
              <li class="active"><a href="#">Link</a></li>
              <li><a href="" >Test</a></li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li class="nav-header">Sidebar</li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
            </ul>
          </div><!--/.well -->
         </div><!--/span-->
        
		<div class="span6"> 
			
			<div class="row-fluid">
				<div class="pagination" onload="getSearchResult(); return false">
 			 		<ul id = "current-pages"></ul>
				</div>
				<p id="result"> </p>
			</div>
		  <div class="row-fluid">
			<c:set var="numOfRows" value="0" />
			<c:set var="countProduct" value="0" />
			<c:set var="numOfRows" value="0" />
			
			<c:forEach items="${productList}" var="productDetails" >  
				<c:if test="${countProduct%2 == 0}">
					<div class="row-fluid">
						<ul class="thumbnails">					
				</c:if>  
			
				<li class="span6">
                	<div class="thumbnail">
                  		<img src="${productDetails.image-url}" alt="">
                  		<div class="caption">
                  			<h3>Product</h3>
                    		<p><c:out value="${productDetails.name}"/></p>
                    		<p><a href="#" class="btn btn-primary">Action</a> <a href="#" class="btn">Action</a></p>
                  		</div>
                	</div>
              	</li>
              	
              	<c:if test="${countProduct%2 == 1}">
              		</ul>	
					</div>				
				</c:if> 
				 
              	<c:set var="countProduct" value="${countProduct+1}" />
               
        	</c:forEach>  
		</div>
		
      </div><!--/row-->
      
      <div class="span3">
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
              <li class="nav-header">Sidebar</li>
              <li class="active"><a href="#">Link</a></li>
              <li><a href="" >Test</a></li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li class="nav-header">Sidebar</li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
            </ul>
          </div><!--/.well -->
         </div><!--/span-->

    </div><!--/.fluid-container-->
	</div>
    </div><!--/.container-narrow-->
	
	<hr>
	<div >
		<footer >
			<p>&copy; Company 2011</p>
		</footer>
	</div>

	
	<!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="http://code.jquery.com/jquery-1.7.2.min.js"></script>	
	<script src="http://twitter.github.com/bootstrap/assets/js/bootstrap.js"></script>    

  </body>
</html>
