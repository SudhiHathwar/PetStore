package com.swagger.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class ApiConfiguration {

    @Value("${api.swagger.host}")
    private String hostEndpoint;

    @Value("${api.swagger.pet.create}")
    private String createEndpoint;

    @Value("${api.swagger.pet.update}")
    private String updateEndpoint;

    @Value("${api.swagger.pet.get}")
    private String getEndpoint;

    @Value("${api.swagger.pet.delete}")
    private String deletePetEndpoint;

    public String getCreatePetEndpoint() {
        return createEndpoint;
    }

    public String getUpdatePetEndpoint() {
        return updateEndpoint;
    }

    public String getDeletePetEndpoint() {
        return deletePetEndpoint;
    }

    public String getHostEndpoint() {
        return hostEndpoint;
    }

    public String getPetEndpoint() {
        return getEndpoint;
    }
}
