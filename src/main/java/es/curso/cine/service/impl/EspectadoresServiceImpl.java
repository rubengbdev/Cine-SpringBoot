package es.curso.cine.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.curso.cine.controller.dto.DirectorDto;
import es.curso.cine.controller.dto.EspectadorAsistenciasDto;
import es.curso.cine.controller.dto.EspectadorDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.mapper.MapperService;
import es.curso.cine.persistence.entities.Director;
import es.curso.cine.persistence.entities.Espectador;
import es.curso.cine.persistence.entities.types.Genero;
import es.curso.cine.persistence.repository.EspectadorDao;
import es.curso.cine.service.EspectadorEmisionService;
import es.curso.cine.service.EspectadoresService;
import es.curso.cine.utils.CineErrorConstants;

@Service
@Transactional(rollbackFor = MiValidationException.class)
public class EspectadoresServiceImpl implements EspectadoresService {

	@Autowired
	@Lazy
	private MapperService mapperServices;

	@Autowired
	@Lazy
	private EspectadorDao espectadorDao;

	@Autowired
	@Lazy
	private EspectadorEmisionService espectadorEmisionService;

	/******************************* GET *******************************/

	@Override
	public EspectadorDto getEspectador(Long idEspectador) {

		return (mapperServices.espectadorToDto(espectadorDao.findById(idEspectador).get()));

	}

	@Override
	public Set<EspectadorDto> getEspectadores() {
		List<Espectador> respuesta = new ArrayList<Espectador>();
		Set<EspectadorDto> respuestaDto = new HashSet<EspectadorDto>();

		respuesta = espectadorDao.findAll();

		for (Espectador espectador : respuesta) {

			respuestaDto.add(mapperServices.espectadorToDto(espectador));

		}

		return (respuestaDto);
	}
	

	@Override
	public PagedResponseDto<EspectadorDto> getEspectadores(Pageable pageable) {
		
		Page<Espectador> consulta = espectadorDao.findAllByName(pageable);

		if (consulta != null) {

			return new PagedResponseDto<EspectadorDto>(consulta, mapperServices::espectadorToDto);
		}

		return null;
	}


	/******************************* POST *******************************/

	@Override
	public Long createEspectador(EspectadorDto espectadorDto) throws MiValidationException {

		validarNuevoEspectador(espectadorDto);

		Espectador creado = new Espectador();

		creado = mapperServices.dtoToEspectador(espectadorDto);

		espectadorDao.save(creado);

		return (mapperServices.espectadorToDto(creado).getId());
	}

	private void validarNuevoEspectador(EspectadorDto espectadorDto) throws MiValidationException {

		if (espectadorDao.findByNombreCompleto(espectadorDto.getNombreCompleto()) != null)
			throw new MiValidationException(CineErrorConstants.ESPECTADOR_NOMBRE_EXISTENTE,
					espectadorDto.getNombreCompleto());

		if (!Genero.findByValue(espectadorDto.getGeneroFavorito()))
			throw new MiValidationException(CineErrorConstants.PELICULA_GENERO_INEXISTENTE,
					espectadorDto.getGeneroFavorito());
	}

	/******************************* PUT *******************************/

	@Override
	public Long updateEspectador(EspectadorDto espectadorDto) {

		Espectador actualizado = new Espectador();

		if (getEspectador(espectadorDto.getId()) != null) {

			actualizado = espectadorDao.save(mapperServices.dtoToEspectador(espectadorDto));

			return (mapperServices.espectadorToDto(actualizado).getId());

		}

		return (null);
	}

	/******************************* DELETE *******************************/

	@Override
	public void deleteEspectador(Long idEspectador) {

		espectadorDao.deleteById(idEspectador);
	}

	/******************************* OTROS *******************************/

	@Override
	public Espectador espectadorPorNombre(String nombre) {

		return (espectadorDao.buscaEspectadorPorNombre(nombre));
	}

	@Override
	public Espectador espectadorPorId(Long id) {

		return (espectadorDao.findById(id).get());
	}

	@Override
	public List<EspectadorAsistenciasDto> getAsistenciasEspectadores() {

//		List<Espectador> respuesta = new ArrayList<Espectador>();
//		List<EspectadorAsistenciasDto> respuestaDto = new ArrayList<EspectadorAsistenciasDto>();
//
//		respuesta = espectadorDao.findAll();
//		
//		for (Espectador espectador: respuesta ) {
//					
//			respuestaDto.add(mapperServices.espectadorToEspectadorAsistenciaDto(espectador));
//		}		
//		
//		respuestaDto.sort((a,b) -> a.getNumeroEmisiones() > b.getNumeroEmisiones() ? -1 : 1);
//		return (respuestaDto);

		// JPQL

//		List<Espectador> respuesta = new ArrayList<Espectador>();
//		List<EspectadorAsistenciasDto> respuestaDto = new ArrayList<EspectadorAsistenciasDto>();
//
//		respuesta = espectadorDao.espectadoresPorNumeroAsistencias();
//				
//		for (Espectador espectador: respuesta ) {
//			
//			respuestaDto.add(mapperServices.espectadorToEspectadorAsistenciaDto(espectador));
//		}	
//		
//		return (respuestaDto);

		return (espectadorDao.espectadoresPorNumeroAsistenciasV2());
	}
	

