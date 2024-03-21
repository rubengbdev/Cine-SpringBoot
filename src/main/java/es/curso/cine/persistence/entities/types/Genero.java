package es.curso.cine.persistence.entities.types;

public enum Genero {
	
	THRILLER, POLICIACA, ACCION, GANGSTERS, COMEDIA, ANIMACION;
	
	public static Boolean findByValue(String genero) {
		
		for (Genero elemento : Genero.values()) {
			
			if (elemento.name().equals(genero)) {
				
				return true;
			}
		}
			
		return false;
	}
}
