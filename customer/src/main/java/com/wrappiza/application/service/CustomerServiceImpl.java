package com.wrappiza.application.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wrappiza.application.exception.ServiceErrorCode;
import com.wrappiza.application.exception.ServiceTerminalException;
import com.wrappiza.application.exception.TerminalDBException;
import com.wrappiza.application.model.CreateCustomerRequest;
import com.wrappiza.application.model.CreateCustomerResource;
import com.wrappiza.application.model.CreateCustomerResponse;
import com.wrappiza.application.model.Status;
import com.wrappiza.application.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Implemented class handling customer service
 * 
 * @author Chandan Kumar
 *
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private final CustomerRepository repo;

	@Autowired
	private final CustomerServiceMapper mapper;

	
	public CustomerServiceImpl(CustomerRepository repo, CustomerServiceMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	public CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
		log.debug("Request of CreateCustomerRequest : ", createCustomerRequest);

		CreateCustomerResponse createCustomerResponse = null;

		try {
			// When request having more than one customer
			if (createCustomerRequest.getCreateCustomerRequestInfos().size() > 1) {
				List<CreateCustomerResource> resources = repo.saveCustomer(mapper
						.createResource(createCustomerRequest.getCreateCustomerRequestInfos(), Status.CREATED.name()));
				createCustomerResponse = mapper.buildCreateCustomerResponse(resources);
			} else {
				List<CreateCustomerResource> resources = mapper
						.createResource(createCustomerRequest.getCreateCustomerRequestInfos(), Status.CREATED.name());
				CreateCustomerResource resource = repo.saveSingleCustomer(resources.get(0));
				resources.clear();
				resources.add(resource);
				createCustomerResponse = mapper.buildCreateCustomerResponse(resources);
			}
		} catch (TerminalDBException ex) {

			log.error("Exception occurred while saving add customer : ", ex);

			throw new ServiceTerminalException(ServiceErrorCode.DB_INSERTION_FAILURE);
		}

		log.debug("Response of CreateCustomerResponse : ", createCustomerResponse);

		return createCustomerResponse;
	}

	/**
	 * Retrieving all customer from the repository
	 * 
	 * @param
	 */
	@Override
	public CreateCustomerResponse retrieveCustomer() {

		List<CreateCustomerResource> createCustomerResource = null;
		CreateCustomerResponse response = null;

		try {
			createCustomerResource = repo.getCustomer();

			response = mapper.buildCreateCustomerResponse(createCustomerResource);

		} catch (TerminalDBException ex) {

			log.error("Exception occured while retrieving customer : {} ", ex);

			throw new ServiceTerminalException(ServiceErrorCode.DB_SEARCH_FAILURE);
		}

		log.debug("Response of retrieve customer : {} ", response);

		return response;
	}

	/**
	 * Retrieving single customer from the repository
	 * 
	 * @param  id, name
	 */
	@Override
	public CreateCustomerResponse getSingleCustomer(String id, String mobileNumber) {

		log.debug("Id : {} and mobile number : {} of retrieving single customer: ", id, mobileNumber);

		CreateCustomerResource createCustomerResource = null;
		CreateCustomerResponse response = null;

		try {
			if (StringUtils.isNotBlank(id)) {
				createCustomerResource = repo.getCustomerById(id);

			} else if (StringUtils.isNotBlank(mobileNumber)) {
				createCustomerResource = repo.getCustomerByMobileNumber(mobileNumber);

			} else {
				throw new ServiceTerminalException(ServiceErrorCode.EMPTY_SEARCH_FAILURE);
			}
			List<CreateCustomerResource> resources = new ArrayList<>();
			resources.add(createCustomerResource);
			response = mapper.buildCreateCustomerResponse(resources);

		} catch (TerminalDBException ex) {

			log.error("Exception occured while retrieving single customer : ", ex);

			throw new ServiceTerminalException(ServiceErrorCode.DB_SEARCH_FAILURE);
		}

		log.debug("Response of retrieve single customer : ", response);

		return response;
	}

	/**
	 * Updating single customer from the repository
	 * 
	 * @param  id
	 * @param mobileNumber
	 * 
	 */
	@Override
	public CreateCustomerResponse updateSingleCustomer(String id, String mobileNumber,
			CreateCustomerRequest createCustomerRequest) {

		log.debug(" Id : {} and mobile number : {} of updating single customer with request : {}", id, mobileNumber,
				createCustomerRequest);

		CreateCustomerResponse response = null;
		CreateCustomerResource createCustomerResource = null;

		try {
			if (StringUtils.isNotBlank(id)) {

				List<CreateCustomerResource> createCustomerResourceOne = mapper
						.createResource(createCustomerRequest.getCreateCustomerRequestInfos(), Status.UPDATED.name());

				createCustomerResourceOne.get(0).setCustomerId(id);

				createCustomerResource = repo.updateCustomerById(createCustomerResourceOne.get(0));

			} else if (StringUtils.isNotBlank(mobileNumber)) {

				List<CreateCustomerResource> createCustomerResourceOne = mapper
						.createResource(createCustomerRequest.getCreateCustomerRequestInfos(), Status.UPDATED.name());

				createCustomerResourceOne.get(0).setCustomerId(mobileNumber);

				createCustomerResource = repo.updateCustomerByMobileNumber(createCustomerResourceOne.get(0));

			} else {
				throw new ServiceTerminalException(ServiceErrorCode.DB_UPDATION_FAILURE);
			}

			List<CreateCustomerResource> resources = new ArrayList<>();
			resources.add(createCustomerResource);

			response = mapper.buildCreateCustomerResponse(resources);

		} catch (TerminalDBException ex) {

			log.error("Exception occured while updating single customer : ", ex);

			throw new ServiceTerminalException(ServiceErrorCode.DB_UPDATION_FAILURE);
		}

		log.debug("Response of update single customer : ", response);

		return response;
	}

	/**
	 * Deleting single customer from the repository
	 * 
	 * @param id, mobileNumber
	 */
	@Override
	public CreateCustomerResponse deleteSingleCustomer(String id, String mobileNumber) {

		log.debug(" Id : {} and mobileNumber : {} of deleting single customer ", id, mobileNumber);

		CreateCustomerResponse response = null;
		CreateCustomerResource createCustomerResource = null;

		try {
			if (StringUtils.isNotBlank(id)) {

				createCustomerResource = repo.deleteCustomerById(id);
			} else if (StringUtils.isNotBlank(mobileNumber)) {

				createCustomerResource = repo.deleteCustomerByMobileNumber(mobileNumber);
			} else {

				throw new ServiceTerminalException(ServiceErrorCode.DB_DELETION_FAILURE);
			}

			List<CreateCustomerResource> resources = new ArrayList<>();
			resources.add(createCustomerResource);

			response = mapper.buildCreateCustomerResponse(resources);

		} catch (TerminalDBException ex) {

			log.error("Exception occurred while deleting single customer : ", ex);

			throw new ServiceTerminalException(ServiceErrorCode.DB_DELETION_FAILURE);
		}

		log.debug("Response of delete single customer : ", response);

		return response;
	}
}