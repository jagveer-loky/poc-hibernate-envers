package com.fiserv.preproposta.api.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiserv.preproposta.api.domain.entity.TypeReport;
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
public class ReportParams implements Serializable {

    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy";

    private String requester;
    private TypeReport type;
    private String institution;
    private String serviceContract;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDate initialDate;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDate finalDate;
    private Boolean notIn;
    private Collection<String> responsesTypes;
    private Collection<String> status;
    private Collection<String> fields;

    private Collection<String> fieldsToIgnore;

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
     * @return Class
     */
    public Class getBeanType() {
        return type.getType();
    }

    /**
     * @param value Object
     * @return String
     */
    public String format(final Object value) {
        return String.format("%0" + 8 + "d", Long.valueOf((String) value));
    }
}
