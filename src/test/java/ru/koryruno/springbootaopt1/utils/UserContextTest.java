package ru.koryruno.springbootaopt1.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserContextTest {

    @AfterEach
    public void tearDown() {
        UserContext.setUSERNAME(null);
    }

    @Test
    public void testSetAndGetUSERNAME() {
        String testUsername = "testUser";
        UserContext.setUSERNAME(testUsername);
        assertEquals(testUsername, UserContext.getUSERNAME());
    }

}
