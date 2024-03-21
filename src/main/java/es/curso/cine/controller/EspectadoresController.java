package es.curso.cine.controller;

import java.util.List;
import java.util.Set;

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

import es.curso.cine.controller.dto.EspectadorAsistenciasDto;
import es.curso.cine.controller.dto.EspectadorDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.service.EspectadoresService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/espectadores")
@Api(tags = "Espectadores")
public class EspectadoresController {
	
	@Autowired
	private EspectadoresService espectadoresServices;
	
	/*******************************GET*******************************/
	
	@ApiOperation(value = "Busca un espectador", notes = "Devuelve los datos de un espectador en base a su ID")
	@GetMapping(path = "/espectador",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EspectadorDto> getEspectador(@RequestParam Long idEspectador) {
		
		EspectadorDto respuesta = espectadoresServices.getEspectador(idEspectador);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Busca todos los espectadores", notes = "Devuelve los datos de todos los espectadores")
	@GetMapping(path = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResponseDto<EspectadorDto>> getEspectadores(@PageableDefault (page = 0, size = 5, sort = "nombreCompleto", direction = Sort.Direction.DESC) Pageable pageable) {
		
		PagedResponseDto<EspectadorDto> respuesta = espectadoresServices.getEspectadores(pageable);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Asistencias de espectadores", notes = "Devuelve los datos de todos los espectadores con estadisticas")
	@GetMapping(path = "/asistencias_espectadores",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResponseDto<EspectadorAsistenciasDto>> getAsistenciasEspectadores(@PageableDefault (page = 0, size = 5) Pageable pageable) {
		
		PagedResponseDto<EspectadorAsistenciasDto> respuesta = espectadoresServices.getAsistenciasEspectadores(pageable);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
//	@ApiOperation(value = "Asistencias de espectadores", notes = "Devuelve los datos de todos los espectadores con estadisticas")
//	@GetMapping(path = "/asistencias_espectadores",produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<EspectadorAsistenciasDto>> getAsistenciasEspectadores() {
//		
//		List<EspectadorAsistenciasDto> respuesta = espectadoresServices.getAsistenciasEspectadores();
//		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
//	}
	/*******************************POST*******************************/
	
	@ApiOperation(value = "Crea  un espectador", notes = "Devuelve el id del nuevo espectador")
	@PostMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createEspectador(@RequestBody @Validated EspectadorDto espectador) throws MiValidationException {
		
		Long respuesta = espectadoresServices.createEspectador(espectador);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	/*******************************PUT*******************************/
	
	@ApiOperation(value = "Actualiza  un espectador", notes = "Devuelve el id del espectador actualizado")	
	@PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> updateEspectador(@RequestBody @Validated EspectadorDto espectador) {
	
		Long respuesta = espectadoresServices.updateEspectador(espectador);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "Actualiza dinero gastao del espectador", notes = "Devuelve el id del espectador actualizado")	
	@PutMapping(path = "/dinero_gastado", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> actualizaDineroGastadoByEspectadorId(@RequestParam Long idEspectador) throws MiValidationException {
	
		Long respuesta = espectadoresServices.actualizaDineroGastadoByEspectadorId(idEspectador);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	
	@ApiOperation(value = "actualiza genero favorito", notes = "Devuelve el espectador actualizado")	
	@PutMapping(path = "/genero_favorito", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EspectadorDto> actualizaGeneroFavorito(@RequestParam Long idEspectador) throws MiValidationException {
	
		EspectadorDto respuesta = espectadoresServices.actualizaGeneroFavorito(idEspectador);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}
	/*******************************DELETE*******************************/
	
	@ApiOperation(value = "Borra  un espectador")
	@DeleteMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEspectador(@RequestParam Long idEspectador) {
		
		espectadoresServices.deleteEspectador(idEspectador);
	}
	

}
