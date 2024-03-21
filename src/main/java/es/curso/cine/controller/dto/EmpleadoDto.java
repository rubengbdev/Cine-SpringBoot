package es.curso.cine.controller.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class EmpleadoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8331225215486188041L;

	private Long id;
	
	@Length(min = 1)
	private String nombreCompleto;
	
	@Length(min = 9, max = 9)
	private String telefonoMovil;
	
	@Email
	private String email;
}
