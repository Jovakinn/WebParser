package com.mainacad.service;

import com.mainacad.model.Item;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
            item.setUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

    private static Long getItemPrice(Document document) {
        Long itemPrice = null;
        Elements elements = document.getElementsByAttributeValueContaining("class","x-product-sticky__price");

        if (elements != null){
            String itemPriceAsText = elements.text().replaceAll("\\D", "");
            if (!itemPriceAsText.isEmpty()){
                itemPrice = Long.valueOf(itemPriceAsText);
            }
        }
        return itemPrice;
    }

    private static String getItemImgUrl(Document document) {
        String itemImgUrl = "";

        Elements element = document.getElementsByAttributeValueContaining("class","x-image-holder__img");
        if (element != null && element.hasAttr("src")){
            itemImgUrl = element.attr("src");
            if (!itemImgUrl.startsWith("https:")){
                itemImgUrl = "https:" + itemImgUrl;
            }
        }
        return itemImgUrl;
    }

    private static String getItemName(Document document) {
        String itemName = "";
        Elements element = document.getElementsByAttributeValueContaining("class","x-product-sticky__prod-name");
        if (element != null){
            itemName = element.text();
        }
        return itemName.trim();
    }

    private static String getItemId(Document document) {
        String itemId = "";
        Elements element = document.getElementsByAttributeValueContaining("class","x-product-info__identity-item");
        if (element != null ){
            itemId = element.text();
        }
        return itemId.trim();
    }
}
