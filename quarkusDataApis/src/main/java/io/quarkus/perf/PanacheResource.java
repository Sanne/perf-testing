package io.quarkus.perf;

import io.quarkus.perf.entity.PanacheDataEntity;
import io.quarkus.perf.service.PanacheDataService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/panache")
public class PanacheResource {


    @Inject
    PanacheDataService panacheDataService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PanacheDataEntity> getPanacheAllData() {
        return panacheDataService.getAllData();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public PanacheDataEntity getPanacheSingleData(@PathParam("name") String dataName) {
        return panacheDataService.getDataItem(dataName);
    }
}
