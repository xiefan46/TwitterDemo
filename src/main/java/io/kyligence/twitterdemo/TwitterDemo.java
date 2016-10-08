package io.kyligence.twitterdemo;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.util.Properties;

/**
 * Created by liuyiming on 6/23/16.
 */
@SpringBootApplication
public class TwitterDemo {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TwitterDemo.class);
        KafkaSettings kafkaSettings = context.getBean(KafkaSettings.class);
        Properties prop = new Properties();
        prop.put("bootstrap.servers", kafkaSettings.getBrokers());
        prop.put("acks", "0");
        prop.put("value.serializer", StringSerializer.class.getCanonicalName());
        prop.put("key.serializer", StringSerializer.class.getCanonicalName());
        final String topic = kafkaSettings.getTopic();
        final KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(prop);

        final Gson gson = new Gson();
        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
                kafkaProducer.send(new ProducerRecord<String, String>(topic, null, gson.toJson(status)));
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

            public void onScrubGeo(long l, long l1) {

            }

            public void onStallWarning(StallWarning stallWarning) {

            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(listener);
        // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.sample();
    }
}
