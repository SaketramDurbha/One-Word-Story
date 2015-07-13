package me.onewordstory.services;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.Application;

import com.owlike.genson.ext.jaxrs.GensonJsonConverter;

public class MyApplication extends ResourceConfig {
	final Application application = new ResourceConfig()
    .register(MultiPartFeature.class)
    .register(GensonJsonConverter.class);
	
	//final ResourceConfig config = new ResourceConfig().register(GensonJsonConverter.class);
	
	public MyApplication() {
	    register(MultiPartFeature.class);
	    register(GensonJsonConverter.class);
	    /*
	    register(com.wordnik.swagger.jaxrs.listing.ApiListingResource.class);
	    register(com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider.class);
	    register(com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON.class);
	    register(com.wordnik.swagger.jaxrs.listing.ResourceListingProvider.class);
	    */
	}
	
	/*
	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		// register resources and features
		classes.add(MultiPartFeature.class);
		classes.add(UploadService.class);
		classes.add(LoggingFilter.class);
		return classes;
	}
	*/
}