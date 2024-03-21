package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.Past;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2680378939336991791L;
	
	private Long id;
	
	private String titulo;
	
	@Min(0)
	private Integer duracion;
	
	@Min(0)
	private Double precio;
	
	@Past
	private LocalDate fechaEmision;
	
	private String genero;
	
	private String director;
	
	private Integer calificacionEdad;

	
//	private List<LocalDateTime> emisiones;
}
