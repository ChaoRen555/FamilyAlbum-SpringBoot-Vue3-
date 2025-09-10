package com.album.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collect {
    private Integer id;
    private Integer userId;
    private Integer categoryId;
    private String userName;
    private String categoryName;
    private Category category;
}
