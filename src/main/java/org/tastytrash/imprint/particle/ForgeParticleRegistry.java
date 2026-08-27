package org.tastytrash.imprint.particle;
//? if forge {

/*import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.tastytrash.imprint.client.ImprintClient;

public class ForgeParticleRegistry {
	private static final DeferredRegister<ParticleType<?>> REGISTRY =
			DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ImprintClient.MOD_ID);

	private static final RegistryObject<SimpleParticleType> SMALLEST = REGISTRY.register("footprint_smallest", () -> new SimpleParticleType(true));
	private static final RegistryObject<SimpleParticleType> SMALLER = REGISTRY.register("footprint_smaller", () -> new SimpleParticleType(true));
	private static final RegistryObject<SimpleParticleType> SMALL = REGISTRY.register("footprint_small", () -> new SimpleParticleType(true));
	private static final RegistryObject<SimpleParticleType> MEDIUM = REGISTRY.register("footprint_medium", () -> new SimpleParticleType(true));
	private static final RegistryObject<SimpleParticleType> LARGE = REGISTRY.register("footprint_large", () -> new SimpleParticleType(true));
	private static final RegistryObject<SimpleParticleType> LARGEST = REGISTRY.register("footprint_largest", () -> new SimpleParticleType(true));

	public static void register(IEventBus modBus) {
		REGISTRY.register(modBus);
		modBus.register(ForgeParticleRegistry.class);
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
