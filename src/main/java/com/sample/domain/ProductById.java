package com.sample.domain;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
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

  public ProductById(String itemid, int version, UUID productid) {
    this.itemid = itemid;
    this.version = version;
    this.productid = productid;
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
}
