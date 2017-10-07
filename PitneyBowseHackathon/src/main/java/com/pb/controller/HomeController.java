package com.pb.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pb.services.ServiceTax;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		 ModelAndView model = new ModelAndView();
		
	//	ArrayList<String> taxList = (ArrayList<String>) ServiceTax.getTaxDetails();
    //    System.out.println("Size of tax list "+taxList.size());
	//	model.addObject("taxes", taxList);
		//model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/checkOut", method = RequestMethod.GET)
	public String checkOut(Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		 ModelAndView model = new ModelAndView();
		
	//	ArrayList<String> taxList = (ArrayList<String>) ServiceTax.getTaxDetails();
    //    System.out.println("Size of tax list "+taxList.size());
	//	model.addObject("taxes", taxList);
		//model.addAttribute("serverTime", formattedDate );
		
		return "checkOutPage";
	}
	
	@RequestMapping(value = "/proceessFormAddress", method = RequestMethod.GET)
	public String proceessFormAddress(Locale locale,HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		 ModelAndView model = new ModelAndView();
		
	//	ArrayList<String> taxList = (ArrayList<String>) ServiceTax.getTaxDetails();
    //    System.out.println("Size of tax list "+taxList.size());
	//	model.addObject("taxes", taxList);
		//model.addAttribute("serverTime", formattedDate );
		String address = request.getParameter("address");
		String sourceCountry = request.getParameter("sourceCountry");
		String destinationCountry = request.getParameter("destinationCountry");
		System.out.println("Address"+address+"\n SourceCountry"+sourceCountry+"\n DestinationCountry "+destinationCountry);
		HttpSession session = request.getSession();
		session.setAttribute("address", address);
		session.setAttribute("sourceCountry", sourceCountry);
		session.setAttribute("destinationCountry", destinationCountry);
		if(sourceCountry.equalsIgnoreCase(destinationCountry)) {
			System.out.println();
			return "geoTax";
		}
		
		return "getoTaxWithDuty";
	}
}
