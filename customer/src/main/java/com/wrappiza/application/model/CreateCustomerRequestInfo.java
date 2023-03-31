package com.wrappiza.application.model;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class for CreateCustomerRequestInfo
 * 
 * @author Chandan Kumar
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class CreateCustomerRequestInfo {

	@NotBlank(message="Name must not be blank")
	@Length(max=50,message ="Name can't not too long")
	@Length(min=3,message ="Name can't not too small")
	private String name;
	@Email
	@NotBlank(message="Email must not be blank")
	@Length(max=200,message ="Email can't not too long")
	private String email;
	@NotBlank(message="Mobile Number must not be blank")
	@Length(max=10,message ="Mobile Number must be less than 10 digits")
	private String mobileNumber;
}