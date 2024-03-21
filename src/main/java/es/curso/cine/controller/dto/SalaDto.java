package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Min;

public class SalaDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1785659570910459936L;
	private Long id;
	
	@Min(0)
	private Integer numero;
	
	@Min(0)
	private Integer aforo;
	
	private Boolean premium;
	
	private Integer numeroFilas;
	
	private Integer numeroColumnas;
	
	private List<LocalDateTime> emisiones;
	
	public SalaDto() {
		super();	
	}

	public SalaDto(Long id, @Min(0) Integer numero, @Min(0) Integer aforo, Boolean premium, Integer numeroFilas,
			Integer numeroColumnas, List<LocalDateTime> emisiones) {
		super();
		this.id = id;
		this.numero = numero;
		this.aforo = aforo;
		this.premium = premium;
		this.numeroFilas = numeroFilas;
		this.numeroColumnas = numeroColumnas;
		this.emisiones = emisiones;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long long1) {
		this.id = long1;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getAforo() {
		return aforo;
	}

	public void setAforo(Integer aforo) {
		this.aforo = aforo;
	}

	public Boolean getPremium() {
		return premium;
	}

	public void setPremium(Boolean boolean1) {
		this.premium = boolean1;
	}

	public List<LocalDateTime> getEmisiones() {
		return emisiones;
	}

	public void setEmisiones(List<LocalDateTime> emisiones) {
		this.emisiones = emisiones;
	}

	public Integer getNumeroFilas() {
		return numeroFilas;
	}

	public void setNumeroFilas(Integer numeroFilas) {
		this.numeroFilas = numeroFilas;
	}

	public Integer getNumeroColumnas() {
		return numeroColumnas;
	}

	public void setNumeroColumnas(Integer numeroColumnas) {
		this.numeroColumnas = numeroColumnas;
	}
	
	
	
}
