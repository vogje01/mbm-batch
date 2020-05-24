package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.transportunitpoint;

import com.hlag.fis.batch.writer.MysqlWriterBuilder;
import com.hlag.fis.db.mysql.model.TransportUnitPoint;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class TransportUnitPointWriter {

    @Autowired
    @Qualifier("mysqlEntityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    public ItemWriter getWriter() {
        return new MysqlWriterBuilder<TransportUnitPoint>(entityManagerFactory).build();
    }
}
