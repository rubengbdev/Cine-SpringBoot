package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Future;
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
public class EmisionNuevaDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2242156179982943799L;

	@NotNull (message = "numero de sala requerido")
	private Integer numeroSala;
	
	@NotNull (message = "titulo de pelicula requerido")
	private String tituloPelicula;
	
	@Future (message = "no puede ser pasada o presente")
	@DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime fecha;
	
	private String nombreAcomodador;
	
	@NotNull(message = "el nombre de limpiador es requerido")
	private String nombreLimpiador;
	
//	private Set<Long> espectadoresEmision;
}
