package com.fiserv.preproposta.api.application.pagination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DPage<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer codigoRetorno;

	private String mensagem;
	
    private List<T> conteudo;

    private boolean lastPage;

    private int totalPages;

    private long totalItems;

    private int currentPage;

    @JsonIgnore
    public boolean isEmpty() {
        return conteudo == null || conteudo.isEmpty();
    }

    public Iterator<T> iterator() {
        return conteudo != null ? conteudo.iterator() : Collections.emptyIterator();
    }

    public void add(T item) {
        if (conteudo == null)
            conteudo = new ArrayList<>();

        conteudo.add(item);
    }
}
