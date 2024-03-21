package es.curso.cine.service;

import java.util.Set;

import es.curso.cine.controller.dto.LimpiadorDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.persistence.entities.Limpiador;

public interface LimpiadoresService {
	
	LimpiadorDto getLimpiador (Long idLimpiador);
	
	Set<LimpiadorDto> getLimpiadores();
	
	Long createLimpiador(LimpiadorDto limpiadorDto) throws MiValidationException;
	
	void deleteLimpiador(Long idLimpiador);
	
	Long updateLimpiador (LimpiadorDto limpiadorDto);
	
	/*******************************AcomodadorLimpiador*******************************/
	
	Limpiador limpiadorPorNombre(String nombre);
}
