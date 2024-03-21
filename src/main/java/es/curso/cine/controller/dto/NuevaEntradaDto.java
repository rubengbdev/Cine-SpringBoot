package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NuevaEntradaDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9176120541702650643L;
	
	private Long id;
	
	@Min(value = 0, message = "fila requerida")
	private Integer fila;
	
	@Min(value = 0, message = "columna requerida")
	private Integer columna;
	
	@NotNull(message = "El nombre de espectador es requerido")
	private String nombreEspectador;
	
	@NotNull(message = "fecha no puede ser null")
	@Future(message = "Fecha debe ser en el futuro")
	@DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) 
	private LocalDateTime fechaEmision;
	
	@NotNull(message ="titulo de pelicula no puede ser null")
	private String nombrePelicula;
	
	@NotNull(message = "numero de sala no puede ser null")
	private Integer numeroSala;
}
