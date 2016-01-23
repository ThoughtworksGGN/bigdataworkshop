package com.tw.mrexample;


import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Minutes;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.*;
import scala.None;



public class SparkStreamingExample {

  private final static JavaStreamingContext jsc = new JavaStreamingContext("yarn","Twitter-Mine",new Duration(10000));
  public static void main(String[] args) {

    System.setProperty("twitter4j.oauth.consumerKey", "2arj8atjC86JuORqTdinQQLb8");
    System.setProperty("twitter4j.oauth.consumerSecret","rPkYY42pOis9r8mLLRof3sSZOv18LqsHAF6JEq7mDWrTi4vKx0");
    System.setProperty("twitter4j.oauth.accessToken", "4802302332-3vvZYwEHOIlm5hq4TNWtWUfJ9WHcLiyiKqnxXE5");
    System.setProperty("twitter4j.oauth.accessTokenSecret", "NZc4FarjaCIDc7inxijkjFT9Fp1ndz9Jl9h8PuKHkCwwl");
    String[] filters = {};
    JavaReceiverInputDStream dStream = TwitterUtils.createStream(jsc,filters);
    dStream.window(new Duration(10000),new Duration(1000)).map(new Function() {
      public String call(Object v1) throws Exception {
        System.out.println("Tweet: " + v1);
        return v1.toString();
      }
    }).countByValue().saveAsHadoopFiles("/examples/wordcount/","output3");

  }
}
