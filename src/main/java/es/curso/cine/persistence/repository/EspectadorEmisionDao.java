package es.curso.cine.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.curso.cine.controller.dto.DirectorRecaudacionEntradasDto;
import es.curso.cine.controller.dto.PeliculaRecaudacionPalomitasDto;
import es.curso.cine.persistence.entities.EmisionId;
import es.curso.cine.persistence.entities.EspectadorEmision;

public interface EspectadorEmisionDao extends JpaRepository<EspectadorEmision, Long>{

	@Query("SELECT em FROM EspectadorEmision em WHERE em.emision.emisionId = :emisionId")
	Set<EspectadorEmision> buscarEspectadorEmisionById(EmisionId emisionId);

	@Query("SELECT em FROM EspectadorEmision em WHERE em.espectador.id = :idEspectador")
	Set<EspectadorEmision> getEspectadorEmisionByIdEspectador(Long idEspectador);
	
	@Query("SELECT AVG(ee.valoracionServicio) FROM EspectadorEmision ee WHERE ee.emision.acomodador.id = :idAcomodador")
	Double findValoracionesByAcomodadorId(Long idAcomodador);
	
	EspectadorEmision findByFilaAndColumnaAndEmisionEmisionId(Integer fila, Integer columna, EmisionId emisionId);
	
	Set<EspectadorEmision> findByEmisionEmisionIdAndEspectadorId(EmisionId emisionId, Long id);
	
	List<EspectadorEmision> findByEmisionEmisionId(EmisionId emisionId);

	List<EspectadorEmision> findByEmisionEmisionIdOrderByFilaAscColumnaAsc(EmisionId emisionId);
	
	List<EspectadorEmision> findByEmisionEmisionIdFechaAndEmisionEmisionIdIdSalaOrderByFilaAscColumnaAsc(LocalDateTime fecha, Long idSala);

	//Valoraciones
	
	List<EspectadorEmision> findByEspectadorIdAndValoracionServicioIsNullAndEmisionEmisionIdFechaAfter(Long idEspectador,LocalDateTime fecha);
	
	EspectadorEmision findByEspectadorIdAndEmisionEmisionIdFechaAndEmisionSalaNumeroAndEmisionPeliculaTitulo(Long idEspectador, LocalDateTime fecha, Integer numeroSala, String titulo);
	
//	EspectadorEmision findByEmisionEmisionIdFechaAndEmisionSalaIdAndEmisionPeliculaIdAndEspectadorId(LocalDateTime fecha, Long idSala, Long idPelicula, Long idEspectador);

	Boolean existsByEmisionEmisionIdFechaAndEmisionSalaIdAndEmisionPeliculaIdAndEspectadorId(LocalDateTime fecha, Long idSala, Long idPelicula, Long idEspectador);

	Set<EspectadorEmision> findByEmisionAcomodadorId(Long id);

	Boolean existsByEspectadorIdAndEmisionEmisionIdFechaAndEmisionSalaNumeroAndEmisionPeliculaTitulo(Long idEspectador,
			LocalDateTime fecha, Integer numeroSala, String tituloPelicula);
	
	Integer countByEmisionEmisionId(EmisionId emisionId);
	
	@Query("SELECT SUM(es.gastoPalomitas) FROM EspectadorEmision es WHERE es.emision.emisionId = :id")
	Double getRecaudacionPalomitasPorEmision(EmisionId id);
	
	Long countByEspectadorId(Long id);
	
	@Query("SELECT SUM(es.gastoPalomitas) FROM EspectadorEmision es WHERE es.espectador.id = :idEspectador")
	Double getRecaudacionPalomitasByEspectador(Long idEspectador);
	
	@Query("SELECT SUM(es.emision.pelicula.precio) FROM EspectadorEmision es WHERE es.espectador.id = :idEspectador")
	Double getRecaudacionEntradasByEspectador(Long idEspectador);
	
	//Quitar group by
	@Query("SELECT SUM(ee.gastoPalomitas + ee.emision.pelicula.precio) FROM EspectadorEmision ee WHERE ee.espectador.id = :idEspectador GROUP BY ee.espectador.id")
	Double getRecaudacionTotalsByEspectador(Long idEspectador);
	
	@Query("SELECT ee.emision.pelicula.genero FROM EspectadorEmision ee WHERE ee.espectador.id = :idEspectador")	
	public List<String> generoFavoritoByIdEspectador(Long idEspectador);
	
	@Query("SELECT ee.emision.pelicula.genero FROM EspectadorEmision ee  WHERE ee.espectador.id = :idEspectador  GROUP BY ee.emision.pelicula.genero ORDER BY COUNT (ee.emision.pelicula.genero) DESC")
	public List<String> generoFavoritoByIdEspectador2(Long idEspectador);
	
	@Query("SELECT ee.emision.pelicula.genero FROM EspectadorEmision ee  WHERE ee.espectador.id = :idEspectador GROUP BY ee.emision.pelicula.genero ORDER BY COUNT (ee.emision.pelicula.genero) DESC")
	public Page<String> generoFavoritoByIdEspectador3(Long idEspectador, Pageable pageable);

	@Query("SELECT SUM(ee.emision.pelicula.precio) FROM EspectadorEmision ee WHERE ee.emision.pelicula.director.nombre = :nombre")
	Double getRecaudacionPorDirector(String nombre);
	
	@Query("SELECT new es.curso.cine.controller.dto.DirectorRecaudacionEntradasDto(ee.emision.pelicula.director.nombre, SUM(ee.emision.pelicula.precio)) FROM EspectadorEmision ee GROUP BY ee.emision.pelicula.director.nombre ORDER BY SUM(ee.emision.pelicula.precio) DESC")	
	List<DirectorRecaudacionEntradasDto>  recaudacionDirectores();

	@Query("SELECT new es.curso.cine.controller.dto.PeliculaRecaudacionPalomitasDto(ee.emision.pelicula.titulo, ROUND(AVG(ee.gastoPalomitas),2)) "
			+ "FROM EspectadorEmision ee GROUP BY ee.emision.pelicula.titulo ORDER BY ROUND(AVG(COALESCE(ee.gastoPalomitas, '0')),2) DESC")	
	List<PeliculaRecaudacionPalomitasDto> getPeliculasRecaudacionPalomita();
	
}