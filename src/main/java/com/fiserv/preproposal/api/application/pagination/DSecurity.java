package com.fiserv.preproposal.api.application.pagination;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DSecurity implements Serializable {

	public DSecurity() {

	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4742283449140456003L;

	private Integer codigoRetorno;

	private String mensagem;

}
