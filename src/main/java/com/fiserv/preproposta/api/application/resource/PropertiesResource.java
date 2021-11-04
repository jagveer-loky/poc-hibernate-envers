package com.fiserv.preproposta.api.application.resource;

import com.fiserv.preproposta.api.application.pagination.DResponse;
import com.fiserv.preproposta.api.domain.service.PropertiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestScope
@RequestMapping(value = "/properties", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "PROPERTIES", description = "Resource with the requests that allow to manipulate pre proprosal properties.")
public class PropertiesResource {

    private static final Logger LOG = LogManager.getLogger(PropertiesResource.class);

    private final PropertiesService propertiesService;

    @Operation(
            summary = "Update the NSA value",
            description = "Update the actual NSA value in the table TB_PREPROPOSAL_PROPERTIES, this parameter is used to generate the NSA (Número Sequencial do Arquivo) of the Response files.",
            tags = "PROPERTIES",
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "nsaValue",
                            required = true,
                            description = "New NSA value",
                            allowEmptyValue = true,
                            example = "12"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucessful Operation"),
            @ApiResponse(responseCode = "404", description = "Property Not Found", content = @Content(schema = @Schema(implementation = DResponse.class)))
    })
    @PostMapping("/nsa/{nsaValue}")
    public ResponseEntity<Void> setNsa(@PathVariable Long nsaValue) {
        propertiesService.setNsa(nsaValue);
        return ResponseEntity.ok().build();
    }
}
