package es.curso.cine.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.curso.cine.controller.dto.LimpiadorDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.mapper.MapperService;
import es.curso.cine.persistence.entities.Limpiador;
import es.curso.cine.persistence.entities.types.Turno;
import es.curso.cine.persistence.repository.LimpiadorDao;
import es.curso.cine.service.LimpiadoresService;
import es.curso.cine.utils.CineErrorConstants;

@Service
@Transactional(rollbackFor = MiValidationException.class)
public class LimpiadoresServiceImpl implements LimpiadoresService{

	@Autowired
	@Lazy
	MapperService mapperServices;
	
	@Autowired
	@Lazy
	private LimpiadorDao limpiadorDao;
	
	/*******************************GET*******************************/
	
	@Override
	public LimpiadorDto getLimpiador(Long idLimpiador) {

		Limpiador respuesta = new Limpiador();
		
		respuesta = limpiadorDao.findById(idLimpiador).get();		
		
		return mapperServices.limpiadorToDto(respuesta);
	}

	@Override
	public Set<LimpiadorDto> getLimpiadores() {
		
		List<Limpiador> respuesta = new ArrayList<Limpiador>();
		Set<LimpiadorDto> respuestaDto = new HashSet<LimpiadorDto>();

		respuesta = limpiadorDao.findAll();
		
		for (Limpiador limpiador: respuesta ) {
					
			respuestaDto.add(mapperServices.limpiadorToDto(limpiador));
					
		}		

		return (respuestaDto);	
	}
	
	/*******************************POST*******************************/

	@Override
	public Long createLimpiador(LimpiadorDto limpiadorDto) throws MiValidationException {
		
		validarNuevoLimpiador(limpiadorDto);
		
		Limpiador creado = mapperServices.dtoToLimpiador(limpiadorDto);
		
		limpiadorDao.save(creado);
		
		return (mapperServices.limpiadorToDto(creado).getId());
	}

	private void validarNuevoLimpiador(LimpiadorDto limpiadorDto) throws MiValidationException {
		
		if (limpiadorDao.findByTelefonoMovil(limpiadorDto.getTelefonoMovil()) != null)
			throw new MiValidationException(CineErrorConstants.LIMPIADOR_TELEFONO_EXISTENTE, limpiadorDto.getTelefonoMovil());
		
		if (limpiadorDao.findByEmail(limpiadorDto.getEmail()) != null)
			throw new MiValidationException(CineErrorConstants.LIMPIADOR_EMAIL_EXISTENTE, limpiadorDto.getEmail());
		
		if (limpiadorDao.findByNombreCompleto(limpiadorDto.getNombreCompleto()) != null)
			throw new MiValidationException(CineErrorConstants.LIMPIADOR_NOMBRE_EXISTENTE, limpiadorDto.getNombreCompleto());
		
		if (!Turno.findByValue(limpiadorDto.getTurno()))
			throw new MiValidationException(CineErrorConstants.LIMPIADOR_TURNO_INEXISTENTE);
	}
	
	/*******************************PUT*******************************/

	@Override
	public Long updateLimpiador(LimpiadorDto limpiadorDto) {
		
		Limpiador actualizado = new Limpiador();
		
		if (getLimpiador(limpiadorDto.getId()) != null) {
			
			actualizado = mapperServices.dtoToLimpiador(limpiadorDto);
			
			limpiadorDao.save(actualizado);
			
			return (mapperServices.limpiadorToDto(actualizado).getId());

		}

		return null;
	}
	
	
	/*******************************DELETE*******************************/
	
	@Override
	public void deleteLimpiador(Long idLimpiador) {
		
		limpiadorDao.deleteById(idLimpiador);
		
	}

	@Override
	public Limpiador limpiadorPorNombre(String nombre) {
		
		Limpiador limpiadorBuscado = new Limpiador();
		
		limpiadorBuscado = limpiadorDao.buscaLimpiadorPorNombre(nombre);

		return (limpiadorBuscado);
		
	}

}
