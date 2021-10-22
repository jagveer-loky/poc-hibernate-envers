package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.application.exceptions.NotFoundException;
import com.fiserv.preproposal.api.domain.dtos.ReportParams;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import com.fiserv.preproposal.api.domain.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jobrunr.scheduling.BackgroundJob;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
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
    public final static String DATETIME_PATTERN = "ddMMyyyyHHmmss";

    /**
     *
     */
    private final ReportService reportService;

    /**
     * @param reportParams ReportParams
     * @return Boolean
     */
    @PostMapping(TypeReport.BASIC_VALUE)
    @Transactional
    public Boolean createBasicReport(@RequestBody final ReportParams reportParams) {

        reportParams.setType(TypeReport.BASIC);

        final EReport eReport = reportService.save(createEReportFromJobParams(reportParams));

        BackgroundJob.enqueue(() -> reportService.startBasicReport(reportParams, eReport));

        return true;
    }

    /**
     * @param reportParams ReportParams
     * @return Boolean
     */
    @PostMapping(TypeReport.COMPLETE_VALUE)
    public Boolean createCompleteReport(@RequestBody final ReportParams reportParams) {

        reportParams.setType(TypeReport.COMPLETE);

        final EReport eReport = reportService.save(createEReportFromJobParams(reportParams));

        BackgroundJob.enqueue(() -> reportService.startCompleteReport(reportParams, eReport));

        return true;
    }

    /**
     * @param reportParams ReportParams
     * @return Boolean
     */
    @PostMapping(TypeReport.QUANTITATIVE_VALUE)
    public Boolean createQuantitativeReport(@RequestBody final ReportParams reportParams) {

        reportParams.setType(TypeReport.QUANTITATIVE);

        final EReport eReport = reportService.save(createEReportFromJobParams(reportParams));

        BackgroundJob.enqueue(() -> reportService.startQuantitativeReport(reportParams, eReport));

        return true;
    }

    /**
     * @param id Long
     * @return ResponseEntity<byte [ ]>
     * @throws IOException
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

    /**
     * @param reportParams ReportParams
     * @return EReport
     */
    private EReport createEReportFromJobParams(final ReportParams reportParams) {

        // Instancing the jpa Entity to persist
        // This entity will save the percentage done of the job
        final EReport eReport = new EReport();
        eReport.setType(reportParams.getType());
        eReport.setRequester(reportParams.getRequester());

        return eReport;
    }
}
