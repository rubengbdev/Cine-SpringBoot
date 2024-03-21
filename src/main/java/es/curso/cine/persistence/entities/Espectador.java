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
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

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
@Table(name="espectador")
public class Espectador implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8790552018621053440L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	private Long id;
	
	@Column (name = "nombre_completo", nullable = false, unique = true) 
	@Length (min = 5, message = "longitud minima de 5 caracteres")
	private String nombreCompleto;
	
	@Column (name = "fecha_nacimiento", nullable = false)
	@Past(message = "la fecha de nacimiento debe ser en pasado")
	private LocalDate fechaNacimiento;
	
	@Column (name = "genero_favorito", nullable = false)
	@Enumerated (EnumType.STRING)
	private Genero generoFavorito;
	
	@Column (name = "dinero_gastado")
	@Min(0)
	private Double dineroGastado;
	
//	//relacion
//	@OneToMany(mappedBy = "espectador", fetch = FetchType.LAZY)
//	private Set<EspectadorEmision> emisionesEspectador;
}
