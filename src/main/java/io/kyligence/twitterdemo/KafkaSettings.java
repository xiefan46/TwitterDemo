package io.kyligence.twitterdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by liuyiming on 6/23/16.
 */
@Configuration
public class KafkaSettings {
    @Value("${kafka.brokers}")
    private String brokers;

    @Value("${kafka.topic}")
    private String topic;


    public String getBrokers() {
        return brokers;
    }

    public String getTopic() {
        return topic;
    }


}
