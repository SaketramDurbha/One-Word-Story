package me.onewordstory.services;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.Application;

import com.owlike.genson.ext.jaxrs.GensonJsonConverter;

public class MyApplication extends ResourceConfig {
	
	final Application application = new ResourceConfig()
    .register(MultiPartFeature.class)
    .register(GensonJsonConverter.class);
	
	public MyApplication() {
	    register(MultiPartFeature.class);
	    register(GensonJsonConverter.class);
	}
	
}