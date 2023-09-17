package com.postype.sns.domain.post.repository;

import com.postype.sns.domain.post.domain.Post;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	Page<Post> findAllByMemberId(Long memberId, Pageable pageable);
	@Query(nativeQuery = true, value = "SELECT * FROM POST as p WHERE p.id in :ids")
	List<Post> findAllByInId(@Param("ids") List<Long> ids);
	@Query(nativeQuery = true, value = "SELECT * FROM POST as p WHERE p.member_id in :memberIds ORDER BY p.id desc LIMIT :size")
	List<Post> findAllByINMemberIdsAndOrderByIdDesc(@Param("memberIds") List<Long> memberIds, @Param("size") int size);
	@Query(nativeQuery = true, value = "SELECT * FROM POST as p WHERE p.member_id in :memberIds AND p.id < :id ORDER BY p.id desc LIMIT :size")
	List<Post> findAllByLessThanIdAndInMemberIdsAndOrderByIdDesc(@Param("id") Long id, @Param("memberIds") List<Long> memberIds, @Param("size") int size);
	@Query(nativeQuery = true, value = "SELECT * FROM POST as p WHERE p.id in :ids")
	Page<Post> findAllByMember(@Param("ids") List<Long> ids, Pageable pageable);

}
