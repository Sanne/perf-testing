package org.johara.provider;

import org.johara.config.ConfigFactory;

public class EagerCachedInstanceInstantiationProvider {
    private final String defaultConfigValue =  ConfigFactory.getConfig().getValue(ConfigFactory.CONFIG_ITEM);

    public String getDefaultConfigValue() {
        return defaultConfigValue;
    }

}
