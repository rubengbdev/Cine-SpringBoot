package es.curso.cine.service;

import java.util.List;
import java.util.Set;

import es.curso.cine.controller.dto.SalaDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.persistence.entities.Emision;
import es.curso.cine.persistence.entities.Sala;

public interface SalasService {
	
	
	/*******************************GET*******************************/
	
	SalaDto getSala (Long id);
	
	Set<SalaDto> getSalas();
	
	Sala getSalaEntity (Long id);

	/*******************************POST
	 * @throws MiValidationException *******************************/

	Long createSala(SalaDto sala) throws MiValidationException;
	
	/*******************************PUT*******************************/

	Long updateSala (SalaDto sala);
	
	/*******************************DELETE*******************************/
	
	void deleteSala (Long id);

	/*******************************OTROS*******************************/

	List<Emision> getEmisionesBySala(SalaDto salaDto);
	
	/*******************************ESTADISTICAS*******************************/

	SalaDto findUltimaSalaLlena();
	
	Integer numeroSalaPorId(Long id);

	Sala findByNumero(Integer numero);

	Sala findById(Long id);
}
