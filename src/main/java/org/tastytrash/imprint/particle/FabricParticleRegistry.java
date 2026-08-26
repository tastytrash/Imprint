package org.tastytrash.imprint.particle;
//? if fabric {

/*import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.tastytrash.imprint.client.ImprintClient;

//? >= 26.1 {
/^import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
^///? } else {
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
//? }
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
*///? }

public class FabricParticleRegistry {
	//? if fabric {
	/*public static void init() {
		ParticleRegistry.FOOTPRINT_SMALLEST = register("footprint_smallest", 3/32f);
		ParticleRegistry.FOOTPRINT_SMALLER = register("footprint_smaller", 4/32f);
		ParticleRegistry.FOOTPRINT_SMALL = register("footprint_small", 5/32f);
		ParticleRegistry.FOOTPRINT_MEDIUM = register("footprint_medium", 6/32f);
		ParticleRegistry.FOOTPRINT_LARGE = register("footprint_large", 7/32f);
		ParticleRegistry.FOOTPRINT_LARGEST = register("footprint_largest", 8/32f);
	}

	private static SimpleParticleType register(String name, float size) {
		SimpleParticleType type = Registry.register(
				BuiltInRegistries.PARTICLE_TYPE,
				//? > 1.20.1 {
				/^Identifier.fromNamespaceAndPath(ImprintClient.MOD_ID, name),
				^///? } else {
				new Identifier(ImprintClient.MOD_ID, name),
				//? }
				FabricParticleTypes.simple(true)
		);
		//? >= 26.1 {
		/^ParticleProviderRegistry.getInstance().register(type, sprites -> new FootprintParticle.Factory(sprites, size));
		^///? } else {
		ParticleFactoryRegistry.getInstance().register(type, sprites -> new FootprintParticle.Factory(sprites, size));
		//? }
		return type;
	}
	*///? }
}
