package org.tastytrash.imprint.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import org.tastytrash.imprint.config.ImprintConfig;
//? if neoforge {
/*import org.tastytrash.imprint.particle.NeoForgeParticleRegistry;
*///? } else {
import org.tastytrash.imprint.particle.FabricParticleRegistry;
//? }
import org.tastytrash.imprint.particle.ParticleRegistry;
import org.tastytrash.imprint.spawner.FootprintSpawner;

//? if neoforge {
/*import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

//? >= 26.1 {
import me.shedaniel.autoconfig.AutoConfigClient;
//? }

*///?}

public class ImprintClient {
	public static final String MOD_ID = "imprint";

	public static ImprintConfig config;

	//? if neoforge {
	/*public static void init(ModContainer container) {
		*///?} else {
		public static void init() {
		//?}
		//? fabric {
		 FabricParticleRegistry.init();
		//? }

		FootprintSpawner.register();
		AutoConfig.register(ImprintConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ImprintConfig.class).getConfig();

		//? if neoforge {
		/*container.registerExtensionPoint(
				IConfigScreenFactory.class,
				//? < 26.1 {
				 /^(modContainer, parentScreen) -> AutoConfig.getConfigScreen(ImprintConfig.class, parentScreen).get()
				^///? } else {
				(modContainer, parentScreen) -> AutoConfigClient.getConfigScreen(ImprintConfig.class, parentScreen).get()
				//? }
		);
		*///?}
	}
}
