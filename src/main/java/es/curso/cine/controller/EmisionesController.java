package es.curso.cine.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
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

import es.curso.cine.controller.dto.EmisionDto;
import es.curso.cine.controller.dto.EmisionNuevaDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.controller.dto.PeliculaDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.persistence.entities.EmisionId;
import es.curso.cine.service.EmisionesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/emisiones")
@Api(tags = "Emisiones")
public class EmisionesController {
	
	@Autowired
	private EmisionesService emisionesServices;
	
	/*******************************GET*******************************/
	
	@ApiOperation(value = "Busca una emision", notes = "Devuelve los datos de una emision en base a su ID")
	@GetMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmisionDto> getEmision(@RequestParam Long idSala, @RequestParam Long idPelicula, @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {
		
		EmisionDto respuesta = emisionesServices.getEmision(new EmisionId(idSala,idPelicula,fecha));
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	
	@ApiOperation(value = "Busca todas las emisiones", notes = "Devuelve los datos de todos los limpiadores")
	@GetMapping(path = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResponseDto<EmisionDto>> getEmisiones(@PageableDefault (page = 0, size = 5, sort = "emisionId.fecha", direction = Sort.Direction.DESC) Pageable pageable) {
		
		PagedResponseDto<EmisionDto> respuesta = emisionesServices.getEmisiones(pageable);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Muestra las butacas ocupadas", notes = "Devuelve una lista con las butacas ocupadas")
	@GetMapping(path = "/butacas_ocupadas",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Integer[]>> getButacasOcupadas(@RequestParam Long idSala, @RequestParam Long idPelicula, @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME)
	LocalDateTime fecha) {
		
		List<Integer[]> respuesta = emisionesServices.getButacasOcupadas(idSala, idPelicula, fecha);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Emisiones del pasado sin palomitas ni visitantes", notes = "Devuelve una lista con las emisiones")
	@GetMapping(path = "/emisiones_pasadas_sin_palomitas_visitantes_actualiza",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<EmisionDto>> getEmisionesPasadasSinPalomitasVisitantes() {
		
		Set<EmisionDto> respuesta = emisionesServices.getEmisionesPasadasSinPalomitasVisitantes();
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	/*******************************POST*******************************/
	
	@ApiOperation(value = "Crea  una emision da igual fecha", notes = "Devuelve el id de nueva emision")
	@PostMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createEmision(@RequestBody @Validated EmisionDto emision) throws MiValidationException {
		
		Long respuesta = emisionesServices.createEmision(emision);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Crea  una emision FUTURA", notes = "Devuelve el id de nueva emision")
	@PostMapping(path = "/emision_futura",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createEmisionNueva(@RequestBody @Validated EmisionNuevaDto emision) throws MiValidationException {
		
		Long respuesta = emisionesServices.createEmisionNueva(emision);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	/*******************************PUT*******************************/
	
	@ApiOperation(value = "Actualiza  emision", notes = "Devuelve el id de emision actualizado")	
	@PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> updateEmision(@RequestBody @Validated EmisionDto emision) {
	
		Long respuesta = emisionesServices.updateEmision(emision);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Asigna aleatoriamente limpiadores y acomodadores en las emisiones", notes = "Devuelve un valor true/false según exito")	
	@PutMapping(path = "/aleatorizarLimpiadorAcomodador", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> aleatorizar() {
	
		Boolean respuesta = emisionesServices.updateAleatorioLimpiadorControlador();
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Borra valores asignados aleatoriamente a limpiadores y acomodadores en emisiones", notes = "Devuelve un valor true/false según exito")	
	@PutMapping(path = "/borrarAleatorios", produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Boolean> borrar() {
	
		Boolean respuesta = emisionesServices.borrar();
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	/*******************************DELETE*******************************/
	
	@ApiOperation(value = "Borra emision")
	@DeleteMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEmision(@RequestParam Long idSala,  Long idPelicula, LocalDateTime fecha) {
	
		emisionesServices.deleteEmision(new EmisionId(idSala,idPelicula,fecha));
	}
	
	/*******************************POST - GENERADOR ALEATORIOS*******************************/
	
	@ApiOperation(value = "Crea emisiones aleatorias")
	@PostMapping(path = "/generador",produces = MediaType.APPLICATION_JSON_VALUE)
	public void emisionesAleatorias() throws MiValidationException {
		
		emisionesServices.generarEmisiones();
	}
}
