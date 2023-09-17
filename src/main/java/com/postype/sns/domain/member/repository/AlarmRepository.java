package com.postype.sns.domain.member.repository;

import com.postype.sns.domain.member.domain.Alarm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
	Page<Alarm> findAllByMemberId(Long memberId, Pageable pageable);
}
