package com.wrappiza.application.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wrappiza.application.exception.ServiceErrorCode;
import com.wrappiza.application.exception.ServiceTerminalException;
import com.wrappiza.application.exception.TerminalDBException;
import com.wrappiza.application.model.CreateOrderRequest;
import com.wrappiza.application.model.CreateOrderResource;
import com.wrappiza.application.model.CreateOrderResponse;
import com.wrappiza.application.model.Status;
import com.wrappiza.application.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Implemented class handling order service
 * 
 * @author Chandan Kumar
 *
 */
@Service
@Slf4j
public class OrderServiceImpl implements OderService {

	@Autowired
	private OrderRepository repo;

	@Autowired
	private OrderServiceMapper mapper;

	public OrderServiceImpl(OrderRepository repo, OrderServiceMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	/**
	 * Saving order to the repository
	 * 
	 * @param createOrderRequest
	 */
	@Override
	public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {

		log.debug("Request of CreateOrderRequest : ", createOrderRequest);

		CreateOrderResponse createOrderResponse = null;

		try {
			// When request having more than one order

			if (createOrderRequest.getCreateOrderRequestInfos().size() > 1) {
				List<CreateOrderResource> resources = repo.saveOrder(
						mapper.createResource(createOrderRequest.getCreateOrderRequestInfos(), Status.CREATED.name()));
				createOrderResponse = mapper.buildCreateOrderResponse(resources);
			} else {
				List<CreateOrderResource> resources = mapper
						.createResource(createOrderRequest.getCreateOrderRequestInfos(), Status.CREATED.name());
				CreateOrderResource resource = repo.saveSingleOrder(resources.get(0));
				resources.clear();
				resources.add(resource);
				createOrderResponse = mapper.buildCreateOrderResponse(resources);
			}

		} catch (TerminalDBException ex) {

			log.error("Exception occured while saving add order : ", ex);

			throw new ServiceTerminalException(ServiceErrorCode.DB_INSERTION_FAILURE);
		}

		log.debug("Response of createOrderResponse : ", createOrderResponse);

		return createOrderResponse;
	}

	/**
	 * Retrieving all order from the repository
	 * 
	 * @param
	 */
	@Override
	public CreateOrderResponse retrieveOrder() {

		List<CreateOrderResource> createOrderResource = null;
		CreateOrderResponse response = null;

		try {
			createOrderResource = repo.getOrder();

			response = mapper.buildCreateOrderResponse(createOrderResource);

		} catch (TerminalDBException ex) {

			log.error("Exception occured while retrieving order : ", ex);

			throw new ServiceTerminalException(ServiceErrorCode.DB_SEARCH_FAILURE);
		}

		log.debug("Response of retrieve order : ", response);

		return response;
	}

	/**
	 * Retrieving single order from the repository
	 * 
	 * @param Integer id,String customerName
	 */
	@Override
	public CreateOrderResponse getSingleOrder(String id, String customerName) {

		log.debug("Id : {} and customerName : {} of retrieving single order: ", id, customerName);

		CreateOrderResource createOrderResource = null;
		CreateOrderResponse response = null;

		try {
			if (StringUtils.isNotBlank(id)) {
				createOrderResource = repo.getOrderById(id);

			} else if (StringUtils.isNotBlank(customerName)) {
				createOrderResource = repo.getOrderByCustomerName(customerName);

			} else {
				throw new ServiceTerminalException(ServiceErrorCode.EMPTY_SEARCH_FAILURE);
			}
			List<CreateOrderResource> resources = new ArrayList<>();
			resources.add(createOrderResource);
			response = mapper.buildCreateOrderResponse(resources);

		} catch (TerminalDBException ex) {

			log.error("Exception occured while retrieving single order : ", ex);

			throw new ServiceTerminalException(ServiceErrorCode.DB_SEARCH_FAILURE);
		}

		log.debug("Response of retrieve single order : ", response);

		return response;
	}

	/**
	 * Updating single order from the repository
	 * 
	 * @param Integer id
	 * @param String  customerName
	 * 
	 */
	@Override
	public CreateOrderResponse updateSingleOrder(String id, String customerName,
			CreateOrderRequest createOrderRequest) {

		log.debug(" Id : {} and customerName : {} of updating single order with request : {}", id, customerName,
				createOrderRequest);

		CreateOrderResponse response = null;
		CreateOrderResource createOrderResource = null;

		try {
			if (StringUtils.isNotBlank(id)) {
				List<CreateOrderResource> createOrderResourceOne = mapper
						.createResource(createOrderRequest.getCreateOrderRequestInfos(), Status.UPDATED.name());
				createOrderResourceOne.get(0).setOrderId(id);

				createOrderResource = repo.updateOrderById(createOrderResourceOne.get(0));

			} else if (StringUtils.isNotBlank(customerName)) {
				List<CreateOrderResource> createOrderResourceOne = mapper
						.createResource(createOrderRequest.getCreateOrderRequestInfos(), Status.UPDATED.name());
				createOrderResourceOne.get(0).setOrderId(customerName);
				createOrderResource = repo.updateOrderByCustomerName(createOrderResourceOne.get(0));

			} else {
				throw new ServiceTerminalException(ServiceErrorCode.DB_UPDATION_FAILURE);
			}

			List<CreateOrderResource> resources = new ArrayList<>();
			resources.add(createOrderResource);

			response = mapper.buildCreateOrderResponse(resources);

		} catch (TerminalDBException ex) {

			log.error("Exception occured while updating single order : ", ex);

			throw new ServiceTerminalException(ServiceErrorCode.DB_UPDATION_FAILURE);
		}

		log.debug("Response of update single order : ", response);

		return response;
	}

	/**
	 * Deleting single order from the repository
	 * 
	 * @param Integer id,String customerName
	 */
	@Override
	public CreateOrderResponse deleteSingleOrder(String id, String customerName) {

		log.debug(" Id : {} and customerName : {} of deleting single order ", id, customerName);

		CreateOrderResponse response = null;
		CreateOrderResource createOrderResource = null;

		try {
			if (StringUtils.isNotBlank(id)) {

				createOrderResource = repo.deleteOrderById(id);
			} else if (StringUtils.isNotBlank(customerName)) {

				createOrderResource = repo.deleteOrderByCustomerName(customerName);
			} else {

				throw new ServiceTerminalException(ServiceErrorCode.DB_DELETION_FAILURE);
			}

			List<CreateOrderResource> resources = new ArrayList<>();
			resources.add(createOrderResource);

			response = mapper.buildCreateOrderResponse(resources);

		} catch (TerminalDBException ex) {

			log.error("Exception occured while deleting single order : ", ex);

			throw new ServiceTerminalException(ServiceErrorCode.DB_DELETION_FAILURE);
		}

		log.debug("Response of delete single order : ", response);

		return response;
	}

}
