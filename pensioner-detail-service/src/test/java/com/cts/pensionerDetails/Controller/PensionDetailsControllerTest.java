package com.cts.pensionerDetails.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
//import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.pensionerDetails.Exception.NotFoundException;
import com.cts.pensionerDetails.Model.BankDetails;
import com.cts.pensionerDetails.Model.PensionerDetails;
import com.cts.pensionerDetails.Service.PensionerDetailServiceImpl;
import com.cts.pensionerDetails.Util.DateUtil;
import com.cts.pensionerDetails.feign.AuthorisationClient;

import lombok.extern.slf4j.Slf4j;

/**
 * Test cases for the pensioner Details controller
 */
@WebMvcTest
@Slf4j
class PensionDetailsControllerTest {
	
	@Value("${errorMessage}")
	private String AADHAAR_NUMBER_NOT_FOUND;
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PensionerDetailServiceImpl service;
	
	@MockBean
	private AuthorisationClient authorisationClient;
	
	/**
	 * Test Case for test To Get Correct Pensioner Details From Controller
	 * 
	 * @throws Exception
	 * 
	 *
	 */
	
	@BeforeEach
	void setup() throws ParseException {

		// mock authorization microservice response
		when(authorisationClient.validate(ArgumentMatchers.anyString())).thenReturn(true);

	}
	
	
	@Test
	@DisplayName("Verify response after sending get request with valid Aadhar number /aadhar")
	void testToGetCorrectPensionerDetailsFromController() throws Exception {

		final String aadhaarNumber = "123456789012";
		final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
		PensionerDetails pensionerDetail = new PensionerDetails("Asha", DateUtil.parseDate("29-01-1999"),
				"PCASD1234Q", 27000, 10000, "self", new BankDetails("ICICI", 12345678, "private"));
		when(service.getPensionerDetailByAadhaarNumber(token,aadhaarNumber)).thenReturn(pensionerDetail);			
		mockMvc.perform(get("/pensionerDetailByAadhaar/{aadhaarNumber}", aadhaarNumber)
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", Matchers.equalTo("Asha")))
				.andExpect(jsonPath("$.pan",Matchers.equalTo("PCASD1234Q")))
				.andExpect(jsonPath("$.dateOfBirth", Matchers.equalTo("1999-01-29")))
				.andExpect(jsonPath("$.bank.accountNumber", Matchers.equalTo(12345678)));

	}

	/**
	 * Test Case for the Aadhaar Number Not In CSV File
	 * @throws Exception 
	 */
	@Test
	@DisplayName("Verify to get exception when aadhar number is not found")
	void testForAadharNumberNotInCsvFile() throws Exception {
		
		final String aadhaarNumber = "12345678888";
		final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
		when(service.getPensionerDetailByAadhaarNumber(ArgumentMatchers.any(),ArgumentMatchers.any()))
				.thenThrow(new NotFoundException(AADHAAR_NUMBER_NOT_FOUND));
				
		mockMvc.perform(get("/pensionerDetailByAadhaar/{aadhaarNumber}", aadhaarNumber)
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.message", Matchers.equalTo(AADHAAR_NUMBER_NOT_FOUND)));
	}

}
