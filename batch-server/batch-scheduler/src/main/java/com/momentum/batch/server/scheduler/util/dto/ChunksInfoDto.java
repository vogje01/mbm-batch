package com.momentum.batch.server.scheduler.util.dto;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public class ChunksInfoDto {

    private long bytesUploaded;

    private long chunkCount;

    private long chunkIndex;

    private long fileIndex;

    private String chunkBlob;

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

    public String getChunkBlob() {
        return chunkBlob;
    }

    public void setChunkBlob(String chunkBlob) {
        this.chunkBlob = chunkBlob;
    }
}
