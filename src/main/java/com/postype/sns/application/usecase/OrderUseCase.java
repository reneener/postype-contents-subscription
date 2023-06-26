package com.postype.sns.application.usecase;

import com.postype.sns.application.contoller.dto.MemberDto;
import com.postype.sns.application.exception.ApplicationException;
import com.postype.sns.application.exception.ErrorCode;
import com.postype.sns.domain.member.service.MemberService;
import com.postype.sns.application.contoller.dto.OrderDto;
import com.postype.sns.domain.order.service.OrderService;
import com.postype.sns.application.contoller.dto.PostDto;
import com.postype.sns.domain.post.service.PostService;
import com.postype.sns.utill.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderUseCase {
	private final PostService postService;
	private final OrderService orderService;

	@Transactional
	public OrderDto create(MemberDto member, Long postId){
		PostDto post = postService.getPost(postId);
		return orderService.create(member, post);
	}

	public OrderDto getOrderByMemberAndPost(MemberDto member, Long postId){
		PostDto post = postService.getPost(postId);
		return orderService.findByMemberIdAndPostId(member, post);
	}

	public Page<OrderDto> getOrder(MemberDto member, Pageable pageable) {
		return orderService.findAllByMember(member, pageable);
	}
}
