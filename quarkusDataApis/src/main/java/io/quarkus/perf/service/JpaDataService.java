package io.quarkus.perf.service;

import io.quarkus.perf.entity.DataEntity;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class JpaDataService {

    private static final Logger LOG = Logger.getLogger(JpaDataService.class);

    @Inject
    EntityManager em;


    @Transactional
    public List<DataEntity> getAllData(){

        LOG.debug("Executing getAllData()");

        Query query = em.createQuery("FROM DataEntity", DataEntity.class);
        return  query.getResultList();
    }

    @Transactional
    public DataEntity getDataItem(String dataname){

        LOG.debug("Executing getDataItem()");

        Query query = em.createQuery("FROM DataEntity WHERE DATA_NAME = ?1", DataEntity.class);
        query.setParameter(1, dataname);
        return (DataEntity) query.getSingleResult();
    }

}
