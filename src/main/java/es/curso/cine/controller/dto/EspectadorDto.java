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
public class EspectadorDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2701897266788501849L;
	
	private Long id;
	
	@Length(min = 1)
	private String nombreCompleto;
	
	@Past
	private LocalDate fechaNacimiento;
	
	private String generoFavorito;
	
	@Min(0)
	private Double dineroGastado;
	
//	private Set<Long> emisionesEspectador;
}
