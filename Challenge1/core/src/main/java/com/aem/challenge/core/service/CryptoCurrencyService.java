package com.aem.challenge.core.service;

import java.util.List;

import com.aem.challenge.core.result.CurrencyResult;



/**
 * @author prasanth.aravilli
 * Service which exposes API for best crypto currency prices
 *
 */
public interface CryptoCurrencyService {

	public String getFileURL();
	
	/**
	 * @return best List of CurrencyResult objects
	 * 
	 */
	public List<CurrencyResult> getBestResultsList();
	
	/**
	 * @return the same list as  getBestResultsList but in JSON format
	 */
	public String getBestResultsAsJson();
	
}