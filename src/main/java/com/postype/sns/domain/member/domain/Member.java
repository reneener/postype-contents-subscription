package com.postype.sns.domain.member.domain;

import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.BaseEntity;
import com.postype.sns.domain.order.domain.Order;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "member_id")
	private String memberId;

	private String password;

	@Column(name = "member_name")
	private String memberName;

	@Column(unique = true)
	private String email;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private MemberRole role = MemberRole.USER;

	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<Order>();

	@Column(name = "is_deleted")
	private Boolean isDeleted = Boolean.FALSE;

	@Builder
	public Member(String memberId, String password, String memberName, String email, MemberRole role){
		this.memberId = memberId;
		this.password = password;
		this.memberName = memberName;
		this.email = email;
		this.role = role;
	}
	public static Member fromDto(MemberDto dto){
		return Member.builder()
				.memberId(dto.getMemberId())
				.memberName(dto.getMemberName())
				.email(dto.getEmail())
				.role(dto.getRole())
				.build();
	}

}
