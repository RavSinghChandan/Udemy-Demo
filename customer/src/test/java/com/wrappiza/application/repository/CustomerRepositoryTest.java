package com.wrappiza.application.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.wrappiza.application.exception.TerminalDBException;
import com.wrappiza.application.model.CreateCustomerResource;
import com.wrappiza.application.model.Status;
import com.wrappiza.application.service.CustomerServiceMapper;

/**
 * Test cases for Repository class
 * 
 * @author Chandan Kumar
 *
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class CustomerRepositoryTest {

	@InjectMocks
	private CustomerRepositoryImpl repo;

	@Mock
	private MongoTemplate mongoTemplate;

	@Mock
	private CustomerServiceMapper mapper;

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#saveCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Save Customer")
	@Order(1)
	void saveCustomerTest() {

		List<CreateCustomerResource> resources = customerResources();

		when(mongoTemplate.insertAll(resources)).thenReturn(resources);

		List<CreateCustomerResource> resource = repo.saveCustomer(resources);

		assertThat(resource, notNullValue());
		assertThat(resource.get(0).getName(), equalTo("Chandan Kumar"));
		assertThat(resource.get(0).getCustomerId(), equalTo("1"));
		assertThat(resource.get(0).getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(resource.get(0).getDate(), equalTo(LocalDate.now()));
		assertThat(resource.get(0).getMobileNumber(), equalTo("1234567891"));

		verify(mongoTemplate, times(1)).insertAll(resources);
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#saveCustomer()} and
	 * exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Save Customer Exception")
	@Order(2)
	void saveCustomerTerminalDBExceptionTest() {

		List<CreateCustomerResource> resources = customerResources();

		repo.saveCustomer(resources);

		when(mongoTemplate.insertAll(resources)).thenThrow(TerminalDBException.class);

		verify(mongoTemplate, times(1)).insertAll(resources);

		assertThrows(TerminalDBException.class, () -> mongoTemplate.insertAll(resources));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#saveSingleCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Save Single Customer")
	@Order(3)
	void saveSingleCustomerTest() {

		CreateCustomerResource resources = customerResources().get(0);

		when(mongoTemplate.insert(resources)).thenReturn(resources);

		CreateCustomerResource resource = repo.saveSingleCustomer(resources);

		assertThat(resource, notNullValue());
		assertThat(resource.getName(), equalTo("Chandan Kumar"));
		assertThat(resource.getCustomerId(), equalTo("1"));
		assertThat(resource.getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(resource.getDate(), equalTo(LocalDate.now()));
		assertThat(resource.getMobileNumber(), equalTo("1234567891"));

		verify(mongoTemplate, times(1)).insert(resources);
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#saveSingleCustomer()} and
	 * exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Save Single Customer Exception")
	@Order(4)
	void saveSingleCustomerTerminalDBExceptionTest() {

		CreateCustomerResource resource = customerResources().get(0);

		repo.saveSingleCustomer(resource);

		when(mongoTemplate.insert(resource)).thenThrow(TerminalDBException.class);

		verify(mongoTemplate, times(1)).insert(resource);

		assertThrows(TerminalDBException.class, () -> mongoTemplate.insert(resource));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#getCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Get Customer")
	@Order(5)
	void getCustomerTest() {

		List<CreateCustomerResource> resources = customerResources();

		Query query = new Query();
		query.addCriteria(Criteria.where("status").ne(Status.DELETED));

		when(mongoTemplate.find(query, CreateCustomerResource.class)).thenReturn(resources);

		List<CreateCustomerResource> resource = repo.getCustomer();

		assertThat(resource, notNullValue());
		assertThat(resource.get(0).getName(), equalTo("Chandan Kumar"));
		assertThat(resource.get(0).getCustomerId(), equalTo("1"));
		assertThat(resource.get(0).getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(resource.get(0).getDate(), equalTo(LocalDate.now()));
		assertThat(resource.get(0).getMobileNumber(), equalTo("1234567891"));

		verify(mongoTemplate, times(1)).find(query, CreateCustomerResource.class);
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#getCustomer()} and
	 * exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Get Customer Exception")
	@Order(6)
	void getCustomerTerminalDBExceptionTest() {

		repo.getCustomer();

		Query query = new Query();
		query.addCriteria(Criteria.where("status").ne(Status.DELETED));

		when(mongoTemplate.find(query, CreateCustomerResource.class)).thenThrow(TerminalDBException.class);

		verify(mongoTemplate, times(1)).find(query, CreateCustomerResource.class);

		assertThrows(TerminalDBException.class, () -> mongoTemplate.find(query, CreateCustomerResource.class));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#getCustomerByMobileNumber()}
	 * and response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Get Customer By Mobile Number")
	@Order(7)
	void getCustomerByMobileNumberTest() {

		String mobileNumber = "1234567891";
		Query query = new Query();
		query.addCriteria(Criteria.where("status").ne(Status.DELETED));

		CreateCustomerResource oneResource = customerResources().get(0);

		when(mongoTemplate.findOne(any(Query.class), any())).thenReturn(oneResource);

		CreateCustomerResource resource = repo.getCustomerByMobileNumber(mobileNumber);

		assertThat(resource, notNullValue());
		assertThat(resource.getName(), equalTo("Chandan Kumar"));
		assertThat(resource.getCustomerId(), equalTo("1"));
		assertThat(resource.getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(resource.getDate(), equalTo(LocalDate.now()));
		assertThat(resource.getMobileNumber(), equalTo("1234567891"));

		verify(mongoTemplate, times(1)).findOne(any(Query.class), any());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#getCustomerByMobileNumber()}
	 * and exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Get Customer By Mobile Number Exception")
	@Order(8)
	void getCustomerByMobileNumberTerminalDBExceptionTest() {

		String mobileNumber = "1234567891";

		Query query = new Query();
		query.addCriteria(Criteria.where("mobileNumber").is(mobileNumber));
		query.addCriteria(Criteria.where("status").ne(Status.DELETED));

		repo.getCustomerByMobileNumber(mobileNumber);

		when(mongoTemplate.findOne(query, CreateCustomerResource.class)).thenThrow(TerminalDBException.class);

		verify(mongoTemplate, times(1)).findOne(query, CreateCustomerResource.class);

		assertThrows(TerminalDBException.class, () -> mongoTemplate.findOne(query, CreateCustomerResource.class));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#getCustomerById()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Get Customer By Id")
	@Order(9)
	void getCustomerByIdTest() {

		String id = "1";
		
		Query query = new Query();
		query.addCriteria(Criteria.where("status").ne(Status.DELETED));
		query.addCriteria(Criteria.where("_id").is(id));

		CreateCustomerResource resources = customerResources().get(0);

		when(mongoTemplate.findOne(query, CreateCustomerResource.class)).thenReturn(resources);

		CreateCustomerResource resource = repo.getCustomerById(id);

		assertThat(resource, notNullValue());
		assertThat(resource.getName(), equalTo("Chandan Kumar"));
		assertThat(resource.getCustomerId(), equalTo("1"));
		assertThat(resource.getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(resource.getDate(), equalTo(LocalDate.now()));
		assertThat(resource.getMobileNumber(), equalTo("1234567891"));

		verify(mongoTemplate, times(1)).findOne(query, CreateCustomerResource.class);
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#getCustomerById()} and
	 * exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Get Customer By Id Exception")
	@Order(10)
	void getCustomerByIdTerminalDBExceptionTest() {

		String id = "1";
		
		Query query = new Query();
		query.addCriteria(Criteria.where("status").ne(Status.DELETED));
		query.addCriteria(Criteria.where("_id").is(id));

		repo.getCustomerById(id);

		when(mongoTemplate.findOne(query, CreateCustomerResource.class)).thenThrow(TerminalDBException.class);

		verify(mongoTemplate, times(1)).findOne(query, CreateCustomerResource.class);

		assertThrows(TerminalDBException.class, () -> mongoTemplate.findOne(query, CreateCustomerResource.class));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#updateCustomerByMobileNumber()}
	 * and response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Update Customer By Mobile Number")
	@Order(11)
	void updateCustomerByMobileNumberTest() {

		CreateCustomerResource resources = customerResources().get(0);

		when(mongoTemplate.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class), any()))
				.thenReturn(resources);

		CreateCustomerResource resource = repo.updateCustomerByMobileNumber(customerResources().get(0));

		assertThat(resource, notNullValue());
		assertThat(resource.getName(), equalTo("Chandan Kumar"));
		assertThat(resource.getCustomerId(), equalTo("1"));
		assertThat(resource.getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(resource.getDate(), equalTo(LocalDate.now()));
		assertThat(resource.getMobileNumber(), equalTo("1234567891"));

		verify(mongoTemplate, times(1)).findAndModify(any(Query.class), any(Update.class),
				any(FindAndModifyOptions.class), any());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#updateCustomerByMobileNumber()}
	 * and exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Update Customer By Mobile Number Exception")
	@Order(12)
	void updateCustomerByMobileNumberTerminalDBExceptionTest() {

		repo.updateCustomerByMobileNumber(customerResources().get(0));

		lenient().when(mongoTemplate.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class),
				any())).thenThrow(TerminalDBException.class);

		verify(mongoTemplate, times(1)).findAndModify(any(Query.class), any(Update.class),
				any(FindAndModifyOptions.class), any());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#updateCustomerById()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Update Customer By Id")
	@Order(13)
	void updateCustomerByIdTest() {

		CreateCustomerResource resources = customerResources().get(0);

		when(mongoTemplate.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class), any()))
				.thenReturn(resources);

		CreateCustomerResource resource = repo.updateCustomerById(customerResources().get(0));

		assertThat(resource, notNullValue());
		assertThat(resource.getName(), equalTo("Chandan Kumar"));
		assertThat(resource.getCustomerId(), equalTo("1"));
		assertThat(resource.getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(resource.getDate(), equalTo(LocalDate.now()));
		assertThat(resource.getMobileNumber(), equalTo("1234567891"));

		verify(mongoTemplate, times(1)).findAndModify(any(Query.class), any(Update.class),
				any(FindAndModifyOptions.class), any());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#updateCustomerById()} and
	 * exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Update Customer By Id Exception")
	@Order(14)
	void updateCustomerByIdTerminalDBExceptionTest() {

		repo.updateCustomerById(customerResources().get(0));

		lenient().when(mongoTemplate.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class),
				any())).thenThrow(TerminalDBException.class);

		verify(mongoTemplate, times(1)).findAndModify(any(Query.class), any(Update.class),
				any(FindAndModifyOptions.class), any());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#deleteCustomerByMobileNumber()}
	 * and response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Delete Customer By Mobile Number")
	@Order(15)
	void deleteCustomerByMobileNumberTest() {

		String mobileNumber = "1234567891";

		CreateCustomerResource resources = customerResources().get(0);

		when(mongoTemplate.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class), any()))
				.thenReturn(resources);

		CreateCustomerResource resource = repo.deleteCustomerByMobileNumber(mobileNumber);

		assertThat(resource, notNullValue());
		assertThat(resource.getName(), equalTo("Chandan Kumar"));
		assertThat(resource.getCustomerId(), equalTo("1"));
		assertThat(resource.getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(resource.getDate(), equalTo(LocalDate.now()));
		assertThat(resource.getMobileNumber(), equalTo("1234567891"));

		verify(mongoTemplate, times(1)).findAndModify(any(Query.class), any(Update.class),
				any(FindAndModifyOptions.class), any());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#deleteCustomerByMobileNumber()}
	 * and exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Delete Customer By Mobile Number Exception")
	@Order(16)
	void deleteCustomerByMobileNumberTerminalDBExceptionTest() {

		repo.deleteCustomerByMobileNumber("1234567891");

		lenient().when(mongoTemplate.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class),
				any())).thenThrow(TerminalDBException.class);

		verify(mongoTemplate, times(1)).findAndModify(any(Query.class), any(Update.class),
				any(FindAndModifyOptions.class), any());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#deleteCustomerById()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Delete Customer By Id")
	@Order(17)
	void deleteCustomerByIdTest() {

		String id = "1";

		CreateCustomerResource resources = customerResources().get(0);

		when(mongoTemplate.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class), any()))
				.thenReturn(resources);

		CreateCustomerResource resource = repo.deleteCustomerById(id);

		assertThat(resource, notNullValue());
		assertThat(resource.getName(), equalTo("Chandan Kumar"));
		assertThat(resource.getCustomerId(), equalTo("1"));
		assertThat(resource.getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(resource.getDate(), equalTo(LocalDate.now()));
		assertThat(resource.getMobileNumber(), equalTo("1234567891"));

		verify(mongoTemplate, times(1)).findAndModify(any(Query.class), any(Update.class),
				any(FindAndModifyOptions.class), any());
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerRepository#deleteCustomerById()} and
	 * exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Delete Customer By Id Exception")
	@Order(18)
	void deleteCustomerByIdTerminalDBExceptionTest() {

		repo.deleteCustomerById("1");

		lenient().when(mongoTemplate.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class),
				any())).thenThrow(TerminalDBException.class);

		verify(mongoTemplate, times(1)).findAndModify(any(Query.class), any(Update.class),
				any(FindAndModifyOptions.class), any());
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
}