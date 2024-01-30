package com.model.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("com.model.orders.OrderServiceImpl")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper mapper;

	@Override
	public void create(OrdersDTO dto) throws Exception {
		mapper.createOrder(dto); //order 정보 저장
		int orderno = dto.getOrderno(); // 생성된 orderno가 DTO로 저장된다.
		System.out.println(orderno);
		List<OrderdetailDTO> list = dto.getList();

		for (int i = 0; i < list.size(); i++) {
			OrderdetailDTO odto = list.get(i);
			odto.setOrderno(orderno); // 주문detail에저장할 주문번호
			System.out.println(odto);
			mapper.createDetail(odto); // order deail 저장
		}
	}

}
