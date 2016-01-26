package com.tw.example;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.spark.*;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.List;

public class SparkExample {

  private static final Configuration configuration = new Configuration();

  public static void main(String[] args){


    System.out.println("Taking input dir: " + args[0]);
    SparkConf sc= new SparkConf().setAppName("MR");
    SparkContext sContext = new SparkContext(sc);
    JavaSparkContext jsp = new JavaSparkContext(sContext);
    long wordCount = jsp.newAPIHadoopFile(args[0], TextInputFormat.class, LongWritable.class,Text.class,configuration).mapValues(getWords()).count();


  }

  private static Function<Text, String> getWords() {
    return new Function<Text, String>() {
      public String call(Text v1) throws Exception {
        System.out.println("Word: " + v1.toString());
        return v1.toString();
      }
    };
  }
}
