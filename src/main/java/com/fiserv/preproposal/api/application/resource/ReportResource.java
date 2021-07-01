package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.domain.dtos.*;
import com.fiserv.preproposal.api.domain.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class ReportResource {

    private static final String DATE_TIME_PATTERN = "dd-MM-yyyy";

    private static final Logger LOG = LogManager.getLogger(ReportResource.class);

    private final ReportService reportService;

    @Operation(
            summary = "Return the Report One Information",
            description = "Return the Report One Information",
            tags = "REPORT"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sucessful Operation",
                    content = @Content(schema = @Schema(implementation = DReport1[].class))
            )
    })
    @GetMapping(DReport1.NAME)
    public List<DReport1> getReport1(@RequestParam final String institution,
                                     @RequestParam final String serviceContract,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                     @RequestParam(required = false) final Set<String> status) {
        LOG.info("Get Report 1");
        return reportService.getReport1(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }


    @GetMapping(DReport2.NAME)
    public List<DReport2> getReport2(@RequestParam final String institution,
                                     @RequestParam final String serviceContract,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                     @RequestParam(required = false) final Set<String> status) {
        LOG.info("Get Report 2");
        return reportService.getReport2(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    @GetMapping(DReport3.NAME)
    public List<DReport3> getReport3(@RequestParam final String institution,
                                     @RequestParam final String serviceContract,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                     @RequestParam(required = false) final Set<String> status) {
        LOG.info("Get Report 3");
        return reportService.getReport3(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    @GetMapping(DReport4.NAME)
    public List<DReport4> getReport4(@RequestParam final String institution,
                                     @RequestParam final String serviceContract,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                     @RequestParam(required = false) final Set<String> status) {
        LOG.info("Get Report 4");
        return reportService.getReport4(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    @GetMapping(DReport5.NAME)
    public List<DReport5> getReport5(@RequestParam final String institution,
                                     @RequestParam final String serviceContract,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                     @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                     @RequestParam(required = false) final Set<String> status) {
        LOG.info("Get Report 5");
        return reportService.getReport5(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status);
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param status          String[]
     * @return ResponseEntity<byte [ ]>
     */
    @GetMapping(DReport1.NAME + "/csv")
    public ResponseEntity<byte[]> getCSVReport1(@RequestParam final String institution,
                                                @RequestParam final String serviceContract,
                                                @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                                @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                                @RequestParam(required = false) final Set<String> status) {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment;filename=" + DReport1.NAME + ".csv")
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(reportService.getCSVReport1(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status));
    }

    /**
     * @param institution     String
     * @param serviceContract String
     * @param initialDate     LocalDate
     * @param finalDate       LocalDate
     * @param status          String[]
     * @return ResponseEntity<byte [ ]>
     */
    @GetMapping(DReport2.NAME + "/csv")
    public ResponseEntity<byte[]> getCSVReport2(@RequestParam final String institution,
                                                @RequestParam final String serviceContract,
                                                @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate initialDate,
                                                @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) final LocalDate finalDate,
                                                @RequestParam(required = false) final Set<String> status) {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment;filename=" + DReport2.NAME + ".csv")
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(reportService.getCSVReport2(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status));
    }


    /**
     * @return ResponseEntity<byte [ ]>
     */
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
                .body(reportService.getCSVReport3(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status));
    }


    /**
     * @return ResponseEntity<byte [ ]>
     */
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
                .body(reportService.getCSVReport4(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status));
    }

    /**
     * @return ResponseEntity<byte [ ]>
     */
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
                .body(reportService.getCSVReport5(institution, serviceContract, initialDate, finalDate, (Objects.isNull(status) || status.isEmpty()) ? null : status));
    }

}
