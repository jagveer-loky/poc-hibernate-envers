package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.repository.ReportRepository;
import com.fiserv.preproposal.api.infrastrucutre.io.IOService;
import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.JobScheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class ReportRepositoryTests {

    @Autowired
    ReportRepository reportRepository;

    /**
     *
     */
    @Test
    void teste() {
        System.out.println(reportRepository.findAll().size());
        // Instancing the jpa Entity to persist
        // This entity will save the percentage done of the job
        final EReport eReport = new EReport();
        eReport.setPath("getFullPath()");
        eReport.setOwner("ownerJob");
        eReport.setCountLines(123);
        reportRepository.save(eReport);
    }


}
