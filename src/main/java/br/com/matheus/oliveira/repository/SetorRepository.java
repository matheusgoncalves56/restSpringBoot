package br.com.matheus.oliveira.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.matheus.oliveira.model.Setor;

public interface SetorRepository extends CrudRepository<Setor, Long>, CustomSetorRepository{

}
