package ru.boyko.darya.models;

import lombok.Data;

@Data

public class UserModel {
    private Integer id;
    private String email,
            first_name,
            last_name,
            avatar;
}
