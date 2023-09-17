package com.postype.sns.domain.order.application;

import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.order.dto.OrderDto;
import com.postype.sns.domain.order.application.OrderService;
import com.postype.sns.domain.post.dto.PostDto;
import com.postype.sns.domain.post.application.PostService;
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
