package es.curso.cine.persistence.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable 
public class EmisionId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1522219867737514698L;

	@Column (name = "idSala", nullable = false)
	private Long idSala;
	
	@Column (name = "id_pelicula", nullable = false)
	private Long idPelicula;
	
	@Column (name = "fecha", nullable = false)
	private LocalDateTime fecha;

	
	
	
	
	public EmisionId() {
		super();
	}

	public EmisionId(Long idSala, Long idPelicula, LocalDateTime fecha) {
		super();
		this.idSala = idSala;
		this.idPelicula = idPelicula;
		this.fecha = fecha;
	}

	
	
	
	
	public Long getIdSala() {
		return idSala;
	}

	public void setIdSala(Long idSala) {
		this.idSala = idSala;
	}

	public Long getIdPelicula() {
		return idPelicula;
	}

	public void setIdPelicula(Long idPelicula) {
		this.idPelicula = idPelicula;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;	}
	
}
