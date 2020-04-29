package org.johara.provider;

import org.johara.config.ConfigFactory;

public class DclInstanceConfigProvider {
    private volatile String defaultConfigValue;

    public String getDefaultConfigValue() {
        if (defaultConfigValue == null) {
            synchronized (DclInstanceConfigProvider.class) {
                if (defaultConfigValue == null) {
                    defaultConfigValue = ConfigFactory.getConfig()
                            .getValue(ConfigFactory.CONFIG_ITEM);;
                }
            }
        }
        return defaultConfigValue;
    }

}
