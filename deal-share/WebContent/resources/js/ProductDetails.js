
function getXmlHttpRequestObject(){
	var xmlHttp = null;
	if (window.XMLHttpRequest) {
	  xmlHttp = new XMLHttpRequest();
	  if ( typeof xmlHttp.overrideMimeType != 'undefined') { 
	    xmlHttp.overrideMimeType('application/xml'); 
	  }
	} else if (window.ActiveXObject) {
	  xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
	  alert('Perhaps your browser does not support xmlhttprequests?');
	}
	return xmlHttp;
}

function getCJProductXml(productName){
	var xmlDoc = null;
	var xmlHttp = getXmlHttpRequestObject();
	var requestUrl = "https://product-search.api.cj.com/v2/product-search?website-id=6423529&serviceable-area=US&page-number=1&keywords=" + productName;
	xmlHttp.onreadystatechange = function() {
		console.debug("Inside Callback");
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			xmlDoc=xmlHttp.responseXML;
			alert(xmlHttp.responseText);
			return xmlDoc;
		} else {
		    // wait for the call to complete
		}
	};
	xmlHttp.open('GET', requestUrl, true);
	xmlHttp.send(null);	
}

function getProductList(){
	var xmlDoc = getCJProductXml("Laptop");
	console.debug("After call to CJ");
	document.getElementById("PL").firstChild.innerHTML = "<p>Hello</p>";
}

