package com.postype.sns.domain.post.repository;

import com.postype.sns.domain.post.domain.Comment;
import com.postype.sns.domain.post.domain.Post;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	Page<Comment> findAllByPost(Post post, Pageable pageable);
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE Comment entity SET deleted_at = NOW() WHERE entity.post_id = :post")
	void deleteAllByPost(@Param("post") Post post);
}
