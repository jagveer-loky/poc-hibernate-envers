package com.fiserv.preproposal.api.application.resource;

import com.fiserv.preproposal.api.domain.dtos.DFilter;
import com.fiserv.preproposal.api.domain.dtos.DReport1;
import com.fiserv.preproposal.api.domain.dtos.DReport2;
import com.fiserv.preproposal.api.domain.dtos.DReport3;
import com.fiserv.preproposal.api.domain.dtos.DReport4;
import com.fiserv.preproposal.api.domain.dtos.DReport5;
import com.fiserv.preproposal.api.domain.service.ProposalService;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestScope
@RequestMapping(value = "/preproposal/report", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReportResource {

    private static final String DATE_TIME_PATTERN = "dd-MM-yyyy";

    private static final Logger LOG = LogManager.getLogger(ReportResource.class);

    private final ReportService reportService;

    private final ProposalService proposalService;

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
    @PostMapping("/" + DReport1.NAME)
    public List<DReport1> getReport1(@RequestBody DFilter dFilter) {
        LOG.info("Get Report 1");
        return proposalService.getReport1(dFilter);
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
                .body(reportService.getReport1(institution, serviceContract, initialDate, finalDate, status));
    }

    @PostMapping("/" + DReport2.NAME)
    public List<DReport2> getReport2(@RequestBody DFilter dFilter) {
        LOG.info("Get Report 2");
        return proposalService.getReport2(dFilter);
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
                .body(reportService.getReport2(institution, serviceContract, initialDate, finalDate, status));
    }

    @PostMapping("/" + DReport3.NAME)
    public List<DReport3> getReport3() {
        LOG.info("Get Report 3");
        return proposalService.getReport3();
    }

    /**
     * @return ResponseEntity<byte [ ]>
     */
    @GetMapping(DReport3.NAME + "/csv")
    public ResponseEntity<byte[]> geCSVtReport3() {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment;filename=" + DReport3.NAME + ".csv")
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(reportService.getReport3());
    }

    @PostMapping("/" + DReport4.NAME)
    public List<DReport4> getReport4() {
        LOG.info("Get Report 4");
        return proposalService.getReport4();
    }

    /**
     * @return ResponseEntity<byte [ ]>
     */
    @GetMapping(DReport4.NAME + "/csv")
    public ResponseEntity<byte[]> getCSVReport4() {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment;filename=" + DReport4.NAME + ".csv")
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(reportService.getReport4());
    }

    @PostMapping("/" + DReport5.NAME)
    public List<DReport5> getReport5() {
        LOG.info("Get Report 5");
        return proposalService.getReport5();
    }

    /**
     * @return ResponseEntity<byte [ ]>
     */
    @GetMapping(DReport5.NAME + "/csv")
    public ResponseEntity<byte[]> getCSVReport5() {
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment;filename=" + DReport5.NAME + ".csv")
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(reportService.getReport5());
    }

}
