package com.example.dictonaryassignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DictionaryController {
    private Dictionary dictionary;

    @Autowired
    public DictionaryController (Dictionary d){
        this.dictionary = d;
    }

    @RequestMapping("/")
    public String showHomePage(Model model){
        model.addAttribute("dictionary", dictionary.getDictionary());
        return "Index";
    }

    @RequestMapping("/words/{word}")
    public String showWordPage (@PathVariable String word, Model model){
        model.addAttribute("item", dictionary.getWord(word));
        return "Word";
    }

    @RequestMapping("/search")
    public String showSearchPage(Model model){
        String term = "";
        model.addAttribute("term", term);
        return "Search";
    }

    @RequestMapping("/get-word")
    public String processSearch(@RequestParam String term){
        return "redirect:/words/"+ dictionary.getClosestWord(term);
    }
}
