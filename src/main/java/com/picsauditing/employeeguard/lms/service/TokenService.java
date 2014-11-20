package com.picsauditing.employeeguard.lms.service;

import com.picsauditing.employeeguard.lms.model.dto.Token;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Handles SF  Web Server OAuth Authentication Flow<br/>
 *
 * <a href="https://www.salesforce.com/us/developer/docs/api_rest/Content/intro_understanding_web_server_oauth_flow.htm">More Info</a>
 *
 * @author: sergey.emelianov
 */
@Component
public class TokenService {

    private static final String GRANT_TYPE = "grant_type";
    private static final String USER_NAME = "username";
    private static final String USER_PASSWORD = "password";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String REDIRECT_URI = "redirect_uri";
    private static final String CODE = "code";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String SF_OAUTH_TOKEN_URL = "http://login.salesforce.com/services/oauth2/token";
    private static final String FORMAT = "format";

    private static final String FL_ACCESS_TOKEN = "access_token";
    private static final String FL_REFRESH_TOKEN = "refresh_token";
    private static final String FL_INSTANCE_URL = "instance_url";
    private static final String FL_ID = "id";
    private static final String FL_ISSUED_AT = "issued_at";
    private static final String FL_SIGNATURE = "signature";

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${APPLICATION_CALLBACK_URI}")
    String redirectUri;

    @Value("${APPLICATION_CLIENT_ID}")
    String appClientId;

    @Value("${APPLICATION_CLIENT_SECRET}")
    String appClientSecret;

    @Autowired
    private TokenRepository<Token, String> tokenRepository;

    /**
     * Upon receiving a code from SF, obtains access_token
     * @param code
     */
    public JSONObject handleCodeReceive(final String code) {

        JSONObject response;

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String,String> () {{
            add(GRANT_TYPE,"authorization_code");
            add(CLIENT_ID, appClientId);
            add(CLIENT_SECRET, appClientSecret);
            add(CODE, code);
            add(REDIRECT_URI,redirectUri);
        }};

        HttpHeaders requestHeaders = new HttpHeaders();

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, requestHeaders);
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(SF_OAUTH_TOKEN_URL,HttpMethod.POST,requestEntity,String.class,params);
            log.debug("handleCodeReceive: {}", responseEntity.getBody());
            response = new JSONObject(responseEntity.getBody());
            tokenRepository.save(convertAuthorizationResponseToToken(response));
            return response;
        } catch (HttpStatusCodeException re) {
            log.error("error message: {} response: {}", re.getMessage(), re.getResponseBodyAsString());
            return null;
        } catch (JSONException e) {
            log.error("can't convert response to json.");
            return null;
        }
    }

    /**
     * Helper to convert SF response to token
     *
     * @param response
     * @return
     * @throws JSONException
     */
    Token convertAuthorizationResponseToToken(JSONObject response) throws JSONException {
        Token tkn = new Token();
        tkn.setAccessToken(response.getString(FL_ACCESS_TOKEN));
        tkn.setId(response.getString(FL_ID));
        tkn.setInstance_url(response.getString(FL_INSTANCE_URL));
        tkn.setRefreshToken(response.getString(FL_REFRESH_TOKEN));
        return tkn;
    }

    Token updateTokenByRefresh(Token tkn, JSONObject response) throws JSONException {

		/*
		{ "id":"https://login.salesforce.com/id/00Dx0000000BV7z/005x00000012Q9P",
				"issued_at":"1278448384422","instance_url":"https://na1.salesforce.com",
				"signature":"SSSbLO/gBhmmyNUvN18ODBDFYHzakxOMgqYtu+hDPsc=",
				"access_token":"00Dx0000000BV7z!AR8AQP0jITN80ESEsj5EbaZTFG0RNBaT1cyWk7TrqoDjoNIWQ2ME_sTZzBjfmOE6zMHq6y8PIW4eWze9JksNEkWUl.Cju7m4","token_type":"Bearer","scope":"id api refresh_token"
		}
		*/
        tkn.setAccessToken(response.getString(FL_ACCESS_TOKEN));
        tkn.setSignature(response.getString(FL_SIGNATURE));
        tkn.setIssuedAt(response.getString(FL_ISSUED_AT));
        return tkn;
    }

    /**
     * Refreshes authorization token
     *
     * @param token
     * @return
     */
    public Token refreshToken(final Token token) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String,String> () {{
            add(GRANT_TYPE,REFRESH_TOKEN);
            add(CLIENT_ID, appClientId );
            add(CLIENT_SECRET, appClientSecret);
            add(REFRESH_TOKEN, token.getRefreshToken());
            add(FORMAT, "json");
        }};

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(new ArrayList<MediaType>(1){{add(MediaType.APPLICATION_JSON);}});

        try {
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, requestHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange(SF_OAUTH_TOKEN_URL,HttpMethod.POST,requestEntity,String.class,params);
            //update the token in repository
            updateTokenByRefresh(token, new JSONObject(responseEntity.getBody()));
            tokenRepository.save(token);
            log.debug("token updated in repository");
        } catch ( HttpStatusCodeException re ) {
            re.printStackTrace();
            log.error("can't process refresh token request: {} ", re.getResponseBodyAsString());

        } catch (JSONException e) {
            log.error("can't convert refresh token from response", e.getMessage());
        }

        return token;
    }
}
