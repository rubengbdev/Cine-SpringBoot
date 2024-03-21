package es.curso.cine.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;

import es.curso.cine.controller.dto.EspectadorAsistenciasDto;
import es.curso.cine.controller.dto.EspectadorDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.persistence.entities.Espectador;

public interface EspectadoresService {

	/*******************************GET*******************************/

	EspectadorDto getEspectador(Long idEspectador);
	Set<EspectadorDto> getEspectadores();
	PagedResponseDto<EspectadorDto> getEspectadores(Pageable pageable);
	
	/*******************************POST*******************************/

	Long createEspectador(EspectadorDto espectadorDto) throws MiValidationException;
	
	/*******************************PUT*******************************/

	Long updateEspectador(EspectadorDto espectadorDto);
	
	/*******************************DELETE*******************************/

	void deleteEspectador(Long idEspectador);
	
	/*******************************OTROS*******************************/

	Espectador espectadorPorNombre(String nombre);
	Espectador espectadorPorId(Long id);
	List<EspectadorAsistenciasDto> getAsistenciasEspectadores();
	PagedResponseDto<EspectadorAsistenciasDto> getAsistenciasEspectadores(Pageable pageable);
	Long getNumeroAsitenciasPorEspectador(Long id);	
	Long actualizaDineroGastadoByEspectadorId(Long id) throws MiValidationException;
	EspectadorDto actualizaGeneroFavorito(Long idEspectador);
}
