package com.album.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture {
    private Integer id;
    private String name;
    private String img;
    private String description;
    private Integer userId;
    private Integer categoryId;
    private String roleRadio;
    private String time;
    private String statusRadio;
    private String userName;
    private String categoryName;
}
