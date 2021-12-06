package com.fiserv.preproposal.api.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Field {

    private String label;

    private Integer index;

    private Object value;

    /**
     * @param label String
     * @param index Integer
     */
    public Field(final String label, final Integer index) {
        this.label = label;
        this.index = index;
    }
}
