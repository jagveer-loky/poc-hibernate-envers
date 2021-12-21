package com.fiserv.luc.api.domain.repository.brazil;

import com.fiserv.luc.api.domain.entity.brazil.BRPhysicPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BRPhysicPersonRepository extends JpaRepository<BRPhysicPerson, Long>  {
}
