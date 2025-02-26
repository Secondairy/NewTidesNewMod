package me.secondairy.config;

import me.secondairy.NewTidesNewMod;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = NewTidesNewMod.MOD_ID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("Global")
    public Boolean global_toggle = true;

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("Tides")
    public Boolean enable_tides = true;
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("Tides")
    public String tides_response = "#newtidesnewvibes";

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("Winds")
    public  Boolean enable_winds = true;
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("Winds")
    public String winds_response = "#newwindsnewwins";

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("Info")
    public Boolean enable_info = true;
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("Info")
    public String info_response = "ingo";

    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("Ingo")
    public Boolean enable_ingo = true;
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Category("Ingo")
    public String ingo_response = "ingo";
}
