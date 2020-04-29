package org.johara.provider;

import org.johara.config.ConfigFactory;

public class DclLocalRefStaticConfigProvider {
    private static volatile String defaultConfigValue;

    public static String getDefaultConfigValue() {
        String localRef = defaultConfigValue;
        if (localRef == null) {
            synchronized (DclLocalRefStaticConfigProvider.class) {
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
