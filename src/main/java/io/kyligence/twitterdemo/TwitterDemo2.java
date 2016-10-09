package io.kyligence.twitterdemo;

import com.google.gson.Gson;
import io.kyligence.model.TwitterTuple;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import twitter4j.*;

import java.util.Date;
import java.util.Properties;


@SpringBootApplication
public class TwitterDemo2 {

    public static void main(String[] args) throws Exception{
        ApplicationContext context = SpringApplication.run(TwitterDemo2.class);
        final KafkaSettings kafkaSettings = context.getBean(KafkaSettings.class);
        Properties prop = new Properties();
        prop.put("bootstrap.servers", kafkaSettings.getBrokers());
        prop.put("acks", "0");
        prop.put("value.serializer", StringSerializer.class.getCanonicalName());
        prop.put("key.serializer", StringSerializer.class.getCanonicalName());
        final String topic = "TWITTER_TAG_STREAM";
        final KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(prop);
        final int factor = kafkaSettings.getFactor();
        final Gson gson = new Gson();
        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
                for(int i=0;i<factor;i++){
                    HashtagEntity[] tags = status.getHashtagEntities();
                    if(tags != null && tags.length != 0){
                        for(HashtagEntity tag : tags){
                            TwitterTuple tuple = new TwitterTuple(status);
                            tuple.setHashTag(tag.getText());
                            kafkaProducer.send(new ProducerRecord<String, String>(topic, null, gson.toJson(tuple)));
                        }

                    }

                }
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
