package com.fiserv.preproposta.api.domain.repository;

import com.fiserv.preproposta.api.domain.entity.EProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertiesRepository extends JpaRepository<EProperties, Long>  {
}
