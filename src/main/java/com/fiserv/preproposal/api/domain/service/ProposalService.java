package com.fiserv.preproposal.api.domain.service;

import com.fiserv.preproposal.api.domain.dtos.DFilter;
import com.fiserv.preproposal.api.domain.dtos.DReport1;
import com.fiserv.preproposal.api.domain.dtos.DReport2;
import com.fiserv.preproposal.api.domain.dtos.DReport3;
import com.fiserv.preproposal.api.domain.dtos.DReport4;
import com.fiserv.preproposal.api.domain.dtos.DReport5;
import com.fiserv.preproposal.api.domain.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProposalService {

    private final ProposalRepository proposalRepository;

    public List<DReport1> getReport1(final DFilter dFilter) {
        dFilter.validateFilter();
        return proposalRepository.getReport1(dFilter.getInstitution(),dFilter.getServiceContract(),dFilter.getInitialDate(),dFilter.getFinalDate(),dFilter.getStatus());
    }

    public List<DReport2> getReport2(final DFilter dFilter) {
        //TODO Validar os filtros nas subqueries
        dFilter.validateFilter();
        return proposalRepository.getReport2(dFilter.getInstitution(),dFilter.getServiceContract(),dFilter.getInitialDate(),dFilter.getFinalDate(),dFilter.getStatus());
    }

    public List<DReport3> getReport3(final DFilter dFilter) {
        dFilter.validateFilter();
        return proposalRepository.getReport3(dFilter.getInstitution(),dFilter.getServiceContract(),dFilter.getInitialDate(),dFilter.getFinalDate(),dFilter.getStatus());
    }

    public List<DReport4> getReport4(final DFilter dFilter) {
        dFilter.validateFilter();
        return proposalRepository.getReport4(dFilter.getInstitution(),dFilter.getServiceContract(),dFilter.getInitialDate(),dFilter.getFinalDate(),dFilter.getStatus());
    }

    public List<DReport5> getReport5(final DFilter dFilter) {
        dFilter.validateFilter();
        return proposalRepository.getReport5(dFilter.getInstitution(),dFilter.getServiceContract(),dFilter.getInitialDate(),dFilter.getFinalDate(),dFilter.getStatus());
    }
}
