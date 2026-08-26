package org.tastytrash.imprint.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "imprint")
public class ImprintConfig implements ConfigData {

	@ConfigEntry.Category("general")
	@ConfigEntry.Gui.Tooltip
	public boolean enabled = true;

	@ConfigEntry.Category("general")
	@ConfigEntry.Gui.Tooltip
	public boolean showOtherPlayers = true;

	@ConfigEntry.Category("general")
	@ConfigEntry.Gui.Tooltip
	public boolean showMobs = true;

	@ConfigEntry.Category("general")
	@ConfigEntry.Gui.Tooltip
	public boolean showWhileCrouching = true;

	@ConfigEntry.Category("general")
	@ConfigEntry.Gui.Tooltip
	public boolean showWhileCrawling = true;

	public enum FootprintSizes {
		Tiny, Smaller, Small, Medium, Big, Large, Horse
	}

	@ConfigEntry.Category("visuals")
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
	public FootprintSizes footprintSizes = FootprintSizes.Medium;

	@ConfigEntry.Category("visuals")
	@ConfigEntry.Gui.Tooltip
	public double footprintLifetime = 3.0;

	@ConfigEntry.Category("visuals")
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.ColorPicker
	public int footprintColor = 0x000000;

	@ConfigEntry.Category("visuals")
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.BoundedDiscrete(min = 0, max = 100)
	public int alpha = 40;

	@ConfigEntry.Category("visuals")
	@ConfigEntry.Gui.Tooltip
	public boolean rainbowMode = false;

	@ConfigEntry.Category("movement")
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.BoundedDiscrete(min = 1, max = 20)
	public int tickInterval = 7;

	@ConfigEntry.Category("movement")
	@ConfigEntry.Gui.Tooltip
	public double speedThreshold = 0.6;

	@ConfigEntry.Category("movement")
	@ConfigEntry.Gui.Tooltip
	public double footOffset = 0.2;

	@ConfigEntry.Category("visuals")
	@ConfigEntry.Gui.Tooltip
	public boolean enableDustParticles = true;

	@ConfigEntry.Category("visuals")
	@ConfigEntry.Gui.Tooltip
	public boolean enableWetness = true;

	@ConfigEntry.Category("performance")
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.BoundedDiscrete(min = 16, max = 256)
	public int maxRenderDistance = 64;

	@ConfigEntry.Category("performance")
	@ConfigEntry.Gui.Tooltip
	public boolean removeDistantFootprints = true;

	@ConfigEntry.Category("performance")
	@ConfigEntry.Gui.Tooltip
	public boolean skipCollisionCheck = false;

	@ConfigEntry.Category("performance")
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.BoundedDiscrete(min = 0, max = 5000)
	public int maxParticles = 0;
}
