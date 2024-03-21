package es.curso.cine.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
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

import es.curso.cine.controller.dto.Butaca2Dto;
import es.curso.cine.controller.dto.DirectorRecaudacionEntradasDto;
import es.curso.cine.controller.dto.EmisionDto;
import es.curso.cine.controller.dto.EspectadorEmisionDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.controller.dto.PeliculaRecaudacionPalomitasDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.mapper.MapperService;
import es.curso.cine.persistence.entities.Emision;
import es.curso.cine.persistence.entities.EmisionId;
import es.curso.cine.persistence.entities.EspectadorEmision;
import es.curso.cine.persistence.entities.Sala;
import es.curso.cine.persistence.repository.EspectadorEmisionDao;
import es.curso.cine.service.AcomodadoresService;
import es.curso.cine.service.EmisionesService;
import es.curso.cine.service.EspectadorEmisionService;
import es.curso.cine.service.EspectadoresService;
import es.curso.cine.service.PeliculasService;
import es.curso.cine.service.SalasService;
import es.curso.cine.utils.CineConstants;
import es.curso.cine.utils.CineErrorConstants;

@Service
@Transactional(rollbackFor = MiValidationException.class)
public class EspectadorEmisionServiceImpl implements EspectadorEmisionService {

	@Autowired
	@Lazy
	private MapperService mapperServices;

	@Autowired
	@Lazy
	private EspectadorEmisionDao espectadorEmisionDao;

	@Autowired
	private EmisionesService emisionesService;

	@Autowired
	private EspectadoresService espectadorService;

	@Autowired
	private SalasService salasService;

	@Autowired
	private PeliculasService peliculasService;

	@Autowired
	private AcomodadoresService acomodadoresService;

	/******************************* GET *******************************/

	@Override
	public EspectadorEmisionDto getEspectadorEmision(Long idEspectadorEmision) {

		EspectadorEmision respuesta = new EspectadorEmision();

		respuesta = espectadorEmisionDao.findById(idEspectadorEmision).get();

		return mapperServices.espectadorEmisionToDto(respuesta);
	}

	@Override
	public Set<EspectadorEmisionDto> getEspectadorEmisiones() {

		List<EspectadorEmision> respuesta = new ArrayList<EspectadorEmision>();
		Set<EspectadorEmisionDto> respuestaDto = new HashSet<EspectadorEmisionDto>();

		respuesta = espectadorEmisionDao.findAll();

		for (EspectadorEmision espectadorEmision : respuesta) {

			respuestaDto.add(mapperServices.espectadorEmisionToDto(espectadorEmision));

		}

		return (respuestaDto);
	}
	
	@Override
	public PagedResponseDto<EspectadorEmisionDto> getEspectadorEmisiones(Pageable pageable) {
		
		Page<EspectadorEmision> consulta = espectadorEmisionDao.findAll(pageable);
		
		if (consulta != null) {
			
			return new PagedResponseDto<EspectadorEmisionDto>(consulta, mapperServices::espectadorEmisionToDto);
		}
		
		return null;
	}

	@Override
	public List<EspectadorEmision> getEspectadorEmisionesEntidad() {

		return espectadorEmisionDao.findAll();

	}

	/******************************* POST *******************************/

	@Override
	public Long createEspectadorEmision(EspectadorEmisionDto espectadorEmisionDto) throws MiValidationException {

		validarEspectadorEmision(espectadorEmisionDto);

		EspectadorEmision creado = mapperServices.dtoToEspectadorEmision(espectadorEmisionDto);
		espectadorEmisionDao.save(creado);

		return (mapperServices.espectadorEmisionToDto(creado).getId());
	}

