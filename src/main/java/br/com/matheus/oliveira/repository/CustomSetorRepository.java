package br.com.matheus.oliveira.repository;

import java.util.List;

import br.com.matheus.oliveira.model.vo.SetorVO;

public interface CustomSetorRepository {
	public List<SetorVO> setorComColaboradores();
}
