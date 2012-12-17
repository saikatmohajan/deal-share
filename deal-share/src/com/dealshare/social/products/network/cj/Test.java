package com.dealshare.social.products.network.cj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static void main(String[] args) throws HttpException, IOException {
		/*HttpClient client = new HttpClient();
        GetMethod method = new GetMethod("https://product-search.api.cj.com/v2/product-search?website-id=6423529&serviceable-area=US&page-number=1&keywords=Laptop");
        method.addRequestHeader("Authorization", "00820f48c12a5f23b8635dff30bab025f9a47199d497134be58cbe87107e365d31e85c657499c87d34c78c7dc602ed51ca953d7bf95ab3ac0f9f42da28c31bae1b/565fd82d90fa463abaa33e4b1a70c98795926c5b274a056999a31f07f10a21b17769422a746ead6d2f8be93b7e1399f1cd4db1e6c747ee24d19b419c6b302241");
        method.addRequestHeader("Accept", "application/xml");
        int statusCode = client.executeMethod(method);
        System.out.println(statusCode);
        System.out.println(method.getResponseBodyAsString());
        Header[] headers = method.getResponseHeaders();
        for(Header e : headers){
        	System.out.println(e.getName() + " : " + e.getValue());
        }
        FileOutputStream f = new FileOutputStream(new File("CJResult.xml"));  
        f.write(method.getResponseBody());*/ 
        ResponseParser.processxmlFile();
	}

}
