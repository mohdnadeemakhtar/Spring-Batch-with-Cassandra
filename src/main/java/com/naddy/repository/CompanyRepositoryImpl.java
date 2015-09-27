package com.naddy.repository;

import static com.google.common.collect.ImmutableSet.of;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cassandra.core.CachedPreparedStatementCreator;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

import org.springframework.stereotype.Service;

import com.datastax.driver.core.*;

import com.naddy.entity.Company;
import com.naddy.entity.Person;

import com.naddy.model.Opinions;
import com.naddy.model.Opinions.Brands;

@Service
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {

    @Autowired
    private CassandraOperations cassandraOperations;

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Override
    public Company getCompany(final String firstName, final String lastName) {
        final String cql = "select * from company where first_name = ? and last_name = ?";
        final PreparedStatement cachedPreparedStatement = getCachedPreparedStatement(cql);
        final BoundStatement statement = cachedPreparedStatement.bind(firstName, lastName);

        final ResultSet resultSet = cassandraOperations.getSession().execute(statement);
        final Row row = resultSet.one();
        return toCompany(row);
    }

    @Override
    public Opinions getOpinion(final String firstName, final String lastName) {
        final Company company = getCompany(firstName, lastName);
        final Brands brands = new Brands(of(company.getBrandOpinions().get("followed")),
                of(company.getBrandOpinions().get("hidden")));

        return new Opinions(brands);
    }

    private Company toCompany(final Row row) {
        final Company company = new Company();
        final Person person = new Person(row.getString("first_name"), row.getString("last_name"));
        company.setPerson(person);
        company.setAddress(row.getString("address"));
        company.setBrandOpinions(row.getMap("brand_opinions", String.class, String.class));
        return company;
    }

    private PreparedStatement getCachedPreparedStatement(final String cql) {
        final CachedPreparedStatementCreator cachedPreparedStatementCreator = new CachedPreparedStatementCreator(cql);
        final PreparedStatement preparedStatement = cachedPreparedStatementCreator.createPreparedStatement(
                cassandraTemplate.getSession());

        preparedStatement.setConsistencyLevel(ConsistencyLevel.ONE);

        return preparedStatement;
    }
}
