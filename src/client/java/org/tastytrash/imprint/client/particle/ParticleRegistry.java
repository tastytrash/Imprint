package org.tastytrash.imprint.client.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.tastytrash.imprint.client.ImprintClient;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParticleRegistry {
    public static final Map<SimpleParticleType, ParticleProviderRegistry.PendingParticleProvider<SimpleParticleType>> FACTORIES = new LinkedHashMap<>();

    public static final SimpleParticleType FOOTPRINT_SMALLEST = register("footprint_smallest", sprites -> new FootprintParticle.Factory(sprites, 3/32f));
    public static final SimpleParticleType FOOTPRINT_SMALLER = register("footprint_smaller", sprites -> new FootprintParticle.Factory(sprites, 4/32f));
    public static final SimpleParticleType FOOTPRINT_SMALL = register("footprint_small", sprites -> new FootprintParticle.Factory(sprites, 5/32f));
    public static final SimpleParticleType FOOTPRINT_MEDIUM = register("footprint_medium", sprites -> new FootprintParticle.Factory(sprites, 6/32f));
    public static final SimpleParticleType FOOTPRINT_LARGE = register("footprint_large", sprites -> new FootprintParticle.Factory(sprites, 7/32f));
    public static final SimpleParticleType FOOTPRINT_LARGEST = register("footprint_largest", sprites -> new FootprintParticle.Factory(sprites, 8/32f));

    public static void init() {
        ParticleProviderRegistry registry = ParticleProviderRegistry.getInstance();
        FACTORIES.forEach(registry::register);
    }

    private static SimpleParticleType register(String name, ParticleProviderRegistry.PendingParticleProvider<SimpleParticleType> constructor) {
        var particle = Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                Identifier.fromNamespaceAndPath(ImprintClient.MOD_ID, name),
                FabricParticleTypes.simple(true));
        FACTORIES.put(particle, constructor);
        return particle;
    }
}
