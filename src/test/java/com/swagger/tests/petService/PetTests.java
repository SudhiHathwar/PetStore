package com.swagger.tests.petService;

import com.swagger.Application;
import com.swagger.petStore.Pet;
import com.swagger.petStore.PetStoreFactory;
import com.swagger.petStore.PetStoreService;
import com.swagger.petStore.StatusType;
import com.swagger.tests.configurations.AbstractTestNGSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

@SpringBootTest(classes = Application.class)
public class PetTests extends AbstractTestNGSpringContextTests {

    @Autowired
    PetStoreService petStoreService;

    @Autowired
    PetStoreFactory petStoreFactory;

    @Test
    public void createPet() {
        Pet expectedResponse = petStoreFactory.createDefault();
        Pet actualResponse = petStoreService.createPet(expectedResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());

        actualResponse = petStoreService.getPet(expectedResponse.getId());
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
    }

    @Test
    public void getPetServiceShouldReturnTheRequestedPetData() {
        Pet expectedResponse = petStoreFactory.createDefault();
        petStoreService.createPet(expectedResponse);

        Pet actualResponse = petStoreService.getPet(expectedResponse.getId());

        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
    }

    @Test
    public void updatePetData() {
        Pet pet = petStoreFactory.createDefault();
        petStoreService.createPet(pet);

        pet.setStatus(StatusType.SOLD);
        Pet actualResponse = petStoreService.updatePetDetails(pet);

        assertEquals(pet.getId(), actualResponse.getId());
        assertEquals(pet.getName(), actualResponse.getName());
        assertEquals(pet.getStatus(), actualResponse.getStatus());

        assertEquals(pet.getStatus(), petStoreService.getPet(pet.getId()).getStatus());
    }

    @Test
    public void deletePetData() {
        Pet pet = petStoreFactory.createDefault();
        petStoreService.createPet(pet);
        petStoreService.deletePetDetails(pet.getId());
        assertThrows(() -> petStoreService.getPet(pet.getId()));
    }
}
