package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.application.exceptions.NotFoundException;
import com.fiserv.preproposal.api.domain.dtos.ReportParams;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import com.fiserv.preproposal.api.domain.service.report.ReportService;
import com.fiserv.preproposal.api.domain.service.report.ThreadService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@Transactional/*(readOnly = true)*/
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportResource {

    /**
     *
     */
    private final static String DATE_PATTERN = "dd/MM/yyyy";

    /**
     *
     */
    private final ReportService reportService;

    /**
     *
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     *
     */
    @Getter
    private final ExecutorService executorService = Executors.newFixedThreadPool(50);

    /**
     * @return Boolean
     */
    @DeleteMapping("/delete-expired")
    public Boolean deleteExpiredReports() {

        reportService.deleteBeforeYesterday();

        return true;
    }

    /**
     * @return Boolean
     */
    @GetMapping("/generate")
    public Boolean generate() {

        reportService.generateReports(LocalDate.now());

        return true;
    }

    /**
     * @param reportParams ReportParams
     * @return Long
     */
    @PostMapping(TypeReport.BASIC_VALUE)
    public Long createBasicReport(@RequestBody final ReportParams reportParams) {

        reportParams.setType(TypeReport.BASIC);

        final EReport eReport = reportService.save(EReport.createFrom(reportParams));

        eReport.setCountLines(reportService.getCountBasicReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

        executorService.execute(() -> reportService.createBasicReport(reportParams, reportService.save(eReport)));

        return eReport.getId();

    }



    /**
     * @param reportParams ReportParams
     * @return Long
     */
    @Transactional/*(readOnly = true)*/
    @PostMapping(TypeReport.COMPLETE_VALUE)
    public Long createCompleteReport(@RequestBody final ReportParams reportParams) {

        reportParams.setType(TypeReport.COMPLETE);

        final EReport eReport = reportService.save(EReport.createFrom(reportParams));

        eReport.setCountLines(reportService.getCountCompleteReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

        executorService.execute(() -> reportService.createCompleteReport(reportParams, reportService.save(eReport)));

        return eReport.getId();

    }

    /**
     * @param reportParams ReportParams
     * @return Long
     */
    @PostMapping(TypeReport.QUANTITATIVE_VALUE)
    public Long createQuantitativeReport(@RequestBody final ReportParams reportParams) {

        reportParams.setType(TypeReport.QUANTITATIVE);

        final EReport eReport = reportService.save(EReport.createFrom(reportParams));

        eReport.setCountLines(reportService.getCountQuantitativeReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

        executorService.execute(() -> reportService.createQuantitativeReport(reportParams, reportService.save(eReport)));

        return eReport.getId();
    }

    /**
     * @param id Long
     * @return ResponseEntity<byte [ ]>
     * @throws NotFoundException
     */
    @GetMapping("{id}/download")
    public ResponseEntity<byte[]> downloadById(@PathVariable final Long id, @RequestParam final Integer noCache) throws NotFoundException {

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.noCache())
                .body(reportService.downloadById(id));
    }

    /**
     * @param requester String
     * @return List<EReport>
     */
    @GetMapping
    public List<EReport> listByRequester(@RequestParam final String requester) {
        return reportService.findByRequester(requester);
    }

    /**
     * @return HashMap<String, Set < String>>
     */
    @GetMapping("pre-proposal-fields")
    public HashMap<String, Set<String>> getFieldsFromReports() {
        return reportService.getFieldsFromReports();
    }

}