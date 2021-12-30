package com.fiserv.luc.api.domain.service

import com.fiserv.luc.api.domain.entity.brazil.BRPhysicPerson
import com.fiserv.luc.api.domain.service.brazil.BRPhysicPersonService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDate

@SpringBootTest
internal class BRPhysicPersonServiceTests {
    /**
     *
     */
    @Autowired
    var physicPersonService: BRPhysicPersonService? = null

    /**
     *
     */
    @Test
    @Sql("/dataset/truncate-all-tables.sql")
    fun `Test Insert BRPhysicPerson Must Pass`() {
        val pep = false
        val fatherName = "Israel"
        val motherName = "Valdina"
        val name = "Emanuel"
        val birthDate = LocalDate.now().minusYears(32)
        val physicPerson = physicPersonService!!.save(BRPhysicPerson(pep, fatherName, motherName, name, birthDate))
        Assertions.assertThat(physicPerson).isNotNull
        Assertions.assertThat(physicPerson.id).isNotNull
        Assertions.assertThat(physicPerson.pep).isNotNull
        Assertions.assertThat(physicPerson.fatherName).isNotNull
        Assertions.assertThat(physicPerson.motherName).isNotNull
        Assertions.assertThat(physicPerson.name).isNotNull
        Assertions.assertThat(physicPerson.birthDate).isNotNull
        Assertions.assertThat(pep).isEqualTo(physicPerson.pep)
        Assertions.assertThat(fatherName).isEqualTo(physicPerson.fatherName)
        Assertions.assertThat(motherName).isEqualTo(physicPerson.motherName)
        Assertions.assertThat(name).isEqualTo(physicPerson.name)
        Assertions.assertThat(birthDate).isEqualTo(physicPerson.birthDate)
    }
}
