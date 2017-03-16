package com.sample.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sample.domain.ProductRepository;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("/api")
public class RestResource {

  @Autowired
  private ProductRepository repository;

  @GET
  @Path("count")
  public String count() {
    return Long.valueOf(repository.count()).toString() + " item(s) returned";
  }

  @POST
  @Path("insert")
  public void insert() {
    repository.insert();
  }

  @POST
  @Path("batch-insert")
  public void batchInsert() {
    repository.batchInsert();
  }
}
