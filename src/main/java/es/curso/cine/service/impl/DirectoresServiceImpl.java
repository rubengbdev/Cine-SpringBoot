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
import es.curso.cine.controller.dto.DirectorRecaudacionEntradasDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.mapper.MapperService;
import es.curso.cine.persistence.entities.Director;
import es.curso.cine.persistence.entities.types.Genero;
import es.curso.cine.persistence.repository.DirectorDao;
import es.curso.cine.service.DirectoresService;
import es.curso.cine.service.EspectadorEmisionService;
import es.curso.cine.service.PeliculasService;
import es.curso.cine.utils.CineErrorConstants;

@Service
@Transactional(rollbackFor = MiValidationException.class)
public class DirectoresServiceImpl implements DirectoresService {

	@Autowired
	@Lazy
	private PeliculasService peliculasService;

	@Autowired
	@Lazy
	private DirectoresService directoresService;

	@Autowired
	private MapperService mapperServices;

	@Autowired
	@Lazy
	private DirectorDao directorDao;

	@Autowired
	@Lazy
	private EspectadorEmisionService espectadorEmisionService;

	/******************************* GET *******************************/

	@Override
	public DirectorDto getDirector(Long id) {

		Director respuesta = new Director();
		respuesta = directorDao.existsById(id) ? directorDao.findById(id).get() : null;

		return mapperServices.directorToDto(respuesta);
	}

	@Override
	public Set<DirectorDto> getDirectores() {

		List<Director> respuesta = new ArrayList<Director>();
		Set<DirectorDto> respuestaDto = new HashSet<DirectorDto>();

		respuesta = directorDao.findAll();

		for (Director director : respuesta) {

			respuestaDto.add(mapperServices.directorToDto(director));

		}

		return (respuestaDto);

	}

	@Override
	public PagedResponseDto<DirectorDto> getDirectores(Pageable pageable) {
		Page<Director> consulta = directorDao.findAll(pageable);

		if (consulta != null) {

			return new PagedResponseDto<DirectorDto>(consulta, mapperServices::directorToDto);
		}

		return null;
	}

	/******************************* POST *******************************/

	@Override
	public Long createDirector(DirectorDto director) throws MiValidationException {

		validarNuevoDirector(director);

		Director creado = mapperServices.dtoToDirector(director);

		directorDao.save(creado);

		return (mapperServices.directorToDto(creado).getId());

	}

	private void validarNuevoDirector(DirectorDto directorDto) throws MiValidationException {

		if (directorDao.findByNombre(directorDto.getNombre()) != null)
			throw new MiValidationException(CineErrorConstants.DIRECTOR_EXISTENTE, directorDto.getNombre());
	}

	/******************************* UPDATE *******************************/

	@Override
	public Long updateDirector(DirectorDto director) {

		Director actualizado = new Director();

		if (getDirector(director.getId()) != null) {

			actualizado = mapperServices.dtoToDirector(director);

			directorDao.save(actualizado);

			return (mapperServices.directorToDto(actualizado).getId());

		}

		return null;
	}

	/******************************* DELETE *******************************/

	@Override
	public void deleteDirector(Long id) {

		directorDao.deleteById(id);

	}

	@Override
	public void deleteDirectorPelicula(Integer id) {
//		
//		for(PeliculaDto peliculaLista : peliculasService.getPeliculas()) {
//		
//			if (mapperServices.dtoToPelicula(peliculaLista).getDirector().getId() == id) {
//		
//				peliculaLista.setDirector(null);
//			
//				peliculasService.updatePelicula(peliculaLista);
//				
//				deleteDirector(id);
//				
//			}
//			
//		}

	}

	/******************************* OTROS *******************************/

	@Override
	public Director getDirectorByName(String director) {

		return directorDao.findByNombre(director);
	}

	public List<String> getPeliculasDirector(Director director) {

		List<String> listaPeliculas = new ArrayList<>();
//		
//		for (PeliculaDto pelicula : peliculasService.getPeliculas() ) {
//			
//			if (pelicula.getDirector().equalsIgnoreCase(director.getNombre())) {
//				
//				listaPeliculas.add(pelicula.getTitulo());
//			}
//			
//		}
//				
		return listaPeliculas;
	}

	// ESTADISTICAS

	@Override
	public Set<DirectorDto> getDirectoresByGenero(String genero) {

		Set<DirectorDto> directoresDto = new HashSet<DirectorDto>();

		for (Director directorLista : directorDao.buscaDirectorPorGenero(Genero.valueOf(genero))) {

			directoresDto.add(mapperServices.directorToDto(directorLista));
		}

		return (directoresDto);

	}

	@Override
	public Integer directoresConMasPelisDe(Integer numeroPeliculas) {

		return (directorDao.directoresConMasPelisDe(numeroPeliculas));

	}

	@Override
	public List<DirectorRecaudacionEntradasDto> recaudacionDirectores() {

		// V1 convirtiendo por java pero no ordenado
		List<Director> directorLista = directorDao.findAll();
		List<DirectorRecaudacionEntradasDto> respuesta = new ArrayList<DirectorRecaudacionEntradasDto>();

		for (Director elemento : directorLista) {

			respuesta.add(mapperServices.directorToDirectorRecaudacionEntradasDto(elemento));
		}

		return respuesta;

		// V2 repo
//		return (espectadorEmisionService.recaudacionDirectores());
	}

	@Override
	public Double getRecaudacionPorDirector(String nombre) {

		return (espectadorEmisionService.getRecaudacionPorDirector(nombre));
	}
}
