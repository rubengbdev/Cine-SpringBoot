package es.curso.cine.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
import es.curso.cine.persistence.entities.EmisionId;
import es.curso.cine.persistence.entities.Espectador;
import es.curso.cine.persistence.entities.EspectadorEmision;
import es.curso.cine.persistence.entities.Limpiador;
import es.curso.cine.persistence.entities.Pelicula;
import es.curso.cine.persistence.entities.Sala;
import es.curso.cine.persistence.entities.types.Genero;
import es.curso.cine.persistence.entities.types.Turno;
import es.curso.cine.persistence.repository.EmisionDao;
import es.curso.cine.service.AcomodadoresService;
import es.curso.cine.service.DirectoresService;
import es.curso.cine.service.EmisionesService;
import es.curso.cine.service.EspectadorEmisionService;
import es.curso.cine.service.EspectadoresService;
import es.curso.cine.service.LimpiadoresService;
import es.curso.cine.service.PeliculasService;
import es.curso.cine.service.SalasService;

@Service
public class MapperServiceImpl implements MapperService {

	@Autowired
	@Lazy
	private DirectoresService directoresService;
	
	@Autowired
	@Lazy
	private PeliculasService peliculasService;
	
	@Autowired
	@Lazy
	private EmisionDao emisionDao;
	
	@Autowired
	@Lazy
	private SalasService salasService;
	
	@Autowired
	@Lazy
	private EmisionesService emisionesService;
	
	@Autowired
	@Lazy
	private AcomodadoresService acomodadorService;
	
	@Autowired
	@Lazy
	private LimpiadoresService limpiadorService;
	
	@Autowired
	@Lazy
	private EspectadorEmisionService espectadorEmisionService;
	
	@Autowired
	@Lazy
	private EspectadoresService espectadorService;
	
	
	@Override
	public PeliculaDto peliculaToDto (Pelicula pelicula) {
		
		PeliculaDto respuesta = new PeliculaDto();
		
		respuesta.setId(pelicula.getId());
		respuesta.setTitulo(pelicula.getTitulo());
		respuesta.setDuracion(pelicula.getDuracion());
		respuesta.setFechaEmision(pelicula.getFechaEmision());
		respuesta.setPrecio(pelicula.getPrecio());
		respuesta.setGenero(pelicula.getGenero().name());
		respuesta.setDirector(pelicula.getDirector().getNombre());
		respuesta.setCalificacionEdad(pelicula.getCalificacionEdad());
//		respuesta.setEmisiones(pelicula.getEmisiones() != null ? pelicula.getEmisiones().stream().map( p -> p.getEmisionId().getFecha() ).collect( Collectors.toList() ) : null);
	
		return respuesta;
	}
	
	public Pelicula dtoToPelicula (PeliculaDto peliculaDto) {
		
		if (peliculaDto == null) return null;
		
		Pelicula respuesta = new Pelicula();
		
		respuesta.setId(peliculaDto.getId());
		respuesta.setTitulo(peliculaDto.getTitulo());
		respuesta.setDuracion(peliculaDto.getDuracion());
		respuesta.setFechaEmision(peliculaDto.getFechaEmision());
		respuesta.setPrecio(peliculaDto.getPrecio());
		respuesta.setGenero(Genero.valueOf(peliculaDto.getGenero()));
		respuesta.setDirector(directoresService.getDirectorByName(peliculaDto.getDirector()));
		respuesta.setCalificacionEdad(peliculaDto.getCalificacionEdad());
//		respuesta.setEmisiones(emisionesService.getEmisionByFecha(emisionesService.getEmisiones()));
		
		return respuesta;
		
	}

	@Override
	public DirectorDto directorToDto(Director director) {
		
		DirectorDto respuesta = new DirectorDto();
		
		respuesta.setId(director.getId());
		respuesta.setNombre(director.getNombre());
		respuesta.setFechaNacimiento(director.getFechaNacimiento());
		respuesta.setNumPeliculas(director.getNumPeliculas());
//		respuesta.setPeliculas(director.getPeliculas() != null ? director.getPeliculas().stream().map( p -> p.getTitulo() ).collect( Collectors.toList() ) : null);
		
		return respuesta;
		
	}

