package com.fiserv.preproposalApi.application.pagination;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DResponse<T> {

	private Integer codigoRetorno;

	private String mensagem;

	@JsonInclude(Include.NON_NULL)
	private T conteudo;

    public static <T> DResponse<T> ok(T data, String message) {
        return new DResponse<>(0, message, data);
    }

    public static <T>  DResponse<T> notOk(String message) {
        return new DResponse<>(1, message, null);
    }
    
    public static <T>  DResponse<T> notOk(Integer codigo, String message) {
        if(null == codigo) {
            notOk(message);
        }
        return new DResponse<>(codigo, message, null);
    }
}