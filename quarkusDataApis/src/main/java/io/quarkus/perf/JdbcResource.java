package io.quarkus.perf;

import io.quarkus.perf.entity.DataEntity;
import io.quarkus.perf.service.JdbcDataService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/jdbc")
public class JdbcResource {

    @Inject
    JdbcDataService jdbcDataService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DataEntity> getJdbcAllData() {
        return jdbcDataService.getAllData();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataEntity getJdbcSingleData(@PathParam("name") String dataName) {
        return jdbcDataService.getDataItem(dataName);
    }

}
