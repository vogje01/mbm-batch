package com.momentum.batch.server.manager.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Paths;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Value("${mbm.library.directory}")
    public String libraryDirectory;

    @RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
    public FileSystemResource getFile(@PathVariable("file_name") String fileName) {
        return new FileSystemResource(Paths.get(libraryDirectory, fileName + ".jar"));
    }
}
