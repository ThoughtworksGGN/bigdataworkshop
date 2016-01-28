package com.tw.example;

import java.io.IOException;
import java.util.Map;

public class Tweet {

  private String user;
  private String text;
  private String sentiment;
  private static String FIELD_DELIMITER = "\u0001";

  public static void setSentimentDictionary(Map<String, String> sentimentDictionary) {
    Tweet.sentimentDictionary = sentimentDictionary;
  }

  private static Map<String,String> sentimentDictionary;
  public static final String POSITIVE = "positive";
  public static final String NEGATIVE = "negative";
  public static final String NEUTRAL = "neutral";
  public static final String UNKNOWN = "unkown";


  public String getSentiment() {
    return sentiment;
  }

  public String getText() {
    return text;
  }

  public String getUser() {

    return user;
  }

  private Tweet(String user, String text, String sentiment)
  {
    this.user = user;
    this.text = text;

    this.sentiment = sentiment;
  }

  public static Tweet parse(String tweet) throws IOException {
    try {
      String user = tweet.split(FIELD_DELIMITER)[2];
      String text = tweet.split(FIELD_DELIMITER)[3];
      String sentiment = getSentiment(text);
      System.out.println("Tweet: "+ text);
      return new Tweet(user,text,sentiment);
    }catch(Exception e){
      return new Tweet("","",UNKNOWN);
    }

  }

  private static String getSentiment(String text) {
    String[] words = text.split(" ");
    int positive_count=0,negative_count=0,neutral_count = 0;
    for(String word : words) {
      if (sentimentDictionary.containsKey(word))
      {
        String wordSentiment = sentimentDictionary.get(word);
        if(wordSentiment.equals(POSITIVE)) positive_count++;
        else if (wordSentiment.equals(NEGATIVE)) negative_count++;
        else neutral_count++;
      }

    }
    if(positive_count==0 && negative_count == 0 && neutral_count == 0)
      return UNKNOWN;
    if(positive_count > neutral_count && positive_count > negative_count)
      return POSITIVE;
    if(negative_count > neutral_count)
      return NEGATIVE;
    return NEUTRAL;
  }
}
