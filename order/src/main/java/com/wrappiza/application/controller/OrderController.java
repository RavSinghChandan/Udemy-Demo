package com.wrappiza.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wrappiza.application.model.CreateOrderRequest;
import com.wrappiza.application.model.CreateOrderResponse;
import com.wrappiza.application.service.OrderServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/wrappiza")
@Slf4j
public class OrderController {

	@Autowired
	private OrderServiceImpl service;

	OrderController(OrderServiceImpl service) {
		this.service = service;
	}

	/**
	 * API used for creating a order service
	 * 
	 * @param createOrderRequest
	 */
	@PostMapping(value = "/addorder")
	public ResponseEntity<CreateOrderResponse> addCustomer(@RequestBody CreateOrderRequest createOrderRequest) {

		log.info("Add order is in progress");

		CreateOrderResponse response = service.createOrder(createOrderRequest);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * API used for retrieving all order
	 * 
	 * @param
	 */
	@GetMapping(value = "/order")
	@Tag(name = "Get Order", description = "All Order is retrieved")
	public ResponseEntity<CreateOrderResponse> retrieveAll() {

		log.info("Retrieve all order is in progress");

		CreateOrderResponse response = service.retrieveOrder();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * API used for retrieving single order by order Id and customer name
	 * 
	 * @param String id,String customerName
	 */
	@GetMapping(value = "/singleorder")
	@Tag(name = "Get Single Order", description = "Single order is retrieved")
	public ResponseEntity<CreateOrderResponse> retrieveSingleOrder(@RequestParam(required = false) String id,
			@RequestParam(required = false) String customerName) {

		log.info("Retrieve single order is in progress");

		CreateOrderResponse response = service.getSingleOrder(id, customerName);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * API used for updating single order by order Id or customer name
	 * 
	 * @param String id,String customerName
	 */
	@PutMapping(value = "/updateorder")
	@Tag(name = "Order Updation", description = "Single order is updated")
	public ResponseEntity<CreateOrderResponse> updateOrder(@RequestBody CreateOrderRequest createOrderRequest,
			@RequestParam(required = false) String id, @RequestParam(required = false) String customerName) {

		log.info("Update order is in progress");

		CreateOrderResponse response = service.updateSingleOrder(id, customerName, createOrderRequest);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * API used for deleting single order by order Id or customer name
	 * 
	 * @param String id,String customerName
	 */
	@DeleteMapping(value = "/deleteorder")
	@Tag(name = "Order Deletion", description = "Single order is deleted")
	public ResponseEntity<CreateOrderResponse> deleteMenu(@RequestParam(required = false) String id,
			@RequestParam(required = false) String customerName) {

		log.info("Delete order is in progress");

		CreateOrderResponse response = service.deleteSingleOrder(id, customerName);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}