package es.curso.cine.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

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
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS )
public abstract class Empleado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2676117136537218394L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column (name = "id")
	private Long id;
	
	@Column (name = "nombre_completo", nullable = false, unique = true)
	@Length (min = 5, message = "longitud minima de 5 caracteres")
	@NotNull
	private String nombreCompleto;

	@Column (name = "telefono_movil", nullable = false)
	@Size(min = 9 , max = 13)
	@Pattern(regexp = "^[6][0-9]{8}$",message = "No es un telefono")
	private String telefonoMovil;
	
	@Column (name = "email", nullable = false)
	@Email (message = "no es un email")
	@Length (min = 5, message = "longitud minima de 5 caracteres")
	private String email;
}
