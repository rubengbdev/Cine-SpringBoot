package es.curso.cine.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="espectador_emision")
public class EspectadorEmision implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -925388067141043657L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	private Long id;
	
	@Column (name = "fila")
	@Min(0)
	private Integer fila;
	
	@Column (name = "columna")
	@Min(0)
	private Integer columna;
	
	@Column (name = "valoracion_servicio", columnDefinition = "DOUBLE(4,2)")
	private Double valoracionServicio;
	
	@Column (name = "gasto_palomitas",columnDefinition = "DOUBLE(6,2)")
	private Double gastoPalomitas;
	
	@ManyToOne
	@JoinColumn(name = "id_espectador", referencedColumnName = "id", nullable = false)
	private Espectador espectador;
	
	@ManyToOne
	@JoinColumns({
        @JoinColumn(
            name = "id_pelicula",
            referencedColumnName = "id_pelicula",nullable = false),
        @JoinColumn(
            name = "id_sala",
            referencedColumnName = "idSala", nullable = false),
        @JoinColumn(
            name = "fecha",
            referencedColumnName = "fecha", nullable = false)
    })
	private Emision emision;
}
