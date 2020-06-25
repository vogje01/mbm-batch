package com.momentum.batch.common.util;

import com.momentum.batch.server.database.domain.dto.FileSystemDto;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public class DirectoryTree {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

    private static FileSystemDto newDirectory(File file) {
        FileSystemDto fileSystemDto = new FileSystemDto();
        fileSystemDto.setIsDirectory(true);
        fileSystemDto.setName(file.getName());
        fileSystemDto.setHasSubDirectories(MbmFileUtils.hasSubDirectories(file));
        fileSystemDto.setDateModified(simpleDateFormat.format(file.lastModified()));
        return fileSystemDto;
    }

    private static FileSystemDto newFile(File file) {
        FileSystemDto fileSystemDto = new FileSystemDto();
        fileSystemDto.setSize(FileUtils.sizeOf(file));
        fileSystemDto.setIsDirectory(false);
        fileSystemDto.setName(file.getName());
        fileSystemDto.setDateModified(simpleDateFormat.format(file.lastModified()));
        return fileSystemDto;
    }

    public static FileSystemDto getDirectoryTree(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {

        // Create root node
        FileSystemDto root = newDirectory(directory);

        // Iterate of the tree
        Iterator<File> iterator = FileUtils.iterateFilesAndDirs(directory, fileFilter, dirFilter);
        while (iterator.hasNext()) {
            File file = iterator.next();
            if (file.isDirectory()) {
                root.addItem(newDirectory(file));
            } else {
                root.addItem(newFile(file));
            }
        }
        return root;
    }


    public static DirectoryNode createDirectoryTree(final String[] list) {
        DirectoryNode treeRootNode = null;
        for (final String rawPath : list) {
            final String path = rawPath.startsWith("/") ? rawPath.substring(1) : rawPath;
            final String[] pathElements = path.split("/");
            DirectoryNode movingNode = null;
            for (final String pathElement : pathElements) {
                movingNode = new DirectoryNode(movingNode, pathElement);
            }

            if (treeRootNode == null) {
                treeRootNode = movingNode.getRoot();
            } else {
                treeRootNode.merge(movingNode.getRoot());
            }
        }

        return treeRootNode;
    }

    private static class DirectoryNode {

        private final Set<DirectoryNode> children = new LinkedHashSet<>();

        private final String value;

        private final DirectoryNode parent;

        public DirectoryNode(final DirectoryNode parent, final String value) {
            this.parent = parent;
            if (this.parent != null) {
                this.parent.children.add(this);
            }
            this.value = value;
        }

        public Set<DirectoryNode> getChildren() {
            return this.children;
        }

        public String getValue() {
            return this.value;
        }

        public DirectoryNode getParent() {
            return this.parent;
        }

        public int getLeafCount() {
            int leafCount = this.isLeaf() ? 1 : 0;
            for (final DirectoryNode child : this.children) {
                leafCount += child.getLeafCount();
            }

            return leafCount;
        }

        public boolean isLeaf() {
            return this.children.isEmpty();
        }

        public DirectoryNode getRoot() {
            return this.parent == null ? this : this.parent.getRoot();
        }

        public void merge(final DirectoryNode that) {
            if (!this.value.equals(that.value)) {
                return;
            } else if (this.children.isEmpty()) {
                this.children.addAll(that.children);
                return;
            }

            final DirectoryNode[] thisChildren = this.children.toArray(new DirectoryNode[this.children.size()]);
            for (final DirectoryNode thisChild : thisChildren) {
                for (final DirectoryNode thatChild : that.children) {
                    if (thisChild.value.equals(thatChild.value)) {
                        thisChild.merge(thatChild);
                    } else if (!this.children.contains(thatChild)) {
                        this.children.add(thatChild);
                    }
                }
            }
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final DirectoryNode that = (DirectoryNode) o;
            return Objects.equals(this.value, that.value)
                    && Objects.equals(this.parent, that.parent);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.value, this.parent);
        }
    }
}

