package com.jay.tassadar.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Slf4j
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String userName;
    private Integer phone;

    public void test() {
        log.info("");
    }
}
