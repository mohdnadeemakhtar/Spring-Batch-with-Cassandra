package com.naddy.batch;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.batch.item.ItemWriter;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.cassandra.core.CassandraTemplate;

/**
 * Created by makhtar on 25/09/15.
 */
public class CassandraBatchItemWriter<Company> implements ItemWriter<Company>, InitializingBean {

    protected static final Log logger = LogFactory.getLog(CassandraBatchItemWriter.class);
    private final Class<Company> aClass;
    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Override
    public void afterPropertiesSet() throws Exception { }

    public CassandraBatchItemWriter(final Class<Company> aClass) {
        this.aClass = aClass;
    }

    @Override
    public void write(final List<? extends Company> items) throws Exception {
        logger.debug("Write operations is performing, the size is {}" + items.size());
        if (!items.isEmpty()) {
            logger.info("Deleting in a batch performing...");
            cassandraTemplate.deleteAll(aClass);
            logger.info("Inserting in a batch performing...");
            cassandraTemplate.insert(items);
        }

        logger.debug("Items is null...");
    }
}
