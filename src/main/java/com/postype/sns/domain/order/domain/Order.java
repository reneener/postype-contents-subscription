package com.postype.sns.domain.order.domain;

import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.post.domain.Post;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="post_id")
	private Post post;
	@Column(name="register_at")
	private Timestamp registerAt;

	public static Order of(Member member, Post post){
		Order order = new Order();
		order.setMember(member);
		order.post = post;
		return order;
	}
	public void setMember(Member member){
		if(this.member != null){
			this.member.getOrders().remove(this);
		}
		this.member = member;
		member.getOrders().add(this);
	}
	@PrePersist
	void orderAt() {
		this.registerAt = Timestamp.from(Instant.now());
	}
}
