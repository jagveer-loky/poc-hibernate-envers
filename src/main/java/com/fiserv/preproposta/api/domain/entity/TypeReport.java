package com.fiserv.preproposta.api.domain.entity;

import com.fiserv.preproposta.api.domain.dtos.BasicReport;
import com.fiserv.preproposta.api.domain.dtos.CompleteReport;
import com.fiserv.preproposta.api.domain.dtos.QuantitativeReport;
import lombok.Getter;
import lombok.Setter;

public enum TypeReport {

    BASIC(BasicReport.class),
    COMPLETE(CompleteReport.class),
    QUANTITATIVE(QuantitativeReport.class);

    public final static String BASIC_VALUE = "basic";
    public final static int BASIC_MULTIPLIER_TO_SAVE = 5;

    public final static String COMPLETE_VALUE = "complete";
    public final static int COMPLETE_MULTIPLIER_TO_SAVE = 5;

    public final static String QUANTITATIVE_VALUE = "quantitative";
    public final static int QUANTITATIVE_MULTIPLIER_TO_SAVE = 2;

    @Setter
    @Getter
    private Class type;

    TypeReport(final Class type) {
        this.type = type;
    }

    public String getName() {
        return this.type.getName().toLowerCase();
    }

    public int getMultiplierToSave() {
        if (this.equals(BASIC))
            return BASIC_MULTIPLIER_TO_SAVE;
        else if (this.equals(COMPLETE))
            return COMPLETE_MULTIPLIER_TO_SAVE;
        else
            return QUANTITATIVE_MULTIPLIER_TO_SAVE;
    }
}
