package br.com.cmabreu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cmabreu.rti1516e.ObjectInstance;
import br.com.cmabreu.services.FederateService;

@RestController
@RequestMapping("/instances")
public class InstanceController {

	@Autowired
	private FederateService federateService;		
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<ObjectInstance> getList() {
		return federateService.getInstances();
	}	
	
	@RequestMapping(value = "/{instanceHandle}", method = RequestMethod.GET)
	public @ResponseBody ObjectInstance getInstance( @PathVariable("instanceHandle") Integer instanceHandle) {
		return federateService.getInstance( instanceHandle );
	}
	
	@RequestMapping(value = "/{objectName}", method = RequestMethod.GET)
	public @ResponseBody ObjectInstance getInstance( @PathVariable("objectName") String objectName) {
		return federateService.getInstance( objectName );
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public @ResponseBody Integer getCount() {
		return federateService.getInstancesCount();
	}
	
}
