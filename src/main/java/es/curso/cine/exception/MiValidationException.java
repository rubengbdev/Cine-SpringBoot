package es.curso.cine.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MiValidationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3952138050737841701L;

	private final LocalDateTime fechaError;
	private final String claveError;
	private final List<String> params;
	
	public MiValidationException(String claveError) {
		super();
		this.fechaError = LocalDateTime.now();
		this.claveError = claveError;
		this.params = new ArrayList<>();
	}
	
	public MiValidationException(String claveError, String ... params) {
		super();
		this.fechaError = LocalDateTime.now();
		this.claveError = claveError;
		this.params = Arrays.asList(params);
	}
	

	public LocalDateTime getFechaError() {
		return fechaError;
	}

	public String getClaveError() {
		return claveError;
	}

	public List<String> getParams() {
		return params;
	}


	
	
}
