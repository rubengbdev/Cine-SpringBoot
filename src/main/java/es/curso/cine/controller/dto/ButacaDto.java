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
public class ButacaDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3916112444521083814L;

	private List<Integer[]> disponibles;
	private List<Integer[]> ocupadas;
}
