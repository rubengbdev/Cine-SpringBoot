package es.curso.cine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.curso.cine.controller.dto.FirstParametrosDto;
import es.curso.cine.controller.dto.FirstRespuestaDto;
import es.curso.cine.controller.dto.SecondParametrosDto;
import es.curso.cine.controller.dto.SecondRespuestaDto;
import es.curso.cine.service.EstadisticasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/estadisticas")
@Api(tags = "Estadisticas extra")
public class EstadisticasController {

	@Autowired
	private EstadisticasService estadisticasService;
	
	/*******************************GET*******************************/
	
	@ApiOperation(value = "Calculo de estadisticas 1", notes = "Muestra pelicula sen sala premium, directores por genero, ultima sala llena, emisiones por precio de la pelicula")
	@PostMapping(path = "/ejercicio1",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<FirstRespuestaDto> getEjercicio1(@RequestBody @Validated FirstParametrosDto parametros) {
	
		return  ResponseEntity.status(HttpStatus.OK).body(estadisticasService.getEjercicio1(parametros));
	
	}
	
	@ApiOperation(value = "Calculo de estadisticas 2", notes = "Directores con mas peliculas de X numero, ultimas emisiones por fecha, peliculas por genero y precio determinados, y emisiones por sala")
	@PostMapping(path = "/ejercicio2",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<SecondRespuestaDto> getEjercicio2(@RequestBody @Validated SecondParametrosDto parametros) {
	
		return  ResponseEntity.status(HttpStatus.OK).body(estadisticasService.getEjercicio2(parametros));
	
	}
}
