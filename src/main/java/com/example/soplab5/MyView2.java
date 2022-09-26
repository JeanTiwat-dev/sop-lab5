package com.example.soplab5;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Route(value = "index2")
public class MyView2  extends FormLayout {
    private Button goodword, badword, sentence, show;
    private TextField addword, addsentence;
    private ComboBox boxbad, boxgood;
    private TextArea badwordarea, goodwordarea;
    public MyView2(){
        this.setResponsiveSteps(new ResponsiveStep("40em", 2));
        addword = new TextField();
        addsentence = new TextField();
        addword.setLabel("Add Word");
        addsentence.setLabel("Add Sentence");
        goodword = new Button("Add Good Word");
        badword = new Button("Add Bad Word");
        sentence = new Button("Add Sentence");
        show = new Button("Show Sentence");
        boxbad = new ComboBox<>();
        boxgood = new ComboBox<>();
        boxbad.setLabel("Bad Words");
        boxgood.setLabel("Good Words");
        badwordarea = new TextArea();
        badwordarea.setLabel("Bad Sentences");
        badwordarea.setEnabled(false);
        goodwordarea = new TextArea();
        goodwordarea.setLabel("Good Sentences");
        goodwordarea.setEnabled(false);
        add(addword, addsentence, goodword, sentence, badword, goodwordarea, boxgood, badwordarea, boxbad, show);
        goodword.addClickListener(event ->{
            String vals = addword.getValue();
            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addGood/"+vals)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            boxgood.setItems(out);
        });
        badword.addClickListener(event ->{
            String vals = addword.getValue();
            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addBad/"+vals)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            boxbad.setItems(out);
        });
        sentence.addClickListener(event ->{
            String vals = addsentence.getValue();
            WebClient.create()
                    .post()
                    .uri("http://localhost:8080/proof/"+vals)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
        });
        show.addClickListener(event ->{
            Sentence out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getSentence")
                    .retrieve()
                    .bodyToMono(Sentence.class)
                    .block();
            goodwordarea.setValue(out.goodSentences+"");
            badwordarea.setValue(out.badSentences+"");
        });


    }

}
