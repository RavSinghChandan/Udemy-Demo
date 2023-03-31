package com.wrappiza.application.repository;

import java.util.List;

import com.wrappiza.application.model.CreateCustomerResource;

/**
 * Interface for Customer Repository
 * 
 * @author Chandan Kumar
 *
 */
public interface CustomerRepository {

	/**
	 * Takes the customer list and save it to the repository
	 *
	 * @param createCustomerResource
	 */
	List<CreateCustomerResource> saveCustomer(List<CreateCustomerResource> createCustomerResource);

	/**
	 * Takes a single customer and save it to the repository
	 *
	 * @param createCustomerResource
	 */
	CreateCustomerResource saveSingleCustomer(CreateCustomerResource createCustomerResource);

	/**
	 * Retrieve all customer saved into the repository
	 * 
	 * @param
	 */
	List<CreateCustomerResource> getCustomer();

	/**
	 * Retrieve single customer saved from the repository by mobile number
	 * 
	 * @param mobileNumber
	 */
	CreateCustomerResource getCustomerByMobileNumber(String mobileNumber);

	/**
	 * Retrieve single customer saved from the repository by id
	 * 
	 * @param id
	 */
	CreateCustomerResource getCustomerById(String id);

	/**
	 * Update single customer saved from the repository by mobile number
	 * 
	 * @param createCustomerResource
	 * 
	 */
	CreateCustomerResource updateCustomerByMobileNumber(CreateCustomerResource createCustomerResource);

	/**
	 * Update single customer saved from the repository by id
	 * 
	 * @param createCustomerResource
	 * 
	 */
	CreateCustomerResource updateCustomerById(CreateCustomerResource createCustomerResource);

	/**
	 * Delete single customer saved from the repository by mobile number
	 * 
	 * @param mobileNumber
	 */
	CreateCustomerResource deleteCustomerByMobileNumber(String mobileNumber);

	/**
	 * Delete single customer saved from the repository by id
	 * 
	 * @param id
	 */
	CreateCustomerResource deleteCustomerById(String id);
}