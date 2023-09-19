package com.postype.sns.domain.member.repository;

import com.postype.sns.domain.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByMemberId(String memberId);
	@Query(nativeQuery = true, value = "SELECT * FROM MEMBER as p WHERE p.id in :ids")
	List<Member> findAllByIds(@Param("ids") List<Long> ids);
}
