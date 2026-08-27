package org.tastytrash.imprint.client;

import com.mojang.logging.LogUtils;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import org.slf4j.Logger;
import org.tastytrash.imprint.config.ImprintConfig;

//? if fabric {
/*import org.tastytrash.imprint.particle.FabricParticleRegistry;
*///? }
import org.tastytrash.imprint.spawner.FootprintSpawner;

//? if forge {
/*import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
*///? }

//? if neoforge {
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

//? >= 26.1 {
import me.shedaniel.autoconfig.AutoConfigClient;
//? }

//?}

public class ImprintClient {
	public static final String MOD_ID = "imprint";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static ImprintConfig config;

	//? if neoforge || forge {
	public static void init(ModContainer container) {
	//? } else {
	/*public static void init() {
	*///?}

		AutoConfig.register(ImprintConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ImprintConfig.class).getConfig();

		//? fabric {
		 /*FabricParticleRegistry.init();
		*///? }

		FootprintSpawner.register();

		//? if neoforge {
		container.registerExtensionPoint(
				IConfigScreenFactory.class,
				//? < 26.1 {
				 /*(modContainer, parentScreen) -> AutoConfig.getConfigScreen(ImprintConfig.class, parentScreen).get()
				*///? } else {
				(modContainer, parentScreen) -> AutoConfigClient.getConfigScreen(ImprintConfig.class, parentScreen).get()
				//? }
		);
		//?} else if forge {
		/*ModLoadingContext.get().registerExtensionPoint(
				ConfigScreenHandler.ConfigScreenFactory.class,
				() -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) ->
						AutoConfig.getConfigScreen(ImprintConfig.class, parent).get()
				)
		);
		*///?}
	}
}
