package org.tastytrash.imprint.particle;
//? if neoforge {

/*import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.tastytrash.imprint.client.ImprintClient;

public class NeoForgeParticleRegistry {
	private static final DeferredRegister<ParticleType<?>> REGISTRY =
			DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, ImprintClient.MOD_ID);

	private static final DeferredHolder<ParticleType<?>, SimpleParticleType> SMALLEST = REGISTRY.register("footprint_smallest", () -> new SimpleParticleType(true));
	private static final DeferredHolder<ParticleType<?>, SimpleParticleType> SMALLER = REGISTRY.register("footprint_smaller", () -> new SimpleParticleType(true));
	private static final DeferredHolder<ParticleType<?>, SimpleParticleType> SMALL = REGISTRY.register("footprint_small", () -> new SimpleParticleType(true));
	private static final DeferredHolder<ParticleType<?>, SimpleParticleType> MEDIUM = REGISTRY.register("footprint_medium", () -> new SimpleParticleType(true));
	private static final DeferredHolder<ParticleType<?>, SimpleParticleType> LARGE = REGISTRY.register("footprint_large", () -> new SimpleParticleType(true));
	private static final DeferredHolder<ParticleType<?>, SimpleParticleType> LARGEST = REGISTRY.register("footprint_largest", () -> new SimpleParticleType(true));

	public static void register(IEventBus modBus) {
		REGISTRY.register(modBus);
		modBus.register(NeoForgeParticleRegistry.class);
	}

	@SubscribeEvent
	public static void onRegisterProviders(RegisterParticleProvidersEvent event) {
		ParticleRegistry.FOOTPRINT_SMALLEST = SMALLEST.get();
		ParticleRegistry.FOOTPRINT_SMALLER = SMALLER.get();
		ParticleRegistry.FOOTPRINT_SMALL = SMALL.get();
		ParticleRegistry.FOOTPRINT_MEDIUM = MEDIUM.get();
		ParticleRegistry.FOOTPRINT_LARGE = LARGE.get();
		ParticleRegistry.FOOTPRINT_LARGEST = LARGEST.get();

		event.registerSpriteSet(ParticleRegistry.FOOTPRINT_SMALLEST, sprites -> new FootprintParticle.Factory(sprites, 3/32f));
		event.registerSpriteSet(ParticleRegistry.FOOTPRINT_SMALLER, sprites -> new FootprintParticle.Factory(sprites, 4/32f));
		event.registerSpriteSet(ParticleRegistry.FOOTPRINT_SMALL, sprites -> new FootprintParticle.Factory(sprites, 5/32f));
		event.registerSpriteSet(ParticleRegistry.FOOTPRINT_MEDIUM, sprites -> new FootprintParticle.Factory(sprites, 6/32f));
		event.registerSpriteSet(ParticleRegistry.FOOTPRINT_LARGE, sprites -> new FootprintParticle.Factory(sprites, 7/32f));
		event.registerSpriteSet(ParticleRegistry.FOOTPRINT_LARGEST, sprites -> new FootprintParticle.Factory(sprites, 8/32f));

		org.tastytrash.imprint.util.FootprintUtil.loadFromJson();
	}
}
*///? }
