package com.ourpalm.httpserver.core.config;

import java.util.HashMap;
import java.util.Map;

public class ContextMapping {

	private String context;
	
	private String contextHandlerClassName;
	
	private Map<String, EntityMapping> urlMapping = new HashMap<String, EntityMapping>();
	
	
	public String getContext() {
		return context;
	}


	public void setContext(String context) {
		this.context = context;
	}


	public String getContextHandlerClassName() {
		return contextHandlerClassName;
	}


	public void setContextHandlerClassName(String contextHandlerClassName) {
		this.contextHandlerClassName = contextHandlerClassName;
	}


	public Map<String, EntityMapping> getUrlMapping() {
		return urlMapping;
	}


	public void setUrlMapping(Map<String, EntityMapping> urlMapping) {
		this.urlMapping = urlMapping;
	}


	public EntityMapping getMappingEntity(String url){
		return urlMapping.get(url);
	}
	
	
	
	
}
