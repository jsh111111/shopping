package com.model.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("com.model.cart.CartServiceImpl")
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartMapper mapper;

	@Override
	public void deleteAll(String id) {
		mapper.deleteAll(id);
		
	}

}
