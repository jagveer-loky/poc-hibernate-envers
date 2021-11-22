package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.application.exceptions.NotFoundException;
import com.fiserv.preproposal.api.domain.dtos.ReportParams;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import com.fiserv.preproposal.api.domain.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    public Boolean deleteExpired() {

        reportService.deleteExpired();

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

        BackgroundJob.enqueue(() -> reportService.startBasicReport(reportParams, eReport));

        return eReport.getId();
    }

    /**
     * @param reportParams ReportParams
     * @return Long
     */
    @PostMapping(TypeReport.COMPLETE_VALUE)
    public Long createCompleteReport(@RequestBody final ReportParams reportParams) {

        reportParams.setType(TypeReport.COMPLETE);

        final EReport eReport = reportService.save(EReport.createFrom(reportParams));

        BackgroundJob.enqueue(() -> reportService.startCompleteReport(reportParams, eReport));

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

        BackgroundJob.enqueue(() -> reportService.startQuantitativeReport(reportParams, eReport));

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