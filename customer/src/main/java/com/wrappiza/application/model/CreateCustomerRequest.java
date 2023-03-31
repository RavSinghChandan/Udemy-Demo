package com.wrappiza.application.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class for CreateCustomerRequest
 * 
 * @author Chandan Kumar
 * 
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreateCustomerRequest {

	@Valid
	@NotNull
	private List<CreateCustomerRequestInfo> createCustomerRequestInfos;
}