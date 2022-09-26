package com.example.soplab5;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class WordPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    protected Word words = new Word();

    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.POST)
    public ArrayList<String> addBadWord(@PathVariable("word") String s) {
        words.badWords.add(s);
        return words.badWords;
    }

    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.POST)
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s) {
        words.badWords.remove(words.badWords.indexOf(s));
        return words.badWords;
    }

    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.POST)
    public ArrayList<String> addGoodWord(@PathVariable("word") String s) {
        words.goodWords.add(s);
        return words.goodWords;
    }

    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.POST)
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s) {
        words.goodWords.remove(words.goodWords.indexOf(s));
        return words.goodWords;
    }

    @RequestMapping(value = "/proof/{sentence}", method = RequestMethod.POST)
    public void proofSentence(@PathVariable("sentence") String s) {
        boolean check_good = false, check_bad = false;
        for ( String txt: words.badWords
             ) {
            if (s.contains(txt)) {
                check_bad = true;
            }
        }
        for ( String txt: words.goodWords
             ) {
            if (s.contains(txt)) {
                check_good = true;
            }
        }
//        both
        if (check_bad && check_good) {
            rabbitTemplate.convertAndSend("Fanout","",s);

        }
//        bad word
        else if (check_bad) {
            rabbitTemplate.convertAndSend("Direct","bad",s);

        }
//        good word
        else if (check_good) {
            rabbitTemplate.convertAndSend("Direct","good",s);
        }

    }
    @RequestMapping(value = "/getSentence", method = RequestMethod.GET)
    public Sentence getSentence(){
        Object sentence = rabbitTemplate.convertSendAndReceive("Direct","buy", "");
        return (Sentence) sentence;
    }
}
