package ru.boyko.darya.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGetResponseModel {
    private Integer page,
    per_page,
    total,
    total_pages;
    private List<UserModel> data;

}
