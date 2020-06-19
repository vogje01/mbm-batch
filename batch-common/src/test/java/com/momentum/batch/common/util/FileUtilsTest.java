package com.momentum.batch.common.util;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@SpringBootTest
public class FileUtilsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void shouldReturnVersion_whenCalledWithReleaseVersion() {

        String fileName = "batch-jobs-abc-0.0.5-RELEASE.jar";

        String version = FileUtils.getVersion(fileName);
        assertEquals("0.0.5-RELEASE", version);
    }

    @Test
    public void shouldReturnVersion_whenCalledWithSnapshotVersion() {

        String fileName = "batch-jobs-abc-0.0.5-SNAPSHOT.jar";

        String version = FileUtils.getVersion(fileName);
        assertEquals("0.0.5-SNAPSHOT", version);
    }

    @Test
    public void shouldReturnVersion_whenCalledWithNullVersion() {

        String fileName = "batch-jobs-abc-0.0.5.jar";

        String version = FileUtils.getVersion(fileName);
        assertEquals("0.0.5", version);
    }

    @Test
    public void shouldReturnHash_whenFileCreate() throws IOException {

        // Create a temp file
        File temp = File.createTempFile("tempfile", ".tmp");
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
        bw.write("This is the temporary file content.");
        bw.close();

        String hash = FileUtils.getHash(temp.getAbsolutePath());

        assertEquals("4584d837d2b8d08de87dc0d75a6ef746", hash);
    }
}