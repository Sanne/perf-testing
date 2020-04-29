package io.quarkus.perf;

import io.quarkus.perf.entity.DataEntity;
import io.quarkus.perf.service.StatelessSessionDataService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/stateless")
public class StatelessSessionResource {

    @Inject
    StatelessSessionDataService statelessSessionDataService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DataEntity> getStatelessAllData() {
        return statelessSessionDataService.getAllData();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public DataEntity getStatelessSingleData(@PathParam("name") String dataName) {
        return statelessSessionDataService.getDataItem(dataName);
    }

}
