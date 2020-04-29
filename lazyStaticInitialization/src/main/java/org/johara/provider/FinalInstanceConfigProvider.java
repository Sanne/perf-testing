package org.johara.provider;

import org.johara.config.ConfigFactory;

public class FinalInstanceConfigProvider {

    private FinalLazyInitializer finalWrapper;

    private class FinalLazyInitializer {
        final String defaultConfigValue;
        public FinalLazyInitializer(String defaultConfigValue) {
            this.defaultConfigValue = defaultConfigValue;
        }
    }

    public String getDefaultConfigValue(){
        FinalLazyInitializer tempWrapper = finalWrapper;

        if (tempWrapper == null) {
            synchronized (FinalInstanceConfigProvider.class) {
                if (finalWrapper == null) {
                    finalWrapper = new FinalLazyInitializer(ConfigFactory.getConfig().getValue(ConfigFactory.CONFIG_ITEM));
                }
                tempWrapper = finalWrapper;
            }
        }
        return tempWrapper.defaultConfigValue;
    }

}
