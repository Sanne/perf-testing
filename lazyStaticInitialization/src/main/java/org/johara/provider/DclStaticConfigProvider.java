package org.johara.provider;

import org.johara.config.ConfigFactory;

public class DclStaticConfigProvider {
    private static volatile String defaultConfigValue;

    public static String getDefaultConfigValue() {
        if (defaultConfigValue == null) {
            synchronized (DclStaticConfigProvider.class) {
                if (defaultConfigValue == null) {
                    defaultConfigValue = ConfigFactory.getConfig()
                            .getValue(ConfigFactory.CONFIG_ITEM);;
                }
            }
        }
        return defaultConfigValue;
    }

}
