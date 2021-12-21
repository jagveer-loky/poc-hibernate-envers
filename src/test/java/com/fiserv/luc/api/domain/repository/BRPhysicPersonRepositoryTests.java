package com.fiserv.luc.api.domain.repository;

import com.fiserv.luc.api.domain.entity.brazil.BRPhysicPerson;
import com.fiserv.luc.api.domain.repository.brazil.BRPhysicPersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
class BRPhysicPersonRepositoryTests {

    /**
     *
     */
    @Autowired
    BRPhysicPersonRepository physicPersonRepository;

    /**
     *
     */
    @Test
    @Sql({
            "/dataset/truncate-all-tables.sql"
    })
    public void insertBRPhysicPersonMustPass() {

        final boolean pep = false;
        final String fatherName = "Israel";
        final String motherName = "Valdina";

        final String name = "Emanuel";
        final LocalDate birthDate = LocalDate.now().minusYears(32);

        final BRPhysicPerson physicPerson = this.physicPersonRepository.save(new BRPhysicPerson(pep, fatherName, motherName, name, birthDate));

        Assertions.assertNotNull(physicPerson);
        Assertions.assertNotNull(physicPerson.getId());
        Assertions.assertNotNull(physicPerson.getPep());
        Assertions.assertNotNull(physicPerson.getFatherName());
        Assertions.assertNotNull(physicPerson.getMotherName());

        Assertions.assertNotNull(physicPerson.getName());
        Assertions.assertNotNull(physicPerson.getBirthDate());

        Assertions.assertEquals(pep, physicPerson.getPep());
        Assertions.assertEquals(fatherName, physicPerson.getFatherName());
        Assertions.assertEquals(motherName, physicPerson.getMotherName());

        Assertions.assertEquals(name, physicPerson.getName());
        Assertions.assertEquals(birthDate, physicPerson.getBirthDate());

    }

}
