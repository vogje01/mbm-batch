package com.momentum.batch.server.listener.service.library;

import com.momentum.batch.common.library.LibraryReader;
import com.momentum.batch.common.library.LibrarySender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class LibraryService {

    @Value(value = "${mbm.library.port}")
    private int libraryPort;

    @Value(value = "${mbm.library.directory}")
    private String libraryDirectory;

    private LibraryReader reader;

    @Autowired
    public LibraryService() {
    }

    @PostConstruct
    public void initialize() {
        File srcFile = new File(String.valueOf(Paths.get(libraryDirectory, StringUtils.EMPTY)));
        try {
            reader = new LibraryReader(new LibrarySender(libraryPort), srcFile.getAbsolutePath());
            reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String fileName) throws IOException {
        File srcFile = new File(String.valueOf(Paths.get(libraryDirectory, fileName)));
        reader = new LibraryReader(new LibrarySender(libraryPort), srcFile.getAbsolutePath());
        reader.read();
    }
}
