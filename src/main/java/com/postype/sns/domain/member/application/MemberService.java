package com.postype.sns.domain.member.application;

import com.postype.sns.domain.member.dto.AlarmDto;
import com.postype.sns.domain.member.dto.request.MemberLoginRequest;
import com.postype.sns.domain.member.dto.request.MemberRegisterRequest;
import com.postype.sns.global.common.ErrorCode;
import com.postype.sns.global.exception.ApplicationException;
import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.member.repository.AlarmRepository;
import com.postype.sns.domain.member.repository.MemberRepository;
import com.postype.sns.global.utill.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
	public MemberDto register(MemberRegisterRequest request) {

		memberRepository.findByMemberId(request.getMemberId()).ifPresent(it -> {
			throw new ApplicationException(ErrorCode.DUPLICATED_MEMBER_ID);
		});

		Member member = MemberRegisterRequest.getMember(request);
		member.setPassword(encoder.encode(member.getPassword()));

		return MemberDto.fromEntity(memberRepository.save(member));
	}

	public String login(MemberLoginRequest request) {
		Member member = getMemberOrException(request.getMemberId());

		if(!encoder.matches(request.getPassword(), member.getPassword()))
			throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

		return JwtTokenUtils.generateToken(request.getMemberId(), secretKey, expiredTimeMs);
	}

	public Page<AlarmDto> getAlarmList(MemberDto memberDto, Pageable pageable){
		return alarmRepository.findAllByMemberId(memberDto.getId(), pageable).map(AlarmDto::fromEntity);
	}
	private Member getMemberOrException(String memberId){
		return memberRepository.findByMemberId(memberId).orElseThrow(() ->
			new ApplicationException(ErrorCode.MEMBER_NOT_FOUND, String.format("%s not founded", memberId)));
	}

}
