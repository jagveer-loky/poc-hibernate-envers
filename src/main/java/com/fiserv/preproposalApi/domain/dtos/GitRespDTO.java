package com.fiserv.preproposalApi.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GitRespDTO {
	private String apiName;
	private String commit;
	private String branch;
	private String buildTime;
	private String buildVersion;
	private String requestTime;
}
