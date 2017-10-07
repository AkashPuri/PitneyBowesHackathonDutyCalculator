package com.pb.services;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.util.Base64;
import org.json.XML;
import org.json.simple.JSONObject;

/**
 * Code sample for accessing GeoTax API. This program demonstrates the capability of GeoTax API 
 * to get particular tax rates for tax rate type (General or Auto) by address or location in XML and JSON response formats.
 * To make it work, make sure to update your API Key and SECRET before running this code. 
 */
public class DutyCalculator {
	
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

	private static final String LOCATION_TAX_GEOTAX_REQUEST_SRC = "\\src\\main\\resources\\geotaxTaxBatchByLocation.json";

	private static final String LOCATION_TAXRATE_GEOTAX_REQUEST_SRC = "\\src\\main\\resources\\geoTaxRateBatchByLocation.json";
	
	private static final String ADDRESS_TAX_GEOTAX_REQUEST_SRC = "\\src\\main\\resources\\geoTaxBatchByAddress.json";

	private static final String ADDRESS_TAXRATE_GEOTAX_REQUEST_SRC = "\\src\\main\\resources\\geoTaxRateBatchByAddress.json";
	
	private static final String IPD_GEOTAX_BYADDRESS_REQUEST_SRC = "\\src\\main\\resources\\ipdGeoTaxByLocationBatchRequest.json";

	private static String accessToken;
	
	public static void main(String[] args) { 
		getDutiesAndTaxes();
	}
	
private static final String API_FRAGMENT1 = "geotax/v1/";
	
	private static String LOCATION_INTELLIGENCE_API_URL1 = "http://www.dutycalculator.com/api2.1/2bac6dba1354599a/supported-countries/from?display_alpha2_code=1";
	
	private static String LOCATION_INTELLIGENCE_API_URL2 = "http://www.dutycalculator.com/api2.1/2bac6dba1354599a/calculation?from=usa&to=nld&classify_by=cat&cat[0]=25&reference[0]=product-1&desc[0]=Nike+Air+Jordans&qty[0]=1&value[0]=29.95&shipping=6.25&insurance=1.30&currency=usd";
	
	private static String LOCATION_INTELLIGENCE_API_URL3 = "http://www.dutycalculator.com/api2.1/2bac6dba1354599a/calculation?from=usa&to=in&classify_by=cat&cat[0]=25&desc[0]=Nike+Air+Jordans&qty[0]=1&value[0]=29.95&cat[1]=1578&desc[1]=Apple+iPhone+3G&qty[1]=2&value[1]=400&shipping=6.25&insurance=1.30&currency=eur"; 
	
	/**
	 * Returns particular tax rate by address in XML or JSON formats
	 * @param responseTypeIsXml
	 * @param taxRateTypeId			Type of tax, it can be "Auto" for Auto tax and "General" got General tax
	 * @param address
	 */
	
	public static Map<String,String> getDutiesAndTaxes() {
		//String apiUrl = "taxrate/" + taxRateTypeId + "/byaddress?address=" + address;
		String apiUrl1 = "";
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(LOCATION_INTELLIGENCE_API_URL3);
		Builder builder;
		boolean responseTypeIsXml = true;
		if (responseTypeIsXml) {
			builder = target.request(MediaType.APPLICATION_XML);
		} else {
			builder = target.request(MediaType.APPLICATION_JSON).header(AUTH_HEADER, accessToken);
		};
                String xmlString = builder.get(String.class);
                System.out.println(xmlString);
                org.json.JSONObject obj = XML.toJSONObject(xmlString);
		//System.out.println(obj.toString());
		org.json.JSONObject duty = (org.json.JSONObject) obj.get("duty-calculation");
		//System.out.println(duty.toString());
		org.json.JSONObject total = (org.json.JSONObject) duty.get("total-charges");
		//System.out.println(total.toMap());
		
		System.out.println("\n\n\n");
	//	System.out.println(total.get("amount"));
		System.out.println(total.get("customs-value"));
		
		org.json.JSONObject customValues = (org.json.JSONObject) total.get("customs-value");
		System.out.println(customValues.toMap());
			org.json.JSONObject customeValuesAmount = (org.json.JSONObject) customValues.get("amount");
			 Object customeValuesName = customValues.get("name");
		System.out.println(customeValuesName+" "+customeValuesAmount.get("content"));
		
		Map<String, String> amountMap = new HashMap<String,String>();
		amountMap.put("amount",(String) customeValuesAmount.get("content").toString());
		
		System.out.println(total.get("duty"));
		org.json.JSONObject dutyValuesAmount = (org.json.JSONObject) total.get("duty");
		org.json.JSONObject dutyValuesName = (org.json.JSONObject) dutyValuesAmount.get("amount");
		amountMap.put("duty", dutyValuesName.get("content").toString());
		System.out.println(dutyValuesName.get("content").toString());
		
		System.out.println(total.get("sales-tax"));
		org.json.JSONObject salesTaxValuesAmount = (org.json.JSONObject) total.get("sales-tax");
		org.json.JSONObject salesTaxValuesName = (org.json.JSONObject) salesTaxValuesAmount.get("amount");
		amountMap.put("salesTax", salesTaxValuesName.get("content").toString());
		System.out.println(salesTaxValuesName.get("content").toString());
		
		System.out.println(total.get("total"));
		org.json.JSONObject totalTaxValuesAmount = (org.json.JSONObject) total.get("total");
		org.json.JSONObject totalTaxValuesName = (org.json.JSONObject) totalTaxValuesAmount.get("amount");
		amountMap.put("totalTax", totalTaxValuesName.get("content").toString());
		System.out.println(totalTaxValuesName.get("content").toString());
		//System.out.println(total.get("additional-import-taxes"));
		return amountMap;
	}
	
	/**
	 * @param args
	 */
	
	
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
