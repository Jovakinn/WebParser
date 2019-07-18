package com.mainacad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainacad.model.Item;
import com.mainacad.service.WebClient;

import java.util.Arrays;
import java.util.logging.Logger;

public class AppRunner {

    private static Logger logger = Logger.getLogger(AppRunner.class.getName());
    public static void main(String[] args) {

        // json
        // {"name" : "value",
        // "list" : {"element1" : "value1""
        // element1" : "value1"
        // "element1" : "value1"
        //  }
        //}

        Item item = WebClient.getItem("https://www.amazon.com/HP-Flagship-Premium-i7-8750H-Keyboard/dp/B07TKXFYCP/ref=sr_1_9?keywords=hp+15+omen&qid=1563466678&s=computers-intl-ship&sr=8-9");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            logger.info(objectMapper.writeValueAsString(Arrays.asList(item)));
        } catch (Exception e) {
            e.printStackTrace();
        }


        //csv
        // item ID, name,....,item URL;
        // 1111, Test item1,.....,http...;
        // 2222, Test item2,......,http...;


        // xml
        /*
        <item>
                <itemId>1111</itemId>
                <name>test Name</name>

                ...

        </item>
         */


    }
}
