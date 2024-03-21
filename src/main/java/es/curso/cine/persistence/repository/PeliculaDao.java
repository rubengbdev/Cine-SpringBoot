package es.curso.cine.persistence.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.curso.cine.persistence.entities.Pelicula;
import es.curso.cine.persistence.entities.types.Genero;

public interface PeliculaDao extends JpaRepository<Pelicula, Long>{

	@Query ("SELECT p FROM Pelicula p WHERE p.duracion = (SELECT MAX(p2.duracion) FROM Pelicula p2)")
	public Pelicula encuentraMasLarga();

	@Query("SELECT e.pelicula FROM Emision e WHERE e.pelicula.fechaEmision IS NULL AND e.sala.premium = :premium")
	public Set<Pelicula> encontrarPeliculasPorPremium(String premium);
	
	@Query("SELECT p FROM Pelicula p WHERE (p.genero = :genero1 OR p.genero = :genero2) AND p.precio > :precio")
	Set<Pelicula> peliculaGeneroPorPrecio(Genero genero1, Genero genero2, Double precio);
	
	@Query("SELECT p.titulo FROM Pelicula p where p.id = :idPeli")
	String peliculaPorNombre(Long idPeli);
	
	Pelicula findByTitulo (String titulo);
	
	Page<Pelicula> findByCalificacionEdadLessThanEqualOrCalificacionEdadIsNull(Integer edad, Pageable pageable);
	
	//CAMBIO PELICULA ESPECTADOR CALIFICACION HACER
	Page<Pelicula> findByCalificacionEdadLessThanEqualOrCalificacionEdadIsNullAndFechaEmisionIsNull(Integer edad, Pageable pageable);

	@Query("SELECT p FROM Pelicula p ORDER BY p.titulo ASC")
	Page<Pelicula> findAllOrdenado(Pageable pageable);

	
											/*CONSULTAS DE PRUEBA*/
//	//Consulta 1
//	//JPQL
//	@Query("SELECT e.pelicula.titulo FROM Emision e WHERE e.pelicula.fechaEmision IS NULL AND e.sala.premium = :premium")
//	public Set<String> encontrarPorPremium(String premium);
//
//	//Consulta 2
//	//JPQL 
//	@Query("SELECT p.director.nombre FROM Pelicula p WHERE p.genero = upper(:genero)")
//	Set<String> buscaDirectorComedia(Genero genero); 
//	
//	//QueryMethod
//	List<Pelicula> findByGenero (Genero genero);
//	
//	//Consulta 7
//	@Query("SELECT p.titulo FROM Pelicula p WHERE (p.genero = :genero1 OR p.genero = :genero2) AND p.precio > :precio")
//	List<String> peliculaGeneroPorPrecio(String genero1, String genero2, Double precio);
//	
//	//Los query method siempre devuelven entidades
//	List<Pelicula> findByGeneroAndPrecioGreaterThanOrGeneroAndPrecioGreaterThan(Genero genero1, Genero genero2, Double precio);

}

