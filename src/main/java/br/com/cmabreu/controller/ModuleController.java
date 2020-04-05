package br.com.cmabreu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cmabreu.model.Module;
import br.com.cmabreu.services.FederateService;

@RestController
@RequestMapping("/modules")
public class ModuleController {

	@Autowired
	private FederateService federateService;		
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<Module> getList() {
		return federateService.getModules();
	}	
	
	@RequestMapping(value = "/{moduleFileName}", method = RequestMethod.GET)
	public @ResponseBody Module getModule( @PathVariable("moduleFileName") String moduleFileName) {
		
		System.out.println(moduleFileName);
		
		return federateService.getModule( moduleFileName );
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public @ResponseBody Integer getCount() {
		return federateService.getModules().size();
	}
	
}
