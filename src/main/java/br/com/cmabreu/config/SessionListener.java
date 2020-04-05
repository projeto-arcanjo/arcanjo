package br.com.cmabreu.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionListener implements HttpSessionListener {

    @Autowired
    private SessionRegistry sessionRegistry;
 
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        sessionRegistry.addSession(se.getSession());
    }
 
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        sessionRegistry.removeSession( se.getSession() );
    }	
	
}
