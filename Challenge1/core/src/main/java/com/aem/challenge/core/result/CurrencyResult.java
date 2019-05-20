
package com.aem.challenge.core.result;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Currency Results
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "currency"})
public class CurrencyResult {
	
	private static final Logger LOG = LoggerFactory.getLogger(CurrencyResult.class);
	
	private List<Quote> quotes = new ArrayList<>();
	
	@JsonProperty(value = "bestQuote")
    private BestQuote bestQuote;
	
	@JsonProperty(value = "currency")
    private String currency;
	
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty(value = "date")
    private String date;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Quote {

	   	@JsonProperty(value = "time")
	    private String time;

	    @JsonProperty(value = "price")
	    private String price;
	   
	    public String getTime() {
			return time;
		}

		public double getPrice() {
			return Double.parseDouble(this.price); 
		}

		public void setTime(String time) {
			this.time = time;
		}

		public void setPrice(String price) {
			this.price = price;
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPropertyOrder({ "buy", "sell"})
	public static class BestQuote {
		
		@JsonProperty(value = "sell")
	    private Quote sell;
		
		@JsonProperty(value = "buy")
	    private Quote buy;
		
		@JsonProperty(value = "diff")
	    private String diff;
		
		public Quote getSell() {
			return sell;
		}

		public Quote getBuy() {
			return buy;
		}

		public void setSell(Quote sell) {
			this.sell = sell;
		}

		public void setBuy(Quote buy) {
			this.buy = buy;
		}

		public String getDiff() {
			return diff;
		}

		public void setDiff(String diff) {
			this.diff = diff;
		}

		
		
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}

	public String getCurrency() {
		return currency;
	}

	public String getDate() {
		return (date != null) ? date : "";
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public static final Comparator<Quote> ensureSortedByTime = 
			new Comparator<Quote>() {
		public int compare(Quote e1, Quote e2) {
			try{
				return e1.getTime().compareTo(e2.getTime());
				//return (e1.getPrice() < e2.getPrice() ? -1 : (e1.getPrice() == e2
					//	.getPrice() ? 0 : 1));
			}catch(Exception e){
				LOG.error(":: Exception occurred to compare the quotes ::"+e);
			}
			return 0;
			
		}
	};

	public BestQuote getBestQuote() {
		return bestQuote;
	}

	public void setBestQuote(BestQuote bestQuote) {
		this.bestQuote = bestQuote;
	}
	
}
