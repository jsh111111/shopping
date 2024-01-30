package com.model.orders;

public interface OrderMapper {

	int createOrder(OrdersDTO dto);
	
	void createDetail(OrderdetailDTO odto);
}
