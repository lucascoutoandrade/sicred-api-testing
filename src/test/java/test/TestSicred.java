package test;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import GerenciadorProdutos.GerenciadorProdutos;
import io.qameta.allure.Description;
import io.restassured.RestAssured;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestSicred extends BaseTest {
	
private JSONObject jsonObject = new JSONObject();
private GerenciadorProdutos login = new GerenciadorProdutos("kminchelle","0lelplR") ;

@Test
@Description("Test Decription: Obter usuário by ID")
public void t01_shouldGetUsers() {
	
	int idUser = 2;

		RestAssured.given()
		.spec(defaultRequestSpecification())
		.when()
		.get("users/"+idUser)
		.then()
		.spec(defaultResponseSpecification())
		.statusCode(200)
		.body(
				"id", Matchers.instanceOf(Integer.class),
				"id", Matchers.not(Matchers.emptyOrNullString()),
				"id", Matchers.is(idUser))
	
		;
		
	}

@Test
@Description("Test Decription: Efetuar Login")
public void t02_shouldLogin() {
	
	//Usando Object Mapping Serialization
	RestAssured.given()
	.spec(defaultRequestSpecification())
	.body(login)
	.post("/auth/login")
	.then()
	.spec(defaultResponseSpecification())
	.statusCode(201)
	
	;
		
}
@Test
@Description("Test Decription: Obter Produtos com autorização")
public void t03_shouldGetProductsWithAuth() {
	
			   RestAssured.given()
			   .headers("Authorization","Bearer "+login.getToken()+"")
			   .spec(defaultRequestSpecification())
			   .when()
			   .get("/auth/products")
			   .then()
			   .spec(defaultResponseSpecification())
			   .statusCode(200)
			   
			   ;
	
}
@Test
@Description("Test Decription: Adicionar Produtos")
public void t04_shouldAddProduct() {
	
	//Usando JsonObject
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
			.spec(defaultRequestSpecification())
			.when()
			.body(jsonObject.toString())
			.post("/products/add")
			.then()
			.spec(defaultResponseSpecification())
			.statusCode(200)
			
			
			;		
}
@Test
@Description("Test Decription: Consultar Produtos")
public void t05_shouldGetProducts() {
	
			RestAssured.given()
			.when()
			.spec(defaultRequestSpecification())
			.get("/products")
			.then()
			.spec(defaultResponseSpecification())
			.statusCode(200)
		
			;

}
@Test
@Description("Test Decription: Consultar produtos por ID")
public void t06_shouldGetProductsByID() {
	
            RestAssured.given()
        	.spec(defaultRequestSpecification())
			.when()
			.get("/products/85")
			.then()
			.spec(defaultResponseSpecification())
			.statusCode(200)
		
			;
}

}
