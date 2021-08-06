package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.domain.dtos.*;
import com.fiserv.preproposal.api.domain.entity.EReport;
import com.fiserv.preproposal.api.domain.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RestController
@RequestScope
@RequiredArgsConstructor
@RequestMapping(value = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "REPORT", description = "Resource with the requests that allow to query the information referring to pre proposal reports.")
public class ReportResource {

    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy";

    private final ReportService reportService;

    @Operation(
            summary = "Return the Report One CSV",
            description = "Return the Report One CSV",
            tags = "CSV",
            parameters = {
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "institution",
                            required = true,
                            description = "Filter Institution",
                            example = "00000007"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "serviceContract",
                            required = true,
                            description = "Filter Service Contract",
                            example = "149"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "initialDate",
                            required = true,
                            description = "Filter Initial Date",
                            example = "10/10/2020"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "finalDate",
                            required = true,
                            description = "Filter Final Date",
                            allowEmptyValue = true,
                            example = "09/11/2021"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "notIn",
                            description = "If 'false' reponseType will filtered with clause 'not in', else with clause 'in'",
                            example = "[true, false]"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "responsesTypes",
                            description = "Filter Responses Types List",
                            allowEmptyValue = true,
                            example = "[FISERV_ONLINE,LEAD]"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "status",
                            description = "Filter Status List",
                            allowEmptyValue = true,
                            example = "[PRE1,PRE2]"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sucessful Operation",
                    content = @Content(schema = @Schema(type = "string", format = "binary"))
            )
    })
    @GetMapping(BasicReport.NAME + "/csv")
    public Boolean create(@RequestParam final String institution,
                          @RequestParam final String serviceContract,
                          @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                          @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                          @RequestParam(required = false) final Boolean notIn,
                          @RequestParam(required = false) final Set<String> responsesTypes,
                          @RequestParam(required = false) final Set<String> status,
                          @RequestParam(required = false) final Set<String> fields) {

        reportService.create(institution, serviceContract, initialDate, finalDate, notIn, responsesTypes, status, fields);

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
    public List<EReport> findByOwner(@RequestParam final String owner) throws IOException {
        return reportService.findByOwner(owner);
    }

    @Operation(
            summary = "Return the Report Two CSV",
            description = "Return the Report Two CSV",
            tags = "CSV",
            parameters = {
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "institution",
                            required = true,
                            description = "Filter Institution",
                            example = "00000007"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "serviceContract",
                            required = true,
                            description = "Filter Service Contract",
                            example = "149"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "initialDate",
                            required = true,
                            description = "Filter Initial Date",
                            example = "10/10/2020"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "finalDate",
                            required = true,
                            description = "Filter Final Date",
                            allowEmptyValue = true,
                            example = "09/11/2021"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "notIn",
                            description = "If 'false' reponseType will filtered with clause 'not in', else with clause 'in'",
                            example = "[true, false]"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "responsesTypes",
                            allowEmptyValue = true, description = "Filter Responses Types List",
                            example = "[FISERV_ONLINE,LEAD]"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "status",
                            description = "Filter Status List",
                            allowEmptyValue = true,
                            example = "[PRE1,PRE2]"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sucessful Operation",
                    content = @Content(schema = @Schema(type = "string", format = "binary"))
            )
    })
    @GetMapping(QuantitativeReport.NAME + "/csv")
    public Boolean getQuantitativeCSVReport(@RequestParam final String institution,
                                            @RequestParam final String serviceContract,
                                            @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                            @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                            @RequestParam(required = false) final Boolean notIn,
                                            @RequestParam(required = false) final Set<String> responsesTypes,
                                            @RequestParam(required = false) final Set<String> status,
                                            @RequestParam(required = false) final Set<String> fields) {

        final JobParams jobParams = JobParams.builder().institution(institution).serviceContract(serviceContract).initialDate(initialDate).finalDate(finalDate).notIn(notIn).responsesTypes(responsesTypes).status(status).fields(fields).build();

        reportService.getAsyncQuantitativeCSVReport(UUID.randomUUID().toString(), jobParams);

        return true;

//        return ResponseEntity
//                .ok()
//                .header("Content-Disposition", "attachment;filename=" + QuantitativeReport.NAME + ".csv")
//                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
//                .body(reportsService.getQuantitativeCSVReport(format(institution), serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status, fields));
    }

    @Operation(
            summary = "Return the Report Five CSV",
            description = "Return the Report Five CSV",
            tags = "CSV",
            parameters = {
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "institution",
                            required = true,
                            description = "Filter Institution",
                            example = "00000007"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "serviceContract",
                            required = true,
                            description = "Filter Service Contract",
                            example = "149"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "initialDate",
                            required = true,
                            description = "Filter Initial Date",
                            example = "10/10/2020"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "finalDate",
                            required = true,
                            description = "Filter Final Date",
                            allowEmptyValue = true,
                            example = "09/11/2021"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "notIn",
                            description = "If 'false' reponseType will filtered with clause 'not in', else with clause 'in'",
                            example = "[true, false]"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "responsesTypes",
                            allowEmptyValue = true, description = "Filter Responses Types List",
                            example = "[FISERV_ONLINE,LEAD]"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "status",
                            description = "Filter Status List",
                            allowEmptyValue = true,
                            example = "[PRE1,PRE2]"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sucessful Operation",
                    content = @Content(schema = @Schema(type = "string", format = "binary"))
            )
    })
    @GetMapping(CompleteReport.NAME + "/csv")
    public Boolean getCSVCompleteReport(@RequestParam final String institution,
                                        @RequestParam final String serviceContract,
                                        @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                        @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                        @RequestParam(required = false) final Boolean notIn,
                                        @RequestParam(required = false) final Set<String> responsesTypes,
                                        @RequestParam(required = false) final Set<String> status,
                                        @RequestParam(required = false) final Set<String> fields) {

        final JobParams jobParams = JobParams.builder().institution(institution).serviceContract(serviceContract).initialDate(initialDate).finalDate(finalDate).notIn(notIn).responsesTypes(responsesTypes).status(status).fields(fields).build();

        reportService.getAsyncCompleteCSVReport(UUID.randomUUID().toString(), jobParams);

        return true;

//        return ResponseEntity
//                .ok()
//                .header("Content-Disposition", "attachment;filename=" + CompleteReport.NAME + ".csv")
//                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
//                .body(reportsService.getCompleteCSVReport(format(institution), serviceContract, initialDate, finalDate, !Objects.isNull(notIn) && notIn, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status, fields));
    }


    /**
     * @return
     */
    @GetMapping("fields")
    public HashMap<String, Set<String>> getFieldsFromReports() {
        return reportService.getFieldsFromReports();
    }
}
