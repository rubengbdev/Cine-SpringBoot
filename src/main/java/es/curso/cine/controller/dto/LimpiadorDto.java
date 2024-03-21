package es.curso.cine.controller.dto;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LimpiadorDto extends EmpleadoDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8944662253938354364L;

	private Integer turno;

	@Min(0)
	private Double salario;
	
//	private Set<LocalDateTime> emisionesLimpiador;
}
