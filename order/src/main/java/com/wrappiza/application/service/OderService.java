package com.wrappiza.application.service;

import com.wrappiza.application.model.CreateOrderRequest;
import com.wrappiza.application.model.CreateOrderResponse;

/**
 * Interface for order service
 * 
 * @author Chandan Kumar
 *
 */
public interface OderService {

	/**
	 * Method used to create a order logic
	 * 
	 * @param createOrderRequest
	 */
	CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest);
	
	/**
	 * Method used to retrieve all Order logic
	 * 
	 * @param
	 */
	public CreateOrderResponse retrieveOrder();
	
	/**
	 * Method used to retrieve single order logic
	 * 
	 * @param String id,String customerName
	 */
	public CreateOrderResponse getSingleOrder(String id, String customerName);
	
	/**
	 * Method used to update single order logic
	 * 
	 * @param id
	 * @param cutomerName
	 * @param createOrderRequest
	 * 
	 */
	public CreateOrderResponse updateSingleOrder(String id, String customerName, CreateOrderRequest createOrderRequest);

	/**
	 * Method used to delete single order logic
	 * 
	 * @param String id,String customerName
	 */
	public CreateOrderResponse deleteSingleOrder(String id, String customerName);

}
