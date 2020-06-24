package com.momentum.batch.common.util.filewatch;

import java.util.Set;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@FunctionalInterface
public interface FileChangeListener {

    /**
     * Called when files have been changed.
     *
     * @param changeSet a set of the {@link ChangedFiles}
     */
    void onChange(Set<ChangedFiles> changeSet);

}
