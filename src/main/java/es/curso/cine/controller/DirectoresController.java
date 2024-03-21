package es.curso.cine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.curso.cine.controller.dto.DirectorDto;
import es.curso.cine.controller.dto.DirectorRecaudacionEntradasDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.service.DirectoresService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/api/directores")
@Api(tags = "Directores")
public class DirectoresController {
	
	@Autowired
	@Lazy
	private DirectoresService directoresServices;
	
	/*******************************GET*******************************/
	
	@ApiOperation(value = "Busca un director", notes = "Devuelve los datos de un director en base a su ID",nickname ="getDirector")
	@GetMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DirectorDto> getDirector(@RequestParam Long idDirector) {
		 
		DirectorDto respuesta = directoresServices.getDirector(idDirector);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Busca todos los directores", notes = "Devuelve los datos de todos los directores")
	@GetMapping(path = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResponseDto<DirectorDto>> getDirectores(@PageableDefault (page = 0, size = 5, sort = "numPeliculas", direction = Sort.Direction.ASC) Pageable pageable) {

		PagedResponseDto<DirectorDto> respuesta = directoresServices.getDirectores(pageable);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Muestra recaudacion por cada director y ordena de mayor a menor", notes = "Devuelve nombre y recaudacion por cada director")
	@GetMapping(path = "/recaudacion_directores",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DirectorRecaudacionEntradasDto>> recaudacionDirectores() {

		List<DirectorRecaudacionEntradasDto> respuesta = directoresServices.recaudacionDirectores();
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	/*******************************POST*******************************/
	
	@ApiOperation(value = "Crea  un director", notes = "Devuelve el id del nuevo director")
	@PostMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createDirector(@RequestBody @Validated DirectorDto director) throws MiValidationException {
		
		Long respuesta = directoresServices.createDirector(director);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	/*******************************PUT*******************************/
	
	@ApiOperation(value = "Actualiza  un director", notes = "Devuelve el id del director actualizado")	
	@PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> updatePelicula(@RequestBody @Validated DirectorDto director) {
	
		Long respuesta = directoresServices.updateDirector(director);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	/*******************************DELETE*******************************/
	
	@ApiOperation(value = "Borra  un director")
	@DeleteMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDirector(@RequestParam Long idDirector) {
	
		directoresServices.deleteDirector(idDirector);
	}
	
}
