package es.curso.cine.controller.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectorRecaudacionEntradasDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7804205469547469418L;
	@NotNull(message = "no puede ser nulo el nombre de director")
	@Length(min = 1, message = "nombre director requerido de longitud mayor")
	private String nombre;
	
	private Double recaudacionEntradas;
}
