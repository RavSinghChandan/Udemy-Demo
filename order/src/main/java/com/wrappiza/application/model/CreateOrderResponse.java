package com.wrappiza.application.model;

import lombok.*;

import java.util.List;

/**
 * Model class for CreateOrderResponse
 * 
 * @author Chandan Kumar
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponse {

	private List<CreateOrderResource> createOrderResources;
}