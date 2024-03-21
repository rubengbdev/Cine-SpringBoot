package es.curso.cine.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.curso.cine.controller.dto.DirectorDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.controller.dto.PeliculaDto;
import es.curso.cine.controller.dto.PeliculaRecaudacionPalomitasDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.mapper.MapperService;
import es.curso.cine.persistence.entities.Pelicula;
import es.curso.cine.persistence.entities.types.Genero;
import es.curso.cine.persistence.repository.DirectorDao;
import es.curso.cine.persistence.repository.PeliculaDao;
import es.curso.cine.service.DirectoresService;
import es.curso.cine.service.EmisionesService;
import es.curso.cine.service.EspectadorEmisionService;
import es.curso.cine.service.PeliculasService;
import es.curso.cine.utils.CineErrorConstants;

@Service
@Transactional(rollbackFor = MiValidationException.class)
public class PeliculasServiceImpl implements PeliculasService {

	@Autowired
	DirectoresService directoresService;

	@Autowired
	EmisionesService emisionesService;

	@Autowired
	MapperService mapperServices;

	@Autowired
	@Lazy
	private PeliculaDao peliculaDao;

	@Autowired
	@Lazy
	private DirectorDao directorDao;

	@Autowired
	private EspectadorEmisionService espectadorEmisionService;

	/******************************* GET *******************************/

	@Override
	public PeliculaDto getPelicula(Long id) {

		Pelicula respuesta = new Pelicula();
		respuesta = peliculaDao.existsById(id) ? peliculaDao.findById(id).get() : null;

		return mapperServices.peliculaToDto(respuesta);
	}

	@Override
	public PagedResponseDto<PeliculaDto> getPeliculas(Pageable pageable) {
		
		Page<Pelicula> consulta = peliculaDao.findAll(pageable);
		
		if (consulta != null) {
			
			return new PagedResponseDto<PeliculaDto>(consulta, mapperServices::peliculaToDto);
		}
		
		return null;
		
//		Set<PeliculaDto> respuestaDto = new HashSet<PeliculaDto>();
//
//		respuesta = peliculaDao.findAll();
//
//		for (Pelicula pelicula : respuesta) {
//
//			respuestaDto.add(mapperServices.peliculaToDto(pelicula));
//		}
//
//		return (respuestaDto);
	}

	public Pelicula getPeliculaEntity(Long id) {

		Pelicula respuesta = new Pelicula();

		respuesta = peliculaDao.findById(id).get();

		return (respuesta);

	}

	/******************************* POST *******************************/

	@Override
	public Long createPelicula(PeliculaDto pelicula) throws MiValidationException {

		validarNuevaPelicula(pelicula);

		Pelicula creado = mapperServices.dtoToPelicula(pelicula);
		peliculaDao.save(creado);

		return (mapperServices.peliculaToDto(creado).getId());

	}

	private void validarNuevaPelicula(PeliculaDto pelicula) throws MiValidationException {

		if (directoresService.getDirectorByName(pelicula.getDirector()) == null)
			throw new MiValidationException(CineErrorConstants.DIRECTOR_INEXISTENTE, pelicula.getTitulo(),
					pelicula.getDirector());

		if (peliculaDao.findByTitulo(pelicula.getTitulo()) != null)
			throw new MiValidationException(CineErrorConstants.PELICULA_EXISTENTE, pelicula.getTitulo());

		if (!Genero.findByValue(pelicula.getGenero()))
			throw new MiValidationException(CineErrorConstants.PELICULA_GENERO_INEXISTENTE, pelicula.getGenero());
	}

	/******************************* PUT *******************************/

	@Override
	public Long updatePelicula(PeliculaDto pelicula) {

		Pelicula actualizado = new Pelicula();

		if (getPelicula(pelicula.getId()) != null) {

			actualizado = mapperServices.dtoToPelicula(pelicula);

			peliculaDao.save(actualizado);

			return (mapperServices.peliculaToDto(actualizado).getId());
		}

		return null;

	}

	/******************************* DELETE *******************************/

	@Override
	public void deletePelicula(Long id) {

		peliculaDao.deleteById(id);

	}

	/******************************* OTROS *******************************/

	@Override
	public PeliculaDto getPeliculaMasLarga() {

		return mapperServices.peliculaToDto(peliculaDao.encuentraMasLarga());
	}

	// Metodo para recorrer y bucar posicion
	public Integer buscaPosicion(List<String> listaPeliculas, PeliculaDto peliBuscada) {

		for (Integer i = 0; i < listaPeliculas.size(); i++) {

			if (listaPeliculas.get(i).equalsIgnoreCase(peliBuscada.getTitulo())) {

				return i;
			}
		}

		return null;
	}

