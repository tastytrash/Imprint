package org.tastytrash.imprint.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import org.tastytrash.imprint.client.config.ImprintConfig;
import org.tastytrash.imprint.client.particle.ParticleRegistry;
import org.tastytrash.imprint.client.spawner.FootprintSpawner;

//? if fabric {
import net.fabricmc.api.ClientModInitializer;
//? }

public class ImprintClient
//? if fabric {
        implements ClientModInitializer
//? }
{
    public static final String MOD_ID = "imprint";
    public static ImprintConfig config;

    //? if fabric {
    @Override
    public void onInitializeClient() {
        ParticleRegistry.init();
        FootprintSpawner.register();
        AutoConfig.register(ImprintConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ImprintConfig.class).getConfig();
    }
    //? }

    //? if neoforge {
    /*
    @net.neoforged.fml.common.EventBusSubscriber(modid = ImprintClient.MOD_ID, value = net.neoforged.api.distmarker.Dist.CLIENT)
    public static class NeoForgeEvents {
        @net.neoforged.bus.api.SubscribeEvent
        public static void onClientTick(net.neoforged.neoforge.client.event.ClientTickEvent.Post event) {
            FootprintSpawner.tick(net.minecraft.client.Minecraft.getInstance());
        }
    }
     */
    //? }
}
