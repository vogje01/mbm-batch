package com.hlag.fis.batch.logging;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BatchStepLogger {

    String value() default "";
}
