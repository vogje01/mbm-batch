package com.momentum.batch.server.scheduler.util.dto;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public class ChunksInfo {

    private long bytesUploaded;

    private long chunkCount;

    private long chunkIndex;

    private long fileIndex;

    private String customData;

    public long getBytesUploaded() {
        return bytesUploaded;
    }

    public void setBytesUploaded(long bytesUploaded) {
        this.bytesUploaded = bytesUploaded;
    }

    public long getChunkCount() {
        return chunkCount;
    }

    public void setChunkCount(long chunkCount) {
        this.chunkCount = chunkCount;
    }

    public long getChunkIndex() {
        return chunkIndex;
    }

    public void setChunkIndex(long chunkIndex) {
        this.chunkIndex = chunkIndex;
    }

    public long getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(long fileIndex) {
        this.fileIndex = fileIndex;
    }

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }
}
