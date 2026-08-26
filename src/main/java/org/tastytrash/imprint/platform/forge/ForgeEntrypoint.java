package org.tastytrash.imprint.platform.forge;

//? forge {

/*import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.tastytrash.imprint.Imprint;
import org.tastytrash.imprint.client.ImprintClient;
import org.tastytrash.imprint.particle.ForgeParticleRegistry;

@Mod(Imprint.MOD_ID)
public class ForgeEntrypoint {

	public ForgeEntrypoint() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ImprintClient.init(null);
		ForgeParticleRegistry.register(modEventBus);
	}
}
*///?}
