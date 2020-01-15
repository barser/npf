package ru.ospos.npf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SandboxApplicationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SandboxApplicationTest.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TestController testController;

    @Test
    public void when_CallTestController_Expect_CorrectResult() {

        Environment environment = applicationContext.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        assertNotNull(activeProfiles);

        var testDto = new TestDto();

        testDto.setAString("Привет");
        testDto.setAChar('Ё');

        testDto.setALong(99_123_456_789L);
        testDto.setADouble(123.456);

        testDto.setABigDecimal(BigDecimal.TEN);
        testDto.setABoolean(Boolean.TRUE);

        testDto.setADate(LocalDate.now());
        testDto.setADateTime(LocalDateTime.now());

        testDto.setACollection(Collections.emptyList());

        var result = testController.post(testDto);
        LOGGER.info(result.getMessage());

        assertTrue(result.isSuccess());
        assertEquals(testDto.toString(), result.getMessage());
    }
}
