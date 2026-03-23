package com.vinayak.user_service.dto;

import lombok.Data;

@Data
public class User {

    private String id;
    private String name;
    private String email;
    private String phoneNumber;
}