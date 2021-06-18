package com.fiserv.preproposal.api.domain.repository.git;

import org.mapstruct.Mapper;

import com.fiserv.preproposal.api.domain.dtos.GitRespDTO;
import com.fiserv.preproposal.api.application.pagination.DResponse;

@Mapper(componentModel = "spring")
public interface IGitRepository {

    default DResponse<GitRespDTO> gitToDResponse(GitRespDTO git) {
        return DResponse.ok(git, "Sucesso.");
    }

}