	@Override
	public Director dtoToDirector(DirectorDto directorDto) {
		
		Director respuesta = new Director();
		
		respuesta.setId(directorDto.getId());
		respuesta.setNombre(directorDto.getNombre());
		respuesta.setFechaNacimiento(directorDto.getFechaNacimiento());
		respuesta.setNumPeliculas(directorDto.getNumPeliculas());
//		respuesta.setPeliculas(directorDto.getPeliculas().size() > 0 ? peliculasService.getPeliculasByDirector(directorDto) : null);

		return respuesta;
		
	}

	@Override
	public EmisionDto emisionToDto(Emision emision) {
		EmisionDto respuesta = new EmisionDto();
		
		respuesta.setFecha(emision.getEmisionId().getFecha());
		respuesta.setNumeroSala(emision.getSala().getNumero());
		respuesta.setTituloPelicula(emision.getPelicula().getTitulo());
		respuesta.setPalomitas(emision.getPalomitas());
		respuesta.setVisitantes(emision.getVisitantes());
		respuesta.setNombreAcomodador(emision.getAcomodador() == null ? null : emision.getAcomodador().getNombreCompleto());
		respuesta.setNombreLimpiador(emision.getLimpiador() == null ? null : emision.getLimpiador().getNombreCompleto());
//		respuesta.setEspectadoresEmision(emision.getEspectadoresEmision() != null ? emision.getEspectadoresEmision().stream().map( em -> em.getId()).collect( Collectors.toSet() ) : null);
//		respuesta.setEspectadoresNombresEmision(emision.getEspectadoresEmision() != null ? emision.getEspectadoresEmision().stream().map( em -> em.getEspectador().getNombreCompleto()).collect( Collectors.toSet() ) : null);

		return (respuesta);
		
	}
	
	@Override
	public Emision dtoToEmision(EmisionDto emisionDto) {
		
		return (new Emision(
								new EmisionId(salasService.findByNumero(emisionDto.getNumeroSala()).getId(),peliculasService.findByTitulo(emisionDto.getTituloPelicula()).getId(),emisionDto.getFecha()),
								emisionDto.getVisitantes(),
								emisionDto.getPalomitas(),
								peliculasService.findByTitulo(emisionDto.getTituloPelicula()),
								salasService.findByNumero(emisionDto.getNumeroSala()),
								emisionDto.getNombreAcomodador() == null ? null : acomodadorService.acomodadorPorNombre(emisionDto.getNombreAcomodador()),
								emisionDto.getNombreLimpiador() == null ? null : limpiadorService.limpiadorPorNombre(emisionDto.getNombreLimpiador())
							)
				);
//		emisionDto.getEspectadoresEmision() == null ? null : espectadorEmisionService.getEspectadorEmisionBy(new EmisionId(salasService.findByNumero(emisionDto.getNumeroSala()).getId(),peliculasService.findByTitulo(emisionDto.getTituloPelicula()).getId(),emisionDto.getFecha())))
	}

	@Override
	public SalaDto salaToDto(Sala sala) {

		SalaDto respuesta = new SalaDto();
		
		respuesta.setId(sala.getId());
		respuesta.setAforo(sala.getAforo());
		respuesta.setNumero(sala.getNumero());
		respuesta.setPremium(sala.getPremium());
		respuesta.setNumeroFilas(sala.getNumeroFilas());
		respuesta.setNumeroColumnas(sala.getNumeroColumnas());
//		respuesta.setEmisiones(sala.getEmisiones() != null ? sala.getEmisiones().stream().map( e -> e.getEmisionId().getFecha()).collect( Collectors.toList() ) : null);
		
		return respuesta;
		
	}

	@Override
	public Sala dtoToSala(SalaDto salaDto) {

		Sala respuesta = new Sala();
		
		respuesta.setId(salaDto.getId());
		respuesta.setAforo(salaDto.getAforo());
		respuesta.setNumero(salaDto.getNumero());
		respuesta.setPremium(salaDto.getPremium());
		respuesta.setNumeroFilas(salaDto.getNumeroFilas());
		respuesta.setNumeroColumnas(salaDto.getNumeroColumnas());
//		respuesta.setEmisiones(salasService.getEmisionesBySala(salaDto));
		
		return respuesta;
		
	}

