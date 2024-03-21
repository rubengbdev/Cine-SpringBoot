package es.curso.cine.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.controller.dto.PeliculaDto;
import es.curso.cine.controller.dto.PeliculaRecaudacionPalomitasDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.service.EspectadoresService;
import es.curso.cine.service.PeliculasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/peliculas")
@Api(tags = "Peliculas")
public class PeliculasController {
	
	@Autowired
	@Lazy
	private PeliculasService peliculasServices;
	
	@Autowired
	@Lazy
	private EspectadoresService espectadorService;
	
	/*******************************GET*******************************/
	
	@ApiOperation(value = "Busca un pelicula", notes = "Devuelve los datos de un pelicula en base a su ID")
	@GetMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PeliculaDto> getPelicula(@RequestParam Long idPelicula) {
		
		PeliculaDto respuesta = peliculasServices.getPelicula(idPelicula);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Busca todos los peliculaes", notes = "Devuelve los datos de todos los peliculas")
	@GetMapping(path = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResponseDto<PeliculaDto>> getPeliculas(@PageableDefault (page = 0, size = 5, sort = "titulo", direction = Direction.DESC) Pageable pageable) {
		
		PagedResponseDto<PeliculaDto> respuesta = peliculasServices.getPeliculas(pageable);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Busca pelicula de mas duracion", notes = "Devuelve los datos de dicha pelicula")
	@GetMapping(path = "/maslarga",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PeliculaDto> getPeliculaMasLarga() {
		
		PeliculaDto respuesta = peliculasServices.getPeliculaMasLarga();
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Busca peliculas que superan una duracion determinada", notes = "Devuelve los nombres de esas peliculas")
	@GetMapping(path = "/porDuracion",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getPeliculasByDuration(@RequestParam Integer duration,@RequestParam String nombreDirector) {
		
		List<String> respuesta = peliculasServices.getPeliculasByDuration(duration,nombreDirector);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Busca peliculas que sean aptas para la edad del espectador por id", notes = "Devuelve los datos de esas peliculas")
	@GetMapping(path = "/calificacion_espectador",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResponseDto<PeliculaDto>> getCarteleraEspectadorById(@RequestParam Long idEspectador, @PageableDefault (page = 0, size = 5, sort = "titulo", direction = Direction.ASC) Pageable pageable) {
		
		PagedResponseDto<PeliculaDto> respuesta = peliculasServices.peliculasPorEdad((int)(long)ChronoUnit.YEARS.between(espectadorService.espectadorPorId(idEspectador).getFechaNacimiento(),  LocalDate.now()), pageable);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Busca peliculas que sean aptas para la edad del espectador por nombre", notes = "Devuelve los datos de esas peliculas")
	@GetMapping(path = "/calificacion_espectador_nombre",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResponseDto<PeliculaDto>> getCarteleraEspectadorByNombre(@RequestParam String nombreEspectador, @PageableDefault (page = 0, size = 5, sort = "titulo", direction = Direction.ASC) Pageable pageable) {
		
		PagedResponseDto<PeliculaDto> respuesta = peliculasServices.peliculasPorEdad((int)(long)ChronoUnit.YEARS.between(espectadorService.espectadorPorNombre(nombreEspectador).getFechaNacimiento(),  LocalDate.now()),pageable);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Peliculas y su recaudacion media de palomitas", notes = "Devuelve los datos de esas peliculas")
	@GetMapping(path = "/recaudacion_palomitas",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PeliculaRecaudacionPalomitasDto>> getPeliculaRecaudacionPalomita() {
		
		List<PeliculaRecaudacionPalomitasDto> respuesta = peliculasServices.getPeliculasRecaudacionPalomita();
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	/*******************************POST*******************************/
	
	@ApiOperation(value = "Crea  un pelicula", notes = "Devuelve el id del nuevo pelicula")
	@PostMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createPelicula(@RequestBody @Validated PeliculaDto pelicula) throws MiValidationException {
		
		Long respuesta = peliculasServices.createPelicula(pelicula);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	/*******************************PUT*******************************/
	
	@ApiOperation(value = "Actualiza una pelicula", notes = "Devuelve el id de la pelicula actualizada")	
	@PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> updatePelicula(@RequestBody @Validated PeliculaDto pelicula) {
	
		Long respuesta = peliculasServices.updatePelicula(pelicula);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

	/*******************************DELETE*******************************/
	
	@ApiOperation(value = "Borra  un pelicula")
	@DeleteMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePelicula(@RequestParam Long idPelicula) {
		
		peliculasServices.deletePelicula(idPelicula);
	}
	
}
