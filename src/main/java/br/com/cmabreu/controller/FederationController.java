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
	
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public @ResponseBody Config getConfig() {
		return new Config( federateService.getFederationName(), federateService.getHlaVersion() );
	}	
	
	
}
