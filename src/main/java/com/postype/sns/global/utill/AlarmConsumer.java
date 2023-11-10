package com.postype.sns.global.utill;

import com.postype.sns.domain.member.domain.AlarmEvent;
import com.postype.sns.domain.member.application.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@RequiredArgsConstructor
@Slf4j
public class AlarmConsumer {
	private final AlarmService alarmService;

	@KafkaListener(topics = "${spring.kafka.template.default-topic}")
	public void consume(AlarmEvent event, Acknowledgment ack){
		log.info("Consume the event {}", event);
		alarmService.send(event.getType(), event.getArgs(), event.getReceiveMemberId());
		ack.acknowledge();
	}

}
