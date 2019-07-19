package com.mainacad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainacad.model.Item;
import com.mainacad.service.WebClient;

import java.util.Arrays;
import java.util.logging.Logger;

public class AppRunner {

    private static Logger logger = Logger.getLogger(AppRunner.class.getName());

    public static void main(String[] args) {

        Item item = WebClient.getItem("https://prom.ua/p843085994-noutbuk-omen-dc0047ur.html");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            logger.info(objectMapper.writeValueAsString(Arrays.asList(item)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
