package org.tastytrash.imprint.client.particle;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
//? if fabric {
import net.minecraft.resources.Identifier;
//? }
import org.tastytrash.imprint.client.ImprintClient;

import java.util.LinkedHashMap;
import java.util.Map;

//? if fabric {
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
//? } else {
/*import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;*/
//? }

public class ParticleRegistry {
    //? if fabric {
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
        var particle = Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(ImprintClient.MOD_ID, name), FabricParticleTypes.simple());
        FACTORIES.put(particle, constructor);
        return particle;
    }
    //? } else {
    /*public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, ImprintClient.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FOOTPRINT_SMALLEST =
            PARTICLE_TYPES.register("footprint_smallest", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FOOTPRINT_SMALLER =
            PARTICLE_TYPES.register("footprint_smaller", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FOOTPRINT_SMALL =
            PARTICLE_TYPES.register("footprint_small", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FOOTPRINT_MEDIUM =
            PARTICLE_TYPES.register("footprint_medium", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FOOTPRINT_LARGE =
            PARTICLE_TYPES.register("footprint_large", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FOOTPRINT_LARGEST =
            PARTICLE_TYPES.register("footprint_largest", () -> new SimpleParticleType(false));

    public static void init(IEventBus modEventBus) {
        PARTICLE_TYPES.register(modEventBus);
    }

    public static void registerProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(FOOTPRINT_SMALLEST.get(), sprites -> new FootprintParticle.Factory(sprites, 3/32f));
        event.registerSpriteSet(FOOTPRINT_SMALLER.get(), sprites -> new FootprintParticle.Factory(sprites, 4/32f));
        event.registerSpriteSet(FOOTPRINT_SMALL.get(), sprites -> new FootprintParticle.Factory(sprites, 5/32f));
        event.registerSpriteSet(FOOTPRINT_MEDIUM.get(), sprites -> new FootprintParticle.Factory(sprites, 6/32f));
        event.registerSpriteSet(FOOTPRINT_LARGE.get(), sprites -> new FootprintParticle.Factory(sprites, 7/32f));
        event.registerSpriteSet(FOOTPRINT_LARGEST.get(), sprites -> new FootprintParticle.Factory(sprites, 8/32f));
    }*/
    //? }
}
