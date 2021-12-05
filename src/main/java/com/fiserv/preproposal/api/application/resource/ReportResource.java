package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.application.exceptions.NotFoundException;
import com.fiserv.preproposal.api.domain.dtos.ReportParams;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import com.fiserv.preproposal.api.domain.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.fiserv.preproposal.api.application.runnable.ThreadsComponent.execute;


@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportResource {

    /**
     *
     */
    private final ReportService reportService;

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
    @Transactional(timeout = 9999999)
    @PostMapping(TypeReport.BASIC_VALUE)
    public Long createBasicReport(@RequestBody final ReportParams reportParams) {

        reportParams.setType(TypeReport.BASIC);

        final EReport eReport = reportService.save(EReport.createFrom(reportParams));

        eReport.setCountLines(reportService.getCountBasicReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

        execute(() -> reportService.createBasicReport(reportParams, reportService.save(eReport)));

        return eReport.getId();

    }

    /**
     * @param reportParams ReportParams
     * @return Long
     */
    @Transactional(timeout = 9999999)
    @PostMapping(TypeReport.COMPLETE_VALUE)
    public Long createCompleteReport(@RequestBody final ReportParams reportParams) {

        reportParams.setType(TypeReport.COMPLETE);

        final EReport eReport = reportService.save(EReport.createFrom(reportParams));

        eReport.setCountLines(reportService.getCountCompleteReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

        execute(() -> reportService.createCompleteReport(reportParams, reportService.save(eReport)));

        return eReport.getId();

    }

    /**
     * @param reportParams ReportParams
     * @return Long
     */
    @Transactional(timeout = 9999999)
    @PostMapping(TypeReport.QUANTITATIVE_VALUE)
    public Long createQuantitativeReport(@RequestBody final ReportParams reportParams) {

        reportParams.setType(TypeReport.QUANTITATIVE);

        final EReport eReport = reportService.save(EReport.createFrom(reportParams));

        eReport.setCountLines(reportService.getCountQuantitativeReport(reportParams.getInstitution(), reportParams.getServiceContract(), reportParams.getInitialDate(), reportParams.getFinalDate(), reportParams.getNotIn(), reportParams.getResponsesTypes(), reportParams.getStatus()));

        execute(() -> reportService.createQuantitativeReport(reportParams, reportService.save(eReport)));

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