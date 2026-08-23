package org.tastytrash.imprint.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import org.tastytrash.imprint.config.ImprintConfig;
import org.tastytrash.imprint.particle.ParticleRegistry;
import org.tastytrash.imprint.spawner.FootprintSpawner;

public class ImprintClient {
    public static final String MOD_ID = "imprint";

    public static ImprintConfig config;

    public static void init() {
        ParticleRegistry.init();
        FootprintSpawner.register();
        AutoConfig.register(ImprintConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ImprintConfig.class).getConfig();
    }
}
