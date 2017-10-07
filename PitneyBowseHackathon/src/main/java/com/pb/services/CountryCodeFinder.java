package com.pb.services;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.json.Json;

import org.json.JSONArray;
import org.json.XML;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.util.Base64;
import org.json.simple.JSONObject;

/**
 * Code sample for accessing GeoTax API. This program demonstrates the capability of GeoTax API 
 * to get particular tax rates for tax rate type (General or Auto) by address or location in XML and JSON response formats.
 * To make it work, make sure to update your API Key and SECRET before running this code. 
 */
public class CountryCodeFinder {
	
	//FIXME Assign your API Key here
	private static String API_KEY = "";
		
	//FIXME Assign your Secret here
	private static String SECRET = "";
		
	private static String OAUTH2_TOKEN_URL = "http://api.pitneybowes.com/oauth/token";

	private static final String API_FRAGMENT = "geotax/v1/";
	
	private static String LOCATION_INTELLIGENCE_API_URL = "https://api.pitneybowes.com/location-intelligence/" + API_FRAGMENT;
	
	private static final String ACCESS_TOKEN = "access_token";

	private static final String BEARER = "Bearer ";

	private static final String BASIC = "Basic ";

	private static final String CLIENT_CREDENTIALS = "client_credentials";

	private static final String GRANT_TYPE = "grant_type";

	private static final String AUTH_HEADER = "Authorization";

	private static final String COLON = ":";
	
	private static final String CURRENT_DIRECTORY = "user.dir";

	private static final String LINE_SEPERATOR = "line.separator";
	

	private static final String TAX_URL = "tax/";

	private static final String TAXRATE_URL = "taxrate/";

	private static final String BYLOCATION = "/bylocation";

	private static final String BYADDRESS = "/byaddress";
	
	public static HashMap<String,String> countryFromMap = new HashMap<String,String>();
	
	public static HashMap<String,String> countryToMap = new HashMap<String,String>();


	private static final String LOCATION_TAX_GEOTAX_REQUEST_SRC = "\\src\\main\\resources\\geotaxTaxBatchByLocation.json";

	private static final String LOCATION_TAXRATE_GEOTAX_REQUEST_SRC = "\\src\\main\\resources\\geoTaxRateBatchByLocation.json";
	
	private static final String ADDRESS_TAX_GEOTAX_REQUEST_SRC = "\\src\\main\\resources\\geoTaxBatchByAddress.json";

	private static final String ADDRESS_TAXRATE_GEOTAX_REQUEST_SRC = "\\src\\main\\resources\\geoTaxRateBatchByAddress.json";
	
	private static final String IPD_GEOTAX_BYADDRESS_REQUEST_SRC = "\\src\\main\\resources\\ipdGeoTaxByLocationBatchRequest.json";

	private static String accessToken;
	
	//public static void main(String[] args) { 
		//getDutiesAndTaxes();
	//}
	
private static final String API_FRAGMENT1 = "geotax/v1/";
	
	private static String LOCATION_INTELLIGENCE_API_URL1 = "http://www.dutycalculator.com/api2.1/2bac6dba1354599a/supported-countries/from?display_alpha2_code=1";
	private static String LOCATION_INTELLIGENCE_API_URL2 = "http://www.dutycalculator.com/api2.1/2bac6dba1354599a/supported-countries/to";
	/**
	 * Returns particular tax rate by address in XML or JSON formats
	 * @param responseTypeIsXml
	 * @param taxRateTypeId			Type of tax, it can be "Auto" for Auto tax and "General" got General tax
	 * @param address
	 */
	
	public static HashMap<String,String> getCountryMapFrom() {
		//String apiUrl = "taxrate/" + taxRateTypeId + "/byaddress?address=" + address;
		String apiUrl1 = "";
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(LOCATION_INTELLIGENCE_API_URL1);
		Builder builder;
		boolean responseTypeIsXml = true;
		if (responseTypeIsXml) {
			builder = target.request(MediaType.APPLICATION_XML);
		} else {
			builder = target.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, accessToken);
		}
	
		
		String xml_data = builder.get(String.class);
		//converting xml to json
		org.json.JSONObject obj = XML.toJSONObject(xml_data);
		
