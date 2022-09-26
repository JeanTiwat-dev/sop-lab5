package com.example.soplab5;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SentenceConsumer {

    protected Sentence sentences = new Sentence();

    @RabbitListener(queues = "BadWordQueue")
    public void addBadSentence(String s) {
        sentences.badSentences.add(s);
        System.out.println("In bad : " + sentences.badSentences);
    }

    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String s) {
        sentences.goodSentences.add(s);
        System.out.println("In Good :" + sentences.goodSentences);
    }
    @RabbitListener(queues = "Getkuy")
    public Sentence getSentence(){
        return sentences;
    }
}
