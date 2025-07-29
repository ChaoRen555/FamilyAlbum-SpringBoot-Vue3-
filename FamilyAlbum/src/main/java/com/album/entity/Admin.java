package com.album.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {
    //N means necessary field
    private Integer id;
    private String username; //N
    private String password;
    private String name;
    private String avatar;
    private String role;
    private String phone;
    private String email;
}
