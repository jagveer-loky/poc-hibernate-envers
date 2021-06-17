package com.fiserv.preproposalApi.application.resource;

import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiserv.preproposalApi.domain.dtos.MessageSourceDTO;
import com.fiserv.preproposalApi.infrastrucutre.aid.enums.ResponsesAndExceptionEnum;
import com.fiserv.preproposalApi.infrastrucutre.aid.MessageSourceUtil;
import com.fiserv.preproposalApi.domain.dtos.GitRespDTO;
import com.fiserv.preproposalApi.domain.repository.git.IGitRepository;
import com.fiserv.preproposalApi.application.pagination.DResponse;
import com.fiserv.preproposalApi.infrastrucutre.aid.DateUtil;

@RestController
@RequestMapping("/isAlive")
public class IsAliveResource {

	@Autowired
	private IGitRepository gitMapper;

	@Value("${git.commit.id.abbrev}")
	private String commitId;

	@Value("${git.build.time}")
	private String buildTime;

	@Value("${git.branch}")
	private String branch;
	
	@Value("${application.name}")
	private String apiName;
	
	@Value("${application.jenkins.build.version}")
	private String buildVersion;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> isAlive() {
		String now = DateUtil.dateTimeNowToString("dd/MM/yyyy HH:mm:ss", ZoneId.of("America/Sao_Paulo"));
		MessageSourceDTO requestTime = MessageSourceUtil.getProperties(ResponsesAndExceptionEnum.CONSULTA_REALIZADA, now);
		DResponse<GitRespDTO> resp = gitMapper.gitToDResponse(new GitRespDTO(apiName, commitId, branch, buildTime, buildVersion, requestTime.getMessage()));
		return new ResponseEntity<DResponse<GitRespDTO>>(resp, HttpStatus.OK);
	}

}
