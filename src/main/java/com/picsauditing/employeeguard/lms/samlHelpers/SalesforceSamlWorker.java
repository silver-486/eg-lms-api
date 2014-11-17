package com.picsauditing.employeeguard.lms.samlHelpers;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class SalesforceSamlWorker {

    private static final Logger logger = LoggerFactory.getLogger(SalesforceSamlWorker.class);

    private static String samlAuthApiUrl = "https://test04-dev-ed.my.salesforce.com/services/oauth2/token?so=00Do0000000cu4b";
    private static String samlAuthUrl = "https://login.salesforce.com?so=00Do0000000cu4b";

    private PrivateKey privateKey;
    private X509Certificate certificate;

    public static String sendSamlRequest(String samlAssertion) {
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

    public static String sendSamlRequestForApi(String samlAssertion) {
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

    public static String CallRestService(String endpoint, String data, String token) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(endpoint);
            httpPost.setEntity(new StringEntity(data, "UTF8"));
            httpPost.setHeader("Authorization", "Bearer " + token);
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpclient.execute(httpPost);

            InputStream body = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(body));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }

            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void readCertificate(InputStream inputStream, String alias, String password) throws NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException, KeyStoreException {
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

    public String GetAssertion(String username)  throws
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

        InputStream inputStream = SalesforceSamlWorker.class.getResourceAsStream("/PICS.jks");
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

        logger.debug("ASSERTION: {}",samlAssertion);
        return samlAssertion;
        //return sendSamlRequest(Base64.encodeBytes(samlAssertion.getBytes("UTF-8")));
    }
}