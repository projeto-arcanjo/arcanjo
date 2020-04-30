package br.com.cmabreu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cmabreu.services.FederateService;

@RestController
@RequestMapping("/instances")
public class InstanceController {

	@Autowired
	private FederateService federateService;		
	
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public @ResponseBody Integer refresh() {
		return federateService.sendObjectsToInterface();
	}
	
}
