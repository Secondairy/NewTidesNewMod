package me.secondairy;

import me.secondairy.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ModInitializer;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class NewTidesNewMod implements ModInitializer {

    public static final String MOD_ID = "newtidesnewmod";

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
    }
}
