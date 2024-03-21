package es.curso.cine.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessagesConfiguration {

	@Bean
	public MessageSource messageSource() {
		
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages/messages");
		messageSource.setDefaultLocale(new Locale("es","ES"));
		messageSource.setUseCodeAsDefaultMessage(true);
		
		return messageSource;
	}
}
