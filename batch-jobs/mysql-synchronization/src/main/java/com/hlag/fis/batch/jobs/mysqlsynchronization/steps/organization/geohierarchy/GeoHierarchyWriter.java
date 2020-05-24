package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.organization.geohierarchy;

import com.hlag.fis.batch.writer.MysqlWriterBuilder;
import com.hlag.fis.db.mysql.model.GeoHierarchy;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class GeoHierarchyWriter {

    @Autowired
    @Qualifier("mysqlEntityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    public ItemWriter getWriter() {
        return new MysqlWriterBuilder<GeoHierarchy>(entityManagerFactory).build();
    }

}
