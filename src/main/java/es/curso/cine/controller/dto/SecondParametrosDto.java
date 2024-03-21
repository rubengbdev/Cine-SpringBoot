package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SecondParametrosDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -891131320095026002L;
	
	@NotNull
	private Integer numeroMinimoPeliculas;
	
	@NotNull
	private String genero1;
	
	@NotNull
	private String genero2;
	
	@NotNull
	private Double precioMinimo;
	
	@NotNull
	private Set<Long> idSalas;
}
