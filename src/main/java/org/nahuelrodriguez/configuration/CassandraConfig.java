package org.nahuelrodriguez.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractClusterConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.List;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractClusterConfiguration {
    private final String contactPoints;
    private final String keySpace;

    public CassandraConfig(@Value("${spring.data.cassandra.keyspace-name}") final String keySpace,
                           @Value("${spring.data.cassandra.contact-points}") final String contactPoints) {
        this.keySpace = keySpace;
        this.contactPoints = contactPoints;
    }

    protected String getKeyspaceName() {
        return keySpace;
    }

    @Override
    protected List<String> getStartupScripts() {
        final var createKeyspace =
                "CREATE KEYSPACE IF NOT EXISTS " + keySpace + " WITH durable_writes = true" +
                        " AND replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};";

        final var createTable = "CREATE TABLE IF NOT EXISTS todolist.to_do_items " +
                "(id UUID PRIMARY KEY, description TEXT, status TEXT, createddatetime TIMESTAMP) ";
        return List.of(createKeyspace, createTable);
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Bean
    @Override
    public CassandraClusterFactoryBean cluster() {
        final var bean = new CassandraClusterFactoryBean();
        bean.setAddressTranslator(getAddressTranslator());
        bean.setAuthProvider(getAuthProvider());
        bean.setClusterBuilderConfigurer(getClusterBuilderConfigurer());
        bean.setClusterName(getClusterName());
        bean.setCompressionType(getCompressionType());
        bean.setContactPoints(contactPoints);
        bean.setLoadBalancingPolicy(getLoadBalancingPolicy());
        bean.setMaxSchemaAgreementWaitSeconds(getMaxSchemaAgreementWaitSeconds());
        bean.setMetricsEnabled(false);
        bean.setNettyOptions(getNettyOptions());
        bean.setPoolingOptions(getPoolingOptions());
        bean.setPort(getPort());
        bean.setProtocolVersion(getProtocolVersion());
        bean.setQueryOptions(getQueryOptions());
        bean.setReconnectionPolicy(getReconnectionPolicy());
        bean.setRetryPolicy(getRetryPolicy());
        bean.setSpeculativeExecutionPolicy(getSpeculativeExecutionPolicy());
        bean.setSocketOptions(getSocketOptions());
        bean.setTimestampGenerator(getTimestampGenerator());
        bean.setKeyspaceCreations(getKeyspaceCreations());
        bean.setKeyspaceDrops(getKeyspaceDrops());
        bean.setStartupScripts(getStartupScripts());
        bean.setShutdownScripts(getShutdownScripts());

        return bean;
    }
}
