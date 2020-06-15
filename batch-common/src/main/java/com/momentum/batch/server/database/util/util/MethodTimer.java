package com.momentum.batch.server.database.util.util;

import java.io.Serializable;

/**
 * Performance timer class.
 *
 * @author Jens Vogt &lt;jens.vogt@momentum-it.ch&gt;
 * @version $Revision: 3599 $ $Date: 2013-01-11 17:28:23 +0100 (Fri, 11 Jan 2013) $
 */
public class MethodTimer implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 3367696949686076256L;
    /**
     * Start time of timer.
     */
    private long startTime;

    /**
     * Constructor.
     */
    public MethodTimer() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Start the timer.
     */
    public void start() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Restart the timer.
     */
    public void restart() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Return the elapsed time between start and stop.
     *
     * @return elapsed time in milliseconds.
     */
    public long elapsed() {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Returns a formatted string of the elapsed time in milliseconds.
     *
     * @return formatted string with elapsed time in milliseconds.
     */
    public String elapsedStr() {
        return " [" + elapsed() + "ms]";
    }
}
