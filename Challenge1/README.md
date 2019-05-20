# Solution Implementation

Solution of Challenge1 test is provided in AEM through a Sling Model, OSGI service and a front-end component

## Core Implementation - Java

The main implementations are:

* CryptoCurrencyService.java : An OSGI service impl which reads the JSON file URL from OSGI configuration. This service mainly exposes two APIs. 
	1.  getBestResultsList() - reads json, convert them into CurrencyResult.java model (using Jackson-JSON APIs). Then it identifies the best buy and sell 		of each currency and return the results in list. 
	2.  getBestResultsAsJson() - calls getBestResultsList() API of this service and returns the result in JSON format
	
* CryptoCurrencyUtil.java : Util that can be used to call HTTPClient for external URLs, read inputStream from File System etc.

* CryptoCurrencyComponentModel.java : Sling Model class that calls service APIs. This sling model is used in apps component ('/apps/Challenge1/components/content/crypto-currency') to render the best buy and sell table in front-end

* CryptoCurrencyServlet.java : A sling servlet exposed by servlet path '/bin/crypto/currency'. This returns result in JSON. The result is appended at the end of this documentation.

## Apps Implementation - Sightly

* crypto-currency : A component is created to render the table in the required format. Using sightly, sling model class is invoked and best buy/sell prices are displayed. 

## Display 

* To see the component working after deployment of this code base, hit the en.html page under content - http://localhost:4502/content/Challenge1/en.html

## Note : Screenshot of component view in front-end are uploaded to GIT under /DevelopedComp-Screenshots folder for reference