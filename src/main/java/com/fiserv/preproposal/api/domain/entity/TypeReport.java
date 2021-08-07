package com.fiserv.preproposal.api.domain.entity;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import lombok.Getter;
import lombok.Setter;

public enum TypeReport {

    BASIC(BasicReport.class),
    COMPLETE(CompleteReport.class),
    QUANTITATIVE(QuantitativeReport.class);

    @Setter
    @Getter
    private Class type;

    TypeReport(final Class type) {
        this.type = type;
    }
}
