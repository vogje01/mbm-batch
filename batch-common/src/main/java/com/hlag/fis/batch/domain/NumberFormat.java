package com.hlag.fis.batch.domain;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
public enum NumberFormat {
    DE("de"),
    ENDB("en-gb"),
    ENUS("en-us");

    private String id;

    NumberFormat(String id) {
        this.id = id;
    }
}
