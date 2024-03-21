package es.curso.cine.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "emision")
public class Emision implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2804658659941582858L;

	@EmbeddedId
	private EmisionId emisionId;
	
	@Column (name = "visitantes")
	private Integer visitantes;
	
	@Column (name = "palomitas", 
			columnDefinition = "DOUBLE(6,2)")
	private Double palomitas;
		
	@ManyToOne
	@JoinColumn(name = "id_pelicula", referencedColumnName = "id", insertable = false, updatable = false)
	private Pelicula pelicula;
	
	@ManyToOne
	@JoinColumn(name = "idSala", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Sala sala;
		
	@ManyToOne
	@JoinColumn (name = "id_acomodador", referencedColumnName = "id", nullable = true)
	private Acomodador acomodador;
	
	@ManyToOne
	@JoinColumn (name = "id_limpiador", referencedColumnName = "id", nullable = true)
	private Limpiador limpiador;
	
//	@OneToMany(mappedBy = "emision")
//	private Set<EspectadorEmision> espectadoresEmision;
}
