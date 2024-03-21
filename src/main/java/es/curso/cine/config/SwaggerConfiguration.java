package es.curso.cine.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

	private static final String SWAGGER_SCAN_PACKAGE = "es.curso.cine";
//
//	@Value("${spring.application.name}")
//	private String swaggerTitle;
//
//	@Value("${swagger.description}")
//	private String swaggerDescription;
//
//	@Value("${swagger.version}")
//	private String swaggerVersion;
//
	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_PACKAGE))
				.paths(PathSelectors.any()).build()
				.directModelSubstitute(LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(LocalDateTime.class, java.util.Date.class)
				.directModelSubstitute(Pageable.class, SwaggerPageable.class);
	}
	
	@Getter
	private static class SwaggerPageable {
		
		private Integer size;
		private Integer page;
		private String[] sort;
	}
}
