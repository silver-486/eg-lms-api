package com.picsauditing.employeeguard.lms;


import com.picsauditing.employeeguard.lms.samlHelpers.SalesforceSamlWorker;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.*;
import java.security.cert.CertificateException;

public class TestSSO {

    private static final Logger logger = LoggerFactory.getLogger(TestSSO.class);
    private static String testJson = "{\'id\':\'123456789\',\'payload\':[{\'id\':1,\'Command\':\'addUser\',\'data\':{\'AccountIds\':\'585212415\',\'Type\':\'employee\',\'firstName\':\'John\',\'lastName\':\'Smith\',\'email\':\'JohnSmith@pics.com\',\'UserId\':\'123456789\',\'locale\':\'en_US\'}},{\'id\':2,\'Command\':\'addUser\',\'data\':{\'AccountIds\':\'585212415\',\'type\':\'employee\',\'firstName\':\'Steve\',\'lastName\':\'Lee\',\'email\':\'SteveLee@pics.com\',\'UserId\':\'923456789\',\'locale\':\'en_US\'}},{\'id\':3,\'Command\':\'addUser\',\'data\':{\'AccountIds\':\'213221212\',\'type\':\'employee\',\'firstName\':\'Tony\',\'lastName\':\'Dong\',\'email\':\'TonyDong@pics.com\',\'UserId\':\'2323322323\',\'locale\':\'en_US\'}},{\'id\':4,\'Command\':\'getLearningObjects\'},{\'id\':5,\'Command\':\'getAssignments\'}]}";

    public static void main(String[] args) throws ConfigurationException, UnrecoverableKeyException, InvalidKeyException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, KeyStoreException, NoSuchProviderException, SignatureException, IOException, org.opensaml.xml.signature.SignatureException, URISyntaxException, UnmarshallingException, MarshallingException {
        String userId = "12345";
        String assertion = new SalesforceSamlWorker().GetAssertion(userId);
        String token  = SalesforceSamlWorker.AuthorizeForApi();//(Base64.encodeBytes(assertion.getBytes("UTF-8")));
        String result = SalesforceSamlWorker.CallRestService("https://test04-dev-ed.my.salesforce.com/services/data/v32.0/sobjects/Account", "External_ID__c", "2312121", "Name", "Express Logistics and Transport", token);
        logger.debug(result);
    }

}