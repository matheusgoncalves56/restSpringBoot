package br.com.matheus.oliveira.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.matheus.oliveira.model.Setor;
import br.com.matheus.oliveira.model.vo.SetorVO;
import br.com.matheus.oliveira.repository.CustomSetorRepository;

public class CustomSetorRepositoryImpl implements CustomSetorRepository {
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<SetorVO> setorComColaboradores() {
		Query query = em.createQuery("select s from Setor s inner join fetch s.colaboradores");
		
		List<Setor> resultList = query.getResultList();
		if(resultList != null) {
			List<SetorVO> resultado = new ArrayList<>();
			resultList.forEach(setor -> resultado.add(new SetorVO(setor)));
			return resultado;	
		}
		
		return null;
	}

}
