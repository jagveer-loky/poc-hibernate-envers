package com.fiserv.preproposta.api.domain.repository.git;

import com.fiserv.preproposta.api.application.pagination.DResponse;
import com.fiserv.preproposta.api.domain.dtos.GitRespDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IGitRepository {

    default DResponse<GitRespDTO> gitToDResponse(GitRespDTO git) {
        return DResponse.ok(git, "Sucesso.");
    }

}
