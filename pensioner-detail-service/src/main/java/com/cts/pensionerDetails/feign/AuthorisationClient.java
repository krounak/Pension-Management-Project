package com.cts.pensionerDetails.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
/**
 * Feign client to connect with authorization micro-service for token validation
 *
 */
@FeignClient(name= "AUTH-SERVICE", url= "${AUTH-SERVICE_URI:http://localhost:8081}")
public interface AuthorisationClient {
	
	/**
	 * method to validate jwt token
	 * @param token
	 * @return true only if token is valid else false
	 * 
	 */
	@GetMapping("/api/auth/validate")
	public boolean validate(@RequestHeader(name = "Authorization") String token);

}
