package com.wrappiza.application.model;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class for CreateOrderRequest
 * 
 * @author Chandan Kumar
 * 
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

	@Valid
	@NotNull
	private List<CreateOrderRequestInfo> createOrderRequestInfos;
}