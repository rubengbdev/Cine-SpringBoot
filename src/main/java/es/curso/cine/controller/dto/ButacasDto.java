package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ButacasDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8162934332246602557L;

	private List<Butaca2Dto> disponibles;
	private List<Butaca2Dto> ocupadas;
}
