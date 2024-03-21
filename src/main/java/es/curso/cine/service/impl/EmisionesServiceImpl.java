package es.curso.cine.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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

import es.curso.cine.controller.dto.AcomodadorDto;
import es.curso.cine.controller.dto.EmisionDto;
import es.curso.cine.controller.dto.EmisionNuevaDto;
import es.curso.cine.controller.dto.LimpiadorDto;
import es.curso.cine.controller.dto.PagedResponseDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.mapper.MapperService;
import es.curso.cine.persistence.entities.Emision;
import es.curso.cine.persistence.entities.EmisionId;
import es.curso.cine.persistence.entities.types.Turno;
import es.curso.cine.persistence.repository.EmisionDao;
import es.curso.cine.service.AcomodadoresService;
import es.curso.cine.service.EmisionesService;
import es.curso.cine.service.EspectadorEmisionService;
import es.curso.cine.service.LimpiadoresService;
import es.curso.cine.service.PeliculasService;
import es.curso.cine.service.SalasService;
import es.curso.cine.utils.CineErrorConstants;

@Service
@Transactional(rollbackFor = MiValidationException.class)
public class EmisionesServiceImpl implements EmisionesService {

	@Autowired
	@Lazy
	private PeliculasService peliculasService;
	
	@Autowired
	@Lazy
	private SalasService salasService;
	
	@Autowired
	@Lazy
	private EmisionDao emisionDao;
	
	@Autowired
	@Lazy
	private MapperService mapperServices;
	
	@Autowired
	@Lazy
	private LimpiadoresService limpiadoresService;
	
	@Autowired
	@Lazy
	private AcomodadoresService acomodadoresService;
	
	@Autowired
	@Lazy
	private EspectadorEmisionService espectadorEmisionService;
	
	/*******************************GET*******************************/

	@Override
	public EmisionDto getEmision(EmisionId emisionId) {
		
		Emision respuesta = new Emision();
		
		respuesta = emisionDao.findByEmisionId(emisionId);		
		
		return mapperServices.emisionToDto(respuesta);
		
	}
	
	@Override
	public EmisionDto getEmision(Long idSala, Long idPelicula, LocalDate fecha) {
		
		return null;
		
	}
	

	@Override
	public PagedResponseDto<EmisionDto> getEmisiones(Pageable pageable) {

		Page<Emision> consulta = emisionDao.findAllByTitulo(pageable);
		
		if (consulta != null) {
			
			return new PagedResponseDto<EmisionDto>(consulta, mapperServices::emisionToDto);
		}
		
		return null;
	}
	
	@Override
	public Set<EmisionDto> getEmisiones() {
		
		List<Emision> respuesta = new ArrayList<Emision>();
		Set<EmisionDto> respuestaDto = new HashSet<EmisionDto>();

		respuesta = emisionDao.findAll();
		
		for (Emision emision: respuesta ) {
					
			respuestaDto.add(mapperServices.emisionToDto(emision));
					
		}		

		return (respuestaDto);	
	}
	
	/*******************************POST*******************************/
	
	@Override
	public Long createEmision(EmisionDto emisionDto) throws MiValidationException {
		
		validarNuevaEmision(emisionDto);
		
		Emision creado = new Emision();
		
		creado = mapperServices.dtoToEmision(emisionDto);
		
		emisionDao.save(creado);
		
		return creado.getPelicula().getId();
		
	}
	
	@Override
	public Long createEmisionNueva(EmisionNuevaDto emisionNuevaDto) throws MiValidationException {
		
		validarNuevaEmision(mapperServices.emisionToDto(mapperServices.emisionNuevaDtoToEmision(emisionNuevaDto)));
		
		Emision creado = new Emision();
		
		creado = mapperServices.emisionNuevaDtoToEmision(emisionNuevaDto);
		
		emisionDao.save(creado);
		
		return creado.getPelicula().getId();
		
	}

