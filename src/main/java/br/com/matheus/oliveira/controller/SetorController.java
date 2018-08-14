package br.com.matheus.oliveira.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.matheus.oliveira.model.Setor;
import br.com.matheus.oliveira.model.vo.SetorVO;
import br.com.matheus.oliveira.repository.SetorRepository;

@Controller
@RequestMapping(path = "/setor")
public class SetorController {
	private static final Logger log = LoggerFactory.getLogger(SetorController.class);

	@Autowired
	private SetorRepository setorRepository;

	@RequestMapping(value = "/listarTodos", method = RequestMethod.GET)
	public ResponseEntity<?> listarTodos() {
		try {
			Iterable<Setor> setores = setorRepository.findAll();

			List<Setor> resultado = new ArrayList<>();
			setores.forEach(resultado::add);

			return new ResponseEntity<List<Setor>>(resultado, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/setorComColaboradores", method = RequestMethod.GET)
	public ResponseEntity<?> setorComColaboradores() {
		try {
			List<SetorVO> setores = setorRepository.setorComColaboradores();
			return new ResponseEntity<List<SetorVO>>(setores, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		try {
			Optional<Setor> setor = setorRepository.findById(id);
			if (setor.isPresent()) {
				return new ResponseEntity<Setor>(setor.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		try {
			setorRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> adicionar(@RequestBody Setor setor, UriComponentsBuilder ucBuilder) {
		try {
			Setor resultado = setorRepository.save(setor);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/setor/{id}").buildAndExpand(resultado.getId()).toUri());

			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
