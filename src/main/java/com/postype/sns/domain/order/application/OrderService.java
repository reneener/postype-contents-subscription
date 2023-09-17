package com.postype.sns.domain.order.application;

import com.postype.sns.global.exception.ApplicationException;
import com.postype.sns.global.common.ErrorCode;
import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.order.domain.Order;
import com.postype.sns.domain.order.dto.OrderDto;
import com.postype.sns.domain.order.repository.OrderRepository;
import com.postype.sns.domain.post.domain.Post;
import com.postype.sns.domain.post.dto.PostDto;
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
