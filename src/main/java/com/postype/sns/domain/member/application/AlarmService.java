package com.postype.sns.domain.member.application;

import com.postype.sns.global.exception.ApplicationException;
import com.postype.sns.global.common.ErrorCode;
import com.postype.sns.domain.member.domain.Alarm;
import com.postype.sns.domain.member.domain.AlarmArgs;
import com.postype.sns.domain.member.domain.AlarmType;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.member.repository.AlarmRepository;
import com.postype.sns.domain.member.repository.EmitterRepository;
import com.postype.sns.domain.member.repository.MemberRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AlarmService {

	private final static Long DEFAULT_TIMEOUT = 60 * 1000 * 60L;
	private final static String ALARM_NAME = "alarm";

	private final EmitterRepository emitterRepository;
	private final AlarmRepository alarmRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void send(AlarmType type, AlarmArgs args, Long receiveMemberId){
		Member member = memberRepository.findById(receiveMemberId).orElseThrow(()
				-> new ApplicationException(ErrorCode.MEMBER_NOT_FOUND));

		Alarm alarm = alarmRepository.save(Alarm.builder()
						.member(member)
						.type(type)
						.args(args)
						.build());

		emitterRepository.get(receiveMemberId).ifPresentOrElse(sseEmitter -> {
			try{
				sseEmitter.send(SseEmitter.event()
						.id(alarm.getId().toString())
						.name(ALARM_NAME)
						.data("new alarm"));
			}catch (IOException e) {
				emitterRepository.delete(receiveMemberId);
				throw new ApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
			}
		}, () -> log.info("No emitter founded")); //브라우저의 접속 정보가 없을 경우
	}

	public void send(AlarmType type, AlarmArgs args, List<Long> receivedMemberIds){
		List<Alarm> alarmList = new ArrayList<>();
		//alarm save
		for(int i=0; i< receivedMemberIds.size(); i++){
			List<Member> member = memberRepository.findAllByIds(receivedMemberIds);
			alarmList.add(Alarm.builder()
					.member(member.get(i))
					.type(type)
					.args(args)
					.build());
		}
		List<Alarm> alarms = alarmRepository.saveAll(alarmList);

		for(int i=0; i<alarms.size(); i++) {
			Long alarmId = alarms.get(i).getId();
			Long receivedId = receivedMemberIds.get(i);

			emitterRepository.get(receivedId).ifPresentOrElse(sseEmitter -> {
				try {
					sseEmitter.send(SseEmitter.event().id(alarmId.toString()).name(ALARM_NAME)
						.data("new alarm"));
				} catch (IOException e) {
					emitterRepository.delete(receivedId);
					throw new ApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
				}
			}, () -> log.info("No emitter founded")); //브라우저의 접속 정보가 없을 경우
		}
	}

	public SseEmitter connectAlarm(Long memberId){
		SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
		emitterRepository.save(memberId, sseEmitter);
		//재접속
		sseEmitter.onCompletion(() -> emitterRepository.delete(memberId));
		sseEmitter.onTimeout(() -> emitterRepository.delete(memberId));

		try{
			sseEmitter.send(SseEmitter.event().id("").name(ALARM_NAME).data("connect completed"));
		}catch(IOException e){
			throw new ApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
		}
		return sseEmitter;
	}


}
