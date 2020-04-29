package io.quarkus.perf.service;

import io.quarkus.perf.entity.PanacheDataEntity;
import org.jboss.logging.Logger;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class PanacheDataService {

    private static final Logger LOG = Logger.getLogger(PanacheDataService.class);


    @Transactional
    public List<PanacheDataEntity> getAllData(){

        LOG.debug("Executing getAllData()");

        return PanacheDataEntity.listAll();
    }

    @Transactional
    public PanacheDataEntity getDataItem(String dataname){

        LOG.debug("Executing getDataItem()");

        return PanacheDataEntity.findByName(dataname);
    }

}
