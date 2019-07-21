package com.mainacad.service;

import com.mainacad.model.Item;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.jsoup.Jsoup.connect;

public class Parser extends HTMLEditorKit.ParserCallback implements Runnable {

    private static java.util.List<Item> itemList = Collections.synchronizedList(new ArrayList<Item>());
    private static boolean h2Tag = false;
    private static int threadCount = 0;
    private String url;

    Connection connection = connect(url);
        static Document document;
    {
        try {
            document = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            Elements elements = document.getElementsByAttributeValueContaining("class",
                    "x-gallery-tile__name ek-link ek-link_style_multi-line");
            WebClient.getItem(String.valueOf(elements));
            StackTraceElement [] stackTraceElements = Thread.currentThread().getStackTrace();

            for (StackTraceElement element : stackTraceElements) {
                element.toString();
            }


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

    public void handleText(char[] data, int pos) {
        if (h2Tag) {
            parse(WebClient.getItem(String.valueOf(itemList)));
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
