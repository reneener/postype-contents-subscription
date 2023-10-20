package com.postype.sns.domain.member.dto;

import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.member.domain.MemberRole;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class MemberDto implements UserDetails {
	private final Long id;
	private final String memberId;
	private final String password;
	private final String memberName;
	private final String email;
	private final MemberRole role;
	private final LocalDateTime registerAt;
	private final LocalDateTime updatedAt;
	private final Boolean isDeleted;

	@Builder
	public MemberDto(Long id, String memberId, String password, String memberName, String email, MemberRole role,
					 LocalDateTime registerAt, LocalDateTime updatedAt, Boolean isDeleted) {
		this.id = id;
		this.memberId = memberId;
		this.password = password;
		this.memberName = memberName;
		this.email = email;
		this.role = role;
		this.registerAt = registerAt;
		this.updatedAt = updatedAt;
		this.isDeleted = isDeleted;
	}

	public static MemberDto fromEntity(Member member){
		return MemberDto.builder()
				.id(member.getId())
				.password(member.getPassword())
				.memberId(member.getMemberId())
				.memberName(member.getMemberName())
				.email(member.getEmail())
				.role(member.getRole())
				.registerAt(member.getRegisteredAt())
				.updatedAt(member.getUpdatedAt())
				.isDeleted(member.getIsDeleted())
				.build();
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
		return !this.isDeleted;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.isDeleted;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !this.isDeleted;
	}

	@Override
	public boolean isEnabled() {
		return !this.isDeleted;
	}
}
