package com.postype.sns.domain.order.dto;


import com.postype.sns.domain.order.domain.Order;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDto {
	private Long id;
	private String memberId;
	private Long postId;
	private int price;
	private Timestamp registerAt;
	public static OrderDto fromEntity(Order order){
		return new OrderDto(
			order.getId(),
			order.getMember().getMemberId(),
			order.getPost().getId(),
			order.getPost().getPrice().getValue(),
			order.getRegisterAt()
		);
	}
}
