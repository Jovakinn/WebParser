package com.mainacad.service;

import com.mainacad.model.Item;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebClient {

    public static Item getItem(String url){
        Item item = new Item();

        try {
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();
            item.setItemId(getItemId(document));
            item.setName(getItemName(document));
            item.setImageUrl(getItemImgUrl(document));
            item.setPrice(getItemPrice(document));
            item.setInitialPrice(getItemInitPrice(document));
            item.setUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (item.getItemId().isEmpty()){
            return new Item("11111", "Test name", "http://test-url", "http://test-img-url", 15000, 10000);
        }
        return item;
    }

    private static Integer getItemPrice(Document document) {
        Integer itemPrice = null;
        Element element = document.getElementById("priceblock_ourprice");
        if (element != null){
            String itemPriceAsText = element.text().replaceAll("\\D", "");
            if (!itemPriceAsText.isEmpty()){
                itemPrice = Integer.valueOf(itemPriceAsText);
            }
        }
        return itemPrice;
    }

    private static Integer getItemInitPrice(Document document) {
        Integer itemInitPrice = null;

        Elements spanElements =
                document.getElementsByAttributeValueContaining("class", "priceBlockStrikePriceString");
        if (!spanElements.isEmpty()){
            String itemInitPriceAsText = spanElements.first().text().replaceAll("\\D", "");
            if (!itemInitPriceAsText.isEmpty()){
                itemInitPrice = Integer.valueOf(itemInitPriceAsText);
            }
        }
        return itemInitPrice;
    }

    private static String getItemImgUrl(Document document) {
        String itemImgUrl = "";

        Element element = document.getElementById("product-title");
        if (element != null && element.hasAttr("data-old-hires")){
            itemImgUrl = element.attr("data-old-hires");
            if (!itemImgUrl.startsWith("https:")){
                itemImgUrl = "https:" + itemImgUrl;
            }
        }
        return itemImgUrl;
    }

    private static String getItemName(Document document) {
        String itemName = "";
        Element element = document.getElementById("product-title");
        if (element != null){
            itemName = element.text();
        }
        return itemName.trim();
    }

    private static String getItemId(Document document) {
        String itemId = "";
        Element element = document.getElementById("ASIN");
        if (element != null && element.hasAttr("value")){
            itemId = element.attr("value");
        }
        return itemId;
    }
}
