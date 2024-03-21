package es.curso.cine.service;

import java.util.Set;

import org.springframework.data.domain.Pageable;

import es.curso.cine.controller.dto.ButacasDto;
import es.curso.cine.controller.dto.EmisionDto;
import es.curso.cine.controller.dto.NuevaEntradaDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.controller.dto.PeliculaDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.persistence.entities.EmisionId;

public interface VentaEntradasService {

	PagedResponseDto<PeliculaDto> peliculasCarteleraEdadEspectador(Long idEspectador, Pageable pageable);
	
	PagedResponseDto<EmisionDto> emisionesFuturasByPelicula(String tituloPelicula, Pageable pageable);
	
	ButacasDto butacasDisponiblesDto(EmisionId emisionId);
	
	Long ventaEntrada(NuevaEntradaDto nuevaEntradaDto) throws MiValidationException;
}
