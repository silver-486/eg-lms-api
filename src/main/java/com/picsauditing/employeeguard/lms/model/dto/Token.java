package com.picsauditing.employeeguard.lms.model.dto;

import java.io.Serializable;

/**
 * Representation of token for SF API interaction
 *
 * @author: sergey.emelianov
 */
public class Token  implements Serializable {

    String accessToken;
    String refreshToken;
    String instance_url;
    String id;
    String issuedAt;
    String signature;


    public Token(String accessToken, String refreshToken, String instance_url, String id) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.instance_url = instance_url;
        this.id = id;
    }

    public Token() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getInstance_url() {
        return instance_url;
    }

    public void setInstance_url(String instance_url) {
        this.instance_url = instance_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (!accessToken.equals(token.accessToken)) return false;
        if (!id.equals(token.id)) return false;
        if (!instance_url.equals(token.instance_url)) return false;
        if (issuedAt != null ? !issuedAt.equals(token.issuedAt) : token.issuedAt != null) return false;
        if (!refreshToken.equals(token.refreshToken)) return false;
        if (signature != null ? !signature.equals(token.signature) : token.signature != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accessToken.hashCode();
        result = 31 * result + refreshToken.hashCode();
        result = 31 * result + instance_url.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + (issuedAt != null ? issuedAt.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        return result;
    }
}
