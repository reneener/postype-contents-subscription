package com.postype.sns.domain.member.application;

import com.postype.sns.global.exception.ApplicationException;
import com.postype.sns.global.common.ErrorCode;
import com.postype.sns.domain.member.dto.FollowDto;
import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.member.domain.AlarmArgs;
import com.postype.sns.domain.member.domain.AlarmEvent;
import com.postype.sns.domain.member.domain.AlarmType;
import com.postype.sns.domain.member.domain.Follow;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.member.repository.FollowRepository;
import com.postype.sns.domain.member.repository.MemberRepository;
import com.postype.sns.global.utill.AlarmProducer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FollowService {

	private final FollowRepository followRepository;
	private final MemberRepository memberRepository;
	private final AlarmProducer alarmProducer;

	@Transactional
	public FollowDto create(MemberDto fromMemberDto, MemberDto toMemberDto) {

		Member toMember = Member.fromDto(toMemberDto);
		Member fromMember = Member.fromDto(fromMemberDto);

		if(fromMember.getId() == toMember.getId())
			throw new ApplicationException(ErrorCode.MEMBER_IS_SAME);

		Follow newFollow = Follow.builder()
				.fromMember(fromMember)
				.toMember(toMember)
				.build();

		//follow save
		Follow follow = followRepository.save(newFollow);

		alarmProducer.send(new AlarmEvent(toMember.getId(), //알람을 받는 팔로 대상자의 아이디
				AlarmType.NEW_SUBSCRIBE_ON_MEMBER,
				new AlarmArgs(fromMember.getId(), // 알람을 발생시킨 팔로 요청자의 아이디
					"Follow", follow.getId())// 알람이 발생한 팔로우의 아이디
			)
		);

		return FollowDto.fromEntity(follow);
	}

	public Page<FollowDto> getFollowList(MemberDto fromMember, Pageable pageable) {
		Member member = getMemberOrException(fromMember.getMemberId());
		return followRepository.findAllByFromMemberId(member.getId(), pageable).map(FollowDto::fromEntity);
	}
	//해당 멤버를 팔로잉 하고 있는 멤버들의 목록을 반환
	public List<FollowDto> getFollowers(MemberDto toMember){
		Member member = getMemberOrException(toMember.getMemberId());
		return followRepository.findAllByToMemberId(member.getId()).stream()
				.map(FollowDto::fromEntity)
				.collect(Collectors.toList());
	}

	private Member getMemberOrException(String memberId){
		return memberRepository.findByMemberId(memberId).orElseThrow(() ->
			new ApplicationException(ErrorCode.MEMBER_NOT_FOUND, String.format("%s not founded", memberId)));
	}
}
