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

import br.com.matheus.oliveira.model.Colaborador;
import br.com.matheus.oliveira.repository.ColaboradorRepository;

@Controller
@RequestMapping(path = "/colaborador")
public class ColaboradorController {

	private static final Logger log = LoggerFactory.getLogger(ColaboradorController.class);

	@Autowired
	private ColaboradorRepository colaboradorRepository;

	@RequestMapping(value = "/listarTodos", method = RequestMethod.GET)
	public ResponseEntity<?> listarTodos() {
		try {
			Iterable<Colaborador> colaboradores = colaboradorRepository.findAll();

			List<Colaborador> resultado = new ArrayList<>();
			colaboradores.forEach(resultado::add);

			return new ResponseEntity<List<Colaborador>>(resultado, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		try {
			Optional<Colaborador> colaborador = colaboradorRepository.findById(id);
			if (colaborador.isPresent()) {
				return new ResponseEntity<Colaborador>(colaborador.get(), HttpStatus.OK);
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
			colaboradorRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> adicionar(@RequestBody Colaborador colaborador, UriComponentsBuilder ucBuilder) {
		try {
			Colaborador resultado = colaboradorRepository.save(colaborador);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/colaborador/{id}").buildAndExpand(resultado.getId()).toUri());

			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
