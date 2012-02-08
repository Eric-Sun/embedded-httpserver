package com.ourpalm.httpserver.core;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ourpalm.commons.Lifecycle;
import com.ourpalm.httpserver.core.config.ContextMapping;
import com.ourpalm.httpserver.core.config.EntityMapping;

public class HttpServerConfig implements Lifecycle{
	Log logger = LogFactory.getLog(HttpServerConfig.class);
	private String path = null ;
	private static Map<String, ContextMapping> contextMap = new HashMap<String, ContextMapping>();
	
	public Map<String, ContextMapping> getContextMap() {
		return contextMap;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
		
		File cfgFile = new File(path);
		
		loadCfg(cfgFile);
		
	}

	
	public void setConfigPath(String path){
		this.path = path; 
	}
	
	
	private void loadCfg(File cfgFile){
		
		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = fac.newDocumentBuilder();
			Document doc = builder.parse(cfgFile);
			Element rootElement = doc.getDocumentElement();
			NodeList contextList = rootElement.getElementsByTagName("mapping-context");
			
			for( int i = 0; i< contextList.getLength();i++){
				Node n = contextList.item(i);
				String context = n.getAttributes().getNamedItem("context").getTextContent();
				String contextClassName = n.getAttributes().getNamedItem("contextClass").getTextContent();
				
				ContextMapping contextMapping = new ContextMapping();
				Map<String, EntityMapping> urlMapping = new HashMap<String, EntityMapping>();
				contextMapping.setContext(context);
				contextMapping.setContextHandlerClassName(contextClassName);
				contextMapping.setUrlMapping(urlMapping);
				NodeList entityList = n.getChildNodes();
				for ( int j=0;j<entityList.getLength();j++){
					EntityMapping entityMapping = new EntityMapping();
					Node entity = entityList.item(j);
					if(!entity.getNodeName().equals("mapping-entry"))
						continue;
					NodeList urlAndClass = entity.getChildNodes();
					String url = null;
					String className = null;
					for( int k=0;k<urlAndClass.getLength();k++){
						Node uAndC= urlAndClass.item(k);
						if(uAndC.getNodeName().equals("url"))
							url = uAndC.getTextContent();
						else if(uAndC.getNodeName().equals("handle-class"))
							className = uAndC.getTextContent(); 
					}
					entityMapping.setClassName(className);
					entityMapping.setUrl(url);
					urlMapping.put(context+url, entityMapping);
				}
				
				contextMap.put(context, contextMapping);
			}
			
			
		} catch (Exception e) {
			
			logger.error("load config error!",e);
		}
	}
	
}
