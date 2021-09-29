import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import files.payload;
import io.restassured.RestAssured;

public class StaticJson {
	
	@Test
	public void ReadJsonFile() throws IOException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = 
				given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(new String(Files.readAllBytes(Paths.get("C:\\Arijit\\JSON\\addPlace.json"))))
				.when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		System.out.println(response);
	}
	
}
