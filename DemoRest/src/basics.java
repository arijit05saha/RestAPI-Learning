import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Rest Assured work on the below method...
		// given - all the input details 
		// when - submit the API (resource and http method)
		// then - validate the response
		
		
		//ADD place; UPDATE Place; GET Place
		
		//validate if ADD place is working
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = 
				given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(payload.AddPlace())
				.when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		System.out.println(response);
		
		// parse json string
		JsonPath js = new JsonPath(response);
		String placeID = js.getString("place_id");
		System.out.println(placeID);
		
		String newAddress = "Summer Walk, Africa";
		
		//validate if UPDATE place is working
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//validate if GET place is working
		String getPlaceRes = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		String actualAddress = ReUsableMethods.rawToJson(getPlaceRes).getString("address");
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, newAddress);
		
	}

}
