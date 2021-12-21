package com.fiserv.luc.api.application.isalive;

import com.fiserv.luc.api.infrastructure.pagination.DResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface GitMapper {

    default DResponse<GitRespDTO> gitToDResponse(GitRespDTO git) {
        return DResponse.ok(git, "Sucesso.");
    }

}
