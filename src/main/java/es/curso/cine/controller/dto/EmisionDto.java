package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class EmisionDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3567858744185197548L;
	
	@NotNull (message = "numero de sala requerido")
	private Integer numeroSala;
	
	@NotNull (message = "titulo de pelicula requerido")
	private String tituloPelicula;
	
	@DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime fecha;
	
	private Integer visitantes;
	
	private Double palomitas;
	
	private String nombreAcomodador;
	
	@NotNull(message = "El nombre de limpiador es requerido")
	private String nombreLimpiador;
	
//	private Set<Long> espectadoresEmision;
//	private Set<String> espectadoresNombresEmision;
}
