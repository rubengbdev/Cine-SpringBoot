package es.curso.cine.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.curso.cine.persistence.entities.Limpiador;

public interface LimpiadorDao extends JpaRepository<Limpiador, Long>{
	
	@Query("SELECT l FROM Limpiador l WHERE l.nombreCompleto = :nombre")
	public Limpiador buscaLimpiadorPorNombre(String nombre);
	
	Limpiador findByNombreCompleto(String nombreCompleto);
	
	Limpiador findByTelefonoMovil(String telefonoMovil);
	
	Limpiador findByEmail(String email);
	
}
