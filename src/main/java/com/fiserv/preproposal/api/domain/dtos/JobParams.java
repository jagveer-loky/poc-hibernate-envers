package com.fiserv.preproposal.api.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobParams implements Serializable {

    private String institution;
    private String serviceContract;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private Boolean notIn;
    private Collection<String> responsesTypes;
    private Collection<String> status;
    private Collection<String> fields;

    /**
     * @return institution String
     */
    public String getInstitution() {
        return format(institution);
    }

    /**
     * @return Boolean
     */
    public Boolean getNotIn() {
        return !Objects.isNull(notIn) && notIn;
    }

    /**
     * @return responsesTypes Collection<String>
     */
    public Collection<String> getResponsesTypes() {
        return (Objects.isNull(responsesTypes) || responsesTypes.isEmpty()) ? null : responsesTypes;
    }

    /**
     * @return status Collection<String>
     */
    public Collection<String> getStatus() {
        return (Objects.isNull(status) || status.isEmpty()) ? null : status;
    }

    /**
     * @param value Object
     * @return String
     */
    public String format(final Object value) {
        return String.format("%0" + 8 + "d", Long.valueOf((String) value));
    }
}
