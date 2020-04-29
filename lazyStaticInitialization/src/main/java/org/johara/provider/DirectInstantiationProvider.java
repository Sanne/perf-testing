package org.johara.provider;

import org.johara.config.ConfigFactory;

public class DirectInstantiationProvider {

    public String getDefaultConfigValue() {
        return ConfigFactory.getConfig()
                .getValue(ConfigFactory.CONFIG_ITEM);
    }

}
