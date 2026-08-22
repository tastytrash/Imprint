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

    @ConfigEntry.Category("movement")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int tickInterval = 7;

    @ConfigEntry.Category("movement")
    @ConfigEntry.Gui.Tooltip
    public double speedThreshold = 0.6;

    @ConfigEntry.Category("movement")
    @ConfigEntry.Gui.Tooltip
    public double footOffset = 0.3;

    @ConfigEntry.Category("visuals")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 300)
    public int footprintLifetime = 60;

    @ConfigEntry.Category("visuals")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.ColorPicker
    public int footprintColor = 0x000000;

    @ConfigEntry.Category("visuals")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int alpha = 100;

    @ConfigEntry.Category("visuals")
    @ConfigEntry.Gui.Tooltip
    public double scale = 1.0;

    @ConfigEntry.Category("visuals")
    @ConfigEntry.Gui.Tooltip
    public boolean rainbowMode = false;
}