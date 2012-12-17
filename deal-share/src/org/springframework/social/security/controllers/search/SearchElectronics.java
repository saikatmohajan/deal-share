package org.springframework.social.security.controllers.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dealshare.social.products.network.cj.Constants;
import com.dealshare.social.products.network.cj.ResponseParser;

@Controller
@RequestMapping("/product")
public class SearchElectronics {
	
	
	@RequestMapping("/{productType}/pn/{pageNumber}")
	 public @ResponseBody Map<String, List<Map<String, String>>> findLaptop(@PathVariable String productType, @PathVariable String pageNumber){
		Map<String, String> param = new HashMap<String, String>();
		param.put("keywords", productType);
		param.put("page-number", pageNumber);
			
		Map<String, List<Map<String, String>>> json = new HashMap<String, List<Map<String, String>>>();
		
		List<Map<String,String>> productList =  ResponseParser.getCjProductList(param);
		List<Map<String,String>> resultMetadata =  new ArrayList<Map<String,String>>();
		
		Element productsElement = (Element)ResponseParser.getCjXmlDoc(Constants.CJ_XML_FILE_NAME).getElementsByTagName("products").item(0);
		System.out.println("Inside find Laptop..Request : " + productType);
		ModelAndView modelAndView = new ModelAndView("search");
		modelAndView.addObject("productList", productList);		
		modelAndView.addObject("totalMatch",ResponseParser.getCjTotalMatch(productsElement));
		
		param.put("totalMatch", Long.toString(ResponseParser.getCjTotalMatch(productsElement)));
		resultMetadata.add(param);
		
		json.put("productList", productList);
		json.put("resultMetadata", resultMetadata);
		
		
				
		//return modelAndView;
		return  json;
	}
	
	@RequestMapping("/{productType}")
	 public @ResponseBody String findTotalMatch(@PathVariable String productType){
		Map<String, String> param = new HashMap<String, String>();
		param.put("keywords", productType);
		//param.put("page-number", pageNumber);
		List<Map<String,String>> productLlist =  ResponseParser.getCjProductList(param);
		
		Element productsElement = (Element)ResponseParser.getCjXmlDoc(Constants.CJ_XML_FILE_NAME).getElementsByTagName("products").item(0);
		String totalMatched = Long.toString(ResponseParser.getCjTotalMatch(productsElement));
		System.out.println("Total Matches : " + totalMatched);
		return  totalMatched;
	}
	
	
}