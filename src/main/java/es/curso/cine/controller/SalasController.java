package es.curso.cine.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.curso.cine.controller.dto.SalaDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.service.SalasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/salas")
@Api(tags = "Salas")
public class SalasController {

	@Autowired
	private SalasService salasServices;

	/*******************************GET*******************************/
	
	@ApiOperation(value = "Busca un sala", notes = "Devuelve los datos de un sala en base a su ID")
	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SalaDto> getSala(@RequestParam Long idSala) {

		SalaDto respuesta = (salasServices.getSala(idSala));
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

	@ApiOperation(value = "Busca todos los salaes", notes = "Devuelve los datos de todos los salas")
	@GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<SalaDto>> getSalas() {

		Set<SalaDto> respuesta = salasServices.getSalas();
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

	/*******************************POST*******************************/
	
	@ApiOperation(value = "Crea  un sala", notes = "Devuelve el id del nuevo sala")
	@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createSala(@RequestBody @Validated SalaDto sala) throws MiValidationException {

		Long respuesta = salasServices.createSala(sala);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

	/*******************************PUT*******************************/
	
	@ApiOperation(value = "Actualiza  un sala", notes = "Devuelve el id del sala actualizado")
	@PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> updateSala(@RequestBody @Validated SalaDto sala) {

		Long respuesta = salasServices.updateSala(sala);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

	/*******************************DELETE*******************************/
	
	@ApiOperation(value = "Borra  un sala")
	@DeleteMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateSala(@RequestParam Long idSala) {

		salasServices.deleteSala(idSala);
	}

}
