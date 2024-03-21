package es.curso.cine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.curso.cine.controller.dto.FirstParametrosDto;
import es.curso.cine.controller.dto.FirstRespuestaDto;
import es.curso.cine.controller.dto.SecondParametrosDto;
import es.curso.cine.controller.dto.SecondRespuestaDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.mapper.MapperService;
import es.curso.cine.service.DirectoresService;
import es.curso.cine.service.EmisionesService;
import es.curso.cine.service.EstadisticasService;
import es.curso.cine.service.PeliculasService;
import es.curso.cine.service.SalasService;

@Service
@Transactional(rollbackFor = MiValidationException.class)
public class EstadisticasServiceImpl implements EstadisticasService{

	@Autowired
	@Lazy
	private PeliculasService peliculasService;
	
	@Autowired
	@Lazy
	private EmisionesService emisionesService;
	
	@Autowired
	@Lazy
	private SalasService salasService;
	
	@Autowired
	@Lazy
	private MapperService mapperService;
	
	@Autowired
	@Lazy
	private DirectoresService directoresService;
	
	@Override
	public FirstRespuestaDto getEjercicio1(FirstParametrosDto parametros) {

		FirstRespuestaDto respuesta = new 	FirstRespuestaDto();
		
		respuesta.setPeliculas(peliculasService.getPeliculasByPremium(parametros.getPremium()));
		respuesta.setDirectores(directoresService.getDirectoresByGenero(parametros.getGenero()));
		respuesta.setEmisiones(emisionesService.findEmisionByPrecioPeli(parametros.getPrecioMinimo()));
		respuesta.setSala(salasService.findUltimaSalaLlena());

		return (respuesta);
	}

	@Override
	public SecondRespuestaDto getEjercicio2(SecondParametrosDto parametros) {
		
//		SecondRespuestaDto respuesta = new 	SecondRespuestaDto();
//		
//		respuesta.setNumDirectores(directoresService.directoresConMasPelisDe(parametros.getNumPeliculas()));
//		respuesta.setUltimasEmisiones(emisionesService.lastEmisiones());
//		respuesta.setPeliculasPorGeneroPrecio(peliculasService.getPeliculaGeneroPrecio(parametros.getGenero1(),parametros.getGenero2(),parametros.getPrecioMinimo()));
//		respuesta.setEmisionesPorSala(emisionesService.getEmisionesBySala(parametros.getIdSalas()));
//		
//		return (respuesta);
		return null;
	}
}
