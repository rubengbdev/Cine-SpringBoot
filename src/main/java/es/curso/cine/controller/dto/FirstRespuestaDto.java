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
public class FirstRespuestaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -110378751731056418L;

	private Set<PeliculaDto> peliculas;
	
	private Set<DirectorDto> directores;
	
	private Set<EmisionDto> emisiones;
		
	private SalaDto sala;
}
