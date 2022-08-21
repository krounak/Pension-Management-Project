package com.cts.pensionerDetails.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.pensionerDetails.Exception.InvalidTokenException;
import com.cts.pensionerDetails.Model.PensionerDetails;
import com.cts.pensionerDetails.Service.PensionerDetailServiceImpl;
//import com.cts.processPension.feign.AuthorisationClient;
import com.cts.pensionerDetails.feign.AuthorisationClient;

import lombok.extern.slf4j.Slf4j;

/**
 *Pensioner Details Controller is to get the details of pensioner by
 *passing the Aadhaar Number
 */
@RestController
@Slf4j
@CrossOrigin
public class PensionerDetailsController {

	@Autowired
	private PensionerDetailServiceImpl pensionerDetailService;
	
	@Autowired
	AuthorisationClient authorisationClient;
	
	
	/**
	 * @URL: http://localhost:8083/api/pensioner-detail/pensionerDetailByAadhaar/123456789012
	 * 
	 * @return if Aadhaar Number then return the pensioner details else throws
	 *         Exception
	 * 
	 * @Expceted: {
				  "name": "Ananad",
				  "dateOfBirth": "1956-09-11T18:30:00.000+00:00",
				  "pan": "BHMER12436",
				  "salary": 27000,
				  "allowance": 10000,
				  "pensionType": "self",
				  "bank": {
				    "bankName": "ICICI",
				    "accountNumber": 12345678,
				    "bankType": "private"
				  }
				}
	 *
	 */

	@GetMapping("/pensionerDetailByAadhaar/{aadhaarNumber}")
	public PensionerDetails getPensionerDetailByAadhaar(@RequestHeader(name = "Authorization") String token , @PathVariable String aadhaarNumber) {
		log.info("START - getPensionerDetailByAadhaar()");
		if (!authorisationClient.validate(token)) {
			throw new InvalidTokenException("You are not allowed to access this resource");
		}
		return pensionerDetailService.getPensionerDetailByAadhaarNumber(token,aadhaarNumber);
	}

}
