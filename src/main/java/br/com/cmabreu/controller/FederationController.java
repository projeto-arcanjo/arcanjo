package br.com.cmabreu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cmabreu.model.Config;
import br.com.cmabreu.services.FederateService;


@RestController
@RequestMapping("/federation")
public class FederationController {

	@Autowired
	private FederateService federateService;	
	
	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public @ResponseBody String startFederation() {
		String result = "UNDEFINED"; 
		try {
			result = federateService.startRti();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return result;
	}	


	
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public @ResponseBody Boolean refreshData() {
		try {
			federateService.refreshData();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return federateService.isStarted();
	}
		
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public @ResponseBody Config getConfig() {
		return new Config( federateService.getFederationName(), federateService.getHlaVersion() );
	}	
	
}
