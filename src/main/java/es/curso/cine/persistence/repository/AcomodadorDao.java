package es.curso.cine.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.curso.cine.persistence.entities.Acomodador;

public interface AcomodadorDao extends JpaRepository<Acomodador, Long>{

	@Query("SELECT a FROM Acomodador a WHERE a.nombreCompleto = :nombre")
	public Acomodador buscaAcomodadorPorNombre(String nombre); 
		
	Acomodador findByNombreCompleto(String nombreCompleto);
	
	Acomodador findByTelefonoMovil(String telefonoMovil);
	
	Acomodador findByEmail(String email);
		
}
