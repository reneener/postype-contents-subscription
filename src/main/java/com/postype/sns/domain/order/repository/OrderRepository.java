package com.postype.sns.domain.order.repository;

import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.order.dto.OrderDto;
import com.postype.sns.domain.order.domain.Order;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	Order findByMemberIdAndPostId(Long memberId, Long postId);
	Optional<Order> findAllByMemberIdAndPostId(Long memberId, Long postId);
	Page<OrderDto> findAllByMember(MemberDto member, Pageable pageable);
}
