package com.momentum.batch.server.database.domain.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public class FileSystemDto {

    private String name;

    private long size;

    private String thumbnail;

    private String dateModified;

    private boolean isDirectory;

    private boolean hasSubDirectories;

    private List<FileSystemDto> items = new ArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public boolean getIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(boolean directory) {
        isDirectory = directory;
    }

    public boolean getHasSubDirectories() {
        return hasSubDirectories;
    }

    public void setHasSubDirectories(boolean hasSubDirectories) {
        this.hasSubDirectories = hasSubDirectories;
    }

    public List<FileSystemDto> getItems() {
        return items;
    }

    public void setItems(List<FileSystemDto> items) {
        this.items = items;
    }

    public void addItem(FileSystemDto item) {
        if (!items.contains(item)) {
            items.add(item);
        }
    }

    public void removeItem(FileSystemDto item) {
        if (items.contains(item)) {
            items.remove(item);
        }
    }
}
