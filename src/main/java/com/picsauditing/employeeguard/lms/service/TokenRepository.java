package com.picsauditing.employeeguard.lms.service;

import org.springframework.data.repository.Repository;

import java.io.Serializable;

/**
 * Stores and manages tokens
 *
 * @author: sergey.emelianov
 */
public interface TokenRepository<Token, String>{
	/**
	 * Obtains current token for tenant
	 * @param tenantId
	 * @return
	 */
	Token findForTenant(String tenantId);

	/**
	 * Saves token for tenant
	 * @param token
	 * @return
	 */
	Token save(Token token);
}
