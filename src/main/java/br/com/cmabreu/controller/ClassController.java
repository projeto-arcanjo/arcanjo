package br.com.cmabreu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cmabreu.rti1516e.ObjectClass;
import br.com.cmabreu.services.FederateService;

@RestController
@RequestMapping("/classes")
public class ClassController {

	@Autowired
	private FederateService federateService;		
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<ObjectClass> getList() {
		return federateService.getClasses();
	}	
	
	@RequestMapping(value = "/byname/{className}", method = RequestMethod.GET)
	public @ResponseBody ObjectClass getClassByName( @PathVariable("className") String className) {
		return federateService.getClass( className );
	}

	
	@RequestMapping(value = "/byhandle/{classHandle}", method = RequestMethod.GET)
	public @ResponseBody ObjectClass getClassByHandle( @PathVariable("classHandle") Integer classHandle) {
		return federateService.getClass( classHandle );
	}
	
	
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public @ResponseBody Integer getCount() {
		return federateService.getClasses().size();
	}
	
}
