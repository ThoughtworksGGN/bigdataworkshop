package com.tw.helperutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class DictionaryBuilder
{
  private static Map<String,String> dictionary = new HashMap<String, String>();

  public static Map<String,String> fetchDictionary() throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(
        new File("/resources/abcd.tff")
        ));
    String line="";
    while((line = br.readLine()) != null)
    {
      String [] pairs = line.split(" ");
      String key = pairs[2].split("=")[1];
      String value = pairs[5].split("=")[1];
      dictionary.put(key,value);
    }

    return dictionary;
  }

}
