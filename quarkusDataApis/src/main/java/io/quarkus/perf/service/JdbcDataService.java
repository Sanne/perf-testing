package io.quarkus.perf.service;

import io.agroal.api.AgroalDataSource;
import io.quarkus.perf.entity.DataEntity;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class JdbcDataService {

    private static final Logger LOG = Logger.getLogger(JdbcDataService.class);

    @Inject
    AgroalDataSource agroalDataSource;

    public List<DataEntity> getAllData() {

        LOG.debug("Executing getAllData()");

        List<DataEntity> resultList = null;

        try (Connection connection = agroalDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM DATA_OBJECTS");
        ) {
            connection.setAutoCommit(false);
            try {
                LOG.debug("execute query");
                ResultSet results = statement.executeQuery();
                LOG.debug("extract results");
                resultList = new ArrayList<>();
                while (results.next()) {
                    resultList.add(new DataEntity( results.getLong("ID"), results.getString("DATA_NAME")));
                    LOG.debug("add result");
                }
                connection.commit(); //commit transaction
                LOG.debug("commit");
                connection.setAutoCommit(true);

            } catch (SQLException se) {
                se.printStackTrace();
                connection.rollback(); //rollback  transaction
                LOG.debug("rollback");
                connection.setAutoCommit(true);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;

    }

    public DataEntity getDataItem(String dataname) {

        LOG.debug("Executing getDataItem()");

        DataEntity returnEntity = null;
        try (Connection connection = agroalDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM DATA_OBJECTS WHERE DATA_NAME = ?");) {
            connection.setAutoCommit(false);
            LOG.debug("begin");
            try {
                LOG.debug("execute query");
                statement.setString(1, dataname);
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    LOG.debug("extract result");
                    returnEntity = new DataEntity( results.getLong("ID"), results.getString("DATA_NAME"));
                }
                connection.commit(); //commit transaction
                LOG.debug("commit");
                connection.setAutoCommit(true);

            } catch (SQLException se){
                se.printStackTrace();
                connection.rollback(); //rollback  transaction
                connection.setAutoCommit(true);
                LOG.debug("rollback");
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnEntity;
    }

}
