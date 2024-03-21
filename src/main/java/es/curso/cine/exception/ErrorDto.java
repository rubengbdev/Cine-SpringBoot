package es.curso.cine.exception;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ErrorDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3083193202978697864L;
	private LocalDateTime fechaError;
	private String codigoError;
	private String descripcionError;
	
	public LocalDateTime getFechaError() {
		return fechaError;
	}
	public void setFechaError(LocalDateTime fechaError) {
		this.fechaError = fechaError;
	}
	public String getCodigoError() {
		return codigoError;
	}
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}
	public String getDescripcionError() {
		return descripcionError;
	}
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	
	
}
