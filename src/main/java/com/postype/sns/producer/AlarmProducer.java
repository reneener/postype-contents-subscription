package com.postype.sns.producer;

import com.postype.sns.domain.member.model.AlarmEvent;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmProducer {
	private final KafkaTemplate<Long, AlarmEvent> kafkaTemplate;

	@Value("${spring.kafka.template.default-topic}")
	private String topic;

	public void send(AlarmEvent alarmEvent){
		kafkaTemplate.send(topic, alarmEvent.getReceiveMemberId(), alarmEvent); //topic, key, value
		log.info("send to kafka finished");
	}


}
