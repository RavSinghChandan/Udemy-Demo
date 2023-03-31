package com.wrappiza.application.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.wrappiza.application.exception.ServiceErrorCode;
import com.wrappiza.application.exception.TerminalDBException;
import com.wrappiza.application.model.CreateCustomerResource;
import com.wrappiza.application.model.Status;

import lombok.extern.slf4j.Slf4j;

/**
 * Implemented class handling customer repository
 * 
 * @author Chandan Kumar
 *
 */
@Repository
@Slf4j
public class CustomerRepositoryImpl implements CustomerRepository {

	@Autowired
	private final MongoTemplate mongoTemplate;


	public CustomerRepositoryImpl(MongoTemplate mongoTemplate) {
		super();
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * Method to save multiple customers into the database
	 * 
	 * @param createCustomerResource
	 */
	@Override
	public List<CreateCustomerResource> saveCustomer(List<CreateCustomerResource> createCustomerResource) {

		log.info("Saving add Customer is in progress");

		List<CreateCustomerResource> resources = null;

		try {
			resources = (List<CreateCustomerResource>) mongoTemplate.insertAll(createCustomerResource);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_INSERTION_FAILURE.getMessage());
		}

		return resources;
	}

	/**
	 * Method to save single customer into the database
	 * 
	 * @param createCustomerResource
	 */
	@Override
	public CreateCustomerResource saveSingleCustomer(CreateCustomerResource createCustomerResource) {

		log.info("Saving single customer is in progress");

		CreateCustomerResource resource = null;

		try {
			resource = mongoTemplate.insert(createCustomerResource);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_INSERTION_FAILURE.getMessage());
		}

		return resource;
	}

	/**
	 * Method to get all customer from the database
	 * 
	 * @param
	 */
	@Override
	public List<CreateCustomerResource> getCustomer() {

		log.info("Retrieving customer is in progress");

		List<CreateCustomerResource> resources = null;

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("status").ne(Status.DELETED));

			resources = mongoTemplate.find(query, CreateCustomerResource.class);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_SEARCH_FAILURE.getMessage());
		}

		return resources;
	}

	/**
	 * Method to get single customer by name from the database
	 * 
	 * @param  mobileNumber
	 */
	public CreateCustomerResource getCustomerByMobileNumber(String mobileNumber) {

		log.info("Retrieving single customer by mobile number is in progress");

		CreateCustomerResource resource = null;

		try {
			Query query = new Query();
			
			query.addCriteria(Criteria.where("status").ne(Status.DELETED));
			query.addCriteria(Criteria.where("mobileNumber").is(mobileNumber));

			resource = mongoTemplate.findOne(query, CreateCustomerResource.class);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_SEARCH_FAILURE.getMessage());
		}

		return resource;
	}

	/**
	 * Method to get single customer by id from the database
	 * 
	 * @param  id
	 */
	public CreateCustomerResource getCustomerById(String id) {

		log.info("Retrieving single customer by id is in progress");

		CreateCustomerResource resource = null;

		try {
			Query query = new Query();
			
			query.addCriteria(Criteria.where("status").ne(Status.DELETED));
			query.addCriteria(Criteria.where("_id").is(id));

			resource = mongoTemplate.findOne(query, CreateCustomerResource.class);
				
			//resources = mongoTemplate.findById(id, CreateCustomerResource.class);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_SEARCH_FAILURE.getMessage());
		}

		return resource;
	}

	/**
	 * Method to update single customer by name from the database
	 * 
	 * @param createCustomerResource
	 * 
	 */
	@Override
	public CreateCustomerResource updateCustomerByMobileNumber(CreateCustomerResource createCustomerResource) {

		log.info("Updating customer by mobile number is in progress");

		CreateCustomerResource resource = null;

		try {
			Query query = new Query();
 
			query.addCriteria(Criteria.where("status").ne(Status.DELETED));
			query.addCriteria(Criteria.where("mobileNumber").is(createCustomerResource.getCustomerId()));

			resource = buildUpdate(createCustomerResource, query);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_UPDATION_FAILURE.getMessage());
		}

		return resource;
	}

	/**
	 * Method to update single customer by id from the database
	 * 
	 * @param createCustomerResource
	 * 
	 */
	@Override
	public CreateCustomerResource updateCustomerById(CreateCustomerResource createCustomerResource) {

		log.info("Updating customer by id is in progress");

		CreateCustomerResource resource = null;

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("status").ne(Status.DELETED));
			query.addCriteria(Criteria.where("_id").is(createCustomerResource.getCustomerId()));

			resource = buildUpdate(createCustomerResource, query);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_UPDATION_FAILURE.getMessage());
		}

		return resource;
	}

	/**
	 * Method to delete single customer by mobileNumber from the database
	 * 
	 * @param  mobileNumber
	 */
	@Override
	public CreateCustomerResource deleteCustomerByMobileNumber(String mobileNumber) {

		log.info("Deleting customer by mobile number is in progress");

		CreateCustomerResource resource = null;

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("mobileNumber").is(mobileNumber));

			resource = buildDelete(query);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_DELETION_FAILURE.getMessage());
		}

		return resource;
	}

	/**
	 * Method to delete single customer by id from the database
	 * 
	 * @param id
	 */
	@Override
	public CreateCustomerResource deleteCustomerById(String id) {

		log.info("Deleting customer by id is in progress");

		CreateCustomerResource resource = null;

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(id));

			resource = buildDelete(query);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_DELETION_FAILURE.getMessage());
		}

		return resource;
	}

	/**
	 * Method to build update
	 * 
	 * @param createCustomerResource
	 * @param query
	 */
	private CreateCustomerResource buildUpdate(CreateCustomerResource createCustomerResource, Query query) {

		Update update = new Update();

		update.set("name", createCustomerResource.getName());
		update.set("email", createCustomerResource.getEmail());
		update.set("mobileNumber", createCustomerResource.getMobileNumber());
		update.set("status", createCustomerResource.getStatus());

		CreateCustomerResource resource = mongoTemplate.findAndModify(query, update,
				new FindAndModifyOptions().returnNew(true), CreateCustomerResource.class);

		return resource;
	}

	/**
	 * Method to build delete
	 * 
	 * @param query
	 * 
	 */
	private CreateCustomerResource buildDelete(Query query) {

		Update update = new Update();
		update.set("status", Status.DELETED.name());

		CreateCustomerResource resource = mongoTemplate.findAndModify(query, update,
				new FindAndModifyOptions().returnNew(true), CreateCustomerResource.class);

		return resource;
	}
}