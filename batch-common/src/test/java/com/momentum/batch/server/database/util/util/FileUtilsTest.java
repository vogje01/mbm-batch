package com.momentum.batch.server.database.util.util;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@SpringBootTest()
public class FileUtilsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void fileVersionTest() {

        String fileName = "batch-jobs-abc-0.0.4.jar";

        String version = FileUtils.getVersion(fileName);
        assertEquals("0.0.4", version);
    }
}