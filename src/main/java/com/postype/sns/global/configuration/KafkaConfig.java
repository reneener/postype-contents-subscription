package com.postype.sns.global.configuration;

import com.postype.sns.domain.member.application.AlarmService;
import com.postype.sns.domain.member.domain.AlarmEvent;
import com.postype.sns.global.utill.AlarmConsumer;
import com.postype.sns.global.utill.AlarmProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
@ConditionalOnProperty(value = "kafka.enabled", matchIfMissing = true)
@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaTemplate<Long, AlarmEvent> kafkaTemplate;

    @Autowired
    private AlarmService alarmService;

    @Bean
    public AlarmProducer AlarmProducer(){
        return new AlarmProducer(kafkaTemplate);
    }

    @Bean
    public AlarmConsumer AlarmConsumer(){
        return new AlarmConsumer(alarmService);
    }
}
