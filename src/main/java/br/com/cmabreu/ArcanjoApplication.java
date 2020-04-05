package br.com.cmabreu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"br.com.cmabreu.repository"})
@EntityScan( basePackages = {"br.com.cmabreu.model"} )
public class ArcanjoApplication {

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }	
    
	public static void main(String[] args) {
		SpringApplication.run(ArcanjoApplication.class, args);
	}
	
}
