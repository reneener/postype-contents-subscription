package com.postype.sns.domain.member.repository;

import com.postype.sns.domain.member.domain.Follow;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
	Page<Follow> findAllByFromMemberId(Long id, Pageable pageable);
	List<Follow> findAllByToMemberId(Long id);
}
