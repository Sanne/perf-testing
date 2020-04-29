package org.johara.config;

import java.util.HashMap;
import java.util.Map;

public class ConfigFactory {

    public static final String CONFIG_ITEM = "some.config";
    public static final String CONFIG_ITEM_DEV = "dev.config";

    private static Config config = new Config();

    static {

        config.addConfig(CONFIG_ITEM, "Some Config");
        config.addConfig(CONFIG_ITEM_DEV, "Some Dev Config");

    }

    public static Config getConfig() {

        //Add some cost to instantiating Config
        String results = CONFIG_ITEM;

        for (int i = 10; i > 0; i--) {
            results = results + i + "-";
        }

        return config;
    }

    public static class Config {
        private Map<String, String> values = new HashMap<>();

        public String getValue(String name) {
            return values.getOrDefault(name, "UNKNOWN");
        }

        public void addConfig(String key, String value) {
            this.values.put(key, value);
        }
    }
}

