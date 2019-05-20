package com.aem.challenge.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoCurrencyUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(CryptoCurrencyUtil.class);
	
	/**
	 * @param hours
	 * @return time format changed from 1350 to 13:50PM
	 */
	public static String convertTimeFormatToAmPm(String hours){
		try{
			int time = Integer.valueOf(hours);
			Date date = new SimpleDateFormat("hhmm").parse(String.format("%04d", time));
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
			return sdf.format(date);
		}catch(Exception e){
			LOG.error(" :: Exception occured while converting the date ::"+e);
		}
		return "";
	}
	
	/**
	 * @param url
	 * @return 
	 * This API can be used for HTTPClient calls
	 */
	private static HttpEntity HTTPGet(String url){
		try{
			HttpClientBuilder builder = HttpClientBuilder.create();
			HttpGet get = new HttpGet(url);
			get.addHeader("Content-Type", "application/json;charset=utf-8");
	       	CloseableHttpResponse response = builder.build().execute(get);
	       	return response.getEntity();
		}catch (Exception e) {
			LOG.error(" :: Error occured to read through HTTP Client ::"+e);
		}
		return null;		
	}
	
	/**
	 * @param url
	 * @return
	 * This API used to read file from File System
	 */
	public static InputStream getInputStreamFromURL(String url){
		File file = new File(url);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			return fis;
		}catch(Exception e){
			LOG.error("could not read file from local system");
		}
		return null;
	}
	
}
