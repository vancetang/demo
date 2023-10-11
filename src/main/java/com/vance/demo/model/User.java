package com.vance.demo.model;

import java.util.Date;

import lombok.Data;

/**
 * 使用者物件
 * 
 * @author Vance
 */
@Data
public class User {
    private String id;
    private String name;
    private Date date = new Date();
}
