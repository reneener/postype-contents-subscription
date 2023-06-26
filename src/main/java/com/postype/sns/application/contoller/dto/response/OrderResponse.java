package com.postype.sns.application.contoller.dto.response;


import com.postype.sns.application.contoller.dto.OrderDto;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderResponse {

	private Long id;
	private String memberId;
	private Long postId;
	private int value;
	private Timestamp orderAt;

	public static OrderResponse fromDto(OrderDto order){
		return new OrderResponse(
			order.getId(),
			order.getMemberId(),
			order.getPostId(),
			order.getPrice(),
			order.getRegisterAt()
		);
	}

}
