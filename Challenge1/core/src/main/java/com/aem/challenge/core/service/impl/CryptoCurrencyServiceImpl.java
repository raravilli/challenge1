package com.aem.challenge.core.service.impl;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.challenge.core.result.CurrencyResult;
import com.aem.challenge.core.result.CurrencyResult.BestQuote;
import com.aem.challenge.core.result.CurrencyResult.Quote;
import com.aem.challenge.core.service.CryptoCurrencyService;
import com.aem.challenge.core.util.CryptoCurrencyUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Designate(ocd = CryptoCurrencyServiceImpl.Config.class)
@Component(service = CryptoCurrencyService.class, immediate = true)
public class CryptoCurrencyServiceImpl  implements CryptoCurrencyService {
	
	
	
	private static final Logger LOG = LoggerFactory.getLogger(CryptoCurrencyServiceImpl.class);
	public static final String SERVICE_NAME = "Crypto Currency Service";
	public static final String SERVICE_DESCRIPTION = "Crypto Currency API Service";
	DecimalFormat df = new DecimalFormat("#.##"); 
	
	List<CurrencyResult> bestResultsList = new LinkedList<CurrencyResult>();

	// configuration to set the JSON API URL
	@ObjectClassDefinition(name = "Crypto Currency Configuration Service", description = "Crypto Currency Configuration Service")
	public static @interface Config {

		@AttributeDefinition(name = "File URL", description = "Input FIle URL", defaultValue = "./data/20180507.json")
		String propertyFileURL() default "./data/20180507.json";

	}

	protected String fileURL;

	@Activate
	protected void activate(final Config config) throws Exception {
		this.fileURL = config.propertyFileURL();
	}

	public String getFileURL() {
		return fileURL;
	}
	
	private List<CurrencyResult> readInputFile(){
		try{
			bestResultsList = new LinkedList<CurrencyResult>();
			ObjectMapper mapper = new ObjectMapper();
			//HttpEntity entity = httpGet(this.fileURL);			
			InputStream is = CryptoCurrencyUtil.getInputStreamFromURL(this.fileURL);			
			if(is != null){
				List<CurrencyResult> results = mapper.readValue(is, new TypeReference<List<CurrencyResult>>(){});
				for(CurrencyResult result : results){
					List<Quote> quotes = result.getQuotes();
					Collections.sort(quotes, CurrencyResult.ensureSortedByTime );
					BestQuote bestQuote = findBestSlots(quotes);
					if(bestQuote.getSell() != null && bestQuote.getBuy() != null){
						CurrencyResult bestResult = new CurrencyResult();
						bestResult.setCurrency(result.getCurrency());
						bestResult.setBestQuote(bestQuote);
						bestResultsList.add(bestResult);					
					}
				}
			}
		}catch (Exception e){
			LOG.error(" :: Error occured reading input file ::"+e);
		}		
		return bestResultsList;
	}
	
	private BestQuote findBestSlots(List<Quote> quotes){		
		double diff = Double.MIN_VALUE;
		BestQuote bestQuote = new BestQuote();
		Quote sellQuote = null;
		Quote buyQuote = null;
		double currentDiff;
		/* the best buy and sell timings are captured */ 
		for(int i=0; i< quotes.size()-1; i++){
			for(int j=i+1; j< quotes.size(); j++ ){
				currentDiff = quotes.get(j).getPrice() - quotes.get(i).getPrice();
				if(currentDiff > diff){
					diff = currentDiff;
					sellQuote = quotes.get(j);
					buyQuote = 	quotes.get(i);		
				}
			}
		}
		if(sellQuote != null && buyQuote != null){
			sellQuote.setTime(CryptoCurrencyUtil.convertTimeFormatToAmPm(sellQuote.getTime()));
			buyQuote.setTime(CryptoCurrencyUtil.convertTimeFormatToAmPm(buyQuote.getTime()));
			bestQuote.setSell(sellQuote);
			bestQuote.setBuy(buyQuote);
			bestQuote.setDiff(df.format(diff));
		}
		return bestQuote;
	}
	
	public List<CurrencyResult> getBestResultsList() {
		return readInputFile();
	}
	
	public String getBestResultsAsJson(){
		ObjectMapper mapper = new ObjectMapper();
		try {
			 return mapper.writeValueAsString(readInputFile());
		} catch (JsonProcessingException e) {
			LOG.error(" Exception in converting object to JSON :"+e);
		}
		return null; 
	}

	public void setBestResultsList(List<CurrencyResult> bestResultsList) {
		this.bestResultsList = bestResultsList;
	}
}
