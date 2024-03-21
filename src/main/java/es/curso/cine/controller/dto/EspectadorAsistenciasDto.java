package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Min;
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
public class EspectadorAsistenciasDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4982263644831144692L;
	
	@Length(min = 1, message = "Nombre demasiado corto")
	private String nombreCompleto;
	
	@Past(message = "La fecha debe ser pasada")
	private LocalDate fechaNacimiento;
	
	@Min(value = 0, message = "Numero de emisiones")
	private Long numeroEmisiones;
}
