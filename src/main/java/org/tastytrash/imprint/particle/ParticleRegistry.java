package org.tastytrash.imprint.particle;

//? >= 26.1 {

/*import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;

*///? } else {
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
//? }

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.tastytrash.imprint.client.ImprintClient;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParticleRegistry {
	//? >= 26.1 {

	/*public static final Map<SimpleParticleType, ParticleProviderRegistry.PendingParticleProvider<SimpleParticleType>> FACTORIES = new LinkedHashMap<>();

	*///? } else {
	public static final Map<SimpleParticleType, ParticleFactoryRegistry.PendingParticleFactory<SimpleParticleType>> FACTORIES = new LinkedHashMap<>();
	//? }

	public static final SimpleParticleType FOOTPRINT_SMALLEST = register("footprint_smallest", sprites -> new FootprintParticle.Factory(sprites, 3/32f));
	public static final SimpleParticleType FOOTPRINT_SMALLER = register("footprint_smaller", sprites -> new FootprintParticle.Factory(sprites, 4/32f));
	public static final SimpleParticleType FOOTPRINT_SMALL = register("footprint_small", sprites -> new FootprintParticle.Factory(sprites, 5/32f));
	public static final SimpleParticleType FOOTPRINT_MEDIUM = register("footprint_medium", sprites -> new FootprintParticle.Factory(sprites, 6/32f));
	public static final SimpleParticleType FOOTPRINT_LARGE = register("footprint_large", sprites -> new FootprintParticle.Factory(sprites, 7/32f));
	public static final SimpleParticleType FOOTPRINT_LARGEST = register("footprint_largest", sprites -> new FootprintParticle.Factory(sprites, 8/32f));

	public static void init() {
		//? >= 26.1 {

		/*ParticleProviderRegistry registry = ParticleProviderRegistry.getInstance();

		*///? } else {
		 ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();
		//? }
		FACTORIES.forEach(registry::register);
	}

	//? >= 26.1 {
	//private static SimpleParticleType register(String name, ParticleProviderRegistry.PendingParticleProvider<SimpleParticleType> constructor) {
	//? } else {
	private static SimpleParticleType register(String name, ParticleFactoryRegistry.PendingParticleFactory<SimpleParticleType> constructor) {
	//? }
		var particle = Registry.register(BuiltInRegistries.PARTICLE_TYPE,
				//? > 1.20.1 {
				//ResourceLocation.fromNamespaceAndPath(ImprintClient.MOD_ID, name),
				//? } else {
				new ResourceLocation(ImprintClient.MOD_ID, name),
				//? }
				FabricParticleTypes.simple(true));
		FACTORIES.put(particle, constructor);
		return particle;
	}
}