		//System.out.println(obj.toString());
		org.json.JSONObject countries = (org.json.JSONObject) obj.get("countries");
		//System.out.println(countries.toString());
		JSONArray countryArray = countries.getJSONArray("country");
		for(int index=0;index<countryArray.length();index++) {
	//	System.out.println("Country Code "+countryArray.getJSONObject(index).get("code")+" Country Name "+countryArray.getJSONObject(index).get("content"));
			countryFromMap.putIfAbsent(countryArray.getJSONObject(index).get("content").toString(),countryArray.getJSONObject(index).get("code").toString());
		}
		System.out.println(countryFromMap.toString());
		return countryFromMap;
	}
	
	public static HashMap<String,String> getCountryMapTo() {
		//String apiUrl = "taxrate/" + taxRateTypeId + "/byaddress?address=" + address;
		String apiUrl1 = "";
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(LOCATION_INTELLIGENCE_API_URL2);
		Builder builder;
		boolean responseTypeIsXml = true;
		if (responseTypeIsXml) {
			builder = target.request(MediaType.APPLICATION_XML);
		} else {
			builder = target.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, accessToken);
		}
	
		
		String xml_data = builder.get(String.class);
		//converting xml to json
		org.json.JSONObject obj = XML.toJSONObject(xml_data);
		
		//System.out.println(obj.toString());
		org.json.JSONObject countries = (org.json.JSONObject) obj.get("countries");
		//System.out.println(countries.toString());
		JSONArray countryArray = countries.getJSONArray("country");
		for(int index=0;index<countryArray.length();index++) {
	//	System.out.println("Country Code "+countryArray.getJSONObject(index).get("code")+" Country Name "+countryArray.getJSONObject(index).get("content"));
			countryToMap.putIfAbsent(countryArray.getJSONObject(index).get("content").toString(),countryArray.getJSONObject(index).get("code").toString());
		}
		System.out.println(countryToMap.toString());
		return countryToMap;
	}
	
	
	/**
	 * @param args
	 */
	public static void main1(String[] args) {
		
		//Acquires OAuth2 token
		acquireAuthToken();

		//Gets particular General Tax Rate by Address in XML format
		getParticularTaxRateByAddress(true, "General", "4750%20Walnut%20st,%20Boulder,%20CO");
		
		//Gets particular General Tax Rate by Address in JSON format
		getParticularTaxRateByAddress(false, "General", "4750%20Walnut%20st,%20Boulder,%20CO");
		
		//Gets particular Auto Tax Rate by Address in XML format
		getParticularTaxRateByAddress(true, "Auto", "4750%20Walnut%20st,%20Boulder,%20CO");
				
		//Gets particular Auto Tax Rate by Address in JSON format
		getParticularTaxRateByAddress(false, "Auto", "4750%20Walnut%20st,%20Boulder,%20CO");
		
		//Gets particular General Tax Rate by Location in XML format
		getParticularTaxRateByLocation(true, "General", "40.018912", "-105.239771");
				
		//Gets particular General Tax Rate by Location in JSON format
		getParticularTaxRateByLocation(false, "General", "40.018912", "-105.239771");
		
		//Gets particular Auto Tax Rate by Location in XML format
		getParticularTaxRateByLocation(true, "Auto", "40.018912", "-105.239771");
						
		//Gets particular Auto Tax Rate by Location in JSON format
		getParticularTaxRateByLocation(false, "Auto", "40.018912", "-105.239771");
		
		//Calculates particular General Tax by Address in XML format for a given purchase amount
		calculateParticularTaxByAddress(true, "General", "4750%20Walnut%20st,%20Boulder,%20CO", "100");

		//Calculates particular General Tax by Address in JSON format for a given purchase amount
		calculateParticularTaxByAddress(false, "General", "4750%20Walnut%20st,%20Boulder,%20CO", "100");

		//Calculates particular Auto Tax by Address in XML format for a given purchase amount
		calculateParticularTaxByAddress(true, "Auto", "4750%20Walnut%20st,%20Boulder,%20CO", "100");

		//Calculates particular Auto Tax by Address in JSON format for a given purchase amount
		calculateParticularTaxByAddress(false, "Auto", "4750%20Walnut%20st,%20Boulder,%20CO", "100");

		//Calculates particular General Tax by Location in XML format for a given purchase amount
		calculateParticularTaxByLocation(true, "General", "40.018912", "-105.239771", "100");
		
		//Calculates particular General Tax by Location in JSON format for a given purchase amount
		calculateParticularTaxByLocation(false, "General", "40.018912", "-105.239771", "100");

		//Calculates particular Auto Tax by Location in XML format for a given purchase amount
		calculateParticularTaxByLocation(true, "Auto", "40.018912", "-105.239771", "100");
		
		//Calculates particular Auto Tax by Location in JSON format for a given purchase amount
		calculateParticularTaxByLocation(false, "Auto", "40.018912", "-105.239771", "100");
		
		// Gets getBatchTaxRateByLocation response in JSON format
		getBatchTaxRateByLocation(false, "General");

		// Gets getBatchTaxRateByLocation response in XML format
		getBatchTaxRateByLocation(true, "General");
		
		// Gets getBatchTaxRateByLocation response in JSON format Auto
		getBatchTaxRateByLocation(false, "Auto");

		// Gets getBatchTaxRateByLocation response in XML format Auto
		getBatchTaxRateByLocation(true, "Auto");

		// Gets calculateBatchTaxByLocation response in JSON format
		calculateBatchTaxByLocation(false, "General");
		
		// Gets calculateBatchTaxByLocation response in XML format 
		calculateBatchTaxByLocation(true, "General");
		
		// Gets calculateBatchTaxByLocation response in JSON format for Auto
		calculateBatchTaxByLocation(false, "Auto");
		
		// Gets calculateBatchTaxByLocation response in XML format for Auto
		calculateBatchTaxByLocation(true, "Auto");
		
		// Gets getBatchTaxRateByAddress response in JSON format
		getBatchTaxRateByAddress(false, "General");
		
		// Gets getBatchTaxRateByAddress response in XML format
		getBatchTaxRateByAddress(true, "General");
		
		// Gets getBatchTaxRateByAddress response in JSON format for Auto
		getBatchTaxRateByAddress(false, "Auto");
		
		// Gets getBatchTaxRateByAddress response in XML format for Auto
		getBatchTaxRateByAddress(true, "Auto");
		
		// Gets calculateBatchTaxByAddress response in JSON format
		calculateBatchTaxByAddress(false, "General");
		
		// Gets calculateBatchTaxByAddress response in XML format
		calculateBatchTaxByAddress(true, "General");
		
		// Gets calculateBatchTaxByAddress response in JSON format for Auto
		calculateBatchTaxByAddress(false, "Auto");
		
		// Gets calculateBatchTaxByAddress response in XML format for Auto
		calculateBatchTaxByAddress(true, "Auto");
		
		//Get IPD Tax By Address in XML format 
		getIPDTaxByAddress(true, "4750 Walnut st, Boulder, CO");
		
		//Get IPD Tax By Address in JSON format
		getIPDTaxByAddress(false, "4750 Walnut st, Boulder, CO");
		
		
		//Get IPD Tax By Address BATCH in XML format 
		getIPDTaxByAddressBatch(true);
		
		//Get IPD Tax By Address BATCH in JSON format
		getIPDTaxByAddressBatch(false);

	}
	
	private static void getIPDTaxByAddressBatch(boolean responseTypeIsXml){
		
		String apiUrl =  "taxdistrict/ipd/byaddress";
		try {
			processPOSTRequest(buildGeoTaxRequest(IPD_GEOTAX_BYADDRESS_REQUEST_SRC), responseTypeIsXml, apiUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void getIPDTaxByAddress(boolean responseTypeIsXml, String address){
		String apiUrl = null;
		try {
			apiUrl = "taxdistrict/ipd/byaddress?address=" + URLEncoder.encode(address,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		processRequest(responseTypeIsXml, apiUrl);
	}
	 
	
	/**
	 * Acquires OAuth2 token for accessing APIs
	 */
	private static void acquireAuthToken() {
		String authHeader = BASIC + Base64.encodeAsString(API_KEY + COLON + SECRET);
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(OAUTH2_TOKEN_URL);
	
		Builder builder = target.request().header(AUTH_HEADER, authHeader);
		Form form = new Form();
		form.param(GRANT_TYPE, CLIENT_CREDENTIALS);
		Response response = builder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
		String jsonResponse = response.readEntity(String.class);
		
		JsonReader jsonReader = Json.createReader(new StringReader(jsonResponse));
		JsonObject jsonObject = jsonReader.readObject();
		jsonReader.close();
		accessToken = jsonObject.getString(ACCESS_TOKEN);
		accessToken = BEARER + accessToken;
	}
	
	/**
	 * Generic client request executor that makes a rest call to APIs and prints response in XML or JSON formats.
	 * @param responseTypeIsXml
	 * @param apiUrl
	 */
	private static void processRequest(boolean responseTypeIsXml, String apiUrl) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(LOCATION_INTELLIGENCE_API_URL + apiUrl);
		Builder builder;
		if (responseTypeIsXml) {
			builder = target.request(MediaType.APPLICATION_XML).header(AUTH_HEADER, accessToken);
		} else {
			builder = target.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, accessToken);
		}
		System.out.println(builder.get(String.class));
	}

	private static void processPOSTRequest(Entity paramEntity, boolean responseTypeIsXml, String apiUrl) {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(LOCATION_INTELLIGENCE_API_URL + apiUrl);
		Builder builder;
		if (responseTypeIsXml) {
			builder = target.request(MediaType.APPLICATION_XML).header(AUTH_HEADER, accessToken);

		} else {
			builder = target.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, accessToken);
		}

		System.out.println(builder.post(paramEntity, String.class));

	}
	
	private static Entity buildGeoTaxRequest(String locationTaxGeotaxRequestSrc) throws IOException {

		String currentDir = System.getProperty(CURRENT_DIRECTORY);
		BufferedReader reader = new BufferedReader(new FileReader(currentDir + locationTaxGeotaxRequestSrc));
		String line = null;
		StringBuilder builder = new StringBuilder();
		String ls = System.getProperty(LINE_SEPERATOR);
		String jsonRequest = null;

		try {
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(ls);
			}

			jsonRequest = builder.toString();

			Entity paramEntity = Entity.entity(jsonRequest, MediaType.APPLICATION_JSON_TYPE);

			return paramEntity;
		} finally {
			reader.close();
		}
	}
	
	/**
	 * Returns particular tax rate by address in XML or JSON formats
	 * @param responseTypeIsXml
	 * @param taxRateTypeId			Type of tax, it can be "Auto" for Auto tax and "General" got General tax
	 * @param address
	 */
	private static void getParticularTaxRateByAddress(boolean responseTypeIsXml, String taxRateTypeId, String address) {
		String apiUrl = "taxrate/" + taxRateTypeId + "/byaddress?address=" + address;
		processRequest(responseTypeIsXml, apiUrl);
	}
	
	/**
	 * Returns particular tax rate by location in XML or JSON formats
	 * @param responseTypeIsXml
	 * @param taxRateTypeId			Type of tax, it can be "Auto" for Auto tax and "General" got General tax
	 * @param latitude
	 * @param longitude
	 */
	private static void getParticularTaxRateByLocation(boolean responseTypeIsXml, String taxRateTypeId, String latitude, String longitude) {
		String apiUrl = "taxrate/" + taxRateTypeId + "/bylocation?latitude=" + latitude + "&longitude=" + longitude;
		processRequest(responseTypeIsXml, apiUrl);
	}
	
	/**
	 * Returns particular tax by address for a purchase amount in XML or JSON formats
	 * @param responseTypeIsXml
	 * @param taxRateTypeId			Type of tax, it can be "Auto" for Auto tax and "General" got General tax
	 * @param address
	 * @param purchaseAmount
	 */
	private static void calculateParticularTaxByAddress(boolean responseTypeIsXml, String taxRateTypeId, String address, String purchaseAmount) {
		String apiUrl = "tax/" + taxRateTypeId + "/byaddress?address=" + address + "&purchaseAmount=" + purchaseAmount;
		processRequest(responseTypeIsXml, apiUrl);
	}
	
	/**
	 * Returns particular tax by location for a purchase amount in XML or JSON formats
	 * @param responseTypeIsXml
	 * @param taxRateTypeId			Type of tax, it can be "Auto" for Auto tax and "General" got General tax
	 * @param latitude
	 * @param longitude
	 * @param purchaseAmount
	 */
	private static void calculateParticularTaxByLocation(boolean responseTypeIsXml, String taxRateTypeId, String latitude, String longitude, String purchaseAmount) {
		String apiUrl = "tax/" + taxRateTypeId + "/bylocation?latitude=" + latitude + "&longitude=" + longitude + "&purchaseAmount=" + purchaseAmount;
		processRequest(responseTypeIsXml, apiUrl);
	}
	
	private static void getBatchTaxRateByAddress(boolean responseTypeIsXml, String taxRateTypeId) {
		String apiUrl = TAXRATE_URL + taxRateTypeId + BYADDRESS;
		try {
			processPOSTRequest(buildGeoTaxRequest(ADDRESS_TAXRATE_GEOTAX_REQUEST_SRC), responseTypeIsXml, apiUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void calculateBatchTaxByAddress(boolean responseTypeIsXml, String taxRateTypeId) {
		String apiUrl = TAX_URL + taxRateTypeId + BYADDRESS;
		try {
			processPOSTRequest(buildGeoTaxRequest(ADDRESS_TAX_GEOTAX_REQUEST_SRC), responseTypeIsXml, apiUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void calculateBatchTaxByLocation(boolean responseTypeIsXml, String taxRateTypeId) {
		String apiUrl = TAX_URL + taxRateTypeId + BYLOCATION;
		try {
			processPOSTRequest(buildGeoTaxRequest(LOCATION_TAX_GEOTAX_REQUEST_SRC), responseTypeIsXml, apiUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void getBatchTaxRateByLocation(boolean responseTypeIsXml, String taxRateTypeId) {
		String apiUrl = TAXRATE_URL + taxRateTypeId + BYLOCATION;
		try {
			processPOSTRequest(buildGeoTaxRequest(LOCATION_TAXRATE_GEOTAX_REQUEST_SRC), responseTypeIsXml, apiUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
