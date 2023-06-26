package com.postype.sns.application.contoller;

import com.postype.sns.application.contoller.dto.MemberDto;
import com.postype.sns.application.contoller.dto.request.OrderCreateRequest;
import com.postype.sns.application.contoller.dto.response.OrderResponse;
import com.postype.sns.application.contoller.dto.response.Response;
import com.postype.sns.application.exception.ApplicationException;
import com.postype.sns.application.exception.ErrorCode;
import com.postype.sns.application.usecase.OrderUseCase;
import com.postype.sns.domain.order.model.Order;
import com.postype.sns.utill.ClassUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final OrderUseCase OrderUseCase;

	@PostMapping("/{postId}")
	public Response<OrderResponse> create(@AuthenticationPrincipal MemberDto memberDto, @PathVariable Long postId){
		return Response.success(
			OrderResponse.fromDto(OrderUseCase.create(memberDto, postId)));
	}
	@GetMapping("/{postId}")
	public Response<OrderResponse> getOrderByMemberAndPost(@AuthenticationPrincipal MemberDto memberDto, @PathVariable Long postId){
		return Response.success(
			OrderResponse.fromDto(OrderUseCase.getOrderByMemberAndPost(memberDto, postId)));
	}
	@GetMapping
	public Response<Page<OrderResponse>> getOrder(@AuthenticationPrincipal MemberDto memberDto, Pageable pageable){
		return Response.success(
			OrderUseCase.getOrder(memberDto, pageable).map(OrderResponse::fromDto));
	}


}
