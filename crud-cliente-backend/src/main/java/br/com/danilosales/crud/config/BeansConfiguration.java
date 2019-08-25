package br.com.danilosales.crud.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import br.com.danilosales.crud.model.audit.AuditorAwareImpl;

@Component
public class BeansConfiguration {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public AuditorAwareImpl auditorAware() {
		return new AuditorAwareImpl();
	}
	
}
