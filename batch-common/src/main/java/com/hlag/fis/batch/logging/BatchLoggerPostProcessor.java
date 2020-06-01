package com.hlag.fis.batch.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class BatchLoggerPostProcessor implements BeanPostProcessor {

    private static Logger logger = LoggerFactory.getLogger(BatchLoggerPostProcessor.class);

    private BatchLogProducer batchLogProducer;

    private String nodeName;

    private String hostName;

    private BatchLoggerJob jobLogger;

    @Autowired
    BatchLoggerPostProcessor(BatchLogProducer batchLogProducer, String nodeName, String hostName) {
        this.hostName = hostName;
        this.nodeName = nodeName;
        this.batchLogProducer = batchLogProducer;
        jobLogger = new BatchLoggerJob(hostName, nodeName, batchLogProducer);
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (Logger.class.isAssignableFrom(field.getType()) && field.getAnnotation(BatchLogging.class) != null) {

                logger.debug("Attempting to inject a SLF4J logger on bean: " + bean.getClass());

                field.setAccessible(true);
                BatchLogging batchLogging = field.getAnnotation(BatchLogging.class);
                try {
                    jobLogger.setClazz(bean.getClass());
                    jobLogger.setJobName(batchLogging.jobName());
                    jobLogger.setStepName(batchLogging.stepName());
                    field.set(bean, jobLogger);
                    logger.debug("Successfully injected a SLF4J logger on bean: " + bean.getClass());
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    logger.warn("Could not inject logger for class: " + bean.getClass(), e);
                }
            }
        }

        return bean;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}