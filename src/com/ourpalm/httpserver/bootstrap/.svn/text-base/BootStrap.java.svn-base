package com.ourpalm.httpserver.bootstrap;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ourpalm.commons.Lifecycle;
import com.ourpalm.httpserver.core.CjshHttpHandler;
import com.ourpalm.httpserver.core.ContextMappingAware;
import com.ourpalm.httpserver.core.HttpServerConfig;
import com.ourpalm.httpserver.util.Reflection;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class BootStrap implements Lifecycle{
	Log logger = LogFactory.getLog(BootStrap.class);
	private int port;
	private String filePath;
	public BootStrap(int port,String filePath){
		this.filePath= filePath;
		this.port = port;
	}
	
	HttpServer hs;
	
	@Override
	public void destroy() {
	}

	@Override
	public void init() {
		HttpServerConfig config = new HttpServerConfig();
		config.setConfigPath(filePath);
		config.init();
		logger.info("Load config successfully! filePath ="+filePath);
		try {
			hs = HttpServer.create(new InetSocketAddress(port),0);
			
			for ( String context  : config.getContextMap().keySet()) {
				String className = config.getContextMap().get(context).getContextHandlerClassName();
				Object obj = Reflection.reflect(className);
				((ContextMappingAware)obj).setContext(config.getContextMap().get(context));
				hs.createContext(context,(HttpHandler)obj);   
			}
			hs.setExecutor(null); // creates a default executor   
			hs.start();  
			logger.info("Server Start ! Port = "+port);
		} catch (Exception e) {
			logger.error("Start the serve ERROR!",e);
		}   
		 
	}

	
	public static void main(String[] args){
		BootStrap st = new BootStrap(8880,"hs-urlmapping.xml");
		
		st.init();
	}
	
}
