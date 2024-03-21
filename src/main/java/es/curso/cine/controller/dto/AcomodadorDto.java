package es.curso.cine.controller.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcomodadorDto extends EmpleadoDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8859326776100786008L;

	@Min(0)
	@Max(10)
	private Double valoracionMedia;
	
	@Min(16)
	private Integer edad;
	
	@Min(0)
	private Integer antiguedad;
	

	
//	private Set<LocalDateTime> emisionesAcomodador;
}
