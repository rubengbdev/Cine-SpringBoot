package es.curso.cine.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="acomodador")
public class Acomodador extends Empleado {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8871857923178426117L;

	@Column(name = "valoracion_media", columnDefinition = "DOUBLE(4,2)")
	@Min(0)
	@Max(10)
	private Double valoracionMedia;
	
	@Column(name = "edad", nullable = false)
	@Min(14)
	private Integer edad;
	
	@Column(name = "antiguedad")
	@Min(0)
	@Max(70)
	private Integer antiguedad;
	
//	@OneToMany(mappedBy = "acomodador", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//	private Set<Emision> emisionesAcomodador;

	public Acomodador() {
		super();
	}

	public Acomodador(Long id, String nombreCompleto, String telefonoMovil, String email,
			@Min(0) @Max(10) Double valoracionMedia, @Min(14) Integer edad, @Min(0) @Max(70) Integer antiguedad) {
		super(id, nombreCompleto, telefonoMovil, email);
		this.valoracionMedia = valoracionMedia;
		this.edad = edad;
		this.antiguedad = antiguedad;
	}
}
