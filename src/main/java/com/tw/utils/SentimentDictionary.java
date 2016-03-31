package com.tw.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SentimentDictionary
{
  private Map<String,Integer> map = new HashMap<String, Integer>();

  private static SentimentDictionary instance= null;

  public static SentimentDictionary getInstance() throws IOException {
    if(instance == null){
      instance = buildDictionary();
    }
    return instance;
  }

  private static SentimentDictionary buildDictionary() throws IOException {
    SentimentDictionary sentimentDictionary = new SentimentDictionary();
    BufferedReader br = new BufferedReader(new FileReader(
      new File("/home/tw/data/dictionary.tff"))
    );

    String line;

    while((line = br.readLine()) != null)
    {
      String [] words = line.split(" ");
      sentimentDictionary.map.put(words[0], "y".equals(words[1]) ? 1 : -1);
    }

    return sentimentDictionary;
  }

  public Integer getSentiment(String word){
    if(map.containsKey(word)){
      return map.get(word);
    }

    return 0;
  }
}