	private void validarNuevaEmision(EmisionDto emisionDto) throws MiValidationException {
				
		if (emisionDao.findByEmisionIdFechaAndPeliculaIdAndSalaId(emisionDto.getFecha(),peliculasService.findByTitulo(emisionDto.getTituloPelicula()).getId(),salasService.findByNumero(emisionDto.getNumeroSala()).getId()) != null) 
			throw new MiValidationException(CineErrorConstants.EMISION_EXISTENTE);
		
		if (peliculasService.findByTitulo(emisionDto.getTituloPelicula()) == null) 
			throw new MiValidationException(CineErrorConstants.PELICULA_INEXISTENTE, emisionDto.getTituloPelicula());
		
		if (salasService.findByNumero(emisionDto.getNumeroSala()) == null) 
			throw new MiValidationException(CineErrorConstants.SALA_INEXISTENTE, emisionDto.getNumeroSala().toString());
		
//		if (acomodadoresService.acomodadorPorNombre(emisionDto.getNombreAcomodador()) == null) 
//			throw new MiValidationException(CineErrorConstants.ACOMODADOR_INEXISTENTE, emisionDto.getNombreAcomodador());
		
		if (limpiadoresService.limpiadorPorNombre(emisionDto.getNombreLimpiador()) == null) 
			throw new MiValidationException(CineErrorConstants.LIMPIADOR_INEXISTENTE, emisionDto.getNombreLimpiador());
		
		if (Boolean.FALSE.equals(Turno.compruebaHorario(emisionDto.getFecha(),limpiadoresService.limpiadorPorNombre(emisionDto.getNombreLimpiador()).getTurno().name())))
			throw new MiValidationException(CineErrorConstants.EMISION_LIMPIADOR_FECHA_TURNO_INCORRECTO,emisionDto.getNombreLimpiador(),emisionDto.getFecha().toString());

		if (salasService.findByNumero(emisionDto.getNumeroSala()).getPremium() && emisionDto.getNombreAcomodador() == null) 
			throw new MiValidationException(CineErrorConstants.PREMIUM_ACOMODADOR_REQUERIDO, emisionDto.getNumeroSala().toString());
		
		//OTRA FORMA DE HACERLO
//		if(limpiadoresService.limpiadorPorNombre(emisionDto.getNombreLimpiador()).getTurno().ordinal() == 0 && ((emisionDto.getFecha().getHour() < CineConstants.Horarios.FINAL_MANANA)) || emisionDto.getFecha().getHour() > CineConstants.Horarios.FINAL_MANANA -1) {
//			 throw new MiValidationException("XXX", "El horario de mañana esta mal");
//		}

	}
	
	/*******************************PUT*******************************/
	
	@Override
	public Long updateEmision(EmisionDto emision) {

		Emision actualizado = new Emision();
					
		actualizado = mapperServices.dtoToEmision(emision);
			
		emisionDao.save(actualizado);
			
		return (actualizado.getSala().getId());
		
	}
	
	/*******************************DELETE*******************************/
	
	@Override
	public void deleteEmision(EmisionId emisionId) {
						
		emisionDao.delete(emisionDao.findByEmisionId(emisionId));
		
	}

	/*******************************OTROS*******************************/
	
	@Override
	public List<Emision> getEmisionByFecha(Set<EmisionDto> lista) {

		List<Emision> emisionesFinales = new ArrayList<>();
		Set<EmisionDto> emisiones = new HashSet<>();

		emisiones = getEmisiones();
		
		for (EmisionDto emision : emisiones) {
			
			emisionesFinales.add(mapperServices.dtoToEmision(emision));
			//emisionesFinales.add(emisionDao.findByEmisionId(new EmisionId(emision.getIdSala(),emision.getIdPelicula(),emision.getFecha())));
			
		}
		
		return (emisionesFinales);
		
	}
	
	/*******************************ESTADISTICAS*******************************/

