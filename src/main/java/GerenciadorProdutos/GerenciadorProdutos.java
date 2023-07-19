package GerenciadorProdutos;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GerenciadorProdutos {
	
private String username;
private String password;
private JSONObject jsonObject = new JSONObject();



public GerenciadorProdutos(String username, String password) {
	super();
	this.username = username;
	this.password = password;
}


public String getToken() {
		
		String token = null;
		

		jsonObject.put("username", "kminchelle");
		jsonObject.put("password","0lelplR");

		
		Response tokenResponse = RestAssured.given().headers("Content-Type","application/json")
		.and()
		.body(jsonObject.toString())
		.log().all()
		.post("/auth/login")
		.then()
		.log().all()
		.statusCode(200)
		.extract()
		.response();
		
		token = tokenResponse.jsonPath().getString("token");
		
		System.out.println("TOKEN => "+token);
		
		return token;
		
	}


public String getUsername() {
	return username;
}





public void setUsername(String username) {
	this.username = username;
}





public String getPassword() {
	return password;
}





public void setPassword(String password) {
	this.password = password;
}

	

}
