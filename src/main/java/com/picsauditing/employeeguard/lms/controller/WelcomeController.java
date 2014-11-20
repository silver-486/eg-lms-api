package com.picsauditing.employeeguard.lms.controller;

import com.picsauditing.employeeguard.lms.model.dto.Token;
import com.picsauditing.employeeguard.lms.service.SFCommunicationFacade;
import com.picsauditing.employeeguard.lms.service.TokenRepository;
import com.picsauditing.employeeguard.lms.service.TokenService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.opensaml.xml.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

@Controller
public class WelcomeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SFCommunicationFacade sfCommunicationFacade;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenRepository<Token, String> tokenRepository;

    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @Value("${MOTHERSHIP_ORG_ID}")
    private String mothershipOrgId;

    /**
     * Temporary variable for saving item id after creating; just for demo
     */
    private String itemId;

    /**
     * Example of JSON data
     */
    private final static String testJson = "{\"External_ID__c\" : \"2312121\", \"Request_Body__c\" : \"{\'id\':\'123456789\',\'payload\':[{\'id\':1,\'Command\':\'addUser\',\'data\':{\'AccountIds\':\'585212415\',\'Type\':\'employee\',\'firstName\':\'John\',\'lastName\':\'Smith\',\'email\':\'JohnSmith@pics.com\',\'UserId\':\'123456789\',\'locale\':\'en_US\'}},{\'id\':2,\'Command\':\'addUser\',\'data\':{\'AccountIds\':\'585212415\',\'type\':\'employee\',\'firstName\':\'Steve\',\'lastName\':\'Lee\',\'email\':\'SteveLee@pics.com\',\'UserId\':\'923456789\',\'locale\':\'en_US\'}},{\'id\':3,\'Command\':\'addUser\',\'data\':{\'AccountIds\':\'213221212\',\'type\':\'employee\',\'firstName\':\'Tony\',\'lastName\':\'Dong\',\'email\':\'TonyDong@pics.com\',\'UserId\':\'2323322323\',\'locale\':\'en_US\'}},{\'id\':4,\'Command\':\'getLearningObjects\'},{\'id\':5,\'Command\':\'getAssignments\'}]}\"}";


    private String GetUserId(String userEmail) {
        if ("ssouser@pics.com".equals(userEmail)) return "12345";
        return null;
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping("/hello")
    public String hello(Model model, HttpServletRequest request) {
        String userId = "";
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                userId = GetUserId(userDetails.getUsername());
            }

			CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
			if (csrfToken != null) {
				model.addAttribute("_csrf", csrfToken);
			}

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("exception", e.getMessage());
        }


        model.addAttribute("name", userId);
        return "hello";
    }

    /**
     * Performs SSO on SF
     * @return
     */
    @RequestMapping(value = "/authAndGotoSF", method = RequestMethod.GET)
    public ModelAndView method() {
        String link = new String();
        String assertion;
        String userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                userId = GetUserId(userDetails.getUsername());

                assertion = sfCommunicationFacade.readAssertion(userId);
                link = sfCommunicationFacade.sendSamlRequest(Base64.encodeBytes(assertion.getBytes("UTF-8")));
                String pageName = "Courses";
                String currentUrl = "http://localhost:8083/hello";
                String accountId = "585212415";
                link += "&retURL=apex/RedirectToClient?pname=" + pageName + "?rUrl=" + currentUrl + "?accId=" + accountId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:" + link);

    }

    /**
     * Method for getting access and refresh tokens
     * @param code
     */
    @RequestMapping(value = "/callback")
    public String handleOauthCallback(Model model, @RequestParam("code") String code) {
        logger.debug("SF callback: {}", code);
        //Web Server Exchanges Verification Code for Access Token
		model.addAttribute("token",tokenService.handleCodeReceive(code));
        logger.info("new access token is provisioned and stored in token repository");
		return "callback";

    }


    @RequestMapping(value = "/createitem", method = RequestMethod.POST)
    public
    @ResponseBody
    String createItem(@RequestBody String data, Model model) {
        String result = new String();
        //FIXME: figure out where the = comes from
        data = data.substring(0, data.length() - 1);
        if (data == null || data.equals("")) data = testJson;
        try {
            logger.debug("loadData before encoding: " + data);
            data = java.net.URLDecoder.decode(data, "UTF-8");
            logger.debug("loadData after encoding: " + data);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                Token token = tokenRepository.findForTenant(mothershipOrgId);
                String endpoint = String.format("https://test04-dev-ed.my.salesforce.com/services/data/v29.0/sobjects/Request__c");
                String[][] params = new String[][]{{null, data}};
                result = sfCommunicationFacade.sendRequest("POST", endpoint, params, token);
                JSONObject json = (JSONObject) new JSONParser().parse(result);
                itemId = json.get("id").toString();
                result = String.format("%1$s: %2$s", json.get("id"), json.get("success"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getdata", method = RequestMethod.GET)
    public
    @ResponseBody
    String loadData(Model model) {
        String result = new String();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                Token token = tokenRepository.findForTenant(mothershipOrgId);
                try {
                    String getparam = "SELECT Id , Response_Body__c , Status__c , External_ID__c From Request__c WHERE ( Status__c = \'Failed\' OR Status__c = \'Completed\' ) AND Status__c != \'Sent To EG\'";
                    String endpoint = String.format("https://test04-dev-ed.my.salesforce.com/services/data/v29.0/query/?q=%1$s", URLEncoder.encode(getparam, "UTF-8"));
                    result = sfCommunicationFacade.sendRequest("GET", endpoint, null, token);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/updatedata", method = RequestMethod.POST)
    public
    @ResponseBody
    String updateData(@RequestBody String data, Model model) {
        String result = new String();
        //FIXME: figure out where the = comes from
        data = data.substring(0, data.length() - 1);
        if (data == null || data.equals("")) data = testJson;
        try {
            logger.debug("loadData before encoding: " + data);
            data = java.net.URLDecoder.decode(data, "UTF-8");
            logger.debug("loadData after encoding: " + data);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                Token token = tokenRepository.findForTenant(mothershipOrgId);
                String endpoint = String.format("https://test04-dev-ed.my.salesforce.com/services/data/v29.0/sobjects/Request__c/" + itemId);
                String[][] params = new String[][]{{null, data}};
                result = sfCommunicationFacade.sendRequest("PATCH", endpoint, params, token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     *
     */
    @RequestMapping(value = "/deletedata", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteData(Model model) {
        String result = new String();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                Token token = tokenRepository.findForTenant(mothershipOrgId);
                try {
                    String endpoint = String.format("https://test04-dev-ed.my.salesforce.com/services/data/v29.0/sobjects/Request__c/" + itemId);
                    result = sfCommunicationFacade.sendRequest("DELETE", endpoint, null, token);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Injects token into repository to allow local development
     */
    @RequestMapping(value = "/mocktoken", method = RequestMethod.GET)
    public void mockTokenSave() {
        /*
        aPrx1RSrhEAk35j.0TC9YvKqCCJcZDdQWLk4JqFhu14MOIBnHwjAZt.UB8nYQe1jpOpXlvwSSw==
2014-11-19T12:47:38.455535+00:00 app[web.1]: 12:47:38.455 [http-nio-32765-exec-7] DEBUG c.p.e.lms.service.TokenService - handleCodeReceive:
{"id":"https://login.salesforce.com/id/00Do0000000cu4bEAA/005o0000001FdIEAA0","issued_at":"1416401258405","scope":"api web refresh_token","i
nstance_url":"https://test04-dev-ed.my.salesforce.com","token_type":"Bearer","refresh_token":"5Aep861LNDQReieQSKqIzQvPZuV86FXXkAtJlXP_DpT2rxTgbuqSFlE8uo36Z0UE6gIhCFF.IRH9TbwggvNF3ht","signature":"Cyji+Txw6EKZtsZ5nAZ0BFVkWrw2eLvEQ8UoecCU/fM=","access_token":"00Do0000000cu4b!ARMAQKUCcIJg108MZJ2aHWf7zkIrsobHVD4RFbK4dbiODLMLUciW4qOGsNiMAwQCHMCrtjlZ1Sa8kJuMXQFf0dS0cMn2EWI3"}
         */
        Token tkn = new Token();
        tkn.setId("https://login.salesforce.com/id/00Do0000000cu4bEAA/005o0000001FdIEAA0");
        tkn.setAccessToken("00Do0000000cu4b!ARMAQKUCcIJg108MZJ2aHWf7zkIrsobHVD4RFbK4dbiODLMLUciW4qOGsNiMAwQCHMCrtjlZ1Sa8kJuMXQFf0dS0cMn2EWI334");
        //FIXME: this is for development purposes only, should be taken from running application
        tkn.setRefreshToken("5Aep861LNDQReieQSKqIzQvPZuV86FXXkAtJlXP_DpT2rxTgbsLeLK3GfWTNulOVBNMTHPlrwJ6HpmkM0S9wdWq");
        tokenRepository.save(tkn);
        logger.debug("dummy token is saved to repository");
    }

}