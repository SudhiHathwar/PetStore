package com.swagger.petStore;

import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Component
public class PetStoreFactory {

    public Pet createDefault() {

        String[] photoUrl = {randomAlphanumeric(4)};
        Tag[] tag = {Tag.builder().build()};

        return Pet.builder()
                .id(Integer.parseInt(randomNumeric(4)))
                .name(randomAlphanumeric(4))
                .photoUrls(photoUrl)
                .tags(tag)
                .status(StatusType.AVAILABLE)
                .build();
    }

    public Pet createWithStatus(StatusType statusType){
        String[] photoUrl = {randomAlphanumeric(4)};
        Tag[] tag = {Tag.builder().build()};

        return Pet.builder()
                .id(Integer.parseInt(randomNumeric(4)))
                .name(randomAlphanumeric(4))
                .photoUrls(photoUrl)
                .tags(tag)
                .status(statusType)
                .build();
    }
}
