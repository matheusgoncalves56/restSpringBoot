package br.com.matheus.oliveira.model.vo;

import br.com.matheus.oliveira.model.Colaborador;

public class ColaboradorVO {
	private Long id;
	private String nome;
	private String email;
	
	public ColaboradorVO(Colaborador colaborador) {
		this.id = colaborador.getId();
		this.nome = colaborador.getNome();
		this.email = colaborador.getEmail();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
