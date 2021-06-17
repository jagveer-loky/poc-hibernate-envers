package com.fiserv.preproposalApi.domain.repository.report.impl;

import com.fiserv.preproposalApi.domain.repository.report.IReportRepository;
import com.fiserv.preproposalApi.infrastrucutre.reports.IReportManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;


/**
 *
 */
@Component
@RequiredArgsConstructor
public class IReportRepositoryImpl implements IReportRepository {

    /**
     *
     */
    private static final String CRC_REPORT_PATH = "/reports/report1.jasper";

    /**
     *
     */
    private final IReportManager reportManager;

//    /**
//     * @return ByteArrayOutputStream
//     */
//    @Override
//    public ByteArrayOutputStream export() {
//        final Map<String, Object> parameters = new HashMap<>();
//
//        try {
//            final List<ByteArrayInputStream> list = new ArrayList<>();
//            final ByteArrayInputStream logo = new ByteArrayInputStream(IOUtils.toByteArray(getClass().getResource("/reports/crc/logo-pti.png")));
//            list.add(logo);
//            parameters.put("logo", list);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return this.reportManager.exportToPDF(parameters, CRC_REPORT_PATH);
//    }


    @Override
    public ByteArrayOutputStream getReport1(@NonNull final String institution, @NonNull final String serviceContract, @NonNull final  LocalDate initialDate, @NonNull final LocalDate finalDate, final String... status) {
        return null;
    }

    @Override
    public ByteArrayOutputStream getReport2(@NonNull final String institution, @NonNull final String serviceContract, @NonNull final  LocalDate initialDate, @NonNull final LocalDate finalDate, final String... status) {
        return null;
    }

    @Override
    public ByteArrayOutputStream getReport3() {
        return null;
    }

    @Override
    public ByteArrayOutputStream getReport4() {
        return null;
    }

    @Override
    public ByteArrayOutputStream getReport5() {
        return null;
    }
}

