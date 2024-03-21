package es.curso.cine.controller;

import java.util.List;
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

import es.curso.cine.controller.dto.AcomodadorDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.service.AcomodadoresService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/acomodadores")
@Api(tags = "Acomodadores")
public class AcomodadorController {
	
	@Autowired
	private AcomodadoresService acomodadoresService;
	
	/*******************************GET*******************************/
	
	@ApiOperation(value = "Busca un acomodador", notes = "Devuelve los datos de un acomodador en base a su ID")
	@GetMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AcomodadorDto> getAcomodador(@RequestParam Long idAcomodador) {
		
		AcomodadorDto respuesta = acomodadoresService.getAcomodador(idAcomodador);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Busca todos los acomodadores", notes = "Devuelve los datos de todos los acomodadores")
	@GetMapping(path = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<AcomodadorDto>> getAcomodadores() {
		
		Set<AcomodadorDto> respuesta = acomodadoresService.getAcomodadores();
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	/*******************************POST*******************************/
	
	@ApiOperation(value = "Crea  un acomodador", notes = "Devuelve el id del nuevo acomodador")
	@PostMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createAcomodador(@RequestBody @Validated AcomodadorDto acomodador) throws MiValidationException {
	
		Long respuesta = acomodadoresService.createAcomodador(acomodador);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

	/*******************************PUT*******************************/
	
	@ApiOperation(value = "Actualiza  un acomodador", notes = "Devuelve el id del acomodador actualizado")	
	@PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> updateAcomodador(@RequestBody @Validated AcomodadorDto acomodador) {
		
		Long respuesta = acomodadoresService.updateAcomodador(acomodador);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Actualiza  valoracion de acomodador")	
	@PutMapping(path = "/actualizar_valoracion", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Long>> actualizarValoracionAcomodador() {

		List<Long> respuesta = acomodadoresService.actualizarValoracionAcomodador();
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);		
	}
	
	/*******************************DELETE*******************************/
	
	@ApiOperation(value = "Borra  un acomodador")
	@DeleteMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAcomodador(@RequestParam Long idAcomodador) {
	
		acomodadoresService.deleteAcomodador(idAcomodador);
	}

}
