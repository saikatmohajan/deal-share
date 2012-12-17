<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    		<p>
                    			<a href="#" class="btn btn-primary">Action</a> 
                    			<a href="#" class="btn">Action</a>
                    		</p>
                  		</div>
                	</div>
              	</li>
              	
              	<c:if test="${countProduct%2 == 1}">
              		</ul>	
					</div>				
				</c:if> 
				 
              	<c:set var="countProduct" value="${countProduct+1}" />
               
        	</c:forEach>  