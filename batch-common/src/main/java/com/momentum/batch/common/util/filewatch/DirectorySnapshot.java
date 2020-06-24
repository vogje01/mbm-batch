package com.momentum.batch.common.util.filewatch;


import org.springframework.util.Assert;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

import static com.momentum.batch.common.util.filewatch.ChangedFile.Type;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
class DirectorySnapshot {

    private static final Set<String> DOTS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(".", "..")));

    private final File directory;

    private final Date time;

    private Set<FileSnapshot> files;

    /**
     * Create a new {@link DirectorySnapshot} for the given directory.
     *
     * @param directory the source directory
     */
    DirectorySnapshot(File directory) {
        Assert.notNull(directory, "Directory must not be null");
        Assert.isTrue(!directory.isFile(), () -> "Directory '" + directory + "' must not be a file");
        this.directory = directory;
        this.time = new Date();
        Set<FileSnapshot> files = new LinkedHashSet<>();
        collectFiles(directory, files);
        this.files = Collections.unmodifiableSet(files);
    }

    private void collectFiles(File source, Set<FileSnapshot> result) {
        File[] children = source.listFiles();
        if (children != null) {
            for (File child : children) {
                if (child.isDirectory() && !DOTS.contains(child.getName())) {
                    collectFiles(child, result);
                } else if (child.isFile()) {
                    result.add(new FileSnapshot(child));
                }
            }
        }
    }

    ChangedFiles getChangedFiles(DirectorySnapshot snapshot, FileFilter triggerFilter) {
        Assert.notNull(snapshot, "Snapshot must not be null");
        File directory = this.directory;
        Assert.isTrue(snapshot.directory.equals(directory),
                () -> "Snapshot source directory must be '" + directory + "'");
        Set<ChangedFile> changes = new LinkedHashSet<>();
        Map<File, FileSnapshot> previousFiles = getFilesMap();
        for (FileSnapshot currentFile : snapshot.files) {
            if (acceptChangedFile(triggerFilter, currentFile)) {
                FileSnapshot previousFile = previousFiles.remove(currentFile.getFile());
                if (previousFile == null) {
                    changes.add(new ChangedFile(directory, currentFile.getFile(), Type.ADD));
                } else if (!previousFile.equals(currentFile)) {
                    changes.add(new ChangedFile(directory, currentFile.getFile(), Type.MODIFY));
                }
            }
        }
        for (FileSnapshot previousFile : previousFiles.values()) {
            if (acceptChangedFile(triggerFilter, previousFile)) {
                changes.add(new ChangedFile(directory, previousFile.getFile(), Type.DELETE));
            }
        }
        return new ChangedFiles(directory, changes);
    }

    private boolean acceptChangedFile(FileFilter triggerFilter, FileSnapshot file) {
        return (triggerFilter == null || !triggerFilter.accept(file.getFile()));
    }

    private Map<File, FileSnapshot> getFilesMap() {
        Map<File, FileSnapshot> files = new LinkedHashMap<>();
        for (FileSnapshot file : this.files) {
            files.put(file.getFile(), file);
        }
        return files;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof DirectorySnapshot) {
            return equals((DirectorySnapshot) obj, null);
        }
        return super.equals(obj);
    }

    boolean equals(DirectorySnapshot other, FileFilter filter) {
        if (this.directory.equals(other.directory)) {
            Set<FileSnapshot> ourFiles = filter(this.files, filter);
            Set<FileSnapshot> otherFiles = filter(other.files, filter);
            return ourFiles.equals(otherFiles);
        }
        return false;
    }

    private Set<FileSnapshot> filter(Set<FileSnapshot> source, FileFilter filter) {
        if (filter == null) {
            return source;
        }
        Set<FileSnapshot> filtered = new LinkedHashSet<>();
        for (FileSnapshot file : source) {
            if (filter.accept(file.getFile())) {
                filtered.add(file);
            }
        }
        return filtered;
    }

    @Override
    public int hashCode() {
        int hashCode = this.directory.hashCode();
        hashCode = 31 * hashCode + this.files.hashCode();
        return hashCode;
    }

    /**
     * Return the source directory of this snapshot.
     *
     * @return the source directory
     */
    File getDirectory() {
        return this.directory;
    }

    @Override
    public String toString() {
        return this.directory + " snapshot at " + this.time;
    }

}