	@Override
	public Set<EmisionDto> findEmisionByPrecioPeli(Double precio) {
		
		Set<EmisionDto> emisionesDto = new HashSet<EmisionDto>();
		
		for (Emision emisionLista : emisionDao.findByPeliculaPrecioGreaterThanOrderByPalomitasDesc(precio)) {
		
			emisionesDto.add(mapperServices.emisionToDto(emisionLista));
		
		}
		
		return (emisionesDto);
		
	}

	@Override
	public Set<EmisionDto> lastEmisiones() {
		
		Set<EmisionDto> emisionesDto = new HashSet<EmisionDto>();
		
		for (Emision emisionLista : emisionDao.ultimasEmisiones()) {
			
			emisionesDto.add(mapperServices.emisionToDto(emisionLista));
		
		}
		
		return (emisionesDto);
		
	}

	@Override
	public Set<EmisionDto> getEmisionesBySala(Set<Long> idSalas) {
		
		Set<EmisionDto> emisionesSalaDto = new HashSet<EmisionDto>();
		
		for (Emision emisionLista : emisionDao.findByEmisionIdIdSalaIn(idSalas)) {
		
			emisionesSalaDto.add(mapperServices.emisionToDto(emisionLista));
		
		}
		
		return (emisionesSalaDto);
		
	}

	@Override
	public Set<Emision> getEmisionesByLimpiador(LimpiadorDto limpiador) {
		
		Set<Emision> emisionesDto = new HashSet<Emision>();
		
		for (Emision emisionLista : emisionDao.encuntraLimpiador(limpiador.getId())) {
		
			emisionesDto.add(emisionLista);
		
		}
		
		return (emisionesDto);
		
	}

	@Override
	public Set<Emision> getEmisionesByAcomodador(AcomodadorDto acomodador) {
		
		Set<Emision> emisionesDto = new HashSet<Emision>();
		
		for (Emision emisionLista : emisionDao.encuntraAcomodador(acomodador.getId())) {
		
			emisionesDto.add(emisionLista);
		
		}
		
		return (emisionesDto);
		
	}

	@Override
	public Boolean updateAleatorioLimpiadorControlador() {
		
		List<LimpiadorDto> listaLimpiadoresList = new ArrayList<>(limpiadoresService.getLimpiadores());
		List<AcomodadorDto> listaAcomodadoresList = new ArrayList<>(acomodadoresService.getAcomodadores());
		
		for (Emision emision : emisionDao.findAll()) {
			
			if (emision.getAcomodador() == null && emision.getLimpiador() == null) {
				
				Integer aleatorioLimpiador = (int)Math.floor(Math.random()*listaLimpiadoresList.size());
				
				emision.setLimpiador(mapperServices.dtoToLimpiador(listaLimpiadoresList.get(aleatorioLimpiador)));
				
				limpiadoresService.updateLimpiador(mapperServices.limpiadorToDto(emision.getLimpiador()));
				
				Integer hayAcomodador = (int)Math.floor(Math.random()*2);

				if (hayAcomodador > 0) {
										
					Integer aleatorioAcomodador = (int)Math.floor(Math.random()*listaAcomodadoresList.size());
					
					emision.setAcomodador(mapperServices.dtoToAcomodador(listaAcomodadoresList.get(aleatorioAcomodador)));
										
					acomodadoresService.updateAcomodador(mapperServices.acomodadorToDto(emision.getAcomodador()));
					
				}
				
				updateEmision(mapperServices.emisionToDto(emision));
				
			}
			
		}
		
		return true;
	}
	
	@Override
	public Boolean borrar() {
				
		for (Emision emision : emisionDao.findAll()) {
			
				emision.setLimpiador(null);
				emision.setAcomodador(null);
				updateEmision(mapperServices.emisionToDto(emision));
		}
		return true;
	}

	@Override
	public Emision emisionPorFechaPeliSala(LocalDateTime fechaEmision, String nombrePelicula, Integer numeroSala) {
		
		return (emisionDao.getEmisionByFechaPeliSala(fechaEmision,nombrePelicula,numeroSala));
	}
	
