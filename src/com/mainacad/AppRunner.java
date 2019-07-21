package com.mainacad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainacad.service.Parser;
import com.mainacad.service.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class AppRunner {

    private static Logger logger = Logger.getLogger(AppRunner.class.getName());

    public static void main(String[] args) {

        List parser = Parser.parse(WebClient.getItem("https://prom.ua/FM-transmittery-avtomobilnye"));
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            logger.info(objectMapper.writeValueAsString(Arrays.asList(parser)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
