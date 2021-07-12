package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.domain.dtos.*;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestScope
@RequestMapping(value = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "REPORT", description = "Resource with the requests that allow to query the information referring to pre proposal reports.")
public class ReportResource {

    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy";

    private static final Logger LOG = LogManager.getLogger(ReportResource.class);

    private final ReportService reportService;

    @Operation(
            summary = "Return the Report One Information",
            description = "Return the Report One Information",
            tags = "REPORT",
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
                            example = "2020-10-10"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "finalDate",
                            required = true,
                            description = "Filter Final Date",
                            example = "2021-09-11"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "in",
                            required = true,
                            description = "If true reponseType will filtered with clause in, else with clause not in",
                            example = "[true, false]"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "responsesTypes",
                            required = true,
                            description = "Filter Responses Types List",
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
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BasicReport.class)))
            )
    })
    @GetMapping(BasicReport.NAME)
    public List<BasicReport> getBasicReport(@RequestParam final String institution,
                                            @RequestParam final String serviceContract,
                                            @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                            @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                            @RequestParam final boolean in,
                                            @RequestParam final Set<String> responsesTypes,
                                            @RequestParam(required = false) final Set<String> status) {
        LOG.info("Get Report 1");
        return reportService.getBasicReport(format(institution), serviceContract, initialDate, finalDate, in, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    @Operation(
            summary = "Return the Report Two Information",
            description = "Return the Report Two Information",
            tags = "REPORT",
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
                            example = "2020-10-10"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "finalDate",
                            required = true,
                            description = "Filter Final Date",
                            allowEmptyValue = true,
                            example = "2021-09-11"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "in",
                            required = true,
                            description = "If true reponseType will filtered with clause in, else with clause not in",
                            example = "[true, false]"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "responsesTypes",
                            required = true,
                            description = "Filter Responses Types List",
                            example = "[FISERV_ONLINE,LEAD]"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "status",
                            required = true,
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
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = QuantitativeReport.class)))
            )
    })
    @GetMapping(QuantitativeReport.NAME)
    public List<QuantitativeReport> getQuantitativeReport(@RequestParam final String institution,
                                                          @RequestParam final String serviceContract,
                                                          @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                                          @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                                          @RequestParam final boolean in,
                                                          @RequestParam final Set<String> responsesTypes,
                                                          @RequestParam(required = false) final Set<String> status) {
        LOG.info("Get Report 2");
        return reportService.getQuantitativeReport(format(institution), serviceContract, initialDate, finalDate, in, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    @Operation(
            summary = "Return the Report Three Information",
            description = "Return the Report Three Information",
            tags = "REPORT",
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
                            example = "2020-10-10"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "finalDate",
                            required = true,
                            description = "Filter Final Date",
                            allowEmptyValue = true,
                            example = "2021-09-11"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "status",
                            required = true,
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
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DReport3.class)))
            )
    })
    @GetMapping(DReport3.NAME)
    public List<DReport3> getReport3(@RequestParam final String institution,
                                     @RequestParam final String serviceContract,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                     @RequestParam(required = false) final Set<String> status) {
        LOG.info("Get Report 3");
        return reportService.getReport3(format(institution), serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    @GetMapping(DReport4.NAME)
    public List<DReport4> getReport4(@RequestParam final String institution,
                                     @RequestParam final String serviceContract,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                     @RequestParam(required = false) final Set<String> status) {
        LOG.info("Get Report 4");
        return reportService.getReport4(format(institution), serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    @GetMapping(DReport5.NAME)
    public List<DReport5> getReport5(@RequestParam final String institution,
                                     @RequestParam final String serviceContract,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                     @RequestParam(required = false) final Set<String> status) {
        LOG.info("Get Report 5");
        return reportService.getReport5(format(institution), serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

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
                            example = "2020-10-10"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "finalDate",
                            required = true,
                            description = "Filter Final Date",
                            example = "2021-09-11"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "in",
                            required = true,
                            description = "If true reponseType will filtered with clause in, else with clause not in",
                            example = "[true, false]"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "responsesTypes",
                            required = true,
                            description = "Filter Responses Types List",
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
    public ResponseEntity<byte[]> getBasicCSVReport(@RequestParam final String institution,
                                                    @RequestParam final String serviceContract,
                                                    @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                                    @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                                    @RequestParam final boolean in,
                                                    @RequestParam final Set<String> responsesTypes,
                                                    @RequestParam(required = false) final Set<String> status) {
        System.out.println(institution);
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment;filename=" + BasicReport.NAME + ".csv")
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(reportService.getBasicCSVReport(format(institution), serviceContract, initialDate, finalDate, in, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status));
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
                            example = "2020-10-10"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "finalDate",
                            required = true,
                            description = "Filter Final Date",
                            allowEmptyValue = true,
                            example = "2021-09-11"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "in",
                            required = true,
                            description = "If true reponseType will filtered with clause in, else with clause not in",
                            example = "[true, false]"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "responsesTypes",
                            required = true,
                            description = "Filter Responses Types List",
                            example = "[FISERV_ONLINE,LEAD]"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "status",
                            required = true,
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
    public ResponseEntity<byte[]> getQuantitativeCSVReport(@RequestParam final String institution,
                                                           @RequestParam final String serviceContract,
                                                           @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                                           @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                                           @RequestParam final boolean in,
                                                           @RequestParam final Set<String> responsesTypes,
                                                           @RequestParam(required = false) final Set<String> status) {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment;filename=" + QuantitativeReport.NAME + ".csv")
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(reportService.getQuantitativeCSVReport(format(institution), serviceContract, initialDate, finalDate, in, (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes, (Objects.isNull(status) || status.isEmpty()) ? null : status));
    }


    @Operation(
            summary = "Return the Report Three CSV",
            description = "Return the Report Three CSV",
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
                            example = "2020-10-10"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "finalDate",
                            required = true,
                            description = "Filter Final Date",
                            allowEmptyValue = true,
                            example = "2021-09-11"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "status",
                            required = true,
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
    @GetMapping(DReport3.NAME + "/csv")
    public ResponseEntity<byte[]> geCSVtReport3(@RequestParam final String institution,
                                                @RequestParam final String serviceContract,
                                                @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                                @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                                @RequestParam(required = false) final Set<String> status) {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment;filename=" + DReport3.NAME + ".csv")
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(reportService.getCSVReport3(format(institution), serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status));
    }


    @Operation(
            summary = "Return the Report Four CSV",
            description = "Return the Report Four CSV",
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
                            example = "2020-10-10"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "finalDate",
                            required = true,
                            description = "Filter Final Date",
                            allowEmptyValue = true,
                            example = "2021-09-11"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "status",
                            required = true,
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
    @GetMapping(DReport4.NAME + "/csv")
    public ResponseEntity<byte[]> getCSVReport4(@RequestParam final String institution,
                                                @RequestParam final String serviceContract,
                                                @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                                @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                                @RequestParam(required = false) final Set<String> status) {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment;filename=" + DReport4.NAME + ".csv")
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(reportService.getCSVReport4(format(institution), serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status));
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
                            example = "2020-10-10"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "finalDate",
                            required = true,
                            description = "Filter Final Date",
                            allowEmptyValue = true,
                            example = "2021-09-11"
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "status",
                            required = true,
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
    @GetMapping(DReport5.NAME + "/csv")
    public ResponseEntity<byte[]> getCSVReport5(@RequestParam final String institution,
                                                @RequestParam final String serviceContract,
                                                @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                                @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                                @RequestParam(required = false) final Set<String> status) {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment;filename=" + DReport5.NAME + ".csv")
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(reportService.getCSVReport5(format(institution), serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status));
    }

    /**
     * @param value Object
     * @return String
     */
    public String format(final Object value) {
        return String.format("%0" + 8 + "d", Long.valueOf((String) value));
    }
}
