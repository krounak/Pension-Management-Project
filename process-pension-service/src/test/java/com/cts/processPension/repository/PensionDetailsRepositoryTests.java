package com.cts.processPension.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.processPension.model.PensionerInput;
import com.cts.processPension.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Test class to test all the repository functionality
 *
 */
@SpringBootTest
@Slf4j
class PensionDetailsRepositoryTests {


	@Autowired
	private PensionerDetailsRepository pensionerDetailsRepository;



	@Test
	@DisplayName("This method is responsible to test save() for pensioner details")
	void testSaveForPensionerDetails() throws ParseException {
		log.info("START - testSaveForPensionerDetails()");

		PensionerInput pi_empty = new PensionerInput();
		PensionerInput pi = new PensionerInput("Shubham", DateUtil.parseDate("1999-02-02"), "BHPKN12931",
				"211228329912", "Self");

		PensionerInput savedDetails = pensionerDetailsRepository.save(pi);
		assertEquals(savedDetails.getAadhaarNumber(), pi.getAadhaarNumber());
		assertNotNull(savedDetails);
		
		log.info("END - testSaveForPensionerDetails()");
	}
}
