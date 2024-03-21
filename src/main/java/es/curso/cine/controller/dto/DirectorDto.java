package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1064931209374283678L;
	
	private Long id;

	@NotNull(message = "no puede ser nulo el nombre de director")
	@Length(min = 1, message = "nombre director requerido de longitud mayor")
	private String nombre;

	@Min(0)
	private Integer numPeliculas;
	
	@Past
	private LocalDate fechaNacimiento;
	
//	private List<String>  peliculas;
}
