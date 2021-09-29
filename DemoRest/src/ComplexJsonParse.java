import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		//print number of courses
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//Print Title of the first course
		String title_1 = js.getString("courses[0].title");
		System.out.println(title_1);
		
		//Print All course titles and their respective Prices
		for (int i = 0; i < count; i++){
			String title = js.getString("courses["+i+"].title");
			String price = js.getString("courses["+i+"].price");
			System.out.println(title + " : " + price);
		}
		
		//Print no of copies sold by RPA Course
		for (int i = 0; i < count; i++){
			String title = js.getString("courses["+i+"].title");
			if (title.equalsIgnoreCase("rpa")) {
				String copies = js.getString("courses["+i+"].copies");
				System.out.println(title + " : " + copies);
				break;
			}			
		}
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		int sum = 0;
		for (int i = 0; i < count; i++){
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			sum = sum + (price * copies);			
		}
		if (totalAmount == sum) {
			System.out.println("Total amount matches");
		}
		else {
			System.out.println("Total amount does not match!");
		}
	}	

}
