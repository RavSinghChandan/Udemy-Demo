package com.wrappiza.application.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class for CreateOrderRequestInfo
 * 
 * @author Chandan Kumar
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class CreateOrderRequestInfo {

	 
    private String customerName;
    private String customerAddress;
    private List<String> items;
    private double totalPrice;
    private String orderDeliveryStatus;
    
	 
}