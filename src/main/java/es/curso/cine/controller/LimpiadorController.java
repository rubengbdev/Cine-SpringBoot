package es.curso.cine.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.curso.cine.controller.dto.LimpiadorDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.service.LimpiadoresService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/limpiadores")
@Api(tags = "Limpiadores")
public class LimpiadorController {

	@Autowired
	private LimpiadoresService limpiadoresService;

	/*******************************GET*******************************/
	
	@ApiOperation(value = "Busca un limpiador", notes = "Devuelve los datos de un limpiador en base a su ID")
	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LimpiadorDto> getLimpiador(@RequestParam Long idLimpiador) {

		LimpiadorDto respuesta = limpiadoresService.getLimpiador(idLimpiador);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

	@ApiOperation(value = "Busca todos los limpiadores", notes = "Devuelve los datos de todos los limpiadores")
	@GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<LimpiadorDto>> getLimpiadores() {

		Set<LimpiadorDto> respuesta = limpiadoresService.getLimpiadores();
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

	/*******************************POST*******************************/
	
	@ApiOperation(value = "Crea  un limpiador", notes = "Devuelve el id del nuevo limpiador")
	@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createLimpiador(@RequestBody @Validated LimpiadorDto limpiador)
			throws MiValidationException {

		Long respuesta = limpiadoresService.createLimpiador(limpiador);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

	/*******************************PUT*******************************/
	
	@ApiOperation(value = "Actualiza  un limpiador", notes = "Devuelve el id del limpiador actualizado")
	@PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> updateLimpiador(@RequestBody @Validated LimpiadorDto limpiador) {

//		if (limpiadoresService.getLimpiador(limpiador.getId()) != null) {
//			return limpiadoresService.updateLimpiador(limpiador);
//		}
//		return null;
		Long respuesta = limpiadoresService.updateLimpiador(limpiador);
		return respuesta == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

	/*******************************DELETE*******************************/
	
	@ApiOperation(value = "Borra  un limpiador")
	@DeleteMapping(path = "/{idLimpiador}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLimpiador(@PathVariable Long idLimpiador) {

		limpiadoresService.deleteLimpiador(idLimpiador);
	}

}
