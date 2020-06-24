package com.momentum.batch.common.util.filewatch;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public final class ChangedFile {

    private final File sourceDirectory;

    private final File file;

    private final Type type;

    /**
     * Create a new {@link ChangedFile} instance.
     *
     * @param sourceDirectory the source directory
     * @param file            the file
     * @param type            the type of change
     */
    public ChangedFile(File sourceDirectory, File file, Type type) {
        Assert.notNull(sourceDirectory, "SourceDirectory must not be null");
        Assert.notNull(file, "File must not be null");
        Assert.notNull(type, "Type must not be null");
        this.sourceDirectory = sourceDirectory;
        this.file = file;
        this.type = type;
    }

    /**
     * Return the file that was changed.
     *
     * @return the file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Return the type of change.
     *
     * @return the type of change
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Return the name of the file relative to the source directory.
     *
     * @return the relative name
     */
    public String getRelativeName() {
        File directory = this.sourceDirectory.getAbsoluteFile();
        File file = this.file.getAbsoluteFile();
        String directoryName = StringUtils.cleanPath(directory.getPath());
        String fileName = StringUtils.cleanPath(file.getPath());
        Assert.state(fileName.startsWith(directoryName),
                () -> "The file " + fileName + " is not contained in the source directory " + directoryName);
        return fileName.substring(directoryName.length() + 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof ChangedFile) {
            ChangedFile other = (ChangedFile) obj;
            return this.file.equals(other.file) && this.type.equals(other.type);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.file.hashCode() * 31 + this.type.hashCode();
    }

    @Override
    public String toString() {
        return this.file + " (" + this.type + ")";
    }

    /**
     * Change types.
     */
    public enum Type {

        /**
         * A new file has been added.
         */
        ADD,

        /**
         * An existing file has been modified.
         */
        MODIFY,

        /**
         * An existing file has been deleted.
         */
        DELETE

    }

}