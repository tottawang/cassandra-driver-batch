package com.sample.domain;

import java.util.Map;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.sample.conf.SecondaryTable;

@Table(name = "product_by_id")
@SecondaryTable(masterEntity = Product.class)
public class ProductById {

  @PartitionKey(0)
  private UUID productid;

  @PartitionKey(1)
  private String itemid;

  @ClusteringColumn
  private int version;

  @Column(name = "scopes")
  private Map<String, String> scopes;

  public ProductById(String itemid, int version, UUID productid, Map<String, String> scopes) {
    this.itemid = itemid;
    this.version = version;
    this.productid = productid;
    this.scopes = scopes;
  }

  public UUID getProductid() {
    return productid;
  }

  public void setProductid(UUID _productid) {
    this.productid = _productid;
  }

  public int getVersion() {
    return this.version;
  }

  public void setVersion(int _version) {
    this.version = _version;
  }

  public String getItemid() {
    return itemid;
  }

  public void setItemid(String _itemid) {
    this.itemid = _itemid;
  }

  public Map<String, String> getScopes() {
    if (scopes.isEmpty())
      return null;
    else
      return scopes;
  }

  public void setScopes(Map<String, String> scopes) {
    this.scopes = scopes;
  }
}
