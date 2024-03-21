package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Butaca2Dto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2021494657755430334L;
	private Integer fila;
	private Integer columna;
	@Override
	public int hashCode() {
		return Objects.hash(columna, fila);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Butaca2Dto other = (Butaca2Dto) obj;
		return Objects.equals(columna, other.columna) && Objects.equals(fila, other.fila);
	}
	
	
}
