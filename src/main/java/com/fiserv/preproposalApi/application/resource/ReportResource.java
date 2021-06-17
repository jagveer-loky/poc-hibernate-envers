package com.fiserv.preproposalApi.application.resource;

import com.fiserv.preproposalApi.domain.dtos.DFilter;
import com.fiserv.preproposalApi.domain.dtos.DReport1;
import com.fiserv.preproposalApi.domain.dtos.DReport2;
import com.fiserv.preproposalApi.domain.dtos.DReport3;
import com.fiserv.preproposalApi.domain.dtos.DReport4;
import com.fiserv.preproposalApi.domain.dtos.DReport5;
import com.fiserv.preproposalApi.domain.service.ProposalService;
import com.fiserv.preproposalApi.domain.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestScope
@RequestMapping(value = "/preproposal/report", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReportResource {

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
    @PostMapping("/report1")
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
    @GetMapping(value = "/xls/report1"/*, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE*/)
    public ResponseEntity<byte[]> getReport1(@NotNull final String institution, @NotNull final String serviceContract, @NotNull final LocalDate initialDate, @NotNull final LocalDate finalDate, final String... status) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(reportService.getReport1(institution, serviceContract, initialDate, finalDate, status).toByteArray());
    }

    @PostMapping("/report2")
    public List<DReport2> getReport2(@RequestBody DFilter dFilter) {
        LOG.info("Get Report 2");
        return proposalService.getReport2(dFilter);
    }

    @PostMapping("/report3")
    public List<DReport3> getReport3() {
        LOG.info("Get Report 3");
        return proposalService.getReport3();
    }

    @PostMapping("/report4")
    public List<DReport4> getReport4() {
        LOG.info("Get Report 4");
        return proposalService.getReport4();
    }

    @PostMapping("/report5")
    public List<DReport5> getReport5() {
        LOG.info("Get Report 5");
        return proposalService.getReport5();
    }

}
