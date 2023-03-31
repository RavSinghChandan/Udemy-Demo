package com.wrappiza.application.model;

import lombok.*;

import java.util.List;

/**
 * Model class for CreateCustomerResponse
 * 
 * @author Chandan Kumar
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerResponse {

	private List<CreateCustomerResource> createCustomerResources;
}