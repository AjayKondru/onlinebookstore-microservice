package com.org.mybookstore.api.order;

import java.util.Date;

public record OrderResponse(Long id, Date orderDate,double totalAmount,String status) {

}
