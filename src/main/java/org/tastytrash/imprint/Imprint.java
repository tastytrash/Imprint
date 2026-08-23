package org.tastytrash.imprint;

import org.tastytrash.imprint.platform.Platform;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? fabric {
import org.tastytrash.imprint.platform.fabric.FabricPlatform;
//?} neoforge {
/*import com.example.modtemplate.platform.neoforge.NeoforgePlatform;
 *///?} forge {
/*import com.example.modtemplate.platform.forge.ForgePlatform;
 *///?}

@SuppressWarnings("LoggingSimilarMessage")
public class Imprint {

	public static final String MOD_ID = /*$ mod_id*/ "imprint";
	public static final String MOD_VERSION = /*$ mod_version*/ "1.2";
	public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/ "Imprint";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final Platform PLATFORM = createPlatformInstance();

	public static void onInitialize() {
		LOGGER.info("Initializing {} on {}", MOD_ID, Imprint.xplat().loader());
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	public static void onInitializeClient() {
		LOGGER.info("Initializing {} Client on {}", MOD_ID, Imprint.xplat().loader());
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	static Platform xplat() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		/*return new NeoforgePlatform();
		 *///?} forge {
		/*return new ForgePlatform();
		 *///?}
	}

	private static ResourceLocation id(String path) {
		//? > 1.20.1 {
		// return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
		 //?} <= 1.20.1 {
		return new ResourceLocation(MOD_ID, path);
		//?}
	}

	private static ResourceLocation id(String namespace, String path) {
		//? > 1.20.1 {
		// return ResourceLocation.fromNamespaceAndPath(namespace, path);
		//?} <= 1.20.1 {
		return new ResourceLocation(namespace, path);
		//?}
	}
}
