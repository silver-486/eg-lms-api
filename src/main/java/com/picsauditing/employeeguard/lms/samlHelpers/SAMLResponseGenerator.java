package com.picsauditing.employeeguard.lms.samlHelpers;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.saml2.core.*;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.signature.*;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;

import javax.naming.ConfigurationException;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.security.*;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

//import org.opensaml.xml.signature.PublicKey;

public abstract class SAMLResponseGenerator {

    private static XMLObjectBuilderFactory builderFactory = null;
    private String issuerId;
    private X509Certificate certificate;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    protected abstract Assertion buildAssertion(String idenityId) throws ConfigurationException;

    public SAMLResponseGenerator(X509Certificate certificate, PublicKey publicKey, PrivateKey privateKey, String issuerId) {
        this.certificate = certificate;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.issuerId = issuerId;
    }

    public String generateSAMLAssertionString(String identityId) throws UnrecoverableKeyException, InvalidKeyException, NoSuchAlgorithmException, CertificateException, KeyStoreException, NoSuchProviderException, SignatureException, MarshallingException, ConfigurationException, IOException, org.opensaml.xml.signature.SignatureException, UnmarshallingException {
        Response response = buildDefaultResponse(issuerId);

        Assertion assertion = buildAssertion(identityId);
        response.getAssertions().add(assertion);

        assertion = signObject(assertion, certificate, publicKey, privateKey);
        response = signObject(response, certificate, publicKey, privateKey);

        Element plaintextElement = marshall(response);

        return XMLHelper.nodeToString(plaintextElement);
    }

    @SuppressWarnings("unchecked")
    protected <T extends XMLObject> XMLObjectBuilder<T> getXMLObjectBuilder(QName qname)
            throws ConfigurationException {
        if (builderFactory == null) {
            // OpenSAML 2.3
            try {
                DefaultBootstrap.bootstrap();
            } catch (org.opensaml.xml.ConfigurationException e) {
                e.printStackTrace();
            }
            builderFactory = Configuration.getBuilderFactory();
        }
        return (XMLObjectBuilder<T>) builderFactory.getBuilder(qname);
    }

    protected <T extends XMLObject> T buildXMLObject(QName qname)
            throws ConfigurationException {
        XMLObjectBuilder<T> keyInfoBuilder = getXMLObjectBuilder(qname);
        return keyInfoBuilder.buildObject(qname);
    }

    protected Attribute buildStringAttribute(String name, String value)
            throws ConfigurationException {
        Attribute attrFirstName = buildXMLObject(Attribute.DEFAULT_ELEMENT_NAME);
        attrFirstName.setName(name);
        attrFirstName.setNameFormat(Attribute.UNSPECIFIED);

        // Set custom Attributes
        XMLObjectBuilder<XSString> stringBuilder = getXMLObjectBuilder(XSString.TYPE_NAME);
        XSString attrValueFirstName = stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
        attrValueFirstName.setValue(value);

        attrFirstName.getAttributeValues().add(attrValueFirstName);
        return attrFirstName;
    }

    private <T extends XMLObject> Element marshall(T object) throws MarshallingException {
        return Configuration.getMarshallerFactory().getMarshaller(object).marshall(object);
    }

    @SuppressWarnings("unchecked")
    private <T extends XMLObject> T unmarshall(Element element) throws MarshallingException, UnmarshallingException {
        return (T) Configuration.getUnmarshallerFactory().getUnmarshaller(element).unmarshall(element);
    }

    protected <T extends SignableSAMLObject> T signObject(T object, X509Certificate certificate, PublicKey publicKey, PrivateKey privateKey) throws MarshallingException, ConfigurationException, IOException, UnrecoverableKeyException, InvalidKeyException, NoSuchAlgorithmException, CertificateException, KeyStoreException, NoSuchProviderException, SignatureException, org.opensaml.xml.signature.SignatureException, UnmarshallingException {
        BasicX509Credential signingCredential = new BasicX509Credential();
        signingCredential.setEntityCertificate(certificate);
        signingCredential.setPrivateKey(privateKey);
        signingCredential.setPublicKey(publicKey);

        KeyInfo keyInfo = buildXMLObject(KeyInfo.DEFAULT_ELEMENT_NAME);
        X509Data x509Data = buildXMLObject(X509Data.DEFAULT_ELEMENT_NAME);
        org.opensaml.xml.signature.X509Certificate x509Certificate = buildXMLObject(org.opensaml.xml.signature.X509Certificate.DEFAULT_ELEMENT_NAME);
        x509Certificate.setValue(Base64.encodeBytes(certificate.getEncoded()));
        x509Data.getX509Certificates().add(x509Certificate);
        keyInfo.getX509Datas().add(x509Data);

        Signature signature = buildXMLObject(Signature.DEFAULT_ELEMENT_NAME);
        signature.setSigningCredential(signingCredential);
        signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
        signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
        signature.setKeyInfo(keyInfo);

        object.setSignature(signature);

        Element element = marshall(object);
        Signer.signObject(signature);

        return unmarshall(element);
    }

    protected Response buildDefaultResponse(String issuerId) {
        try {
            DateTime now = new DateTime();

            // Create Status
            StatusCode statusCode = buildXMLObject(StatusCode.DEFAULT_ELEMENT_NAME);
            statusCode.setValue(StatusCode.SUCCESS_URI);

            Status status = buildXMLObject(Status.DEFAULT_ELEMENT_NAME);
            status.setStatusCode(statusCode);

            // Create Issuer
            Issuer issuer = buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
            issuer.setValue(issuerId);
            issuer.setFormat(Issuer.ENTITY);

            // Create the response
            Response response = buildXMLObject(Response.DEFAULT_ELEMENT_NAME);
            response.setIssuer(issuer);
            response.setStatus(status);
            response.setIssueInstant(now);
            response.setVersion(SAMLVersion.VERSION_20);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }
}