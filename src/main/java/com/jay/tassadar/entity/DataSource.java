package com.jay.tassadar.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataSource implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String url;
    private String username;
    private String password;

}
