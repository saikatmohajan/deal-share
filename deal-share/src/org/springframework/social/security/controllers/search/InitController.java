package org.springframework.social.security.controllers.search;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InitController {
	
	@RequestMapping("/productSearch")
	 public ModelAndView searchPage(@PathVariable String productType, @PathVariable String pageNumber){
		
		ModelAndView modelAndView = new ModelAndView("search");
		
		return  modelAndView;
	}

}
