package com.picsauditing.employeeguard.lms;


import com.picsauditing.employeeguard.lms.samlHelpers.SalesforceSamlWorker;
import org.apache.http.client.utils.URLEncodedUtils;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.*;
import java.security.cert.CertificateException;

public class TestSSO {

    private static final Logger logger = LoggerFactory.getLogger(TestSSO.class);

    //Simple method for quick testing some code
    public static void main(String[] args) throws ConfigurationException, UnrecoverableKeyException, InvalidKeyException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, KeyStoreException, NoSuchProviderException, SignatureException, IOException, org.opensaml.xml.signature.SignatureException, URISyntaxException, UnmarshallingException, MarshallingException {
        String userId = "12345";
        String token = SalesforceSamlWorker.AuthorizeForApi();
        String result = null;
        try {
            String getparam = "SELECT Id , Response_Body__c , Status__c , External_ID__c From Request__c WHERE ( Status__c = 'Failed' OR Status__c = 'Completed' ) AND External_ID__c = '2312121'";
            String endpoint = String.format("https://test04-dev-ed.my.salesforce.com/services/data/v29.0/query/?q=%1$s", URLEncoder.encode(getparam, "UTF-8"));
            result = SalesforceSamlWorker.CallRestService("GET", endpoint, null, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug(result);
    }

}