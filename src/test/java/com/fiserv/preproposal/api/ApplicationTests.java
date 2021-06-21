package com.fiserv.preproposal.api;

import com.fiserv.preproposal.api.domain.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class ApplicationTests {

	@Autowired
	ReportService reportService;

	@Test
	void contextLoads() {
		reportService.getReport1( "00000007","149", LocalDate.now().minusDays(30), LocalDate.now(), null);
	}

}
