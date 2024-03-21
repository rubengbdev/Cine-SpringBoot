package es.curso.cine.controller;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.curso.cine.controller.dto.ButacasDto;
import es.curso.cine.controller.dto.EmisionDto;
import es.curso.cine.controller.dto.NuevaEntradaDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.controller.dto.PeliculaDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.persistence.entities.EmisionId;
import es.curso.cine.service.VentaEntradasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/api/venta_entrada")
@Api(tags = "Venta de Entradas")
public class VentaEntradasController {

	@Autowired
	private VentaEntradasService ventaEntradasService;
	
	/*******************************GET*******************************/
	
	@ApiOperation(value = "Busca peliculas por espectador(edad) y cartelera apta", notes = "Devuelve lista de peliculas disponibles para espectador")
	@GetMapping(path = "/peliculas-espectador",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResponseDto<PeliculaDto>> peliculasCarteleraEdadEspectador(@RequestParam Long idEspectador,@PageableDefault (page = 0, size = 5, sort = "titulo", direction = Direction.ASC) Pageable pageable) {
	
		PagedResponseDto<PeliculaDto> respuesta = ventaEntradasService.peliculasCarteleraEdadEspectador(idEspectador,pageable);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);

	}
	
	@ApiOperation(value = "Busca emisiones futuras por pelicula(id)", notes = "Devuelve lista de emisiones futuras para determinada pelicula")
	@GetMapping(path = "/emisiones_futuras",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResponseDto<EmisionDto>> emisionesFuturasByPelicula(@RequestParam String titulo, @PageableDefault (page = 0, size = 5,sort = "emisionId.fecha", direction = Direction.DESC) Pageable pageable) {
		
		PagedResponseDto<EmisionDto> respuesta = ventaEntradasService.emisionesFuturasByPelicula(titulo, pageable);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Butacas disponibles y ocupadas por emision", notes = "Devuelve lista de emisiones futuras para determinada pelicula")
	@GetMapping(path = "/butacas_disponibles",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ButacasDto> butacasLibresOcupadas(@RequestParam Long idSala, @RequestParam Long idPelicula, @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {

		ButacasDto respuesta = ventaEntradasService.butacasDisponiblesDto(new EmisionId(idSala, idPelicula, fecha)); //Quizá por fecha y sala valga, o debería, mirar que no haya duplicados en esas cosas en base datos
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	/*******************************POST*******************************/
	
	@ApiOperation(value = "Se vende una entrada a un espectador", notes = "Devuelve el id del nuevo registro EspectadorEmision")
	@PostMapping(path = "/ventas",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> ventaEntrada(@RequestBody @Validated NuevaEntradaDto nuevaEntradaDto) throws MiValidationException {
		
		Long respuesta = ventaEntradasService.ventaEntrada(nuevaEntradaDto);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}				
}
