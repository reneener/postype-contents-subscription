package com.postype.sns.domain.member.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
@Slf4j
//TODO :: 메시징 큐에 저장해야 함 20:00
public class EmitterRepository {

	private Map<String, SseEmitter> emitterMap = new HashMap<>();
	public SseEmitter save(Long memberId, SseEmitter sseEmitter){
		final String key = getKey(memberId);
		emitterMap.put(key, sseEmitter);
		log.info("set sseEmitter {}", memberId);
		return sseEmitter;
	}
	public void delete(Long memberId){
		log.info("delete sseEmitter {}", memberId);
		emitterMap.remove(getKey(memberId));
	}
	public Optional<SseEmitter> get(Long memberId){
		final String key = getKey(memberId);
		log.info("get sseEmitter {}", memberId);
		return Optional.ofNullable(emitterMap.get(key));
	}
	private String getKey(Long memberId){
		return "Emitter:MemberId:" + memberId;
	}

}
