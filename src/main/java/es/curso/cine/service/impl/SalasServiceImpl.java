package es.curso.cine.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.curso.cine.controller.dto.SalaDto;
import es.curso.cine.exception.MiValidationException;
import es.curso.cine.mapper.MapperService;
import es.curso.cine.persistence.entities.Emision;
import es.curso.cine.persistence.entities.Sala;
import es.curso.cine.persistence.repository.EmisionDao;
import es.curso.cine.persistence.repository.SalaDao;
import es.curso.cine.service.SalasService;
import es.curso.cine.utils.CineErrorConstants;

@Service
@Transactional(rollbackFor = MiValidationException.class)
public class SalasServiceImpl implements SalasService {
	
	@Autowired
	@Lazy
	private SalaDao salaDao;
	
	@Autowired
	@Lazy
	private MapperService mapperServices;
	
	@Autowired
	@Lazy
	private EmisionDao emisionDao;
	
	/*******************************GET*******************************/

	@Override
	public SalaDto getSala(Long id) {
		
		Sala respuesta = new Sala();
		
		respuesta = salaDao.findById(id).get();		
		
		return mapperServices.salaToDto(respuesta);
	}

	@Override
	public Set<SalaDto> getSalas() {
		
		List<Sala> respuesta = new ArrayList<Sala>();
		Set<SalaDto> respuestaDto = new HashSet<SalaDto>();

		respuesta = salaDao.findAll();
		
		for (Sala sala: respuesta ) {
					
			respuestaDto.add(mapperServices.salaToDto(sala));
					
		}		

		return (respuestaDto);	
	}
	
	@Override
	public Sala getSalaEntity (Long id) {
		
		Sala respuesta = salaDao.findById(id).get();
		
		return respuesta;
	}
	
	
	/*******************************POST*******************************/

	@Override
	public Long createSala(SalaDto salaDto) throws MiValidationException {
		
		validarNuevaSala(salaDto);
		
		Sala creado = mapperServices.dtoToSala(salaDto);
		salaDao.save(creado);
		
		return (mapperServices.salaToDto(creado).getId());
	}
	
	private void validarNuevaSala(SalaDto salaDto) throws MiValidationException {
		
		if(salaDao.findByNumero(salaDto.getNumero()) != null) 
			throw new MiValidationException(CineErrorConstants.SALA_EXISTENTE,salaDto.getNumero().toString());

	}
	
	/*******************************PUT*******************************/

	@Override
	public Long updateSala(SalaDto sala) {
		
		Sala actualizado = new Sala();
		
		if (getSala(sala.getId()) != null) {

			actualizado = mapperServices.dtoToSala(sala);
			
			salaDao.save(actualizado);
			
			return (mapperServices.salaToDto(actualizado).getId());
		}
		
		return null;
	}
	
	/*******************************DELETE*******************************/

	@Override
	public void deleteSala(Long id) {
		
		salaDao.deleteById(id);
		
	}
	
	/*******************************OTROS*******************************/
	
	@Override
	public List<Emision> getEmisionesBySala(SalaDto salaDto) {
		
		List<Emision> listaEmisiones = new ArrayList<>();
		
		for (Emision emisionListada : emisionDao.findAll()) {
			
			if (emisionListada.getEmisionId().getIdSala() == salaDto.getId()) {
				  
				listaEmisiones.add(emisionListada);

			}
			
		}
		return (listaEmisiones);
		
	}

	/*******************************ESTADISTICAS*******************************/

	@Override
	public SalaDto findUltimaSalaLlena() {
		
		List<Sala> movida = salaDao.encuentraUltimaSalaLlenaEstadistica();
		
		SalaDto salaDto = mapperServices.salaToDto(salaDao.encuentraUltimaSalaLlenaEstadistica().get(0));
		
		return (salaDto);
	}

	@Override
	public Integer numeroSalaPorId(Long id) {
		
		return (salaDao.numeroSalaPorId(id));
	}
	
	@Override
	public Sala findByNumero(Integer numero) {
		
		return (salaDao.findByNumero(numero));
	}
	
	@Override
	public Sala findById(Long id) {
		
		return (salaDao.findById(id).get());
	}
}
