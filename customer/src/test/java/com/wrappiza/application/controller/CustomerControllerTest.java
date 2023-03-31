package com.wrappiza.application.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrappiza.application.CustomerApplication;
import com.wrappiza.application.model.CreateCustomerRequest;
import com.wrappiza.application.model.CreateCustomerRequestInfo;
import com.wrappiza.application.model.CreateCustomerResource;
import com.wrappiza.application.model.CreateCustomerResponse;
import com.wrappiza.application.service.CustomerServiceImpl;

/**
 * Test cases for controller class
 * 
 * @author Chandan Kumar
 * 
 */
@SpringBootTest(classes = CustomerApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	private String addCustomer = "/wrappiza/addcustomer";
	private String retrieveAll = "/wrappiza/customer";
	private String getSingleCustomerByMobileNumber = "/wrappiza/singlecustomer?mobileNumber=1234567890";
	private String getSingleCustomerById = "/wrappiza/singlecustomer?id=2";
	private String updateCustomerByMobileNumber = "/wrappiza/updatecustomer?mobileNumber=1234567890";
	private String updateCustomerById = "/wrappiza/updatecustomer?id=1";
	private String deleteCustomerByMobileNumber = "/wrappiza/deletecustomer?mobileNumber=1234567890";
	private String deleteCustomerById = "/wrappiza/deletecustomer?id=2";

	@MockBean
	private CustomerServiceImpl service;

	@Test
	@Order(1)
	@DisplayName("Add customer when name is missing")
	void createCustomer_shouldReturnBadRequest_whenNameIsMissing() throws Exception {

		String requestBody = "{\"email\": \"ravchandan16@gmail.com\"," + " \"mobileNumber\": \"1234567891\"}";

		mvc.perform(post(addCustomer).contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Order(2)
	@DisplayName("Add customer when email is missing")
	void createCustomer_shouldReturnBadRequest_whenEmailIsInvalid() throws Exception {

		String requestBody = "{\"name\": \"Chandan Kumar\", \"email\": \"invalid-email\", \"mobileNumber\": \"1234567891\"}";

		mvc.perform(post(addCustomer).contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Order(3)
	@DisplayName("Add customer when mobile number is missing")
	void createCustomer_shouldReturnBadRequest_whenMobileNumberIsInvalid() throws Exception {

		String requestBody = "{\"name\": \"Chandan Kumar\", \"email\": \"ravchandan16@gmail.com\", \"mobileNumber\": \"invalid-mobile\"}";

		mvc.perform(post(addCustomer).contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Order(4)
	@DisplayName("Add customer when name, email and mobile number is missing")
	void createCustomer_shouldReturnBadRequest_whenAllInputsAre_InValid() throws Exception {

		String requestBody = "{\"name\": \"invalid-name\", \"email\": \"invalid-email\", \"mobileNumber\": \"invalid-mobile\"}";

		mvc.perform(post(addCustomer).contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerController#retrieveAll()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@Order(5)
	@DisplayName("Retrieve All")
	void testRetrieveAll() throws Exception {

		CreateCustomerResponse response = customerResponse();

		when(service.retrieveCustomer()).thenReturn(response);

		mvc.perform(get(retrieveAll).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerController#retrieveSingleCustomer()}
	 * and response is handled successfully
	 * 
	 */
	@Test
	@Order(6)
	@DisplayName("Retrieve Single Customer By Mobile Number")
	void testRetrieveSingleCustomerByMobileNumber() throws Exception {

		CreateCustomerResponse response = customerResponse();

		when(service.getSingleCustomer(null, "Chandan Kumar")).thenReturn(response);

		mvc.perform(get(getSingleCustomerByMobileNumber).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerController#retrieveSingleCustomerById()}
	 * and response is handled successfully
	 * 
	 */
	@Test
	@Order(7)
	@DisplayName("Retrieve Single Customer By Id")
	void testRetrieveSingleCustomerById() throws Exception {

		CreateCustomerResponse response = customerResponse();

		when(service.getSingleCustomer("2", null)).thenReturn(response);

		mvc.perform(get(getSingleCustomerById).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerController#updateCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@Order(8)
	@DisplayName("Update Customer By Mobile Number")
	void testupdateCustomerByMobileNumber() throws Exception {

		CreateCustomerResponse response = customerResponse();
		response.getCreateCustomerResources().remove(1);

		CreateCustomerRequest request = customerRequest();
		request.getCreateCustomerRequestInfos().remove(1);

		when(service.updateSingleCustomer(null, "1234567891", request)).thenReturn(response);

		mvc.perform(put(updateCustomerByMobileNumber).content(mapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerController#updateCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@Order(9)
	@DisplayName("Update Customer By Id")
	void testUpdateCustomerById() throws Exception {

		CreateCustomerResponse response = customerResponse();
		response.getCreateCustomerResources().remove(1);

		CreateCustomerRequest request = customerRequest();
		request.getCreateCustomerRequestInfos().remove(1);

		when(service.updateSingleCustomer("1", null, request)).thenReturn(response);

		mvc.perform(put(updateCustomerById).content(mapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerController#deleteCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@Order(10)
	@DisplayName("Delete Customer By Mobile Number")
	void deleteCustomerByMobileNumber() throws Exception {

		CreateCustomerResponse response = customerResponse();

		when(service.deleteSingleCustomer(null, "1234567891")).thenReturn(response);

		mvc.perform(delete(deleteCustomerByMobileNumber).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerController#deleteCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@Order(11)
	@DisplayName("Delete Customer By Id")
	void testDeleteCustomerById() throws Exception {

		CreateCustomerResponse response = customerResponse();

		when(service.deleteSingleCustomer("1", null)).thenReturn(response);

		mvc.perform(delete(deleteCustomerById).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	/**
	 * Build mock to create customer response
	 */
	private CreateCustomerResponse customerResponse() {

		CreateCustomerResponse customerResponse = new CreateCustomerResponse();
		customerResponse.setCreateCustomerResources(customerResources());

		return customerResponse;
	}

	/**
	 * Build mock to create customer resources
	 */
	private List<CreateCustomerResource> customerResources() {

		CreateCustomerResource createCustomerResource1 = new CreateCustomerResource();
		createCustomerResource1.setCustomerId("1");
		createCustomerResource1.setName("Chandan Kumar");
		createCustomerResource1.setEmail("ravchandan15@gmail.com");
		createCustomerResource1.setMobileNumber("1234567891");
		createCustomerResource1.setDate(LocalDate.now());

		CreateCustomerResource createCustomerResource2 = new CreateCustomerResource();
		createCustomerResource2.setCustomerId("2");
		createCustomerResource2.setName("Dhiraj Kumar");
		createCustomerResource2.setEmail("ravchandan16@gmail.com");
		createCustomerResource2.setMobileNumber("1234567892");
		createCustomerResource2.setDate(LocalDate.now());

		List<CreateCustomerResource> createCustomerResource = new ArrayList<>();
		createCustomerResource.add(createCustomerResource1);
		createCustomerResource.add(createCustomerResource2);

		return createCustomerResource;
	}

	/**
	 * Build mock to create customer request
	 */
	private CreateCustomerRequest customerRequest() {

		CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
		createCustomerRequest.setCreateCustomerRequestInfos(customerRequestInfo());

		return createCustomerRequest;
	}

	/**
	 * Build mock to create customer request info
	 */
	private List<CreateCustomerRequestInfo> customerRequestInfo() {

		CreateCustomerRequestInfo createCustomerRequestInfo1 = new CreateCustomerRequestInfo();
		createCustomerRequestInfo1.setName("Chandan Kumar");
		createCustomerRequestInfo1.setEmail("ravchandan15@gmail.com");
		createCustomerRequestInfo1.setMobileNumber("1234567891");

		CreateCustomerRequestInfo createCustomerRequestInfo2 = new CreateCustomerRequestInfo();
		createCustomerRequestInfo2.setName("Dhiraj Kumar");
		createCustomerRequestInfo2.setEmail("ravchandan16@gmail.com");
		createCustomerRequestInfo2.setMobileNumber("1234567892");

		List<CreateCustomerRequestInfo> items = new ArrayList<>();
		items.add(createCustomerRequestInfo1);
		items.add(createCustomerRequestInfo2);

		return items;
	}
}