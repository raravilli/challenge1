package com.aem.challenge.core.models;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.challenge.core.result.CurrencyResult;
import com.aem.challenge.core.service.CryptoCurrencyService;


@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class CryptoCurrencyComponentModel {
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@SlingObject
    private SlingHttpServletRequest request;
	
	@Inject
	private CryptoCurrencyService service;
	
	private  Resource res;
	
	public List<CurrencyResult> currencyResults;
	
	private int totalCount = 0;
	
	
	
	private String loggedIn = "false";		

	public String getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(String loggedIn) {
		this.loggedIn = loggedIn;
	}

	public void suggestAPI(){
		
		service.getBestResultsAsJson();
		
		
	}
	
	@PostConstruct
	protected void init() {	
		try{	
			this.setRes(request.getResource());
			this.setCurrencyResults(service.getBestResultsList());
		}
		catch(Exception e){
			LOG.error("::Exception occured to fetch the results from ES::"+e);
		}
	}

	public Resource getRes() {
		return res;
	}

	public void setRes(Resource res) {
		this.res = res;
	}

	public CryptoCurrencyService getService() {
		return service;
	}

	public void setService(CryptoCurrencyService service) {
		this.service = service;
	}

	public List<CurrencyResult> getCurrencyResults() {
		return currencyResults;
	}

	public void setCurrencyResults(List<CurrencyResult> currencyResults) {
		this.currencyResults = currencyResults;
	}


}
