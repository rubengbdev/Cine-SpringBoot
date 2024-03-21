package es.curso.cine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import es.curso.cine.controller.dto.EspectadorEmisionDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.service.EspectadorEmisionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/espectador_emision")
@Api(tags = "EspectadoresEmision")
public class EspectadorEmisionController {

	@Autowired
	private EspectadorEmisionService espectadorEmisionesServices;
	
	/*******************************GET*******************************/
	
	@ApiOperation(value = "Busca un registro EspectadorEmision", notes = "Devuelve los datos de un registro EspectadorEmision en base a su ID")
	@GetMapping(path = "/espectador_emision",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EspectadorEmisionDto> getEspectadorEmision(@RequestParam Long idEspectadorEmision) {
		
		EspectadorEmisionDto respuesta = espectadorEmisionesServices.getEspectadorEmision(idEspectadorEmision);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Busca todos los registro EspectadorEmisiones", notes = "Devuelve los datos de todos los registro EspectadorEmisiones")
	@GetMapping(path = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResponseDto<EspectadorEmisionDto>> getEspectadorEmisiones(@PageableDefault (page = 0, size = 5, sort = "emision.emisionId.fecha", direction= Sort.Direction.ASC) Pageable pageable) {
		
		PagedResponseDto<EspectadorEmisionDto> respuesta = espectadorEmisionesServices.getEspectadorEmisiones(pageable);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	/*******************************POST*******************************/
	
	@ApiOperation(value = "Crea  un registro EspectadorEmision", notes = "Devuelve el id del nuevo registro EspectadorEmision")
	@PostMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createEspectadorEmision(@RequestBody @Validated EspectadorEmisionDto espectadorEmision) throws MiValidationException {
		
		Long respuesta = espectadorEmisionesServices.createEspectadorEmision(espectadorEmision);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	/*******************************PUT*******************************/
	
	@ApiOperation(value = "Actualiza  un registro EspectadorEmision", notes = "Devuelve el id del registro EspectadorEmision actualizado")	
	@PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> updateEspectadorEmision(@RequestBody @Validated EspectadorEmisionDto espectadorEmision) {
	
		Long respuesta = espectadorEmisionesServices.updateEspectadorEmision(espectadorEmision);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	/*******************************DELETE*******************************/
	
	@ApiOperation(value = "Borra  un registro EspectadorEmision")
	@DeleteMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEspectadorEmision(@RequestParam Long idEspectadorEmision) {
		
		espectadorEmisionesServices.deleteEspectadorEmision(idEspectadorEmision);
	}
	
	/*******************************GENERAR ALEATORIOS*******************************/

	@PutMapping(path = "/generar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> aleatorizar() throws MiValidationException {
	
		Boolean respuesta = espectadorEmisionesServices.generarEspectadorEmision();
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
}
