package es.curso.cine.service;

import java.time.LocalDateTime;
import java.util.Set;

import es.curso.cine.controller.dto.EspectadorEmisionDto;
import es.curso.cine.exception.MiValidationException;

public interface ValoracionesService {
	
	public Set<EspectadorEmisionDto> getEmisionesSinValoracion(Long idEspectador);
	public Long updateValoracionEspectador(Long idEspectador, LocalDateTime fecha, Integer numeroSala, String tituloPelicula,
			Double valoracion, Double palomitas) throws MiValidationException;
}
