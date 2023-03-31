package com.wrappiza.application.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wrappiza.application.model.CreateCustomerRequestInfo;
import com.wrappiza.application.model.CreateCustomerResource;
import com.wrappiza.application.model.CreateCustomerResponse;

/**
 * Mapping DTO's to Object entities and visa-versa
 * 
 * @author Chandan Kumar
 *
 */
@Component
public class CustomerServiceMapper {

	/**
	 * Method to build createResource
	 * 
	 * @param createCustomerRequestInfo
	 *
	 */
	public List<CreateCustomerResource> createResource(List<CreateCustomerRequestInfo> createCustomerRequestInfo,
			String status) {

		List<CreateCustomerResource> resources = new ArrayList<>();

		for (CreateCustomerRequestInfo request : createCustomerRequestInfo) {

			CreateCustomerResource resource = new CreateCustomerResource();

			resource.setName(request.getName());
			resource.setEmail(request.getEmail());
			resource.setMobileNumber(request.getMobileNumber());
			resource.setStatus(status);
			resource.setDate(LocalDate.now());

			resources.add(resource);
		}

		return resources;
	}

	/**
	 * Method to build CreateCustomerResource
	 * 
	 * @param createCustomerResource
	 *
	 */
	public CreateCustomerResponse buildCreateCustomerResponse(List<CreateCustomerResource> createCustomerResource) {

		CreateCustomerResponse createCustomerResponse = new CreateCustomerResponse();
		createCustomerResponse.setCreateCustomerResources(createCustomerResource);

		return createCustomerResponse;
	}
}

