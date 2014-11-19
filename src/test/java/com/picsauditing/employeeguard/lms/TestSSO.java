package com.picsauditing.employeeguard.lms;


import com.picsauditing.employeeguard.lms.model.dto.Token;
import com.picsauditing.employeeguard.lms.service.SFCommunicationFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.UnmarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.*;
import java.security.cert.CertificateException;

@RunWith(MockitoJUnitRunner.class)
public class TestSSO {

    @Mock
    private SFCommunicationFacade sfCommunicationFacade = new SFCommunicationFacade();

    private final Logger logger = LoggerFactory.getLogger(TestSSO.class);

    //Simple method for quick testing some code
    @Test
    public void testFacade() throws ConfigurationException, UnrecoverableKeyException, InvalidKeyException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, KeyStoreException, NoSuchProviderException, SignatureException, IOException, org.opensaml.xml.signature.SignatureException, URISyntaxException, UnmarshallingException, MarshallingException {


        String userId = "12345";
        //String accessTokenStr = sfCommunicationFacade.authorizeForApi();
        String result = null;
        try {
            String getparam = "SELECT Id , Response_Body__c , Status__c , External_ID__c From Request__c WHERE ( Status__c = 'Failed' OR Status__c = 'Completed' ) AND External_ID__c = '2312121'";
            String endpoint = String.format("https://test04-dev-ed.my.salesforce.com/services/data/v29.0/query/?q=%1$s", URLEncoder.encode(getparam, "UTF-8"));
            Token token = new Token();
           // token.setAccessToken(accessTokenStr);
            result = sfCommunicationFacade.sendRequest("GET", endpoint, null, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug(result);
    }

}