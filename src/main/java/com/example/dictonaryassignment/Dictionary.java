package com.example.dictonaryassignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Console;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class Dictionary {
    private List<Word> dictionary;

    public List<Word> getDictionary(){
        return dictionary;
    }

    public void addWord(Word word){
        dictionary.add(word);
    }

    public Word getWord(String name) {
        int index = 0;
        for(int i = 0; i<dictionary.size(); i++){
            if(dictionary.get(i).getWord().equals(name)){
                index = i;
            }
        }
        return dictionary.get(index);
    }

    public String getClosestWord(String term){
        int index = 0;
        int maxMatches = 0;
        if(!term.isBlank() || !term.isEmpty()) {
            for (int i = 0; i < dictionary.size(); i++) {
                int matches = 0;
                String word = dictionary.get(i).getWord().toLowerCase();
                char[] ta = term.toLowerCase(Locale.ROOT).toCharArray();
                int leng = Math.max(word.length(), term.length());
                for (int j = 0; j < term.length() ; j++) {
                    if (word.contains(ta[j]+"")) {
                        matches++;
                    }
                }
                if (matches > maxMatches) {
                    maxMatches = matches;
                    index = i;
                }
            }
        }
        return dictionary.get(index).getWord();
    }

    @PostConstruct
    public void initDictionary() {
        ObjectMapper mapper = new ObjectMapper();
        try {
           Word[] wordArray = mapper.readValue(Paths.get("words.json").toFile(),Word[].class);
           dictionary = Arrays.asList(wordArray);
        } catch (IOException e) {
            e.printStackTrace();
            dictionary = new ArrayList<>();
        }
    }
}
