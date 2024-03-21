package es.curso.cine.controller.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

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
public class FirstParametrosDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5957977696585621842L;

	@NotNull
	@Length(max = 1, min = 1)
	private String premium;
	
	@NotNull
	private String genero;
	
	@NotNull
	private Double precioMinimo;
}
