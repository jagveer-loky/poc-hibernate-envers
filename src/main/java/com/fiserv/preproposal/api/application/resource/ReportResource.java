package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.application.exceptions.NotFoundException;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import com.fiserv.preproposal.api.domain.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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
     * @param eReport EReport
     * @return Long
     */
    @Transactional(timeout = 9999999)
    @PostMapping(TypeReport.BASIC_VALUE)
    public Long createBasicReport(@RequestBody final EReport eReport) {

        eReport.setType(TypeReport.BASIC);

        eReport.setCountLines(reportService.getCountBasicReport(eReport.getInstitution(), eReport.getServiceContract(), eReport.getInitialDate(), eReport.getFinalDate(), eReport.getNotIn(), eReport.getResponsesTypes(), eReport.getStatus()));

        reportService.save(eReport);

        execute(() -> reportService.createBasicReport(eReport));

        return eReport.getId();

    }

    /**
     * @param eReport EReport
     * @return Long
     */
    @Transactional(timeout = 9999999)
    @PostMapping(TypeReport.COMPLETE_VALUE)
    public Long createCompleteReport(@RequestBody final EReport eReport) {

        eReport.setType(TypeReport.COMPLETE);

        eReport.setCountLines(reportService.getCountCompleteReport(eReport.getInstitution(), eReport.getServiceContract(), eReport.getInitialDate(), eReport.getFinalDate(), eReport.getNotIn(), eReport.getResponsesTypes(), eReport.getStatus()));

        reportService.save(eReport);

        execute(() -> reportService.createCompleteReport(eReport));

        return eReport.getId();

    }

    /**
     * @param eReport EReport
     * @return Long
     */
    @Transactional(timeout = 9999999)
    @PostMapping(TypeReport.QUANTITATIVE_VALUE)
    public Long createQuantitativeReport(@RequestBody final EReport eReport) {

        eReport.setType(TypeReport.QUANTITATIVE);

        eReport.setCountLines(reportService.getCountQuantitativeReport(eReport.getInstitution(), eReport.getServiceContract(), eReport.getInitialDate(), eReport.getFinalDate(), eReport.getNotIn(), eReport.getResponsesTypes(), eReport.getStatus()));

        reportService.save(eReport);

        execute(() -> reportService.createQuantitativeReport(eReport));

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
     * @return HashMap<String, Collection < String>>
     */
    @GetMapping("pre-proposal-fields")
    public HashMap<String, Collection<String>> getFieldsFromReports() {
        return reportService.getFieldsFromReports();
    }

}