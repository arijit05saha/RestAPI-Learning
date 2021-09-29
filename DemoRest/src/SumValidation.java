import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void SumValidation() {
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		int count = js.getInt("courses.size()");
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		int sum = 0;
		for (int i = 0; i < count; i++){
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			sum = sum + (price * copies);			
		}
		Assert.assertEquals(sum, totalAmount);
	}
}
