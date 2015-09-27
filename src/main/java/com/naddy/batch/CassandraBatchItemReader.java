package com.naddy.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.cassandra.core.CassandraOperations;

import com.naddy.entity.Company;

/**
 * Created by makhtar on 25/09/15.
 */
public class CassandraBatchItemReader<Company> implements ItemReader<Company> {

    private static final Logger log = LoggerFactory.getLogger(CassandraBatchItemReader.class);

    @Autowired
    private CassandraOperations cassandraOperations;

    private final Class<Company> aClass;

    private int index = 0;

    public CassandraBatchItemReader(final Class<Company> aClass) {
        this.aClass = aClass;
    }

    @Override
    public Company read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        final List<Company> companies = cassandraOperations.selectAll(aClass);

        log.debug("Read operations is performing, the object size is  {}", companies.size());

        if (index < companies.size()) {
            final Company company = companies.get(index);
            index++;
            return company;
        }

        return null;
    }
}
