package com.fiserv.preproposal.api.application.resource;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneId;

import com.fiserv.preproposal.api.application.pagination.DResponse;
import com.fiserv.preproposal.api.infrastrucutre.aid.enums.ResponsesAndExceptionEnum;
import com.fiserv.preproposal.api.infrastrucutre.aid.util.DateUtil;
import com.fiserv.preproposal.api.infrastrucutre.aid.util.MessageSourceUtil;
import com.fiserv.preproposal.api.domain.dtos.GitRespDTO;
import com.fiserv.preproposal.api.domain.dtos.MessageSourceDTO;
import com.fiserv.preproposal.api.domain.repository.git.IGitRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/isAlive")
@Tag(name = "INFO", description = "Resource with the requests that allow to query API info.")
public class IsAliveResource {

	@Autowired
	private IGitRepository gitMapper;

	@Value("${git.commit.id}")
	private String commitId;

	@Value("${git.build.time}")
	private String buildTime;

	@Value("${git.branch}")
	private String branch;
	
	@Value("${application.name}")
	private String apiName;
	
	@Value("${application.jenkins.build.version}")
	private String buildVersion;

	@Operation(
			summary = "Get API Information",
			description = "Returns the information of the version of the API being executed.",
			tags = "INFO"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucessful Operation",content = @Content(schema = @Schema(implementation = GitRespDTO.class)))
	})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> isAlive() {
		String hostname = null;
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		final String now = DateUtil.dateTimeNowToString("dd/MM/yyyy HH:mm:ss", ZoneId.of("America/Sao_Paulo"));
		final MessageSourceDTO requestTime = MessageSourceUtil.getProperties(ResponsesAndExceptionEnum.CONSULTA_REALIZADA, now);
		final DResponse<GitRespDTO> resp = gitMapper.gitToDResponse(new GitRespDTO(apiName, commitId, branch, buildTime, buildVersion, requestTime.getMessage(), hostname));
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
