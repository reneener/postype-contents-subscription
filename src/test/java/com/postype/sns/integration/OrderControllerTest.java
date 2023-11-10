package com.postype.sns.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.postype.sns.domain.order.application.OrderUseCase;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.order.dto.OrderDto;
import com.postype.sns.domain.post.domain.Post;
import com.postype.sns.fixture.MemberFixture;
import com.postype.sns.fixture.OrderFixture;
import com.postype.sns.fixture.PostFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderUseCase orderUseCase;

	@Test
	@DisplayName("주문 성공 테스트")
	@WithMockUser
	void orderCreateSuccess() throws Exception {
		Member buyer = MemberFixture.get();
		Post post = PostFixture.get();

		when(orderUseCase.create(any(), any())).thenReturn(OrderDto.fromEntity(OrderFixture.get(buyer, post)));

		mockMvc.perform(post("/api/v1/orders/1")
			.contentType(MediaType.APPLICATION_JSON)
		).andDo(print())
			.andExpect(status().isOk());
	}
	@Test
	@WithAnonymousUser //인증 되지 않은 유저
	@DisplayName("주문 요청시 로그인을 하지 않은 경우 실패 테스트")
	void orderCreateFailCausedByNotLogin() throws Exception {
		mockMvc.perform(post("/api/v1/order/1")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}
}
