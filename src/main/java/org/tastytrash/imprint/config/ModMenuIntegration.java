package org.tastytrash.imprint.config;

//? fabric {
/*import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

//? >= 26.1 {

import me.shedaniel.autoconfig.AutoConfigClient;

//? } else {
/^import me.shedaniel.autoconfig.AutoConfig;
^///? }

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
		//? >= 26.1 {

		return parent -> AutoConfigClient.getConfigScreen(ImprintConfig.class, parent).get();

		//? } else {
		/^return parent -> AutoConfig.getConfigScreen(ImprintConfig.class, parent).get();
		^///? }

    }
}
*///?} neoforge {
public class ModMenuIntegration {
}
//?}
