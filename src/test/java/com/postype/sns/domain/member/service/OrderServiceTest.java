package com.postype.sns.domain.member.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.postype.sns.application.exception.ApplicationException;
import com.postype.sns.application.exception.ErrorCode;
import com.postype.sns.application.contoller.dto.MemberDto;
import com.postype.sns.domain.member.model.Member;
import com.postype.sns.domain.member.repository.MemberRepository;
import com.postype.sns.domain.order.model.Order;
import com.postype.sns.domain.order.repository.OrderRepository;
import com.postype.sns.domain.order.service.OrderService;
import com.postype.sns.domain.post.model.Post;
import com.postype.sns.application.contoller.dto.PostDto;
import com.postype.sns.domain.post.repository.PostRepository;
import com.postype.sns.fixture.MemberFixture;
import com.postype.sns.fixture.OrderFixture;
import com.postype.sns.fixture.PostFixture;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class OrderServiceTest {

	@Autowired
	OrderService orderService;
	@MockBean
	OrderRepository orderRepository;
	@MockBean
	MemberRepository memberRepository;
	@MockBean
	PostRepository postRepository;

	@Test
	@DisplayName("주문 등록 성공 테스트")
	@Transactional
	void orderSuccess(){
		String memberId = "test05";
		String password = "test";
		Member member = MemberFixture.get(memberId, password, 1L);
		Post post = PostFixture.get(memberId, 2L, 1L);
		Order order = Order.of(member, post);

		when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(member));
		when(postRepository.findById(2L)).thenReturn(Optional.of(post));
		when(orderRepository.save(any())).thenReturn(order);

		Assertions.assertDoesNotThrow(() -> orderService.create(MemberDto.fromEntity(member), PostDto.fromPost(post)));
	}
	@Test
	@DisplayName("주문 등록 시 이미 구입한 포스트일 경우 실패 테스트")
	void orderCreateFailCausedByDuplicatedOrder(){
		String memberId = "test05";
		String password = "test";

		Member memberFixture = MemberFixture.get(memberId, password, 1L);
		Post postFixture = PostFixture.get(memberId, 2L, 1L);
		Order orderFixture = OrderFixture.get(memberFixture, postFixture);
		orderFixture.setMember(memberFixture);

		when(orderRepository.save(orderFixture)).thenReturn(orderFixture);
		when(orderRepository.findAllByMemberIdAndPostId(any(), any())).thenReturn(Optional.of(orderFixture));
		when(orderRepository.save(any())).thenReturn(mock(Order.class));

		ApplicationException e = Assertions.assertThrows(ApplicationException.class,
			() -> orderService.create(MemberDto.fromEntity(memberFixture), PostDto.fromPost(postFixture)));
		Assertions.assertEquals(ErrorCode.ALREADY_ORDER, e.getErrorCode());
	}
}
