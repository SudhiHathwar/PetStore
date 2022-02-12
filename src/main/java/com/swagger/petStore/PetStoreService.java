package com.swagger.petStore;

import com.google.gson.Gson;
import com.swagger.configurations.ApiConfiguration;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

import static io.restassured.RestAssured.given;

@Service
public class PetStoreService {

    private ApiConfiguration apiConfiguration;

    @Autowired
    public PetStoreService(@Nonnull ApiConfiguration apiConfiguration) {
        this.apiConfiguration = apiConfiguration;
    }

    @Step("Getting Pet details for id: {petId}")
    public Pet getPet(Object petId) {

        Pet pet =
                given().filters(new AllureRestAssured(), new ErrorLoggingFilter())
                        .contentType(ContentType.JSON)
                        .when()
                        .get(
                                apiConfiguration.getHostEndpoint()
                                        + String.format(apiConfiguration.getPetEndpoint(), petId))
                        .then()
                        .statusCode(200)
                        .extract()
                        .response()
                        .as(Pet.class);

        return pet;
    }

    @Step("Create a new  Pet")
    public Pet createPet(Pet pet) {

        Pet response =
                given().filters(new AllureRestAssured(), new ErrorLoggingFilter())
                        .contentType(ContentType.JSON)
                        .body(new Gson().toJson(pet))
                        .when()
                        .post(
                                apiConfiguration.getHostEndpoint()
                                        + apiConfiguration.getCreatePetEndpoint())
                        .then()
                        .statusCode(200)
                        .extract()
                        .response()
                        .as(Pet.class);
        return response;
    }

    @Step("Update Pet details")
    public Pet updatePetDetails(Pet pet) {

        Pet response =
                given().filters(new AllureRestAssured(), new ErrorLoggingFilter())
                        .contentType(ContentType.JSON)
                        .body(new Gson().toJson(pet))
                        .when()
                        .put(
                                apiConfiguration.getHostEndpoint()
                                        + apiConfiguration.getUpdatePetEndpoint())
                        .then()
                        .extract()
                        .response()
                        .as(Pet.class);

        return response;
    }

    @Step("Delete Pet details for id: {petId}")
    public void deletePetDetails(Object petId) {

        given().filters(new AllureRestAssured(), new ErrorLoggingFilter())
                .contentType(ContentType.JSON)
                .header("api_key", "some_random_key")
                .when()
                .delete(
                        apiConfiguration.getHostEndpoint()
                                + String.format(apiConfiguration.getDeletePetEndpoint(), petId))
                .then()
                .statusCode(200);
    }
}
