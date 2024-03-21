package es.curso.cine.persistence.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;

import es.curso.cine.persistence.entities.types.Genero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "pelicula")
public class Pelicula implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1418257845450905358L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	private Long id;
	
	@Column (name = "titulo", nullable = false, length = 50, unique = true, columnDefinition = "VARCHAR(50) CHECK (LENGTH(titulo) >= 5)")
	//@Length(min = 5,message = "longitud minima de 5 caracteres")
	private String titulo;
	
	@Column (name = "duracion", columnDefinition = "INT CHECK (duracion >= 0)")
	@Min(0)
	private Integer duracion;
	
	@Column (name = "precio", nullable = false, columnDefinition = "DOUBLE(6,2)") // precision = 6, scale = 2
	@Min(0)
	private Double precio;
	
	@Column (name = "fecha_fin_emision", nullable = true, columnDefinition = "DATE CHECK (fecha_fin_emision <= SYSDATE())")
	@PastOrPresent(message = "la fecha debe ser pasada o presente, no futura")
	private LocalDate fechaEmision;
	
	@Column (name = "genero", nullable = false)
	@Enumerated (EnumType.STRING)
	private Genero genero;
	
	@ManyToOne
	@JoinColumn(name = "id_director", referencedColumnName = "id")
	private Director director;
	
//	@OneToMany(mappedBy = "pelicula", cascade = CascadeType.REMOVE)
//	private List<Emision> emisiones = new ArrayList<Emision>();
	
	@Column (name = "calificacion_edad", nullable = false)
	@Min(0)
	private Integer calificacionEdad;

	//CUIDADO CON ESTO PREGUNTAR A CARLOS LOMBOK SI AL CREAR YO EL GETTER LO REESCRIBO O USA EL OTRO
	public Integer getCalificacionEdad() {
		
		if (calificacionEdad == null) {
			return 0;
		} else {
			return calificacionEdad;

		}
	}
}
