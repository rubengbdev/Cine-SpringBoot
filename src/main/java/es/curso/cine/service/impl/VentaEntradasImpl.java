package es.curso.cine.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.curso.cine.controller.dto.ButacasDto;
import es.curso.cine.controller.dto.EmisionDto;
import es.curso.cine.controller.dto.NuevaEntradaDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.controller.dto.PeliculaDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.mapper.MapperService;
import es.curso.cine.persistence.entities.Emision;
import es.curso.cine.persistence.entities.EmisionId;
import es.curso.cine.persistence.entities.Pelicula;
import es.curso.cine.service.EmisionesService;
import es.curso.cine.service.EspectadorEmisionService;
import es.curso.cine.service.EspectadoresService;
import es.curso.cine.service.PeliculasService;
import es.curso.cine.service.VentaEntradasService;

@Service
@Transactional(rollbackFor = MiValidationException.class)
public class VentaEntradasImpl implements VentaEntradasService {

	@Autowired
	@Lazy
	private PeliculasService peliculasService;

	@Autowired
	@Lazy
	private EmisionesService emisionesService;

	@Autowired
	@Lazy
	private EspectadorEmisionService espectadorEmisionService;

	@Autowired
	@Lazy
	private EspectadoresService espectadorService;

	@Autowired
	@Lazy
	private MapperService mapperService;

	// METODO 1
	@Override
	public PagedResponseDto<PeliculaDto> peliculasCarteleraEdadEspectador(Long idEspectador, Pageable pageable) {

		Page<Pelicula> consulta = peliculasService
				.peliculasCarteleraEdadEspectador(
						(int) ChronoUnit.YEARS.between(
								espectadorService.espectadorPorId(idEspectador).getFechaNacimiento(), LocalDate.now()),
						pageable);

		if (consulta != null) {

			return new PagedResponseDto<PeliculaDto>(consulta, mapperService::peliculaToDto);
		}

		return null;

//		return (peliculasService.peliculasCarteleraEdadEspectador((int) ChronoUnit.YEARS.between(espectadorService.espectadorPorId(idEspectador).getFechaNacimiento(), LocalDate.now())));
	}

	// METODO 2
	@Override
	public PagedResponseDto<EmisionDto> emisionesFuturasByPelicula(String tituloPelicula, Pageable pageable) {

		Page<Emision> consulta = emisionesService.findByPeliculaTituloAndEmisionIdFechaAfter(tituloPelicula, pageable);

		if (consulta != null) {

			return new PagedResponseDto<EmisionDto>(consulta, mapperService::emisionToDto);
		}

		return null;

//		return (emisionesService.findByPeliculaTituloAndEmisionIdFechaAfter(tituloPelicula));
	}

	// METODO 3
	@Override
	public ButacasDto butacasDisponiblesDto(EmisionId emisionId) {

		return (new ButacasDto(
				espectadorEmisionService.butacasDtoDisponibles(emisionId.getFecha(), emisionId.getIdPelicula(),
						emisionId.getIdSala()),
				espectadorEmisionService.butacasDtoOcupadas(emisionId.getFecha(), emisionId.getIdPelicula(),
						emisionId.getIdSala())));
	}

	// METODO 4
	@Override
	public Long ventaEntrada(NuevaEntradaDto nuevaEntradaDto) throws MiValidationException {

		return (espectadorEmisionService.createEspectadorEmision(mapperService
				.espectadorEmisionToDto(mapperService.nuevaEntradaDtoToEspectadorEmision(nuevaEntradaDto))));
	}
}
