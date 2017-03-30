package com.sample.domain;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.Transient;
import com.sample.conf.EnableBatch;

@Table(name = "products")
@EnableBatch
public class Product {

  @PartitionKey(0)
  private String itemid;

  @PartitionKey(1)
  private int version;

  @ClusteringColumn
  private UUID productid;

  @Transient
  private String text;

  public Product(String itemid, int version, UUID productid) {
    this.itemid = itemid;
    this.version = version;
    this.productid = productid;
    this.text = "dummy";
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

  // @Transient
  public ProductKey getProductKey() {
    return new ProductKey(this.itemid, this.version, this.productid);
  }

  public void setProductKey(ProductKey productKey) {
    this.productid = productKey.getProductid();
    this.version = productKey.getVersion();
    this.itemid = productKey.getItemid();
  }


  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
