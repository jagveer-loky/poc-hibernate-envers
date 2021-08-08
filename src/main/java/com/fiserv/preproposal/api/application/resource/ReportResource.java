package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.domain.dtos.ReportParams;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import com.fiserv.preproposal.api.domain.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.BackgroundJob;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    @Value("${io.output}")
    private String path;

    private final ReportService reportService;

    @PostMapping(TypeReport.BASIC_VALUE)
    public Boolean createBasicReport(@RequestBody ReportParams reportParams) {

        reportParams.setType(TypeReport.BASIC);

        final EReport eReport = reportService.save(createEReportFromJobParams(reportParams));

        BackgroundJob.enqueue(() -> reportService.startBasicReport(reportParams, eReport));

        return true;
    }

    @PostMapping(TypeReport.COMPLETE_VALUE)
    public Boolean createCompleteReport(@RequestBody ReportParams reportParams) {

        reportParams.setType(TypeReport.COMPLETE);

        final EReport eReport = reportService.save(createEReportFromJobParams(reportParams));

        BackgroundJob.enqueue(() -> reportService.startCompleteReport(reportParams, eReport));

        return true;
    }

    @PostMapping(TypeReport.QUANTITATIVE_VALUE)
    public Boolean createQuantitativeReport(@RequestBody ReportParams reportParams) {

        reportParams.setType(TypeReport.QUANTITATIVE);

        final EReport eReport = reportService.save(createEReportFromJobParams(reportParams));

        BackgroundJob.enqueue(() -> reportService.startQuantitativeReport(reportParams, eReport));

        return true;
    }

    @GetMapping("{id}/download")
    public ResponseEntity<byte[]> downloadById(@PathVariable final Long id) throws IOException, NotFound {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment;filename=report.csv") // TODO ver necessidade do nome
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(reportService.downloadById(id));
    }

    @GetMapping
    public List<EReport> findByRequester(@RequestParam final String requester) {
        return reportService.findByRequester(requester);
    }

    public EReport createEReportFromJobParams(final ReportParams reportParams) {

        // Instancing the jpa Entity to persist
        // This entity will save the percentage done of the job
        final EReport eReport = new EReport();
        eReport.setPath((path + "/" + reportParams.getRequester() + "/" + reportParams.getType() + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_PATTERN))).toLowerCase());
        eReport.setType(reportParams.getType());
        eReport.setRequester(reportParams.getRequester());

        return eReport;
    }

    /**
     * @return
     */
    @GetMapping("fields")
    public HashMap<String, Set<String>> getFieldsFromReports() {
        return reportService.getFieldsFromReports();
    }
}
