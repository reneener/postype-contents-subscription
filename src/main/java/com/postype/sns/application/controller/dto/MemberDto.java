package com.postype.sns.application.controller.dto;

import com.postype.sns.domain.member.model.Member;
import com.postype.sns.domain.member.model.MemberRole;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
public class MemberDto implements UserDetails {
	private Long id;
	private String memberId;
	private String password;
	private String memberName;
	private String email;
	private MemberRole role;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;

	public static MemberDto fromEntity(Member member){
		return new MemberDto(
			member.getId(),
			member.getMemberId(),
			member.getPassword(),
			member.getMemberName(),
			member.getEmail(),
			member.getRole(),
			member.getRegisteredAt(),
			member.getUpdatedAt(),
			member.getDeletedAt()
		);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.getRole().toString()));
	}

	@Override
	public String getUsername() {
		return this.getMemberId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.deletedAt == null;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.deletedAt == null;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.deletedAt == null;
	}

	@Override
	public boolean isEnabled() {
		return this.deletedAt == null;
	}
}
