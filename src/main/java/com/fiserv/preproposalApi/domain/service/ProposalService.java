package com.fiserv.preproposalApi.domain.service;

import com.fiserv.preproposalApi.domain.dtos.DFilter;
import com.fiserv.preproposalApi.domain.dtos.DReport1;
import com.fiserv.preproposalApi.domain.dtos.DReport2;
import com.fiserv.preproposalApi.domain.dtos.DReport3;
import com.fiserv.preproposalApi.domain.dtos.DReport4;
import com.fiserv.preproposalApi.domain.dtos.DReport5;
import com.fiserv.preproposalApi.domain.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProposalService {

    private static final Logger LOG = LogManager.getLogger(ProposalService.class);

    private final ProposalRepository proposalRepository;

    public List<DReport1> getReport1(DFilter dFilter) {
        dFilter.validateFilter();
        return proposalRepository.getReport1(dFilter.getInstitution(),dFilter.getServiceContract(),dFilter.getInitialDate(),dFilter.getFinalDate(),dFilter.getStatus());
    }

    public List<DReport2> getReport2(DFilter dFilter) {
        //TODO Validar os filtros nas subqueries
        dFilter.validateFilter();
        return proposalRepository.getReport2(dFilter.getInstitution(),dFilter.getServiceContract(),dFilter.getInitialDate(),dFilter.getFinalDate(),dFilter.getStatus());
    }

    public List<DReport3> getReport3() {
        return proposalRepository.getReport3();
    }

    public List<DReport4> getReport4() {
        return proposalRepository.getReport4();
    }

    public List<DReport5> getReport5() {
        return proposalRepository.getReport5();
    }
}