	@Override
	public List<String> getPeliculasByDuration(Integer duration, String nombreDirector) {

//		List<String> peliculasDuration = new ArrayList<>();
//
//		Set<PeliculaDto> pelis = getPeliculas();
//
//		for (PeliculaDto peliLista : pelis) {
//
//			if (peliLista.getDirector().equalsIgnoreCase(nombreDirector) && peliLista.getDuracion() > duration) {
//
//				peliculasDuration.add(peliLista.getTitulo());
//			}
//		}
//
//		if (peliculasDuration.size() > 0) {
//
//			return peliculasDuration;
//		}
//
		return null;
	}

	@Override
	public List<Pelicula> getPeliculasByDirector(DirectorDto directorDto) {

		List<Pelicula> listaPeliculas = new ArrayList<>();

		for (Pelicula peliculaListada : peliculaDao.findAll()) {

			if (peliculaListada.getDirector().getNombre().equalsIgnoreCase(directorDto.getNombre())) {

				listaPeliculas.add(peliculaListada);
			}
		}

		return listaPeliculas;
	}

	// ESTADISTICAS

	@Override
	public Set<PeliculaDto> getPeliculasByPremium(String premium) {

		Set<PeliculaDto> peliculasDto = new HashSet<PeliculaDto>();

		for (Pelicula peliculaLista : peliculaDao.encontrarPeliculasPorPremium(premium)) {

			peliculasDto.add(mapperServices.peliculaToDto(peliculaLista));

		}

		return peliculasDto;
	}

	@Override
	public Set<PeliculaDto> getPeliculaGeneroPrecio(String genero1, String genero2, Double precio) {

		Set<PeliculaDto> peliculasDto = new HashSet<PeliculaDto>();

		for (Pelicula peliculaLista : peliculaDao.peliculaGeneroPorPrecio(Genero.valueOf(genero1),
				Genero.valueOf(genero2), precio)) {

			peliculasDto.add(mapperServices.peliculaToDto(peliculaLista));

		}

		return (peliculasDto);
	}

	@Override
	public String peliculaPorNombre(Long id) {

		return (peliculaDao.peliculaPorNombre(id));
	}

	@Override
	public Pelicula findByTitulo(String titulo) {

		return (peliculaDao.findByTitulo(titulo));
	}

	public Set<PeliculaDto> getPeliculasPorCalificacionDeEspectador(Long idEspectador) {

		return null;
	}

	@Override
	public PagedResponseDto<PeliculaDto> peliculasPorEdad(Integer edad, Pageable pageable) {

		Page<Pelicula> consulta = peliculaDao.findByCalificacionEdadLessThanEqualOrCalificacionEdadIsNull(edad,pageable);
		
		if (consulta != null) {
			
			return new PagedResponseDto<PeliculaDto>(consulta, mapperServices::peliculaToDto);
		}
		
		return null;
		
//		PagedResponseDto<PeliculaDto> respuesta = new HashSet<PeliculaDto>();
//
//		for (Pelicula elemento : consulta) {
//			respuesta.add(mapperServices.peliculaToDto(elemento));
//		}
//
//		return respuesta;
	}

	@Override
	public Page<Pelicula> peliculasCarteleraEdadEspectador(Integer edad, Pageable pageable) {

		return peliculaDao.findByCalificacionEdadLessThanEqualOrCalificacionEdadIsNullAndFechaEmisionIsNull(edad, pageable);
//		return (mapperServices.setPeliculaToSetPeliculaDto(
//				peliculaDao.findByCalificacionEdadLessThanEqualOrCalificacionEdadIsNullAndFechaEmisionIsNull(edad)));
	}

	@Override
	public List<PeliculaRecaudacionPalomitasDto> getPeliculasRecaudacionPalomita() {

		//V1 REPO en una consulta
//		return espectadorEmisionService.getPeliculasRecaudacionPalomita();

		//V2 REPO con dos consultas + java
		List<Pelicula> peliculas = peliculaDao.findAll();
		List<PeliculaRecaudacionPalomitasDto> peliculasConRecaudacion = new ArrayList<PeliculaRecaudacionPalomitasDto>();
		for (Pelicula elemento : peliculas) {

			Double palomitasPelicula = emisionesService.recaudacionPorPelicula(elemento.getTitulo()) == null ? null :emisionesService.recaudacionPorPelicula(elemento.getTitulo());
			Integer espectadoresPelicula = emisionesService.espectadoresPorPelicula(elemento.getTitulo()) == null ? null :emisionesService.espectadoresPorPelicula(elemento.getTitulo());
			Double recaudacionMedia;

			if (espectadoresPelicula == null || espectadoresPelicula == 0) {
				recaudacionMedia = 0.00 ;
			} else {
				recaudacionMedia = palomitasPelicula / espectadoresPelicula;
			}
			
			peliculasConRecaudacion.add(new PeliculaRecaudacionPalomitasDto(elemento.getTitulo(), recaudacionMedia));
		}

		return peliculasConRecaudacion;
	}
}
