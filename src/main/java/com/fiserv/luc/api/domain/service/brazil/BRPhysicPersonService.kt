package com.fiserv.luc.api.domain.service.brazil

import com.fiserv.luc.api.domain.entity.brazil.BRPhysicPerson
import com.fiserv.luc.api.domain.repository.brazil.BRPhysicPersonRepository
import com.fiserv.luc.api.infrastructure.database.audited.revision.AbstractRevisionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class BRPhysicPersonService(private val physicPersonRepository: BRPhysicPersonRepository) : AbstractRevisionService<BRPhysicPerson?>() {

    /**
     * @param filters  String
     * @param pageable Pageable
     * @return Page<BRPhysicPerson>
    </BRPhysicPerson> */
    fun listByFilters(filters: String?, pageable: Pageable?): Page<BRPhysicPerson> {
        return physicPersonRepository.findAll(pageable)
    }

    /**
     * @param id Long
     * @return Optional<BRPhysicPerson>
    </BRPhysicPerson> */
    fun findById(id: Long): Optional<BRPhysicPerson> {
        return physicPersonRepository.findById(id)
    }

    /**
     * @param physicPerson BRPhysicPerson
     * @return BRPhysicPerson
     */
    fun save(physicPerson: BRPhysicPerson): BRPhysicPerson {
        return physicPersonRepository.save(physicPerson)
    }

    /**
     * @param id Long
     */
    fun deleteById(id: Long) {
        physicPersonRepository.deleteById(id)
    }
}