	@Override
	public Emision emisionPorFechaPeliSala(LocalDateTime fechaEmision, Long nombrePelicula, Long numeroSala) {

		return (emisionDao.findByEmisionIdFechaAndPeliculaIdAndSalaId(fechaEmision,nombrePelicula,numeroSala));
	}

	@Override
	public List<Integer[]> getButacasOcupadas(Long idSala, Long idPelicula, LocalDateTime fecha) {
		
		return (espectadorEmisionService.findByEmisionEmisionIdOrderByFilaAscColumnaAsc(fecha, idPelicula, idSala));
	}

	@Override
	public Page<Emision> findByPeliculaTituloAndEmisionIdFechaAfter(String tituloPelicula, Pageable pageable) {

		
		return emisionDao.findByPeliculaTituloAndEmisionIdFechaAfter(tituloPelicula,LocalDateTime.now(),pageable);
//		return (mapperServices.setEmisionToSetEmisionDto(emisionDao.findByPeliculaTituloAndEmisionIdFechaAfter(tituloPelicula,LocalDateTime.now())));
	}
	
	@Override	
	public void generarEmisiones() throws MiValidationException {
		
		for (int i = 0; i < 10; i++) {
			
			EmisionDto nuevaEmision = new EmisionDto();

			nuevaEmision.setFecha(LocalDateTime.of((int)Math.floor(Math.random()*(2026 - 2024 + 1 ) + 2024), Month.JULY, 29, (int)Math.floor(Math.random()*(18 - 16 + 1) + 16), 30, 40));
			nuevaEmision.setNumeroSala((int)Math.floor(Math.random()*12 + 1));
			nuevaEmision.setTituloPelicula(peliculasService.getPelicula((long)Math.floor(Math.random()*20)).getTitulo());
			nuevaEmision.setNombreAcomodador(acomodadoresService.getAcomodador((long)52).getNombreCompleto());
			nuevaEmision.setNombreLimpiador(limpiadoresService.getLimpiador((long)4).getNombreCompleto());
//			nuevaEmision.setEspectadoresEmision(null);

			createEmision(nuevaEmision);
		}
		
	}

	@Override
	public Set<EmisionDto> getEmisionesPasadasSinPalomitasVisitantes() {

		Set<Emision> emisionesActualizar = emisionDao.findByEmisionIdFechaBeforeAndPalomitasIsNullAndVisitantesIsNull(LocalDateTime.now());
		Set<EmisionDto> emisionesActualizadas = new HashSet<>();
		
		for (Emision elemento : emisionesActualizar) {
			
//			Double palomitas = 0.0;
//			List<EspectadorEmision> espectadoresConvertidos = new ArrayList<EspectadorEmision>(elemento.getEspectadoresEmision());
//			
//			//Cambiar el .size por un count en el dao de espectadorEmisionById de emision (creo)
//			for (int i = 0; i < espectadoresConvertidos.size(); i++) {
//				
//				palomitas += espectadoresConvertidos.get(i).getGastoPalomitas();
//			}
			
			//elemento.setPalomitas(palomitas);
			elemento.setPalomitas(espectadorEmisionService.getRecaudacionPalomitas(elemento.getEmisionId()));

			//elemento.setVisitantes(espectadoresConvertidos.size());
			//Cambiar el .size por un count en el dao de espectadorEmision (byId).
			elemento.setVisitantes(espectadorEmisionService.countEspectadoresByEmisionEmisionId(elemento.getEmisionId()));
			
			updateEmision(mapperServices.emisionToDto(elemento));
			emisionesActualizadas.add(mapperServices.emisionToDto(elemento));
		}

		return (emisionesActualizadas);
	}

	@Override
	public Double recaudacionPorPelicula(String titulo) {
		
		return emisionDao.recaudacionPorPelicula(titulo);
	}

	@Override
	public Integer espectadoresPorPelicula(String titulo) {
		
		return emisionDao.espectadoresPorPelicula(titulo);
	}

}
