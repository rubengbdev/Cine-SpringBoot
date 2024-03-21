package es.curso.cine.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.curso.cine.controller.dto.DirectorDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.controller.dto.PeliculaDto;
import es.curso.cine.controller.dto.PeliculaRecaudacionPalomitasDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.persistence.entities.Pelicula;

public interface PeliculasService {
	
	/*******************************GET*******************************/
	
	PagedResponseDto<PeliculaDto> getPeliculas(Pageable pageable);
	
	PeliculaDto getPelicula (Long id);
	
	Pelicula getPeliculaEntity (Long id);
	
	/*******************************POST*******************************/
	
	Long createPelicula (PeliculaDto pelicula) throws MiValidationException;
	
//	Long createPeliculaDirector (PeliculaDto pelicula);
	
	/*******************************PUT*******************************/
	
	Long updatePelicula (PeliculaDto pelicula);
	
	/*******************************DELETE*******************************/
	
	void deletePelicula (Long id);
	
//	void deletePeliculaDirector(Integer id);
	
	/*******************************OTROS*******************************/

	PeliculaDto getPeliculaMasLarga ();

	List<String> getPeliculasByDuration(Integer duration, String nombreDirector);

	List<Pelicula> getPeliculasByDirector(DirectorDto directorDto);

//	List<Pelicula> getTituloPeliculas(Set<PeliculaDto> lista);
	
	/*******************************ESTADISTICA*******************************/
		
	Set<PeliculaDto> getPeliculasByPremium(String premium);
	
	Set<PeliculaDto> getPeliculaGeneroPrecio (String genero1, String genero2, Double precio);
	
	String peliculaPorNombre(Long id);

	Pelicula findByTitulo(String titulo);

	PagedResponseDto<PeliculaDto> peliculasPorEdad(Integer edad, Pageable pageable);

	Page<Pelicula> peliculasCarteleraEdadEspectador(Integer edad,  Pageable pageable);

	List<PeliculaRecaudacionPalomitasDto> getPeliculasRecaudacionPalomita();
}
