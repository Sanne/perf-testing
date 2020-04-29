package org.johara.provider;

import org.johara.config.ConfigFactory;

public class DclLocalRefInstanceConfigProvider {
    private volatile String defaultConfigValue;

    public String getDefaultConfigValue() {
        String localRef = defaultConfigValue;
        if (localRef == null) {
            synchronized (DclLocalRefInstanceConfigProvider.class) {
                localRef = defaultConfigValue;
                if (localRef == null) {
                    defaultConfigValue = localRef = ConfigFactory.getConfig()
                            .getValue(ConfigFactory.CONFIG_ITEM);;
                }
            }
        }
        return localRef;
    }

}
