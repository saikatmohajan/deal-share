package com.dealshare.social.products.network.cj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ResponseParser implements Constants{
	
	public static void processxmlFile(){
		
		/*int c = 0;
		for (Map<String,String> map : getCjProductList()) {
			System.out.println("Product No : " + c);
			System.out.println("------------------");
			for (Map.Entry<String, String> entry : map.entrySet()){
			    System.out.println(entry.getKey() + " : " + entry.getValue());
			}
			c++;
		}*/
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = null;
		if(nlList.getLength() != 0){
			nValue = (Node) nlList.item(0);
			return nValue.getNodeValue();
		}else{
			return Constants.EMPTY_STRING;
		}
		
	 }
	
	public static long getCjTotalMatch(Element element) {
        long totalMatch = 0;
        // get a map containing the attributes of this node
        NamedNodeMap attributes = element.getAttributes();
        // get the number of nodes in this map
        int numAttrs = attributes.getLength();
        for (int i = 0; i < numAttrs; i++) {
            Attr attr = (Attr) attributes.item(i);
            String attrName = attr.getNodeName();
            String attrValue = attr.getNodeValue();
            if(attrName.equals(Constants.CJ_PRODUCTS_TOTAL_MATCHED)){
            	totalMatch = Long.parseLong(attrValue);
            	//System.out.println("Found attribute: " + attrName + " with value: " + totalMatch);
            }
        }
        return totalMatch;
    }
	
	public static int getRecordReturned(Element element) {
        int recordsReturned = 0;
        // get a map containing the attributes of this node
        NamedNodeMap attributes = element.getAttributes();
        // get the number of nodes in this map
        int numAttrs = attributes.getLength();
        for (int i = 0; i < numAttrs; i++) {
            Attr attr = (Attr) attributes.item(i);
            String attrName = attr.getNodeName();
            String attrValue = attr.getNodeValue();
            if(attrName.equals(Constants.CJ_PRODUCTS_RECORD_RETURNED)){
            	recordsReturned = Integer.parseInt(attrValue);
            	System.out.println("Found attribute: " + attrName + " with value: " + recordsReturned);
            }
        }
        return recordsReturned;
    }
	
	public static int getPageNumber(Element element) {
        int pageNumber = 0;
        // get a map containing the attributes of this node
        NamedNodeMap attributes = element.getAttributes();
        // get the number of nodes in this map
        int numAttrs = attributes.getLength();
        for (int i = 0; i < numAttrs; i++) {
            Attr attr = (Attr) attributes.item(i);
            String attrName = attr.getNodeName();
            String attrValue = attr.getNodeValue();
            if(attrName.equals(Constants.CJ_PRODUCTS_PAGE_NUMBER)){
            	pageNumber = Integer.parseInt(attrValue);
            	System.out.println("Found attribute: " + attrName + " with value: " + pageNumber);
            }
        }
        return pageNumber;
    }
	
	public static Map<String,String> getCjProductDetails(Element product){
		Map<String,String> productDetails = new HashMap<String,String>();
		String nValue = null;
		
		nValue = getTagValue(Constants.CJ_PRODUCT_ADD_ID, product);
		productDetails.put(Constants.CJ_PRODUCT_ADD_ID, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_ADVERTISER_ID, product);
		productDetails.put(Constants.CJ_PRODUCT_ADVERTISER_ID, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_ADVERTISER_NAME, product);
		productDetails.put(Constants.CJ_PRODUCT_ADVERTISER_NAME, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_ADVERTISER_CATEGORY, product);
		productDetails.put(Constants.CJ_PRODUCT_ADVERTISER_CATEGORY, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_BUY_URL, product);
		productDetails.put(Constants.CJ_PRODUCT_BUY_URL, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_CATALOG_ID, product);
		productDetails.put(Constants.CJ_PRODUCT_CATALOG_ID, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_CURRENCY, product);
		productDetails.put(Constants.CJ_PRODUCT_CURRENCY, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_DESCRIPTION, product);
		productDetails.put(Constants.CJ_PRODUCT_DESCRIPTION, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_IMAGE_URL, product);
		productDetails.put(Constants.CJ_PRODUCT_IMAGE_URL, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_IN_STOCK, product);
		productDetails.put(Constants.CJ_PRODUCT_IN_STOCK, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_ISBN, product);
		productDetails.put(Constants.CJ_PRODUCT_ISBN, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_MANUFACTURER_NAME, product);
		productDetails.put(Constants.CJ_PRODUCT_MANUFACTURER_NAME, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_MANUFACTURER_SKU, product);
		productDetails.put(Constants.CJ_PRODUCT_MANUFACTURER_SKU, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_NAME, product);
		productDetails.put(Constants.CJ_PRODUCT_NAME, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_PRICE, product);
		productDetails.put(Constants.CJ_PRODUCT_PRICE, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_RETAIL_PRICE, product);
		productDetails.put(Constants.CJ_PRODUCT_RETAIL_PRICE, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_SALE_PRICE, product);
		productDetails.put(Constants.CJ_PRODUCT_SALE_PRICE, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_SKU, product);
		productDetails.put(Constants.CJ_PRODUCT_SKU, nValue);
		
		nValue = getTagValue(Constants.CJ_PRODUCT_UPC, product);
		productDetails.put(Constants.CJ_PRODUCT_UPC, nValue);
		
		return productDetails;
	}
	
	public static List<Map<String,String>> getCjProductList(Map<String,String> param){
		List<Map<String,String>> productList = new ArrayList<Map<String,String>>();
		callCjRestApi(param);
		Document doc = getCjXmlDoc(CJ_XML_FILE_NAME);
		NodeList nodeList = doc.getElementsByTagName("product");
		for(int productCount = 0; productCount < nodeList.getLength(); productCount++){
			Node product = nodeList.item(productCount);
			if (product.getNodeType() == Node.ELEMENT_NODE) { 
				Map<String, String> productDetails = getCjProductDetails((Element)product);
				if(productDetails.get(Constants.CJ_PRODUCT_CATALOG_ID).equals("cjo:221")){
					productList.add(productDetails);
				}
			}
		}
		return productList;
	}
	
	public static Document getCjXmlDoc(String xmlFileName){
		Document doc = null;
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFileName);
			doc.getDocumentElement().normalize();
		}catch(Exception e){
			
		}
		return doc;
	}
	
	public static void callCjRestApi(Map<String, String> requestParam){
		int statusCode = 0;    
        FileOutputStream f = null;
        HttpClient client = new HttpClient();
		StringBuilder urlBuilder = new StringBuilder(Constants.CJ_REST_URL);
		for(Map.Entry<String,String> param: requestParam.entrySet()){
			if(!param.getValue().equals("")){
				urlBuilder.append("&"+param.getKey());
				urlBuilder.append("="+param.getValue());
			}
		}
		Logger.getLogger(ResponseParser.class).debug("CJ URL : " + urlBuilder);
		System.out.println("URL : " + urlBuilder);
        GetMethod method = new GetMethod(urlBuilder.toString());
        method.addRequestHeader("Authorization", Constants.CJ_REST_DEVELOPER_KEY);
        method.addRequestHeader("Accept", "application/xml");
		try {
			statusCode = client.executeMethod(method);
			f = new FileOutputStream(new File(CJ_XML_FILE_NAME));
			f.write(method.getResponseBody());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
	}
}
