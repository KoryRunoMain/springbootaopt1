package ru.koryruno.springbootaopt1.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThreadUtilsTest {

    @Test
    public void testWaitTime() {
        long startTime = System.currentTimeMillis();
        long waitTime = 1000;
        ThreadUtils.waitTime(waitTime);
        long elapsedTime = System.currentTimeMillis() - startTime;
        assertTrue(elapsedTime >= waitTime,
                "The time that has passed must be greater than or equal to the expected time");
    }

}
