package es.curso.cine.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.curso.cine.controller.dto.AcomodadorDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.mapper.MapperService;
import es.curso.cine.persistence.entities.Acomodador;
import es.curso.cine.persistence.entities.EspectadorEmision;
import es.curso.cine.persistence.repository.AcomodadorDao;
import es.curso.cine.service.AcomodadoresService;
import es.curso.cine.service.EspectadorEmisionService;
import es.curso.cine.utils.CineErrorConstants;

@Service
@Transactional(rollbackFor = MiValidationException.class)
public class AcomodadoresServiceImpl implements AcomodadoresService {

	@Autowired
	@Lazy
	private MapperService mapperServices;
	
	@Autowired
	@Lazy
	private AcomodadorDao acomodadorDao;
	
	@Autowired
	@Lazy
	private EspectadorEmisionService espectadorEmisionServices;
	
	
	@Override
	public AcomodadorDto getAcomodador(Long idAcomodador) {

		Acomodador respuesta = new Acomodador();
		
		respuesta = acomodadorDao.findById(idAcomodador).get();		
		
		return mapperServices.acomodadorToDto(respuesta);
		
	}
	
	@Override
	public Boolean existsById(Long idAcomodador) {

		return (acomodadorDao.existsById(idAcomodador));		
	}

	@Override
	public Set<AcomodadorDto> getAcomodadores() {

		List<Acomodador> respuesta = new ArrayList<Acomodador>();
		Set<AcomodadorDto> respuestaDto = new HashSet<AcomodadorDto>();

		respuesta = acomodadorDao.findAll();
		
		for (Acomodador acomodador: respuesta ) {
					
			respuestaDto.add(mapperServices.acomodadorToDto(acomodador));
					
		}		

		return (respuestaDto);	
		
	}

	@Override
	public Long createAcomodador(AcomodadorDto acomodadorDto) throws MiValidationException {
		
		validarNuevoAcomodador(acomodadorDto);
		
		Acomodador creado = new Acomodador();
		
		creado = mapperServices.dtoToAcomodador(acomodadorDto);
		
		acomodadorDao.save(creado);
		
		return (mapperServices.acomodadorToDto(creado).getId());
	}

	private void validarNuevoAcomodador(AcomodadorDto acomodadorDto) throws MiValidationException {
		
		if (acomodadorDao.findByTelefonoMovil(acomodadorDto.getTelefonoMovil()) != null)
			throw new MiValidationException(CineErrorConstants.ACOMODADOR_TELEFONO_EXISTENTE, acomodadorDto.getTelefonoMovil());

		if (acomodadorDao.findByEmail(acomodadorDto.getEmail()) != null)
			throw new MiValidationException(CineErrorConstants.ACOMODADOR_EMAIL_EXISTENTE, acomodadorDto.getEmail());
		
		if (acomodadorDao.findByNombreCompleto(acomodadorDto.getNombreCompleto()) != null)
			throw new MiValidationException(CineErrorConstants.ACOMODADOR_NOMBRE_EXISTENTE, acomodadorDto.getNombreCompleto());
	}
	
	@Override
	public void deleteAcomodador(Long idAcomodador) {
		
		acomodadorDao.deleteById(idAcomodador);		
		
	}

	@Override
	public Long updateAcomodador(AcomodadorDto acomodadorDto) {
		
		Acomodador actualizado = new Acomodador();
		
		if (getAcomodador(acomodadorDto.getId()) != null) {
			
			actualizado = mapperServices.dtoToAcomodador(acomodadorDto);
			
			acomodadorDao.save(actualizado);
			
			return (mapperServices.acomodadorToDto(actualizado).getId());

		}

		return null;
	}

	@Override
	public Acomodador acomodadorPorNombre(String nombre) {
		
		Acomodador acomodadorBuscado = new Acomodador();
		
		acomodadorBuscado = acomodadorDao.buscaAcomodadorPorNombre(nombre);

		return (acomodadorBuscado);
	}

	@Override
	public List<Long> actualizarValoracionAcomodador() {
		
		List<Acomodador> acomodadores = acomodadorDao.findAll();
		List<EspectadorEmision> espectadoresEmisiones = espectadorEmisionServices.getEspectadorEmisionesEntidad();
		List<Long> actualizados = new ArrayList<Long>();
		
		for (Acomodador acomodador : acomodadores) {
			
			Integer numeroNotas = 0;
			Double sumaNotas = 0.00;
			Double valoracionMedia = 0.00;
			
			for (EspectadorEmision elemento: espectadoresEmisiones) {
			
				if (elemento.getEmision().getAcomodador() != null) {

					if (acomodador.getId() == elemento.getEmision().getAcomodador().getId()) {
						
						numeroNotas++;
						sumaNotas += elemento.getValoracionServicio();
						
					}
				}
				
				
			}
			
			valoracionMedia = sumaNotas / numeroNotas;
			acomodador.setValoracionMedia(valoracionMedia);
			acomodadorDao.save(acomodador);
			actualizados.add(acomodador.getId());
			
		}
		
		return actualizados;
		
	}
	
	@Override
	public void actualizarValoracionAcomodadorIndividual(Long id) {

		if (acomodadorDao.existsById(id)) {
			
			List<Double> valoraciones = espectadorEmisionServices.valoracionesPorAcomodador(id);
			Acomodador acomodadorActualizar = acomodadorDao.findById(id).get();
			Double notasTotales = (double) 0;
			
			for (Double elemento : valoraciones) {
				
				notasTotales+= elemento;
			}
						
			acomodadorActualizar.setValoracionMedia(notasTotales / valoraciones.size());
			acomodadorDao.save(acomodadorActualizar);			
		}
	}

}
