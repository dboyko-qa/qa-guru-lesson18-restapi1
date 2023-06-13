package ru.boyko.darya.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
//use ignoreUnknown properties to not parse unnecessary support field in response
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceResponseModel {
    private ResourceModel data;


}
