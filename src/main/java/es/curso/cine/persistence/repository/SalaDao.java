package es.curso.cine.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.curso.cine.persistence.entities.Sala;

public interface SalaDao  extends JpaRepository<Sala, Long>{

	Sala findByNumero (Integer numero);
	
//	@Query("SELECT e.sala FROM Emision e WHERE e.visitantes = e.sala.aforo ORDER BY e.emisionId.fecha desc limit 1")
//	public Sala encuentraUltimaSalaLlenaEstadistica();
	@Query("SELECT e.sala FROM Emision e WHERE e.visitantes = e.sala.aforo ORDER BY e.emisionId.fecha desc")
	public List<Sala> encuentraUltimaSalaLlenaEstadistica();
	
	@Query("SELECT s.numero FROM Sala s WHERE s.id = :idSala")
	public Integer numeroSalaPorId(Long idSala);
}
