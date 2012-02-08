package com.ourpalm.httpserver.servicehandler;

import java.util.Map;

import com.ourpalm.httpserver.core.HttpExchangeAware;
import com.ourpalm.httpserver.core.RequestParametersAware;
import com.ourpalm.httpserver.core.ServiceHandler;
import com.sun.net.httpserver.HttpExchange;

public class DummyServiceHandler implements ServiceHandler,RequestParametersAware,HttpExchangeAware {

	HttpExchange httpExchange;
	@Override
	public void setHttpExchange(HttpExchange httpExchange) {
		this.httpExchange = httpExchange;
	}

	@Override
	public void setRequestParameters(Map params) {
		this.params = params;
	}

	Map<String, Object> params = null;
	
	@Override
	public void handler() {

		System.out.println(params.size());
		
	}

}
