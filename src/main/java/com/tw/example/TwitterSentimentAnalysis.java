package com.tw.example;
import com.tw.helperutils.DictionaryBuilder;
import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.IOException;
import java.util.Map;

public class TwitterSentimentAnalysis {

  private static final Configuration configuration = new Configuration();
  private static Map<String,String> dictionary;
  private static final String RECORD_DELIMITER = "\u0003";

  public static void main(String[] args) throws IOException {
    dictionary = DictionaryBuilder.fetchDictionary();
    Tweet.setSentimentDictionary(dictionary);
    SparkConf sc= new SparkConf().setAppName("Spark - Twitter Sentinment Analysis");
    sc.set("textinputformat.record.delimiter", RECORD_DELIMITER);
    SparkContext sContext = new SparkContext(sc);
    JavaSparkContext jsp = new JavaSparkContext(sContext);
        Map<String,Long> sentimentCountMap = jsp
            .textFile("/tweets/")
            .mapToPair(mapByTweetSentimentToOne()
            ).reduceByKey(summingFunction()).collectAsMap();


    Long positiveCount = sentimentCountMap.get(Tweet.POSITIVE);
    Long negativeCount = sentimentCountMap.get(Tweet.NEGATIVE);
    Long neutralCount = sentimentCountMap.get(Tweet.NEUTRAL);

    System.out.println("Positive: " + positiveCount);
    System.out.println("Negative: " + negativeCount);
    System.out.println("Neutral: "+ neutralCount);

    Double positivePercentage = positiveCount.doubleValue()/(positiveCount + negativeCount + neutralCount);
    Double negativePercentage = negativeCount.doubleValue()/(positiveCount + negativeCount + neutralCount);

    System.out.println("Positive Percentage: " + positivePercentage*100);
    System.out.println("Negative Percentage: " + negativePercentage*100);
  }

  private static Function2<Long, Long, Long> summingFunction() {
    return new Function2<Long, Long, Long>() {
  public Long call(Long v1, Long v2) throws Exception {
    return v1+v2;
  }
};
  }

  private static PairFunction<String, String, Long> mapByTweetSentimentToOne() {
    return new PairFunction<String, String, Long>() {
        public Tuple2<String, Long> call(String tweetText) throws Exception {
          Tweet tweet = Tweet.parse(tweetText);
          return new Tuple2<String, Long>(tweet.getSentiment(), 1L);
        }
      };
  }
}
