package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EspectadorEmisionDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9176120541702650643L;
	
	private Long id;
	
	@Min(value = 0, message = "fila requerida")
	private Integer fila;
	
	@Min(value = 0, message = "columna requerida")
	private Integer columna;
	
	@Min(value = 0, message = "valor por debajo del minimo 0 en valoracion")
	@Max(value = 10, message = "valor por encima del maximo 10 en valoracion")
	private Double valoracionServicio;
	
	@Min(value = 0, message = "valor por debajo del minimo 0 en gasto de palomitas")
	
	private Double gastoPalomitas;
	
	@NotNull(message = "El nombre de espectador es requerido")
	private String nombreEspectador;
	
	@NotNull(message = "fecha no puede ser null")
	@DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime fechaEmision;
	
	@NotNull(message ="titulo de pelicula no puede ser null")
	private String nombrePelicula;
	
	@NotNull(message = "numero de sala no puede ser null")
	private Integer numeroSala;
	
	
	
	
	
	public EspectadorEmisionDto() {
		super();
	}

	public EspectadorEmisionDto(Long id, Integer fila, Integer columna, Double valoracionServicio,
			Double gastoPalomitas, String nombreEspectador, LocalDateTime fechaEmision, String nombrePelicula,
			Integer numeroSala) {
		super();
		this.id = id;
		this.fila = fila;
		this.columna = columna;
		this.valoracionServicio = valoracionServicio;
		this.gastoPalomitas = gastoPalomitas;
		this.nombreEspectador = nombreEspectador;
		this.fechaEmision = fechaEmision;
		this.nombrePelicula = nombrePelicula;
		this.numeroSala = numeroSala;
	}

	public EspectadorEmisionDto( Integer fila, Integer columna, Double valoracionServicio,
			Double gastoPalomitas, String nombreEspectador, LocalDateTime fechaEmision, String nombrePelicula,
			Integer numeroSala) {
		super();
		this.fila = fila;
		this.columna = columna;
		this.valoracionServicio = valoracionServicio;
		this.gastoPalomitas = gastoPalomitas;
		this.nombreEspectador = nombreEspectador;
		this.fechaEmision = fechaEmision;
		this.nombrePelicula = nombrePelicula;
		this.numeroSala = numeroSala;
	}	
}