	@Override
	public LimpiadorDto limpiadorToDto(Limpiador limpiador) {
		
		LimpiadorDto respuesta = new LimpiadorDto();
		
		respuesta.setId(limpiador.getId());
		respuesta.setNombreCompleto(limpiador.getNombreCompleto());
		respuesta.setEmail(limpiador.getEmail());
		respuesta.setTelefonoMovil(limpiador.getTelefonoMovil());
		respuesta.setTurno(limpiador.getTurno().ordinal());
		respuesta.setSalario(limpiador.getSalario());
//		respuesta.setEmisionesLimpiador(limpiador.getEmisionesLimpiador() != null ? limpiador.getEmisionesLimpiador().stream().map( e -> e.getEmisionId().getFecha()).collect( Collectors.toSet() ) : null);

		return respuesta;
	}

	@Override
	public Limpiador dtoToLimpiador(LimpiadorDto limpiadorDto) {

		Limpiador respuesta = new Limpiador();

		respuesta.setId(limpiadorDto.getId());
		respuesta.setNombreCompleto(limpiadorDto.getNombreCompleto());
		respuesta.setEmail(limpiadorDto.getEmail());
		respuesta.setTelefonoMovil(limpiadorDto.getTelefonoMovil());
		respuesta.setTurno(Turno.values()[limpiadorDto.getTurno()]);
		respuesta.setSalario(limpiadorDto.getSalario());
//		respuesta.setEmisionesLimpiador(limpiadorDto.getEmisionesLimpiador() == null ? null : emisionesService.getEmisionesByLimpiador(limpiadorDto));

		
		return respuesta;
	}

	@Override
	public AcomodadorDto acomodadorToDto(Acomodador acomodador) {
		
		AcomodadorDto respuesta = new AcomodadorDto();

		respuesta.setId(acomodador.getId());
		respuesta.setNombreCompleto(acomodador.getNombreCompleto());
		respuesta.setEmail(acomodador.getEmail());
		respuesta.setTelefonoMovil(acomodador.getTelefonoMovil());
		respuesta.setEdad(acomodador.getEdad());
		respuesta.setAntiguedad(acomodador.getAntiguedad());
		respuesta.setValoracionMedia(acomodador.getValoracionMedia());
//		respuesta.setEmisionesAcomodador(acomodador.getEmisionesAcomodador() != null ? acomodador.getEmisionesAcomodador().stream().map( e -> e.getEmisionId().getFecha()).collect( Collectors.toSet() ) : null);
		
		return respuesta;


	}

	@Override
	public Acomodador dtoToAcomodador(AcomodadorDto acomodadorDto) {
		
		return (new Acomodador (
									acomodadorDto.getId(),
									acomodadorDto.getNombreCompleto(),
									acomodadorDto.getTelefonoMovil(),
									acomodadorDto.getEmail(),
									acomodadorDto.getValoracionMedia(),
									acomodadorDto.getEdad(),
									acomodadorDto.getAntiguedad()
								)
				);
//		acomodadorDto.getEmisionesAcomodador() == null ? null : emisionesService.getEmisionesByAcomodador(acomodadorDto)

	}

	@Override
	public EspectadorDto espectadorToDto(Espectador espectador) {

		return (new EspectadorDto (
									espectador.getId(),
									espectador.getNombreCompleto(),
									espectador.getFechaNacimiento(),
									espectador.getGeneroFavorito().name(),
									espectador.getDineroGastado()
									)
				);
//		espectadorEmisionService.setIdEspectadorEmision(espectador)
	}

	@Override
	public Espectador dtoToEspectador(EspectadorDto espectadorDto) {
		
		return (new Espectador (
									espectadorDto.getId(),
									espectadorDto.getNombreCompleto(),
									espectadorDto.getFechaNacimiento(),
									Genero.valueOf(espectadorDto.getGeneroFavorito()),
									espectadorDto.getDineroGastado()
//									espectadorDto.getEmisionesEspectador() == null ? null : espectadorEmisionService.getEspectadorEmisionByIdEspectador(espectadorDto.getId())
									)
				);
	}

