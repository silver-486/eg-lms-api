package com.picsauditing.employeeguard.lms.controller;

import com.picsauditing.employeeguard.lms.samlHelpers.SalesforceSamlWorker;
import org.opensaml.xml.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

@Controller
public class WelcomeController {

	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message);
		return "welcome";
	}

	@RequestMapping("/index")
	public String foo(Map<String, Object> model) {
		return "index";
	}

	private final static String testJson = "{\"id\":\"123456789\",\"payload\":[{\"id\":1,\"cmd\":\"addUser\",\"data\":{\"clientId\":\"585212415\",\"userType\":\"employee\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"email\":\"JohnSmith@pics.com\",\"employeeId\":\"123456789\",\"locale\":\"en-us\"}},{\"id\":2,\"cmd\":\"addUser\",\"data\":{\"clientId\":\"585212415\",\"userType\":\"employee\",\"firstName\":\"Steve\",\"lastName\":\"Lee\",\"email\":\"SteveLee@pics.com\",\"employeeId\":\"923456789\",\"locale\":\"en-us\"}},{\"id\":3,\"cmd\":\"addUser\",\"data\":{\"clientId\":\"213221212\",\"userType\":\"employee\",\"firstName\":\"Tony\",\"lastName\":\"Dong\",\"email\":\"TonyDong@pics.com\",\"employeeId\":\"2323322323\",\"locale\":\"en-us\"}},{\"id\":4,\"cmd\":\"getLearningObjects\"},{\"id\":5,\"cmd\":\"getAssignments\"}]}";
	//private final static String testJson = "{{\"request\":\"{\'id\':\'123456789\',\'payload\':[{\'id\':1,\'cmd\':\'addUser\',\'data\':{\'clientId\':\'585212415\',\'userType\':\'employee\',\'firstName\':\'John\',\'lastName\':\'Smith\',\'email\':\'JohnSmith@pics.com\',\'employeeId\':\'123456789\',\'locale\':\'en-us\'}},{\'id\':2,\'cmd\':\'addUser\',\'data\':{\'clientId\':\'585212415\',\'userType\':\'employee\',\'firstName\':\'Steve\',\'lastName\':\'Lee\',\'email\':\'SteveLee@pics.com\',\'employeeId\':\'923456789\',\'locale\':\'en-us\'}},{\'id\':3,\'cmd\':\'addUser\',\'data\':{\'clientId\':\'213221212\',\'userType\':\'employee\',\'firstName\':\'Tony\',\'lastName\':\'Dong\',\'email\':\'TonyDong@pics.com\',\'employeeId\':\'2323322323\',\'locale\':\'en-us\'}},{\'id\':4,\'cmd\':\'getLearningObjects\'},{\'id\':5,\'cmd\':\'getAssignments\'}]}\"}}";


	private String GetUserId(String userEmail) {
		if ("ssouser@pics.com".equals(userEmail)) return "12345";
		return null;
	}

	@RequestMapping("/hello")
	public String hello(Model model) {
		String link = new String();
		String assertion = new String();
		String userId = new String();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				userId = GetUserId(userDetails.getUsername());

				assertion = new SalesforceSamlWorker().GetAssertion(userId);
				link = SalesforceSamlWorker.sendSamlRequest(Base64.encodeBytes(assertion.getBytes("UTF-8")));
				String pageName = "Courses";
				String currentUrl = "http://localhost:8083/hello";
				String accountId = "585212415";
				link += "&retURL=apex/RedirectToClient?pname=" + pageName + "?rUrl=" + currentUrl+"?accId="+accountId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e.getMessage());
		}
		model.addAttribute("name", userId);
		model.addAttribute("link", link);
		return "hello";
	}


	@RequestMapping(value = "/getdata", method = RequestMethod.POST)
	public
	@ResponseBody
	String Data(@RequestBody String data, Model model) {
		String link = new String();
		String assertion = new String();
		String userId = new String();
		String result = new String();
		if (data == null || data.equals("")) data = testJson;
		try {
			data = java.net.URLDecoder.decode(data, "UTF-8");
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				userId = GetUserId(userDetails.getUsername());

				assertion = new SalesforceSamlWorker().GetAssertion(userId);
				String token = SalesforceSamlWorker.sendSamlRequestForApi(Base64.encodeBytes(assertion.getBytes("UTF-8")));
				result = SalesforceSamlWorker.CallRestService("https://test04-dev-ed.my.salesforce.com/services/apexrest/LMSData", data, token);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("api_result", result);
		model.addAttribute("name", userId);
		model.addAttribute("link", link);
		return result;
	}

}