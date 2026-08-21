package org.tastytrash.imprint.client;

import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.tastytrash.imprint.client.particle.FootprintParticle;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParticleRegistry {
    public static final Map<SimpleParticleType, ParticleProviderRegistry.PendingParticleProvider<SimpleParticleType>> FACTORIES = new LinkedHashMap<>();

    public static final SimpleParticleType FOOTPRINT = register("footprint", FootprintParticle.Factory::new);
    public static void init() {
        ParticleProviderRegistry registry = ParticleProviderRegistry.getInstance();
        FACTORIES.forEach(registry::register);
    }

    private static SimpleParticleType register(String name, ParticleProviderRegistry.PendingParticleProvider<SimpleParticleType> constructor) {
        var particle = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(ImprintClient.MOD_ID, name), FabricParticleTypes.simple());
        FACTORIES.put(particle, constructor);
        return particle;
    }
}
