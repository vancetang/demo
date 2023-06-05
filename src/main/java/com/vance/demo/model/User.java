package com.vance.demo.model;

import java.util.Date;

import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private Date date = new Date();
}
