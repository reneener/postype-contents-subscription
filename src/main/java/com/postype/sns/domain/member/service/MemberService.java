package com.postype.sns.domain.member.service;

import com.postype.sns.application.controller.dto.AlarmDto;
import com.postype.sns.application.exception.ErrorCode;
import com.postype.sns.application.exception.ApplicationException;
import com.postype.sns.application.controller.dto.MemberDto;
import com.postype.sns.domain.member.model.Member;
import com.postype.sns.domain.member.repository.AlarmRepository;
import com.postype.sns.domain.member.repository.MemberRepository;
import com.postype.sns.utill.JwtTokenUtils;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final AlarmRepository alarmRepository;
	private final BCryptPasswordEncoder encoder;


	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.token.expired-time-ms}")
	private Long expiredTimeMs;

	public MemberDto loadMemberByMemberId(String memberId){
		return MemberDto.fromEntity(getMemberOrException(memberId));
	}

	public MemberDto getMember(String fromMemberId){
		Member member = getMemberOrException(fromMemberId);
		return MemberDto.fromEntity(member);
	}

	@Transactional
	public MemberDto register(String memberId, String password, String memberName, String email) {

		memberRepository.findByMemberId(memberId).ifPresent(it -> {
			throw new ApplicationException(ErrorCode.DUPLICATED_MEMBER_ID);
		});

		Member savedMember = memberRepository.save(Member.of(memberId, encoder.encode(password), memberName, email));

		return MemberDto.fromEntity(savedMember);
	}

	public String login(String memberId, String password) {
		Member member = getMemberOrException(memberId);

		//if(!member.getPassword().equals(password)) 인코딩 전
		if(!encoder.matches(password, member.getPassword()))
			throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

		//token
		String token = JwtTokenUtils.generateToken(memberId, secretKey, expiredTimeMs);

		return token;
	}

	public Page<AlarmDto> getAlarmList(MemberDto memberDto, Pageable pageable){
		return alarmRepository.findAllByMemberId(memberDto.getId(), pageable).map(AlarmDto::fromEntity);
	}
	private Member getMemberOrException(String memberId){
		return memberRepository.findByMemberId(memberId).orElseThrow(() ->
			new ApplicationException(ErrorCode.MEMBER_NOT_FOUND, String.format("%s not founded", memberId)));
	}

}
