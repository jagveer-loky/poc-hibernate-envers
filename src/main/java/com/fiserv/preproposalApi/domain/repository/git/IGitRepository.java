package com.fiserv.preproposalApi.domain.repository.git;

import org.mapstruct.Mapper;

import com.fiserv.preproposalApi.domain.dtos.GitRespDTO;
import com.fiserv.preproposalApi.application.pagination.DResponse;

@Mapper(componentModel = "spring")
public interface IGitRepository {

    default DResponse<GitRespDTO> gitToDResponse(GitRespDTO git) {
        return DResponse.ok(git, "Sucesso.");
    }

}
