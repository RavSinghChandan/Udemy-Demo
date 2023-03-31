package com.wrappiza.application.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wrappiza.application.model.CreateOrderRequestInfo;
import com.wrappiza.application.model.CreateOrderResource;
import com.wrappiza.application.model.CreateOrderResponse;



/**
 * Mapping DTO's to Object entities and vise-versa
 * 
 * @author Chandan Kumar
 *
 */
@Component
public class OrderServiceMapper {

	/**
	 * Method to build createResource
	 * 
	 * @param createOrderRequestInfo
	 *
	 */
	public List<CreateOrderResource> createResource(List<CreateOrderRequestInfo> createOrderRequestInfo,
			String status) {
		List<CreateOrderResource> resources = new ArrayList<>();

		for (CreateOrderRequestInfo request : createOrderRequestInfo) {

			CreateOrderResource resource = new CreateOrderResource();

			resource.setCustomerName(request.getCustomerName());
			resource.setCustomerAddress(request.getCustomerAddress());
			resource.setItems(request.getItems());
			resource.setTotalPrice(request.getTotalPrice());
			resource.setStatus(status);
			resource.setOrderDeliveryStatus(request.getOrderDeliveryStatus());
			resource.setDate(LocalDate.now());

			resources.add(resource);
		}

		return resources;
	}

	/**
	 * Method to build CreateMenuResponse
	 * 
	 * @param createOrderResources
	 *
	 */
	public CreateOrderResponse buildCreateOrderResponse(List<CreateOrderResource> createOrderResources) {

		CreateOrderResponse createOrderResponse = new CreateOrderResponse();
		createOrderResponse.setCreateOrderResources(createOrderResources);

		return createOrderResponse;
	}
}
