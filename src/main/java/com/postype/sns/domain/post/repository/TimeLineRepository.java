package com.postype.sns.domain.post.repository;

import com.postype.sns.domain.post.domain.TimeLine;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeLineRepository extends JpaRepository<TimeLine, Long> {
	@Query(nativeQuery = true, value = "SELECT * FROM TIMELINE as p WHERE p.member_id = :memberId ORDER BY p.id desc LIMIT :size")
	List<TimeLine> findAllByMemberIdAndOrderByIdDesc(@Param("memberId") Long memberId, @Param("size") int size);
	@Query(nativeQuery = true, value = "SELECT * FROM TIMELINE as p WHERE p.member_id = :memberId AND p.id < :id ORDER BY p.id desc LIMIT :size")
	List<TimeLine> findAllByLessThanIdAndMemberIdAndOrderByIdDesc(@Param("id") Long id, @Param("memberId") Long memberId, @Param("size") int size);
}
