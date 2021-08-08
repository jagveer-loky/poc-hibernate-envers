package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.domain.dtos.JobParams;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.entity.TypeReport;
import com.fiserv.preproposal.api.domain.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.BackgroundJob;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportResource {

    private final ReportService reportService;

    @PostMapping(TypeReport.BASIC_VALUE)
    public Boolean createBasicReport( @RequestBody JobParams jobParams) {
        jobParams.setType(TypeReport.BASIC);

        BackgroundJob.enqueue(() -> reportService.startBasicReport( jobParams, reportService.save(reportService.createEReportFromJobParams(jobParams))));

        return true;
    }

    @PostMapping(TypeReport.COMPLETE_VALUE)
    public Boolean createCompleteReport(@RequestBody JobParams jobParams) {
        jobParams.setType(TypeReport.COMPLETE);

        BackgroundJob.enqueue(() -> reportService.startCompleteReport(jobParams, reportService.save(reportService.createEReportFromJobParams(jobParams))));

        return true;
    }

    @PostMapping(TypeReport.QUANTITATIVE_VALUE)
    public Boolean createQuantitativeReport(@RequestBody JobParams jobParams) {
        jobParams.setType(TypeReport.QUANTITATIVE);

        BackgroundJob.enqueue(() -> reportService.startQuantitativeReport(jobParams, reportService.save(reportService.createEReportFromJobParams(jobParams))));

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
    public List<EReport> findByRequester(@RequestParam final String requester) throws IOException {
        return reportService.findByRequester(requester);
    }

//    @Operation(
//            summary = "Return the Report Two CSV",
//            description = "Return the Report Two CSV",
//            tags = "CSV",
//            parameters = {
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "institution",
//                            required = true,
//                            description = "Filter Institution",
//                            example = "00000007"
//                    ),
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "serviceContract",
//                            required = true,
//                            description = "Filter Service Contract",
//                            example = "149"
//                    ),
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "initialDate",
//                            required = true,
//                            description = "Filter Initial Date",
//                            example = "10/10/2020"
//                    ),
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "finalDate",
//                            required = true,
//                            description = "Filter Final Date",
//                            allowEmptyValue = true,
//                            example = "09/11/2021"
//                    ),
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "notIn",
//                            description = "If 'false' reponseType will filtered with clause 'not in', else with clause 'in'",
//                            example = "[true, false]"
//                    ),
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "responsesTypes",
//                            allowEmptyValue = true, description = "Filter Responses Types List",
//                            example = "[FISERV_ONLINE,LEAD]"
//                    ),
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "status",
//                            description = "Filter Status List",
//                            allowEmptyValue = true,
//                            example = "[PRE1,PRE2]"
//                    )
//            }
//    )
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Sucessful Operation",
//                    content = @Content(schema = @Schema(type = "string", format = "binary"))
//            )
//    })
//    @GetMapping(QuantitativeReport.NAME + "/csv")
//    public Boolean getQuantitativeCSVReport(@RequestParam final String institution,
//                                            @RequestParam final String serviceContract,
//                                            @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
//                                            @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
//                                            @RequestParam(required = false) final Boolean notIn,
//                                            @RequestParam(required = false) final Set<String> responsesTypes,
//                                            @RequestParam(required = false) final Set<String> status,
//                                            @RequestParam(required = false) final Set<String> fields) {
//
//        final JobParams jobParams = JobParams.builder().institution(institution).serviceContract(serviceContract).initialDate(initialDate).finalDate(finalDate).notIn(notIn).responsesTypes(responsesTypes).status(status).fields(fields).build();
//
//        reportService.getAsyncQuantitativeCSVReport(UUID.randomUUID().toString(), jobParams);
//
//        return true;
//
////        return ResponseEntity
////                .ok()
////                .header("Content-Disposition", "attachment;filename=" + QuantitativeReport.NAME + ".csv")
////                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
////                .body(reportsService.getQuantitativeCSVReport(format(institution), serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status, fields));
//    }
//
//    @Operation(
//            summary = "Return the Report Five CSV",
//            description = "Return the Report Five CSV",
//            tags = "CSV",
//            parameters = {
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "institution",
//                            required = true,
//                            description = "Filter Institution",
//                            example = "00000007"
//                    ),
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "serviceContract",
//                            required = true,
//                            description = "Filter Service Contract",
//                            example = "149"
//                    ),
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "initialDate",
//                            required = true,
//                            description = "Filter Initial Date",
//                            example = "10/10/2020"
//                    ),
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "finalDate",
//                            required = true,
//                            description = "Filter Final Date",
//                            allowEmptyValue = true,
//                            example = "09/11/2021"
//                    ),
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "notIn",
//                            description = "If 'false' reponseType will filtered with clause 'not in', else with clause 'in'",
//                            example = "[true, false]"
//                    ),
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "responsesTypes",
//                            allowEmptyValue = true, description = "Filter Responses Types List",
//                            example = "[FISERV_ONLINE,LEAD]"
//                    ),
//                    @Parameter(
//                            in = ParameterIn.QUERY,
//                            name = "status",
//                            description = "Filter Status List",
//                            allowEmptyValue = true,
//                            example = "[PRE1,PRE2]"
//                    )
//            }
//    )
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Sucessful Operation",
//                    content = @Content(schema = @Schema(type = "string", format = "binary"))
//            )
//    })
//    @GetMapping(CompleteReport.NAME + "/csv")
//    public Boolean getCSVCompleteReport(@RequestParam final String institution,
//                                        @RequestParam final String serviceContract,
//                                        @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
//                                        @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
//                                        @RequestParam(required = false) final Boolean notIn,
//                                        @RequestParam(required = false) final Set<String> responsesTypes,
//                                        @RequestParam(required = false) final Set<String> status,
//                                        @RequestParam(required = false) final Set<String> fields) {
//
//        final JobParams jobParams = JobParams.builder().institution(institution).serviceContract(serviceContract).initialDate(initialDate).finalDate(finalDate).notIn(notIn).responsesTypes(responsesTypes).status(status).fields(fields).build();
//
//        reportService.getAsyncCompleteCSVReport(UUID.randomUUID().toString(), jobParams);
//
//        return true;
//
////        return ResponseEntity
////                .ok()
////                .header("Content-Disposition", "attachment;filename=" + CompleteReport.NAME + ".csv")
////                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
////                .body(reportsService.getCompleteCSVReport(format(institution), serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status, fields));
//    }


    /**
     * @return
     */
    @GetMapping("fields")
    public HashMap<String, Set<String>> getFieldsFromReports() {
        return reportService.getFieldsFromReports();
    }
}
