package com.tw.example;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;

public class SparkExample {

  public static void run(JavaSparkContext jsp, String inputDir, String outputDir) {
    //write your logic here
  }


  public static void main(String[] args){
    SparkConf sc = new SparkConf().setAppName("WordCount");
    JavaSparkContext jsp = new JavaSparkContext(new SparkContext(sc));

    run(jsp, args[0], args[1]);
  }
}
