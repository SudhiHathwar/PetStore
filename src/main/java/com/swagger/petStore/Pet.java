package com.swagger.petStore;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Pet {

    private int id;
    private String name;
    private String[] photoUrls;
    private Tag[] tags;
    private StatusType status;
}
