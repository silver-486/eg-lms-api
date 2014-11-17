package com.picsauditing.employeeguard.lms.samlHelpers;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.*;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.UUID;


public class SalesforceSAMLResponseGenerator extends SAMLResponseGenerator {

    private static final String SALESFORCE_LOGIN_URL = "https://login.salesforce.com?so=00Do0000000cu4b";
    private static final String SALESFORCE_AUDIENCE_URI = "https://saml.salesforce.com";
   // private static final Logger logger = LoggerFactory.getLogger(SalesforceSAMLResponseGenerator.class);
    private static final int maxSessionTimeoutInMinutes = 10;

    private String nameId;

    public SalesforceSAMLResponseGenerator(X509Certificate certificate, PublicKey publicKey, PrivateKey privateKey,
                                           String issuerId, String nameId) {
        super(certificate, publicKey, privateKey, issuerId);
        this.nameId = nameId;
    }

    @Override
    protected Assertion buildAssertion(String who) throws javax.naming.ConfigurationException {
        // Create the NameIdentifier
        NameID nameId = buildXMLObject(NameID.DEFAULT_ELEMENT_NAME);
        nameId.setFormat(NameID.UNSPECIFIED);
		nameId.setValue(who);
        // Create the SubjectConfirmation

        SubjectConfirmationData confirmationMethod = buildXMLObject(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
        DateTime notBefore = new DateTime();
        DateTime notOnOrAfter = notBefore.plusMinutes(maxSessionTimeoutInMinutes);
        confirmationMethod.setNotOnOrAfter(notOnOrAfter);
        confirmationMethod.setRecipient(SALESFORCE_LOGIN_URL);

        SubjectConfirmation subjectConfirmation = buildXMLObject(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
        subjectConfirmation.setMethod(SubjectConfirmation.METHOD_BEARER);
        subjectConfirmation.setSubjectConfirmationData(confirmationMethod);

        // Create the Subject
        Subject subject = buildXMLObject(Subject.DEFAULT_ELEMENT_NAME);

        subject.setNameID(nameId);
        subject.getSubjectConfirmations().add(subjectConfirmation);

        // Create Authentication Statement
		AuthnStatement authnStatement = buildXMLObject(AuthnStatement.DEFAULT_ELEMENT_NAME);
        DateTime now2 = new DateTime();
        authnStatement.setAuthnInstant(now2);
        authnStatement.setSessionNotOnOrAfter(now2.plus(maxSessionTimeoutInMinutes));

        AuthnContext authnContext = buildXMLObject(AuthnContext.DEFAULT_ELEMENT_NAME);

        AuthnContextClassRef authnContextClassRef = buildXMLObject(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
        authnContextClassRef.setAuthnContextClassRef(AuthnContext.UNSPECIFIED_AUTHN_CTX);

        authnContext.setAuthnContextClassRef(authnContextClassRef);
        authnStatement.setAuthnContext(authnContext);

		/**
		 * setting up uid attributeStatement

		AttributeStatement attributeStatement = buildXMLObject(AttributeStatement.DEFAULT_ELEMENT_NAME);
		Attribute uidAttribute = buildStringAttribute("Subject", "ssouser@pics.com");
        attributeStatement.getAttributes().add(uidAttribute);
		*/

        Audience audience = buildXMLObject(Audience.DEFAULT_ELEMENT_NAME);
        audience.setAudienceURI(SALESFORCE_AUDIENCE_URI);

        AudienceRestriction audienceRestriction = buildXMLObject(AudienceRestriction.DEFAULT_ELEMENT_NAME);
        audienceRestriction.getAudiences().add(audience);

        Conditions conditions = buildXMLObject(Conditions.DEFAULT_ELEMENT_NAME);
        conditions.setNotBefore(notBefore);
        conditions.setNotOnOrAfter(notOnOrAfter);
        conditions.getConditions().add(audienceRestriction);


        // Create Issuer
        Issuer issuer = (Issuer) buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
        issuer.setValue(getIssuerId());

        // Create the assertion
        Assertion assertion = buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
        assertion.setIssuer(issuer);
        assertion.setID(UUID.randomUUID().toString());
        assertion.setIssueInstant(notBefore);
        assertion.setVersion(SAMLVersion.VERSION_20);

        assertion.getAuthnStatements().add(authnStatement);
        assertion.setConditions(conditions);
        assertion.setSubject(subject);

		//assertion.getAttributeStatements().add(attributeStatement);

		return assertion;
    }

}