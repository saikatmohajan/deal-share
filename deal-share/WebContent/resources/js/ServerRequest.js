var productList;
var columnsPerRow = 3;
var itemsPerPage = 50;
var productType;
var numberOfPages;
var totalMatch;
var mainMenuClicked = 0;
var startPage=1;

function callSearch(type,pageNumber) {
	var productUrl = "/product";
	productType = type;
	productUrl = productUrl + "/" + productType + "/pn/" + pageNumber;
	$.ajax({
		type : "GET",
		url : productUrl,
		success : function(response) {
			var productList = response.productList;
			var resultMetadata = response.resultMetadata;
			for(var metadata in resultMetadata){
				totalMatch = resultMetadata[metadata].totalMatch;
			}
			console.log("Total Match ..  Ha Ha  : ", totalMatch);
			
			numberOfPages = Math.ceil((totalMatch/itemsPerPage)) ;
			
				if(numberOfPages>1){
					//removeAllChildren(document.getElementById("searchResult"));
					//removeAllChildren(document.getElementById("current-pages"));
					if(mainMenuClicked==1){
						removeAllChildren(document.getElementById("searchResult"));
						removeAllChildren(document.getElementById("current-pages"));
						createPagination(startPage);
						mainMenuClicked=0;
					}else{
						removeAllChildren(document.getElementById("searchResult"));
					}
					createSearchResult(productList);
				}else{
					//removeAllChildren(document.getElementById("searchResult"));
					//removeAllChildren(document.getElementById("current-pages"));
					removeAllChildren(document.getElementById("resultContainner"));
					createSearchResult(productList);
				}
				
			
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
	
}

function createSearchResult(productList) {
	var columnsPerRow = 3;
	var count = 0;
	var PROD_NAME;
	var PROD_IMG_URL;
	
	for ( var product in productList) {
		
		if (productList.hasOwnProperty(product)) {
			PROD_NAME = productList[product].name;
			PROD_IMG_URL = productList[product].image-url;
	
			if(count%columnsPerRow==0){
				var rowFluidDiv = document.createElement("div");
				var thumbnailUl = document.createElement("ul");
				
				thumbnailUl.setAttribute("class", "thumbnails");
				thumbnailUl.appendChild(createList(PROD_NAME, PROD_IMG_URL));
				
				rowFluidDiv.setAttribute("class", "row-fluid");
				if(PROD_NAME != "undefined"){
					rowFluidDiv.appendChild(thumbnailUl);
				}
				document.getElementById("searchResult").appendChild(rowFluidDiv);
			}else{
				thumbnailUl.appendChild(createList(PROD_NAME, PROD_IMG_URL));
			}
		}
		count++;
	}
	
}


function createList(productName, imageUrl){
	var listItem = document.createElement("li");
	var thumbnailDiv = document.createElement("div");
	var image = document.createElement("img");
	var productDiv = document.createElement("div");
	var para1 = document.createElement("p");
	var para2 = document.createElement("p");
	var buyButton = document.createElement("a");
	var detailsButton = document.createElement("a");
	
	buyButton.setAttribute("class", "btn btn-primary");
	buyButton.appendChild(document.createTextNode("Buy"));
	
	detailsButton.setAttribute("class", "btn");
	detailsButton.appendChild(document.createTextNode("Details"));
	
	para2.appendChild(buyButton);
	para2.appendChild(detailsButton);
	
	para1.appendChild(document.createTextNode(productName));
	
	productDiv.setAttribute("class", "caption");
	productDiv.appendChild(para1);
	productDiv.appendChild(para2);
	
	image.setAttribute("src", imageUrl);
	image.setAttribute("alt", "");
	
	thumbnailDiv.setAttribute("class", "thumbnail");
	thumbnailDiv.appendChild(image);
	thumbnailDiv.appendChild(productDiv);
	
	listItem.setAttribute("class", "span6");
	listItem.appendChild(thumbnailDiv);
	
	return listItem;
}

function removeAllChildren(element){
	while(element.firstChild){
		element.removeChild(element.firstChild);
	}
}

function onClickMainMenu(type,pageNumber){
	mainMenuClicked = 1;
	//createPagination(startPage);
	callSearch(type,pageNumber);
}