package es.curso.cine.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import es.curso.cine.persistence.entities.types.Turno;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="limpiador")
public class Limpiador extends Empleado {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4038905122341140537L;

	@Column(name = "turno", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Turno turno;
	
	@Column(name = "salario", nullable = false, columnDefinition = "DOUBLE(6,2)")
	@Min(0)
	@Max(2500)
	private Double salario;
	
//	@OneToMany(mappedBy = "limpiador", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//	private Set<Emision> emisionesLimpiador;
	
}
