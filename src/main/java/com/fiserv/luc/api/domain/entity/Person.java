package com.fiserv.luc.api.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiserv.luc.api.Application;
import com.fiserv.luc.api.infrastructure.database.audited.revision.AbstractEntity;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

import static com.fiserv.luc.api.domain.entity.Person.TABLE_NAME;

@Data
@Entity
@Audited(withModifiedFlag = true)
@lombok.EqualsAndHashCode(callSuper = true)
@Table(name = Application.PREFIX + TABLE_NAME)
@Inheritance(strategy = InheritanceType.JOINED)
public class Person extends AbstractEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -112345695623456789L;

    /**
     *
     */
    public static final String TABLE_NAME = "PERSON";

    /**
     *
     */
    @NotNull
    @NotEmpty
    @Column(nullable = false)
    protected String name;

    /**
     *
     */
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected LocalDate birthDate;

}
