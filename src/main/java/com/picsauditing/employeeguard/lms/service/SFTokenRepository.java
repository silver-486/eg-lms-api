package com.picsauditing.employeeguard.lms.service;

import com.picsauditing.employeeguard.lms.model.dto.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: sergey.emelianov
 */
@Component
public class SFTokenRepository implements TokenRepository<Token,String> {

	private Map<String,Token> map = new HashMap<String, Token>();

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Token findForTenant(String tenantId) {
		Token token = map.get(tenantId);
		if ( token == null ) {
			log.error("no token exists for tenant {}", tenantId);
		}
		return token;
	}

	@Override
	public Token save(Token token) {
		map.put(readTenanatIdFromToken(token),token);
		return token;
	}


	private String readTenanatIdFromToken(final Token token) {
		String tenantId = null;
		Pattern pattern = Pattern.compile("id/(\\w+)\\/");
		Matcher matcher = pattern.matcher(token.getId());
		while(matcher.find()) {
			tenantId = matcher.group(1);
			log.debug("match found: {}", tenantId);
		}
		return tenantId;
	}
}
