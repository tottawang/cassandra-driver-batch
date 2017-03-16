package com.sample.conf;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

@Configuration
public class CassandraConfiguration {

  public static final String CASSANDRA_CLUSTER = "sample.cluster";
  public static final String CASSANDRA_SESSION = "sample.session";
  public static final String CASSANDRA_MAPPINGMANAGER = "sample.mappingmanager";
  public static final String CASSANDRA_MAPPERS = "sample.mappers";

  @Value("${CASSANDRA_KEYSPACE}")
  private String CASSANDRA_KEYSPACE;

  @Value("${CASSANDRA_CONTACTPOINTS}")
  private String CASSANDRA_CONTACTPOINTS;

  @Value("${CASSANDRA_PORT}")
  private String CASSANDRA_PORT;

  @Value("${CASSANDRA_STRATEGY}")
  private String CASSANDRA_STRATEGY;

  @Value("${CASSANDRA_REPLICATION_OPTIONS}")
  private String CASSANDRA_REPLICATION_OPTIONS;

  @Bean(name = CASSANDRA_CLUSTER, autowire = Autowire.BY_NAME)
  public Cluster cassandraCluster() {
    Cluster cluster = null;
    QueryOptions options = new QueryOptions().setConsistencyLevel(ConsistencyLevel.LOCAL_ONE);
    cluster = Cluster.builder()
        .addContactPoints(org.springframework.util.StringUtils
            .commaDelimitedListToStringArray(CASSANDRA_CONTACTPOINTS))
        .withPort(Integer.parseInt(CASSANDRA_PORT)).withQueryOptions(options).build();
    return cluster;
  }

  @Bean(name = CASSANDRA_SESSION, autowire = Autowire.BY_NAME)
  public Session cassandraSession() {
    Session session = null;
    try {
      session = cassandraCluster().connect(CASSANDRA_KEYSPACE);
    } catch (Exception ex) {
      throw new RuntimeException("Failed to connect to Cassandra host.", ex);
    }
    return session;
  }

  @Bean(name = CASSANDRA_MAPPINGMANAGER, autowire = Autowire.BY_NAME)
  public MappingManager cassandraMappingManager() {
    return new MappingManager(cassandraSession());
  }
}