	private void validarEspectadorEmision(EspectadorEmisionDto espectadorEmisionDto) throws MiValidationException {

		if (emisionesService.emisionPorFechaPeliSala(espectadorEmisionDto.getFechaEmision(),
				espectadorEmisionDto.getNombrePelicula(), espectadorEmisionDto.getNumeroSala()) == null)
			throw new MiValidationException(CineErrorConstants.EMISION_INEXISTENTE);

		if (espectadorService.espectadorPorNombre(espectadorEmisionDto.getNombreEspectador()) == null)
			throw new MiValidationException(CineErrorConstants.ESPECTADOR_INEXISTENTE,
					espectadorEmisionDto.getNombreEspectador());

		if (espectadorEmisionDao.findByFilaAndColumnaAndEmisionEmisionId(espectadorEmisionDto.getFila(),
				espectadorEmisionDto.getColumna(),
				emisionesService
						.emisionPorFechaPeliSala(espectadorEmisionDto.getFechaEmision(),
								espectadorEmisionDto.getNombrePelicula(), espectadorEmisionDto.getNumeroSala())
						.getEmisionId()) != null)
			throw new MiValidationException(CineErrorConstants.BUTACA_OCUPADA,
					espectadorEmisionDto.getFila().toString(), espectadorEmisionDto.getColumna().toString());

		if (espectadorEmisionDto.getColumna() < 0
				|| espectadorEmisionDto.getColumna() > salasService.findByNumero(espectadorEmisionDto.getNumeroSala())
						.getNumeroColumnas()
				|| espectadorEmisionDto.getFila() < 0 || espectadorEmisionDto.getFila() > salasService
						.findByNumero(espectadorEmisionDto.getNumeroSala()).getNumeroFilas())
			throw new MiValidationException(CineErrorConstants.BUTACA_INEXISTENTE,
					espectadorEmisionDto.getFila().toString(), espectadorEmisionDto.getColumna().toString());

		if ((calculaEdad(espectadorEmisionDto) < peliculasService.findByTitulo(espectadorEmisionDto.getNombrePelicula())
				.getCalificacionEdad())
				|| (peliculasService.findByTitulo(espectadorEmisionDto.getNombrePelicula())
						.getCalificacionEdad() == null))
			throw new MiValidationException(CineErrorConstants.CALIFICACION_EDAD_INCORRECTA,
					espectadorEmisionDto.getNombrePelicula(), peliculasService
							.findByTitulo(espectadorEmisionDto.getNombrePelicula()).getCalificacionEdad().toString(),
					calculaEdad(espectadorEmisionDto).toString());

		if (espectadorEmisionDao.existsByEmisionEmisionIdFechaAndEmisionSalaIdAndEmisionPeliculaIdAndEspectadorId(
				espectadorEmisionDto.getFechaEmision(),
				salasService.findByNumero(espectadorEmisionDto.getNumeroSala()).getId(),
				peliculasService.findByTitulo(espectadorEmisionDto.getNombrePelicula()).getId(),
				espectadorService.espectadorPorNombre(espectadorEmisionDto.getNombreEspectador()).getId()))
			throw new MiValidationException(CineErrorConstants.ESPECTADOR_BUTACA_EMISION_EXISTENTE);


	}

	private Integer calculaEdad(EspectadorEmisionDto espectadorEmisionDto) {

		Period edad = Period.between(
				espectadorService.espectadorPorNombre(espectadorEmisionDto.getNombreEspectador()).getFechaNacimiento(),
				LocalDate.now());

		return (Integer) edad.getYears();
	}

	/******************************* PUT *******************************/

	@Override
	public Long updateEspectadorEmision(EspectadorEmisionDto espectadorEmisionDto) {

		EspectadorEmision actualizado = new EspectadorEmision();

		if (getEspectadorEmision(espectadorEmisionDto.getId()) != null) {

			actualizado = espectadorEmisionDao.saveAndFlush(mapperServices.dtoToEspectadorEmision(espectadorEmisionDto));

			return (mapperServices.espectadorEmisionToDto(actualizado).getId());
		}
		
		return null;
	}

	/******************************* DELETE *******************************/

	@Override
	public void deleteEspectadorEmision(Long idEspectadorEmision) {

		espectadorEmisionDao.deleteById(idEspectadorEmision);

	}

	/******************************* GENERADOR ALEATORIO *******************************/

	@Override
	public Boolean generarEspectadorEmision() throws MiValidationException {

		Set<EmisionDto> emisiones = emisionesService.getEmisiones();

		List<EmisionDto> emisionesList = new ArrayList<EmisionDto>(emisiones);
		for (int i = 11; i < 16; i++) {

			Integer fila = (int) Math.floor(Math.random() * 20);
			Integer columna = (int) Math.floor(Math.random() * 40);
			Double valoracion = (Double) Math.random() * 10;
			Double gastoPalomitas = Math.floor(Math.random() * 30);
			Long idEspectador = (long) Math.floor(Math.random() * (4 - 3 + 1) + 3);
			Emision emisionConvertida = mapperServices.dtoToEmision(emisionesList.get(i));
			String nombreEspectador = "Espectador" + idEspectador.toString();
			String titulo = emisionConvertida.getPelicula().getTitulo();
			Integer numeroSala = emisionConvertida.getSala().getNumero();

			createEspectadorEmision(new EspectadorEmisionDto(fila, columna, valoracion, gastoPalomitas,
					nombreEspectador, emisionesList.get(i).getFecha(), titulo, numeroSala));

		}

		return true;
	}
	
	/******************************* OTROS *******************************/

//	@Override
//	public Set<Long> setIdEspectadorEmision(Espectador espectador) {
//
//		Set<Long> id = new HashSet<Long>();
//
//		for (EspectadorEmision em : espectador.getEmisionesEspectador()) {
//			id.add(em.getId());
//		}
//
//		return (id);
//	}

