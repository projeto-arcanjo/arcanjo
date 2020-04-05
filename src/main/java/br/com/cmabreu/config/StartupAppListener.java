package br.com.cmabreu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.cmabreu.services.FederateService;

@Component
public class StartupAppListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private FederateService federateService;	

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			federateService.startRti();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}


}
