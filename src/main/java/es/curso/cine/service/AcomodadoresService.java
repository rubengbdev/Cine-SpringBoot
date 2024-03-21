package es.curso.cine.service;

import java.util.List;
import java.util.Set;

import es.curso.cine.controller.dto.AcomodadorDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.persistence.entities.Acomodador;

public interface AcomodadoresService {
	
	/*******************************GET**********************************/

	AcomodadorDto getAcomodador (Long idAcomodador);
	Set<AcomodadorDto> getAcomodadores();
	Boolean existsById(Long idAcomodador);
	
	/*******************************POST
	 * @throws MiValidationException *********************************/

	Long createAcomodador(AcomodadorDto acomodadorDto) throws MiValidationException;
	
	/*******************************PUT**********************************/

	Long updateAcomodador(AcomodadorDto acomodadorDto);
	
	/*******************************DELETE*******************************/

	void deleteAcomodador(Long idAcomodador);
	
	/*******************************AcomodadorLimpiador******************/
	
	Acomodador acomodadorPorNombre(String nombre);
	
	/*******************************ValoracionMedia**********************/

	List<Long> actualizarValoracionAcomodador();
	void actualizarValoracionAcomodadorIndividual(Long id);
}
