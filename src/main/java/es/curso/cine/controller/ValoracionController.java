package es.curso.cine.controller;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.curso.cine.controller.dto.EspectadorEmisionDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.service.ValoracionesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/valoracion")
@Api(tags = "Valoracion")
public class ValoracionController {

	@Autowired
	@Lazy
	private ValoracionesService valoracionesService;
	
	/*******************************GET*******************************/

	@ApiOperation(value = "Muestra todas las emisiones puntuables por espectador (no puntuadas ya)", notes = "Devuelve lista de emisiones")
	@GetMapping(path = "/puntuables", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<EspectadorEmisionDto>> getEmisionesPuntuables(@RequestParam Long idEspectador) {

		Set<EspectadorEmisionDto> respuesta = valoracionesService.getEmisionesSinValoracion(idEspectador);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
				: ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

	/*******************************PUT*******************************/

	@ApiOperation(value = "Añade valoracion a una emisión")
	@PutMapping(path = "/actualiza_valoracion", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> updateValoracionEspectador(@RequestParam Long idEspectador,
			@RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha, @RequestParam Integer numeroSala, @RequestParam String tituloPelicula,
			@RequestParam Double valoracion, @RequestParam(required = false) Double palomitas) throws MiValidationException {

		Long respuesta = valoracionesService.updateValoracionEspectador(idEspectador, fecha, numeroSala, tituloPelicula, valoracion, palomitas);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
				: ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
}
