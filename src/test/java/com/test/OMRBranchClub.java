package com.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.base.BaseClass;
import com.endpoints.EndPoints;
import com.pojo.AddAddress_Input_pojo;
import com.pojo.AddAddress_Output_pojo;
import com.pojo.ChangeProfilePic_Output_Pojo;
import com.pojo.Data;
import com.pojo.DeleteAddress_Input_pojo;
import com.pojo.DeleteAddress_Output_pojo;
import com.pojo.GetAddresses_Output_pojo;
import com.pojo.Login_Output_pojo;
import com.pojo.UpdateAddress_Output_pojo;
import com.pojo.UpdateAddress_input_pojo;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class OMRBranchClub extends BaseClass {

	String logtoken;
	int address_id2;

	@Test(priority = 1)
	public void login() throws FileNotFoundException, IOException {

		addHeader("content-Type", "application/json");

		basicAuth(getPropertyValue("userName"), getPropertyValue("password"));

		Response response = requestType("POST", EndPoints.LOGIN);

		int statuscode = getStatuscode(response);
		System.out.println(statuscode);
		org.testng.Assert.assertEquals(statuscode, 200, "verify status code");

		Login_Output_pojo login_Output_pojo = response.as(Login_Output_pojo.class);
		String message = login_Output_pojo.getMessage();
        org.testng.Assert.assertEquals(message, "Login successfully","verify login");
        
		logtoken = login_Output_pojo.getData().getLogtoken();

	}

	@Test(priority = 2)
	public void CreateAddress() {
		List<Header> h = new ArrayList<Header>();
		Header h1 = new Header("content-Type", "application/json");

		Header h2 = new Header("Authorization", "Bearer " + logtoken);
		h.add(h1);
		h.add(h2);
		Headers headers = new Headers(h);
		addHeaders(headers);

		AddAddress_Input_pojo addAddress_Input_pojo = new AddAddress_Input_pojo("Sowndarya", "Jeganathan", "9629559254",
				"shanti sadan", 56, 32, 103, "625016", "Kochadai", "Home");

		addBody(addAddress_Input_pojo);

		Response response = requestType("POST", EndPoints.ADDADDRESS);
		int statuscode = getStatuscode(response);
		System.out.println(statuscode);

		AddAddress_Output_pojo addAddress_Output_pojo = response.as(AddAddress_Output_pojo.class);
		String message = addAddress_Output_pojo.getMessage();
		address_id2 = addAddress_Output_pojo.getAddress_id();
		org.testng.Assert.assertEquals(statuscode, 200, "verify status code");

		org.testng.Assert.assertEquals(message, "Address added successfully", "verify address created");

	}

	@Test(priority = 3)
	public void UpdateAddress() {
		List<Header> h = new ArrayList<Header>();
		Header h1 = new Header("content-Type", "application/json");

		Header h2 = new Header("Authorization", "Bearer " + logtoken);
		h.add(h1);
		h.add(h2);
		Headers headers = new Headers(h);
		addHeaders(headers);

		UpdateAddress_input_pojo updateAddress_input_pojo = new UpdateAddress_input_pojo(String.valueOf(address_id2),
				"sowndarya", "jeganathan", "9629559254", "green home", 46, 34, 105, "600096", "OMR", "Home");
		addBody(updateAddress_input_pojo);

		Response response = requestType("PUT", EndPoints.UPDATEADDRESS);
		int statuscode = getStatuscode(response);
		System.out.println(statuscode);

		org.testng.Assert.assertEquals(statuscode, 200, "verify statuscode");

		UpdateAddress_Output_pojo updateAddress_Output_pojo = response.as(UpdateAddress_Output_pojo.class);
		String message = updateAddress_Output_pojo.getMessage();
		System.out.println(message);
		org.testng.Assert.assertEquals(message, "Address updated successfully", "verify address updated");
	}

	@Test(priority = 4)
	public void getAddress() {

		List<Header> h = new ArrayList<Header>();
		Header h1 = new Header("content-Type", "application/json");

		Header h2 = new Header("Authorization", "Bearer " + logtoken);
		h.add(h1);
		h.add(h2);
		Headers headers = new Headers(h);
		addHeaders(headers);

		Response response = requestType("GET", EndPoints.GETADDRESSES);
		int statuscode = getStatuscode(response);
		System.out.println(statuscode);

		org.testng.Assert.assertEquals(statuscode, 200, "verify statuscode");

		GetAddresses_Output_pojo getAddresses_Output_pojo = response.as(GetAddresses_Output_pojo.class);
		String message = getAddresses_Output_pojo.getMessage();
		org.testng.Assert.assertEquals(message, "OK", "verify ok");

	}
	
	@Test(priority=5)
	public void DeleteAddress() {
		
		List<Header> h = new ArrayList<Header>();
		Header h1 = new Header("content-Type", "application/json");

		Header h2 = new Header("Authorization", "Bearer " + logtoken);
		h.add(h1);
		h.add(h2);
		Headers headers = new Headers(h);
		addHeaders(headers);
		
		DeleteAddress_Input_pojo deleteAddress_Input_pojo=new DeleteAddress_Input_pojo(String.valueOf(address_id2));
		addBody(deleteAddress_Input_pojo);
		
	     Response response = requestType("DELETE", EndPoints.DELETEADDRESS);
	     
		int statuscode = getStatuscode(response);
		org.testng.Assert.assertEquals(statuscode, 200,"verify status code");
		
		DeleteAddress_Output_pojo deleteAddress_Output_pojo = response.as(DeleteAddress_Output_pojo.class);
		String message = deleteAddress_Output_pojo.getMessage();
		org.testng.Assert.assertEquals(message, "Address deleted successfully","verify address deleted");
		
	}
		@Test(priority=6)
		public void profilepicupdate() {
			
			List<Header> h = new ArrayList<Header>();
			Header h1 = new Header("content-Type", "multipart/form-data");

			Header h2 = new Header("Authorization", "Bearer " + logtoken);
			h.add(h1);
			h.add(h2);
			Headers headers = new Headers(h);
			addHeaders(headers);
			
			formData();
			
		    Response response = requestType("POST", EndPoints.PROFILEPIC);
		     
			int statuscode = getStatuscode(response);
			org.testng.Assert.assertEquals(statuscode, 200,"verify status code");
			
			ChangeProfilePic_Output_Pojo changeProfilePic_Output_Pojo = response.as(ChangeProfilePic_Output_Pojo.class);
			String message = changeProfilePic_Output_Pojo.getMessage();
			org.testng.Assert.assertEquals(message, "Profile updated Successfully","verify Profile updated Successfully");
			
	
		
		
		
		
		
		
		
		
		
		
		
		
	}

	
}
