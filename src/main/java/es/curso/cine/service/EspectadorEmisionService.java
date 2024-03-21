package es.curso.cine.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.curso.cine.controller.dto.Butaca2Dto;
import es.curso.cine.controller.dto.DirectorRecaudacionEntradasDto;
import es.curso.cine.controller.dto.EspectadorEmisionDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.controller.dto.PeliculaRecaudacionPalomitasDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.persistence.entities.EmisionId;
import es.curso.cine.persistence.entities.EspectadorEmision;

public interface EspectadorEmisionService {
	
	
	/*******************************GET*******************************/

	EspectadorEmisionDto getEspectadorEmision(Long idEspectadorEmision);
	Set<EspectadorEmisionDto> getEspectadorEmisiones();
	PagedResponseDto<EspectadorEmisionDto> getEspectadorEmisiones(Pageable pageable);
	List<EspectadorEmision> getEspectadorEmisionesEntidad();

	/*******************************POST*******************************/

	Long createEspectadorEmision(EspectadorEmisionDto espectadorEmision) throws MiValidationException;

	/*******************************PUT*******************************/

	Long updateEspectadorEmision(EspectadorEmisionDto espectadorEmision);
	
	/*******************************DELETE*******************************/
	
	void deleteEspectadorEmision(Long idEspectadorEmision);

	/*******************************OTROS*******************************/

//	Set<Long> setIdEspectadorEmision(Espectador espectador);
	Set<EspectadorEmision> getEspectadorEmisionBy(EmisionId emisionId);
	Set<EspectadorEmision> getEspectadorEmisionByIdEspectador(Long idEspectador);
	
	/*******************************GENERA ESPECTADOREMISION*******************************/
	
	Boolean generarEspectadorEmision() throws MiValidationException;
//	Boolean validarCrear(EspectadorEmision espectadorEmision);
	List<EspectadorEmision> findByEmisionEmisionId(LocalDateTime fecha, Long idPelicula, Long idSala);
	List<Integer[]> findByEmisionEmisionIdOrderByFilaAscColumnaAsc(LocalDateTime fecha, Long idPelicula, Long idSala);
	List<Integer[]> butacasDisponibles(LocalDateTime fecha, Long idPelicula, Long idSala);
	
	//Version con listas de dtos
	List<Butaca2Dto> butacasDtoDisponibles(LocalDateTime fecha, Long idPelicula, Long idSala);
	List<Butaca2Dto> butacasDtoOcupadas(LocalDateTime fecha, Long idPelicula, Long idSala);
	
	//valoracion
	Set<EspectadorEmisionDto> findByEspectadorValoracionNullFecha(Long idEspectador);
	EspectadorEmision findByEspectadorIdAndEmisionEmisionIdFechaAndEmisionSalaNumeroAndEmisionPeliculaTitulo(Long idEspectador, LocalDateTime fecha, Integer numeroSala, String tituloPelicula);
	
	//Valoracion
	List<Double> valoracionesPorAcomodador(Long id);
	void updateValoracion(EspectadorEmision emisionParaActualizar, Double valoracion, Double palomitas)throws MiValidationException;	
	Boolean existsByEspectadorIdAndEmisionEmisionIdFechaAndEmisionSalaNumeroAndEmisionPeliculaTitulo(Long idEspectador,LocalDateTime fecha, Integer numeroSala, String tituloPelicula);
	Integer countEspectadoresByEmisionEmisionId(EmisionId emisionId);
	Double getRecaudacionPalomitas(EmisionId emisionId);
	Long countByEspectadorId(Long id);
	
	/*******************************RECAUDACION POR ESPECTADOR*******************************/
	Double getRecaudacionEntradasByEspectador(Long id);
	Double getRecaudacionPalomitasByEspectador(Long id);
	Double getRecaudacionTotalsByEspectador(Long id);
	
	/*******************************GENERO FAVORITO*******************************/
	List<String> generoFavoritoByIdEspectador(Long idEspectador);
	List<String> generoFavoritoByIdEspectador2(Long idEspectador);
	Page<String> generoFavoritoByIdEspectador3(Long idEspectador, Pageable pageable);
	Double getRecaudacionPorDirector(String nombre);
	List<DirectorRecaudacionEntradasDto>  recaudacionDirectores();
	List<PeliculaRecaudacionPalomitasDto> getPeliculasRecaudacionPalomita();
}
