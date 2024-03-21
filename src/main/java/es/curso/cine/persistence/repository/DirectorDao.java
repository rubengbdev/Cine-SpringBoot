package es.curso.cine.persistence.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.curso.cine.controller.dto.DirectorRecaudacionEntradasDto;
import es.curso.cine.controller.dto.EspectadorAsistenciasDto;
import es.curso.cine.persistence.entities.Director;
import es.curso.cine.persistence.entities.types.Genero;

@Repository
public interface DirectorDao extends JpaRepository<Director, Long>{

	@Query("SELECT d FROM Director d WHERE d.numPeliculas >= :numeroMinimoPeliculas")
	public Director findByNumeroMinimoPeliculas (Integer numeroMinimoPeliculas);
	
	public Director findByNombre (String nombre);
	
	//ESTADISTICAS
	
	@Query("SELECT count(d) FROM Director d WHERE d.numPeliculas > :numPelis")
	public Integer directoresConMasPelisDe(Integer numPelis);
	
	//public Integer countByNumPeliculasGreatherThan(Integer numPelis);
	
	@Query("SELECT p.director FROM Pelicula p WHERE p.genero = :genero")
	Set<Director> buscaDirectorPorGenero(Genero genero);

	@Query("SELECT new es.curso.cine.controller.dto.EspectadorAsistenciasDto(ee.espectador.nombreCompleto, ee.espectador.fechaNacimiento, COUNT(ee)) FROM EspectadorEmision ee GROUP BY ee.espectador.id ORDER BY COUNT(ee) DESC")	
	List<EspectadorAsistenciasDto>  espectadoresPorNumeroAsistenciasV2();
}
