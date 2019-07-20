package com.mainacad.service;

import com.mainacad.model.Item;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parser extends HTMLEditorKit.ParserCallback implements Runnable {
    private static java.util.List<Item> itemList = Collections.synchronizedList(new ArrayList<Item>());
    private boolean h2Tag = false;
    private int count;
    private static int threadCount = 0;


    public static List<Item> parse(Item item){
        for (int i = 0; i < 20 ; i++) {
            while (threadCount == 20){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Thread thread = new Thread(new Parser());
            thread.setName(Integer.toString(i));
            threadCount++;
            thread.start();

        }
        return itemList;

    }



    @Override
    public void run() {
        threadCount++;
    }

    private static void addItem(Item item) {
        itemList.add(item);
    }

    //This method retrieves the necessary information after the H2 tag is detected
    @Override
    public void handleText(char[] data, int pos) {
        if (h2Tag) {
            parse(WebClient.getItem("https://prom.ua/FM-transmittery-avtomobilnye"));
            // WebClient.getItem("https://prom.ua/FM-transmittery-avtomobilnye");
        }
    }

    @Override
    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        if (HTML.Tag.H2 == t) {
            h2Tag = true;
        }
    }

    @Override
    public void handleEndTag(HTML.Tag t, int pos) {
        if (HTML.Tag.H2 == t) {
            h2Tag = false;
        }
    }
}
