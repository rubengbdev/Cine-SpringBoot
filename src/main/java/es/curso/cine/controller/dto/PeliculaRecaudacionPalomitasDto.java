package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.time.LocalDate;

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
public class PeliculaRecaudacionPalomitasDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 44827908585411433L;
	
	@Length(min = 1,message = "longitud minima de 5 caracteres")
	private String titulo;
	
	@NotNull(message = "no puede ser null")
	private Double recaudacionMediaPalomitas;
}
