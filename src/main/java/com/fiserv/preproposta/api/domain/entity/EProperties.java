package com.fiserv.preproposta.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_PREPROPOSAL_PROPERTIES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EProperties {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "PROPERTY_NAME")
    private String Name;

    @NotNull
    @Column(name = "PROPERTY_VALUE")
    private Long value;

}
