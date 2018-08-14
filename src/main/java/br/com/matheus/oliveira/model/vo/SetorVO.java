package br.com.matheus.oliveira.model.vo;

import java.util.ArrayList;
import java.util.List;

import br.com.matheus.oliveira.model.Setor;

public class SetorVO {
	private Long id;
	private String descricao;
	
	private List<ColaboradorVO> colaboradores;

	public SetorVO(Setor setor) {
		this.id = setor.getId();
		this.descricao = setor.getDescricao();
		if(setor.getColaboradores() != null) {
			this.colaboradores = new ArrayList<>();
			setor.getColaboradores().forEach(colaborador -> colaboradores.add(new ColaboradorVO(colaborador)));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<ColaboradorVO> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<ColaboradorVO> colaboradores) {
		this.colaboradores = colaboradores;
	}
}
