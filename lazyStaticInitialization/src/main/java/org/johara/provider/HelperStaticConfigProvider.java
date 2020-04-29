package org.johara.provider;

import org.johara.config.ConfigFactory;

public class HelperStaticConfigProvider {

    private static class LazyConfigInitializer {
        static final String defaultConfigValue = ConfigFactory.getConfig().getValue(ConfigFactory.CONFIG_ITEM);
    }

    public static String getDefaultConfigValue(){
        return LazyConfigInitializer.defaultConfigValue;
    }

}
