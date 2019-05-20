package com.aem.challenge.core.servlets;


import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
//import org.apache.felix.scr.annotations.Reference;
//import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.challenge.core.service.CryptoCurrencyService;


@Component(service=Servlet.class,immediate = true,
        property={
                "description" + "=Crypto Currency Servlet",
                "sling.servlet.methods=" + "GET",
                "sling.servlet.extensions=" + "json",
                "sling.servlet.paths=" + "/bin/crypto/currency"
        })
public class CryptoCurrencyServlet extends SlingSafeMethodsServlet {

	@Reference
	private CryptoCurrencyService service;
	
	private static final Logger LOG = LoggerFactory.getLogger(CryptoCurrencyServlet.class);


	public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)throws IOException{
       
       response.setContentType("application/json");

       try {
    	   	LOG.info(":: Servlet execution started ::");
    	   	response.getWriter().write(service.getBestResultsAsJson());
	       	LOG.info(":: Servlet executed successfully ::");
       } catch (Exception e) {
           LOG.error("{} Exception! ", new Object[] {e.getMessage(), e});
       }
   }


	public CryptoCurrencyService getService() {
		return service;
	}


	public void setService(CryptoCurrencyService service) {
		this.service = service;
	}

}