	@Override
	public EspectadorEmisionDto espectadorEmisionToDto(EspectadorEmision espectadorEmision) {
		
		return (new EspectadorEmisionDto(
									espectadorEmision.getId(),
									espectadorEmision.getFila(),
									espectadorEmision.getColumna(),
									espectadorEmision.getValoracionServicio(),
									espectadorEmision.getGastoPalomitas(),
									espectadorEmision.getEspectador().getNombreCompleto(),
									espectadorEmision.getEmision().getEmisionId().getFecha(),
									espectadorEmision.getEmision().getPelicula().getTitulo(),
									espectadorEmision.getEmision().getSala().getNumero()
									)
				);
	}

	@Override
	public EspectadorEmision dtoToEspectadorEmision(EspectadorEmisionDto espectadorEmisionDto) {
		
		return (new EspectadorEmision(
									espectadorEmisionDto.getId(),
									espectadorEmisionDto.getFila(),
									espectadorEmisionDto.getColumna(),
									espectadorEmisionDto.getValoracionServicio(),
									espectadorEmisionDto.getGastoPalomitas(),
									espectadorService.espectadorPorNombre(espectadorEmisionDto.getNombreEspectador()),
									emisionesService.emisionPorFechaPeliSala(espectadorEmisionDto.getFechaEmision(),espectadorEmisionDto.getNombrePelicula(),espectadorEmisionDto.getNumeroSala())
									)
				);

	}

	
	@Override
	public NuevaEntradaDto espectadorEmisionToNuevaEntradaDto(EspectadorEmision espectadorEmision) {
		
		return (new NuevaEntradaDto(
									espectadorEmision.getId(),
									espectadorEmision.getFila(),
									espectadorEmision.getColumna(),
									espectadorEmision.getEspectador().getNombreCompleto(),
									espectadorEmision.getEmision().getEmisionId().getFecha(),
									espectadorEmision.getEmision().getPelicula().getTitulo(),
									espectadorEmision.getEmision().getSala().getNumero()
									)
				);
	}
	
	@Override
	public EspectadorEmision nuevaEntradaDtoToEspectadorEmision(NuevaEntradaDto nuevaEntrada) {
		
		return (new EspectadorEmision(
									nuevaEntrada.getId(),
									nuevaEntrada.getFila(),
									nuevaEntrada.getColumna(),
									null,
									null,
									espectadorService.espectadorPorNombre(nuevaEntrada.getNombreEspectador()),
									emisionesService.emisionPorFechaPeliSala(nuevaEntrada.getFechaEmision(),nuevaEntrada.getNombrePelicula(),nuevaEntrada.getNumeroSala())
									)
				);
				
	}
	
	
	@Override
	public EmisionNuevaDto emisionNuevaToDto(Emision emision) {
		EmisionNuevaDto respuesta = new EmisionNuevaDto();
		
		respuesta.setFecha(emision.getEmisionId().getFecha());
		respuesta.setNumeroSala(emision.getSala().getNumero());
		respuesta.setTituloPelicula(emision.getPelicula().getTitulo());
		respuesta.setNombreAcomodador(emision.getAcomodador() == null ? null : emision.getAcomodador().getNombreCompleto());
		respuesta.setNombreLimpiador(emision.getLimpiador() == null ? null : emision.getLimpiador().getNombreCompleto());
//		respuesta.setEspectadoresEmision(emision.getEspectadoresEmision() != null ? emision.getEspectadoresEmision().stream().map( em -> em.getId()).collect( Collectors.toSet() ) : null);

		return (respuesta);
		
	}
	
