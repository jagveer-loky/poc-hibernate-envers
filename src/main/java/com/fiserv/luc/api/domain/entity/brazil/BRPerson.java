package com.fiserv.luc.api.domain.entity.brazil;

import com.fiserv.luc.api.Application;
import com.fiserv.luc.api.domain.entity.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

import static com.fiserv.luc.api.domain.entity.brazil.BRPerson.TABLE_NAME;

@Data
@Entity
@Audited
@EqualsAndHashCode(callSuper = true)
@Table(name = Application.PREFIX + TABLE_NAME)
@Inheritance(strategy = InheritanceType.JOINED)
public class BRPerson extends Person implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -54871266695874567L;

    /**
     *
     */
    public static final String TABLE_NAME = "BR_PERSON";

}
