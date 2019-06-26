package twitter;


import com.google.gson.Gson;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;

/*

Reference twitter4j tutorial :

http://twitter4j.org/en/code-examples.html
 */
public class TwitterDemo implements Config {

    public static TwitterStream getTwitterInstance() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());
        return tf.getInstance();
    }

    public static void main(String[] args) throws Exception {
        TwitterStream twitterStream = getTwitterInstance();
        final Gson gson = new Gson();
        String outputFile = "twitter_output";
        File outFile = new File(outputFile);
        outFile.deleteOnExit();
        outFile.createNewFile();
        System.out.println(outFile.getAbsolutePath());
        final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
        StatusListener listener = new StatusListener() {
            public void onStatus(Status status)  {
                try{
                    String twitterString = gson.toJson(status).toString();
                    System.out.println(twitterString);
                    bw.write(twitterString + "\n");
                }catch (IOException e){
                    e.printStackTrace();
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
        twitterStream.addListener(listener);
        twitterStream.sample();
        bw.flush();
        bw.close();
    }

}
