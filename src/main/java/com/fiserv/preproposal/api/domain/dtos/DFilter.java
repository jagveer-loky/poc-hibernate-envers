package com.fiserv.preproposal.api.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DFilter {

    private LocalDate initialDate;
    private LocalDate finalDate;
    private String institution;
    private String serviceContract;
    private List<String> status;


    public void validateFilter() {
        if (Objects.nonNull(status) && status.isEmpty()) {
            status = null;
        }

    }

    /**
     * @return String[]
     */
    public String[] getArrayStatus() {
        return status.toArray(new String[status.size()]);
    }

}
