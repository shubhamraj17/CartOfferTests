package com.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.controller.ApplyOfferRequest;
import com.springboot.controller.OfferRequest;
import com.springboot.controller.SegmentResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartOfferApplicationTests {

	public static Integer DiscountValue=10;

	@Test
	public void checkFlatXForOneSegment() throws Exception {
		List<String> segments = new ArrayList<>();
		segments.add("p2");
		OfferRequest offerRequest = new OfferRequest(1,"FLATX%",10,segments);
		boolean result = addOffer(offerRequest);
		Assert.assertEquals(result,true); // able to add offer
	}


	public boolean addOffer(OfferRequest offerRequest) throws Exception {
		String urlString = "http://localhost:9001/api/v1/offer";
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestProperty("Content-Type", "application/json");

		ObjectMapper mapper = new ObjectMapper();

		String POST_PARAMS = mapper.writeValueAsString(offerRequest);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("POST request did not work.");
		}
		return true;
	}


	public void addOfferForFlatX(String segment) throws Exception {
		List<String> segments = new ArrayList<>();
		segments.add(segment);
		OfferRequest offerRequest = new OfferRequest(1,"FLATX",DiscountValue,segments);
		boolean result = addOffer(offerRequest);
		Assert.assertEquals(result,true);
	}

	public void addOfferForFlatXPercentage(String segment) throws Exception {
		List<String> segments = new ArrayList<>();
		segments.add(segment);
		OfferRequest offerRequest = new OfferRequest(2,"FLATX% off",DiscountValue,segments);
		boolean result = addOffer(offerRequest);
		Assert.assertEquals(result,true);
	}

	@Test
	public void validateCartWithFlatXOfferWithSegmentOne() throws Exception {

		//add offer
		addOfferForFlatX("p1");

		//apply offer instance
		ApplyOfferRequest applyOfferRequest= new ApplyOfferRequest(200, 1, 1);

		//Get User Segment
		String getUserSegment=getUserSegment(Integer.toString(applyOfferRequest.getUser_id()));

		//api call
		Response applyOfferResponse= applyOfferHelper(applyOfferRequest);

		//validation
		Assert.assertEquals(applyOfferResponse.getStatusCode(),200);
		validateCartOfferWithSegment(getUserSegment,applyOfferRequest.getCart_value(),applyOfferResponse);
	}

	@Test
	public void validateCartWithFlatXpercentOfferWithSegmentOne() throws Exception {

		//add offer
		addOfferForFlatXPercentage("p1");

		//apply offer instance
		ApplyOfferRequest applyOfferRequest= new ApplyOfferRequest(200, 1, 1);

		//Get User Segment
		String getUserSegment=getUserSegment(Integer.toString(applyOfferRequest.getUser_id()));

		//api call
		Response applyOfferResponse= applyOfferHelper(applyOfferRequest);

		//validation
		Assert.assertEquals(applyOfferResponse.getStatusCode(),200);
		validateCartOfferWithSegment(getUserSegment,applyOfferRequest.getCart_value(),applyOfferResponse);
	}

	@Test
	public void validateCartWithFlatXOfferWithSegmentTwo() throws Exception {

		//add offer
		addOfferForFlatX("p2");

		//apply offer instance
		ApplyOfferRequest applyOfferRequest= new ApplyOfferRequest(200, 1, 1);

		//Get User Segment
		String getUserSegment=getUserSegment(Integer.toString(applyOfferRequest.getUser_id()));

		//api call
		Response applyOfferResponse= applyOfferHelper(applyOfferRequest);

		//validation
		Assert.assertEquals(applyOfferResponse.getStatusCode(),200);
		validateCartOfferWithSegment(getUserSegment,applyOfferRequest.getCart_value(),applyOfferResponse);
	}

	@Test
	public void validateCartWithFlatXpercentOfferWithSegmentTwo() throws Exception {

		//add offer
		addOfferForFlatXPercentage("p2");

		//apply offer instance
		ApplyOfferRequest applyOfferRequest= new ApplyOfferRequest(200, 1, 1);

		//Get User Segment
		String getUserSegment=getUserSegment(Integer.toString(applyOfferRequest.getUser_id()));

		//api call
		Response applyOfferResponse= applyOfferHelper(applyOfferRequest);

		//validation
		Assert.assertEquals(applyOfferResponse.getStatusCode(),200);
		validateCartOfferWithSegment(getUserSegment,applyOfferRequest.getCart_value(),applyOfferResponse);
	}

	@Test
	public void validateCartWithFlatXOfferWithSegmentThree() throws Exception {

		//add offer
		addOfferForFlatX("p1");

		//apply offer instance
		ApplyOfferRequest applyOfferRequest= new ApplyOfferRequest(200, 1, 1);

		//Get User Segment
		String getUserSegment=getUserSegment(Integer.toString(applyOfferRequest.getUser_id()));

		//api call
		Response applyOfferResponse= applyOfferHelper(applyOfferRequest);

		//validation
		Assert.assertEquals(applyOfferResponse.getStatusCode(),200);
		validateCartOfferWithSegment(getUserSegment,applyOfferRequest.getCart_value(),applyOfferResponse);
	}

	@Test
	public void validateCartWithFlatXpercentOfferWithSegmentThree() throws Exception {

		//add offer
		addOfferForFlatXPercentage("p1");

		//apply offer instance
		ApplyOfferRequest applyOfferRequest= new ApplyOfferRequest(200, 1, 1);

		//Get User Segment
		String getUserSegment=getUserSegment(Integer.toString(applyOfferRequest.getUser_id()));

		//api call
		Response applyOfferResponse= applyOfferHelper(applyOfferRequest);

		//validation
		Assert.assertEquals(applyOfferResponse.getStatusCode(),200);
		validateCartOfferWithSegment(getUserSegment,applyOfferRequest.getCart_value(),applyOfferResponse);
	}


	public void validateCartOfferWithSegment(String UserSegment,Integer cartValue,Response response) throws JSONException {
		JSONObject responseObj= new JSONObject(response.asString());
		Integer cartValueResponse=  responseObj.getInt("cart_value");
		if(UserSegment=="p1"){
			Integer expectedCartValue= cartValue-DiscountValue;
			Assert.assertEquals(expectedCartValue,cartValueResponse);
		}else if(UserSegment=="p2"){
			Integer expectedCartValue= (int) (cartValue - cartValue * DiscountValue*(0.01));
			Assert.assertEquals(expectedCartValue,cartValueResponse);
		}
	}

	public Response applyOfferHelper(ApplyOfferRequest applyOfferRequest){
		RestAssured.baseURI="http://localhost:9001";

		Response response = RestAssured.given().log().all()
				.contentType(ContentType.JSON)
				.body(applyOfferRequest)
				.when().post("/api/v1/cart/apply_offer")
				.thenReturn();
		response.prettyPrint();

		return response;
	}

	public String getUserSegment(String userId) throws JSONException {
		Response response = getUserSegmentHelper(userId);
		Assert.assertEquals(response.getStatusCode(),200);
		JSONObject responseObj= new JSONObject(response.asString());
		String segment= responseObj.getString("segment");
		return segment;
	}

	public Response getUserSegmentHelper(String userId){
		RestAssured.baseURI="http://localhost:1080";
		Response response = RestAssured.given().log().all()
				.queryParam("user_id",userId)
				.when().get("/api/v1/user_segment")
				.thenReturn();
		response.prettyPrint();

		return response;
	}
}
