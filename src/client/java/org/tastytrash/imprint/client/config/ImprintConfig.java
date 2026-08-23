package org.tastytrash.imprint.client.config;

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

    public enum FootprintSizes {
        Tiny, Smaller, Small, Medium, Big, Large
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
}