	@Override
	public Set<EspectadorEmision> getEspectadorEmisionBy(EmisionId emisionId) {
		return (espectadorEmisionDao.buscarEspectadorEmisionById(emisionId));
	}

	@Override
	public Set<EspectadorEmision> getEspectadorEmisionByIdEspectador(Long idEspectador) {
		return (espectadorEmisionDao.getEspectadorEmisionByIdEspectador(idEspectador));
	}



	@Override
	public List<EspectadorEmision> findByEmisionEmisionId(LocalDateTime fecha, Long idPelicula, Long idSala) {

		return (espectadorEmisionDao.findByEmisionEmisionId(new EmisionId(idSala, idPelicula, fecha)));
	}

	@Override
	public List<Integer[]> findByEmisionEmisionIdOrderByFilaAscColumnaAsc(LocalDateTime fecha, Long idPelicula,
			Long idSala) {
		List<EspectadorEmision> emisiones = espectadorEmisionDao
				.findByEmisionEmisionIdOrderByFilaAscColumnaAsc(new EmisionId(idSala, idPelicula, fecha));

		List<Integer[]> butacasFinales = new ArrayList<Integer[]>();

		for (EspectadorEmision elemento : emisiones) {

			butacasFinales.add(new Integer[] { elemento.getFila(), elemento.getColumna() });
		}

		return (butacasFinales);
	}

	@Override
	public List<Integer[]> butacasDisponibles(LocalDateTime fecha, Long idPelicula, Long idSala) {

		List<Integer[]> ocupadas = findByEmisionEmisionIdOrderByFilaAscColumnaAsc(fecha, idPelicula, idSala);
		List<Integer[]> todas = new ArrayList<Integer[]>();
		Sala salaElegida = salasService.findById(idSala);

		for (int i = 1; i <= salaElegida.getNumeroFilas(); i++) {

			for (int j = 1; j <= salaElegida.getNumeroColumnas(); j++) {

				todas.add(new Integer[] { i, j });
			}
		}

		for (int i = 0; i < todas.size(); i++) {

			for (Integer[] elemento : ocupadas) {

				if (todas.get(i)[0] == elemento[0] && todas.get(i)[1] == elemento[1]) {

					todas.remove(i);
				}
			}
		}

		return (todas);
	}

	@Override
	public List<Butaca2Dto> butacasDtoOcupadas(LocalDateTime fecha, Long idPelicula, Long idSala) {
		List<EspectadorEmision> emisiones = espectadorEmisionDao
				.findByEmisionEmisionIdOrderByFilaAscColumnaAsc(new EmisionId(idSala, idPelicula, fecha));
		List<Butaca2Dto> butacasOcupadas = new ArrayList<Butaca2Dto>();

		for (EspectadorEmision elemento : emisiones) {

			butacasOcupadas.add(new Butaca2Dto(elemento.getFila(), elemento.getColumna()));
		}

		return (butacasOcupadas);
	}

	@Override
	public List<Butaca2Dto> butacasDtoDisponibles(LocalDateTime fecha, Long idPelicula, Long idSala) {

		List<Butaca2Dto> ocupadas = butacasDtoOcupadas(fecha, idPelicula, idSala);
		List<Butaca2Dto> disponibles = new ArrayList<Butaca2Dto>();
		Sala salaElegida = salasService.findById(idSala);

		for (int i = 1; i <= salaElegida.getNumeroFilas(); i++) {

			for (int j = 1; j <= salaElegida.getNumeroColumnas(); j++) {

				disponibles.add(new Butaca2Dto(i, j));
			}
		}

		disponibles.removeAll(ocupadas);

		return (disponibles);
	}

	/******************************* VALORACIONES *******************************/
	
	@Override
	public Set<EspectadorEmisionDto> findByEspectadorValoracionNullFecha(Long idEspectador) {

		return (mapperServices.listEspectadorEmisionToSetEspectadorEmisionDto(
				espectadorEmisionDao.findByEspectadorIdAndValoracionServicioIsNullAndEmisionEmisionIdFechaAfter(
						idEspectador, LocalDateTime.now().minusDays(CineConstants.Valoraciones.DIAS_VALORACION))));
	}

	@Override
	public EspectadorEmision findByEspectadorIdAndEmisionEmisionIdFechaAndEmisionSalaNumeroAndEmisionPeliculaTitulo(
			Long idEspectador, LocalDateTime fecha, Integer numeroSala, String tituloPelicula) {

		return espectadorEmisionDao
				.findByEspectadorIdAndEmisionEmisionIdFechaAndEmisionSalaNumeroAndEmisionPeliculaTitulo(idEspectador,
						fecha, numeroSala, tituloPelicula);
	}

