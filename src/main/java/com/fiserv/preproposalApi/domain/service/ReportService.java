package com.fiserv.preproposalApi.domain.service;

import com.fiserv.preproposalApi.domain.dtos.*;
import com.fiserv.preproposalApi.domain.repository.ProposalRepository;
import com.fiserv.preproposalApi.domain.repository.report.IReportRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final IReportRepository reportRepository;

    public ByteArrayOutputStream getReport1(@NotNull final String institution, @NotNull final String serviceContract, @NotNull final LocalDate initialDate, @NotNull final LocalDate finalDate, final String... status) {
        return reportRepository.getReport1(institution, serviceContract, initialDate, finalDate, status);
    }

}