	@Override
	public PagedResponseDto<EspectadorAsistenciasDto> getAsistenciasEspectadores(Pageable pageable) {
		// TODO Auto-generated method stub
		espectadorDao.espectadoresPorNumeroAsistenciasV2();
		
		Page<EspectadorAsistenciasDto> consulta = espectadorDao.espectadoresPorNumeroAsistenciasV2(pageable);

		if (consulta != null) {

			return new PagedResponseDto<EspectadorAsistenciasDto>(consulta);
		}

		return null;
	}

	@Override
	public Long getNumeroAsitenciasPorEspectador(Long id) {

		return espectadorEmisionService.countByEspectadorId(id);
	}

	@Override
	public Long actualizaDineroGastadoByEspectadorId(Long id) throws MiValidationException {

		// VALIDAR QUE EXISTA EL ESPECTADOR y que tenga espectadoresEMision

		validarParaActualizarDinero(id);

		Espectador actualizar = espectadorDao.findById(id).get();

//		Double dineroPalomitas = espectadorEmisionService.getRecaudacionPalomitasByEspectador(id);
//		Double dineroEntradas = espectadorEmisionService.getRecaudacionEntradasByEspectador(id);
//
//		actualizar.setDineroGastado(dineroPalomitas + dineroEntradas);

		Double recaudacionTotal = espectadorEmisionService.getRecaudacionTotalsByEspectador(id);
		actualizar.setDineroGastado(recaudacionTotal);

		updateEspectador(mapperServices.espectadorToDto(actualizar));

		// cambiar para que devuelva el espectador actualizado
		return actualizar.getId();
	}

	private void validarParaActualizarDinero(Long id) throws MiValidationException {

		if (Boolean.FALSE.equals(espectadorDao.existsById(id)))
			throw new MiValidationException(CineErrorConstants.ESPECTADOR_INEXISTENTE);

		if (espectadorEmisionService.countByEspectadorId(id) < 1)
			throw new MiValidationException(CineErrorConstants.ESPECTADOR_SIN_EMISIONES,
					espectadorDao.findById(id).get().getNombreCompleto());
	}

//	@Override
//	public EspectadorDto actualizaGeneroFavorito(Long idEspectador) {
//
//		Espectador espectadorActualizar = espectadorDao.findById(idEspectador).get();
//
//		/* V1 JAVA */
////		List<String> generosFavoritos1 = espectadorEmisionService.generoFavoritoByIdEspectador(idEspectador);
////		String generoMasRepetido = generosFavoritos1.stream().reduce(BinaryOperator.maxBy((o1,
////				o2) -> Collections.frequency(generosFavoritos1, o1) - Collections.frequency(generosFavoritos1, o2)))
////				.orElse(null);
////
////		espectadorActualizar.setGeneroFavorito(Genero.valueOf(generoMasRepetido));
////		updateEspectador(mapperServices.espectadorToDto(espectadorActualizar));
//		
//		/* V2 REPO (no se puede usar limit en esta version) */
//		List<String> generosFavoritos2 = espectadorEmisionService.generoFavoritoByIdEspectador2(idEspectador);
//		espectadorActualizar.setGeneroFavorito(generosFavoritos2.isEmpty() ? null : Genero.valueOf(generosFavoritos2.get(0)));
//		updateEspectador(mapperServices.espectadorToDto(espectadorActualizar));
//
//		/* V3 REPO (paginacion) */
////		List<String> generosFavoritos3 = espectadorEmisionService.generoFavoritoByIdEspectador3(idEspectador);
//
//		return (mapperServices.espectadorToDto(espectadorActualizar));
//	}
	
	@Override
	public EspectadorDto actualizaGeneroFavorito(Long idEspectador) {

//		Espectador espectadorActualizar = espectadorDao.findById(idEspectador).get();

		/* V1 JAVA */
//		List<String> generosFavoritos1 = espectadorEmisionService.generoFavoritoByIdEspectador(idEspectador);
//		String generoMasRepetido = generosFavoritos1.stream().reduce(BinaryOperator.maxBy((o1,
//				o2) -> Collections.frequency(generosFavoritos1, o1) - Collections.frequency(generosFavoritos1, o2)))
//				.orElse(null);
//
//		espectadorActualizar.setGeneroFavorito(Genero.valueOf(generoMasRepetido));
//		updateEspectador(mapperServices.espectadorToDto(espectadorActualizar));
		
//		/* V2 REPO (no se puede usar limit en esta version) */
//		List<String> generosFavoritos2 = espectadorEmisionService.generoFavoritoByIdEspectador2(idEspectador);
//		espectadorActualizar.setGeneroFavorito(generosFavoritos2.isEmpty() ? null : Genero.valueOf(generosFavoritos2.get(0)));
//		updateEspectador(mapperServices.espectadorToDto(espectadorActualizar));

		/* V3 REPO (paginacion para sustituir el limit) */
//		List<String> generosFavoritos3 = espectadorEmisionService.generoFavoritoByIdEspectador3(idEspectador);
		Pageable pageable = Pageable.ofSize(1);
		Page<String> generos = espectadorEmisionService.generoFavoritoByIdEspectador3(idEspectador,pageable);
		Espectador espectadorActualizar = espectadorDao.findById(idEspectador).get();
		espectadorActualizar.setGeneroFavorito(generos.isEmpty() ? null : Genero.valueOf(generos.getContent().get(0)));

		return (mapperServices.espectadorToDto(espectadorActualizar));
	}


}
