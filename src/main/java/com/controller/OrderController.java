package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.model.cart.CartService;
import com.model.contents.ContentsDTO;
import com.model.contents.ContentsService;
import com.model.orders.OrderService;
import com.model.orders.OrderdetailDTO;
import com.model.orders.OrdersDTO;

@Controller
public class OrderController {

	@Autowired
	@Qualifier("com.model.orders.OrderServiceImpl")
	private OrderService service;

	@Autowired
	@Qualifier("com.model.contents.ContentsServiceImpl")
	private ContentsService cservice;

	@Autowired
	@Qualifier("com.model.cart.CartServiceImpl")
	private CartService cartservice;

	@PostMapping("/order/create/{str}")// 뷰페이지 만들어서 직접해보기
	public String create(@PathVariable String str, int tot, String payment, String reqtext, HttpServletRequest request,
			HttpSession session) {
		String id = (String) session.getAttribute("id");
		String mname = (String) session.getAttribute("mname");

		OrdersDTO dto = new OrdersDTO();
		dto.setId(id);
		dto.setMname(mname);
		dto.setTotal(tot);
		dto.setPayment(payment);
		dto.setReqtext(reqtext);

		List<OrderdetailDTO> list = new ArrayList<OrderdetailDTO>();

		if (str.equals("cart")) { // 장바구니에서 옴 - 여러개 구매
			String cno = request.getParameter("cno");// 상품번호들
			String qty = request.getParameter("qtys");// 수량들
			String size = request.getParameter("size");// 사이즈들

			String[] no = cno.split(",");
			for (int i = 0; i < no.length; i++) {
				int contentsno = Integer.parseInt(no[i]);
				ContentsDTO cdto = cservice.read(contentsno);
				OrderdetailDTO ddto = new OrderdetailDTO();
				ddto.setContentsno(contentsno);
				ddto.setPname(cdto.getPname());
				ddto.setQuantity(Integer.parseInt(qty.split(",")[i]));
				ddto.setSize(size.split(",")[i]);
				list.add(ddto);
			}

		} else if (str.equals("order")) { // 주문에서 옴 - 1개 구매
			int contentsno = Integer.parseInt(request.getParameter("contentsno"));
			ContentsDTO cdto = cservice.read(contentsno);
			OrderdetailDTO ddto = new OrderdetailDTO();
			ddto.setContentsno(contentsno);
			ddto.setPname(cdto.getPname());
			ddto.setQuantity(Integer.parseInt(request.getParameter("qty")));
			ddto.setSize(request.getParameter("size"));
			list.add(ddto);
		}

		dto.setList(list);

		try {
			service.create(dto);// 주문
			if (str.equals("cart"))
				cartservice.deleteAll(id); // 장바구니 비우기

			return "redirect:/member/mypage";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}
}