	@Override
	public Emision emisionNuevaDtoToEmision(EmisionNuevaDto emisionDto) {
		
		return (new Emision(
								new EmisionId(salasService.findByNumero(emisionDto.getNumeroSala()).getId(),peliculasService.findByTitulo(emisionDto.getTituloPelicula()).getId(),emisionDto.getFecha()),
								null,
								null,
								peliculasService.findByTitulo(emisionDto.getTituloPelicula()),
								salasService.findByNumero(emisionDto.getNumeroSala()),
								emisionDto.getNombreAcomodador() == null ? null : acomodadorService.acomodadorPorNombre(emisionDto.getNombreAcomodador()),
								emisionDto.getNombreLimpiador() == null ? null : limpiadorService.limpiadorPorNombre(emisionDto.getNombreLimpiador())
				));
//		emisionDto.getEspectadoresEmision() == null ? null : espectadorEmisionService.getEspectadorEmisionBy(new EmisionId(salasService.findByNumero(emisionDto.getNumeroSala()).getId(),peliculasService.findByTitulo(emisionDto.getTituloPelicula()).getId(),emisionDto.getFecha())))
	}
	
	@Override
	public EspectadorAsistenciasDto espectadorToEspectadorAsistenciaDto(Espectador espectador) {

		return (new EspectadorAsistenciasDto (
									espectador.getNombreCompleto(),
									espectador.getFechaNacimiento(),
									espectadorService.getNumeroAsitenciasPorEspectador(espectador.getId())
									)
				);
//		espectadorEmisionService.setIdEspectadorEmision(espectador)
	}

	@Override
	public Espectador espectadorAsistenciasDtoToEspectador(EspectadorAsistenciasDto espectadorAsistenciasDto) {
		
		return (new Espectador (espectadorService.espectadorPorNombre(espectadorAsistenciasDto.getNombreCompleto()).getId(),
								espectadorAsistenciasDto.getNombreCompleto(),
								espectadorAsistenciasDto.getFechaNacimiento(),
								espectadorService.espectadorPorNombre(espectadorAsistenciasDto.getNombreCompleto()).getGeneroFavorito(),
								espectadorService.espectadorPorNombre(espectadorAsistenciasDto.getNombreCompleto()).getDineroGastado()
								)
				);
	}
	
	//RECAUDACION POR DIRECTOR
	
	@Override
	public DirectorRecaudacionEntradasDto directorToDirectorRecaudacionEntradasDto(Director director) {
		
		DirectorRecaudacionEntradasDto respuesta = new DirectorRecaudacionEntradasDto();
		
		return (new DirectorRecaudacionEntradasDto (
					director.getNombre(),
					directoresService.getRecaudacionPorDirector(director.getNombre())
				));
		
	}

	@Override
	public Director directorRecaudacionEntradasDtoToDirector(DirectorRecaudacionEntradasDto directorRecaudacionEntradasDto) {
	
		return (directoresService.getDirectorByName(directorRecaudacionEntradasDto.getNombre()));
	}
	
	
	/*************************************CONVERTIDOR DE LISTAS*************************************/
	
	//Convierte lista de entidad en dto y viceversa para pelicula
	@Override
	public Set<PeliculaDto> setPeliculaToSetPeliculaDto(Set<Pelicula> peliculas) {
		
		Set<PeliculaDto> peliculasDto = new HashSet<PeliculaDto>();

		for (Pelicula elemento : peliculas) {
			peliculasDto.add(peliculaToDto(elemento));
		}
		
		return (peliculasDto);
	}

	@Override
	public Set<EmisionDto> setEmisionToSetEmisionDto(Set<Emision> emisiones) {

		Set<EmisionDto> emisionDto = new HashSet<EmisionDto>();
		
		for (Emision elemento : emisiones) {
			emisionDto.add(emisionToDto(elemento));
		}
		
		return (emisionDto);
	}
	
	@Override
	public Set<EspectadorEmisionDto> listEspectadorEmisionToSetEspectadorEmisionDto(List<EspectadorEmision> lista) {
		
		Set<EspectadorEmisionDto> espectadorEmisionDto = new HashSet<EspectadorEmisionDto>();
		
		for (EspectadorEmision elemento : lista) {
			
			espectadorEmisionDto.add(espectadorEmisionToDto(elemento));
		}
		
		return (espectadorEmisionDto);
	}
}
