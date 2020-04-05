package br.com.cmabreu.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

@Controller
public class ErroHandlerController implements ErrorController {

	@Autowired
	private ErrorAttributes errorAttributes;

	@Value("${terena.midas.location}")
	private String midasLocation;	
	
    @RequestMapping("/error")
    public String handleError( Model model, HttpServletRequest request ) {
    	ServletWebRequest servletWebRequest = new ServletWebRequest( request );
    	Map<String, Object> ea = this.errorAttributes.getErrorAttributes( servletWebRequest, true );
    	
    	Integer status = (Integer)ea.get("status");
    	String error = (String)ea.get("error");
    	String message = (String)ea.get("message");
    	String path = (String)ea.get("path");
    	
    	model.addAttribute( "path", path );
    	model.addAttribute( "status", status );
    	model.addAttribute( "error", error );
    	model.addAttribute( "message", message );
    	model.addAttribute( "midasLocation", midasLocation );
        return "error";
    }
 
    @Override
    public String getErrorPath() {
        return "/error";
    }	
	
}