	@Override
//	@Transactional(rollbackFor = ParseException.class)
//	@Transactional(noRollbackFor = NullPointerException.class)
	public void updateValoracion(EspectadorEmision emisionParaActualizar, Double valoracion, Double palomitas)
			throws MiValidationException {

		valoracionEspectadorEmision(mapperServices.espectadorEmisionToDto(emisionParaActualizar));

		emisionParaActualizar.setValoracionServicio(valoracion);
		emisionParaActualizar.setGastoPalomitas(palomitas);

		updateEspectadorEmision(mapperServices.espectadorEmisionToDto(emisionParaActualizar));

		if (emisionParaActualizar.getEmision().getAcomodador() != null) {
			
			acomodadoresService.actualizarValoracionAcomodadorIndividual(emisionParaActualizar.getEmision().getAcomodador().getId());
		}
	}

	private void valoracionEspectadorEmision(EspectadorEmisionDto espectadorEmisionDto) throws MiValidationException {

		if (emisionesService.emisionPorFechaPeliSala(espectadorEmisionDto.getFechaEmision(),
				espectadorEmisionDto.getNombrePelicula(), espectadorEmisionDto.getNumeroSala()) == null)
			throw new MiValidationException(CineErrorConstants.EMISION_INEXISTENTE);

		if (espectadorService.espectadorPorNombre(espectadorEmisionDto.getNombreEspectador()) == null)
			throw new MiValidationException(CineErrorConstants.ESPECTADOR_INEXISTENTE,
					espectadorEmisionDto.getNombreEspectador());

		if (espectadorEmisionDto.getValoracionServicio() != null)
			throw new MiValidationException(CineErrorConstants.VALORACION_EXISTENTE);

	}

	@Override
	public List<Double> valoracionesPorAcomodador(Long id) {

		Set<EspectadorEmision> emisionesAcomodador =  espectadorEmisionDao.findByEmisionAcomodadorId(id);
		List<Double> valoracionesAcomodador = new ArrayList<Double>();
		for (EspectadorEmision elemento : emisionesAcomodador) {
			
			if (elemento.getValoracionServicio() != null) {
				valoracionesAcomodador.add(elemento.getValoracionServicio());
			}
		}
		
		return (valoracionesAcomodador);
	}

	@Override
	public Boolean existsByEspectadorIdAndEmisionEmisionIdFechaAndEmisionSalaNumeroAndEmisionPeliculaTitulo(
			Long idEspectador, LocalDateTime fecha, Integer numeroSala, String tituloPelicula) {
		return espectadorEmisionDao
				.existsByEspectadorIdAndEmisionEmisionIdFechaAndEmisionSalaNumeroAndEmisionPeliculaTitulo(idEspectador,
						fecha, numeroSala, tituloPelicula);
	}

	@Override
	public Integer countEspectadoresByEmisionEmisionId(EmisionId emisionId) {

		return espectadorEmisionDao.countByEmisionEmisionId(emisionId);
	}

	@Override
	public Long countByEspectadorId(Long id) {
		return espectadorEmisionDao.countByEspectadorId(id);
	}

	@Override
	public Double getRecaudacionPalomitas(EmisionId emisionId) {
		return espectadorEmisionDao.getRecaudacionPalomitasPorEmision(emisionId);
	}
	
	//Recaudacion por espectador
	
	@Override
	public Double getRecaudacionEntradasByEspectador(Long id) {

		return espectadorEmisionDao.getRecaudacionEntradasByEspectador(id);
	}

	@Override
	public Double getRecaudacionPalomitasByEspectador(Long id) {
		
		return espectadorEmisionDao.getRecaudacionPalomitasByEspectador(id);
	}

	@Override
	public Double getRecaudacionTotalsByEspectador(Long id) {
		
		return espectadorEmisionDao.getRecaudacionTotalsByEspectador(id);
	}

	@Override
	public List<String> generoFavoritoByIdEspectador(Long idEspectador) {
		return espectadorEmisionDao.generoFavoritoByIdEspectador(idEspectador);
	}

	@Override
	public List<String> generoFavoritoByIdEspectador2(Long idEspectador) {
		return espectadorEmisionDao.generoFavoritoByIdEspectador2(idEspectador);
	}

	@Override
	public Page<String> generoFavoritoByIdEspectador3(Long idEspectador, Pageable pageable) {
		return espectadorEmisionDao.generoFavoritoByIdEspectador3(idEspectador, pageable);
	}

	@Override
	public Double getRecaudacionPorDirector(String nombre) {

		return espectadorEmisionDao.getRecaudacionPorDirector(nombre);
	}

	@Override
	public List<DirectorRecaudacionEntradasDto> recaudacionDirectores() {

		return espectadorEmisionDao.recaudacionDirectores();
	}

	@Override
	public List<PeliculaRecaudacionPalomitasDto> getPeliculasRecaudacionPalomita() {

		return espectadorEmisionDao.getPeliculasRecaudacionPalomita();
	}




}
