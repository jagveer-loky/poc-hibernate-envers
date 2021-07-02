package com.fiserv.preproposal.api.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GitRespDTO {

	@Schema(
			description = "API Name",
			example = "pre-proposal-api",
			type = "String"
	)
	private String apiName;

	@Schema(
			description = "Git Commit Code",
			example = "ea2caebfd3925d18f0d727239eeeef3c530c2c9d",
			type = "String"
	)
	private String commit;

	@Schema(
			description = "Generated Branch",
			example = "master",
			type = "String"
	)
	private String branch;


	@Schema(
			description = "Build Generation Time",
			example = "02/07/2021 14:38",
			type = "String"
	)
	private String buildTime;

	@Schema(
			description = "Build Version",
			example = "1.0.0",
			type = "String"
	)
	private String buildVersion;

	@Schema(
			description = "Request Date",
			example = "Consulta realizada em 02/07/2021 14:41:26",
			type = "String"
	)
	private String requestTime;
}
