package es.curso.cine.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "sala")
public class Sala implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8242112935587148959L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")	
	private Long id;
	
	@Column (name = "numero", nullable = false, unique = true)
	private Integer numero;
	
	@Column (name = "aforo", columnDefinition = "INT CHECK (aforo > 0)")
	@Min(0)
	private Integer aforo;
	
	@Column (name = "premium", nullable = false, length = 1, columnDefinition = "VARCHAR (50) CHECK (LENGTH(premium) == 1)")
	@Length (min = 1, max = 1)
	private String premium;
	
	@Column (name = "numero_filas", nullable = false)
	@NotNull(message = "numero filas no nulo")
	private Integer numeroFilas;
	
	@Column (name = "numero_columnas", nullable = false)
	@NotNull(message = "numero columnas no nulo")
	private Integer numeroColumnas;
	
//	@OneToMany(mappedBy = "sala", cascade = CascadeType.REMOVE)
//	@NotNull(message = "debe haber emisiones")
//	private List<Emision> emisiones;

	public Boolean getPremium() {
		return premium.equals("S");
	}
	public void setPremium(Boolean premium) {
		
		this.premium = premium ? "S" : "N";
		
	}
}
