package com.momentum.batch.common.library;

/**
 * Library reader / writer on complete callback, for a asynchronious file transfer.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.4
 */
@FunctionalInterface
public interface LibraryOnComplete {

    void onComplete(LibraryWriterProxy fileWriter);
}

