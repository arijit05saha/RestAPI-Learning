import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class DynamicJson {
	
//	@Test()
//	public void addBook() {
	@Test(dataProvider = "BookData")
	public void addBook(String name, String isbn, String aisle, String author) {

		
		RestAssured.baseURI = "http://216.10.245.166";
		
		//add book
		//String response = given().log().all().header("Content-Type", "application/json").body(payload.AddBook("Learn REST Assured with Java", "asaa", "1690", "Arijit Saha"))
		String response = given().log().all().header("Content-Type", "application/json").body(payload.AddBook(name, isbn, aisle, author))
		.when().post("/Library/Addbook.php")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		String Msg = ReUsableMethods.rawToJson(response).getString("Msg");
		String id = ReUsableMethods.rawToJson(response).getString("ID");
		Assert.assertEquals(Msg, "successfully added");
		
//		//get book
//		String getResponse = given().log().all()
//		.when().get("/Library/GetBook.php?AuthorName=Arijit Saha")
//		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		//delete book
		String delResponse = given().log().all().header("Content-Type", "application/json").body(payload.DelBook(id))
		.when().post("/Library/DeleteBook.php")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		String delMsg = ReUsableMethods.rawToJson(delResponse).getString("msg");
		Assert.assertEquals(delMsg, "book is successfully deleted");
	}
	
	
	
	@DataProvider(name="BookData")
	public Object[][] getData() {
		
		return new Object [][] {
			{"Learn REST Assured with Java", "asaa", "1690", "Arijit Saha"}, 
			{"Practice REST Assured with Java", "asbb", "1691", "Arijit Saha"},
			{"Exam REST Assured with Java", "defg", "1692", "Arijit Saha"}
			};

	}

}
