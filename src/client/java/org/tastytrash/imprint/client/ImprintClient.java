package org.tastytrash.imprint.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import org.tastytrash.imprint.client.config.ImprintConfig;
import org.tastytrash.imprint.client.particle.ParticleRegistry;
import org.tastytrash.imprint.client.spawner.FootprintSpawner;

public class ImprintClient implements ClientModInitializer {
    public static final String MOD_ID = "imprint";

    public static ImprintConfig config;

    @Override
    public void onInitializeClient() {
        ParticleRegistry.init();
        FootprintSpawner.register();
        AutoConfig.register(ImprintConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ImprintConfig.class).getConfig();
    }
}