package br.com.petwell.config;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("rest")
public class ApplicationConfig extends ResourceConfig{
	public ApplicationConfig(){
		packages("br.com.petwell.resource");
	 
        //Register Auth Filter here
        register(AuthenticationFilter.class);
	}
}
