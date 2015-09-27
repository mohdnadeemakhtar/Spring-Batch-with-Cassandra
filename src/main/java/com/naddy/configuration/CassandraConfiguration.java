package com.naddy.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;
import com.datastax.driver.core.policies.ExponentialReconnectionPolicy;
import com.datastax.driver.core.policies.ReconnectionPolicy;
import com.datastax.driver.core.policies.RetryPolicy;

import com.naddy.entity.Company;

import com.naddy.repository.RepositoryPackage;

@Configuration
@EnableCassandraRepositories(basePackageClasses = RepositoryPackage.class)
public class CassandraConfiguration extends AbstractCassandraConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraConfiguration.class);

    private final String LOCAL_HOST = "127.0.0.1";

    private final String KEY_SPACE = "integration";

    @Override
    protected String getKeyspaceName() {
        return KEY_SPACE;
    }

    @Override
    protected String getContactPoints() {
        return LOCAL_HOST;
    }

    @Override
    protected int getPort() {
        return 9042;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.RECREATE_DROP_UNUSED;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {Company.class.getPackage().getName()};
    }

    @Bean(name = "cassandraTemplate")
    public CassandraTemplate profileCassandraTemplate() throws Exception {
        final CassandraTemplate cassandraTemplate = new CassandraTemplate(session().getObject(), cassandraConverter());
        logCassandraTemplateCreation(cassandraTemplate);

        return cassandraTemplate;
    }

    private static void logCassandraTemplateCreation(final CassandraTemplate cassandraTemplate) {
        final Session session = cassandraTemplate.getSession();
        final Cluster cluster = session.getCluster();

        LOG.info("Created Cassandra template for keyspace {}", session.getLoggedKeyspace());

        final Metadata metadata = cluster.getMetadata();
        LOG.info("Using cluster: {}", metadata.getClusterName());

        for (final Host host : metadata.getAllHosts()) {
            LOG.info("Host: {} [DC: {}, Rack: {}]", host.getAddress(), host.getDatacenter(), host.getRack());
        }

    }

    @Override
    protected RetryPolicy getRetryPolicy() {
        return DowngradingConsistencyRetryPolicy.INSTANCE;
    }

    @Override
    protected ReconnectionPolicy getReconnectionPolicy() {
        return new ExponentialReconnectionPolicy(200L, 5000L);
    }

}
