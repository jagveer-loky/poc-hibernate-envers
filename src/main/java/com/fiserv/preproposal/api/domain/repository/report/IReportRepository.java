package com.fiserv.preproposal.api.domain.repository.report;


import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

public interface IReportRepository {

    ByteArrayOutputStream getReport1(@NotNull final String institution, @NotNull final String serviceContract, @NotNull final  LocalDate initialDate, @NotNull final LocalDate finalDate, final String... status);

    ByteArrayOutputStream getReport2(@NotNull final String institution, @NotNull final String serviceContract, @NotNull final  LocalDate initialDate, @NotNull final LocalDate finalDate, final String... status);

    ByteArrayOutputStream getReport3();

    ByteArrayOutputStream getReport4();

    ByteArrayOutputStream getReport5();
}
