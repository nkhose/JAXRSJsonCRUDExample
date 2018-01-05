package org.nikita.restdemo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredDemo {

	public static void main(String[] args) {
		
		List <Countries> countryList=new ArrayList();
		
		Response resp=RestAssured.get("http://localhost:8080/JAXRSJsonCRUDExample/rest/countries");
		String body=resp.asString();
		
		//System.out.println(body);
		
		JSONArray jsonArray=new JSONArray(body);
		
		for (Object object : jsonArray) {
			
			Countries country=new Countries();
			JSONObject jsonObject=new JSONObject(object.toString());
			
			//System.out.println(jsonObject);
			
			
			country.setId(jsonObject.getInt("id"));
			country.setCountryName(jsonObject.getString("countryName"));
			country.setPopulation(jsonObject.getLong("population"));
			
			countryList.add(country);		

	}
		System.out.println(countryList);
		
	}

}

class Countries{
	
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
