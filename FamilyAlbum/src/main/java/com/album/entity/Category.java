package com.album.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Integer id;
    private String img;
    private String name;
    private String description;
    private Integer userId;
    private String statusRadio;
    private String time;
    private String roleRadio;
    private String userName;
    private String userAvatar;
}
