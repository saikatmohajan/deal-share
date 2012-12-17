var activePage = "";
var url = "/product/Laptop/pn/";
function getSearchResult(){
	var element = document.getElementById("result");
	console.log(totalMatch, "Total Match : ");
	element.innerHTML = "Total Match : " + totalMatch;
	var rootUl = document.getElementById("current-pages").childNodes;
	
	var totalPages = totalMatch/50;
	if(totalPages > 10) {
		if(rootUl.length == 0){
			createPagination(1);
		}		
	}
}

function createPagination(startPage){
	var pageNumber = 0;	
	var listItem;
	var currentPages = document.getElementById("current-pages");
	
		listItem = createListNode("prevPageGroup", "\u00AB");
		currentPages.appendChild(listItem);
		listItem = createListNode("prevPage", "\u2039");
		currentPages.appendChild(listItem);
		for (var i=startPage; pageNumber<10; i++){
			listItem = createListNode("page"+i, i);
			currentPages.appendChild(listItem);
			pageQueue.enqueue(i);
			pageNumber++;
		}
		listItem = createListNode("nextPage", "\u203A");
		currentPages.appendChild(listItem);
		listItem = createListNode("nextPageGroup", "\u00BB");
		currentPages.appendChild(listItem);
		
		var firstElement = document.getElementById("page"+pageQueue.peek());
		firstElement.setAttribute("class", "active");
		firstElement.setAttribute("href", url+firstElement.textContent);
		
		activePage = firstElement.getAttribute("id");
}

function createListNode(id, text){
	var pageLink = document.createElement("a");
	pageLink.setAttribute("href", "#");
	pageLink.appendChild(document.createTextNode(text));
	
	var listItem = document.createElement("li");
	listItem.setAttribute("id", id);
	if(text=="\u00AB"){
		listItem.setAttribute("onclick", "onClickPreviousPageGroup(); return false");
	}else if(text=="\u2039"){
		listItem.setAttribute("onclick", "onClickPreviousPage();return false");
	}else if(text=="\u203A"){
		listItem.setAttribute("onclick", "onClickNextPage();return false");
	}else if(text=="\u00BB"){
		listItem.setAttribute("onclick", "onClickNextPageGroup();return false");
	}else{
		listItem.setAttribute("onclick", "onClickPage(this);return false");
	}
	listItem.appendChild(pageLink);
	
	return listItem;
}

function onClickPage(list){
	var element = document.getElementById(activePage);
	if(element != null){
		element.setAttribute("class", "");
	}
	list.setAttribute("class", "active");
	list.setAttribute("href",url+list.textContent);
	activePage = list.getAttribute("id");
}

function onClickPreviousPage(){
	var element = document.getElementById(activePage);
	console.log(parseInt(element.textContent,10), "text content");
	var prevElement = document.getElementById("page"+(parseInt(element.textContent,10)-1));
	if(element != null & prevElement != null){
		element.setAttribute("class", "");
		prevElement.setAttribute("class", "active");
		activePage = prevElement.getAttribute("id");
	}
}

function onClickPreviousPageGroup(){
	var queueLength = pageQueue.getLength();
	var startPage = pageQueue.peek()-10;
	var page = 1;
	if(startPage>0){
		for(var i=0; i<queueLength; i++){
			page = pageQueue.dequeue();
			element = document.getElementById("page"+page);
			element.parentNode.removeChild(element);
		}
		element = document.getElementById("prevPageGroup");
		element.parentNode.removeChild(element);
		element = document.getElementById("prevPage");
		element.parentNode.removeChild(element);
		element = document.getElementById("nextPage");
		element.parentNode.removeChild(element);
		element = document.getElementById("nextPageGroup");
		element.parentNode.removeChild(element);
		createPagination(startPage);
	}
	
}

function onClickNextPage(){
	var element = document.getElementById(activePage);
	var nextElement = document.getElementById("page"+(parseInt(element.textContent,10)+1));
	if(element != null & nextElement != null){
		element.setAttribute("class", "");
		nextElement.setAttribute("class", "active");
		activePage = nextElement.getAttribute("id");
	}
}

function onClickNextPageGroup(){
	var queueLength = pageQueue.getLength();
	var page = 0;
	for(var i=0; i<queueLength; i++){
		page = pageQueue.dequeue();
		element = document.getElementById("page"+page);
		element.parentNode.removeChild(element);
	}
	element = document.getElementById("prevPageGroup");
	element.parentNode.removeChild(element);
	element = document.getElementById("prevPage");
	element.parentNode.removeChild(element);
	element = document.getElementById("nextPage");
	element.parentNode.removeChild(element);
	element = document.getElementById("nextPageGroup");
	element.parentNode.removeChild(element);
	createPagination(page+1);
}