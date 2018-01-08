package org.nikita.restdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredDemo {

	public static void main(String[] args) throws IOException {
		 consumeWebServiceByNetUrlGet();
		 consumeWebServiceByNetUrlPost();

		 consumeWebServiceByRestAssuredGet();
	}

	private static void consumeWebServiceByNetUrlGet() throws IOException {
		// TODO Auto-generated method stub

		List<Countries> countryList = new ArrayList<Countries>();
		URL url = new URL("http://localhost:8080/JAXRSJsonCRUDExample/rest/countries");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String output;
		StringBuffer response = new StringBuffer();

		while ((output = br.readLine()) != null) {
			response.append(output);

			String str = new String(response);
			JSONArray jsonArray = new JSONArray(str);

			for (Object object : jsonArray) {
				Countries countries = new Countries();
				JSONObject jsonObject = new JSONObject(object.toString());

				countries.setId(jsonObject.getInt("id"));
				countries.setCountryName(jsonObject.getString("countryName"));
				countries.setPopulation(jsonObject.getLong("population"));

				countryList.add(countries);
			}
			System.out.println("Consume WebService By NetUrl........\n");
			System.out.println(countryList);
		}

		// conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		/*
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader(conn.getInputStream()));
		 * 
		 * String output;
		 * 
		 * System.out.println("Output from server.........\n");
		 * 
		 * while((output=br.readLine())!=null){ System.out.println(output);
		 * System.out.println("\n "); }
		 */

		// conn.disconnect();
	}

	private static void consumeWebServiceByNetUrlPost() throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL("http://localhost:8080/JAXRSJsonCRUDExample/rest/countries");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		//String input = "{\"population\":150000,\"countryName\":\"Shrilanka\",\"id\":7}";

		JSONObject jsonObject =new JSONObject();
		jsonObject.put("id", 7);
		jsonObject.put("countryName", "Shrilanka");
		jsonObject.put("population", 250000);
		
		OutputStream os = conn.getOutputStream();
		os.write(jsonObject.toString().getBytes());
		os.flush();
		
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		
		String output;
		System.out.println("Consume WebService By NetUrl Post........\n");
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();
/*
		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();*/
	}

	private static void consumeWebServiceByRestAssuredGet() {
		// TODO Auto-generated method stub
		List<Countries> countryList = new ArrayList();

		Response resp = RestAssured.get("http://localhost:8080/JAXRSJsonCRUDExample/rest/countries");
		String body = resp.asString();

		// System.out.println(body);

		JSONArray jsonArray = new JSONArray(body);

		for (Object object : jsonArray) {

			Countries country = new Countries();
			JSONObject jsonObject = new JSONObject(object.toString());

			// System.out.println(jsonObject);

			country.setId(jsonObject.getInt("id"));
			country.setCountryName(jsonObject.getString("countryName"));
			country.setPopulation(jsonObject.getLong("population"));

			countryList.add(country);

		}
		System.out.println("\nConsume WebService By RestAssured......\n");
		System.out.println(countryList);

	}
}

class Countries {

	private int id;
	private String countryName;
	private long population;

	@Override
	public String toString() {
		return "\n [id=" + id + ", countryName=" + countryName + ", population=" + population + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

}
