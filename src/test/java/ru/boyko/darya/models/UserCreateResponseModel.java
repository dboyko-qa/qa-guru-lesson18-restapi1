package ru.boyko.darya.models;

import lombok.Data;

@Data
public class UserCreateResponseModel {
    private String name;
    private String job;
    private String id;
    private String createdAt;
}
