package com.wrappiza.application.model;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class for CreateOrderResource
 * 
 * @author Chandan Kumar
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Document("order")
public class CreateOrderResource {

	@Id
	private String orderId;
    private String customerName;
    private String customerAddress;
    private List<String> items;
    private double totalPrice;
    private LocalDate  date;
    private String orderDeliveryStatus;
    private String status;

}