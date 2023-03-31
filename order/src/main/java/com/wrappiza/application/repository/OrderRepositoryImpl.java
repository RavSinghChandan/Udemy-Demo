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
import com.wrappiza.application.model.CreateOrderResource;
import com.wrappiza.application.model.Status;

import lombok.extern.slf4j.Slf4j;

/**
 * Implemented class handling order repository
 * 
 * @author Chandan Kumar
 *
 */
@Repository
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public OrderRepositoryImpl(MongoTemplate mongoTemplate) {
		super();
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * Method to save multiple order into the database
	 * 
	 * @param createOrderResource
	 */
	@Override
	public List<CreateOrderResource> saveOrder(List<CreateOrderResource> createOrderResource) {

		log.info("Saving add order is in progress");

		List<CreateOrderResource> resources = null;

		try {
			resources = (List<CreateOrderResource>) mongoTemplate.insertAll(createOrderResource);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_INSERTION_FAILURE.getMessage());
		}

		return resources;
	}

	/**
	 * Method to save single order into the database
	 * 
	 * @param createOrderResource
	 */
	@Override
	public CreateOrderResource saveSingleOrder(CreateOrderResource createOrderResource) {

		log.info("Saving single order is in progress");

		CreateOrderResource resource = null;

		try {
			resource = mongoTemplate.insert(createOrderResource);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_INSERTION_FAILURE.getMessage());
		}

		return resource;
	}

	/**
	 * Method to get all order from the database
	 * 
	 * @param
	 */
	@Override
	public List<CreateOrderResource> getOrder() {

		log.info("Retrieving order is in progress");

		List<CreateOrderResource> resources = null;

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("status").ne(Status.DELETED));

			resources = mongoTemplate.find(query, CreateOrderResource.class);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_SEARCH_FAILURE.getMessage());
		}

		return resources;
	}

	/**
	 * Method to get single order by customerName from the database
	 * 
	 * @param String customerName
	 */
	public CreateOrderResource getOrderByCustomerName(String customerName) {

		log.info("Retrieving single order by customerName is in progress");

		CreateOrderResource resource = null;

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("status").ne(Status.DELETED));
			query.addCriteria(Criteria.where("customerName").is(customerName));

			resource = mongoTemplate.findOne(query, CreateOrderResource.class);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_SEARCH_FAILURE.getMessage());
		}

		return resource;
	}

	/**
	 * Method to get single order by id from the database
	 * 
	 * @param String id
	 */
	public CreateOrderResource getOrderById(String id) {

		log.info("Retrieving single order by id is in progress");

		CreateOrderResource resource = null;

		try {
			Query query = new Query();

			query.addCriteria(Criteria.where("status").ne(Status.DELETED));
			query.addCriteria(Criteria.where("_id").is(id));

			resource = mongoTemplate.findOne(query, CreateOrderResource.class);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_SEARCH_FAILURE.getMessage());
		}

		return resource;
	}

	/**
	 * Method to update single order by customerName from the database
	 * 
	 * @param CreateOrderResource createOrderResource
	 * 
	 */
	@Override
	public CreateOrderResource updateOrderByCustomerName(CreateOrderResource createOrderResource) {

		log.info("Updating order by customer name is in progress");

		CreateOrderResource resource = null;

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("status").ne(Status.DELETED));
			query.addCriteria(Criteria.where("customerName").is(createOrderResource.getOrderId()));

			resource = buildUpdate(createOrderResource, query);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_UPDATION_FAILURE.getMessage());
		}

		return resource;
	}

	/**
	 * Method to update single order by id from the database
	 * 
	 * @param CreateOrderResource createOrderResource
	 * 
	 */
	@Override
	public CreateOrderResource updateOrderById(CreateOrderResource createOrderResource) {

		log.info("Updating order by id is in progress");

		CreateOrderResource resource = null;

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("status").ne(Status.DELETED));
			query.addCriteria(Criteria.where("_id").is(createOrderResource.getOrderId()));

			resource = buildUpdate(createOrderResource, query);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_UPDATION_FAILURE.getMessage());
		}

		return resource;
	}

	/**
	 * Method to delete single order by customerName from the database
	 * 
	 * @param String customerName
	 */
	@Override
	public CreateOrderResource deleteOrderByCustomerName(String customerName) {

		log.info("Deleting order by customerName is in progress");

		CreateOrderResource resource = null;

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("customerName").is(customerName));

			resource = buildDelete(query);
		} catch (Exception ex) {
			throw new TerminalDBException(ServiceErrorCode.DB_DELETION_FAILURE.getMessage());
		}

		return resource;
	}

	/**
	 * Method to delete single order by id from the database
	 * 
	 * @param String id
	 */
	@Override
	public CreateOrderResource deleteOrderById(String id) {

		log.info("Deleting order by id is in progress");

		CreateOrderResource resource = null;

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
	 * @param createOrderResource
	 * @param query
	 */
	private CreateOrderResource buildUpdate(CreateOrderResource createOrderResource, Query query) {

		Update update = new Update();
		update.set("customerName", createOrderResource.getCustomerName());
		update.set("customerAddress", createOrderResource.getCustomerAddress());
		update.set("items", createOrderResource.getItems());
		update.set("totalPrice", createOrderResource.getTotalPrice());
		update.set("date", createOrderResource.getDate());
		update.set("orderDeliveryStatus", createOrderResource.getOrderDeliveryStatus());
		update.set("status", createOrderResource.getStatus());

		CreateOrderResource resource = mongoTemplate.findAndModify(query, update,
				new FindAndModifyOptions().returnNew(true), CreateOrderResource.class);

		return resource;
	}

	/**
	 * Method to build delete
	 * 
	 * @param query
	 * 
	 */
	private CreateOrderResource buildDelete(Query query) {

		Update update = new Update();
		update.set("status", Status.DELETED.name());

		CreateOrderResource resource = mongoTemplate.findAndModify(query, update,
				new FindAndModifyOptions().returnNew(true), CreateOrderResource.class);

		return resource;
	}
}
