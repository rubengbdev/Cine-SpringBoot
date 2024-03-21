package es.curso.cine.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.curso.cine.controller.dto.AcomodadorDto;
import es.curso.cine.controller.dto.EmisionDto;
import es.curso.cine.controller.dto.EmisionNuevaDto;
import es.curso.cine.controller.dto.LimpiadorDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.persistence.entities.Emision;
import es.curso.cine.persistence.entities.EmisionId;

public interface EmisionesService {
	
	/*******************************GET*******************************/

	EmisionDto getEmision (EmisionId emisionid);
	
	EmisionDto getEmision(Long idSala, Long idPelicula, LocalDate fecha);
	
	Set<EmisionDto> getEmisiones();
	
	PagedResponseDto<EmisionDto> getEmisiones(Pageable pageable);
	
	/*******************************POST*******************************/

	Long createEmision(EmisionDto emision) throws MiValidationException;
	
	/*******************************PUT*******************************/

	Long updateEmision (EmisionDto emision);
	
	/*******************************DELETE*******************************/
	
	void deleteEmision (EmisionId emisionid);

	
	/*******************************OTROS*******************************/

	List<Emision> getEmisionByFecha (Set<EmisionDto> lista);
	
	
	/*******************************ESTADISTICAS*******************************/

	Set<EmisionDto> findEmisionByPrecioPeli (Double precio);
	
	Set<EmisionDto> lastEmisiones ();
	
	Set<EmisionDto> getEmisionesBySala (Set<Long> idSalas); 
	
	Set<Emision> getEmisionesByLimpiador(LimpiadorDto limpiador);
	
	Set<Emision> getEmisionesByAcomodador(AcomodadorDto acomodador);

	Boolean updateAleatorioLimpiadorControlador();

	Emision emisionPorFechaPeliSala(LocalDateTime fechaEmision, String nombrePelicula, Integer numeroSala);

	Emision emisionPorFechaPeliSala(LocalDateTime fechaEmision, Long nombrePelicula, Long numeroSala);

	List<Integer[]> getButacasOcupadas(Long idSala, Long idPelicula, LocalDateTime fecha);

	Boolean borrar();

	Page<Emision> findByPeliculaTituloAndEmisionIdFechaAfter(String tituloPelicula, Pageable pageable);

	void generarEmisiones() throws MiValidationException;

	Set<EmisionDto> getEmisionesPasadasSinPalomitasVisitantes();

	Long createEmisionNueva(EmisionNuevaDto emisionDto) throws MiValidationException;

	//RECAUDACION MEDIA POR PELICULA
	Double recaudacionPorPelicula(String titulo);
	Integer espectadoresPorPelicula(String titulo);
}
