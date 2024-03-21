package es.curso.cine.persistence.entities.types;

import java.time.LocalDateTime;

import es.curso.cine.utils.CineConstants;

public enum Turno {
	
	MANANA,TARDE,NOCHE;
	
	public static Boolean findByValue(Integer turno) {
		
		for (Turno elemento : Turno.values()) {
			
			if (elemento.ordinal() == turno) {
				
				return true;
			}
		}

		return false;
	}
	
	public static Boolean compruebaHorario(LocalDateTime fecha, String turno) {
	
		Integer hora = fecha.getHour();
		Boolean horarioBien = false;
		
		switch (turno) {
		
			case "MANANA": if (hora >= CineConstants.Horarios.INICIO_MANANA && hora < CineConstants.Horarios.FINAL_MANANA) horarioBien = true;
						   break;
				
			case "TARDE": if (hora >= CineConstants.Horarios.INICIO_TARDE && hora < CineConstants.Horarios.FINAL_TARDE) horarioBien = true;
					      break;
				
			case "NOCHE": if (hora >= CineConstants.Horarios.INICIO_NOCHE || hora < CineConstants.Horarios.FINAL_NOCHE) horarioBien = true;
					      break;
				
			default: horarioBien = false;
		}
		
		return (horarioBien);
	}
}
