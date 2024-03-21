package es.curso.cine.mapper;

import java.util.List;
import java.util.Set;

import es.curso.cine.controller.dto.AcomodadorDto;
import es.curso.cine.controller.dto.DirectorDto;
import es.curso.cine.controller.dto.DirectorRecaudacionEntradasDto;
import es.curso.cine.controller.dto.EmisionDto;
import es.curso.cine.controller.dto.EmisionNuevaDto;
import es.curso.cine.controller.dto.EspectadorAsistenciasDto;
import es.curso.cine.controller.dto.EspectadorDto;
import es.curso.cine.controller.dto.EspectadorEmisionDto;
import es.curso.cine.controller.dto.LimpiadorDto;
import es.curso.cine.controller.dto.NuevaEntradaDto;
import es.curso.cine.controller.dto.PeliculaDto;
import es.curso.cine.controller.dto.SalaDto;
import es.curso.cine.persistence.entities.Acomodador;
import es.curso.cine.persistence.entities.Director;
import es.curso.cine.persistence.entities.Emision;
import es.curso.cine.persistence.entities.Espectador;
import es.curso.cine.persistence.entities.EspectadorEmision;
import es.curso.cine.persistence.entities.Limpiador;
import es.curso.cine.persistence.entities.Pelicula;
import es.curso.cine.persistence.entities.Sala;

public interface MapperService {

	//Pelicula
	PeliculaDto peliculaToDto(Pelicula pelicula);
	Pelicula dtoToPelicula(PeliculaDto peliculaDto);
	
	//Director
	DirectorDto directorToDto(Director director);
	Director dtoToDirector (DirectorDto directorDto);
	
	//Emision
	EmisionDto emisionToDto (Emision emision);
	Emision dtoToEmision(EmisionDto emisionDto);
	
	//Sala
	SalaDto salaToDto (Sala sala);
	Sala dtoToSala (SalaDto salaDto);
	
	//Limpiador
	LimpiadorDto limpiadorToDto (Limpiador limpiardor);
	Limpiador dtoToLimpiador (LimpiadorDto limpiadorDto);
	
	//Acomodador
	AcomodadorDto acomodadorToDto (Acomodador acomodador);
	Acomodador dtoToAcomodador (AcomodadorDto acomodadorDto);
	
	//Espectador
	EspectadorDto espectadorToDto (Espectador espectador);
	Espectador dtoToEspectador (EspectadorDto espectadorDto);
	
	//EspectadorEmision
	EspectadorEmisionDto espectadorEmisionToDto (EspectadorEmision espectadorEmisionDto);
	EspectadorEmision dtoToEspectadorEmision (EspectadorEmisionDto espectadorEmisionDto);

	//Convertidor de listas
	Set<PeliculaDto> setPeliculaToSetPeliculaDto(Set<Pelicula> peliculas);
	Set<EmisionDto> setEmisionToSetEmisionDto(Set<Emision> emisiones);
	Set<EspectadorEmisionDto> listEspectadorEmisionToSetEspectadorEmisionDto(List<EspectadorEmision> lista);

	//NuevaEntradaDto
	NuevaEntradaDto espectadorEmisionToNuevaEntradaDto(EspectadorEmision espectadorEmision);
	EspectadorEmision nuevaEntradaDtoToEspectadorEmision(NuevaEntradaDto nuevaEntrada);

	//EmisionNuevaDto
	EmisionNuevaDto emisionNuevaToDto(Emision emision);
	Emision emisionNuevaDtoToEmision(EmisionNuevaDto emisionDto);

	//EspectadorAsistenciasDto
	Espectador espectadorAsistenciasDtoToEspectador(EspectadorAsistenciasDto espectadorAsistenciasDto);
	EspectadorAsistenciasDto espectadorToEspectadorAsistenciaDto(Espectador espectador);

	//DirectorRecaudacionEntradasDto
	DirectorRecaudacionEntradasDto directorToDirectorRecaudacionEntradasDto(Director director);
	Director directorRecaudacionEntradasDtoToDirector(DirectorRecaudacionEntradasDto directorRecaudacionEntradasDto);
}
