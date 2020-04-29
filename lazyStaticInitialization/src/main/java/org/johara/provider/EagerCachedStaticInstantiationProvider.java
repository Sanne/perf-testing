package org.johara.provider;

import org.johara.config.ConfigFactory;

public class EagerCachedStaticInstantiationProvider {
    private static final String defaultConfigValue =  ConfigFactory.getConfig().getValue(ConfigFactory.CONFIG_ITEM);

    public static String getDefaultConfigValue() {
        return defaultConfigValue;
    }

}
