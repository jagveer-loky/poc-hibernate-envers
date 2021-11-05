package com.fiserv.preproposal.api.domain.repository.git;

import com.fiserv.preproposal.api.application.pagination.DResponse;
import com.fiserv.preproposal.api.domain.dtos.GitRespDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IGitRepository {

    default DResponse<GitRespDTO> gitToDResponse(GitRespDTO git) {
        return DResponse.ok(git, "Sucesso.");
    }

}
