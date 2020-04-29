package org.johara;

import org.johara.provider.EagerCachedInstanceInstantiationProvider;
import org.johara.provider.DclInstanceConfigProvider;
import org.johara.provider.DclLocalRefInstanceConfigProvider;
import org.johara.provider.DclLocalRefStaticConfigProvider;
import org.johara.provider.DclStaticConfigProvider;
import org.johara.provider.DclVarHandleInstanceConfigProvider;
import org.johara.provider.DirectInstantiationProvider;
import org.johara.provider.EagerCachedStaticInstantiationProvider;
import org.johara.provider.FinalInstanceConfigProvider;
import org.johara.provider.HelperStaticConfigProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LazyInitializationTestCases {

    private static String CONFIG_RESPONSE = "Some Config";

    @Test
    public void dclInstanceConfigTest() {
        assertEquals(CONFIG_RESPONSE, new DclInstanceConfigProvider().getDefaultConfigValue());
    }

    @Test
    public void dclLocalRefInstanceConfigTest() {
        assertEquals(CONFIG_RESPONSE, new DclLocalRefInstanceConfigProvider().getDefaultConfigValue());
    }

    @Test
    public void dclLocalRefStaticConfigTest() {
        assertEquals(CONFIG_RESPONSE, DclLocalRefStaticConfigProvider.getDefaultConfigValue());
    }

    @Test
    public void dclStaticConfigTest() {
        assertEquals(CONFIG_RESPONSE, DclStaticConfigProvider.getDefaultConfigValue());
    }

    @Test
    public void dclVarHandleRefConfigTest() {
        assertEquals(CONFIG_RESPONSE, new DclVarHandleInstanceConfigProvider().getDefaultConfigValue());
    }

    @Test
    public void directConfigTest() {
        assertEquals(CONFIG_RESPONSE, new DirectInstantiationProvider().getDefaultConfigValue());
    }

    @Test
    public void directConfigBlackHoleTest() {
        assertEquals(CONFIG_RESPONSE, new DirectInstantiationProvider().getDefaultConfigValue());
    }

    @Test
    public void eagerCachedInstanceConfigTest() {
        assertEquals(CONFIG_RESPONSE, new EagerCachedInstanceInstantiationProvider().getDefaultConfigValue());
    }

    @Test
    public void eagerCachedStaticConfigTest() {
        assertEquals(CONFIG_RESPONSE, EagerCachedStaticInstantiationProvider.getDefaultConfigValue());
    }


    @Test
    public void finalInstanceConfigTest() {
        assertEquals(CONFIG_RESPONSE, new FinalInstanceConfigProvider().getDefaultConfigValue());
    }

    @Test
    public void helperStaticConfigTest() {
        assertEquals(CONFIG_RESPONSE, HelperStaticConfigProvider.getDefaultConfigValue());
    }

}
