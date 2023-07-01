package com.postype.sns.domain.order.service;

import com.postype.sns.application.exception.ApplicationException;
import com.postype.sns.application.exception.ErrorCode;
import com.postype.sns.application.controller.dto.MemberDto;
import com.postype.sns.domain.member.model.Member;
import com.postype.sns.domain.order.model.Order;
import com.postype.sns.application.controller.dto.OrderDto;
import com.postype.sns.domain.order.repository.OrderRepository;
import com.postype.sns.domain.post.model.Post;
import com.postype.sns.application.controller.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;


	public OrderDto create(MemberDto memberDto, PostDto post){
		orderRepository.findAllByMemberIdAndPostId(memberDto.getId(), post.getId()).ifPresent(it -> {
			throw new ApplicationException(ErrorCode.ALREADY_ORDER,
				String.format("%s already ordered this post", memberDto.getMemberId()));
		});
		Member member = Member.fromDto(memberDto);

		Order order = orderRepository.save(Order.of(member, Post.of(post)));

		return OrderDto.fromEntity(order);
	}

	public OrderDto findByMemberIdAndPostId(MemberDto member, PostDto post) {
		Order order = orderRepository.findByMemberIdAndPostId(member.getId(), post.getId());
		return OrderDto.fromEntity(order);
	}

	public Page<OrderDto> findAllByMember(MemberDto member, Pageable pageable) {
		return orderRepository.findAllByMember(member, pageable);
	}
}
