package es.curso.cine.controller.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedResponseDto<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5651937194249218207L;

	private long elementosTotales;
	
	private List<T> lista;
	
	private boolean primera;
	
	private boolean ultima;
	
	private int totalPaginas;
	
	private int sizePagina;
	
	private int numeroPagina;
	
	private int elementosDeLaPagina;
	
	public PagedResponseDto(Page<T> pagina) {
		
		elementosTotales = pagina.getTotalElements();
		primera = pagina.isFirst();
		ultima = pagina.isLast();
		totalPaginas = pagina.getTotalPages();
		sizePagina = pagina.getSize();
		numeroPagina = pagina.getNumber();
		elementosDeLaPagina = pagina.getNumberOfElements();
		lista = new ArrayList<>(pagina.getSize());
		lista.addAll(pagina.getContent());
	}
	
	public <W> PagedResponseDto(Page<W> pagina, Function<W,T> funcionTransformacion) {
		
		elementosTotales = pagina.getTotalElements();
		primera = pagina.isFirst();
		ultima = pagina.isLast();
		totalPaginas = pagina.getTotalPages();
		sizePagina = pagina.getSize();
		numeroPagina = pagina.getNumber();
		elementosDeLaPagina = pagina.getNumberOfElements();
		lista = new ArrayList<>(pagina.getSize());
		lista.addAll(pagina.getContent().stream().map(funcionTransformacion).collect(Collectors.toList()));
	}
}
