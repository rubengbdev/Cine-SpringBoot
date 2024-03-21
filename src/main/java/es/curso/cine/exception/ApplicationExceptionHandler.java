package es.curso.cine.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(MiValidationException.class)
	public ResponseEntity<ErrorDto> controlExcepcionesPropias(MiValidationException ex) {
		
		Locale locale = new Locale("es");
		ErrorDto dto = new ErrorDto();
		
		dto.setFechaError(ex.getFechaError());
		dto.setCodigoError(StringUtils.substringBetween(ex.getClaveError(), "error.code." , "."));
		
		if (ex.getParams().isEmpty()) {
			
			dto.setDescripcionError(this.messageSource.getMessage(ex.getClaveError(), null, locale));
		} else {
			
			dto.setDescripcionError(this.messageSource.getMessage(ex.getClaveError(),ex.getParams().toArray(), locale));
		}
		
		return (ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto));
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errores = new ArrayList<>();
		
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			
			errores.add(error.getDefaultMessage());
		}
		
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			
			errores.add(error.getDefaultMessage());
		}
		
		ErrorDto errorDto = new ErrorDto();
		
		errorDto.setFechaError(LocalDateTime.now());
		errorDto.setDescripcionError(String.join(", ", errores));
		
		return handleExceptionInternal(ex, errorDto, headers, HttpStatus.BAD_REQUEST, request);
	}
}
