package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SecondRespuestaDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1232920659648202142L;

	private Integer numDirectores;
	
	private Set<EmisionDto> ultimasEmisiones;
	
	private Set<PeliculaDto> peliculasPorGeneroPrecio;
	
	private Set<EmisionDto> emisionesPorSala;
}
