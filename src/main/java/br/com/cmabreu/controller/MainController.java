package br.com.cmabreu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cmabreu.dto.UserLesserDTO;

@Controller
public class MainController extends BasicController  {

	
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, HttpSession session ) {
		model.addAttribute( "user", getLoggedUser( session ) );
		model.addAttribute( "midasLocation", getMidasLocation() );
		model.addAttribute("milsymbolLocation", getMilsymbolLocation() );		
		return "index";
	}	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root(Model model, HttpSession session ) {
		model.addAttribute( "user", getLoggedUser( session ) );
		model.addAttribute( "midasLocation", getMidasLocation() );
		model.addAttribute("milsymbolLocation", getMilsymbolLocation() );
		return "index";
	}	
	
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String dashboard(Model model, HttpSession session ) {
		model.addAttribute( "user", getLoggedUser( session ) );
		model.addAttribute( "midasLocation", getMidasLocation() );
		return "manager";
	}	
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model, HttpSession session ) {
		UserLesserDTO udto = getLoggedUser( session );
		String result = "index";
		model.addAttribute("milsymbolLocation", getMilsymbolLocation() );
		model.addAttribute( "user", udto );
		model.addAttribute( "midasLocation", getMidasLocation() );
		return result;
	}	
	
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String loginPage(Model model, HttpSession session ) {
		model.addAttribute( "midasLocation", getMidasLocation() );
		return "loginPage";
	}
	
	@RequestMapping(value = "/loginPageError", method = RequestMethod.GET)
	public String loginPageError(Model model, HttpSession session ) {
		model.addAttribute( "midasLocation", getMidasLocation() );
		return "loginPageError";
	}	

	
	@RequestMapping(value = "/sidebar", method = RequestMethod.GET)
	public String sidebar(Model model, HttpSession session ) {
		return "sidebar";
	}


		

}
