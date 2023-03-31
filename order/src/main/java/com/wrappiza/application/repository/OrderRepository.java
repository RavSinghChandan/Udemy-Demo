package com.wrappiza.application.repository;

import java.util.List;

import com.wrappiza.application.model.CreateOrderResource;

/**
 * Interface for Order Repository
 * 
 * @author Chandan Kumar
 *
 */
public interface OrderRepository {

	/**
	 * Takes the order list and save it to the repository
	 * 
	 * @param createOrderResources
	 */
	List<CreateOrderResource> saveOrder(List<CreateOrderResource> createOrderResources);

	/**
	 * Takes a single order and save it to the repository
	 * 
	 * @param createOrderResource
	 */
	CreateOrderResource saveSingleOrder(CreateOrderResource createOrderResource);

	/**
	 * Retrieve all order saved into the repository
	 * 
	 * @param
	 */
	List<CreateOrderResource> getOrder();

	/**
	 * Retrieve single order saved from the repository by customerName
	 * 
	 * @param String customerName
	 */
	CreateOrderResource getOrderByCustomerName(String customerName);

	/**
	 * Retrieve single order saved from the repository by id
	 * 
	 * @param String id
	 */
	CreateOrderResource getOrderById(String id);

	/**
	 * Update single order saved from the repository by customer name
	 * 
	 * @param createOrderResource
	 * 
	 */
	CreateOrderResource updateOrderByCustomerName(CreateOrderResource createOrderResource);

	/**
	 * Update single order saved from the repository by id
	 * 
	 * @param createOrderResource
	 * 
	 */
	CreateOrderResource updateOrderById(CreateOrderResource createOrderResource);

	/**
	 * Delete single order saved from the repository by customerName
	 * 
	 * @param String customerName
	 */
	CreateOrderResource deleteOrderByCustomerName(String customerName);

	/**
	 * Delete single order saved from the repository by id
	 * 
	 * @param String id
	 */
	CreateOrderResource deleteOrderById(String id);

}