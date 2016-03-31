package com.tw.example;

import com.tw.utils.SentimentDictionary;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

public class SparkExample {

  public static void run(JavaSparkContext jsp, String inputDir, String outputDir) {
    final String regex = "|\u0001";
    JavaRDD<String> stringJavaRDD = jsp.textFile(inputDir);
    JavaRDD<Tuple2<String, Integer>> tweetSentiment = stringJavaRDD.map(new Function<String, Tuple2<String, Integer>>() {
      public Tuple2<String, Integer> call(String fullTweet) throws Exception {
        String tweetText = fullTweet.split(regex)[3];
        String[] split = tweetText.split(" ");
        Integer sentimentValue = 0;
        for (String s : split) {
          sentimentValue += SentimentDictionary.getInstance().getSentiment(s);
        }
        return new Tuple2(fullTweet, sentimentValue);
      }
    });
    tweetSentiment.map(new Function<Tuple2<String,Integer>, String>() {
      public String call(Tuple2<String, Integer> v1) throws Exception {
        return v1._1().toString() + regex + v1._2().toString();
      }
    }).saveAsTextFile(outputDir);
  }


  public static void main(String[] args){
    SparkConf sc = new SparkConf().setAppName("WordCount");
    JavaSparkContext jsp = new JavaSparkContext(new SparkContext(sc));
    run(jsp, args[0], args[1]);
  }
}
