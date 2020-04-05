package br.com.cmabreu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cmabreu.rti1516e.InteractionClass;
import br.com.cmabreu.services.FederateService;

@RestController
@RequestMapping("/interactions")
public class InteractionController {

	@Autowired
	private FederateService federateService;		
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<InteractionClass> getList() {
		return federateService.getInteractions();
	}	
	
	@RequestMapping(value = "/{interactionName}", method = RequestMethod.GET)
	public @ResponseBody InteractionClass getInteraction( @PathVariable("interactionName") String interactionName) {
		return federateService.getInteraction( interactionName );
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public @ResponseBody Integer getCount() {
		return federateService.getInteractions().size();
	}
	
}
