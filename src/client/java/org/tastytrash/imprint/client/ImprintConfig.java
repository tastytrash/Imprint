package org.tastytrash.imprint.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "imprint")
public class ImprintConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean enabled = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 600)
    public int footprintLifetime = 200;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int tickInterval = 6;

    @ConfigEntry.Gui.Tooltip
    public double speedThreshold = 0.1;

    @ConfigEntry.Gui.Tooltip
    public double footOffset = 0.3;

    @ConfigEntry.Gui.Tooltip
    public boolean showWhileCrouching = false;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.ColorPicker
    public int footprintColor = 0x000000;

    @ConfigEntry.Gui.Tooltip
    public boolean rainbowMode = false;

    @ConfigEntry.Gui.Tooltip
    public boolean showOtherPlayers = true;

    @ConfigEntry.Gui.Tooltip
    public boolean showMobs = true;

    @ConfigEntry.Gui.Tooltip
    public double mobSpeedThreshold = 0.04;

    @ConfigEntry.Gui.CollapsibleObject
    public ParticleSettings particleSettings = new ParticleSettings();

    public static class ParticleSettings {
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 255)
        public int alpha = 255;

        @ConfigEntry.Gui.Tooltip
        public double scale = 1.0;
    }
}
