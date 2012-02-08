package com.ourpalm.httpserver.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ourpalm.httpserver.core.config.ContextMapping;
import com.ourpalm.httpserver.core.config.EntityMapping;
import com.ourpalm.httpserver.util.Reflection;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CjshHttpHandler implements HttpHandler,ContextMappingAware{

	Log logger = LogFactory.getLog(CjshHttpHandler.class);
	private ContextMapping contextMapping;
	
	public void setContext(ContextMapping contextMapping){
		this.contextMapping = contextMapping;
	}
	
	@Override
	public void handle(HttpExchange ex) throws IOException {
		Map<String, String>  map = new HashMap<String, String>();
		if( ex.getRequestURI().getQuery()!=null ){
			map = parseHttpParams(ex.getRequestURI().getQuery());
		}
		EntityMapping entityMapping = contextMapping.getMappingEntity(ex.getRequestURI().getPath());
		
		String className = entityMapping.getClassName();
		try {
			Object obj = Reflection.reflect(className);
			
			if(obj instanceof ContextMappingAware)
				((ContextMappingAware)obj).setContext(contextMapping);
			if(obj instanceof RequestParametersAware)
				((RequestParametersAware)obj).setRequestParameters(map);
			if(obj instanceof HttpExchangeAware)
				((HttpExchangeAware)obj).setHttpExchange(ex);
			
			((ServiceHandler)obj).handler();
		} catch (Exception e ){
			logger.error("Set Handler error!",e);
		}
		
	}
	
	
	
	
	private Map<String, String> parseHttpParams(String query){
		
		Map<String, String> map = new HashMap<String, String>();
		
		String[] params = query.split("&");
		
		for(String param : params){
		
			String[] kv = param.split("=");
			map.put(kv[0], kv[1]);
			
		}
		
		return map;
	}

}
