package com.sample.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.sample.conf.CassandraBatchProcess;
import com.sample.conf.CassandraConfiguration;

@Component
public class ProductRepository {

  @Autowired
  @Qualifier(CassandraConfiguration.CASSANDRA_SESSION)
  private Session session;

  @Autowired
  @Qualifier(CassandraConfiguration.CASSANDRA_CLUSTER)
  private Cluster cluster;

  @Autowired
  @Qualifier(CassandraConfiguration.CASSANDRA_MAPPINGMANAGER)
  private MappingManager manager;

  @Autowired
  private CassandraBatchProcess batchProcessor;

  public long count() {
    String query = ("SELECT COUNT(*) FROM products");
    ResultSet results = session.execute(query);
    return results.one().getLong("count");
  }

  /**
   * Insert without using batch.
   */
  public void insert() {
    Map<String, String> scopes = new HashMap<String, String>();
    scopes.put("name", "value");
    UUID productId = UUID.randomUUID();
    Product product = new Product("new item id", 1, productId, scopes);
    Mapper<Product> mapper = manager.mapper(Product.class);
    mapper.save(product);

    ProductById productById = new ProductById("new item id", 1, productId, scopes);
    Mapper<ProductById> mapperForProductById = manager.mapper(ProductById.class);
    mapperForProductById.save(productById);
  }

  /**
   * Batch insert.
   */
  public void batchInsert() {
    Map<String, String> scopes = new HashMap<String, String>();
    scopes.put("name", "value");
    UUID productId = UUID.randomUUID();
    Product product = new Product("new item id", 1, productId, scopes);
    ProductById productById = new ProductById("new item id", 1, productId, scopes);
    Collection<?> secondaryTables = new HashSet<>(Arrays.asList(productById));
    batchProcessor.executeBatchInsert(product, secondaryTables);
  }
}
