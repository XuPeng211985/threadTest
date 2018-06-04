package com.forkjoin.RecursiveTask;
import java.util.Random;
public class DocumentMock {
    private String words[] = {"peng","qiang","rose","jake","java","mother","father","happy"};
    public String[][] generteDocument(int numLines,int numWords,String word){
        int counter = 0;
        String[][] document = new String[numLines][numWords];
        Random random = new Random();
        for (int i = 0; i < numLines; i++) {
            for (int j = 0; j < numWords; j++) {
              int index = random.nextInt(words.length);
              document[i][j] = words[index];
              if(document[i][j].equals(word)){
                  counter++;
              }
            }
        }
        System.out.println(word + "在数组中出现了："+counter+" time");
        return document;
    }
}
