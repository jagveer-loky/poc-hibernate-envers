package com.fiserv.luc.api.domain.entity.brazil;

import com.fiserv.luc.api.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

import static com.fiserv.luc.api.domain.entity.brazil.BRPhysicPerson.TABLE_NAME;

@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = Application.PREFIX + TABLE_NAME)
public class BRPhysicPerson extends BRPerson implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -54854612349107167L;

    /**
     *
     */
    public static final String TABLE_NAME = "BR_PHYSIC_PERSON";

    /**
     *
     */
    @NotNull
    private Boolean pep;

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    private String fatherName;

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    private String motherName;

    /**
     * @param pep        Boolean
     * @param fatherName String
     * @param motherName String
     * @param name       String
     * @param birthDate  LocalDate
     */
    public BRPhysicPerson(final Boolean pep, final String fatherName, final String motherName, final String name, final LocalDate birthDate) {
        this.pep = pep;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.name = name;
        this.birthDate = birthDate;
    }
}
