package es.curso.cine.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.curso.cine.persistence.entities.Emision;
import es.curso.cine.persistence.entities.EmisionId;

public interface EmisionDao extends JpaRepository<Emision, EmisionId>{

	//Emision findByEmisionIdFecha (LocalDate fecha);
	
	Emision findByEmisionId (EmisionId emisionId);
		

	@Query("SELECT e FROM Emision e WHERE e.pelicula.precio > :precioPeli ORDER BY e.palomitas DESC")
	public List<Emision> emisionesPorPrecio(Double precioPeli);
	
	public List<Emision> findByPeliculaPrecioGreaterThanOrderByPalomitasDesc(Double precioPeli);
	
	
//	@Query("SELECT e FROM Emision e ORDER BY e.emisionId.fecha DESC LIMIT 10")
//	public List<Emision> ultimasEmisiones();
	@Query("SELECT e FROM Emision e ORDER BY e.emisionId.fecha DESC")
	public List<Emision> ultimasEmisiones();
	
	public Set<Emision> findByEmisionIdIdSalaIn(Set<Long> idSalas);
	
	public Set<Emision> findBySalaIdIn(Set<Long> idSalas);
	
	@Query("SELECT e FROM Emision e WHERE e.limpiador.id = :idLimpiador")
	public Set<Emision> encuntraLimpiador(Long idLimpiador);
	
	@Query("SELECT e FROM Emision e WHERE e.acomodador.id = :idAcomodador")
	public Set<Emision> encuntraAcomodador(Long idAcomodador);

	@Query("SELECT e FROM Emision e WHERE (e.emisionId.fecha = :fechaEmision AND e.emisionId.idPelicula = (SELECT p.id FROM Pelicula p WHERE p.titulo = :nombrePelicula) AND e.emisionId.idSala = (SELECT s.id FROM Sala s WHERE s.numero = :numeroSala))")
	public Emision getEmisionByFechaPeliSala(LocalDateTime fechaEmision, String nombrePelicula, Integer numeroSala);
	
	
	Emision findByEmisionIdFechaAndPeliculaIdAndSalaId(LocalDateTime fecha, Long idPelicula, Long idSala);
	
	Set<Emision> findByPeliculaId(Long id);
	
	Page<Emision> findByPeliculaTituloAndEmisionIdFechaAfter(String titulo,LocalDateTime fecha, Pageable pageable);

	Set<Emision> findByEmisionIdFechaBeforeAndPalomitasIsNullAndVisitantesIsNull(LocalDateTime fecha);

	
	//RECAUDACION MEDIA POR CADA PELICULA
	@Query("SELECT SUM(e.palomitas) FROM Emision e WHERE e.pelicula.titulo = :titulo")
	Double recaudacionPorPelicula(String titulo);
	
	//NUMERO ESPECTADORES POR CADA PELICULA
	@Query("SELECT SUM(e.visitantes) FROM Emision e WHERE e.pelicula.titulo = :titulo")
	Integer espectadoresPorPelicula(String titulo);
	
	@Query("SELECT e FROM Emision e")
	Page<Emision> findAllByTitulo(Pageable pageable);
	
//	//Consulta 1
//	//Querymethod	
//	public List<Emision> findByPeliculaFechaEmisionNullAndSalaPremium (String premium);
//
//	
//	
//	//Consulta 3
//	@Query("SELECT e FROM Emision e WHERE e.pelicula.precio > :precioPeli ORDER BY e.palomitas DESC")
//	public List<Emision> emisionesPorPrecio(Double precioPeli);
//	
//	public List<Emision> findByPeliculaPrecioGreaterThanOrderByPalomitasDesc(Double precioPeli);
//	
//	
//	
//	//Consulta 4
//	@Query("SELECT e.sala.numero FROM Emision e WHERE e.visitantes = e.sala.aforo ORDER BY e.emisionId.fecha desc limit 1")
//	public Integer encuentraUltimaSalaLlena();
//	
//	//No merece la pena hacerlo con query method, debemos recorrer la lista de salas con un findAll() y por cada objeto sala, llamar a este metodo que recibe el aforo y el id de la sala.
//	public Emision findBySalaIdAndVisitantes(Long idSala, Integer aforo);
//	
//	
//	
//	//Consulta 6
//	@Query("SELECT e FROM Emision e ORDER BY e.emisionId.fecha DESC LIMIT 10")
//	public List<Emision> ultimasEmisiones();
//	
//	//¿Como limitarlo a 10? Top o First, si quisieras meterle un parametro extra de condicion es probable que no podrias hacerlo 
//	public List<Emision> findTop10ByEmisionIdFechaDesc();
//	
//	
//	
//	//Consulta 8
//	
//	@Query ("SELECT e FROM Emision e WHERE e.emisionId.idSala IN :idSalas")
//	public List<Emision> emisionesPorSalas(List<Long> idSalas);
//	
//	public List<Emision> findByEmisionIdIdSalaIn(List<Long> idSalas);
//	
//	
//	
//	//PRUEBA 
//	public List<Emision> findTop10ByVisitantesGreatherThanOrderByEmisionIdFechaDesc(Integer minAsistentes);
	
	
	//ESTADISTICAS
	
//	@Query("SELECT e.sala FROM Emision e WHERE e.visitantes = e.sala.aforo ORDER BY e.emisionId.fecha desc limit 1")
//	public Sala encuentraUltimaSalaLlenaEstadistica();
	

}

