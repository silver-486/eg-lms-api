package com.picsauditing.employeeguard.lms.samlHelpers;

import com.picsauditing.employeeguard.lms.model.dto.Token;
import com.picsauditing.employeeguard.lms.service.TokenService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.UnmarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Component
public class SFCommunicationFacade {

    private final Logger logger = LoggerFactory.getLogger(SFCommunicationFacade.class);
    private String samlAuthApiUrl = "https://test04-dev-ed.my.salesforce.com/services/oauth2/token?so=00Do0000000cu4b";
    private String samlAuthUrl = "https://login.salesforce.com?so=00Do0000000cu4b";

    private PrivateKey privateKey;
    private X509Certificate certificate;
    @Autowired
    private TokenService tokenService;


    public String sendSamlRequest(String samlAssertion) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //logger.debug("saml assertion:\n",samlAssertion);
            HttpPost httpPost = new HttpPost(samlAuthUrl);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.STRICT);
            builder.addPart("SAMLResponse", new StringBody(samlAssertion, org.apache.http.entity.ContentType.MULTIPART_FORM_DATA));
            HttpEntity entity = builder.build();

            httpPost.setEntity(entity);

            HttpResponse httpResponse = httpclient.execute(httpPost);

            Header location = httpResponse.getFirstHeader("Location");
            return location.getValue();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
            //logger.error("error executing: {}", e.getMessage());
        }
    }

    private final static String USERNAME = "ssouser@pics.com";
    private final static String PASSWORD = "Developer$VRP2";

    @Value("${APPLICATION_CLIENT_ID}")
    private String CONSUMER_KEY;

    @Value("${APPLICATION_CLIENT_SECRET}")
    private String CONSUMER_SECRET;

    private final static String ENP_POINT_URL = "https://test04-dev-ed.my.salesforce.com/services/oauth2/token";

    public String authorizeForApi() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(ENP_POINT_URL);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.STRICT);
            builder.addPart("grant_type", new StringBody("password", ContentType.TEXT_PLAIN));
            builder.addPart("client_id", new StringBody(CONSUMER_KEY, ContentType.TEXT_PLAIN));
            builder.addPart("client_secret", new StringBody(CONSUMER_SECRET, ContentType.TEXT_PLAIN));
            builder.addPart("username", new StringBody(USERNAME, ContentType.TEXT_PLAIN));
            builder.addPart("password", new StringBody(PASSWORD, ContentType.TEXT_PLAIN));
            HttpEntity entity = builder.build();

            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpclient.execute(httpPost);

            InputStream body = httpResponse.getEntity().getContent();


            BufferedReader reader = new BufferedReader(new InputStreamReader(body));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            logger.debug(out.toString());
            Header location = httpResponse.getFirstHeader("Location");

            if (null != location) {
                System.out.println(location.getValue());
            }
            String[] parts = out.toString().split(":");
            return parts[parts.length - 1].replace("\"", "").replace("}", "").replace("{", "");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error executing: {}", e.getMessage());
        } finally {
            //
        }
        return null;
    }

    /**
     * deprecated method for SAML-authorization
     */
    @Deprecated
    public String sendSamlRequestForApi(String samlAssertion) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(samlAuthApiUrl);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.STRICT);
            builder.addPart("grant_type", new StringBody("assertion", ContentType.TEXT_PLAIN));
            builder.addPart("assertion_type", new StringBody("urn:oasis:names:tc:SAML:2.0:profiles:SSO:browser", ContentType.TEXT_PLAIN));
            builder.addPart("assertion", new StringBody(samlAssertion, org.apache.http.entity.ContentType.MULTIPART_FORM_DATA));
            builder.addPart("format", new StringBody("json", ContentType.TEXT_PLAIN));
            HttpEntity entity = builder.build();

            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpclient.execute(httpPost);

            InputStream body = httpResponse.getEntity().getContent();


            BufferedReader reader = new BufferedReader(new InputStreamReader(body));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            logger.debug(out.toString());
            Header location = httpResponse.getFirstHeader("Location");

            if (null != location) {
                System.out.println(location.getValue());
            }
            String[] parts = out.toString().split(":");
            return parts[parts.length - 1].replace("\"", "").replace("}", "").replace("{", "");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error executing: {}", e.getMessage());
        } finally {
            //
        }
        return null;
    }

    public String sendRequest(String type, String endpoint, String[][] params, Token token) throws Exception {
        HttpRequestBase request;
        if (type.equals("GET")) {
            request = new HttpGet(endpoint);
        } else if (type.equals("POST")) {
            request = new HttpPost(endpoint);
            if (params != null && params.length > 0 && params[0][0] != null) {
                for (int i = 0; i < params.length; i++) {
                    String[] param = params[i];
                    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                    builder.setMode(HttpMultipartMode.STRICT);
                    builder.addPart(param[0], new StringBody(param[1], ContentType.TEXT_PLAIN));
                    HttpEntity entity = builder.build();
                    ((HttpPost) request).setEntity(entity);
                }
            } else if (params[0][0] == null) {
                HttpEntity entity = new StringEntity(params[0][1]);
                ((HttpPost) request).setEntity(entity);
            }
        } else {
            throw new Exception("Invalid request type (GET, POST allowed)");
        }
        request.setHeader("Authorization", "Bearer " + token.getAccessToken());
        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept", "application/json");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(request);
        Header[] headers = httpResponse.getAllHeaders();
        for( Header h : headers ) {
            logger.debug("header: {} {}", h.getName(), h.getValue());
        }
        String out = readResoponse(httpResponse);
        //TODO: check out for session invalid message
        //if access token is invalid,
        //Token newToken = tokenService.refreshToken(token);
        //reexecute call
        logger.debug("response: {}", out);
        if ( out.contains("INVALID_SESSION_ID")) {
            logger.warn("access token expired, refreshing the token");
            Token t2 = tokenService.refreshToken(token);
            request.setHeader("Authorization", "Bearer " + t2.getAccessToken());
            httpResponse = httpClient.execute(request);
            out = readResoponse(httpResponse);
        }
        return out;
    }

    /**
     * Helper to convert response body to a string
     * @param httpResponse
     * @return
     * @throws IOException
     */
    private String readResoponse(HttpResponse httpResponse) throws IOException {
        InputStream body = httpResponse.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(body));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        return  out.toString();
    }

    private void readCertificate(InputStream inputStream, String alias, String password) throws NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException, KeyStoreException {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, password.toCharArray());

        Key key = keyStore.getKey(alias, password.toCharArray());
        if (key == null) {
            throw new RuntimeException("Got null key from keystore!");
        }

        privateKey = (PrivateKey) key;
        certificate = (X509Certificate) keyStore.getCertificate(alias);
        if (certificate == null) {
            throw new RuntimeException("Got null cert from keystore!");
        }
    }

    public String readAssertion(String username) throws
            ConfigurationException,
            UnrecoverableKeyException,
            InvalidKeyException,
            NoSuchAlgorithmException,
            CertificateException,
            KeyStoreException,
            NoSuchProviderException,
            SignatureException, IOException,
            org.opensaml.xml.signature.SignatureException,
            URISyntaxException,
            UnmarshallingException,
            MarshallingException {
        String strIssuer = "http://adsl-static-233-50.tcm.by";
        String strNameID = username;//"ssouser@pics.com";

        InputStream inputStream = SFCommunicationFacade.class.getResourceAsStream("/PICS.jks");
        readCertificate(inputStream, "SSO", "123456");

        SAMLResponseGenerator responseGenerator = new SalesforceSAMLResponseGenerator(certificate, certificate.getPublicKey(), privateKey, strIssuer, strNameID);
        String samlAssertion = null;
        try {
            samlAssertion = responseGenerator.generateSAMLAssertionString(strNameID);
        } catch (javax.naming.ConfigurationException e) {
            e.printStackTrace();
            //logger.debug(e.getMessage());
        }

        org.apache.xml.security.Init inint = new org.apache.xml.security.Init();

        logger.debug("ASSERTION: {}", samlAssertion);
        return samlAssertion;
    }

}