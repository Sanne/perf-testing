package io.quarkus.perf;

import io.quarkus.perf.entity.DataEntity;
import io.quarkus.perf.service.JpaDataService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/jpa")
public class JpaResource {

    @Inject
    JpaDataService jpaDataService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DataEntity> getJpaAllData() {
        return jpaDataService.getAllData();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataEntity getJpaSingleData(@PathParam("name") String dataName) {
        return jpaDataService.getDataItem(dataName);
    }

}
