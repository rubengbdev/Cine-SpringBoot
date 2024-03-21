package es.curso.cine.service;

import es.curso.cine.controller.dto.FirstParametrosDto;
import es.curso.cine.controller.dto.FirstRespuestaDto;
import es.curso.cine.controller.dto.SecondParametrosDto;
import es.curso.cine.controller.dto.SecondRespuestaDto;

public interface EstadisticasService {

	public FirstRespuestaDto getEjercicio1(FirstParametrosDto parametros);
	
	public SecondRespuestaDto getEjercicio2(SecondParametrosDto parametros);
	
}
