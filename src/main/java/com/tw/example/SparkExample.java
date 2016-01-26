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
    System.out.println("Taking input dir: " + inputDir);
    JavaRDD<String> textFileJavaRdd = jsp.textFile(inputDir);

    JavaPairRDD<String, Long> wordCountJavaRdd = textFileJavaRdd
      .flatMap(splitLineBySpace())
      .mapToPair(createPairOfWordAsKeyAndOneAsValue())
      .reduceByKey(combineBySummingValue());

    System.out.println("Writing to output dir: " + outputDir);
    wordCountJavaRdd.saveAsTextFile(outputDir);
  }

  private static FlatMapFunction<String, String> splitLineBySpace() {
    return new FlatMapFunction<String, String>() {
      public Iterable<String> call(String line) throws Exception {
        return Arrays.asList(line.split(" "));
      }
    };
  }

  private static PairFunction<String, String, Long> createPairOfWordAsKeyAndOneAsValue() {
    return new PairFunction<String, String, Long>() {
      public Tuple2<String, Long> call(String word) throws Exception {
        return new Tuple2<String, Long>(word, 1L);
      }
    };
  }

  private static Function2<Long, Long, Long> combineBySummingValue() {
    return new Function2<Long, Long, Long>() {
      public Long call(Long v1, Long v2) throws Exception {
        return v1 + v2;
      }
    };
  }

  public static void main(String[] args){
    SparkConf sc = new SparkConf().setAppName("WordCount");
    JavaSparkContext jsp = new JavaSparkContext(new SparkContext(sc));

    run(jsp, args[0], args[1]);
  }
}
