package org.johara.provider;

import org.johara.config.ConfigFactory;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class DclVarHandleInstanceConfigProvider {
    private volatile String defaultConfigValue;

    public String getDefaultConfigValue() {
        String localRef = getDatabaseNameAcquire();
        if (localRef == null) {
            synchronized (DclVarHandleInstanceConfigProvider.class) {
                localRef = getDatabaseNameAcquire();
                if (localRef == null) {
                    localRef = ConfigFactory.getConfig()
                            .getValue(ConfigFactory.CONFIG_ITEM);
                    setDatabaseNameRelease(localRef);
                }
            }
        }
        return localRef;
    }


    private static final VarHandle DATABASE_NAME;
    private String getDatabaseNameAcquire() {
        return (String) DATABASE_NAME.getAcquire(this);
    }
    private void setDatabaseNameRelease(String value) {
        DATABASE_NAME.setRelease(this, value);
    }

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            DATABASE_NAME = lookup.findVarHandle(DclVarHandleInstanceConfigProvider.class, "defaultConfigValue", String.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

}
