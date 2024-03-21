package es.curso.cine.service.impl;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.curso.cine.controller.dto.EspectadorEmisionDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.persistence.entities.EspectadorEmision;
import es.curso.cine.service.EspectadorEmisionService;
import es.curso.cine.service.ValoracionesService;
import es.curso.cine.utils.CineConstants;
import es.curso.cine.utils.CineErrorConstants;
@Service
@Transactional(rollbackFor = MiValidationException.class)
public class ValoracionesServiceImpl implements ValoracionesService {

	@Autowired
	EspectadorEmisionService espectadorEmisionService;
	
	@Override
	public Set<EspectadorEmisionDto> getEmisionesSinValoracion(Long idEspectador) {

		return espectadorEmisionService.findByEspectadorValoracionNullFecha(idEspectador);
	}
	
	@Override
	public Long updateValoracionEspectador(Long idEspectador, LocalDateTime fecha, Integer numeroSala, String tituloPelicula, Double valoracion, Double palomitas) throws MiValidationException {

		validarEspectadorEmision(idEspectador, fecha, numeroSala, tituloPelicula);
		
		EspectadorEmision emisionParaActualizar = espectadorEmisionService.findByEspectadorIdAndEmisionEmisionIdFechaAndEmisionSalaNumeroAndEmisionPeliculaTitulo(idEspectador,fecha,numeroSala,tituloPelicula);
		
		espectadorEmisionService.updateValoracion(emisionParaActualizar, valoracion, palomitas);
		
		return (emisionParaActualizar.getId());
	}
	
	private void validarEspectadorEmision(Long idEspectador, LocalDateTime fecha, Integer numeroSala, String tituloPelicula) throws MiValidationException {

		if (Boolean.FALSE.equals(espectadorEmisionService.existsByEspectadorIdAndEmisionEmisionIdFechaAndEmisionSalaNumeroAndEmisionPeliculaTitulo(idEspectador,fecha,numeroSala,tituloPelicula)))
			throw new MiValidationException(CineErrorConstants.ESPECTADOR_EMISION_INEXISTENTE);
		
		if (fecha.isBefore(LocalDateTime.now().minusDays(CineConstants.Valoraciones.DIAS_VALORACION))) {
			throw new MiValidationException(CineErrorConstants.ESPECTADOR_EMISION_NO_VALORABLE_FECHA, CineConstants.Valoraciones.DIAS_VALORACION.toString());
		}
	}
}
