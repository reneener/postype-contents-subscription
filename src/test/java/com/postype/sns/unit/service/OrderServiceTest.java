package com.postype.sns.unit.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.postype.sns.global.exception.ApplicationException;
import com.postype.sns.global.common.ErrorCode;
import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.order.domain.Order;
import com.postype.sns.domain.order.repository.OrderRepository;
import com.postype.sns.domain.order.application.OrderService;
import com.postype.sns.domain.post.domain.Post;
import com.postype.sns.domain.post.dto.PostDto;
import com.postype.sns.fixture.MemberFixture;
import com.postype.sns.fixture.PostFixture;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	@InjectMocks
	OrderService orderService;
	@Mock
	OrderRepository orderRepository;

	@Test
	@DisplayName("주문 등록 성공 테스트")
	@Transactional
	void orderSuccess(){
		Member member = MemberFixture.get();
		Post post = PostFixture.get(member);
		Order order = Order.of(member, post);

		when(orderRepository.findAllByMemberIdAndPostId(any(), any())).thenReturn(Optional.empty());
		when(orderRepository.save(any())).thenReturn(order);

		Assertions.assertDoesNotThrow(() -> orderService.create(MemberDto.fromEntity(member), PostDto.fromPost(post)));
	}
	@Test
	@DisplayName("주문 등록 시 이미 구입한 포스트일 경우 실패 테스트")
	void orderCreateFailCausedByDuplicatedOrder(){
		Member member = MemberFixture.get();
		Post post = PostFixture.get(member);
		Order savedOrder = new Order();

		when(orderRepository.findAllByMemberIdAndPostId(any(), any())).thenReturn(Optional.of(savedOrder));

		ApplicationException e = Assertions.assertThrows(ApplicationException.class,
			() -> orderService.create(MemberDto.fromEntity(member), PostDto.fromPost(post)));
		Assertions.assertEquals(ErrorCode.ALREADY_ORDER, e.getErrorCode());
	}
}
