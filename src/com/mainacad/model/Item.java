package com.mainacad.model;

import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Item {
    
    private String itemId;
    private String name;
    private String url;
    private String imageUrl;
    private Integer initialPrice;
    private Integer price;


}
