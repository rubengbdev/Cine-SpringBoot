package es.curso.cine.persistence.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "director")
public class Director implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7466600382769339054L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	private Long id;
	
	@Column (name = "nombre", nullable = false, length = 50, columnDefinition = "VARCHAR(50) CHECK (LENGTH(nombre) >= 5)")
	@Length (min = 5, message = "longitud minima de 5 caracteres")
	//@Pattern (regexp = "^A.*", message = "No empieza por A")
	private String nombre;
	
	@Column (name = "numero_peliculas", nullable = false, columnDefinition = "INT CHECK (numero_peliculas > 0)")
	@Min(0)
	private Integer numPeliculas;
	
	@Column (name = "fecha_nac", nullable = false, columnDefinition = "DATE CHECK (fecha_nac <= SYSDATE())")
	@PastOrPresent(message = "la fecha debe ser pasada o presente, no futura")
	//Si fuese Date usariamos la anotacion extra @Temporal(TemporalType.DATE) que podemos poner TIME también para la hora
	private LocalDate fechaNacimiento;
	
//	@OneToMany(mappedBy = "director", cascade = CascadeType.ALL)
//	private List<Pelicula>  peliculas;
}
