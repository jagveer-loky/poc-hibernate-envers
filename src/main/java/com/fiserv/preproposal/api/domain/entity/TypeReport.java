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

    public final static String BASIC_VALUE = "basic";
    public final static int BASIC_DIVISOR_TO_SAVE = 2;

    public final static String COMPLETE_VALUE = "complete";
    public final static int COMPLETE_DIVISOR_TO_SAVE = 2;

    public final static String QUANTITATIVE_VALUE = "quantitative";
    public final static int QUANTITATIVE_DIVISOR_TO_SAVE = 100;

    @Setter
    @Getter
    private Class type;

    TypeReport(final Class type) {
        this.type = type;
    }

    public String getName() {
        return this.type.getName().toLowerCase();
    }

    public int getDivisorToSave() {
        if (this.equals(BASIC))
            return BASIC_DIVISOR_TO_SAVE;
        else if (this.equals(COMPLETE))
            return COMPLETE_DIVISOR_TO_SAVE;
        else
            return QUANTITATIVE_DIVISOR_TO_SAVE;
    }
}
