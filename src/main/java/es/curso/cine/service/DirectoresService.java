package es.curso.cine.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;

import es.curso.cine.controller.dto.DirectorDto;
import es.curso.cine.controller.dto.DirectorRecaudacionEntradasDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.persistence.entities.Director;

public interface DirectoresService {

	/*******************************GET*******************************/

	DirectorDto getDirector (Long id);
	
	Set<DirectorDto> getDirectores();
	PagedResponseDto<DirectorDto> getDirectores(Pageable pageable);

	
	/*******************************POST*******************************/

	Long createDirector(DirectorDto director) throws MiValidationException;
	
	/*******************************PUT*******************************/
	
	Long updateDirector(DirectorDto director);
	
	/*******************************DELETE*******************************/

	void deleteDirector (Long id);
	void deleteDirectorPelicula(Integer id);
	
	/*******************************OTROS*******************************/

	Director getDirectorByName(String director);
	List<String> getPeliculasDirector(Director director);
	
	/*******************************ESTADISTICAS*******************************/
	
	Set<DirectorDto> getDirectoresByGenero(String genero);
	Integer directoresConMasPelisDe(Integer numeroPeliculas);
	List<DirectorRecaudacionEntradasDto> recaudacionDirectores();
	Double getRecaudacionPorDirector(String nombre);


}
