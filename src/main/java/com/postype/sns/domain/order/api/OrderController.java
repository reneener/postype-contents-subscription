package com.postype.sns.domain.order.api;

import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.order.dto.OrderResponse;
import com.postype.sns.global.common.Response;
import com.postype.sns.domain.order.application.OrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final OrderUseCase OrderUseCase;
	@Operation(summary = "포스트 구입", description = "로그인한 멤버가 postId에 해당하는 포스트를 구입합니다")
	@PostMapping("/{postId}")
	public Response<OrderResponse> create(@ApiIgnore  @AuthenticationPrincipal MemberDto memberDto, @PathVariable Long postId){
		return Response.success(
			OrderResponse.fromDto(OrderUseCase.create(memberDto, postId)));
	}
	@Operation(summary = "포스트 구입 내역 확인하기", description = "로그인한 멤버가 구입한 postId의 구입내역을 가져옵니다")
	@GetMapping("/{postId}")
	public Response<OrderResponse> getOrderByMemberAndPost(@ApiIgnore @AuthenticationPrincipal MemberDto memberDto, @PathVariable Long postId){
		return Response.success(
			OrderResponse.fromDto(OrderUseCase.getOrderByMemberAndPost(memberDto, postId)));
	}
	@Operation(summary = "멤버의 구입 내역 가져오기", description = "로그인한 멤버의 모든 구입내역을 가져옵니다")
	@GetMapping
	public Response<Page<OrderResponse>> getOrder(@ApiIgnore @AuthenticationPrincipal MemberDto memberDto, Pageable pageable){
		return Response.success(
			OrderUseCase.getOrder(memberDto, pageable).map(OrderResponse::fromDto));
	}


}
