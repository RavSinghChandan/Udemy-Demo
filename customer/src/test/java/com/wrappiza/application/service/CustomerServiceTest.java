package com.wrappiza.application.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wrappiza.application.exception.ServiceErrorCode;
import com.wrappiza.application.exception.ServiceTerminalException;
import com.wrappiza.application.exception.TerminalDBException;
import com.wrappiza.application.model.CreateCustomerRequest;
import com.wrappiza.application.model.CreateCustomerRequestInfo;
import com.wrappiza.application.model.CreateCustomerResource;
import com.wrappiza.application.model.CreateCustomerResponse;
import com.wrappiza.application.model.Status;
import com.wrappiza.application.repository.CustomerRepository;

/**
 * Test cases for service class
 * 
 * @author Chandan Kumar
 *
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class CustomerServiceTest {

	@Mock
	private CustomerRepository repo;

	@Mock
	private CustomerServiceMapper mapper;

	@InjectMocks
	private CustomerServiceImpl service;

	@BeforeEach
	void setUp() {
		service = new CustomerServiceImpl(repo, mapper);
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#saveCustomer()} and response
	 * is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Create Customer")
	@Order(1)
	void saveCustomerTest() {

		List<CreateCustomerResource> resources = customerResources();

		CreateCustomerRequest request = customerRequest();

		when(repo.saveCustomer(resources)).thenReturn(resources);

		when(mapper.createResource(request.getCreateCustomerRequestInfos(), Status.CREATED.name()))
				.thenReturn(resources);

		when(mapper.buildCreateCustomerResponse(resources)).thenReturn(customerResponse());

		CreateCustomerResponse response = service.createCustomer(request);

		assertThat(response, notNullValue());
		assertThat(response.getCreateCustomerResources(), notNullValue());
		assertThat(response.getCreateCustomerResources().get(0).getName(), equalTo("Chandan Kumar"));
		assertThat(response.getCreateCustomerResources().get(0).getCustomerId(), equalTo("1"));
		assertThat(response.getCreateCustomerResources().get(0).getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(response.getCreateCustomerResources().get(0).getDate(), equalTo(LocalDate.now()));
		assertThat(response.getCreateCustomerResources().get(0).getMobileNumber(), equalTo("1234567891"));

		assertThat(response, notNullValue());
		assertThat(response.getCreateCustomerResources(), notNullValue());

		assertThat(response.getCreateCustomerResources().get(1).getName(), equalTo("Dhiraj Kumar"));
		assertThat(response.getCreateCustomerResources().get(1).getCustomerId(), equalTo("2"));
		assertThat(response.getCreateCustomerResources().get(1).getEmail(), equalTo("ravchandan16@gmail.com"));
		assertThat(response.getCreateCustomerResources().get(1).getDate(), equalTo(LocalDate.now()));
		assertThat(response.getCreateCustomerResources().get(1).getMobileNumber(), equalTo("1234567892"));

		verify(repo, times(1)).saveCustomer(resources);
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#saveCustomer()} and exception
	 * is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Create Customer Exception")
	@Order(2)
	void saveCustomerServiceTerminalExceptionTest() {

		List<CreateCustomerResource> resources = customerResources();

		CreateCustomerRequest request = customerRequest();

		when(mapper.createResource(request.getCreateCustomerRequestInfos(), Status.CREATED.name()))
				.thenReturn(resources);

		when(repo.saveCustomer(resources)).thenThrow(TerminalDBException.class);

		Throwable exception = assertThrows(ServiceTerminalException.class, () -> service.createCustomer(request));

		assertThat(exception.getMessage(), equalTo(ServiceErrorCode.DB_INSERTION_FAILURE.getMessage()));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#saveSingleCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Retrieve Customer")
	@Order(3)
	void saveSingleCustomerTest() {

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);
		CreateCustomerRequest request = customerRequest();

		request.getCreateCustomerRequestInfos().remove(1);

		when(repo.saveSingleCustomer(resources.get(0))).thenReturn(resources.get(0));

		when(mapper.createResource(request.getCreateCustomerRequestInfos(), Status.CREATED.name()))
				.thenReturn(resources);

		when(mapper.buildCreateCustomerResponse(resources)).thenReturn(customerResponse());

		CreateCustomerResponse response = service.createCustomer(request);

		assertThat(response, notNullValue());
		assertThat(response.getCreateCustomerResources(), notNullValue());
		assertThat(response.getCreateCustomerResources().get(0).getName(), equalTo("Chandan Kumar"));
		assertThat(response.getCreateCustomerResources().get(0).getCustomerId(), equalTo("1"));
		assertThat(response.getCreateCustomerResources().get(0).getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(response.getCreateCustomerResources().get(0).getDate(), equalTo(LocalDate.now()));
		assertThat(response.getCreateCustomerResources().get(0).getMobileNumber(), equalTo("1234567891"));

		verify(repo, times(1)).saveSingleCustomer(resources.get(0));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#saveSingleCustomer()} and
	 * exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Retrieve Customer Exception")
	@Order(4)
	void saveSingleCustomerServiceTerminalExceptionTest() {

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);
		CreateCustomerRequest request = customerRequest();

		request.getCreateCustomerRequestInfos().remove(1);

		when(mapper.createResource(request.getCreateCustomerRequestInfos(), Status.CREATED.name()))
				.thenReturn(resources);

		when(repo.saveSingleCustomer(resources.get(0))).thenThrow(TerminalDBException.class);

		Throwable exception = assertThrows(ServiceTerminalException.class, () -> service.createCustomer(request));

		assertThat(exception.getMessage(), equalTo(ServiceErrorCode.DB_INSERTION_FAILURE.getMessage()));
	}

	/**
	 * Test case to validate {@link com.wrappiza.application.CustomerService#
	 * getCustomer()} and response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Get Single Customer")
	@Order(5)
	void getCustomerTest() {

		List<CreateCustomerResource> resource = customerResources();

		when(repo.getCustomer()).thenReturn(resource);

		when(mapper.buildCreateCustomerResponse(resource)).thenReturn(customerResponse());

		CreateCustomerResponse response = service.retrieveCustomer();

		assertThat(response, notNullValue());
		assertThat(response.getCreateCustomerResources(), notNullValue());
		assertThat(response.getCreateCustomerResources().get(0).getName(), equalTo("Chandan Kumar"));
		assertThat(response.getCreateCustomerResources().get(0).getCustomerId(), equalTo("1"));
		assertThat(response.getCreateCustomerResources().get(0).getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(response.getCreateCustomerResources().get(0).getDate(), equalTo(LocalDate.now()));
		assertThat(response.getCreateCustomerResources().get(0).getMobileNumber(), equalTo("1234567891"));

		verify(repo, times(1)).getCustomer();
	}

	/**
	 * Test case to validate {@link com.wrappiza.application.CustomerService#getCustomer()}
	 * and exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Get Single Customer Exception")
	@Order(6)
	void getCustomerServiceTerminalExceptionTest() {

		when(repo.getCustomer()).thenThrow(TerminalDBException.class);

		Throwable exception = assertThrows(ServiceTerminalException.class, () -> service.retrieveCustomer());

		assertThat(exception.getMessage(), equalTo(ServiceErrorCode.DB_SEARCH_FAILURE.getMessage()));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#getSingleCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Get Single Customer By Mobile Number")
	@Order(7)
	void getCustomerByMobileNumberTest() {

		String mobileNumber = "1234567891";

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);

		when(repo.getCustomerByMobileNumber(mobileNumber)).thenReturn(resources.get(0));

		when(mapper.buildCreateCustomerResponse(resources)).thenReturn(customerResponse());

		CreateCustomerResponse response = service.getSingleCustomer(null, mobileNumber);

		assertThat(response, notNullValue());
		assertThat(response.getCreateCustomerResources(), notNullValue());
		assertThat(response.getCreateCustomerResources().get(0).getName(), equalTo("Chandan Kumar"));
		assertThat(response.getCreateCustomerResources().get(0).getCustomerId(), equalTo("1"));
		assertThat(response.getCreateCustomerResources().get(0).getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(response.getCreateCustomerResources().get(0).getDate(), equalTo(LocalDate.now()));
		assertThat(response.getCreateCustomerResources().get(0).getMobileNumber(), equalTo("1234567891"));

		verify(repo, times(1)).getCustomerByMobileNumber(mobileNumber);
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#getSingleCustomer()} and
	 * exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Get Single Customer By Mobile Number Exception")
	@Order(8)
	void getCustomerByMobileNumberServiceTerminalExceptionTest() {

		String mobileNumber = "1234567891";

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);

		when(repo.getCustomerByMobileNumber(mobileNumber)).thenThrow(TerminalDBException.class);

		Throwable exception = assertThrows(ServiceTerminalException.class,
				() -> service.getSingleCustomer(null, mobileNumber));

		assertThat(exception.getMessage(), equalTo(ServiceErrorCode.DB_SEARCH_FAILURE.getMessage()));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#getSingleCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Get Single Customer By Id")
	@Order(9)
	void getCustomerByIdTest() {

		String id = "1";

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);

		when(repo.getCustomerById(id)).thenReturn(resources.get(0));

		when(mapper.buildCreateCustomerResponse(resources)).thenReturn(customerResponse());

		CreateCustomerResponse response = service.getSingleCustomer(id, null);

		assertThat(response, notNullValue());
		assertThat(response.getCreateCustomerResources(), notNullValue());
		assertThat(response.getCreateCustomerResources().get(0).getName(), equalTo("Chandan Kumar"));
		assertThat(response.getCreateCustomerResources().get(0).getCustomerId(), equalTo("1"));
		assertThat(response.getCreateCustomerResources().get(0).getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(response.getCreateCustomerResources().get(0).getDate(), equalTo(LocalDate.now()));
		assertThat(response.getCreateCustomerResources().get(0).getMobileNumber(), equalTo("1234567891"));

		verify(repo, times(1)).getCustomerById(id);
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#getSingleCustomer()} and
	 * exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Get Single Customer By Id Exception")
	@Order(10)
	void getCustomerByIdServiceTerminalExceptionTest() {

		String id = "1";

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);

		when(repo.getCustomerById(id)).thenThrow(TerminalDBException.class);

		Throwable exception = assertThrows(ServiceTerminalException.class, () -> service.getSingleCustomer(id, null));

		assertThat(exception.getMessage(), equalTo(ServiceErrorCode.DB_SEARCH_FAILURE.getMessage()));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#updateSingleCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Update Single Customer By Mobile Number")
	@Order(11)
	void updateCustomerByMobileNumberTest() {

		String mobileNumber = "1234567891";

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);

		CreateCustomerRequest reqeust = customerRequest();

		when(repo.updateCustomerByMobileNumber(resources.get(0))).thenReturn(resources.get(0));

		when(mapper.createResource(reqeust.getCreateCustomerRequestInfos(), Status.UPDATED.name()))
				.thenReturn(resources);

		when(mapper.buildCreateCustomerResponse(resources)).thenReturn(customerResponse());

		CreateCustomerResponse response = service.updateSingleCustomer(null, mobileNumber, reqeust);

		assertThat(response, notNullValue());
		assertThat(response.getCreateCustomerResources(), notNullValue());
		assertThat(response.getCreateCustomerResources().get(0).getName(), equalTo("Chandan Kumar"));
		assertThat(response.getCreateCustomerResources().get(0).getCustomerId(), equalTo("1"));
		assertThat(response.getCreateCustomerResources().get(0).getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(response.getCreateCustomerResources().get(0).getDate(), equalTo(LocalDate.now()));
		assertThat(response.getCreateCustomerResources().get(0).getMobileNumber(), equalTo("1234567891"));

		verify(repo, times(1)).updateCustomerByMobileNumber(resources.get(0));

	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#updateSingleCustomer()} and
	 * exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Update Single Customer By Mobile Number Exception")
	@Order(12)
	void updateCustomerByMobileNumberServiceTerminalExceptionTest() {

		String id = "1";

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);

		CreateCustomerRequest request = customerRequest();

		when(mapper.createResource(request.getCreateCustomerRequestInfos(), Status.UPDATED.name()))
				.thenReturn(resources);

		when(repo.updateCustomerByMobileNumber(resources.get(0))).thenThrow(TerminalDBException.class);

		Throwable exception = assertThrows(ServiceTerminalException.class,
				() -> service.updateSingleCustomer(null, id, request));

		assertThat(exception.getMessage(), equalTo(ServiceErrorCode.DB_UPDATION_FAILURE.getMessage()));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#updateSingleCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Update Single Customer By Id")
	@Order(13)
	void updateSingleCustomerByIdTest() {

		String id = "1";

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);

		CreateCustomerRequest reqeust = customerRequest();

		when(repo.updateCustomerById(resources.get(0))).thenReturn(resources.get(0));

		when(mapper.createResource(reqeust.getCreateCustomerRequestInfos(), Status.UPDATED.name()))
				.thenReturn(resources);

		when(mapper.buildCreateCustomerResponse(resources)).thenReturn(customerResponse());

		CreateCustomerResponse response = service.updateSingleCustomer(id, null, reqeust);

		assertThat(response, notNullValue());
		assertThat(response.getCreateCustomerResources(), notNullValue());
		assertThat(response.getCreateCustomerResources().get(0).getName(), equalTo("Chandan Kumar"));
		assertThat(response.getCreateCustomerResources().get(0).getCustomerId(), equalTo("1"));
		assertThat(response.getCreateCustomerResources().get(0).getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(response.getCreateCustomerResources().get(0).getDate(), equalTo(LocalDate.now()));
		assertThat(response.getCreateCustomerResources().get(0).getMobileNumber(), equalTo("1234567891"));

		verify(repo, times(1)).updateCustomerById(resources.get(0));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#updateSingleCustomer()} and
	 * exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Update Single Customer By Id Exception")
	@Order(14)
	void updateCustomerByIdServiceTerminalExceptionTest() {

		String mobileNumber = "1234567891";

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);

		CreateCustomerRequest request = customerRequest();

		when(mapper.createResource(request.getCreateCustomerRequestInfos(), Status.UPDATED.name()))
				.thenReturn(resources);

		when(repo.updateCustomerById(resources.get(0))).thenThrow(TerminalDBException.class);

		Throwable exception = assertThrows(ServiceTerminalException.class,
				() -> service.updateSingleCustomer(mobileNumber, null, request));

		assertThat(exception.getMessage(), equalTo(ServiceErrorCode.DB_UPDATION_FAILURE.getMessage()));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#deleteSingleCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Delete Single Customer By Mobile Number")
	@Order(15)
	void deleteCustomerByMobileNumberTest() {

		String mobileNumber = "1234567891";

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);

		when(repo.deleteCustomerByMobileNumber(mobileNumber)).thenReturn(resources.get(0));

		when(mapper.buildCreateCustomerResponse(resources)).thenReturn(customerResponse());

		CreateCustomerResponse response = service.deleteSingleCustomer(null, mobileNumber);

		assertThat(response, notNullValue());
		assertThat(response.getCreateCustomerResources(), notNullValue());
		assertThat(response.getCreateCustomerResources().get(0).getName(), equalTo("Chandan Kumar"));
		assertThat(response.getCreateCustomerResources().get(0).getCustomerId(), equalTo("1"));
		assertThat(response.getCreateCustomerResources().get(0).getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(response.getCreateCustomerResources().get(0).getDate(), equalTo(LocalDate.now()));
		assertThat(response.getCreateCustomerResources().get(0).getMobileNumber(), equalTo("1234567891"));

		verify(repo, times(1)).deleteCustomerByMobileNumber(mobileNumber);
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#deleteSingleCustomer()} and exception
	 * is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Delete Single Customer By Mobile Number Exception")
	@Order(16)
	void deleteCustomerByMobileNumberServiceTerminalExceptionTest() {

		String mobileNumber = "1234567891";

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);

		when(repo.deleteCustomerByMobileNumber(mobileNumber)).thenThrow(TerminalDBException.class);

		Throwable exception = assertThrows(ServiceTerminalException.class,
				() -> service.deleteSingleCustomer(null, mobileNumber));

		assertThat(exception.getMessage(), equalTo(ServiceErrorCode.DB_DELETION_FAILURE.getMessage()));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#deleteSingleCustomer()} and
	 * response is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Delete Single Customer By Id")
	@Order(17)
	void deleteSingleCustomerByIdTest() {

		String id = "1";

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);

		when(repo.deleteCustomerById(id)).thenReturn(resources.get(0));

		when(mapper.buildCreateCustomerResponse(resources)).thenReturn(customerResponse());

		CreateCustomerResponse response = service.deleteSingleCustomer(id, null);

		assertThat(response, notNullValue());
		assertThat(response.getCreateCustomerResources(), notNullValue());
		assertThat(response.getCreateCustomerResources().get(0).getName(), equalTo("Chandan Kumar"));
		assertThat(response.getCreateCustomerResources().get(0).getCustomerId(), equalTo("1"));
		assertThat(response.getCreateCustomerResources().get(0).getEmail(), equalTo("ravchandan15@gmail.com"));
		assertThat(response.getCreateCustomerResources().get(0).getDate(), equalTo(LocalDate.now()));
		assertThat(response.getCreateCustomerResources().get(0).getMobileNumber(), equalTo("1234567891"));

		verify(repo, times(1)).deleteCustomerById(id);
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomdrService#deleteSingleCustomer()} and
	 * exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Delete Single Customer By Id Exception")
	@Order(18)
	void deleteCustomerByIdServiceTerminalExceptionTest() {

		String id = "1";

		List<CreateCustomerResource> resources = customerResources();
		resources.remove(1);

		when(repo.deleteCustomerById(id)).thenThrow(TerminalDBException.class);

		Throwable exception = assertThrows(ServiceTerminalException.class,
				() -> service.deleteSingleCustomer(id, null));

		assertThat(exception.getMessage(), equalTo(ServiceErrorCode.DB_DELETION_FAILURE.getMessage()));
	}

	/**
	 * Test case to validate
	 * {@link com.wrappiza.application.CustomerService#getSingleCustomer()} and
	 * exception is handled successfully
	 * 
	 */
	@Test
	@DisplayName("Delete Single Customer With Null Mobile Number And Id")
	@Order(19)
	void getCustomerWithNullMobileNumberAndId() {

		Throwable exception = assertThrows(ServiceTerminalException.class, () -> service.getSingleCustomer(null, null));

		assertThat(exception.getMessage(), equalTo(ServiceErrorCode.EMPTY_SEARCH_FAILURE.getMessage()));
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