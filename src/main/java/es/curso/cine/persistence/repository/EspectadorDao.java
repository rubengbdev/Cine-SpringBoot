package es.curso.cine.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.curso.cine.controller.dto.EspectadorAsistenciasDto;
import es.curso.cine.persistence.entities.Espectador;

public interface EspectadorDao extends JpaRepository<Espectador, Long>{

	@Query("SELECT e FROM Espectador e WHERE e.nombreCompleto = :nombre")
	public Espectador buscaEspectadorPorNombre(String nombre);

	@Query("SELECT e FROM Espectador e WHERE e.id = :id")
	public Espectador espectadorPorId(Long id); 
	
	public Espectador findByNombreCompleto(String nombreCompleto);	
	
	@Query("SELECT ee.espectador FROM EspectadorEmision ee GROUP BY ee.espectador.id ORDER BY COUNT(ee) DESC")	
	public List<Espectador>  espectadoresPorNumeroAsistencias();
	
	@Query("SELECT new es.curso.cine.controller.dto.EspectadorAsistenciasDto(ee.espectador.nombreCompleto, ee.espectador.fechaNacimiento, COUNT(ee)) FROM EspectadorEmision ee GROUP BY ee.espectador.id ORDER BY COUNT(ee) DESC")	
	public List<EspectadorAsistenciasDto>  espectadoresPorNumeroAsistenciasV2();

	@Query("SELECT new es.curso.cine.controller.dto.EspectadorAsistenciasDto(ee.espectador.nombreCompleto, ee.espectador.fechaNacimiento, COUNT(ee)) FROM EspectadorEmision ee GROUP BY ee.espectador.id ORDER BY COUNT(ee) DESC")	
	public Page<EspectadorAsistenciasDto> espectadoresPorNumeroAsistenciasV2(Pageable pageable);

//	@Query("SELECT e FROM Espectador e ORDER BY e.nombreCompleto ASC")
//	Page<Espectador> findAllByName(Pageable page);
	
	@Query("SELECT e FROM Espectador e")
	Page<Espectador> findAllByName(Pageable page);
	
//	@Query("SELECT ee.emision.pelicula.genero FROM EspectadorEmision ee WHERE ee.espectador.id = :idEspectador")	
//	public List<String> generoFavoritoByIdEspectador(Long idEspectador);
//	
//	@Query("SELECT ee.emision.pelicula.genero FROM EspectadorEmision ee  WHERE ee.espectador.id = :idEspectador  GROUP BY ee.emision.pelicula.genero ORDER BY COUNT (ee.emision.pelicula.genero) DESC")
//	public List<String> generoFavoritoByIdEspectador2(Long idEspectador);
//	
//	@Query("SELECT ee.emision.pelicula.genero FROM EspectadorEmision ee  WHERE ee.espectador.id = :idEspectador GROUP BY ee.emision.pelicula.genero ORDER BY COUNT (ee.emision.pelicula.genero) DESC")
//	public List<String> generoFavoritoByIdEspectador3(Long idEspectador);
}
