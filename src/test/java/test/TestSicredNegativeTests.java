package test;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.qameta.allure.Description;
import io.restassured.RestAssured;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestSicredNegativeTests extends BaseTest
{
private JSONObject jsonObject = new JSONObject();
private static String expiredToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTUsInVzZXJuYW1lIjoia21pbmNoZWxsZSIsImVtYWlsIjoia21pbmNoZWxsZUBxcS5jb20iLCJmaXJzdE5hbWUiOiJKZWFubmUiLCJsYXN0TmFtZSI6IkhhbHZvcnNvbiIsImdlbmRlciI6ImZlbWFsZSIsImltYWdlIjoiaHR0cHM6Ly9yb2JvaGFzaC5vcmcvYXV0cXVpYXV0LnBuZyIsImlhdCI6MTY4OTM4NDA0MiwiZXhwIjoxNjg5Mzg3NjQyfQ.-CUAeu1D1_rjJBiPRg273pEg9jGdoSSCOTrLcwq2O_U";

@Test
@Description("Test Decription: Consultar usuário inexistente")
public void t01_shoulNotFoundUser() {
 
	int idUser = 1001;
		
		RestAssured.given()
		.when()
		.get("users/"+idUser)
		.then()
		.log().all()
		.statusCode(404)
		.body("message", Matchers.is("User with id '"+idUser+"' not found"))
		;
		
		
	}
@Test
@Description("Test Decription: Efetuar Login com Credenciais inválidas")
public void t02_shouldLoginInvalidCredentials() {
	
	jsonObject.put("username", "kminchelle");
	jsonObject.put("password","0lelplRs");

	RestAssured.given().headers("Content-Type","application/json")
	.and()
	.body(jsonObject.toString())
	.log().all()
	.post("/auth/login")
	.then()
	.log().all()
	.statusCode(400)
	.body("message", Matchers.is("Invalid credentials"))
	   
	
	;
	
	
}


@Test
@Description("Test Decription: Efetuar Login com recurso inválido")
public void t03_shouldLoginWithWrongResource() {
	
	jsonObject.put("username", "kminchelle");
	jsonObject.put("password","0lelplR");

	
	RestAssured.given().headers("Content-Type","application/json")
	.and()
	.body(jsonObject.toString())
	.log().all()
	.post("/auth/logi")
	.then()
	.log().all()
	.statusCode(403)
	.body("message", Matchers.is("Authentication Problem"))
	   
	
	;
	
	
}

@Test
@Description("Test Decription: Efetuar login com token expirado")
public void t04_shouldNotLoginWithExpiredToken() {
	
			   RestAssured.given()
			   .headers("Authorization","Bearer "+expiredToken)
			   .when()
			   .get("/auth/login")
			   .then()
			   .log().all()
			   .statusCode(401)
			   .body("message", Matchers.is("Token Expired!"))
			   
			   
			   ;
	
}

@Test
@Description("Test Decription: Consultar Produtos sem informar autorização")
public void t05_shouldGetProductsWithoutAuthorization() {
	
	RestAssured.given()
			   .when()
			   .log().all()
			   .get("/auth/products")
			   .then()
			   .statusCode(403)
			   .body("message", Matchers.is("Authentication Problem"))
			   .log().all();
			
	
}

@Test
@Description("Test Decription: Consultar Produtos com token inválido")
public void t06_shouldGetProductsExpiredToken() {
	
			RestAssured.given()
			   .headers("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTUsInVzZXJuYW1lIjoia21pbmNoZWxsZSIsImVtYWlsIjoia21pbmNoZWxsZUBxcS5jb20iLCJmaXJzdE5hbWUiOiJKZWFubmUiLCJsYXN0TmFtZSI6IkhhbHZvcnNvbiIsImdlbmRlciI6ImZlbWFsZSIsImltYWdlIjoiaHR0cHM6Ly9yb2JvaGFzaC5vcmcvYXV0cXVpYXV0LnBuZyIsImlhdCI6MTY4OTM4NDA0MiwiZXhwIjoxNjg5Mzg3NjQyfQ.-CUAeu1D1_rjJBiPRg273pEg9jGdoSSCOTrLcwq2O_U")
			   .when()
			   .log().all()
			   .get("/auth/products")
			   .then()
			   .statusCode(401)
			   .body("name", Matchers.is("JsonWebTokenError"))
			   .body("message", Matchers.is("Invalid/Expired Token!"))
			   .log().all()
			   ;
}

@Ignore
@Test
public void t07_shouldAddProduct() {
	
	jsonObject.put("title", "Perfume Oil");
	jsonObject.put("description","Mega Discount, Impression of A...");
	jsonObject.put("price",13);
	jsonObject.put("discountPercentage",8.4);
	jsonObject.put("rating",4.26);
	jsonObject.put("stock",100);
	jsonObject.put("brand","Impression of Acqua Di Gio");
	jsonObject.put("category","fragrances");
	jsonObject.put("thumbnail","https://i.dummyjson.com/data/products/11/thumnail.jpg");
	
	
			RestAssured.given().headers("Content-Type","application/json")
			.when().log().all()
			.body(jsonObject.toString())
			.post("/products/add")
			.then()
			.statusCode(200)
			.log().all()
			
			;
}

@Ignore
@Test
public void t08_shouldGetProducts() {
	
			RestAssured.given()
			.when().log().all()
			.get("/products")
			.then()
			.statusCode(200)
			.log().all()
			;
}

@Test
@Description("Test Decription: Consultar produto inexistente")
public void t09_shouldGetProductsNotFound() {
	
	int idProduct = 999;
	
	RestAssured.given()
			.when().log().all()
			.get("/products/"+idProduct)
			.then()
			.log().all()
			   .statusCode(404)
			   .body("message", Matchers.is("Product with id '"+idProduct+"' not found"))
			   
			
			;
}

}
